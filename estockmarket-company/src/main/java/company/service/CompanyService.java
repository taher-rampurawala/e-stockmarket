package company.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import company.model.CompanyStock;
import company.model.Response;
import company.model.Stock;
import company.pojo.Company;
import company.repository.CompanyRepository;

@Service
public class CompanyService {

	@Autowired
	private CompanyRepository companyRepository;

	@Autowired
	private RestTemplate restTemplate;

	public Response register(Company company) {
		Response response = new Response();
		Company newCompany = new Company();
		if (company.getCompanyTurnover() >= 100000000) {
			newCompany.setCompanyCode(company.getCompanyCode());
			newCompany.setCompanyName(company.getCompanyName());
			newCompany.setCompanyTurnover(company.getCompanyTurnover());
			newCompany.setCompanyWebsite(company.getCompanyWebsite());
			newCompany.setCompanyCEO(company.getCompanyCEO());
			newCompany.setStockExchange(company.getStockExchange());

			Company savedCompnay = companyRepository.save(newCompany);
			if (savedCompnay != null) {
				response.setMessage("Compnay saved successfully!!");
				response.setStatus(200);
			} else {
				response.setStatus(409);
				response.setMessage("Comppany not saved successfully!!");
			}

		}

		else {
			response.setStatus(403);
			response.setMessage("Company Turnover must be greater than 10cr");
		}

		return response;
	}

	public CompanyStock getCompanyWithStock(String companyCode) {
		Company company = companyRepository.findByCompanyCode(companyCode);
		CompanyStock companyStock = new CompanyStock();
		if (company != null) {
			HttpHeaders headers = new HttpHeaders();
			headers.setBasicAuth("admin", "password");
			headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
			HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
			ResponseEntity<Stock> response = restTemplate.exchange("http://localhost:8082/api/v1.0/market/stock/" +companyCode,
					HttpMethod.GET, httpEntity, Stock.class);
			companyStock.setCompany(company);
			companyStock.setStock(response.getBody());
			return companyStock;
		}
		return null;
	}

	public List<CompanyStock> getAllCompaniesWithStocks() {
		// TODO Auto-generated method stub
		List<Company> companies = companyRepository.findAll();
		List<CompanyStock> companyStockList = new ArrayList<>();
		for (Company company : companies) {
			CompanyStock companyStock = new CompanyStock();
			companyStock.setCompany(company);
			HttpHeaders headers = new HttpHeaders();
			headers.setBasicAuth("admin", "password");
			headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
			HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
			ResponseEntity<Stock> response = restTemplate.exchange("http://localhost:8082/api/v1.0/market/stock/" +company.getCompanyCode(),
					HttpMethod.GET, httpEntity, Stock.class);
			companyStock.setStock(response.getBody());
			companyStockList.add(companyStock);
		}
		return companyStockList;
	}

	public void deleteCompanyWithStock(String companyCode) {
		Company company = companyRepository.findByCompanyCode(companyCode);
		if (company != null) {
			companyRepository.delete(company);
			HttpHeaders headers = new HttpHeaders();
			headers.setBasicAuth("admin", "password");
			headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
			HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
			restTemplate.exchange("http://localhost:8082/api/v1.0/market/stock/delete" +companyCode,
					HttpMethod.DELETE, httpEntity, Stock.class);
		}
	}

}
