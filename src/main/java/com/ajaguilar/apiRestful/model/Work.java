package com.ajaguilar.apiRestful.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

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
	@ApiModelProperty(position = 0, name = "Id", notes = "Identificador del Work", required = true, value = "1")
	private Long id;
	
	@Column(name = "name")
	@ApiModelProperty(position = 1, name = "Nombre", notes = "Nombre del Work", required = true, value = "Nueva obra")
	private String name;
	
	@Column(name = "description")
	@ApiModelProperty(position = 2, name = "Descripcion", notes = "Descripcion del Work", required = true, value = "Reforma del salon principal")
	private String description;
	
	@Column(name = "active")
	@ApiModelProperty(position = 3, name = "Active", notes = "Work activo de la empresa", required = true, value = "true")
	private Boolean active;
	
	@Column(name = "location")
	@ApiModelProperty(position = 4, name = "Localizaci�n", notes = "Localizacion del Work", required = true, value = "Point de java")
	private Point location;

	// Modelo worker
	@JsonIgnoreProperties(value = { "chiefWorkList" })
	@ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
	@JoinColumn(name = "chief")
	@ApiModelProperty(position = 5, name = "Worker", notes = "Relacion con Trabajador", required = true, value = "Objeto worker")
	private Worker chief;

	// Modelo worker_work
	@JsonIgnoreProperties(value = { "work" })
	@OneToMany(mappedBy = "work", fetch = FetchType.EAGER)
	@ApiModelProperty(position = 6, name = "Worker_Work", notes = "Relacion entre trabajador y la obra", required = true, value = "Objeto worker_work")
	private Set<WorkerWork> workerWork;

	public Work(String name, String description, Boolean active, Point location, Worker chief, Set<WorkerWork> workerWork) {
		this.id = -1L;
		this.name = name;
		this.description = description;
		this.active = active;
		this.location = location;
		this.chief = chief;
		this.workerWork = workerWork;
	}

	public Work() {
		this("", "", true, new Point(Double.MIN_VALUE, Double.MIN_VALUE), new Worker(), new HashSet<WorkerWork>());
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
	
	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
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
