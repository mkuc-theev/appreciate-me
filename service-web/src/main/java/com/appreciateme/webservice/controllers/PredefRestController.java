package com.appreciateme.webservice.controllers;

import com.appreciateme.predef.model.PredefMark;
import com.appreciateme.webservice.services.PredefDataService;
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
@RequestMapping("predefs")
public class PredefRestController {

    @Autowired
    PredefDataService predefDataService;

    @GetMapping("/")
    public String predefs(Model model)
            throws URISyntaxException, IOException, InterruptedException {
        model.addAttribute("predefs", predefDataService.getAllPredefs());
        return "predefs/predefs";
    }

    @GetMapping("/predef")
    public String predef(@RequestParam(name = "id") String id, Model model)
            throws URISyntaxException, IOException, InterruptedException {
        model.addAttribute("predef", predefDataService.getPredefById(id));
        return "predefs/predef";
    }

    @GetMapping("/deleteId")
    public String deletePredefFromList(@RequestParam(name = "id") String id) throws URISyntaxException, IOException, InterruptedException {
        predefDataService.deletePredefById(id);
        return "redirect:/predefs/";
    }

    @GetMapping("/create")
    public String showCreatePredefForm(Model model) {
        model.addAttribute("formData", new PredefMark());
        return "predefs/create";
    }

    @PostMapping("/create")
    public String doCreatePredef(@Valid @ModelAttribute("formData") PredefMark formData,
                                 BindingResult bindingResult,
                                 Model model)
            throws URISyntaxException, IOException, InterruptedException {
        if (bindingResult.hasErrors()) {
            return "tags/create";
        }

        predefDataService.createPredefFromForm(formData);

        return "redirect:/predefs/";
    }
}