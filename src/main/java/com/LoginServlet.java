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

        try {
            Connection con = DBConnection.getConnection();

            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM users WHERE username=? AND password=?"
            );

            ps.setString(1, username);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
              
                HttpSession session = request.getSession();
                session.setAttribute("username", username);

              
                response.sendRedirect("order.jsp");

            } else {
              
                response.setContentType("text/html");
                response.getWriter().println(
                    "<h3>Invalid Credentials</h3><a href='login.html'>Try Again</a>"
                );
            }

        } catch (Exception e) {
            e.printStackTrace();
            response.getWriter().println("Database Error");
        }
    }
}
