package com.geb.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.*;
import com.geb.model.enums.PerfilEnum;
import lombok.*;

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
	@Enumerated(EnumType.STRING)
	private PerfilEnum perfil;
	
	@ManyToMany(mappedBy = "roles")
	private List<User> usuarios;
}
