package com.petromax;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;

import java.io.IOException;
import java.sql.*;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String step = request.getParameter("step");

        if("bunk".equals(step)){
            session.setAttribute("vehicleNo", request.getParameter("vehicleNo"));
            response.sendRedirect("order.jsp?step=bunk");
        }

        else if("fuel".equals(step)){
            session.setAttribute("bunk", request.getParameter("bunk"));
            response.sendRedirect("order.jsp?step=fuel");
        }

        else if("location".equals(step)){
            String fuel = request.getParameter("fuel");
            String qty = request.getParameter("quantity");

            if(fuel == null || qty == null){
                response.sendRedirect("order.jsp?step=fuel");
                return;
            }

            int quantity = Integer.parseInt(qty);
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
        }

        else if("preview".equals(step)){
            session.setAttribute("location", request.getParameter("location"));
            session.setAttribute("mobile", request.getParameter("mobile"));

            try {
                Connection con = DBConnection.getConnection();
                PreparedStatement ps = con.prepareStatement(
                    "SELECT vehicle_type FROM vehicles WHERE username=? AND vehicle_no=?"
                );
                ps.setString(1, (String)session.getAttribute("username"));
                ps.setString(2, (String)session.getAttribute("vehicleNo"));

                ResultSet rs = ps.executeQuery();
                if(rs.next()){
                    session.setAttribute("vehicleType", rs.getString("vehicle_type"));
                }
            } catch(Exception e){
                e.printStackTrace();
            }

            response.sendRedirect("preview.jsp");
        }

        else if("confirm".equals(step)){
            response.sendRedirect("order.jsp?step=done");
        }
    }
}
