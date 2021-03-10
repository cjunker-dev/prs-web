package com.prs.business;

import javax.persistence.*;

@Entity
public class LineItem {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	int id;
	@ManyToOne
	@JoinColumn(name = "requestId")
	Request request;
	@ManyToOne
	@JoinColumn(name = "productId")
	Product product;
	int quantity;
	public LineItem(int id, Request request, Product product, int quantity) {
		super();
		this.id = id;
		this.request = request;
		this.product = product;
		this.quantity = quantity;
	}
	public LineItem() {
		super();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Request getRequest() {
		return request;
	}
	public void setRequest(Request request) {
		this.request = request;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	
}
