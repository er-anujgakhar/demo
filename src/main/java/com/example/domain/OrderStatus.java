package com.example.domain;

public enum OrderStatus { 
	
	DRAFT(0),READY(1),INPROGRESS(2),COMPLETED(3),OVERDUE(4);
	
		private final int value;

    OrderStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static OrderStatus getOrderStatus(int orderStatusId) {
        for (OrderStatus os : OrderStatus.values()) {
            if (orderStatusId == os.value) {
                return os;
            }
        }
        return null;
    }

}
