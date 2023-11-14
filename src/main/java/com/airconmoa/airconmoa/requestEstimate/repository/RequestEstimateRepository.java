package com.airconmoa.airconmoa.requestEstimate.repository;

import com.airconmoa.airconmoa.domain.RequestEstimate;
import com.airconmoa.airconmoa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RequestEstimateRepository extends JpaRepository<RequestEstimate, Long> {
    Optional<RequestEstimate> findByRequestEstimateId(Long requestEstimateId);
}
