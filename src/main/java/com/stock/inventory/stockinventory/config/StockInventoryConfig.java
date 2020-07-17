package com.stock.inventory.stockinventory.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.stock.inventory.stockinventory.service.StockInventoryService;
import com.stock.inventory.stockinventory.service.StockInventoryServiceImpl;

@Configuration
public class StockInventoryConfig {
	
	@Bean
	public StockInventoryService stockInventoryService() {
		return new StockInventoryServiceImpl();
		
	}
}
