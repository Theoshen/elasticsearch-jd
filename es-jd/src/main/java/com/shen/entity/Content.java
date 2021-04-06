package com.shen.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author chensihua
 * @version 1.0.0
 * @ClassName UrlContent.java
 * @email theoshen@foxmail.com
 * @Description TODO
 * @createTime 2021年04月06日 16:21:00
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class Content {
    private String title;
    private String img;
    private String price;
}
