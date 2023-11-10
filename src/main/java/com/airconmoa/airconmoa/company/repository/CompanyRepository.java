package com.airconmoa.airconmoa.company.repository;

import com.airconmoa.airconmoa.domain.Company;
import com.airconmoa.airconmoa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Long> {
    Company findCompanyByCompanyEmail(String email);
}
