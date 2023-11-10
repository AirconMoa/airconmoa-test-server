package com.airconmoa.airconmoa.requestEstimate.repository;

import com.airconmoa.airconmoa.domain.RequestEstimate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestEstimateRepository extends JpaRepository<RequestEstimate, Long> {
}
