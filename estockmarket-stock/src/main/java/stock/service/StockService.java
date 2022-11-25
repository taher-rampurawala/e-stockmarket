package stock.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import stock.model.CompanyStockDto;
import stock.model.Response;
import stock.pojo.Stock;
import stock.repository.StockRepository;

@Service
public class StockService {

	@Autowired
	StockRepository stockRepository;
	
	public Stock findByCompanyCode(String companyCode) {
		return stockRepository.findByCompanyCode(companyCode);
	}

	public Response saveAndupdateStock(String companyCode, Stock newStock) {
		Response response = new Response();
		if(stockRepository.findByCompanyCode(companyCode) != null)
		{
			Stock currentStock = stockRepository.findByCompanyCode(companyCode);
			if(currentStock.getCurrentPrice() == newStock.getCurrentPrice()) {
				response.setStatus(409);
				response.setMessage("New price is same as current price");
			}
			else {
				currentStock.setCurrentFlag(false);
				newStock.setCurrentFlag(true);
				newStock.setCompanyCode(companyCode);
				newStock.setPreviousPrice(currentStock.getCurrentPrice());
				newStock.setPreviousDate(currentStock.getCurrentDt());
				stockRepository.save(newStock);
				response.setStatus(201);
				response.setMessage("Stock updated successfully");
			}
				
		}
		else {
			newStock.setCompanyCode(companyCode);
			stockRepository.save(newStock);
			response.setStatus(201);
			response.setMessage("Stock created successfully");
		}
		return response;
	}


	public CompanyStockDto getStockDetailsForCompany(String companyCode, String startdate, String enddate) throws ParseException {
		CompanyStockDto companyStockDto = new CompanyStockDto();
		
		Date stdate=new SimpleDateFormat("yyyy-MM-dd").parse(startdate); 
		Date edate =new SimpleDateFormat("yyyy-MM-dd").parse(enddate); 

		List<Stock> stocks = stockRepository.findStocksByCompanyCode(companyCode,stdate,edate);
		if(stocks.isEmpty())
			return null;
		double max = Double.MIN_VALUE;
		double min = Double.MAX_VALUE;
		double sum = 0.0;
		for(Stock stock: stocks) {
			double price = stock.getCurrentPrice();
			sum+=price;
			if(price < min)
				min = price;
			if(price > max)
				max = price;
			
		}
		double avg = sum/(stocks.size());
		companyStockDto.setMin(min);
		companyStockDto.setAvg(avg);
		companyStockDto.setMax(max);
		companyStockDto.setStock(stocks);
		return companyStockDto;
	}

	public void deleteCompanyWithStock(String companyCode) {
		List<Stock> stock = stockRepository.deleteByCompanyCode(companyCode);

		if (stock != null) {
			stockRepository.deleteAll(stock);
		}
	}


}
