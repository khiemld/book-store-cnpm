package com.bookstore.admin.service;

import com.bookstore.entity.Order;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
public class OrderService {
    public static List<OrderStatusService> statusList() {
        List<OrderStatusService> statuses = new ArrayList<>();
        OrderStatusService st1 = new OrderStatusService(1, "Chờ xác nhận");
        OrderStatusService st2 = new OrderStatusService(2, "Chờ lấy hàng");
        OrderStatusService st3 = new OrderStatusService(3, "Đang giao");
        OrderStatusService st4 = new OrderStatusService(4, "Đã giao");
        OrderStatusService st5 = new OrderStatusService(5, "Đã hủy");
        statuses.add(st1);
        statuses.add(st2);
        statuses.add(st3);
        statuses.add(st4);
        statuses.add(st5);
        return statuses;
    }

    public static Date estimateReceiveDate(Order order) {
        Date dt = order.getCreateTime();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);
        int delivery = order.getIdDelivery();
        int deleveryTime = 0;
        switch (delivery) {
            case 1:
                deleveryTime = 3;
                break;
            case 2:
                deleveryTime = 2;
                break;
            default:
                deleveryTime = 3;
                break;
        }
        calendar.add(Calendar.DATE, deleveryTime);
        String newYear = String.valueOf(calendar.get(Calendar.YEAR));
        String newMonth = String.valueOf(calendar.get(Calendar.MONTH)+1);
        String newDate = String.valueOf(calendar.get(Calendar.DATE));
        String receiveDate = newYear + "-" + newMonth + "-" + newDate;
        Date newdt = Date.valueOf(receiveDate);
        return newdt;
    }

    public static OrderStatusService findStatusByID(int id) {
        OrderStatusService selectedStatus = null;
        for (OrderStatusService status : statusList()) {
            if (status.getId() == id) {
                selectedStatus = status;
            }
        }
        return selectedStatus;
    }

}
