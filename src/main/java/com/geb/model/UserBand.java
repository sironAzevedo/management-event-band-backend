package com.geb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.geb.model.enums.LeaderEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TB_USER_BAND")
@EntityListeners(AuditingEntityListener.class)
public class UserBand implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name = "ID")
    @GeneratedValue(generator = "BAND_USER_ID_SEQ", strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "BAND_USER_ID_SEQ", sequenceName = "BAND_USER_ID_SEQ", allocationSize = 1)
    private Long codigo;
	
	@ManyToOne
    @JoinColumn(name = "ID_USER")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ID_BAND")
    private Band band;
    
    @Column(name = "LEADER")
    @Enumerated(EnumType.STRING)
    private LeaderEnum leader;

}
