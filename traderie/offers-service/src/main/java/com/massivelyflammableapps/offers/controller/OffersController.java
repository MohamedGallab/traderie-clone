package com.massivelyflammableapps.offers.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("api/v1/offers")
public class OffersController {
    @GetMapping
    public String getMethodName(@RequestParam(required = false) String param) {
        return "yay you made it";
    }
}