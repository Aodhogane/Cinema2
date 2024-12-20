package com.example.SinemaContract.controllers.domeinController;

import com.example.SinemaContract.controllers.BaseController;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

public interface FilmController extends BaseController {
    @RequestMapping("/top-films")
    String getTop5FilmsBySales(Model model);
}