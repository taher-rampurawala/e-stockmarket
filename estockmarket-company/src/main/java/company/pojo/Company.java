package company.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "company")
public class Company {
	
	@Id
	private String _id;
	
	private int companyId;	
	
	private String companyCode;
	
	
	private String companyName;
	
	
	private String companyCEO;
	
	
	private long companyTurnover;
	

	private String companyWebsite;
	
	
	private String stockExchange;


	
}
