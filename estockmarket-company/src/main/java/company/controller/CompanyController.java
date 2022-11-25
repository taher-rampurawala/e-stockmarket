package company.controller;

import java.util.List;


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

import company.model.CompanyStock;
import company.model.Response;
import company.pojo.Company;
import company.repository.CompanyRepository;
import company.service.CompanyService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1.0/market/company")
public class CompanyController {

	@Autowired
	private CompanyService companyService;

	@Autowired
	private CompanyRepository companyRepository;


	@PostMapping("/register")
	public ResponseEntity<String> saveCompany(@RequestBody Company company) {
		log.info("inside register company");
		Response response = companyService.register(company);
			return ResponseEntity.status(response.status).body(response.message);
	}

	@GetMapping("/info/{company_code}")
	public CompanyStock getCompanyWithStock(@PathVariable("company_code") String companyCode) {
		return companyService.getCompanyWithStock(companyCode);

	}

	@GetMapping("/getall")
	public List<Company> getAllCompanyDetails() {
		return companyRepository.findAll();
	}
	
	@GetMapping("/getAllCompaniesWithStocks")
	public List<CompanyStock> getAllCompaniesWithStocks()
	{
		return companyService.getAllCompaniesWithStocks();
	}
	
	@DeleteMapping("/delete/{company_code}")
	public void deleteCompanyWithStock(@PathVariable("company_code") String companyCode){
		
		 companyService.deleteCompanyWithStock(companyCode);
		
	}
	
}

