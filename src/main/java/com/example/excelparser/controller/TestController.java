package com.example.excelparser.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class TestController {
    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public String test() {
        return "Perfect! You did it ;)";
    }
}
