package org.example.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import org.example.entity.User;
import org.example.service.UserService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserController implements HttpHandler {

    private final UserService userService;
    private final ObjectMapper objectMapper;

    public UserController(UserService userService) {
        this.userService = userService;
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String response;
        int statusCode = 200;

        try {
            if ("GET".equals(method)) {
                if (path.equals("/users") || path.equals("/users/")) {
                    response = handleGetAllUsers();
                } else if (path.startsWith("/users/")) {
                    String idStr = path.substring("/users/".length());
                    response = handleGetUserById(idStr);
                    if (response == null) {
                        statusCode = 404;
                        response = "{\"error\":\"用户不存在\"}";
                    }
                } else {
                    statusCode = 404;
                    response = "{\"error\":\"接口不存在\"}";
                }
            } else if ("POST".equals(method) && (path.equals("/users") || path.equals("/users/"))) {
                response = handleCreateUser(exchange);
                statusCode = 201;
            } else if ("PUT".equals(method) && path.startsWith("/users/")) {
                String idStr = path.substring("/users/".length());
                response = handleUpdateUser(idStr, exchange);
                if (response == null) {
                    statusCode = 404;
                    response = "{\"error\":\"用户不存在\"}";
                }
            } else if ("DELETE".equals(method) && path.startsWith("/users/")) {
                String idStr = path.substring("/users/".length());
                response = handleDeleteUser(idStr);
            } else {
                statusCode = 404;
                response = "{\"error\":\"接口不存在\"}";
            }
        } catch (Exception e) {
            statusCode = 500;
            response = "{\"error\":\"" + e.getMessage() + "\"}";
        }

        byte[] responseBytes = response.getBytes(StandardCharsets.UTF_8);
        exchange.getResponseHeaders().set("Content-Type", "application/json;charset=UTF-8");
        exchange.sendResponseHeaders(statusCode, responseBytes.length);
        OutputStream os = exchange.getResponseBody();
        os.write(responseBytes);
        os.close();
    }

    private String handleGetAllUsers() throws Exception {
        List<User> users = userService.getAllUsers();
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "success");
        result.put("data", users);
        return objectMapper.writeValueAsString(result);
    }

    private String handleGetUserById(String idStr) throws Exception {
        Long id = Long.parseLong(idStr);
        User user = userService.getUserById(id);
        if (user == null) {
            return null;
        }
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "success");
        result.put("data", user);
        return objectMapper.writeValueAsString(result);
    }

    private String handleCreateUser(HttpExchange exchange) throws Exception {
        InputStream is = exchange.getRequestBody();
        String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        User user = objectMapper.readValue(body, User.class);
        Long id = userService.createUser(user);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "创建成功");
        result.put("data", id);
        return objectMapper.writeValueAsString(result);
    }

    private String handleUpdateUser(String idStr, HttpExchange exchange) throws Exception {
        Long id = Long.parseLong(idStr);
        User existingUser = userService.getUserById(id);
        if (existingUser == null) {
            return null;
        }
        InputStream is = exchange.getRequestBody();
        String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        User user = objectMapper.readValue(body, User.class);
        user.setId(id);
        userService.updateUser(user);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", "更新成功");
        return objectMapper.writeValueAsString(result);
    }

    private String handleDeleteUser(String idStr) throws Exception {
        Long id = Long.parseLong(idStr);
        boolean success = userService.deleteUser(id);
        Map<String, Object> result = new HashMap<>();
        result.put("code", 200);
        result.put("message", success ? "删除成功" : "删除失败");
        return objectMapper.writeValueAsString(result);
    }
}
