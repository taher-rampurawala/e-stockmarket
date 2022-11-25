package stock.controller;

import java.text.ParseException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import stock.model.CompanyStockDto;
import stock.model.Response;
import stock.pojo.Stock;
import stock.repository.StockRepository;
import stock.service.StockService;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1.0/market/stock")
public class StockController {

	@Autowired
	private StockService stockService;

	@Autowired
	private StockRepository stockRepository;

	Logger logger = LoggerFactory.getLogger(StockController.class);


	@GetMapping("/{company_code}")
	public Stock findStockByCompanyCode(@PathVariable("company_code") String companyCode) {
		logger.info("inside findStockByCompanyCode method of StockController");
		return stockService.findByCompanyCode(companyCode);
	}

	@GetMapping("/getAllStock")
	public List<Stock> getAllStock() {
		return stockRepository.findAll();
	}

	@PostMapping("/add/{company_code}")
	public ResponseEntity<String> saveAndupdateStock(@PathVariable("company_code") String companyCode, @RequestBody Stock stock){
		Response response = stockService.saveAndupdateStock(companyCode, stock);
		return ResponseEntity.status(response.status).body(response.message);
	}

	
	@GetMapping("/get/{company_code}/{startdate}/{enddate}")
	public CompanyStockDto getStockDetailsForCompany(@PathVariable("company_code") String companyCode,@PathVariable("startdate") String startdate, @PathVariable("enddate") String enddate) throws ParseException
	{
		CompanyStockDto companyStockDto = stockService.getStockDetailsForCompany(companyCode,startdate,enddate);
		return companyStockDto;
	
	}

	@DeleteMapping("/delete/{company_code}")
	public void deleteStockByCompanyCode(@PathVariable("company_code") String companyCode) {

		stockService.deleteCompanyWithStock(companyCode);

	}


}
