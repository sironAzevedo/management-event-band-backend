package com.geb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

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
@Table(name = "TB_PJ_CHAVE")
@EntityListeners(AuditingEntityListener.class)
public class PjChave implements Serializable {
	private static final long serialVersionUID = 1L;
	
	
	@Id
    @Column(name = "ID_USER")
    private Long codigo;

    @Column(name = "CHAVE")
    private String chave;
    
    @MapsId
	@OneToOne
    @JoinColumn(name = "ID_USER")
    private User user;
}
