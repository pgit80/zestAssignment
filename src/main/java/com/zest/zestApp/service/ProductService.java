package com.zest.zestApp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zest.zestApp.dao.ItemDao;
import com.zest.zestApp.dao.ProductDao;
import com.zest.zestApp.dto.ItemDto;
import com.zest.zestApp.dto.ProductDto;
import com.zest.zestApp.entity.ItemEntity;
import com.zest.zestApp.entity.ProductEntity;

@Service
public class ProductService {

	private final ProductDao productDao;

	public ProductService(ProductDao productDao) {
		this.productDao = productDao;
	}

	@Autowired
	private ItemDao itemDao;

	@Autowired
	private ItemService itemService;

	public ProductDto addProduct(ProductDto dto) {
		ProductEntity entity = convertProductBeanToEntity(dto);
		entity.setCreatedOn(LocalDateTime.now());

		ProductEntity savedEntity = productDao.save(entity);
		return convertProductEntityToBean(savedEntity);
	}

	public List<ProductDto> getAllProducts() {
		List<ProductEntity> res = productDao.findAll();
		List<ProductDto> resDtos = new ArrayList<>();
		for (ProductEntity en : res) {
			resDtos.add(convertProductEntityToBean(en));
		}
		return resDtos;
	}

	public ProductDto getProductById(Integer id) {
		return convertProductEntityToBean(productDao.getById(id));
	}

	public ProductDto updateProduct(Integer id, ProductDto dto) {
		ProductEntity toUpdate = productDao.findById(id)
				.orElseThrow(() -> new RuntimeException("Product with given Id not found: " + id));

		toUpdate.setProductName(dto.getProductName());
		toUpdate.setModifiedBy(dto.getModifiedBy());
		toUpdate.setModifiedOn(LocalDateTime.now());

		return convertProductEntityToBean(productDao.save(toUpdate));
	}

	public void deleteProduct(Integer id) {
		ProductEntity product = productDao.findById(id)
				.orElseThrow(() -> new RuntimeException("Product with given Id not found: " + id));

		productDao.delete(product);
	}

	public List<ItemDto> getItemsByProductId(Integer productId) {
		if (!productDao.existsById(productId)) {
			throw new RuntimeException("Product with given Id not found: " + productId);
		}
		List<ItemEntity> items = itemDao.findByProductId(productId);

		// 3. Convert entities to DTOs
		List<ItemDto> itemDtos = new ArrayList<>();
		for (ItemEntity item : items) {
			itemDtos.add(convertItemEntityToBean(item));
		}
		return itemDtos;
	}

	// utility
	public ProductEntity convertProductBeanToEntity(ProductDto dto) {
		ProductEntity entity = new ProductEntity();
		BeanUtils.copyProperties(dto, entity);
		return entity;
	}

	public ProductDto convertProductEntityToBean(ProductEntity entity) {
		ProductDto dto = new ProductDto();
		BeanUtils.copyProperties(entity, dto);
		return dto;
	}

	private ItemDto convertItemEntityToBean(ItemEntity itemEntity) {
		if (itemEntity == null) {
			return null;
		}
		ItemDto itemDto = new ItemDto();
		itemDto.setId(itemEntity.getId());
		itemDto.setQuantity(itemEntity.getQuantity());
		if (itemEntity.getProduct() != null) {
			itemDto.setProductId(itemEntity.getProduct().getId());
		}
		return itemDto;
	}
}