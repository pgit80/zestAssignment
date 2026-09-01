package com.zest.zestApp.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zest.zestApp.dao.ProductDao;
import com.zest.zestApp.dto.ProductDto;
import com.zest.zestApp.entity.ProductEntity;
import com.zest.zestApp.security.JwtUtil;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class ProductControllerIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private JwtUtil jwtUtil;

	private final ObjectMapper objectMapper = new ObjectMapper();

	private String token;

	@BeforeEach
	void setUp() {
		productDao.deleteAll();

		ProductEntity product = new ProductEntity();
		product.setProductName("Gaming Laptop");
		product.setCreatedBy("Admin");
		productDao.save(product);

		token = "Bearer " + jwtUtil.generateAccessToken("testuser", "ROLE_USER");
	}

	@Test
	void createProduct() throws Exception {
		ProductDto dto = new ProductDto();
		dto.setProductName("Smart Monitor");
		dto.setCreatedBy("Admin");

		mockMvc.perform(post("/api/v1/addProduct").header("Authorization", token).contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.productName").value("Smart Monitor"));
	}

	@Test
	void getAllProducts() throws Exception {
		ProductEntity entity = new ProductEntity();
		entity.setProductName("Mechanical Keyboard");
		entity.setCreatedBy("Admin");
		productDao.save(entity);

		mockMvc.perform(get("/api/v1/products").header("Authorization", token)).andExpect(status().isOk())
				.andExpect(jsonPath("$[0].productName").value("Gaming Laptop"));
	}

	@Test
	void getProducts() throws Exception {
		mockMvc.perform(get("/api/v1/products")).andExpect(status().isForbidden());
	}
}