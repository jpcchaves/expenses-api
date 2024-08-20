package com.expenses.app.persistence.repository;

import com.expenses.app.domain.models.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

  @Query(value = "SELECT * FROM users u WHERE u.email = :email", nativeQuery = true)
  Optional<User> findByEmail(@Param("email") String email);

  @Query(value = "SELECT EXISTS(SELECT 1 FROM users u WHERE u.email = :email)", nativeQuery = true)
  Boolean existsByEmail(@Param("email") String email);
}
