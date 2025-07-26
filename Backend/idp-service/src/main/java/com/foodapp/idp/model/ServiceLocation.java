package com.foodapp.idp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "service_location")
public class ServiceLocation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(name = "service_id", nullable = false, unique = true, updatable = false)
    String serviceId;

    @Column(name = "city", nullable = false)
    String city;

    @Column(name = "district")
    String district;

    @Column(name = "state")
    String state;

    @Column(name = "zipcode")
    String zipcode;

    @Column(name = "service_from")
    LocalDateTime serviceFrom;

}
