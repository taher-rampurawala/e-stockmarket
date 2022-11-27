package company.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
				response.setMessage("Company saved successfully!!");
				response.setStatus(200);
			} else {
				response.setStatus(409);
				response.setMessage("Company not saved successfully!!");
			}

		}

		else {
			response.setStatus(403);
			response.setMessage("Company Turnover must be greater than 10cr");
		}

		return response;
	}

	public void deleteCompanyWithStock(String companyCode) {
		Company company = companyRepository.findByCompanyCode(companyCode);
		if (company != null) {
			companyRepository.delete(company);
			HttpHeaders headers = new HttpHeaders();
			headers.setBasicAuth("admin", "password");
			headers.set("Accept", MediaType.APPLICATION_JSON_VALUE);
			HttpEntity<String> httpEntity = new HttpEntity<String>(headers);
			restTemplate.exchange("http://localhost:8082/api/v1.0/market/stock/delete/" + companyCode, HttpMethod.DELETE,
					httpEntity, Stock.class);
		}
	}

}
