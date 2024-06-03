package io.hrushik09.ecommerce.webapp.web.controllers.inventory.warehouses;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
class WarehouseController {
    @GetMapping("inventory/locations/{locationCode}/warehouses/create")
    String createWarehousePage(@PathVariable String locationCode, Model model) {
        model.addAttribute("locationCode", locationCode);
        return "inventory/warehouses/create_warehouse";
    }

    @GetMapping("/inventory/warehouses/{code}")
    String warehousePage(@PathVariable String code, @RequestParam(name = "page", defaultValue = "1") int pageNo, Model model) {
        model.addAttribute("code", code);
        model.addAttribute("pageNo", pageNo);
        return "inventory/warehouses/warehouse";
    }
}
