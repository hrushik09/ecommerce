package io.hrushik09.ecommerce.webapp.web.controllers.catalog.countries;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
class CountryController {
    @GetMapping("/catalog/countries/create")
    String createCountryPage() {
        return "catalog/countries/create_country";
    }

    @GetMapping("/catalog/countries")
    String countriesPage(@RequestParam(name = "page", defaultValue = "1") int pageNo, Model model) {
        model.addAttribute("pageNo", pageNo);
        return "catalog/countries/countries";
    }
}
