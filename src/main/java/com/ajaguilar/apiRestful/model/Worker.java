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
import javax.persistence.ManyToMany;
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
	@Column(name = "picture")
	private Byte[] picture;
	@OneToMany(mappedBy = "work", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Work> chiefWorkList;
	@ManyToMany(fetch = FetchType.EAGER)
	private Work works;

	public Worker(String name, String surname, Short active, Byte[] picture, Set<Work> chiefWorkList, Work works) {
		this.id = -1L;
		this.name = name;
		this.surname = surname;
		this.active = active;
		this.picture = picture;
		this.chiefWorkList = chiefWorkList;
		this.works = works;
	}

	public Worker() {
		this("", "", Short.MIN_VALUE, new Byte[Byte.MIN_VALUE], new HashSet<Work>(), new Work());
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

	public Byte[] getPicture() {
		return picture;
	}
	public void setPicture(Byte[] picture) {
		this.picture = picture;
	}

	public Set<Work> getChiefWorkList() {
		return chiefWorkList;
	}
	public void setChiefWorkList(Set<Work> chiefWorkList) {
		this.chiefWorkList = chiefWorkList;
	}

	public Work getWorks() {
		return works;
	}
	public void setWorks(Work works) {
		this.works = works;
	}

}
