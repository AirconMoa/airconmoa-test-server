package com.airconmoa.airconmoa.responseEstimate.repository;

import com.airconmoa.airconmoa.domain.RequestEstimate;
import com.airconmoa.airconmoa.domain.ResponseEstimate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResponseEstimateRepository extends JpaRepository<ResponseEstimate, Long> {
    @Query("select rs from ResponseEstimate rs, RequestEstimate rq, User u " +
            "where rs.requestEstimate.requestEstimateId = rq.requestEstimateId " +
            "and u.email =:userEmail and rq.user.userId = u.userId")
    List<ResponseEstimate> findMyResponseEstimate(@Param("userEmail") String userEmail);
}