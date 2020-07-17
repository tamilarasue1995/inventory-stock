package com.stock.inventory.stockinventory;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.tomcat.util.json.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.inventory.stockinventory.dao.StockInventoryDao;
import com.stock.inventory.stockinventory.dto.InventoryStock;
import com.stock.inventory.stockinventory.service.StockInventoryService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8080")
public class StockInventoryController {

	@Autowired
	StockInventoryService stockInventoryService;

	@Autowired
	StockInventoryDao stockInventoryDao;

	@RequestMapping("/stock/inventory/portal")
	@ResponseBody
	public ModelAndView portal() {
		ModelAndView view = new ModelAndView();
		view.setViewName("portal");
		return view;
	}

	@RequestMapping("/stock/inventory/manage")
	@ResponseBody
	public ModelAndView manage(@RequestParam(required = false) String stockNumber) throws NumberFormatException, JsonProcessingException, IllegalArgumentException {
		ModelAndView view = new ModelAndView();
		ModelMap modelMap = view.getModelMap();
		ObjectMapper mapper = new ObjectMapper();
		if(!StringUtils.isEmpty(stockNumber)) {
			InventoryStock stock = new InventoryStock();
			stock.setStockNumber(Long.parseLong(stockNumber));
			modelMap.addAttribute("mode", true);
			modelMap.addAttribute("data", mapper.writeValueAsString(mapper.convertValue(stockInventoryDao.findById(Long.parseLong(stockNumber)).get(), Map.class)));
		} else {			
			modelMap.addAttribute("mode", false);
		}
		view.setViewName("stock-portal");
		return view;
	}

	@PostMapping("/stock/inventory/create")
	@ResponseBody
	public String create(@RequestBody Map<String,Object> param) throws ParseException, java.text.ParseException {
		LocalDate.parse(String.valueOf(param.get("purchaseDate")));
		InventoryStock dto = new InventoryStock(
				Long.valueOf(String.valueOf(param.get("stockNumber"))),
				String.valueOf(param.get("stockName")), 
				Objects.nonNull(param.get("purchaseDate")) ? LocalDate.parse(String.valueOf(param.get("purchaseDate"))) : null,
						Long.valueOf(String.valueOf(param.get("purchasePrice"))), 
						Objects.nonNull(param.get("sellingDate")) &&  !param.get("sellingDate").equals("") ? LocalDate.parse(String.valueOf(param.get("sellingDate"))) : null, 
								Objects.nonNull(param.get("sellingPrice")) && !param.get("sellingPrice").equals("")? Long.valueOf(String.valueOf(param.get("sellingPrice"))) : 0, 
										Long.valueOf(String.valueOf(param.get("quantity"))));
		try {
			stockInventoryDao.save(dto);	
			if(Long.valueOf(String.valueOf(param.get("quantity"))) == 0) {
				stockInventoryDao.deleteById(Long.valueOf(String.valueOf(param.get("stockNumber"))));
			}
		} catch(Exception exception) {
			exception.printStackTrace();
		} 
		return "Success";
	}

	@RequestMapping("/")
	@ResponseBody
	public List<InventoryStock> insert(){
		return stockInventoryDao.findAll(Sort.by(Sort.Direction.ASC,"stockNumber")).stream().filter(data -> data.getQuantity() != 0).collect(Collectors.toList());
	}
	
	@RequestMapping("/stock/inventory/checkStock")
	@ResponseBody
	public boolean checkStock(@RequestBody Map<String, Object> columnName){
		String stockNumber = String.valueOf(columnName.get("columnName"));
		return stockInventoryDao.existsById(Long.parseLong(stockNumber));
	}
	
	@RequestMapping("/stock/inventory/sort")
	@ResponseBody
	public List<InventoryStock> sort(@RequestBody Map<String, Object> columnName){
		String column = String.valueOf(columnName.get("columnName"));
		Boolean isAsc = Boolean.valueOf(String.valueOf(columnName.get("isAsc")));
		List<InventoryStock> resultList = stockInventoryDao.findAll(Sort.by(isAsc ? Sort.Direction.ASC : Sort.Direction.DESC, column.replaceAll("_[a-z]", String.valueOf(Character.toUpperCase(column.charAt(column.indexOf("_") + 1))))));
		return resultList.stream().filter(data -> data.getQuantity() != 0).collect(Collectors.toList());
	}

}
