package com.zest.zestApp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zest.zestApp.dto.ItemDto;
import com.zest.zestApp.service.ItemService;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
public class ItemController {

	private final ItemService itemService;

	public ItemController(ItemService itemService) {
		this.itemService = itemService;
	}

	@PostMapping("/addItems")
	public ResponseEntity<ItemDto> createItem(@RequestBody ItemDto dto) {
		ItemDto createdItem = itemService.createItem(dto);
		return new ResponseEntity<>(createdItem, HttpStatus.CREATED);
	}

	@PutMapping("/items/{id}")
	public ResponseEntity<ItemDto> updateItem(@PathVariable("id") Integer id, @RequestBody ItemDto dto) {
		ItemDto updatedItem = itemService.updateItem(id, dto);
		return ResponseEntity.ok(updatedItem);
	}

	@DeleteMapping("/items/{id}")
	public ResponseEntity<String> deleteItem(@PathVariable Integer id) {
		itemService.deleteItem(id);
		return new ResponseEntity<String>("Deleted Item with id " + id, HttpStatus.NO_CONTENT);
	}
}