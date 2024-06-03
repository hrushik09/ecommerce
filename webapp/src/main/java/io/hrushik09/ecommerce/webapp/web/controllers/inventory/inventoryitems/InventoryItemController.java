package io.hrushik09.ecommerce.webapp.web.controllers.inventory.inventoryitems;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
class InventoryItemController {
    @GetMapping("/inventory/warehouses/{warehouseCode}/items/create")
    String createInventoryItemPage(@PathVariable String warehouseCode, Model model) {
        model.addAttribute("warehouseCode", warehouseCode);
        return "inventory/inventoryitems/create_inventoryitem";
    }
}
