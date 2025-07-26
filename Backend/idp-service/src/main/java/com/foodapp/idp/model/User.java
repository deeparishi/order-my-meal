package com.foodapp.idp.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.foodapp.idp.enums.Enum;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Data
@Entity
@Table(name = "user")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email_id", nullable = false, unique = true)
    private String emailId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    Enum.Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Address> address;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_type")
    private Enum.UserType userType;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "last_visit")
    private LocalDateTime lastVisited;

    @Column(name = "last_ordered")
    private LocalDateTime lastOrderedAt;

    @Column(name = "last_order_id")
    private String lastOrderId;

    @Column(name = "mail_verified")
    private boolean isEmailVerified;

    @Column(name = "mobile_verified")
    private boolean isMobileVerified;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(() -> role.name());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
