package com.geekbrains.spring.web.order.controllers;

import com.geekbrains.spring.web.lib.dto.ProfileDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
public class ProfileController {
    @GetMapping
    public ProfileDto getCurrentUserInfo(@RequestHeader String username) {
        return new ProfileDto(username);
    }
}
