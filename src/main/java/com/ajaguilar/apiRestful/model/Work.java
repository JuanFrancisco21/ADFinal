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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.geo.Point;

@Entity
@Table(name = "work")
public class Work implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@Column(name = "name")
	private String name;
	@Column(name = "description")
	private String description;
	@Column(name = "location")
	private Point location;
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "chief")
	private Worker chief;
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<Worker> workers;

	public Work(String name, String description, Point location, Worker chief, Set<Worker> workers) {
		this.id = -1L;
		this.name = name;
		this.description = description;
		this.location = location;
		this.chief = chief;
		this.workers = workers;
	}

	public Work() {
		this("", "", new Point(Double.MIN_VALUE, Double.MIN_VALUE), new Worker(), new HashSet<Worker>());
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

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}

	public Point getLocation() {
		return location;
	}
	public void setLocation(Point location) {
		this.location = location;
	}

	public Worker getChief() {
		return chief;
	}
	public void setChief(Worker chief) {
		this.chief = chief;
	}

	public Set<Worker> getWorkers() {
		return workers;
	}
	public void setWorkers(Set<Worker> workers) {
		this.workers = workers;
	}

}
