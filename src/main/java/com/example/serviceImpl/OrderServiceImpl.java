package com.example.serviceImpl;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.domain.OrderStatus;
import com.example.domain.Orders;
import com.example.repository.OrderRepository;
import com.example.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepository;

	public boolean createOrder(Orders orders) {
		orders.setOrderStatusId(OrderStatus.DRAFT.getValue());
		orderRepository.save(orders);
		return true;
	}

	@Override
	public List<Orders> getAllOrders() {

		return orderRepository.findAll();
	}

	@Override
	public Orders getOrderById(int orderId) {
		return orderRepository.findByid(orderId);

	}

	@Override
	public boolean updateOrder(Orders order) {
		orderRepository.save(order);
		return true;
	}

	@Override
	public List<Orders> getOverDueOrders(List<Orders> order) {
		LocalDate now = LocalDate.now();
		List<Orders> ord1 = new ArrayList();
		for (Orders o : order) {
			if (o.getOrderStatusId() == 4) {
				ord1.add(o);
			} else {
				Instant instant = o.getDueDate().toInstant();
				ZoneId defaultZoneId = ZoneId.systemDefault();
				LocalDate dueDate = instant.atZone(defaultZoneId).toLocalDate();
				Period period = Period.between(dueDate, now);
				if (period.getDays() > 7) {
					o.setOrderStatusId(4);
					orderRepository.save(o);
					ord1.add(o);
				}
			}
		}
		return ord1;
	}
}