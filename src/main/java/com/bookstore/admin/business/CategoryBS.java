package com.bookstore.admin.business;



import com.bookstore.dao.CategoryDAO;
import com.bookstore.entity.Category;

import java.util.List;

public class CategoryBS {
    public static String CheckNameAvailable(Category category) {
        List<Category> foundedList = CategoryDAO.findByName(category.getName());
        if (foundedList.size() >= 1) {
            return "<b>Danh mục này đã tồn tại</b>! Vui lòng nhập tên khác";
        }
        return "OK";
    }
}
