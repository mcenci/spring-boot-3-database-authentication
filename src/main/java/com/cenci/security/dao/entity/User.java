package com.cenci.security.dao.entity;

import com.cenci.security.web.model.ProfileUserFormData;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "'user'")
public class User {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "phone")
    private String phone;

    @Column(name = "role")
    private String role;
    
    @Column(name = "enabled", nullable = false)
    private boolean enabled;
    
    @Column(name = "verified", nullable = false)
    private boolean verified;
    
    public void fromProfileUser(ProfileUserFormData form) {
    	this.setUsername(form.getUsername());
    	this.setFirstName(form.getFirstName());
    	this.setLastName(form.getLastName());
    	this.setPhone(form.getPhone());
//    	this.setEnable2FA(form.getEnable2FA()); 
    }
}