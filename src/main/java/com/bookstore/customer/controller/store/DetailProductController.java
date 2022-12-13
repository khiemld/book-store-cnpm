package com.bookstore.customer.controller.store;

import com.bookstore.dao.ProductDAO;
import com.bookstore.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet(name = "DetailProductController", value = "/detail")
public class DetailProductController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String id = request.getParameter("pid");
        ProductDAO productDAO = new ProductDAO();
        Product product = productDAO.getProductByID(Integer.parseInt(id));
        request.setAttribute("detail", product);
        request.getRequestDispatcher("/store/views/detailProduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        String id = request.getParameter("pid");
        ProductDAO productDAO = new ProductDAO();
        Product product = productDAO.getProductByID(Integer.parseInt(id));
        request.setAttribute("detail", product);
        request.getRequestDispatcher("/store/views/detailProduct.jsp").forward(request, response);
    }
}
