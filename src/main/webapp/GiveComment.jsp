<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page
	import="fcu.selab.taxibar.service.CommentService, fcu.selab.taxibar.service.UserService"%>
<%@ page import="fcu.selab.taxibar.data.Comment"%>
<%@ page import="fcu.selab.taxibar.db.CommentDbManager, fcu.selab.taxibar.db.UserDbManager, 
				 fcu.selab.taxibar.db.DriverDbManager, fcu.selab.taxibar.db.RankDbManager" %>
<%@ page import="java.util.List, java.util.Collections" %>
<%@ page import="java.util.List"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/css/bootstrap.min.css"
	integrity="sha384-rwoIResjU2yc3z8GV/NPeZWAv56rSmLldC3R/AZzGRnGxQQKnKkoFVhFQhNUwEyJ"
	crossorigin="anonymous">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/tether/1.4.0/js/tether.min.js"
	integrity="sha384-DztdAPBWPRXSA/3eYEEUWrWCy7G5KFbe8fFjk5JAIxUYHKkDx6Qin1DkWx51bBrb"
	crossorigin="anonymous"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-alpha.6/js/bootstrap.min.js"
	integrity="sha384-vBWWzlZJ8ea9aCX4pEW3rVHjgjt7zpkNpZk+02D9phzyeVkE+jo0ieGizqPLForn"
	crossorigin="anonymous"></script>

<link
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"
	rel="stylesheet">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css">
<link href="./css/star-rating.css" media="all" rel="stylesheet"
	type="text/css">
<script src="./js/star-rating.js" type="text/javascript"></script>
<title>TaxiBar</title>
<style>
	body { font-family: "MyriadPro", "sourcehansans-tc", "Microsoft JhengHei" }

	.container { margin-top: 30px; margin-bottom: 30px; }

	.h1, .h2, .h3, h1, h2, h3 { margin-top: 0; }
	
	.card { margin-top: 1em; }
	
	.card-subtitle{ margin-top: 1em; }
	
	p { font-size: 14px; }
	
	#time { margin-bottom: 0px; }
	
	span { font-size: 40px; padding-left: 10px;}

	#content { resize: none; }

	label { font-size: 16px; margin-top: 10px; }
	
	.labelGood:before{ background-image: url('img/good.png'); width: 20px; height: 30px; background-size: 20px 30px; display: inline-block; content:""; }
	
	.labelNeutral:before{ background-image: url('img/neutral.png'); width: 20px; height: 30px; background-size: 20px 30px; display: inline-block; content:""; background-position: bottom; }
	
	.labelBad:before{ background-image: url('img/bad.png'); width: 20px; height: 30px; background-size: 20px 30px; display: inline-block; content:""; }
	
	.good:before{ background-image: url('img/good.png'); width: 40px; height: 60px; background-size: 40px 60px; display: inline-block; content:"";  }
	
	.neutral:before{ background-image: url('img/neutral.png'); width: 40px; height: 60px; background-size: 40px 60px; display: inline-block; content:"";  }
	
	.bad:before{ background-image: url('img/bad.png'); width: 40px; height: 60px; background-size: 40px 60px; display: inline-block; content:"";  }

	#rate { font-size: 40px; }

	#rate:hover { border: 1px solid #333; }
	
	#good, #soso, #bad { visibility: hidden; }
	
	#plateNumber{ font-size: 40px; text-align: center; padding-right: 20px;}
</style>
</head>
<body>
	<%
		String plateNumber = request.getParameter("plateNumber");
		String empty = "";
		if (null == plateNumber || empty.equals(plateNumber)) {
			plateNumber = "";
		}
		CommentDbManager commentDbManager = CommentDbManager.getInstance();
		UserDbManager userDbManager = UserDbManager.getInstance();
		DriverDbManager driverDbManager = DriverDbManager.getInstance();
		RankDbManager rankDbManager = RankDbManager.getInstance();
		int driverId = driverDbManager.getDriverByPlateNumber(plateNumber).getId();
		int rank = rankDbManager.getRankByPlateNumber(plateNumber).getScore();
		
		List<Comment> lsComments = commentDbManager.getCommentByDriverId(driverId);
		Collections.reverse(lsComments);
	%>
	<div class="container">
		<div class="row">
			<div class="col-sm-12">
				<div class="form-group" style="border-bottom: 1px solid #D3D3D3; position:relative;">
					<%
    					if(rank == 1) {
    						%>
    						<h1 id="plateNumber"><%=plateNumber %><span class="labelGood" style="position:absolute; bottom: 18px;"></span></h1>
    						<%
    					}else if(rank == 0) {
    						%>
    						<h1 id="plateNumber"><%=plateNumber %><span class="labelNeutral" style="position:absolute; bottom: 18px;"></span></h1>
    						<%
    					}else {
    						%>
    						<h1 id="plateNumber"><%=plateNumber %><span class="labelBad" style="position:absolute; bottom: 18px;"></span></h1>
    						<%
    					}
    				%>
				</div>
				<div class="form-group" style="border-bottom: 1px solid #D3D3D3; padding-bottom: 20px;">
					<form id="comment" method="post" action="webapi/comment/add" enctype="application/x-www-form-urlencoded">
						<strong><label>評分：</label></strong>
						<br>
    					<label id="rate" for="good" class="good"></label>
    					<input type="radio" id="good" name="rate" value=<%=1%>>
    					
						<label id="rate" for="soso" class="neutral"></label>
						<input type="radio" id="soso" name="rate" value=<%=0%>>
						
						<label id="rate" for="bad" class="bad"></label>
						<input type="radio" id="bad" name="rate" value=<%=-1%>>
						
						<input value="<%=plateNumber%>" type="text" id="plateNumber" name="plateNumber" hidden>
						<br>
						<strong><label for="content">內容：</label></strong>
						<br>
						<textarea class="col-sm-12" id="content" name="content" rows="5"></textarea>
						<br>
						<br>
						<button type="submit" class="btn btn-default">送出</button>
					</form>
				</div>
			</div>
		</div>
		<div class="row">
		<h3 class="col-sm-12" style="margin-top: 10px;"><strong>所有評論</strong></h3>
<%
    	for(Comment comment : lsComments) {
    		int score = comment.getScore();
    		String userName = userDbManager.getUserById(comment.getUserId()).getUserName();
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
    				<%
    					if(score == 1) {
    						%>
    						<div style="position:relative;">
    							<h4 class="card-title"><strong><%=comment.getComment() %></strong><span class="labelGood" style="position:absolute; bottom: 3px;"></span></h4>
    						</div>
    						<%
    					}else if(score == 0) {
    						%>
    						<div style="position:relative;">
    							<h4 class="card-title"><strong><%=comment.getComment() %></strong><span class="labelNeutral" style="position:absolute; bottom: 3px;"></span></h4>
    						</div>
    						<%
    					}else {
    						%>
    						<div style="position:relative;">
    							<h4 class="card-title"><strong><%=comment.getComment() %></strong><span class="labelBad" style="position:absolute; bottom: 3px;"></span></h4>
    						</div>
    						<%
    					}
    				%>
    				<!-- <input value=<%=score %> id="rate" name="rate" class="rating rating-loading" data-show-clear="false" data-show-caption="false" data-readonly="true"> -->
    				<h5 class="card-subtitle mb-2 text-muted">使用者： <%=newUserName %></h5>
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