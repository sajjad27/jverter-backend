package com.jverter.auth.model.entity;
import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jverter.auth.model.AppRole;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_role")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserRole implements Serializable {

	private static final long serialVersionUID = 1L;


    @JsonIgnore
    @ManyToOne
    @Id
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    
    @Id
    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private AppRole role;
    
    public AppRole getRoleOrDefault() {
        if (role == null) {
            // Return a default enum value or null as per your requirement
            return null; // You can also return a specific default enum value
        }
        return role;
    }
}

