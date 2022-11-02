package com.appreciateme.webservice.controllers;

import com.appreciateme.opinion.model.Opinion;
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

@Controller
@RequestMapping("/opinions")
public class OpinionRestController {

    @Autowired
    OpinionDataService opinionDataService;

    @Autowired
    UserDataService userDataService;

    @GetMapping("/create")
    public String showCreateOpinionForm(Model model, @RequestParam(required = false, name = "id") String id)
            throws URISyntaxException, IOException, InterruptedException {
        model.addAttribute("formData", new Opinion());
        model.addAttribute("users", userDataService.getAllUsers());
        model.addAttribute("selectedUserID", id);
        return "opinions/create";
    }

    @PostMapping("/create")
    public String doCreateOpinion(@Valid @ModelAttribute("formData") Opinion formData,
                                  BindingResult bindingResult,
                                  Model model)
            throws IOException, URISyntaxException, InterruptedException {
        if(bindingResult.hasErrors()) {
            return "opinions/create";
        }
        opinionDataService.createOpinionFromForm(formData);

        return "redirect:/users/user?id=" + formData.getReviewedUserID();
    }

}
