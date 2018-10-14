package com.nmuzychuk.store.admin.product;

import com.nmuzychuk.store.product.ProductRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public class ProductControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductRepository productRepository;

    @Test
    public void testShowProductList() throws Exception {
        mockMvc.perform(get("/admin/products"))
            .andExpect(status().isOk());
    }

    @Test
    public void testAddProduct() throws Exception {
        mockMvc.perform(post("/admin/products")
            .contentType(MediaType.APPLICATION_FORM_URLENCODED)
            .param("name", "foo"))
            .andExpect(status().isFound());
    }
}
