package com.foodapp.idp.repo;

import com.foodapp.idp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Integer> {

    @Query(value = "SELECT * FROM user WHERE email_id = ?1 OR mobile_number = ?1", nativeQuery = true)
    Optional<User> findByEmailOrMobileNumber(String emailOrMobileNumber);

}
