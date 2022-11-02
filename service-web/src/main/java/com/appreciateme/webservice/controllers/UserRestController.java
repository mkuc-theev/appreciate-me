package com.appreciateme.webservice.controllers;

import com.appreciateme.opinion.model.Opinion;
import com.appreciateme.usersservice.model.User;
import com.appreciateme.web.model.UserData;
import com.appreciateme.webservice.services.OpinionDataService;
import com.appreciateme.webservice.services.UserDataService;
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
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserRestController {
    @Autowired
    UserDataService userDataService;

    @Autowired
    OpinionDataService opinionDataService;

    @GetMapping(value = "/")
    public String users(Model model)
            throws URISyntaxException, IOException, InterruptedException {
        model.addAttribute("users", userDataService.getAllUsers());
        return "users/users";
    }

    @GetMapping(value = "/deleteId")
    public String deleteUserFromList(@RequestParam(name = "id") String id)
            throws URISyntaxException, IOException, InterruptedException {
        userDataService.deleteUserFromList(id);
        return "redirect:/users/";
    }

    @GetMapping(value = "/create")
    public String showCreateUserForm(Model model) {
        model.addAttribute("formData", new User());
        return "users/create";
    }

    @PostMapping("/create")
    public String doCreateUser(@Valid @ModelAttribute("formData") User formData,
                               BindingResult bindingResult,
                               Model model)
            throws IOException, URISyntaxException, InterruptedException {
        if (bindingResult.hasErrors()) {
            return "users/create";
        }

        userDataService.createUserFromForm(formData);

        return "redirect:/users/";
    }

    @GetMapping("/user")
    public String viewUser(@RequestParam(name = "id") String id, Model model)
            throws URISyntaxException, IOException, InterruptedException {
        List<Opinion> opinionData = opinionDataService.getAllOpinionsForUser(id);
        List<User> reviewerData = new ArrayList<>();
        for (Opinion opinion : opinionData) {
            reviewerData.add(userDataService.getUserById(opinion.getOpinionUserID()));
        }
        model.addAttribute("userData",
                new UserData(userDataService.getUserById(id),
                        opinionData,
                        reviewerData));
        return "users/user";
    }
}
