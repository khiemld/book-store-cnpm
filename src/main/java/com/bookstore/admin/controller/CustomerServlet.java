package com.bookstore.admin.controller;

import com.bookstore.admin.service.CustomerService;
import com.bookstore.dao.UserDAO;
import com.bookstore.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CustomerServlet", value = {"/admin/customer"})
public class CustomerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

//      Thêm tiếng việt
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        List<User> userList = CustomerService.getAll();
        request.setAttribute("customerList", userList);

        User bestCustomerByPrice= CustomerService.BestCustomerByPrice();
        request.setAttribute("bestCustomerByPrice", bestCustomerByPrice);

        User BestCustomerByBooks= CustomerService.BestCustomerByBooks();
        request.setAttribute("BestCustomerByBooks", BestCustomerByBooks);
        // Lấy action của người dùng
        String action = request.getParameter("action");
        if (action == null) {
            action = new String("home");
        }
        switch (action) {
            case "insert":
                actionInsert(request, response);
                break;
            case "edit":
                actionEdit(request, response);
                break;
            case "save":
                actionSave(request, response);
                break;
            case "home":
//                actionHome(request, response);
                request.getRequestDispatcher("/admin/customer.jsp").forward(request, response);
                break;
            default:
                request.getRequestDispatcher("/admin/customer.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<User> userList = CustomerService.getAll();
        request.setAttribute("customerList", userList);
//      Thêm tiếng việt
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        // Lấy action của người dùng
        String action = request.getParameter("action");
        if (action == null) {
            action = new String("home");
        }
        switch (action) {
            case "insert":
                actionInsert(request, response);
                break;
            case "edit":
                actionEdit(request, response);
                break;
            case "save":
                actionSave(request, response);
                break;
            case "home":
//                actionHome(request, response);
                request.getRequestDispatcher("/admin/customer.jsp").forward(request, response);
                break;
            default:
                request.getRequestDispatcher("/admin/customer.jsp").forward(request, response);
                break;
        }
    }

    protected void actionInsert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        request.setAttribute("action", "insert");
        request.getRequestDispatcher("/admin/customer-form.jsp").forward(request, response);
    }

    protected void actionEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        request.setAttribute("action", "edit");
        String eID = request.getParameter("customerID");
//        Lấy employee có id tương ứng ra
        User selectedCustomer = UserDAO.find(Integer.parseInt(eID));
        request.setAttribute("customer", selectedCustomer);
        request.getRequestDispatcher("/admin/customer-form.jsp").forward(request, response);
    }

    protected void actionSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String cID = request.getParameter("cID");
        String cName = request.getParameter("customerName");
        String cAddress = request.getParameter("customerAddress");
        String cEmail = request.getParameter("customerEmail");
        String cPhone = request.getParameter("customerPhone");
        String cPassword = request.getParameter("customerPassword");
        String cImageLink = request.getParameter("customerImageLink");


//        Kiểm tra người dùng có nhập dữ liệu vào hay chưa
        if (!cName.trim().equals("") && !cAddress.trim().equals("") && !cEmail.trim().equals("") && !cPhone.trim().equals("")) {
//            Thực hiện kiểm tra dữ liệu vào

//            Tạo một đối tượng Product để lưu dữ liệu
            User user = new User();
            if (!cID.equals("")) {
                user.setId(Integer.parseInt(cID));
            }
            user.setName(cName);
            user.setAddress(cAddress);
            user.setEmail(cEmail);
            user.setPhone(cPhone);
            user.setPassword(cPassword);
            user.setImage(cImageLink);
            user.setIsRole(3);

//            Kiểm tra dữ liệu đầu vào đang được lưu trong book
//            Nêu dữ liệu chưa hợp lệ
            if (!"OK".equals(CustomerService.CheckInputData(user))) {
                request.setAttribute("customer", user);
                request.setAttribute("action", "insert");
                request.setAttribute("message", CustomerService.CheckInputData(user));
                request.getRequestDispatcher("/admin/customer-form.jsp").forward(request, response);
            }
//            Nếu dữ liệu hợp lệ
            else {
//              Kiểm tra xem có ID chưa, nếu chưa là thêm mới, nếu có là cập nhật
//              Đang thêm nhân viên mới
                if (user.getId() == 0) {
                    request.setAttribute("action", "insert");
                    request.setAttribute("customer", user);
//                  Kiểm tra xem một số thông tin phải là unique
//                  Nếu số điện thoại đã tồn tại
                    if (!"OK".equals(CustomerService.CheckPhoneAvailable(user))) {
                        request.setAttribute("message", CustomerService.CheckPhoneAvailable(user));
                        request.getRequestDispatcher("/admin/customer-form.jsp").forward(request, response);
                    }
//                    Nếu email đã tồn tại
                    else if(!"OK".equals(CustomerService.CheckEmailAvailable(user))){
                        request.setAttribute("message", CustomerService.CheckPhoneAvailable(user));
                        request.getRequestDispatcher("/admin/customer-form.jsp").forward(request, response);
                    }
//                  Nếu email và điện thoại là mới
                    else {
                        UserDAO.save(user);
                        String message = new String("Vừa thêm khách hàng <b>" + user.getName() + "</b>");
                        request.setAttribute("message", message);
                        List<User> customerList = CustomerService.getAll();
                        request.setAttribute("customerList", customerList);
                        request.getRequestDispatcher("/admin/customer.jsp").forward(request, response);
                    }
                }
//                Đang cập nhật sách
                else {
                    String message = new String("Vừa xem thông tin khách hàng <b>" + user.getName() + "</b>");
                    request.setAttribute("message", message);
                    List<User> customerList = CustomerService.getAll();
                    request.setAttribute("customerList", customerList);
                    request.getRequestDispatcher("/admin/customer.jsp").forward(request, response);
                }
            }
        }
    }
}
