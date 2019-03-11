package com.example.loginservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
final class LoginController {

    Map<String, Login> stubRepository = new HashMap<>();
    {
        stubRepository.put("Euler", new Login("Euler", "2.72"));
    }

    @PostMapping("/login")
    LoginResponse login(@RequestBody Optional<Login> request) {
        if(request.isPresent()) {
            Login login = request.get();
            Login actualLogin = stubRepository.get(login.getName());
            if (actualLogin != null && actualLogin.getPassword().equals(login.getPassword()))
                return new LoginResponse("1", "Login successful");
        }
        return new LoginResponse("-1", "Account not found");
    }

    @GetMapping("/")
    String greeting() {
        return "You are inside login service. Do post on /login to login";
    }
}
