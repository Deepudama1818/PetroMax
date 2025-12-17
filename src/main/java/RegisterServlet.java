package com.petromax;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String vehicleNo = request.getParameter("vehicleNo");
        String vehicleType = request.getParameter("vehicleType");

        // 🔥 DEBUG PRINTS
        System.out.println("REGISTER DATA:");
        System.out.println("username = " + username);
        System.out.println("vehicleNo = " + vehicleNo);
        System.out.println("vehicleType = " + vehicleType);

        try (Connection con = DBUtil.getConnection()) {

            // Insert user
            PreparedStatement userPS = con.prepareStatement(
                "INSERT INTO users(username, password) VALUES (?, ?)"
            );
            userPS.setString(1, username);
            userPS.setString(2, password);
            userPS.executeUpdate();

            System.out.println("USER INSERTED");

            // Insert vehicle
            PreparedStatement vehiclePS = con.prepareStatement(
                "INSERT INTO vehicles(username, vehicle_no, vehicle_type) VALUES (?, ?, ?)"
            );
            vehiclePS.setString(1, username);
            vehiclePS.setString(2, vehicleNo);
            vehiclePS.setString(3, vehicleType);

            vehiclePS.executeUpdate();

            System.out.println("VEHICLE INSERTED");

            response.sendRedirect("login.html?msg=registered");

        } catch (Exception e) {
            e.printStackTrace(); 
            response.getWriter().println("Registration Failed");
        }
    }
}
