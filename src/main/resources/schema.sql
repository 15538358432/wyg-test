-- 创建数据库
CREATE DATABASE IF NOT EXISTS wyg_test DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE wyg_test;

-- 用户表
CREATE TABLE IF NOT EXISTS user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT COMMENT '用户ID',
    name VARCHAR(50) NOT NULL COMMENT '姓名',
    gender TINYINT NOT NULL DEFAULT 0 COMMENT '性别：0-未知，1-男，2-女',
    age INT NOT NULL DEFAULT 0 COMMENT '年龄',
    address VARCHAR(200) DEFAULT NULL COMMENT '家庭住址',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    update_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    deleted TINYINT NOT NULL DEFAULT 0 COMMENT '删除标记：0-未删除，1-已删除'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户信息表';

-- 插入测试数据
INSERT INTO user (name, gender, age, address) VALUES 
('张三', 1, 25, '北京市朝阳区xxx街道xxx号'),
('李四', 2, 30, '上海市浦东新区xxx路xxx号'),
('王五', 1, 28, '广州市天河区xxx大道xxx号');
