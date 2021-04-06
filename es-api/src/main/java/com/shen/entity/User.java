package com.shen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author chensihua
 * @version 1.0.0
 * @ClassName User.java
 * @email theoshen@foxmail.com
 * @Description TODO
 * @createTime 2021年04月06日 14:14:00
 */
@Component
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User {
    private String name;
    private int age;

}
