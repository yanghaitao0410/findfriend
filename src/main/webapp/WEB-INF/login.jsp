<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
	<meta charset="UTF-8">
	<title>findfriend</title>

    <meta http-equiv="X-UA-Compatible" content="IE=edge, chrome=1">
    <link type="text/css" rel="stylesheet" href ="../css/login.css">
    <script type="text/javascript" src ="../js/jquery.min.js" ></script>
    <script type="text/javascript" src ="../js/jquery.cookie.js" ></script>
    <script type="text/javascript">
    	$(function(){
    		var userName;
    		var userPwd;
    		if(null != $.cookie("user_name") && "" != $.cookie("user_name") && null != $.cookie("user_pwd") && "" != $.cookie("user_pwd") ){
    			$("#user_name").val($.cookie("user_name"));
    			$("#user_pwd").val($.cookie("user_pwd"));
    		}
    		

    		$("#login_btn").click(function(){
    			var remember = $("#remember").val();
    			userName = $("#user_name").val().trim();
				userPwd = $("#user_pwd").val().trim();
				if(userName != "" && userPwd != ""){
					if(remember == 1){
	    				$.cookie("user_name", userName, {expires:7});
	    				$.cookie("user_pwd", userPwd, {expires:7});
	    			}
					/* checkAccount(userName, userPwd); */
				}	
    		});
    		
    	});
    		
    		
    	<%-- function checkAccount(user_name, user_pwd){
    		$.ajax({
				url: "<%=request.getContextPath()%>/account/toLogin",
				type:"post",
				data:{"user_name" : user_name , "user_pwd" : user_pwd},
				dataType:"json",
				success:function(data){
					if(data.status == 0){
						alert("登陆成功");
					}else{
						$("#msg").html(data.msg);
					}
				}
			});
    	}	 --%>
    </script>
</head>
<body class="login_bj" >
<div class="zhuce_body">
	<div class="logo"><a href="#"><img src="../images/logo.png" width="114" height="54" border="0"></a></div>
    <div class="zhuce_kong login_kuang">
    	<div class="zc">
        	<div class="bj_bai">
            	<h3>登录</h3>
            	<form action="<%=request.getContextPath()%>/account/toIndex" method="post">
	                <input id="user_name" type="text" name="user_name" class="kuang_txt" placeholder="用户名"/>
	                <input id="user_pwd" type="password" name="user_pwd" class="kuang_txt" placeholder="密码"/>
	                <button id="login_btn" type="submit" class="btn_zhuce">登陆</button>
                </form>
                <div>
	                <a href="#">忘记密码？</a>
	                <input id="remember" type="checkbox" value="1" checked="checked"/>
	                <span>记住我</span>
                </div>
            </div>
        	<div class="bj_right">
        		<p id = "msg" style="color: red;">${msg }</p>
                <p>没有账号？<a href="/findfriend/account/toRegister">立即注册</a></p>
            
            </div>
        </div>
        <P>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;欢迎您来到这里</P>
    </div>

</div>
    
</body>
</html>