package com.autoeshop.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name="autobrand")
public class Autobrand implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "brandid")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long brandid;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "model")
	private String model;
	
	@Column(name = "year")
	private int year;
	
	@ManyToOne(fetch = FetchType.LAZY, optional=false)
	@JoinColumn(name="itemid")
	@JsonBackReference
	private Item item;

	public Long getBrandid() {
		return brandid;
	}

	public void setBrandid(Long brandid) {
		this.brandid = brandid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
}
