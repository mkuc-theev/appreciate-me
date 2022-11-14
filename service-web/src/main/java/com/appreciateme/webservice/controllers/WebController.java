package com.appreciateme.webservice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping(value = "/index")
    public String index() {
        return "start";
    }

    @GetMapping
    public String redirect() {
        return "redirect:/index";
    }
}
