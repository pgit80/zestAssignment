package com.zest.zestApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.zest.zestApp.dao.ProductDao;
import com.zest.zestApp.dto.ProductDto;
import com.zest.zestApp.entity.ProductEntity;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

	@Mock
	private ProductDao productDao;

	@InjectMocks
	private ProductService productService;

	private ProductEntity productEntity;
	private ProductDto productDto;

	@BeforeEach
	void setUp() {
		productEntity = new ProductEntity();
		productEntity.setId(1);
		productEntity.setProductName("Test Laptop");
		productEntity.setCreatedBy("Tester");

		productDto = new ProductDto();
		productDto.setId(1);
		productDto.setProductName("Test Laptop");
		productDto.setCreatedBy("Tester");
	}

	@Test
	void getAllProducts() {
		when(productDao.findAll()).thenReturn(List.of(productEntity));

		List<ProductDto> result = productService.getAllProducts();

		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals("Test Laptop", result.get(0).getProductName());
		verify(productDao, times(1)).findAll();
	}

	@Test
	void getProductById() {
		when(productDao.findById(1)).thenReturn(Optional.of(productEntity));

		ProductDto result = productService.getProductById(1);

		assertNotNull(result);
		assertEquals(1, result.getId());
		assertEquals("Test Laptop", result.getProductName());
		verify(productDao, times(1)).findById(1);
	}

	@Test
	void saveProduct() {
		when(productDao.save(any(ProductEntity.class))).thenReturn(productEntity);

		ProductDto result = productService.addProduct(productDto);

		assertNotNull(result);
		assertEquals("Test Laptop", result.getProductName());
		verify(productDao, times(1)).save(any(ProductEntity.class));
	}
}