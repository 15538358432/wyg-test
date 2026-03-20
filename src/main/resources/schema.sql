-- 用户表
CREATE TABLE IF NOT EXISTS t_user (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    gender TINYINT NOT NULL DEFAULT 0,
    age INT NOT NULL DEFAULT 0,
    address VARCHAR(200) DEFAULT NULL,
    create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    deleted TINYINT NOT NULL DEFAULT 0
);

-- 清空表数据（避免重复插入）
DELETE FROM t_user;

-- 插入测试数据
INSERT INTO t_user (name, gender, age, address) VALUES 
('张三', 1, 25, '北京市朝阳区xxx街道xxx号'),
('李四', 2, 30, '上海市浦东新区xxx路xxx号'),
('王五', 1, 28, '广州市天河区xxx大道xxx号');
