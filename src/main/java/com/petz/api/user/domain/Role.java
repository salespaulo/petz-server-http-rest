package com.petz.api.user.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.google.common.collect.Sets;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "role")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false, length = 100)
	private String name;

	// uni-directional many-to-many association to Privilege
	@ManyToMany
	@JoinTable(name = "role_priv", joinColumns = {
			@JoinColumn(name = "role_id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "privilege_id", nullable = false) })
	private Set<Privilege> privileges = Sets.newHashSet();

}