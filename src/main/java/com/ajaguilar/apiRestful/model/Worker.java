package com.ajaguilar.apiRestful.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import io.swagger.annotations.ApiModelProperty;


@Entity
@Table(name = "worker")
public class Worker implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@ApiModelProperty(position = 0, name = "Id", notes = "Identificador del Work", required = true, value = "1")
	private Long id;
	
	@Column(name = "name")
	@ApiModelProperty(position = 1, name = "Nombre", notes = "Nombre del Worker", required = true, value = "Juan")
	private String name;
	
	@Column(name = "surname")
	@ApiModelProperty(position = 2, name = "Apellido", notes = "Apellido del Worker", required = true, value = "Aguilar")
	private String surname;
	
	@Column(name = "email")
	@ApiModelProperty(position = 3, name = "Email", notes = "Email del Worker", required = true, value = "test@gmail.com")
	private String email;
	
	@Column(name = "active")
	@ApiModelProperty(position = 4, name = "Active", notes = "Worker activo de la empresa", required = true, value = "true")
	private Boolean active;
	
	@Column(name = "picture")
	@ApiModelProperty(position = 5, name = "Foto", notes = "Fotografia del Worker", required = true, value = "URL")
	private String picture;


	// Modelo work
	@JsonIgnoreProperties(value = { "chief" })
	@OneToMany(mappedBy = "chief", cascade = CascadeType.MERGE, fetch = FetchType.EAGER, orphanRemoval = false)
	@ApiModelProperty(position = 6, name = "ListaObras", notes = "Relacion con Trabajos en los que es Jefe", required = true, value = "Objeto work")
	private Set<Work> chiefWorkList;

	// Modelo worker_work
	@JsonIgnoreProperties(value = { "worker" })
	@OneToMany(mappedBy = "worker")
	@ApiModelProperty(position = 7, name = "Worker_Work", notes = "Relacion entre trabajador y la obra", required = true, value = "Objeto worker_work")
	private Set<WorkerWork> workerWork;

	public Worker(String name, String surname, Boolean active, String email, String picture, Set<Work> chiefWorkList, Set<WorkerWork> workerWork) {
		this.id = -1L;
		this.name = name;
		this.surname = surname;
		this.active = active;
		this.email = email;
		this.picture = picture;
		this.chiefWorkList = chiefWorkList;
		this.workerWork = workerWork;
	}

	public Worker() {
		this("", "", true, "", "", new HashSet<Work>(), new HashSet<WorkerWork>());
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public Set<Work> getChiefWorkList() {
		return chiefWorkList;
	}
	public void setChiefWorkList(Set<Work> chiefWorkList) {
		this.chiefWorkList = chiefWorkList;
	}

	public Set<WorkerWork> getWorkerWork() {
		return workerWork;
	}
	public void setWorkerWork(Set<WorkerWork> workerWork) {
		this.workerWork = workerWork;
	}
	
	

	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((surname == null) ? 0 : surname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Worker other = (Worker) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (surname == null) {
			if (other.surname != null)
				return false;
		} else if (!surname.equals(other.surname))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Worker [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + ", active="
				+ active + ", picture=" + picture + "]";
	}
	
	


}
