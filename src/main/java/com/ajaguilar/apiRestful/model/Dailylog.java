package com.ajaguilar.apiRestful.model;

import java.io.Serializable;
import java.util.Date;

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
@Table(name="dailylog")
public class Dailylog implements Serializable{


private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;
    @Column(name="date")
    private Date date;
    @Column(name="hours")
    private float hours;
    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name="idWorkerwork")
    private WorkerWork worker_work;

    public Dailylog(Date date,float hours, WorkerWork worker_work) {
    	this.id = -1L;
        this.date = date;
        this.hours = hours;
        this.worker_work = worker_work;
    }
    
    public Dailylog() {
        this(null, 0f, new WorkerWork());
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

    public WorkerWork getWorkerWork(){
        return worker_work;
    }
    
    public void setWorkerWork(WorkerWork ww){
        this.worker_work = ww;
    }
}

