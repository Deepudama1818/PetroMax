<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String step = request.getParameter("step");
    if (step == null || step.isEmpty()) {
        step = "vehicle";
    }
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>PetroMax Order</title>

<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">

<style>
* { box-sizing:border-box; font-family:'Poppins',sans-serif; }

body{
    margin:0;
    height:100vh;
    background:linear-gradient(135deg,#0f2027,#203a43,#2c5364);
    display:flex;
    justify-content:center;
    align-items:center;
    color:white;
}

.container{
    background:#111827;
    width:430px;
    padding:35px;
    border-radius:18px;
    box-shadow:0 25px 60px rgba(0,0,0,0.6);
}

h2{text-align:center;margin-bottom:25px;}

label{display:block;margin-top:14px;font-size:14px;color:#9ca3af;}

input,select{
    width:100%;
    padding:12px;
    border-radius:8px;
    border:none;
    background:#1f2933;
    color:white;
}

.fuel-row{
    display:flex;
    justify-content:space-between;
    margin-top:10px;
}

.radio{
    display:flex;
    align-items:center;
    gap:6px;
}

.summary{
    background:#1f2933;
    padding:15px;
    border-radius:12px;
    margin-top:15px;
    font-size:14px;
}

button{
    width:100%;
    margin-top:25px;
    padding:14px;
    border-radius:10px;
    border:none;
    background:linear-gradient(135deg,#f97316,#f43f5e);
    color:white;
    font-weight:600;
    cursor:pointer;
}
</style>

<script>
function onlyNumbers(input){
    input.value = input.value.replace(/[^0-9]/g,'');
}
</script>

</head>

<body>
<div class="container">

<h2>PetroMax Fuel Delivery</h2>

<!-- VEHICLE -->
<% if("vehicle".equals(step)) { %>
<form action="order" method="post">
<input type="hidden" name="step" value="bunk">
<label>Vehicle Number</label>
<input type="text" name="vehicleNo" required>
<button>Next</button>
</form>

<!-- BUNK -->
<% } else if("bunk".equals(step)) { %>
<form action="order" method="post">
<input type="hidden" name="step" value="fuel">
<label>Petrol Bunk</label>
<select name="bunk" required>
<option value="">Select</option>
<option>HP</option>
<option>Bharat Petroleum</option>
<option>Essar</option>
<option>Nayara</option>
</select>
<button>Next</button>
</form>

<!-- FUEL -->
<% } else if("fuel".equals(step)) { %>
<form action="order" method="post">
<input type="hidden" name="step" value="location">

<label>Fuel Type</label>
<div class="fuel-row">
<label class="radio"><input type="radio" name="fuel" value="Petrol" required> Petrol</label>
<label class="radio"><input type="radio" name="fuel" value="Diesel"> Diesel</label>
</div>

<label>Quantity (Litres)</label>
<select name="quantity" required>
<option value="">Select</option>
<option value="1">1 L</option>
<option value="2">2 L</option>
<option value="3">3 L</option>
<option value="4">4 L</option>
<option value="5">5 L</option>
</select>

<button>Next</button>
</form>

<!-- LOCATION -->
<% } else if("location".equals(step)) { %>
<form action="order" method="post">
<input type="hidden" name="step" value="preview">

<label>Mobile Number</label>
<input type="text" name="mobile" maxlength="10" oninput="onlyNumbers(this)" required>

<label>Delivery Location</label>
<input type="text" name="location" required>

<button>Preview Order</button>
</form>

<!-- PREVIEW -->
<% } else if("preview".equals(step)) { %>

<div class="summary">
<p><strong>Username:</strong> <%= session.getAttribute("username") %></p>
<p><strong>Vehicle No:</strong> <%= session.getAttribute("vehicleNo") %></p>
<p><strong>Vehicle Type:</strong> <%= session.getAttribute("vehicleType") %></p>
<p><strong>Bunk:</strong> <%= session.getAttribute("bunk") %></p>
<p><strong>Fuel:</strong> <%= session.getAttribute("fuel") %></p>
<p><strong>Quantity:</strong> <%= session.getAttribute("quantity") %> L</p>
<p><strong>Fuel Cost:</strong> ₹<%= session.getAttribute("fuelCost") %></p>
<p><strong>Delivery:</strong> ₹<%= session.getAttribute("deliveryCharge") %></p>
<p><strong>Tax:</strong> ₹<%= session.getAttribute("tax") %></p>
<p><strong>Total:</strong> ₹<%= session.getAttribute("totalAmount") %></p>
</div>

<form action="order" method="post">
<input type="hidden" name="step" value="confirm">
<button>Confirm Order</button>
</form>

<!-- DONE -->
<% } else if("done".equals(step)) { %>

<h3 style="text-align:center;">Order Confirmed</h3>
<p style="text-align:center;">Rider is arriving soon</p>

<div class="summary">
<p><strong>Driver:</strong> Ramesh Kumar</p>
<p><strong>Rating:</strong> 4.6 / 5</p>
<p><strong>Mobile:</strong> +91 7670867303</p>
<p><strong>OTP:</strong> 6412</p>
</div>

<% } %>

</div>
</body>
</html>
