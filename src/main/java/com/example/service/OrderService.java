package com.example.service;

import java.util.List;

import com.example.domain.Orders;

public interface OrderService {
	public boolean createOrder(Orders orders);

	public List<Orders> getAllOrders();

	public Orders getOrderById(int orderId);

	public boolean updateOrder(Orders order);

	public List<Orders> getOverDueOrders(List<Orders> order);

}
