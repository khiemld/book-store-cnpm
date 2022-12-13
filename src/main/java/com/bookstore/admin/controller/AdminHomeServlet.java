package com.bookstore.admin.controller;

import com.bookstore.admin.service.CustomerService;
import com.bookstore.admin.service.EmployeeService;
import com.bookstore.admin.service.ProductService;
import com.bookstore.dao.OrderDAO;
import com.bookstore.dao.ProductDAO;
import  com.bookstore.entity.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminHomeServlet", value = "/admin")
public class AdminHomeServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //      Thêm tiếng việt
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        List<Product> bookList = ProductDAO.getAll();
        request.setAttribute("bookList", bookList);

        List<User> userList = CustomerService.getAll();
        request.setAttribute("customerList", userList);

        List<User> employeeList = EmployeeService.getAll();
        request.setAttribute("employeeList", employeeList);

        List<Order> orderList = OrderDAO.get5LastestOrder();
        request.setAttribute("orderList", orderList);

        List<Product> book5List = ProductDAO.get5LastestProduct();
        request.setAttribute("book5List", book5List);

        Product bestSellerBook= ProductService.BestSellerBook();
        request.setAttribute("bestSellerBook", bestSellerBook);
        // Tổng số bán trong ngày
        // Thu nhập trong tháng
        // Khách hàng trong năm
        // Top selling
        request.getRequestDispatcher("/admin/index.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
