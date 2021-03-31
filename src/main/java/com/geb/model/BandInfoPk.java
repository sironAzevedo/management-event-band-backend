package com.geb.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Embeddable
@NoArgsConstructor
@AllArgsConstructor
public class BandInfoPk implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name = "ID_USER")
    private Long userId;

    @Column(name = "ID_BAND")
    private Long bandId;

}
