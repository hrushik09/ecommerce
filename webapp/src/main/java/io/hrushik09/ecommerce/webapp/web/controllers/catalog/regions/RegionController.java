package io.hrushik09.ecommerce.webapp.web.controllers.catalog.regions;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
class RegionController {
    @GetMapping("/catalog/countries/{countryCode}/regions/create")
    public String createRegionPage(@PathVariable String countryCode, Model model) {
        model.addAttribute("countryCode", countryCode);
        return "catalog/regions/create_region";
    }
}
