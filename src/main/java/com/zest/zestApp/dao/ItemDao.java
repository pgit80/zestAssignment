package com.zest.zestApp.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zest.zestApp.entity.ItemEntity;

@Repository
public interface ItemDao extends JpaRepository<ItemEntity, Integer> {

	List<ItemEntity> findByProductId(Integer Id);
}
