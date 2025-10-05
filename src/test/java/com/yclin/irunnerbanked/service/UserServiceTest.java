package com.yclin.irunnerbanked.service;

import com.yclin.irunnerbanked.model.domain.User;
import org.junit.jupiter.api.Test; // <-- 1. 改为 JUnit 5 的 @Test
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

// @SpringBootTest 会自动加载 Spring 上下文并启用依赖注入
@SpringBootTest
public class UserServiceTest {

    // @Resource 和 @Autowired 在这里效果相同
    @Resource
    private UserService userService;

    @Test // <-- 确保这是 org.junit.jupiter.api.Test
    public void testAddUser() {
        // 在调用前，可以加一个断言确保注入成功，这是一个好习惯
        Assertions.assertNotNull(userService, "UserService should be injected by Spring");

        User user = new User();
        user.setUsername("lyc");
        user.setUseraccount("123456");
        user.setAvatarurl("123");
        user.setGender(0);
        user.setUserpassword("xxx");
        user.setEmail("123");
        user.setPhone("456");

        // 现在 userService 不会是 null 了
        boolean result = userService.save(user);

        System.out.println("New user ID: " + user.getId());
        Assertions.assertTrue(result, "User should be saved successfully");
    }

    @Test
    void userRegister() {
        String userAccount = "yclin";
        String userPassword = "";
        String checkPassword = "123456";
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "yclin";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "yclin";
        userPassword = "123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "yu pi";
        userPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        checkPassword = "123456789";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "ycl in";
        checkPassword = "12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1, result);

        userAccount = "yclin";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertTrue(result > 0);
    }
}