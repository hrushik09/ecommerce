package io.hrushik09.ecommerce.webapp.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
class WarehouseController {
    @GetMapping("inventory/locations/{locationCode}/warehouses/create")
    String createWarehousePage(@PathVariable String locationCode, Model model) {
        model.addAttribute("locationCode", locationCode);
        return "inventory/create_warehouse";
    }
}
