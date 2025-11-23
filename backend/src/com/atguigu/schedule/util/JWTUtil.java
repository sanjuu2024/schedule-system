package com.atguigu.schedule.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

/**
 * JWT 工具类
 * 用于生成和验证 JWT Token
 */
public class JWTUtil {
    // 密钥（生产环境应该从配置文件读取，且长度至少32字节）
    private static final String SECRET_KEY = "YourSecretKeyShouldBeLongEnoughAndSecuredForHS256Algorithm";
    
    // Token 有效期：7天（毫秒）
    private static final long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 7;
    
    // 生成签名密钥
    private static Key getSigningKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    /**
     * 生成 JWT Token
     * @param userId 用户ID
     * @param username 用户名
     * @return JWT Token 字符串
     */
    public static String generateToken(Integer userId, String username) {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + EXPIRATION_TIME);

        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject(username)              // 主题：用户名
                .claim("uid", userId)              // 自定义声明：用户ID
                .claim("username", username)       // 自定义声明：用户名
                .setIssuedAt(now)                  // 签发时间
                .setExpiration(expiration)         // 过期时间
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * 验证并解析 JWT Token
     * @param token JWT Token
     * @return Claims 对象（包含用户信息）
     * @throws JwtException 如果 token 无效或过期
     */
    public static Claims validateToken(String token) throws JwtException {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtException("Token 已过期");
        } catch (MalformedJwtException e) {
            throw new JwtException("Token 格式错误");
        } catch (SignatureException e) {
            throw new JwtException("Token 签名无效");
        } catch (Exception e) {
            throw new JwtException("Token 无效");
        }
    }

    /**
     * 从 Token 中获取用户ID
     * @param token JWT Token
     * @return 用户ID
     */
    public static Integer getUserIdFromToken(String token) {
        Claims claims = validateToken(token);
        return claims.get("uid", Integer.class);
    }

    /**
     * 从 Token 中获取用户名
     * @param token JWT Token
     * @return 用户名
     */
    public static String getUsernameFromToken(String token) {
        Claims claims = validateToken(token);
        return claims.get("username", String.class);
    }

    /**
     * 检查 Token 是否过期
     * @param token JWT Token
     * @return true-已过期，false-未过期
     */
    public static boolean isTokenExpired(String token) {
        try {
            Claims claims = validateToken(token);
            return claims.getExpiration().before(new Date());
        } catch (JwtException e) {
            return true;
        }
    }
}