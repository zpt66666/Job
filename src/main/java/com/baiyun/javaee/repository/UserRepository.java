package com.baiyun.javaee.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baiyun.javaee.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户数据访问接口
 *
 * @author Craft
 */
@Repository
public interface UserRepository extends BaseMapper<User> {

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户对象
     */
    User findByUsername(@Param("username") String username);

    /**
     * 根据邮箱查询用户
     *
     * @param email 邮箱
     * @return 用户对象
     */
    User findByEmail(@Param("email") String email);
}
