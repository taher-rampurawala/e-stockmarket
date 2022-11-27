package company.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import company.pojo.Company;


public interface CompanyRepository extends JpaRepository<Company, Long> {
	@Query("select c from Company c where c.companyCode=:companyCode")
	Company findByCompanyCode(@Param("companyCode") String companyCode);

}
