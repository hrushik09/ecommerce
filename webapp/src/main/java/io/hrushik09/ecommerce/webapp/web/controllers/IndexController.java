package io.hrushik09.ecommerce.webapp.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class IndexController {
    @GetMapping
    String index() {
        return "redirect:/catalog/countries";
    }
}
