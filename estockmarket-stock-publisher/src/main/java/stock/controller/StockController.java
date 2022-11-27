package stock.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import stock.model.Response;
import stock.pojo.Stock;
import stock.service.StockService;


@CrossOrigin
@RestController
@RequestMapping("/api/v1.0/market/stock")
public class StockController {
	
	Logger logger = LoggerFactory.getLogger(StockController.class);

	@Autowired
	private StockService stockService;
	
	@Autowired
	private KafkaTemplate<String, Object> stocktemplate;
	
	@Autowired
	private KafkaTemplate<String, String> template;

	private String topic1 = "compStockPojo1";
	
	private String topic2 = "msgString";

	@PostMapping("/add/{company_code}")
	public ResponseEntity saveAndupdateStock(@PathVariable("company_code") String companyCode, @RequestBody Stock stock){
		logger.info("Sending data to mySQL database");
		Response response = stockService.saveAndupdateStock(companyCode, stock);
		logger.info("Sending message to kafka server");
		stocktemplate.send(topic1, stock);
		return ResponseEntity.status(response.status).body(response.message);
	}

	@DeleteMapping("/delete/{company_code}")
	public void deleteStockByCompanyCode(@PathVariable("company_code") String companyCode) {
		logger.info("Sending delete data to mySQL database");
		stockService.deleteCompanyWithStock(companyCode);
		logger.info("Sending message to kafka server");
		template.send(topic2, companyCode);
	}
}
