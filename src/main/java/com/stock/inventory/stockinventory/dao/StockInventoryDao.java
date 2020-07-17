package com.stock.inventory.stockinventory.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stock.inventory.stockinventory.dto.InventoryStock;

@Repository
public interface StockInventoryDao extends JpaRepository<InventoryStock, Long>{

}
