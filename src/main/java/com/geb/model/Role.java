package com.geb.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.geb.model.enums.PerfilEnum;
import com.geb.model.enums.convert.PerfilConvert;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@ToString
@NoArgsConstructor
@EqualsAndHashCode
@Table(name = "TB_ROLE")
public class Role implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "ROLE_ID_SEQ", strategy = GenerationType.IDENTITY)
	@SequenceGenerator(name = "ROLE_ID_SEQ", sequenceName = "ROLE_ID_SEQ", allocationSize = 1)
	private Long codigo;

	@Column(name = "ROLE_DESC")
	@Convert(converter = PerfilConvert.class)
	private PerfilEnum perfil;
	
	@ManyToMany(mappedBy = "roles")
	private List<User> usuarios;
}
