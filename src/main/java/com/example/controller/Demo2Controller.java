package com.example.controller;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.domain.Orders;
import com.example.service.OrderService;

@RestController
@RequestMapping(value = "/order")
public class Demo2Controller {

	@Autowired
	OrderService orderService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)

	ResponseEntity<Object> createOrder(@RequestBody @Valid Orders order, BindingResult result) {
		Map<String, String> map = new HashMap<String, String>();
		if (result.hasErrors()) {
			return new ResponseEntity(result.getAllErrors(), HttpStatus.BAD_REQUEST);

		}
		boolean success = orderService.createOrder(order);
		if (success) {
			map.put("isSuccess", "true");
			return new ResponseEntity(map, HttpStatus.OK);

		} else {
			map.put("isSuccess", "false");
			return new ResponseEntity(map, HttpStatus.BAD_REQUEST);

		}

	}

	@RequestMapping(value = "/getAll", method = RequestMethod.GET)

	ResponseEntity<Object> getOrders() {
		int i = 0;
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> ord = new LinkedHashMap<String, String>();
		Map<String, String> ord1 = new HashMap<String, String>();
		List<Orders> order = orderService.getAllOrders();
		map.put("isSuccess", "true");
		if (!order.equals(null)) {
			for (Orders o : order) {
				ord.put("id", Integer.toString(o.getId()));
				ord.put("description", o.getDescription());
				ord.put("orderTotal", o.getOrderTotal().toString());
				ord.put("currency", o.getCurrency());
				if (o.getOrderStatusId() == 0) {
					ord.put("orderStatus", "DRAFT");
				} else if (o.getOrderStatusId() == 1) {
					ord.put("orderStatus", "READY");
				} else if (o.getOrderStatusId() == 2) {
					ord.put("orderStatus", "INPROGRESS");
				} else if (o.getOrderStatusId() == 3) {
					ord.put("orderStatus", "COMPLETED");
				} else if (o.getOrderStatusId() == 4) {
					ord.put("orderStatus", "OVERDUE");
				}

				ord.put("dueDate", o.getDueDate().toString());
				ord1.put(Integer.toString(++i), ord.toString());
			}
		}
		map.put("orders", ord1.toString());
		return new ResponseEntity(map, HttpStatus.OK);
	}

	@RequestMapping(value = "/changeStatus", method = RequestMethod.POST)
	ResponseEntity<Object> changeOrderStatus(@RequestParam int orderId) {
		Map<String, String> map = new HashMap<String, String>();
		boolean success = false;
		Orders order = orderService.getOrderById(orderId);
		if (!order.equals(null)) {
			if (order.getOrderStatusId() < 4) {
				order.setOrderStatusId(order.getOrderStatusId() + 1);
				success = orderService.updateOrder(order);

			}
		}
		if (success) {
			map.put("isSuccess", "true");
			return new ResponseEntity(map, HttpStatus.OK);
		} else {
			map.put("isSuccess", "false");
			return new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/overDue", method = RequestMethod.GET)
	ResponseEntity<Object> getOverDueOrders() {
		int i = 0;
		List<Orders> order = orderService.getAllOrders();
		List<Orders> overDueOrders = orderService.getOverDueOrders(order);
		Map<String, String> map = new HashMap<String, String>();
		Map<String, String> ord = new LinkedHashMap<String, String>();
		Map<String, String> ord1 = new HashMap<String, String>();

		if (!overDueOrders.equals(null)) {

			for (Orders o : overDueOrders) {
				ord.put("id", Integer.toString(o.getId()));
				ord.put("description", o.getDescription());
				ord.put("orderTotal", o.getOrderTotal().toString());
				ord.put("currency", o.getCurrency());
				if (o.getOrderStatusId() == 0) {
					ord.put("orderStatus", "DRAFT");
				} else if (o.getOrderStatusId() == 1) {
					ord.put("orderStatus", "READY");
				} else if (o.getOrderStatusId() == 2) {
					ord.put("orderStatus", "INPROGRESS");
				} else if (o.getOrderStatusId() == 3) {
					ord.put("orderStatus", "COMPLETED");
				} else if (o.getOrderStatusId() == 4) {
					ord.put("orderStatus", "OVERDUE");
				}

				ord.put("dueDate", o.getDueDate().toString());
				ord1.put(Integer.toString(++i), ord.toString());
			}
			map.put("orders", ord1.toString());
			return new ResponseEntity(map, HttpStatus.OK);

		} else {
			map.put("isSuccess", "false");
			return new ResponseEntity(map, HttpStatus.BAD_REQUEST);
		}
	}
}