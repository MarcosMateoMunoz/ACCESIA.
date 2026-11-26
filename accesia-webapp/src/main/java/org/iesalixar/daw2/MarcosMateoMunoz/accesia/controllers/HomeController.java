package org.iesalixar.daw2.MarcosMateoMunoz.accesia.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "redirect:/establecimientos";
    }
}
