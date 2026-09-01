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

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
@Tag(name = "Product Management", description = "Endpoints for Product CRUD")
public class ProductController {

	@Autowired
	private ProductService service;

	@Operation(summary = "Get all products", description = "Retrieves a list of all products along with nested items.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Successfully retrieved list"),
			@ApiResponse(responseCode = "401", description = "Unauthorized - Valid JWT token required") })
	@GetMapping("/products")
	public ResponseEntity<List<ProductDto>> getAllProducts() {
		return new ResponseEntity<List<ProductDto>>(service.getAllProducts(), HttpStatus.OK);
	}

	@GetMapping("/products/{id}")
	public ResponseEntity<ProductDto> getProductById(@PathVariable Integer id) {
		return new ResponseEntity<ProductDto>(service.getProductById(id), HttpStatus.OK);
	}

	@Operation(summary = "Create a product", description = "Saves a new product to the system.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Product created successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid payload") })
	@PostMapping("/addProducts")
	public ResponseEntity<ProductDto> saveProduct(@RequestBody ProductDto dto) {
		ProductDto addedDto = service.addProduct(dto);
		return new ResponseEntity<ProductDto>(addedDto, HttpStatus.CREATED);
	}

	@Operation(summary = "Update product by Id", description = "Update an existing product.")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "Product updated successfully"),
			@ApiResponse(responseCode = "400", description = "Invalid payload") })
	@PutMapping("/products/{id}")
	public ResponseEntity<String> updateProductById(@RequestBody ProductDto dto, @PathVariable Integer id) {
		service.updateProduct(id, dto);
		return new ResponseEntity<String>("Updated Product with id " + id, HttpStatus.OK);
	}

	@DeleteMapping("/products/{id}")
	public ResponseEntity<String> deleteProductById(@PathVariable Integer id) {
		service.deleteProduct(id);
		return new ResponseEntity<String>("Deleted Product with id " + id, HttpStatus.OK);
	}

	@GetMapping("/products/{id}/items")
	public ResponseEntity<List<ItemDto>> getItemsByProductId(@PathVariable Integer id) {

		return new ResponseEntity<List<ItemDto>>(service.getItemsByProductId(id), HttpStatus.OK);
	}

}
