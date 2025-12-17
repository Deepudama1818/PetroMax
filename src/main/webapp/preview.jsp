<%@ page language="java" %>
<!DOCTYPE html>
<html>
<head>
<title>PetroMax Preview</title>

<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;600&display=swap" rel="stylesheet">

<style>
body{
margin:0;
height:100vh;
background:linear-gradient(135deg,#020617,#0f172a);
display:flex;
justify-content:center;
align-items:center;
font-family:'Poppins',sans-serif;
color:white;
}

.card{
background:#020617;
width:480px;
padding:30px;
border-radius:18px;
box-shadow:0 30px 70px rgba(0,0,0,0.7);
}

h2{
text-align:center;
color:#38bdf8;
margin-bottom:25px;
}

.row{
display:flex;
justify-content:space-between;
padding:10px 0;
border-bottom:1px solid #1e293b;
}

.total{
color:#22c55e;
font-weight:600;
}

button{
margin-top:25px;
width:100%;
padding:14px;
border:none;
border-radius:12px;
background:#22c55e;
font-size:15px;
font-weight:600;
cursor:pointer;
}
</style>
</head>

<body>
<div class="card">
<h2>PetroMax Order Preview</h2>

<div class="row"><span>User</span><span><%=session.getAttribute("username")%></span></div>
<div class="row"><span>Vehicle No</span><span><%=session.getAttribute("vehicleNo")%></span></div>
<div class="row"><span>Vehicle Type</span><span><%=session.getAttribute("vehicleType")%></span></div>
<div class="row"><span>Fuel</span><span><%=session.getAttribute("fuel")%></span></div>
<div class="row"><span>Quantity</span><span><%=session.getAttribute("quantity")%> L</span></div>

<div class="row"><span>Fuel Cost</span><span>&#8377; <%=session.getAttribute("fuelCost")%></span></div>
<div class="row"><span>Delivery Charge</span><span>&#8377; <%=session.getAttribute("deliveryCharge")%></span></div>
<div class="row"><span>Taxes</span><span>&#8377; <%=session.getAttribute("tax")%></span></div>

<div class="row total"><span>Total Amount</span><span>&#8377; <%=session.getAttribute("totalAmount")%></span></div>

<div class="row"><span>Payment</span><span>Cash On Delivery</span></div>
<div class="row"><span>Location</span><span><%=session.getAttribute("location")%></span></div>

<form action="order" method="post">
<input type="hidden" name="step" value="confirm">
<button>Confirm Order</button>
</form>
</div>
</body>
</html>
