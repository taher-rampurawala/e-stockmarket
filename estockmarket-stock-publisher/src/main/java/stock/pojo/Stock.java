package stock.pojo;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor

public class Stock {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private long stockcode;
	
	private String companyCode;
	
	private double previousPrice;
	
	private double currentPrice;
	
	private Date previousDate;
	
	private Date currentDt;
	
	private boolean currentFlag = true;
	
}
