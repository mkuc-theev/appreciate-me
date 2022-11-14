package com.appreciateme.webservice.controllers;

import com.appreciateme.tag.model.Tag;
import com.appreciateme.webservice.services.TagDataService;
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
@RequestMapping("/tags")
public class TagRestController {

    @Autowired
    TagDataService tagDataService;

    @GetMapping("/")
    public String tags(Model model)
            throws IOException, URISyntaxException, InterruptedException {
        model.addAttribute("tags", tagDataService.getAllTags());
        return "tags/tags";
    }

    @GetMapping("/deleteId")
    public String deleteTagFromList(@RequestParam(name = "id") String id)
            throws URISyntaxException, IOException, InterruptedException {
        tagDataService.removeTag(id);
        return "redirect:/tags/";
    }

    @GetMapping("/create")
    public String showCreateTagForm(Model model) {
        model.addAttribute("formData", new Tag());
        return "tags/create";
    }

    @PostMapping("/create")
    public String doCreateTag(@Valid @ModelAttribute("formData") Tag formData,
                              BindingResult bindingResult,
                              Model model)
            throws IOException, URISyntaxException, InterruptedException {
        if (bindingResult.hasErrors()) {
            return "tags/create";
        }
        tagDataService.createTagFromForm(formData);

        return "redirect:/tags/";
    }
}