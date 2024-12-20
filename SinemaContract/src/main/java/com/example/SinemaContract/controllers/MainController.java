package com.example.SinemaContract.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public interface MainController extends BaseController {

    @GetMapping
    public String mainPage(Model model);
}