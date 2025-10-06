package com.yclin.irunnerbanked.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yclin.irunnerbanked.model.domain.User;
import com.yclin.irunnerbanked.model.request.UpdateUserRequest;
import com.yclin.irunnerbanked.service.UserService;
import com.yclin.irunnerbanked.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.yclin.irunnerbanked.constant.UserConstant.SALT;
import static com.yclin.irunnerbanked.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author yclin
* @description 针对表【user(用户表)】的数据库操作Service实现
* @createDate 2025-10-05 11:21:09
*/
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Resource
    private  UserMapper userMapper;



    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1.校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            return -1;
        }
        if (userAccount.length() < 4) {
            return -1;
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8){
            return -1;
        }

        // 账户不能包含特殊字符
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return -1;
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            return -1;
        }

        // 账户不能重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userMapper.selectCount(queryWrapper);
        if (count > 0) {
            return -1;
        }
        // 2.加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 3.插入数据
        User user = new User();
        user.setUseraccount(userAccount);
        user.setUserpassword(encryptPassword);
        boolean saveResult = this.save(user);
        if (!saveResult) {
            return -1;
        }
        return user.getId();
    }

    @Override
    public User userlogin(String userAccount, String userPassword, HttpServletRequest request) {
        // 1.校验
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        if (userAccount.length() < 4) {
            return null;
        }
        if (userPassword.length() < 8){
            return null;
        }

        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            return null;
        }
        // 2.加密

        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            log.info("user login failed, userAccount Cannot match userPassword");
            return null;
        }
        //3.用户脱敏
        request.getSession().setAttribute(USER_LOGIN_STATE, user);

        User safetyuser = new User();
        safetyuser.setId(user.getId());
        safetyuser.setUsername(user.getUsername());
        safetyuser.setUseraccount(user.getUseraccount());
        safetyuser.setAvatarurl(user.getAvatarurl());
        safetyuser.setGender(user.getGender());
        safetyuser.setEmail(user.getEmail());
        safetyuser.setUserrole(user.getUserrole());
        safetyuser.setUserstatus(user.getUserstatus());
        safetyuser.setPhone(user.getPhone());
        safetyuser.setCreatetime(user.getCreatetime());
        return safetyuser;
    }


    /**
     * 更新用户信息
     *
     * @param userUpdateRequest 更新请求参数
     * @param loginUser         当前登录用户
     * @return
     */
    @Override
    public int updateUser(UpdateUserRequest userUpdateRequest, User loginUser) {
        Long userId = loginUser.getId();
        User oldUser = this.getById(userId);
        if (oldUser == null) {
            throw new RuntimeException("用户不存在");
        }

        User userToUpdate = new User();
        userToUpdate.setId(userId);
        userToUpdate.setUsername(userUpdateRequest.getUsername());
        userToUpdate.setAvatarurl(userUpdateRequest.getAvatarUrl());
        userToUpdate.setGender(userUpdateRequest.getGender());
        userToUpdate.setPhone(userUpdateRequest.getPhone());
        userToUpdate.setEmail(userUpdateRequest.getEmail());

        // 使用 MyBatis-Plus 的 updateById 方法，它会自动忽略 null 值的字段
        boolean result = this.updateById(userToUpdate);
        if (!result) {
            throw new RuntimeException("更新失败");
        }
        return 1; // 返回1表示成功更新1条记录
    }
    @Override
    public User getLoginUser(HttpServletRequest request) {
        Object userObject = request.getSession().getAttribute(USER_LOGIN_STATE);
        if (userObject == null) {
            throw new RuntimeException("用户未登录");
        }
        return (User) userObject;
    }


    @Override
    public User getSafetyUser(User originUser) {
        if (originUser == null) {
            return null;
        }
        User safetyUser = new User();
        safetyUser.setId(originUser.getId());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUseraccount(originUser.getUseraccount());
        safetyUser.setAvatarurl(originUser.getAvatarurl());
        safetyUser.setGender(originUser.getGender());
        safetyUser.setEmail(originUser.getEmail());
        safetyUser.setUserrole(originUser.getUserrole());
        safetyUser.setUserstatus(originUser.getUserstatus());
        safetyUser.setPhone(originUser.getPhone());
        safetyUser.setCreatetime(originUser.getCreatetime());
        // 确保密码字段为 null
        safetyUser.setUserpassword(null);
        return safetyUser;
    }
}




