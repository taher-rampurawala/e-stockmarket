package stock.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import stock.pojo.Stock;

public interface StockRepository extends JpaRepository<Stock, Long> {

	@Query("select s from Stock s where s.companyCode=:companyCode and s.currentFlag = true")
	public Stock findByCompanyCode(@Param("companyCode") String companyCode);

	//@Query("select s from Stock s where s.companyCode=:companyCode and s.currentDt BETWEEN :startdate AND :enddate")
	//public List<Stock> getAllStocksForCompany(@Param("companyCode") String companyCode, @Param("startdate") Date startdate, @Param("enddate") Date enddate);
	
	
	@Query("select s from Stock s where s.companyCode=:companyCode")
	public  List<Stock> deleteByCompanyCode(@Param("companyCode")  String companyCode);

}


