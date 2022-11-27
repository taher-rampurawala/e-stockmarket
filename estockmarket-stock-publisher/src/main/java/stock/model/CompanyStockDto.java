package stock.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import stock.pojo.Stock;

@Getter
@Setter
public class CompanyStockDto {
	
	private List<Stock> stock;
	private double min;
	private double max;
	private double avg;


}
