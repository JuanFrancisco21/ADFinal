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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;


@Entity
@Table(name = "workerWork")
public class WorkerWork implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@ApiModelProperty(position = 0, name = "Id", notes = "Identificador del Worker_Work", required = true, value = "1")
	private Long id;
	
	@Column(name = "current")
	@ApiModelProperty(position = 1, name = "Current", notes = "Estar asignada como activo un trabajador en la obra", required = true, value = "true")
	private Boolean current;
	
	@JsonIgnoreProperties(value = { "workerWork" })
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "idWorker")
	@ApiModelProperty(position = 2, name = "Worker", notes = "Worker el cual se va a relacionar", required = true, value = "Objeto worker")
	private Worker worker;

	@JsonIgnoreProperties(value = { "workerWork" })
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "idWork")
	@ApiModelProperty(position = 3, name = "Work", notes = "Work el cual se va a relacionar", required = true, value = "Objeto work")
	private Work work;

	@JsonIgnoreProperties(value = { "worker_work" })
	@OneToMany(mappedBy = "worker_work", fetch = FetchType.LAZY, orphanRemoval = true)
	@ApiModelProperty(position = 4, name = "Worker_Work", notes = "Relacion entre trabajador y la obra", required = true, value = "Objeto worker_work")
	private Set<Dailylog> dailyLogList;

	public WorkerWork(Worker worker, Work work, Boolean current, Set<Dailylog> dailylog) {
		this.id = -1L;
		this.worker = worker;
		this.work = work;
		this.current = current;
		this.dailyLogList = dailylog;
	}

	public WorkerWork() {
		this(new Worker(), new Work(), Boolean.FALSE, new  HashSet<Dailylog>());
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Worker getWorker() {
		return worker;
	}
	public void setWorker(Worker worker) {
		this.worker = worker;
	}

	public Work getWork() {
		return work;
	}
	public void setWork(Work work) {
		this.work = work;
	}

	public Boolean getCurrent() {
		return current;
	}
	public void setCurrent(Boolean current) {
		this.current = current;
	}

	public Set<Dailylog> getDailyLogList() {
		return dailyLogList;
	}

	public void setDailyLogList(Set<Dailylog> dailyLogList) {
		this.dailyLogList = dailyLogList;
	}
	

}
