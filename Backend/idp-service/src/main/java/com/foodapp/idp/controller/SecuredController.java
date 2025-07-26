package com.foodapp.idp.controller;

import com.foodapp.idp.dto.GenericResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/secured")
public class SecuredController {

    @GetMapping("/")
    public String secured() {
        return "Welcome to secured Food App, Enjoy your orders! :)";
    }
}
