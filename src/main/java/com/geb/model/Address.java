package com.geb.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_ADDRESS")
@EntityListeners(AuditingEntityListener.class)
public class Address implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
    @Column(name = "ID_USER")
    private Long codigo;

    @Column(name = "STREET")
    private String street;
    
    @Column(name = "DISTRICT")
    private String district;

    @Column(name = "CITY")
    private String city;
    
    @Column(name = "STATE")
    private String state;
    
    @Column(name = "COUNTRY")
    private String country;
    
    @Column(name = "CEP")
    private String cep;
    
    @CreationTimestamp
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;
    
    @OneToOne
    @MapsId
    @JoinColumn(name = "ID_USER")
    private User user;
}
