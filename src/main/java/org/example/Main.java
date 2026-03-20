package org.example;

import com.sun.net.httpserver.HttpServer;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.example.controller.UserController;
import org.example.service.UserService;
import org.example.service.impl.UserServiceImpl;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;

public class Main {
    public static void main(String[] args) throws IOException {
        SqlSessionFactory sqlSessionFactory = createSqlSessionFactory();
        
        SqlSession sqlSession = sqlSessionFactory.openSession(true);
        UserService userService = new UserServiceImpl(sqlSession);
        UserController userController = new UserController(userService);

        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
        server.createContext("/users", userController);
        server.setExecutor(null);
        server.start();
        
        System.out.println("服务器已启动，访问 http://localhost:8080");
        System.out.println("用户管理接口：");
        System.out.println("  GET    /users      - 获取所有用户");
        System.out.println("  GET    /users/{id} - 根据ID获取用户");
        System.out.println("  POST   /users      - 创建用户");
        System.out.println("  PUT    /users/{id} - 更新用户");
        System.out.println("  DELETE /users/{id} - 删除用户");
    }

    private static SqlSessionFactory createSqlSessionFactory() throws IOException {
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        return new SqlSessionFactoryBuilder().build(inputStream);
    }
}
