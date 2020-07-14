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
@Table(name = "usergroup")
public class Usergroup {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false, length = 100)
	private String name;

	// bi-directional many-to-many association to User
	@ManyToMany
	@JoinTable(name = "user_usergrp", joinColumns = {
			@JoinColumn(name = "usergrp_id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "user_id", nullable = false) })
	private Set<User> users = Sets.newHashSet();

	// uni-directional many-to-many association to User
	@ManyToMany
	@JoinTable(name = "usergrp_role", joinColumns = {
			@JoinColumn(name = "usergrp_id", nullable = false) }, inverseJoinColumns = {
					@JoinColumn(name = "role_id", nullable = false) })
	private Set<Role> roles = Sets.newHashSet();
}