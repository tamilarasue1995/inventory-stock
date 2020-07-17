package com.stock.inventory.stockinventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication
public class StockInventoryApplication {

	public static void main(String[] args) {
		SpringApplication.run(StockInventoryApplication.class, args);
	}

}
