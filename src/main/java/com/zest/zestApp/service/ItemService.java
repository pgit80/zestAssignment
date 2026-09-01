package com.zest.zestApp.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.zest.zestApp.dao.ItemDao;
import com.zest.zestApp.dao.ProductDao;
import com.zest.zestApp.dto.ItemDto;
import com.zest.zestApp.entity.ItemEntity;
import com.zest.zestApp.entity.ProductEntity;

@Service
public class ItemService {

	private final ItemDao itemDao;
	private final ProductDao productDao;

	public ItemService(ItemDao itemDao, ProductDao productDao) {
		this.itemDao = itemDao;
		this.productDao = productDao;
	}

	public ItemDto createItem(ItemDto dto) {
		ProductEntity product = productDao.findById(dto.getProductId())
				.orElseThrow(() -> new RuntimeException("Product with given Id not found: " + dto.getProductId()));

		ItemEntity entity = convertItemBeanToEntity(dto);
		entity.setProduct(product);

		ItemEntity savedEntity = itemDao.save(entity);
		return convertItemEntityToBean(savedEntity);
	}

	public List<ItemDto> getItemsByProductId(Integer productId) {
		if (!productDao.existsById(productId)) {
			throw new RuntimeException("Product with given Id not found: " + productId);
		}

		List<ItemEntity> items = itemDao.findByProductId(productId);
		List<ItemDto> itemDtos = new ArrayList<>();
		for (ItemEntity item : items) {
			itemDtos.add(convertItemEntityToBean(item));
		}
		return itemDtos;
	}

	public ItemDto updateItem(Integer id, ItemDto dto) {
		ItemEntity toUpdate = itemDao.findById(id)
				.orElseThrow(() -> new RuntimeException("Item with given Id not found: " + id));

		if (dto.getProductId() != null) {
			ProductEntity product = productDao.findById(dto.getProductId())
					.orElseThrow(() -> new RuntimeException("Product with given Id not found: " + dto.getProductId()));
			toUpdate.setProduct(product);
		}

		toUpdate.setQuantity(dto.getQuantity());

		return convertItemEntityToBean(itemDao.save(toUpdate));
	}

	public void deleteItem(Integer id) {
		ItemEntity item = itemDao.findById(id)
				.orElseThrow(() -> new RuntimeException("Item with given Id not found: " + id));

		itemDao.delete(item);
	}

	// Utility methods
	public ItemEntity convertItemBeanToEntity(ItemDto dto) {
		ItemEntity entity = new ItemEntity();
		BeanUtils.copyProperties(dto, entity);
		return entity;
	}

	public ItemDto convertItemEntityToBean(ItemEntity entity) {
		ItemDto dto = new ItemDto();
		BeanUtils.copyProperties(entity, dto);
		if (entity.getProduct() != null) {
			dto.setProductId(entity.getProduct().getId());
		}
		return dto;
	}
}