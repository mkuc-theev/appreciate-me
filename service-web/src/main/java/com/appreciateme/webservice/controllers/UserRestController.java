package com.appreciateme.webservice.controllers;

import com.appreciateme.usersservice.model.User;
import com.appreciateme.webservice.model.CreateUserFormData;
import com.appreciateme.webservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserRestController {
    @Autowired
    UserService userService;

    @ModelAttribute("users")
    public List<User> populateUsers()
            throws URISyntaxException, IOException, InterruptedException {
        return userService.getAllUsers();
    }

    @GetMapping(value = "/")
    public String users(Model model) {
        return "users/users";
    }

    @GetMapping(value = "/deleteId")
    public String deleteUserFromList(@RequestParam(name = "id") String id)
            throws URISyntaxException, IOException, InterruptedException {
        userService.deleteUserFromList(id);
        return "redirect:/users/";
    }

    @GetMapping(value = "/create")
    public String showCreateUserForm(Model model) {
        model.addAttribute("formData", new CreateUserFormData());
        return "users/create";
    }

    @PostMapping("/create")
    public String doCreateUser(@Valid @ModelAttribute("formData") CreateUserFormData formData,
                               BindingResult bindingResult,
                               Model model)
            throws IOException, URISyntaxException, InterruptedException {
        if (bindingResult.hasErrors()) {
            return "users/create";
        }

        System.out.println(formData);
        userService.createUserFromForm(formData);

        return "redirect:/users/";
    }
}
