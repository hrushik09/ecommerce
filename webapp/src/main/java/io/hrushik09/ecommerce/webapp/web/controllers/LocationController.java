package io.hrushik09.ecommerce.webapp.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
class LocationController {
    @GetMapping("/inventory/locations")
    String locationsPage(@RequestParam(name = "page", defaultValue = "1") int pageNo, Model model) {
        model.addAttribute("pageNo", pageNo);
        return "inventory/locations";
    }

    @GetMapping("/inventory/locations/create")
    String createLocationPage() {
        return "inventory/create_location";
    }

    @GetMapping("inventory/locations/{code}")
    String locationPage(@PathVariable String code, Model model) {
        model.addAttribute("code", code);
        return "inventory/location";
    }
}
