package com.example.loginservice;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
final class LoginController {

    Map<String, LoginInfo> stubRepository = new HashMap<>();
    {
        stubRepository.put("Euler", new LoginInfo("Euler", "2.27"));
    }

    @PostMapping("/login")
    LoginResponse login(@RequestBody Optional<LoginInfo> request) {

        return new LoginResponse("-1", "Account not found");
    }
}
