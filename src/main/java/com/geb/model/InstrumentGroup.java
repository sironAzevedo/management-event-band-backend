package com.geb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
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
@Table(name = "TB_GROUP_INSTRUMENT_MUSICAL")
@EntityListeners(AuditingEntityListener.class)
public class InstrumentGroup implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name = "ID")
    @GeneratedValue(generator = "GROUP_INSTRUMENT_ID_SEQ", strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "GROUP_INSTRUMENT_ID_SEQ", sequenceName = "GROUP_INSTRUMENT_ID_SEQ", allocationSize = 1)
    private Long codigo;

    @Column(name = "NAME")
    private String name;
}
