package com.lm359x.bank.infrastructure.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @GetMapping("login")
    public String login() {
        return "login";
    }

    @GetMapping("/")
    public String rofl(){
        return "rofl";
    }

}
