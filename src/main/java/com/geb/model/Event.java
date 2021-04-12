package com.geb.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_EVENT")
public class Event implements Serializable {
    private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name = "ID")
    @GeneratedValue(generator = "EVENT_ID_SEQ", strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "EVENT_ID_SEQ", sequenceName = "EVENT_ID_SEQ", allocationSize = 1)
    private Long codigo;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="ID_BAND", nullable=false)
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Band band;
	
	@Column(name = "DATE_EVENT")
	private LocalDate eventDate;

	@NotNull
    @Column(name = "DESCRIPTION")
    private String description;
    
    @Column(name = "DESC_ADDRESS")
    private String address;

}
