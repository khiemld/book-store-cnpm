package com.bookstore.admin.controller;



import com.bookstore.admin.service.CategoryService;
import com.bookstore.dao.CategoryDAO;
import com.bookstore.entity.Category;



import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "CategoryServlet", value = "/admin/category")
public class CategoryServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //Thêm tiếng việt
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        List<Category> categoryList = CategoryDAO.getAll();
        request.setAttribute("categoryList", categoryList);

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
                request.getRequestDispatcher("/admin/category.jsp").forward(request, response);
                break;
            default:
                request.getRequestDispatcher("/admin/category.jsp").forward(request, response);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //        Thêm tiếng việt
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        List<Category> categoryList = CategoryDAO.getAll();
        request.setAttribute("categoryList", categoryList);

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
                request.getRequestDispatcher("/admin/category.jsp").forward(request, response);
                break;
            default:
                request.getRequestDispatcher("/admin/category.jsp").forward(request, response);
                break;
        }
    }

    protected void actionInsert(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        request.setAttribute("action", "insert");
        request.getRequestDispatcher("/admin/category-form.jsp").forward(request, response);
    }

    protected void actionEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        request.setAttribute("action", "edit");
        String cID = request.getParameter("categoryID");
//      Lấy employee có id tương ứng ra
        Category selectedCategory = CategoryDAO.findById(Integer.parseInt(cID));
        request.setAttribute("category", selectedCategory);
        request.getRequestDispatcher("/admin/category-form.jsp").forward(request, response);
    }

    protected void actionSave(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");

        String cID = request.getParameter("cID");
        String cName = request.getParameter("categoryName");

//        Kiểm tra người dùng có nhập dữ liệu vào hay chưa
        if (!cName.trim().equals("")) {
//            Thực hiện kiểm tra dữ liệu vào

//            Tạo một đối tượng Product để lưu dữ liệu
            Category category = new Category();
            if (!cID.equals("")) {
                category.setId(Integer.parseInt(cID));
            }
            category.setName(cName);

//          Kiểm tra xem có ID chưa, nếu chưa là thêm mới, nếu có là cập nhật
//          Đang thêm nhân viên mới
            if (category.getId() == 0) {
                request.setAttribute("action", "insert");
                request.setAttribute("category", category);
//                  Kiểm tra xem một số thông tin phải là unique
//              Nếu số tên đã tồn tại
                if (!"OK".equals(CategoryService.CheckNameAvailable(category))) {
                    request.setAttribute("message", CategoryService.CheckNameAvailable(category));
                    request.getRequestDispatcher("/admin/category-form.jsp").forward(request, response);
                }
//              Nếu là tên mới
                else {
                    CategoryDAO.save(category);
                    String message = new String("Vừa thêm danh mục sản phẩm <b>" + category.getName() + "</b>");
                    request.setAttribute("message", message);
                    List<Category> categoryList = CategoryDAO.getAll();
                    request.setAttribute("categoryList", categoryList);
                    request.getRequestDispatcher("/admin/category.jsp").forward(request, response);
                }
            }
//          Đang cập nhật sách
            else {
                String message = new String("Vừa xem thông tin danh mục <b>" + category.getName() + "</b>");
                request.setAttribute("message", message);
                List<Category> categoryList = CategoryDAO.getAll();
                request.setAttribute("categoryList", categoryList);
                request.getRequestDispatcher("/admin/category.jsp").forward(request, response);
            }
        }
    }
}
