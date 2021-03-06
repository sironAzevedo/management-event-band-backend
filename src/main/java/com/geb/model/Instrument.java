package com.geb.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
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
@Table(name = "TB_INSTRUMENT_MUSICAL")
@EntityListeners(AuditingEntityListener.class)
public class Instrument implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name = "ID")
    @GeneratedValue(generator = "INSTRUMENTS_ID_SEQ", strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "INSTRUMENTS_ID_SEQ", sequenceName = "INSTRUMENTS_ID_SEQ", allocationSize = 1)
    private Long codigo;

    @Column(name = "NAME")
    private String name;
    
    @ManyToMany(mappedBy = "instruments")
	private List<User> users;   
    
    @JoinColumn(name = "ID_GROUP", nullable = true)
    @ManyToOne(targetEntity = InstrumentGroup.class, fetch = FetchType.LAZY)
	private InstrumentGroup group;
}
