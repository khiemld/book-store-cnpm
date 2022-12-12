package com.bookstore.customer.controller.buy;

import com.bookstore.dao.CartItemDAO;
import com.bookstore.entity.CartItem;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebServlet(name = "DeleteServlet", value = "/deleteCart")
public class DeleteCartController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /*response.setContentType("text/html");
        String uid = request.getParameter("uid");
        String pid = request.getParameter("pid");

        PrintWriter printWriter = response.getWriter();

        printWriter.println(uid);
        printWriter.println(pid);*/
        int uid = Integer.parseInt(request.getParameter("uid"));
        int pid = Integer.parseInt(request.getParameter("pid"));

        CartItemDAO cartItemDAO = new CartItemDAO();
        CartItem cartItem = cartItemDAO.testCartItem(uid, pid).get(0);

        cartItemDAO.delete(cartItem.getId());

        request.setAttribute("uid", uid);
        request.getRequestDispatcher("showCart").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
