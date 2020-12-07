package cn.parzulpan.servlet2;

        import javax.servlet.RequestDispatcher;
        import javax.servlet.ServletException;
        import javax.servlet.annotation.WebServlet;
        import javax.servlet.http.HttpServlet;
        import javax.servlet.http.HttpServletRequest;
        import javax.servlet.http.HttpServletResponse;
        import java.io.IOException;

/**
 * @Author : parzulpan
 * @Time : 2020-12-07
 * @Desc : 请求转发，Servlet1，输入 http://localhost:8080/Servlet/forwardServlet1?username=parzulpan
 */

@WebServlet(name = "ForwardServlet1", urlPatterns = ("/forwardServlet1"))
public class ForwardServlet1 extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求的参数（材料）
        String username = request.getParameter("username");
        System.out.println("ForwardServlet1: " + username); // ForwardServlet2: parzulpan

        // 给材料盖章并转发
        request.setAttribute("key1", "ForwardServlet1 doGet()");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/forwardServlet2");
        requestDispatcher.forward(request, response);
    }
}
