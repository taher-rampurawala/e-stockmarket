package company.model;

import company.pojo.Company;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompanyStock {
	private Company company;
	private Stock stock;

}
