<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>findfriend</title>

    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <link type="text/css" rel="stylesheet" href="css/login.css">
    <script type="text/javascript" src="js/jquery.min.js"></script>
	<script type="text/javascript">
		$(function(){
			$("#username").blur(function(){
				$("#info").html("");
				var user_name = $("#username").val().trim();
				if(user_name != "" && user_name != undefined){
					$.ajax({
						url:"<%=request.getContextPath()%>/account/checkUserName",
						type:"post",
						data:{"user_name" : user_name},
						dataType:"json",
						success:function(data){
							if(data.status == 0){
								$("#info").html(data.msg).css("color","green");
							}else{
								$("#info").html(data.msg).css("color","red");
							}
						},
						error:function(){}
					});
				}
			});
			$("#pwd1").blur(function(){
				var pwd1 = $("#pwd1").val().trim();
				
				
			});
			
			
		});	
	
	
	
	</script>
</head>
<body class="login_bj" >

<div class="zhuce_body">
	<div class="logo"><a href="#"><img src="images/logo.png" width="114" height="54" border="0"></a></div>
    <div class="zhuce_kong">
    	<div class="zc">
        	<div class="bj_bai">
            <h3>欢迎注册</h3>
       	  	  <form action="<%=request.getContextPath()%>/register" method="post">      	  	  	
                <input id="username" name="username" type="text" class="kuang_txt username" placeholder="用户名">
                <input id="pwd1" name="pwd" type="password" class="kuang_txt possword" placeholder="密码">
                <input id="pwd2" name="pwd1" type="password" class="kuang_txt possword" placeholder="确认密码">
                <input id="user_info" name="user_info" type="text" class="kuang_txt user_info" placeholder="安全信息">
                <input id="check_num" name="check_num" type="text" class="kuang_txt check_num" placeholder="验证码">
                <div>
                	<div class="hui_kuang"><img src="images/zc_22.jpg" width="92" height="31"></div>
                	<div class="flush"><a href="#"><img src="images/zc_25.jpg" width="13" height="14"></a></div>
                </div>
              <!--  <div>
               		<input name="" type="checkbox" value=""><span>已阅读并同意<a href="#" target="_blank"><span class="lan">《XXXXX使用协议》</span></a></span>
                </div>
               --> 
                <input name="register" type="button" class="btn_zhuce" value=注册>
                
                </form>
            </div>
        	<div class="bj_right">
            	<!--<p>使用以下账号直接登录</p>
                <a href="#" class="zhuce_qq">QQ注册</a>
                <a href="#" class="zhuce_wb">微博注册</a>
                <a href="#" class="zhuce_wx">微信注册</a>-->
                <p id="info"></p>
                <p>已有账号？<a href="/findfriend/login.jsp">立即登录</a></p>
            
            </div>
        </div>
        <P>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;欢迎您来到这里</P>
    </div>

</div>

</body>
</html>