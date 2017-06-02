<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="utf-8"%>
<%@ page import="fcu.selab.taxibar.service.CommentService, fcu.selab.taxibar.service.UserService" %>
<%@ page import="fcu.selab.taxibar.data.Comment" %>
<%@ page import="java.util.List" %>
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
<title>TaxiBar</title>
<style>
	body{
		font-family:"MyriadPro","sourcehansans-tc","Microsoft JhengHei"
	}
	.h1, .h2, .h3, h1, h2, h3 {
		margin-top: 0;
	}
	.rating-sm {
		font-size: 1em;
	}
	#content {
		resize: none;
	}
	label{
		font-size: 16px;
		margin-top: 10px;
	}
</style>
</head>
<body>
<%
	String plateNumber = (String) session.getAttribute("plateNumber");
	CommentService commentService = new CommentService();
	UserService userService = new UserService();
	List<Comment> lsComments = commentService.getCommentByDriverId(1);
%>
<nav class="navbar navbar-toggleable-md navbar-light bg-faded" style="background-color: #e3f2fd">
  <button class="navbar-toggler navbar-toggler-right" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <strong><a class="navbar-brand" href="#page-top" style="font-size: 24px; padding-left: 20px">TaxiBar</a></strong>
</nav>
<div class="container">
	<div class="row">
		<div class="col-sm-12">
			<div class="card">
  				<h3 class="card-header">新增評論</h3>
  				<div class="card-block">
    				<div class="form-group">
						<form id="comment">
							<strong><label for="plateNumber">車牌號碼：</label></strong>
							<br>
							<input value="<%=plateNumber %>" type="text" id="plateNumber" name="plateNumber">
							<br>
							<strong><label for="title">主旨：</label></strong>
							<br>
							<input type="text" id="title" name="title">
							<br>
							<strong><label for="content">內容：</label></strong>
							<br>
							<textarea id="content" name="content" rows="5" cols="50"></textarea>
							<br>
							<strong><label>評分：</label></strong>
							<br>
							<div class="rating-container rating-sm rating-animate">
								<div class="rating-stars">
									<input id="rate" name="rate" class="rating rating-loading" data-show-clear="false" data-show-caption="false">
								</div>
							</div>
							<br>
							<button type="submit" class="btn btn-default">送出</button>
						</form>
					</div>
  				</div>
			</div>
		</div>
    </div>
 </div>
</body>
<script>
	$(document).ready(function() {
		$("form").submit(function(evt) {
			evt.preventDefault();
			var formData = $(this).serialize();
			console.log(formData);
			$.ajax({
				url : 'webapi/comment/add',
				type : 'POST',
				data : formData,
				async : false,
				cache : false,
				contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
				processData : false,
				success : function(response) {
					alert("Successed!");
					top.location.href = "../taxibar/GiveComment.jsp";
				}, 
				error : function(response) {
					alert("failed!");
				}
			});
			return false;
		});
	});
</script>
</html>