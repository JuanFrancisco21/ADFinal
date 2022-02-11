package com.ajaguilar.apiRestful.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

	// Modelo worker
	@JsonIgnoreProperties(value = { "chiefWorkList" })
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
	@JoinColumn(name = "chief")
	private Worker chief;

	// Modelo worker_work
	@JsonIgnoreProperties(value = { "work" })
	@OneToMany(mappedBy = "work", fetch = FetchType.EAGER)
	private Set<WorkerWork> workerWork;

	public Work(String name, String description, Point location, Worker chief, Set<WorkerWork> workerWork) {
		this.id = -1L;
		this.name = name;
		this.description = description;
		this.location = location;
		this.chief = chief;
		this.workerWork = workerWork;
	}

	public Work() {
		this("", "", new Point(Double.MIN_VALUE, Double.MIN_VALUE), new Worker(), new HashSet<WorkerWork>());
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

	public Set<WorkerWork> getWorkerWork() {
		return workerWork;
	}
	public void setWorkerWork(Set<WorkerWork> workerWork) {
		this.workerWork = workerWork;
	}

	@Override
	public String toString() {
		return "Work [id=" + id + ", name=" + name + ", description=" + description + ", location=" + location + "]";
	}

}
