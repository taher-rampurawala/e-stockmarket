package stock.pojo;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "stock")
public class Stock {
	
	@Id
	private Long id;
	
	private long stockcode;
	
	private String companyCode;
	
	private double previousPrice;
	
	private double currentPrice;
	
	private Date previousDate;
	
	private Date currentDt;
	
	private boolean currentFlag = true;
	
}
