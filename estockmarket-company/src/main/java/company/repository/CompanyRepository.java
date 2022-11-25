package company.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;

import company.pojo.Company;


public interface CompanyRepository extends MongoRepository<Company, Long> {
	
	@Query(value = "{'companyCode' : ?0}")
	Company findByCompanyCode(@Param("companyCode") String companyCode);

}
