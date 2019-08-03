package com.mapper;

import com.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

public interface UserMapper {
    void insertUser(User user);

    User selectUserByName(String name);

    User selectUserById(int userId);
}
