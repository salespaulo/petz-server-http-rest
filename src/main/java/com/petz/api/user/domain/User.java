package com.petz.api.user.domain;

import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.google.common.collect.Sets;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false, length = 100)
	private String name;

	@Column(nullable = false, length = 100)
	private String username;

	@Column(nullable = false, length = 100)
	private String password;

	@Lob
	@Column(nullable = true, name = "refresh_token", columnDefinition="TEXT")
	private String refreshToken;

	// bi-directional many-to-many association to Usergroup
	@ManyToMany(mappedBy = "users")
	private Set<Usergroup> groups = Sets.newHashSet();

	// uni-directional many-to-many association to Role
	@ManyToMany
	@JoinTable(name = "user_role", joinColumns = {
			@JoinColumn(name = "user_id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "role_id", nullable = false) })
	private Set<Role> roles = Sets.newHashSet();

	public Set<String> getPrivileges() {
		final Set<Role> groupRoles = groups.stream().flatMap(g -> g.getRoles().stream()).collect(Collectors.toSet());

		groupRoles.addAll(this.roles);

		return groupRoles.stream().flatMap(r -> r.getPrivileges().stream()).map(Privilege::getName)
				.collect(Collectors.toSet());
	}

}