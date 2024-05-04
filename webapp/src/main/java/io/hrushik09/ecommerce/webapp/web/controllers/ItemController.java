package io.hrushik09.ecommerce.webapp.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
class ItemController {
    @GetMapping
    String index() {
        return "redirect:/items";
    }

    @GetMapping("/items")
    String itemsPage() {
        return "items";
    }
}
