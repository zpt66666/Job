package com.baiyun.javaee.service;

import com.baiyun.javaee.model.User;

/**
 * 用户服务接口
 *
 * @author Craft
 */
public interface UserService {

    /**
     * 用户注册
     *
     * @param user 用户信息
     * @return 注册成功的用户信息
     * @throws com.baiyun.javaee.common.exception.BusinessException 如果用户名或邮箱已存在
     */
    User register(User user);

    /**
     * 用户登录
     *
     * @param usernameOrEmail 用户名或邮箱
     * @param password 密码
     * @return 登录成功的用户信息
     * @throws com.baiyun.javaee.common.exception.BusinessException 如果用户不存在或密码错误
     */
    User login(String usernameOrEmail, String password);

    /**
     * 检查用户名是否已存在
     *
     * @param username 用户名
     * @return true 如果用户名已存在，否则返回 false
     */
    boolean isUsernameExists(String username);

    /**
     * 检查邮箱是否已存在
     *
     * @param email 邮箱
     * @return true 如果邮箱已存在，否则返回 false
     */
    boolean isEmailExists(String email);

    /**
     * 获取当前登录用户信息
     *
     * @return 当前登录用户信息
     */
    User getCurrentUser();

    /**
     * 更新用户信息
     *
     * @param updateDTO 用户更新信息
     * @return 更新后的用户信息
     */
    User updateUser(com.baiyun.javaee.model.dto.UserUpdateDTO updateDTO);
}
