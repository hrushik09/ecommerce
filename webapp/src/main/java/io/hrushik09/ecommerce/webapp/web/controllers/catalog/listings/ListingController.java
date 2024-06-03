package io.hrushik09.ecommerce.webapp.web.controllers.catalog.listings;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
class ListingController {
    @GetMapping("/catalog/regions/{regionCode}/listings/create")
    public String createListing(@PathVariable String regionCode, Model model) {
        model.addAttribute("regionCode", regionCode);
        return "catalog/listings/create_listing";
    }

    @GetMapping("/catalog/listings/{code}")
    public String getListingByCode(@PathVariable String code, Model model) {
        model.addAttribute("code", code);
        return "catalog/listings/listing";
    }
}
