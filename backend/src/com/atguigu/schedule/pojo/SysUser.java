package com.atguigu.schedule.pojo;

import lombok.*;

@AllArgsConstructor   // 添加全参构造
@NoArgsConstructor   // 添加无参构造
@Getter   // 为所有属性添加getter方法
@Setter   // 为所有属性添加setter方法
@EqualsAndHashCode   // 添加hashCode
@ToString   // 添加toString方法
// 或者最后那四个可以写成同一个@Data,即以上可以等效于：
// @AllArgsConstructor
// @NoArgsConstructor
// @Data   // Getter  Setter  EqualsAndHashCode  ToString
public class SysUser {
    private Integer uid;
    private String username;
    private String userPwd;
}
