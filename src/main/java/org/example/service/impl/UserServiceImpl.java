package org.example.service.impl;

import org.apache.ibatis.session.SqlSession;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.example.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private final SqlSession sqlSession;

    public UserServiceImpl(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    private UserMapper getUserMapper() {
        return sqlSession.getMapper(UserMapper.class);
    }

    @Override
    public Long createUser(User user) {
        int rows = getUserMapper().insert(user);
        if (rows > 0) {
            return user.getId();
        }
        return null;
    }

    @Override
    public boolean updateUser(User user) {
        return getUserMapper().update(user) > 0;
    }

    @Override
    public boolean deleteUser(Long id) {
        return getUserMapper().deleteById(id) > 0;
    }

    @Override
    public User getUserById(Long id) {
        return getUserMapper().selectById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return getUserMapper().selectAll();
    }
}
