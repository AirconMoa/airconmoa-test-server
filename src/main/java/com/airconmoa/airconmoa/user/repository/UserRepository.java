package com.airconmoa.airconmoa.user.repository;

import com.airconmoa.airconmoa.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    @Query("select count(u) from User u where u.email = :email")
    Integer findByEmailCount(@Param("email") String email);
}
