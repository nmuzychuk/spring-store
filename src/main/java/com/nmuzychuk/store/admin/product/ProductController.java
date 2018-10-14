package com.nmuzychuk.store.admin.product;

import com.nmuzychuk.store.product.Product;
import com.nmuzychuk.store.product.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/products")
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping
    String showProductList(ModelMap modelMap) {
        modelMap.put("products", productRepository.findAll());
        return "admin/product/productList";
    }

    @GetMapping("/new")
    String showNewProduct(ModelMap modelMap) {
        modelMap.put("product", new Product());
        return "admin/product/newProduct";
    }

    @PostMapping
    String createNewProduct(@ModelAttribute("product") Product product, BindingResult result, ModelMap modelMap) {
        if (result.hasErrors()) {
            modelMap.put("product", product);
            return "admin/product/newProduct";
        } else {
            productRepository.save(product);
            return "redirect:/admin/products";
        }
    }
}
