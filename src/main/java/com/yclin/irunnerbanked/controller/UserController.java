package com.yclin.irunnerbanked.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yclin.irunnerbanked.common.BaseResponse;
import com.yclin.irunnerbanked.common.ResultUtils;
import com.yclin.irunnerbanked.model.domain.User;
import com.yclin.irunnerbanked.model.request.UpdateUserRequest;
import com.yclin.irunnerbanked.model.request.UserLoginRequest;
import com.yclin.irunnerbanked.model.request.UserRegisterRequest;
import com.yclin.irunnerbanked.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 用户接口
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return ResultUtils.error(400, "参数为空");
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return null;
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(result);
    }

    @PostMapping("/login")
    public User userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request ){
        if(userLoginRequest == null){
            return null;
        }

        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();

        if(StringUtils.isAnyBlank(userAccount, userPassword)){
            return null;
        }
        return  userService.userlogin(userAccount, userPassword, request);
    };

    @GetMapping("/search")
    public List<User> searchUsers(String username,HttpServletRequest servletRequest) {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like("username", username);

            return userService.list(queryWrapper);
        }
        return userService.list(queryWrapper);

    }

    @DeleteMapping("/delete/{id}")
    public BaseResponse<Boolean> deleteUsers(@PathVariable long id) {
        if (id <= 0) {
            return ResultUtils.error(400, "ID错误");
        }
        boolean result = userService.removeById(id);
        return ResultUtils.success(result);
    }


    /**
     * 更新当前用户信息
     * @param userUpdateRequest
     * @param request
     * @return
     */
    @PostMapping("/update")
    public BaseResponse<Integer> updateUser(@RequestBody UpdateUserRequest userUpdateRequest, HttpServletRequest request) {
        if (userUpdateRequest == null) {
            // 建议使用我们之前讨论过的全局异常处理来简化这里
            return ResultUtils.error(400, "请求参数为空");
        }

        // 1. 获取当前登录用户
        User loginUser = userService.getLoginUser(request);
        if (loginUser == null) {
            return ResultUtils.error(401, "未登录");
        }

        // 2. 调用 service 更新
        int result = userService.updateUser(userUpdateRequest, loginUser);

        return ResultUtils.success(result);
    }

    /**
     * 获取当前登录用户
     * @param request
     * @return
     */
    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        if (loginUser == null) {
            return ResultUtils.error(401, "未登录");
        }
        // 脱敏后返回
        User safetyUser = userService.getSafetyUser(loginUser);
        return ResultUtils.success(safetyUser);
    }

}
