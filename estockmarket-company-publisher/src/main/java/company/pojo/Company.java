package company.pojo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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
public class Company {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long companyId;
	
	
	private String companyCode;
	
	
	private String companyName;
	
	
	private String companyCEO;
	
	
	private long companyTurnover;
	

	private String companyWebsite;
	
	private String stockExchange;


	
}
