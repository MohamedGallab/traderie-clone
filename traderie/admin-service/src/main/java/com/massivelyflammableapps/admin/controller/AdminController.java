package com.massivelyflammableapps.admin.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/controller")
public class AdminController {
    @GetMapping
    public void getAllOffers() {
        System.out.println("Hello from controller!");
    }
}
