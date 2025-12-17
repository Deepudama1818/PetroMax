package com.petromax;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String vehicleNo = request.getParameter("vehicleNo");
        String vehicleType = request.getParameter("vehicleType");

        try {
            Connection con = DBConnection.getConnection();

            
            PreparedStatement checkUser = con.prepareStatement(
                "SELECT username FROM users WHERE username=?"
            );
            checkUser.setString(1, username);
            ResultSet rs = checkUser.executeQuery();

            if (rs.next()) {
              
                response.sendRedirect("login.html?msg=error");
                return;
            }

           
            PreparedStatement userPS = con.prepareStatement(
                "INSERT INTO users(username, password) VALUES (?,?)"
            );
            userPS.setString(1, username);
            userPS.setString(2, password);
            userPS.executeUpdate();

        
            PreparedStatement vehiclePS = con.prepareStatement(
                "INSERT INTO vehicles(username, vehicle_no, vehicle_type) VALUES (?,?,?)"
            );
            vehiclePS.setString(1, username);
            vehiclePS.setString(2, vehicleNo);
            vehiclePS.setString(3, vehicleType);
            vehiclePS.executeUpdate();

       
            response.sendRedirect("login.html?msg=registered");

        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("login.html?msg=error");
        }
    }
}
