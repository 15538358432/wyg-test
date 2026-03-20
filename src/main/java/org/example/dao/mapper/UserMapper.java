package org.example.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.example.entity.User;

import java.util.List;

@Mapper
public interface UserMapper {

    int insert(User user);

    int update(User user);

    int deleteById(@Param("id") Long id);

    User selectById(@Param("id") Long id);

    List<User> selectAll();
}
