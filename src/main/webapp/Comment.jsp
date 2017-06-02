<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="utf-8"%>
<%@ page import="fcu.selab.taxibar.service.CommentService, fcu.selab.taxibar.service.UserService, fcu.selab.taxibar.service.DriverService" %>
<%@ page import="fcu.selab.taxibar.data.Comment" %>
<%@ page import="java.util.List, java.util.Collections" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css" integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ" crossorigin="anonymous">
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js" integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb" crossorigin="anonymous"></script>
	<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js" integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn" crossorigin="anonymous"></script>
	
	<link href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
    <link href="./css/star-rating.css" media="all" rel="stylesheet" type="text/css">
    <script src="./js/star-rating.js" type="text/javascript"></script>
	<link rel="stylesheet" href="//netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css">
<title>TaxiBar</title>
<style>
	body{
		font-family:"MyriadPro","sourcehansans-tc","Microsoft JhengHei"
	}
	.h1, .h2, .h3, h1, h2, h3 {
		margin-top: 0;
	}
	.card-subtitle{
		margin-top: 1em;
	}
	p {
		font-size: 16px;
	}
	#time {
		margin-bottom: 0px;
	}
	#comment {
		margin-top: 20px;
	}
</style>
</head>
<body>
<%
	String plateNumber = request.getParameter("plateNumber");
	session.putValue("plateNumber", plateNumber);

	CommentService commentService = new CommentService();
	UserService userService = new UserService();
	DriverService driverService = new DriverService();
	int driverId = driverService.getDriverByPlateNumber(plateNumber).getId();
	
	List<Comment> lsComments = commentService.getCommentByDriverId(driverId);
	Collections.reverse(lsComments);
%>
<nav class="navbar navbar-toggleable-md navbar-light bg-faded" style="background-color: #e3f2fd">
  <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <strong><a class="navbar-brand" href="#page-top" style="font-size: 24px; padding-left: 20px">TaxiBar</a></strong>
</nav>
<div class="container">
	<div class="row">
<%
    	for(Comment comment : lsComments) {
    		float score = comment.getScore()*5;
    		float empty = 5-score;
    		String userName = userService.getUser(comment.getUserId()).getUserName();
    		String newUserName = userName;
    		if(userName.length()>2) {
    			newUserName = userName.substring(0, 3);
    			for(int j=0; j<userName.length()-3; j++) {
    				newUserName  = newUserName + "*";
    			}
    		}
    	%>
    	<div class="col-sm-6">
    		<div class="card" id="comment">
  				<div class="card-block">
    				<h3 class="card-title"><strong><%=comment.getTitle() %></strong></h3>
    				<input value=<%=score %> id="rate" name="rate" class="rating rating-loading" data-show-clear="false" data-show-caption="false" data-readonly="true">
    				<h4 class="card-subtitle mb-2 text-muted">使用者： <%=newUserName %></h4>
    				<p class="card-text">內容： <%=comment.getComment() %></p>
    				<p id="time" class="text-right"> <%=comment.getCommentTime().substring(0, 19) %></p>
  				</div>
			</div>
		</div>
    	<%
    	}
    %>
    </div>
 </div>
</body>
</html>