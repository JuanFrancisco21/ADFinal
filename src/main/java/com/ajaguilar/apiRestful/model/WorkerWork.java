package com.ajaguilar.apiRestful.model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "workerWork")
public class WorkerWork implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "idWorker")
	private Worker worker;
	@ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@JoinColumn(name = "idWork")
	private Work work;
	@Column(name = "current")
	Boolean current;

	public WorkerWork(Worker worker, Work work, Boolean current) {
		this.id = -1L;
		this.worker = worker;
		this.work = work;
		this.current = current;
	}

	public WorkerWork() {
		this(new Worker(), new Work(), Boolean.FALSE);
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

}
