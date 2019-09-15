package com.example.domain;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.example.domain.OrderStatus;

@Entity
public class Orders {
	private int id;
	private Integer orderStatusId;
	private BigDecimal orderTotal;
	private String currency, description;
	private Date dueDate;

	@JsonIgnore
	private OrderStatus orderStatus;

	@Id
	@GeneratedValue
	@Column(name = "Id")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@JsonIgnore
	@Column(name = "OrderStatusId")
	public Integer getOrderStatusId() {
		return orderStatusId;
	}

	public void setOrderStatusId(Integer orderStatusId) {
		this.orderStatusId = orderStatusId;
		this.orderStatus = OrderStatus.getOrderStatus(orderStatusId);
	}

	@NotNull(message = "Please provide a order total")
	@DecimalMin("1.00")
	@Column(name = "OrderTotal")
	public BigDecimal getOrderTotal() {
		return orderTotal;
	}

	public void setOrderTotal(BigDecimal orderTotal) {
		this.orderTotal = orderTotal;
	}

	@NotBlank(message = "Currency is mandatory")
	@Size(min = 1, max = 3, message = "Currency should have atleast 3 characters")
	@Column(name = "Currency")
	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	@NotBlank(message = "Description is mandatory")
	@Column(name = "Description")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@NotNull
	@Column(name = "DueDate")
	public Date getDueDate() {
		return dueDate;
	}

	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}

}
