package com.petz.api.pet.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.petz.api.cliente.domain.Cliente;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = "id", callSuper = false)
@Entity
@Table(name = "pet")
public class Pet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(unique = true, nullable = false)
	private Integer id;

	@Column(nullable = false, length = 100)
	private String nome;

	@Column(nullable = true, length = 100)
	private String descricao;

	@Enumerated(EnumType.STRING)
	@Column(nullable = false, length = 50)
	private PetEspecie especie;

	@Column(nullable = false, length = 50)
	@Enumerated(EnumType.STRING)
	private PetGenero genero;

	@JsonIgnore
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "cliente_id")
	private Cliente cliente;

}