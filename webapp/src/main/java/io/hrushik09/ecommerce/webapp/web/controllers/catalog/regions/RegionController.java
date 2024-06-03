package io.hrushik09.ecommerce.webapp.web.controllers.catalog.regions;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
class RegionController {
    @GetMapping("/catalog/countries/{countryCode}/regions/create")
    public String createRegionPage(@PathVariable String countryCode, Model model) {
        model.addAttribute("countryCode", countryCode);
        return "catalog/regions/create_region";
    }

    @GetMapping("/catalog/regions/{code}")
    public String regionPage(@PathVariable String code, @RequestParam(name = "page", defaultValue = "1") int pageNo, Model model) {
        model.addAttribute("code", code);
        model.addAttribute("pageNo", pageNo);
        return "catalog/regions/region";
    }
}
