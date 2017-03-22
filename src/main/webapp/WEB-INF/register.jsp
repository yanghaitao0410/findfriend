<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!doctype html>
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>findfriend</title>

    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <link type="text/css" rel="stylesheet" href="../css/login.css">
    <script type="text/javascript" src="../js/jquery.min.js"></script>
	<script type="text/javascript">
		$(function(){
			$("#username").blur(function(){
				$("#account_info").html("");
				var user_name = $("#username").val().trim();
				regularAccount();
				
				if(user_name != "" && user_name != undefined){
					checkUserNameAjax();
				}
			});
			
			$("#user_pwd").blur(function(){
				var user_pwd = $("#user_pwd").val().trim();
				var pwd2 = $("#pwd2").val().trim();
				if(pwd2 != "" && pwd2 != null){
					check2Pwd();
				}
				regular();
			});
			
			$("#pwd2").blur(check2Pwd);
			
			$("#register").click(function(){
				var user_name = $("#username").val().trim();
				var user_pwd = $("#user_pwd").val().trim();
				var pwd2 = $("#pwd2").val().trim();
				var user_info = $("#user_info").val().trim();
				
				if(user_pwd == pwd2 && user_name != "" && user_info != "" && user_pwd != ""){
					/* $("#register_form").ajaxSubmit(); */
					$.ajax({
						url:"<%=request.getContextPath()%>/account/register",
						type:"post",
					/* 	data:$('#register_form').serialize(), */
						data:{"user_name":user_name, "user_pwd" : user_pwd,"user_info":user_info},
						type:"post",
						dataType:"json",
						success:function(data){
							if(data.status == 0){
								alert(data.msg);
								/* window.location.href="/findfriend/login.jsp"; */
								window.location.href="<%=request.getContextPath()%>/account/toLogin";
							}
						}
					}); 
				}
				
			});
		});	
		
		function checkUserNameAjax(){
			var user_name = $("#username").val().trim();
			$.ajax({
				url:"<%=request.getContextPath()%>/account/checkUserName",
				type:"post",
				data:{"user_name" : user_name},
				dataType:"json",
				success:function(data){
					if(data.status == 0){
						$("#account_info").removeClass("wrong_info").addClass("right_info");
						$("#account_info").html(data.msg);
						
					}else{
						$("#account_info").removeClass("right_info").addClass("wrong_info");
						$("#account_info").html(data.msg);
					}
				},
				error:function(){}
			});
		}
		
		function regularAccount(){
			var user_name = $("#username").val().trim();
			if(user_name.length < 5){
				$("#account_info").removeClass("right_info").addClass("wrong_info");
				$("#account_info").html("用户名长度至少为5位");
				return;
			}
			var pattern = /^[0-9]+$/;
			if(pattern.test(user_name)){
				$("#account_info").removeClass("right_info").addClass("wrong_info");
				$("#account_info").html("用户名不应是存数字");
				return;
			}
		}
		
		function regular(){
			var user_pwd = $("#user_pwd").val().trim();
			var pwd2 = $("#pwd2").val().trim();
			if(user_pwd.length < 6){
				$("#pwd_info").removeClass("right_info").addClass("wrong_info");
				$("#pwd_info").html("密码至少为6位");
				return;
			}				
			var pattern1 = /^[A-Za-z]+$/;
			var pattern2 = /^[0-9]+$/;
			var pattern3 = /^[A-Za-z0-9]+$/;
			if(pattern1.test(user_pwd)){
				$("#pwd_info").removeClass("right_info").addClass("wrong_info");
				$("#pwd_info").html("密码不应是存字母");
			}else
			if(pattern2.test(user_pwd)){
				$("#pwd_info").removeClass("right_info").addClass("wrong_info");
				$("#pwd_info").html("密码不应是存数字");
			}else
			if(pattern3.test(user_pwd)){
				$("#pwd_info").removeClass("wrong_info").addClass("right_info");
				$("#pwd_info").html("密码格式正确");
			}
		}
	
		function check2Pwd(){
			var user_pwd = $("#user_pwd").val().trim();
			var pwd2 = $("#pwd2").val().trim();
			if(pwd2 != "" && pwd2 != null){
				if(user_pwd != pwd2){
					$("#pwd_info").removeClass("right_info").addClass("wrong_info");
					$("#pwd_info").html("两次密码输入不一致");
				}else{
					$("#pwd_info").removeClass("wrong_info").addClass("right_info");
					$("#pwd_info").html("密码输入正确");
				}
			}
			return;
		}
	
	</script>
</head>
<body class="login_bj" >

<div class="zhuce_body">
	<div class="logo"><a href="#"><img src="../images/logo.png" width="114" height="54" border="0"></a></div>
    <div class="zhuce_kong">
    	<div class="zc">
        	<div class="bj_bai">
            <h3>欢迎注册</h3>
       	  	  <!-- <form id ="register_form" >    -->   	  	  	
                <input id="username" name="user_name" type="text" class="kuang_txt username" placeholder="用户名">
                <input id="user_pwd" name="user_pwd" type="password" class="kuang_txt possword" placeholder="密码">
                <input id="pwd2" name="pwd2" type="password" class="kuang_txt possword" placeholder="确认密码">
                <input id="user_info" name="user_info" type="text" class="kuang_txt user_info" placeholder="安全信息">
                <input id="check_num" name="check_num" type="text" class="kuang_txt check_num" placeholder="验证码">
                <div>
                	<div class="hui_kuang"><img src="../images/zc_22.jpg" width="92" height="31"></div>
                	<div class="flush"><a href="#"><img src="../images/zc_25.jpg" width="13" height="14"></a></div>
                </div>
              <!--  <div>
               		<input name="" type="checkbox" value=""><span>已阅读并同意<a href="#" target="_blank"><span class="lan">《XXXXX使用协议》</span></a></span>
                </div>
               --> 
                <input id="register" name="register" type="button" class="btn_zhuce" value=注册 />
                
               <!--  </form> -->
            </div>
        	<div class="bj_right">
            	<!--<p>使用以下账号直接登录</p>
                <a href="#" class="zhuce_qq">QQ注册</a>
                <a href="#" class="zhuce_wb">微博注册</a>
                <a href="#" class="zhuce_wx">微信注册</a>-->
                <h3>提示信息</h3>
                <p id="account_info"></p>
                <p id="pwd_info" class="wrong_info">密码格式为数字字母组合</p>
                <p>已有账号？<a id="toLogin" href="/findfriend/account/toLogin">立即登录</a></p>
            
            </div>
        </div>
        <P>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;欢迎您来到这里</P>
    </div>

</div>

</body>
</html>