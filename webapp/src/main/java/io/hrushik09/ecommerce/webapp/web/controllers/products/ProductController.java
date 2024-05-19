package io.hrushik09.ecommerce.webapp.web.controllers.products;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
class ProductController {
    @GetMapping("/inventory/products/create")
    String createProductPage() {
        return "inventory/products/create_product";
    }

    @GetMapping("/inventory/products")
    String productsPage(@RequestParam(name = "page", defaultValue = "1") int pageNo, Model model) {
        model.addAttribute("pageNo", pageNo);
        return "inventory/products/products";
    }

    @GetMapping("/inventory/products/{code}")
    String productPage(@PathVariable String code, Model model) {
        model.addAttribute("code", code);
        return "inventory/products/product";
    }
}
