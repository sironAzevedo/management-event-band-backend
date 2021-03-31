package com.geb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
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
@Table(name = "TB_BAND_INFO")
@EntityListeners(AuditingEntityListener.class)
public class BandInfo implements Serializable {

	private static final long serialVersionUID = 1L;
	
//	@Id
//    @Column(name = "ID")
//    @GeneratedValue(generator = "BAND_USER_ID_SEQ", strategy = GenerationType.IDENTITY)
//    @SequenceGenerator(name = "BAND_USER_ID_SEQ", sequenceName = "BAND_USER_ID_SEQ", allocationSize = 1)
//    private Long codigo;
	
	@EmbeddedId
    private BandInfoPk codigo;
	
	@ManyToOne
	@MapsId("userId")
    @JoinColumn(name = "ID_USER")
    private User user;

    @ManyToOne
    @MapsId("bandId")
    @JoinColumn(name = "ID_BAND")
    private Band band;
    
    @Column(name = "LEADER")
    @Enumerated(EnumType.STRING)
    private LeaderEnum leader;
    
    @JoinColumn(name = "ID_INSTRUMENT")
    @ManyToOne(targetEntity = Instrument.class,fetch = FetchType.LAZY)
    private Instrument instrument;
    
    @JoinColumn(name = "ID_VOICE")
    @ManyToOne(targetEntity = Voice.class,fetch = FetchType.LAZY)
    private Voice voice;

}
