package com.geb.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.Email;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.geb.model.enums.PerfilEnum;
import com.geb.model.enums.TypePersonEnum;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Table(name = "TB_USER")
@EntityListeners(AuditingEntityListener.class)
public class User implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "ID")
    @GeneratedValue(generator = "USER_ID_SEQ", strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name = "USER_ID_SEQ", sequenceName = "USER_ID_SEQ", allocationSize = 1)
    private Long codigo;

    @Column(name = "NAME")
    private String name;

    @Email
    @Column(name = "EMAIL", unique = true)
    private String email;

    @Email
    @Column(name = "CONFIRM_EMAIL")
    private String confirmEmail;

    @Column(name = "PHONE", unique = true)
    private String phone;

    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "CONFIRM_PASSWORD")
    private String confirmPassword;

    @Column(name = "TYPE_USER")
    @Enumerated(EnumType.STRING)
    private TypePersonEnum typeUser;

    @CreationTimestamp
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "UPDATED_AT")
    private LocalDateTime updatedAt;

    @ManyToMany(fetch = FetchType.EAGER, cascade = { CascadeType.MERGE })
    @JoinTable(name = "TB_USER_ROLE", joinColumns = @JoinColumn(name = "ID_USER", referencedColumnName = "ID"),
            inverseJoinColumns = @JoinColumn(name = "ID_ROLE", referencedColumnName = "ID"))
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(x -> new SimpleGrantedAuthority(x.getPerfil().name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
    public boolean hasRole(PerfilEnum perfil) {
		return getAuthorities().contains(new SimpleGrantedAuthority(perfil.name()));
	}
}