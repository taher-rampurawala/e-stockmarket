package stock.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import stock.pojo.Stock;

public interface StockRepository extends MongoRepository<Stock, Long> {

	@Query(value = "{'companyCode' : ?0, 'currentFlag' : true}")
	public Stock findByCompanyCode(@Param("companyCode") String companyCode);

	@Query(value = "{'companyCode' : ?0, 'currentDt' : {$gt : ?1, $lt : ?2}}")
	public List<Stock> findStocksByCompanyCode(@Param("companyCode") String companyCode, @Param("startdate") Date startdate, @Param("enddate") Date enddate);
	
	@Query(value = "{'companyCode' : ?0}")
	public  List<Stock> deleteByCompanyCode(@Param("companyCode")  String companyCode);

}


