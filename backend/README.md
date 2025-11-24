# ⚙️ 日程管理系统 - 后端

基于 Java Servlet + JDBC 构建的后端服务，提供 RESTful API 接口。



## 🎯 技术栈

- **核心框架**: Java Servlet 6.0
- **JDK 版本**: JDK 17+
- **数据库**: MySQL 8.0+
- **连接池**: Druid 1.2.x
- **JSON 处理**: Jackson 2.15.x
- **日志框架**: Logback 1.4.x
- **工具库**: Lombok 1.18.x
- **认证**: JWT (java-jwt 4.4.x)
- **加密**: Apache Commons Codec (MD5)
- **容器**: Tomcat 10.1.x



## 📦 项目结构

```
backend/
├── src/com/atguigu/schedule/
│   ├── common/                 # 公共模块
│   │   ├── Result.java         # 统一响应封装
│   │   ├── ResultCodeEnum.java # 业务状态码枚举
│   │   └── PageResult.java     # 分页数据封装
│   ├── controller/             # 控制层
│   │   ├── BaseController.java # 控制器基类
│   │   ├── SysUserController.java
│   │   └── SysScheduleController.java
│   ├── service/                # 业务层
│   │   ├── SysUserService.java
│   │   ├── SysScheduleService.java
│   │   └── impl/               # 实现类
│   ├── dao/                    # 数据访问层
│   │   ├── BaseDao.java        # DAO 基类
│   │   ├── SysUserDao.java
│   │   ├── SysScheduleDao.java
│   │   └── impl/               # 实现类
│   ├── pojo/                   # 实体类
│   │   ├── SysUser.java
│   │   └── SysSchedule.java
│   ├── filter/                 # 过滤器
│   │   ├── CorsFilter.java     # 跨域过滤器
│   │   ├── JWTAuthFilter.java  # JWT 认证过滤器
│   │   └── LoggingFilter.java  # 日志过滤器
│   ├── listener/               # 监听器
│   │   └── AppContextListener.java
│   ├── util/                   # 工具类
│   │   ├── JDBCUtil.java       # JDBC 工具
│   │   ├── JWTUtil.java        # JWT 工具
│   │   ├── MD5Util.java        # MD5 加密
│   │   └── JsonUtil.java       # JSON 工具
│   └── test/                   # 测试类
├── resources/
│   ├── db.properties           # 数据库配置
│   └── logback.xml             # 日志配置
├── web/
│   └── WEB-INF/
│       ├── web.xml             # Web 配置
│       └── lib/                # 依赖 JAR 包
└── backend.iml
```



## 🚀 快速开始

### 环境要求

- JDK 17 或更高版本
- Maven 3.6+ 或手动管理 JAR 包
- MySQL 8.0+
- Tomcat 10.1+
- IDEA (推荐) 或 Eclipse



### 1. 数据库初始化

执行以下 SQL 脚本：

```sql
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

create database if not exists schedule_system;
use schedule_system;
-- ----------------------------
-- 创建日程表
-- ----------------------------
DROP TABLE IF EXISTS `sys_schedule`;
CREATE TABLE `sys_schedule`  (
  `sid` int NOT NULL AUTO_INCREMENT,
  `uid` int NULL DEFAULT NULL,
  `title` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `completed` int(1) NULL DEFAULT NULL,
  PRIMARY KEY (`sid`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- 创建用户表
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user`  (
  `uid` int NOT NULL AUTO_INCREMENT,
  `username` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `user_pwd` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  PRIMARY KEY (`uid`) USING BTREE,
  UNIQUE INDEX `username`(`username`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = Dynamic;

-- ----------------------------
-- 插入用户数据
-- ----------------------------
INSERT INTO `sys_user` VALUES (1, 'zhangsan', 'e10adc3949ba59abbe56e057f20f883e');
INSERT INTO `sys_user` VALUES (2, 'lisi', 'e10adc3949ba59abbe56e057f20f883e');

SET FOREIGN_KEY_CHECKS = 1;
```

### 2. 配置数据库

修改 `resources/db.properties`：

```properties
driverClassName=com.mysql.cj.jdbc.Driver
url=jdbc:mysql://{your_database_ip}:{your_database_port}/schedule_system
username=your_username
password=your_password
initialSize=5
maxActive=10
maxWait=3000
```

### 3. 配置 Tomcat

#### IDEA 配置步骤：

1. **添加 Tomcat 服务器**
   - Run -> Edit Configurations  -> +  -> Tomcat Server  -> Local
   - Tomcat Home: 选择 Tomcat 安装目录
   - HTTP port: 8080

2. **部署项目**
   - Run -> Edit Configurations -> Deployment 标签页  -> +  -> Artifact  -> 修改项目访问路径为`/`
   
3. **启动服务**
   - 点击 Run/Debug 按钮启动 Tomcat

### 4. 依赖 JAR 包

将以下 JAR 包放入 `web/WEB-INF/lib/` 目录：

```
mysql-connector-j-8.0.33.jar
druid-1.2.18.jar
jackson-databind-2.15.2.jar
jackson-core-2.15.2.jar
jackson-annotations-2.15.2.jar
java-jwt-4.4.0.jar
lombok-1.18.28.jar
logback-classic-1.4.11.jar
logback-core-1.4.11.jar
slf4j-api-2.0.9.jar
commons-codec-1.15.jar
jakarta.servlet-api-6.0.0.jar
```



## 🔧 核心功能

### 1. 统一响应封装

```java
// 成功响应
Result.ok(data);
Result.ok("操作成功", data);

// 失败响应
Result.fail(ResultCodeEnum.USERNAME_EXIST);
Result.fail(ResultCodeEnum.SYSTEM_ERROR);
```

响应格式：

```json
{
  "code": 200,
  "message": "操作成功",
  "data": { ... },
  "timestamp": 1700000000000
}
```

### 2. 业务状态码

```java
// HTTP 标准码
SUCCESS(200, "操作成功")
BAD_REQUEST(400, "请求参数错误")
UNAUTHORIZED(401, "未授权")

// 用户模块 (1xxx)
USER_NOT_FOUND(1001, "用户不存在")
USERNAME_EXIST(1002, "用户名已存在")

// 日程模块 (2xxx)
SCHEDULE_NOT_FOUND(2001, "日程不存在")

// 认证模块 (3xxx)
TOKEN_INVALID(3002, "Token无效或已过期")
```

### 3. 分页查询

```java
// Controller
PageResult<SysSchedule> pageResult = PageResult.of(
    scheduleList,  // 当前页数据
    total,         // 总记录数
    page,          // 当前页码
    limit          // 每页条数
);
success(resp, "查询成功", pageResult);
```

响应格式：

```json
{
  "code": 200,
  "data": {
    "records": [...],
    "total": 100,
    "pageNum": 1,
    "pageSize": 10,
    "pages": 10
  }
}
```

### 4. JWT 认证

```java
// 生成 Token
String token = JWTUtil.createToken(uid);

// 验证 Token
Long uid = JWTUtil.parseToken(token);
```

### 5. 密码加密

```java
// MD5 加密
String encryptedPwd = MD5Util.encrypt(password);
```



## 📡 API 接口

### 用户接口

| 接口 | 方法 | 路径 | 说明 |
|------|------|------|------|
| 注册 | POST | `/v1/user/register` | 用户注册 |
| 登录 | POST | `/v1/user/login` | 用户登录 |
| 退出 | POST | `/v1/user/logout` | 用户退出 |
| 用户信息 | GET | `/v1/user/info` | 获取用户信息 |
| 用户名检查 | GET | `/v1/user/checkUsername/{username}` | 检查用户名是否存在 |

### 日程接口

| 接口 | 方法 | 路径 | 说明 |
|------|------|------|------|
| 列表查询 | GET | `/v1/schedule/list/{page}/{size}` | 分页查询日程 |
| 添加日程 | POST | `/v1/schedule/add` | 添加日程 |
| 更新日程 | PUT | `/v1/schedule/update` | 更新日程 |
| 删除日程 | DELETE | `/v1/schedule/delete/{id}` | 删除日程 |



## 🔐 安全机制

### 1. CORS 跨域

```java
@WebFilter(filterName = "CorsFilter", urlPatterns = "/*")
public class CorsFilter implements Filter {
    // 允许跨域请求
    resp.setHeader("Access-Control-Allow-Origin", "*");
    resp.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
}
```

### 2. JWT 认证

```java
@WebFilter(filterName = "JWTAuthFilter", urlPatterns = "/v1/*")
public class JWTAuthFilter implements Filter {
    // 校验 Token，将 uid 存入 request
    String token = req.getHeader("token");
    Long uid = JWTUtil.parseToken(token);
    req.setAttribute("uid", uid);
}
```

### 3. 日志记录

```java
@WebFilter(filterName = "LoggingFilter", urlPatterns = "/*")
public class LoggingFilter implements Filter {
    // 记录请求日志
    logger.info("请求: {} {}", method, uri);
}
```



## 🏗️ 三层架构

```
Controller (控制层)
    ↓ 调用
Service (业务层)
    ↓ 调用
DAO (数据访问层)
    ↓ 操作
Database (数据库)
```

### BaseDao 通用查询

```java
// 查询多行
List<T> executeQuery(Class<T> clazz, String sql, Object... params);

// 查询单行
T executeQuerySingle(Class<T> clazz, String sql, Object... params);

// 查询单值
T executeQueryScalar(Class<T> clazz, String sql, Object... params);

// 增删改
int executeUpdate(String sql, Object... params);
```



## 📊 日志配置

日志文件位置：`logs/schedule-system.log`

日志级别：
- `DEBUG`: 开发调试
- `INFO`: 一般信息
- `WARN`: 警告信息
- `ERROR`: 错误信息

## 🐛 常见问题

### 1. 数据库连接失败

检查：
- MySQL 服务是否启动
- `db.properties` 配置是否正确
- 端口是否被占用
- 用户名密码是否正确

### 2. Tomcat 启动失败

检查：
- JDK 版本是否为 17+
- Tomcat 版本是否为 10.1+
- 端口 8080 是否被占用
- 依赖 JAR 包是否完整

### 3. Token 验证失败

检查：
- 前端是否正确传递 Token
- Token 是否过期
- JWT 密钥是否一致

### 4. 跨域问题

确保 `CorsFilter` 已配置并生效。



## 📚 参考文档

- [Java Servlet 规范](https://jakarta.ee/specifications/servlet/)
- [Druid 文档](https://github.com/alibaba/druid/wiki)
- [Jackson 文档](https://github.com/FasterXML/jackson)
- [JWT 官网](https://jwt.io/)
- [Logback 文档](https://logback.qos.ch/)
