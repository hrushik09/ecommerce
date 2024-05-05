package io.hrushik09.ecommerce.webapp.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inventory/locations")
class LocationController {
    @GetMapping
    String locationsPage() {
        return "inventory/locations";
    }
}
