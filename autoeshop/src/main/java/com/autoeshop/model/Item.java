package com.autoeshop.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="Items")
@EntityListeners(AuditingEntityListener.class)
public class Item implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String provider;
	private double price;
	private int quantity;
	
	
	@OneToMany(mappedBy="item")
	private Set<Autobrand> brands = new HashSet<>();

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

	public String getProvider() {
		return provider;
	}

	public void setProvider(String provider) {
		this.provider = provider;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	
	public Set<Autobrand> getBrands() {
		return brands;
	}

	public void setBrands(Set<Autobrand> brands) {
		this.brands = brands;
	}
	
	@Override
	public String toString() {
		String result = String.format(
				"Item[id=%d, name='%s', provider='%s', price='%.2f', quantity='%d']%n", id, name, provider, price, quantity);
		if (brands != null) {
			for (Autobrand brand : brands) {
				result += String.format("Brand[id=%d, name='%s', model='%s', year='%d']%n", 
						brand.getBrandid(), brand.getName(), brand.getModel(),
						brand.getYear());
			}
		}
		return result;
	}
	
	
}
