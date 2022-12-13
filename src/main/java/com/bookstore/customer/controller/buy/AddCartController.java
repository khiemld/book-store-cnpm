package com.bookstore.customer.controller.buy;

import com.bookstore.dao.CartItemDAO;
import com.bookstore.dao.ProductDAO;
import com.bookstore.entity.CartItem;
import com.bookstore.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddCardControl", value = "/cart")
public class AddCartController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       /* request.setCharacterEncoding("UTF-8");
        int idUser = Integer.parseInt(request.getParameter("uid"));
        int idP = Integer.parseInt(request.getParameter("pid"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        ProductDAO productDAO = new ProductDAO();
        CartItemDAO cartItemDAO = new CartItemDAO();

        int price = productDAO.getSalePrice(idP);

        CartItem cartItem = new CartItem(idUser, idP, quantity, price);
        cartItemDAO.addCartItem(cartItem);

        request.setAttribute("uid", idUser);
        request.getRequestDispatcher("showCart").forward(request, response);*/
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html; charset=UTF8");
        request.setCharacterEncoding("UTF-8");
        int idUser = Integer.parseInt(request.getParameter("uid"));
        int idP = Integer.parseInt(request.getParameter("pid"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        ProductDAO productDAO = new ProductDAO();
        CartItemDAO cartItemDAO = new CartItemDAO();

        Product product = productDAO.getProductByID(idP);
        if(product.getQuantity() < 1){
            request.setAttribute("err", "Sản phẩm hiện tại hết hàng");
            request.setAttribute("pid", idP);
            request.getRequestDispatcher("detail").forward(request, response);
        }
        else{
            int price = productDAO.getSalePrice(idP);
            CartItem cartItem = new CartItem(idUser, idP, quantity, price);
            cartItemDAO.addCartItem(cartItem);

            request.setAttribute("uid", idUser);
            request.getRequestDispatcher("showCart").forward(request, response);
        }

    }
}
