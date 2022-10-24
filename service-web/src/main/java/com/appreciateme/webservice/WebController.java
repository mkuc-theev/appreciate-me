package com.appreciateme.webservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.URISyntaxException;

@Controller
public class WebController {

    @Autowired
    WebService webService;
    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }

    @GetMapping(value = "/users")
    public String users(Model model) throws URISyntaxException, IOException, InterruptedException {
        model.addAttribute("users", webService.getAllUsers());
        return "/users";
    }
}
