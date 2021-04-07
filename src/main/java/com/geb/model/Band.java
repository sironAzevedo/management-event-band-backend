package com.geb.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
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
@Table(name = "TB_BAND")
@EntityListeners(AuditingEntityListener.class)
public class Band implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
    @Column(name = "ID")
    @GeneratedValue(generator = "BAND_ID_SEQ", strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "BAND_ID_SEQ", sequenceName = "BAND_ID_SEQ", allocationSize = 1)
    private Long codigo;

    @Column(name = "NAME")
    private String name;
    
    @CreationTimestamp
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;
    
    @Fetch(FetchMode.SUBSELECT)
    @OneToMany(mappedBy = "codigo.band", cascade = CascadeType.ALL)
    private List<BandInfo> info;
    
    @PrimaryKeyJoinColumn
    @OneToOne(mappedBy = "band", cascade = CascadeType.ALL)
    private PjAssociatedBand associated;
    
}
