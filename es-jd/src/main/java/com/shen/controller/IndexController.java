package com.shen.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author chensihua
 * @version 1.0.0
 * @ClassName IndexController.java
 * @email theoshen@foxmail.com
 * @Description TODO
 * @createTime 2021年04月06日 15:46:00
 */
@Controller
public class IndexController {

    @GetMapping({"/", "/index"})
    public String index() {
        return "index";
    }
}
