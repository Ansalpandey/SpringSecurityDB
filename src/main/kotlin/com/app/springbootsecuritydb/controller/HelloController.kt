package com.app.springbootsecuritydb.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class HelloController {
    @GetMapping("/")
    fun hello(): String {
        return "Hello World"
    }

    @GetMapping("/admin")
    fun admin(): String {
        return "Hello Admin"
    }
}