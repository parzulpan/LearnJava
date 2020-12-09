package cn.parzulpan.servlet;

import cn.parzulpan.bean.Student;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @Author : parzulpan
 * @Time : 2020-12-09
 * @Desc :
 */

@WebServlet(name = "SearchStudentServlet", urlPatterns = ("/searchStudentServlet"))
public class SearchStudentServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 模拟 DAO：使用 for 循环生成查询到的数据
        ArrayList<Student> students = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            students.add(new Student(i + 1, "name" + i + 1, 18 + i + 1, "男"));
        }

        // 保存查询到的结果到 request 域中
        request.setAttribute("students", students);

        // 请求转发到 ShowStudent.jsp 页面
        request.getRequestDispatcher("/ShowStudent.jsp").forward(request, response);
    }
}
