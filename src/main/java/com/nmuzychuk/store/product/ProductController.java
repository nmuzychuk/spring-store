package com.nmuzychuk.store.product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;

    @Autowired
    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping("")
    public @ResponseBody
    Iterable<Product> showProductList() {
        return productRepository.findAll();
    }

    @PostMapping("")
    public @ResponseBody
    Product addProduct(@RequestParam String name) {
        Product product = new Product();
        product.setName(name);
        product = productRepository.save(product);
        return product;
    }
}
