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

@Entity
@Table(name = "worker")
public class Worker implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "surname")
	private String surname;
	@Column(name = "active")
	private Short active;
	@Column(name = "picture", columnDefinition = "LONGTEXT")
	private String picture;
	@OneToMany(mappedBy = "chief", cascade = CascadeType.PERSIST, fetch = FetchType.EAGER, orphanRemoval = true)
	private Set<Work> chiefWorkList;
	@OneToMany(fetch = FetchType.LAZY)
	private Set<WorkerWork> workerWork;

	public Worker(String name, String surname, Short active, String picture, Set<Work> chiefWorkList, Set<WorkerWork> workerWork) {
		this.id = -1L;
		this.name = name;
		this.surname = surname;
		this.active = active;
		this.picture = picture;
		this.chiefWorkList = chiefWorkList;
		this.workerWork = workerWork;
	}

	public Worker() {
		this("", "", Short.MIN_VALUE, "", new HashSet<Work>(), new HashSet<WorkerWork>());
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

	public Short getActive() {
		return active;
	}
	public void setActive(Short active) {
		this.active = active;
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

}
