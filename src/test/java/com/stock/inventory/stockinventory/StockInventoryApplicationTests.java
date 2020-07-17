package com.stock.inventory.stockinventory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.inventory.stockinventory.dao.StockInventoryDao;
import com.stock.inventory.stockinventory.dto.InventoryStock;


@SpringBootTest
@AutoConfigureMockMvc
class StockInventoryApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private StockInventoryDao stockInventoryDao;

	@Test
	void testCreate() throws JsonProcessingException, Exception {
		InventoryStock stock = new InventoryStock();
		stock.setStockNumber(1200L);
		stock.setStockName("Laptop");
		stock.setPurchaseDate(LocalDate.now());
		stock.setPurchasePrice(10000L);
		stock.setQuantity(100L);
		MvcResult responseText = mockMvc.perform(post("/stock/inventory/create")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(stock)))
				.andExpect(status().isOk())
				.andReturn();
		assertEquals(responseText.getResponse().getContentAsString(), "Success");
	}

	@Test
	void testgetAllRows() throws JsonProcessingException, Exception {
		MvcResult responseText = mockMvc.perform(post("/")
				.contentType("application/json")
				.content(""))
				.andExpect(status().isOk())
				.andReturn();
		List<InventoryStock> expectedResult = stockInventoryDao.findAll(Sort.by(Sort.Direction.ASC,"stockNumber")).stream().filter(predicate -> predicate.getQuantity() !=0L).collect(Collectors.toList());
		assertEquals(responseText.getResponse().getContentAsString(), objectMapper.writeValueAsString(expectedResult));
	}

	@Test
	void checkStockNumber() throws JsonProcessingException, Exception {
		Map<String, Object> valueMap = new HashMap<>();
		valueMap.put("columnName", 12);
		MvcResult responseText = mockMvc.perform(post("/stock/inventory/checkStock")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(valueMap)))
				.andExpect(status().isOk())
				.andReturn();
		boolean expectedResult = stockInventoryDao.existsById(12L);
		assertEquals(responseText.getResponse().getContentAsString(), objectMapper.writeValueAsString(expectedResult));
	}
	
	@Test
	void testCreateException() throws JsonProcessingException, Exception {
		InventoryStock stock = new InventoryStock();
		stock.setStockName("Laptop");
		stock.setPurchaseDate(LocalDate.now());
		stock.setPurchasePrice(10000L);
		stock.setQuantity(100L);
		MvcResult responseText = mockMvc.perform(post("/stock/inventory/create")
				.contentType("application/json")
				.content(objectMapper.writeValueAsString(stock)))
				.andExpect(status().isOk())
				.andReturn();
		assertEquals(responseText.getResponse().getContentAsString(), "Success");
	}
	
}
