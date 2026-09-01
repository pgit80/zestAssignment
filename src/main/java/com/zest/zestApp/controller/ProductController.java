package com.zest.zestApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zest.zestApp.dto.ItemDto;
import com.zest.zestApp.dto.ProductDto;
import com.zest.zestApp.service.ProductService;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class ProductController {

	@Autowired
	private ProductService service;

	@GetMapping("/products")
	public ResponseEntity<List<ProductDto>> getAllProducts() {
		return new ResponseEntity<List<ProductDto>>(service.getAllProducts(), HttpStatus.OK);
	}

	@GetMapping("/products/{id}")
	public ResponseEntity<ProductDto> getProductById(@PathVariable Integer id) {
		return new ResponseEntity<ProductDto>(service.getProductById(id), HttpStatus.OK);
	}

	@PostMapping("/addProduct")
	public ResponseEntity<ProductDto> saveProduct(@RequestBody ProductDto dto) {
		ProductDto addedDto = service.addProduct(dto);
		return new ResponseEntity<ProductDto>(addedDto, HttpStatus.CREATED);
	}

	@PutMapping("/product/{id}")
	public ResponseEntity<String> updateProductById(@RequestBody ProductDto dto, @PathVariable Integer id) {
		service.updateProduct(id, dto);
		return new ResponseEntity<String>("Updated Product with id " + id, HttpStatus.OK);
	}

	@DeleteMapping("/product/{id}")
	public ResponseEntity<String> deleteProductById(@PathVariable Integer id) {
		service.deleteProduct(id);
		return new ResponseEntity<String>("Deleted Product with id " + id, HttpStatus.OK);
	}

	@GetMapping("/products/{id}/items")
	public ResponseEntity<List<ItemDto>> getItemsByProductId(@PathVariable Integer id) {

		return new ResponseEntity<List<ItemDto>>(service.getItemsByProductId(id), HttpStatus.OK);
	}

}
