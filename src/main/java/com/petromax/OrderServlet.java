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

@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String step = request.getParameter("step");

        /* ================= VEHICLE STEP ================= */
        if ("bunk".equals(step)) {

            session.setAttribute("vehicleNo", request.getParameter("vehicleNo"));
            response.sendRedirect("order.jsp?step=bunk");
            return;
        }

        /* ================= BUNK STEP ================= */
        if ("fuel".equals(step)) {

            session.setAttribute("bunk", request.getParameter("bunk"));
            response.sendRedirect("order.jsp?step=fuel");
            return;
        }

        /* ================= FUEL + QUANTITY STEP ================= */
        if ("location".equals(step)) {

            String fuel = request.getParameter("fuel");
            String qtyStr = request.getParameter("quantity");

            if (fuel == null || qtyStr == null || qtyStr.isEmpty()) {
                response.sendRedirect("order.jsp?step=fuel");
                return;
            }

            int quantity;
            try {
                quantity = Integer.parseInt(qtyStr);
            } catch (NumberFormatException e) {
                response.sendRedirect("order.jsp?step=fuel");
                return;
            }

            int rate = fuel.equals("Petrol") ? 110 : 90;
            int fuelCost = quantity * rate;
            int delivery = 50;
            int tax = 30;
            int total = fuelCost + delivery + tax;

            session.setAttribute("fuel", fuel);
            session.setAttribute("quantity", quantity);
            session.setAttribute("fuelCost", fuelCost);
            session.setAttribute("deliveryCharge", delivery);
            session.setAttribute("tax", tax);
            session.setAttribute("totalAmount", total);

            response.sendRedirect("order.jsp?step=location");
            return;
        }

        /* ================= PREVIEW STEP ================= */
        if ("preview".equals(step)) {

            String location = request.getParameter("location");
            String mobile = request.getParameter("mobile");

            // mobile validation (10 digits only)
            if (mobile == null || !mobile.matches("\\d{10}")) {
                response.sendRedirect("order.jsp?step=location");
                return;
            }

            session.setAttribute("location", location);
            session.setAttribute("mobile", mobile);

            String username = (String) session.getAttribute("username");
            String vehicleNo = (String) session.getAttribute("vehicleNo");

            // Fetch vehicle type from DB (already stored during registration)
            try (Connection con = DBUtil.getConnection()) {

                PreparedStatement ps = con.prepareStatement(
                        "SELECT vehicle_type FROM vehicles WHERE username = ? AND vehicle_no = ?"
                );
                ps.setString(1, username);
                ps.setString(2, vehicleNo);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    session.setAttribute("vehicleType", rs.getString("vehicle_type"));
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

            response.sendRedirect("preview.jsp");
            return;
        }

        /* ================= CONFIRM STEP ================= */
        if ("confirm".equals(step)) {

            response.sendRedirect("order.jsp?step=done");
        }
    }
}
