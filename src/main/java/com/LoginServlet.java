package com.petromax;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try (Connection con = DBUtil.getConnection()) {

            String sql = "SELECT username FROM users WHERE username = ? AND password = ?";
            PreparedStatement ps = con.prepareStatement(sql);

            ps.setString(1, username.trim());
            ps.setString(2, password.trim());

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                HttpSession session = request.getSession();
                session.setAttribute("username", username);

                response.sendRedirect("order.jsp");
            } else {
                response.setContentType("text/html");
                response.getWriter().println(
                    "<h3 style='color:red'>Login failed. Please try again.</h3>" +
                    "<a href='login.html'>Back to Login</a>"
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Database error");
        }
    }
}
