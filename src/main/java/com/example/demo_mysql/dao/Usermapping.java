package com.example.demo_mysql.dao;

import com.example.demo_mysql.entity.User;
import org.apache.ibatis.annotations.*;


@Mapper
public interface  Usermapping {
    @Select("SELECT * FROM user WHERE id = #{id}")
    User findUserById(@Param("id") Integer id);

    @Select("select * from user where username = #{username}")
    User findUserByUsername(@Param("username") String username);

    @Insert("insert into user(username,encryptedPassword,createdAt,updatedAt) values(#{username},#{encryptedPassword},now(),now())")
    void save(@Param("username") String username,@Param("encryptedPassword") String encryptedPassword);

    @Update("update user set encryptedPassword = #{encryptedPassword} where username=#{username}")
    void update(@Param("encryptedPassword") String encryptedPassword,@Param("username") String username);
}

