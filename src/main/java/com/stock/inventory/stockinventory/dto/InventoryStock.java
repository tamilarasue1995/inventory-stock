package com.stock.inventory.stockinventory.dto;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@Table(name = "inventory_stock")
@AllArgsConstructor
@NoArgsConstructor
public class InventoryStock implements Serializable {
private static final long serialVersionUID = -2343243243242432341L;

	@Id
	private Long stockNumber;
	
	@Column(name = "stock_name")
	private String stockName;
	
	@Column(name = "purchase_date")
	private LocalDate purchaseDate;

	@Column(name = "purchase_price")
	private Long purchasePrice;
	
	@Column(name = "selling_date")
	private LocalDate sellingDate;
	
	@Column(name = "selling_price")
	private Long sellingPrice;
	
	@Column(name = "quantity")
	private Long quantity;
	
}