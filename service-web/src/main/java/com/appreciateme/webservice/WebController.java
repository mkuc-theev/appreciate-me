package com.appreciateme.webservice;

import com.appreciateme.usersservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Controller
public class WebController {
    @Autowired
    WebService webService;

    @ModelAttribute("users")
    public List<User> populateUsers() throws URISyntaxException, IOException, InterruptedException {
        return webService.getAllUsers();
    }

    @RequestMapping(value = "/index")
    public String index() {
        return "index";
    }

    @GetMapping(value = "/users")
    public String users(Model model) {
        return "/users";
    }
}
