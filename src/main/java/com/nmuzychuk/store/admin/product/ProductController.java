package com.nmuzychuk.store.admin.product;

import com.nmuzychuk.store.product.Product;
import com.nmuzychuk.store.product.ProductRepository;
import com.nmuzychuk.store.system.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

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
    String createNewProduct(@ModelAttribute("product") Product product, BindingResult result, ModelMap modelMap,
                            RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            modelMap.put("product", product);
            return "admin/product/newProduct";
        } else {
            productRepository.save(product);
            redirectAttributes.addFlashAttribute("message", "Product was created");
            return "redirect:/admin/products";
        }
    }

    @GetMapping("/{id}/edit")
    String editProduct(@PathVariable("id") int id, ModelMap modelMap, HttpServletResponse response) {
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            modelMap.put("product", product.get());
            return "admin/product/editProduct";
        } else {
            throw new ResourceNotFoundException();
        }
    }

    @PutMapping("/{id}")
    String updateProduct(@PathVariable("id") int id, @ModelAttribute("product") Product product, BindingResult result,
                         ModelMap modelMap, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            modelMap.put("product", product);
            return "admin/product/editProduct";
        } else {
            productRepository.save(product);
            redirectAttributes.addFlashAttribute("message", "Product was updated");
            return "redirect:/admin/products";
        }
    }

    @DeleteMapping("/{id}")
    String deleteProduct(@PathVariable("id") int id, RedirectAttributes redirectAttributes) {
        productRepository.deleteById(id);
        redirectAttributes.addFlashAttribute("message", "Product was destroyed");
        return "redirect:/admin/products";
    }
}
