package com.github.luwan.spring.boot.learning.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hechao
 * @date 2020/8/26
 */
@RestController
public class DemoController {

    @RequestMapping("/demo/getPort")
    public Object demo() {
        return "this is service 1";
    }

}
