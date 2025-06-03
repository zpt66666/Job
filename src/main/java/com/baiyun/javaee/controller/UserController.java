package com.baiyun.javaee.controller;

import com.baiyun.javaee.common.ApiResponse;
import com.baiyun.javaee.common.exception.BusinessException;
import com.baiyun.javaee.model.User;
import com.baiyun.javaee.model.dto.UserLoginDTO;
import com.baiyun.javaee.model.dto.UserRegisterDTO;
import com.baiyun.javaee.model.dto.UserUpdateDTO;
import com.baiyun.javaee.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户控制器
 *
 * @author Craft
 */
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 用户注册
     *
     * @param registerDTO 注册信息
     * @return 注册结果
     */
    @PostMapping("/register")
    public ApiResponse<?> register(@Valid @RequestBody UserRegisterDTO registerDTO) {
        // 验证两次密码是否一致
        if (!registerDTO.getPassword().equals(registerDTO.getConfirmPassword())) {
            throw BusinessException.badRequest("两次输入的密码不一致");
        }

        try {
            // 转换DTO为实体对象
            User user = new User()
                    .setUsername(registerDTO.getUsername())
                    .setPassword(registerDTO.getPassword())
                    .setEmail(registerDTO.getEmail())
                    .setRealName(registerDTO.getRealName())
                    .setPhone(registerDTO.getPhone());

            // 执行注册
            User registeredUser = userService.register(user);

            return ApiResponse.success("注册成功", registeredUser);
        } catch (IllegalArgumentException e) {
            throw BusinessException.badRequest(e.getMessage());
        }
    }

    /**
     * 用户登录
     *
     * @param loginDTO 登录信息
     * @return 登录结果
     */
    @PostMapping("/login")
    public ApiResponse<?> login(@Valid @RequestBody UserLoginDTO loginDTO) {
        // 调用UserService进行登录验证
        User user = userService.login(loginDTO.getUsernameOrEmail(), loginDTO.getPassword());

        // TODO: 在这里处理登录成功后的会话管理，例如将用户信息存入Session或生成JWT
        // 目前暂时省略会话管理部分

        // 将用户存入会话 (临时的会话管理方式)
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        request.getSession().setAttribute("user", user);

        return ApiResponse.success("登录成功", user);
    }

    /**
     * 检查用户名是否可用
     *
     * @param username 用户名
     * @return 检查结果
     */
    @GetMapping("/check-username")
    public ApiResponse<?> checkUsername(@RequestParam String username) {
        Map<String, Object> response = new HashMap<>();
        response.put("available", !userService.isUsernameExists(username));
        return ApiResponse.success(response);
    }

    /**
     * 检查邮箱是否可用
     *
     * @param email 邮箱
     * @return 检查结果
     */
    @GetMapping("/check-email")
    public ApiResponse<?> checkEmail(@RequestParam String email) {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            throw BusinessException.unauthorized("用户未登录");
        }

        // 如果邮箱与当前用户的邮箱相同，则认为是可用的
        if (email.equals(currentUser.getEmail())) {
            return ApiResponse.success(false);
        }

        // 检查邮箱是否已被其他用户使用
        boolean isEmailExists = userService.isEmailExists(email);
        return ApiResponse.success(isEmailExists);
    }

    /**
     * 获取当前登录用户信息
     *
     * @return 用户信息
     */
    @GetMapping("/current")
    public ApiResponse<?> getCurrentUser() {
        User currentUser = userService.getCurrentUser();
        if (currentUser == null) {
            throw BusinessException.unauthorized("用户未登录");
        }
        // 可以返回一个UserDTO，避免敏感信息泄露，这里简化直接返回User
        // 为了演示头像功能，临时添加头像URL
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", currentUser.getId());
        userMap.put("username", currentUser.getUsername());
        userMap.put("email", currentUser.getEmail());
        userMap.put("realName", currentUser.getRealName());
        userMap.put("phone", currentUser.getPhone());
        userMap.put("userType", currentUser.getUserType());
        userMap.put("status", currentUser.getStatus());
        // TODO: 这里需要根据实际的用户头像存储方式来生成URL
        // 假设用户头像文件名为 用户ID.jpg 或 用户名.jpg，存储在 /career/images/ 目录下
        // 为了演示，我们暂时使用一个固定的图片路径，请替换成您的图片文件名
        userMap.put("avatarUrl", "/career/images/1.png"); // <-- 已更新为 1.png

        return ApiResponse.success(userMap);
    }

    /**
     * 更新用户信息
     *
     * @param updateDTO 用户更新信息
     * @return 更新结果
     */
    @PutMapping("/update")
    public ApiResponse<?> updateUser(@Valid @RequestBody UserUpdateDTO updateDTO) {
        User updatedUser = userService.updateUser(updateDTO); // 假设有updateUser方法
        return ApiResponse.success("用户信息更新成功", updatedUser);
    }

    /**
     * 用户退出
     *
     * @param request HttpServletRequest
     * @return 退出结果
     */
    @PostMapping("/logout")
    public ApiResponse<?> logout(HttpServletRequest request) {
        // 使当前会话失效
        request.getSession().invalidate();
        return ApiResponse.success("退出成功");
    }
}
