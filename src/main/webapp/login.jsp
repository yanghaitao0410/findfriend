<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>findfriend</title>

    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <link type="text/css" rel="stylesheet" href ="css/login.css">
    <script type="text/javascript" src ="js/jquery.min.js" ></script>
    <script type="text/javascript" src ="js/jquery.cookie.js" ></script>
    <script type="text/javascript">
    	$(function(){
    		var userName;
    		var userPwd;
    		if(undefined != $.cookie("user_name") && undefined != $.cookie("user_pwd")){
    			$("#user_name").val($.cookie("user_name"));
    			$("#user_pwd").val($.cookie("user_pwd"));
    		}
    		

    		$("#login").click(function(){
    			var remember = $("#remember").val();
    			if(remember != undefined){
    				userName = $("#user_name").val();
    				userPwd = $("#user_pwd").val();
    				$.cookie("user_name", userName, {expires:7});
    				$.cookie("user_pwd", userPwd, {expires:7});
    			}
    		});
    		
    	});
    </script>
</head>
<body class="login_bj" >
<div class="zhuce_body">
	<div class="logo"><a href="#"><img src="images/logo.png" width="114" height="54" border="0"></a></div>
    <div class="zhuce_kong login_kuang">
    	<div class="zc">
        	<div class="bj_bai">
            <h3>登录</h3>
       	  	  <form action="<%=request.getContextPath()%>/login" method="get">
                <input id="user_name" type="text" class="kuang_txt" placeholder="用户名"/>
                <input id="user_pwd" type="text" class="kuang_txt" placeholder="密码"/>
                
                <div>
               		<a href="#">忘记密码？</a><input id="remember" type="checkbox" value="1" checked><span>记住我</span>
                </div>
                <input id="login" name="登录" type="button" class="btn_zhuce" value="登录">
                
                </form>
            </div>
        	<div class="bj_right">
        		<p id = "msg" style="color: red;"></p>
                <p>没有账号？<a href="/findfriend/regiest.jsp">立即注册</a></p>
            
            </div>
        </div>
        <P>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;欢迎您来到这里</P>
    </div>

</div>
    
</body>
</html>