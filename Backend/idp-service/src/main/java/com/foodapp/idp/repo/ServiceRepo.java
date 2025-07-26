package com.foodapp.idp.repo;

import com.foodapp.idp.model.ServiceLocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServiceRepo extends JpaRepository<ServiceLocation, Integer> {
}
