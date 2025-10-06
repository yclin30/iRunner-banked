package com.yclin.irunnerbanked.service;

import com.yclin.irunnerbanked.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yclin.irunnerbanked.model.request.UpdateUserRequest;

import javax.servlet.http.HttpServletRequest;

/**
* @author yclin
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2025-10-05 11:21:09
*/
public interface UserService extends IService<User> {
    /**
     *
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @return 新用户id
     */
    long userRegister(String userAccount, String userPassword,String checkPassword);

    /**
     *
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @return 脱敏后数据
     */
    User userlogin(String userAccount, String userPassword, HttpServletRequest request);

    int updateUser(UpdateUserRequest userUpdateRequest, User loginUser);

    User getSafetyUser(User originUser);

    User getLoginUser(HttpServletRequest request);

}
