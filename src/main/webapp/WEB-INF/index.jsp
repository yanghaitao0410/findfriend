<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
	<meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no" />
	<meta name="renderer" content="webkit|ie-comp|ie-stand">
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
	<meta http-equiv="Cache-Control" content="no-siteapp" />
    <title>首页</title>
	
	<link rel="stylesheet" href="../css/sccl.css">
	<link rel="stylesheet" type="text/css" href="../skin/qingxin/skin.css" id="layout-skin"/>
	<script type="text/javascript" src="../js/jquery.min.js"></script>
	<script type="text/javascript" src ="../js/jquery.cookie.js" ></script>
	<script type="text/javascript" src ="../js/index.js" ></script>
    <script type="text/javascript">
    	$(function(){
    		var context = "${pageContext.request.contextPath}";
    		var user_id = $.cookie("user_id");
    		var user_name = $.cookie("user_name");
    		var user_pwd = $.cookie("user_pwd");
    		var index = 0;
    		if(user_name == null ||user_name == "" ||user_name == undefined){
    			window.location.href= context+"/account/toLogin";
    		}
    		//加载用户好友分组
    		loadGroup(user_id, context);
    		//加载用户好友
    		loadFriend(user_id, context);

    		$(".side-menu > li").addClass("menu-item");
			//加载热门动态
    		loadHotShare(user_id, index, context, user_name);
    		
			//点击热门动态按钮
    		$("#hot_share").click(function(){
    			$("#share_list").empty();
    			loadHotShare(user_id, index, context, user_name);
    		});
    		
    		//点击加号链接打开"发送动态"页面
    		$("#send_share").click(function(){
    			window.location.href = "${pageContext.request.contextPath}/share/toUpload?user_name=" + $.cookie("user_name");
    		});
    		
    		//点击个人动态链接加载个人动态
    		$("#user_share").click(function(){
    			$("#share_list").empty();
    			loadSelfShare(context, user_id, user_name , index);
    		});
    		
    		//点击好友动态链接加载好友动态
    		$("#friend_share").click(function(){
    			$("#share_list").empty();
    			loadFriendShare(user_id, index, context, user_name);
    		});
    		
    		//点击退出按钮后跳转到登陆页面
    		$("#exit").click(function(){
    			window.location.href= context+"/account/exitUser";
    			//清除session
    			<%-- <%
    			System.out.print("exit:dfgdsfgdfsgds");
    				HttpSession sess = request.getSession();
    			session.removeAttribute("user_id");
    			session.removeAttribute("user_name");
    			session.removeAttribute("user_pwd");
    		%> --%>
    			/* window.location.href= context+"/account/toLogin";   */
    		}); 
    		
    		//点击好友后加载该好友动态
    		$("#friend_list").on("click", "span", function(){
    			$("#share_list").empty();
    			var friend_id = $(this).parents("li").attr("id");
    			loadSpecificShare(context, friend_id, index, user_name);
    		});
    		
    		//点赞功能
    		$("#share_list").on("click", "img", function(){
    			var share_id = $(this).parents("li").data("share_id");
    			$endorse_count = $(this).next();
    			clickGreed(context, share_id, user_id);
    		});
    		
    		//动态显示隐藏评论区
    		$("#share_list").on("click", ".show_edit_talk", function(){
    			$(this).parent().find("div").toggle();
    		})
    		
    		//发表评论
    		$("#share_list").on("click", ".send_user_talk", function(){
    			var talk_info = $(this).prev().val();
    			var share_id = $(this).parents("li").data("share_id");
    			send_talk(context, share_id, talk_info);
    		})
    		
    		//TODO删除该用户发过的某个动态
    		$("#share_list").on("click", ".delete_talk", function(){
    			alert("dfdddddddddd");
    		});
    		
    	});
    </script>
  </head>
  
  <body>
	<div class="layout-admin">
		<header class="layout-header">
			<span class="header-logo">findfriend</span> 
			<a class="header-menu-btn" href="javascript:;"><i class="icon-font">&#xe600;</i></a>
			<ul class="header-bar">
				<%-- <li class="header-bar-role" ><a href="javascript:;">${user_name}</a></li> --%>
				<li class="header-bar-nav">
					<a href="javascript:;">${user_name}<i class="icon-font" style="margin-left:5px;">&#xe60c;</i></a>
					<ul class="header-dropdown-menu">
						<li><a href="javascript:;">个人信息</a></li>
						<!-- <li><a href="javascript:;">切换账户</a></li> -->
						<li><a id="exit" href="javascript:;">退出</a></li>
					</ul>
				</li>
				<li class="header-bar-nav"> 
					<a href="javascript:;" title="换肤"><i class="icon-font">&#xe608;</i></a>
					<ul class="header-dropdown-menu right dropdown-skin">
						<li><a href="javascript:;" data-val="qingxin" title="清新">清新</a></li>
						<li><a href="javascript:;" data-val="blue" title="蓝色">蓝色</a></li>
						<li><a href="javascript:;" data-val="molv" title="墨绿">墨绿</a></li>
						
					</ul>
				</li>
			</ul>
		</header>
		<aside class="layout-side">
			<ul id="friend_list" class="side-menu">
			  
			</ul>
		</aside>
		
		<aside class="layout-left-side">
			<ul id="new_friend_list">
			  
			</ul>
		</aside>
		
		<div class="layout-side-arrow"><div class="layout-side-arrow-icon"><i class="icon-font">&#xe60d;</i></div></div>
		
		<section class="layout-main">
			<div class="layout-main-tab">
				<button class="tab-btn btn-left"><i class="icon-font">&#xe60e;</i></button>
                <nav class="tab-nav">
                    <div class="tab-nav-content">
                        <a id="hot_share" href="javascript:;" class="content-tab active">热门动态</a>
					<!--<a href="javascript:;" class="content-tab active" data-id="home.html">首页</a> -->
                    </div>
                    <div class="tab-nav-content">
                        <a id="friend_share" href="javascript:;" class="content-tab active">好友动态</a>
                    </div>
                     <div class="tab-nav-content">
                        <a id="user_share" href="javascript:void(0);" class="content-tab active">个人动态</a>
                    </div>
                    <div class="tab-nav-content">
                        <a id="user_talked_share" href="javascript:void(0);" class="content-tab active">评论过的动态</a>
                    </div>
                    <div class="tab-nav-content">
                        <a id="user_greated_share" href="javascript:void(0);" class="content-tab active">赞过的动态</a>
                    </div>
                     <div class="tab-nav-content">
                        <a id="send_share" href="javascript:;" class="content-tab active">+</a>
                    </div>
                </nav>
                <button class="tab-btn btn-right"><i class="icon-font">&#xe60f;</i></button>
			</div>
			<div class="layout-main-body">
			
				<ul id="share_list">
					
				</ul>
				<!-- <iframe class="body-iframe" name="iframe0" width="100%" height="99%" src="home.html" frameborder="0" data-id="home.html" seamless></iframe> -->
			</div>
		</section>
		<div class="layout-footer">@2017 0.1 mycodes</div>
	</div>
	<script type="text/javascript" src="../js/sccl.js"></script>
	<script type="text/javascript" src="../js/sccl-util.js"></script>
  </body>
</html>
