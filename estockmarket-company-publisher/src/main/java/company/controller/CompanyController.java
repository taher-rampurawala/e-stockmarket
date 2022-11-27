package company.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import company.model.Response;
import company.pojo.Company;
import company.service.CompanyService;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("/api/v1.0/market/company")
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private KafkaTemplate<String, Object> companytemplate;
	
	@Autowired
	private KafkaTemplate<String, String> template;

	private String topic1 = "compStockPojo";
	
	private String topic2 = "msgString";


	@PostMapping("/register")
	public ResponseEntity saveCompany(@RequestBody Company company) {
		log.info("Sending data to mySQL database");
		Response response = companyService.register(company);
		log.info("Sending message to kafka server");
		companytemplate.send(topic1, company);
		return ResponseEntity.status(response.status).body(response.message);

	}

	@DeleteMapping("/delete/{company_code}")
	public void deleteCompanyWithStock(@PathVariable("company_code") String companyCode) {
		log.info("Sending delete data to mySQL database");
		companyService.deleteCompanyWithStock(companyCode);
		log.info("Sending message to kafka server");
		template.send(topic2, companyCode);
	}
}
