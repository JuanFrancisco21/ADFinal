package com.ajaguilar.apiRestful.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDate;
import java.sql.Date;

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
@Table(name = "dailylog")
public class Dailylog implements Serializable {

	private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    @ApiModelProperty(position = 0)
    private Long id;
    
    @ApiModelProperty(position = 1)
    @Column(name="date")
    private Date date;
    
    @ApiModelProperty(position = 2)
    @Column(name="hours")
    private float hours;

    @JsonIgnoreProperties(value = { "dailyLogList" })
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "idWorkerwork")
    @ApiModelProperty(position = 3)
    private WorkerWork worker_work;

	public Dailylog(LocalDate date, float hours, WorkerWork worker_work) {
		this.id = -1L;
		this.date = Date.valueOf(date);
		this.hours = hours;
		this.worker_work = worker_work;
	}

	public Dailylog() {
		this(LocalDate.now(), 0f, new WorkerWork());
	}

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}

	public float getHours() {
		return hours;
	}
	public void setHours(float hours) {
		this.hours = hours;
	}

	public WorkerWork getWorkerWork() {
		return worker_work;
	}
	public void setWorkerWork(WorkerWork ww) {
		this.worker_work = ww;
	}
}
