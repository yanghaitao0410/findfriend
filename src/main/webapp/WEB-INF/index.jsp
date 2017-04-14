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
    			
    			loadHotShare(user_id, index, context, user_name);
    		});
    		
    		//点击加号链接打开"发送动态"页面
    		$("#send_share").click(function(){
    			window.location.href = "${pageContext.request.contextPath}/share/toUpload?user_name=" + $.cookie("user_name");
    		});
    		
    		//点击个人动态链接加载个人动态
    		$("#user_share").click(function(){
    			$("#show_share_area").show();
    			$("#edit_group_div").hide();
    			$("#user_info").hide();
    			$("#change_group_div").hide();
    			$("#share_list").empty();
    			loadSelfShare(context, user_id, user_name , index);
    		});
    		
    		//点击好友动态链接加载好友动态
    		$("#friend_share").click(function(){
    			$("#show_share_area").show();
    			$("#edit_group_div").hide();
    			$("#user_info").hide();
    			$("#change_group_div").hide();
    			$("#share_list").empty();
    			loadFriendShare(user_id, index, context, user_name);
    		});
    		
    		//点击退出按钮后跳转到登陆页面
    		$("#exit").click(function(){
    			window.location.href= context+"/account/exitUser";
    			//清除session 不能在这里清除session jsp脚本是全局的
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
    			$("#show_share_area").show();
    			$("#edit_group_div").hide();
    			$("#user_info").hide();
    			$("#change_group_div").hide();
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
    		
    		//删除该用户发过的某个评论
    		$("#share_list").on("click", ".delete_talk", function(){
    			var talk_id = $(this).parent().attr("id");
    			talk_id = talk_id.substr(8);
    			deleteTalk(context, talk_id);
    		});
    		
    		//加载当前用户评论过的动态
    		$("#user_talked_share").click(function(){
    			$("#show_share_area").show();
    			$("#edit_group_div").hide();
    			$("#user_info").hide();
    			$("#change_group_div").hide();
    			$("#share_list").empty();
    			loadTalkedShare(context, user_id, index, user_name);
    		});
    		
    		//加载当前用户赞过的动态
    		$("#user_greated_share").click(function(){
    			$("#show_share_area").show();
    			$("#edit_group_div").hide();
    			$("#user_info").hide();
    			$("#change_group_div").hide();
    			$("#share_list").empty();
    			loadGreatShare(context, user_id, index, user_name);
    		});
    		
    		//当前用户删除自己曾经发过的某个动态，先将该动态放入回收站
    		$("#share_list").on("click",".delete_share", function(){
    			var share_id = $(this).parents("li").data("share_id");
    			Share2Recycle(context, user_id, share_id);
    		});
    		
    		//点击回收站按钮加载回收站动态
    		$("#recycle_share").click(function(){
    			$("#show_share_area").show();
    			$("#edit_group_div").hide();
    			$("#user_info").hide();
    			$("#change_group_div").hide();
    			$("#share_list").empty();
    			loadRecycleShare(context, user_id, user_name);
    		});
    		
    		//从回收站撤回动态
    		$("#share_list").on("click", ".reset_share", function(){
    			var share_id = $(this).parents("li").data("share_id");
    			resetShare(context, user_id, share_id);
    		});
    		
    		//从回收站彻底删除动态
    		$("#share_list").on("click", ".real_delete_share", function(){
    			var share_id = $(this).parents("li").data("share_id");
    			deleteShare(context, user_id, share_id);
    		});
    		
    		//点击个人信息链接加载个人信息
    		$("#user_info_a").click(function(){
    			$("#user_info").show();
    			$("#show_share_area").hide();
    			$("#edit_group_div").hide();
    			$("#change_group_div").hide();
    			queryUserInfo(context, user_id);
    		});
    		
    		//点击个人信息的加号增加动态输入框
    		$("#user_info_list").on("click", ".create_new_hobby",function(){
    			var add_hobby = "<div><input class='user_hobby' type='text'><a class='delete_hobby' href='javascript:;'>X</a></br></div>";
    			$(".hobby_div").append(add_hobby);
    		});
    		
    		//删除一个爱好输入框
    		$("#user_info_list").on("click", ".delete_hobby", function(){
    			$(this).parent().remove();
    		});
    		
    		//修改个人信息后点击提交按钮更新信息
    		$("#user_info_list").on("click", ".edit_submit", function(){
    			var nick_name = $(".nick_name").val().trim();
    			var user_sex = $(".user_sex:checked").val().trim();
    			var user_phone_num = $(".user_phone_num").val().trim();
    			var $hobbys = $(".user_hobby");
    			var string_hobby ="";
				
    			$hobbys.each(function() {
    				var $this = $(this);
    				string_hobby += $this.val() + ",";
    			});
    			updateUserInfo(user_id, nick_name, user_sex, user_phone_num, string_hobby);
    		});
    		
    		//点击好友的小人加载好友信息
    		$("#friend_list").on("click", ".friend_info", function(){
    			$("#user_info").show();
    			$("#show_share_area").hide();
    			$("#change_group_div").hide();
    			$("#edit_group_div").hide();
    			$("#user_info_list").empty();
    			var friend_id = $(this).parents("li").attr("id");
    			loadFriendInfo(context, user_id, friend_id);
    		});
    		
    		//点击箭头图标加载重新分组界面
    		$("#friend_list").on("click", ".change_group", function(){
    			$("#change_group_div").show();
    			$("#show_share_area").hide();
    			$("#user_info").hide();
    			$("#edit_group_div").hide();
    			var friend_id = $(this).parents("li").attr("id");
    			$(".sure_group").data("friend_id", friend_id);
    			loadGroupSelect(context, user_id);
    		});
    		
    		//点击确认按钮后将用户移动到该分组下
    		$(".sure_group").click(function(){
    			var group_name = $("#group_select").select().val();
    			var friend_id = $(".sure_group").data("friend_id");
    			changeGroup (context, user_id, friend_id, group_name);
    		});
    		
    		//点击新建分组按钮
    		$(".add_group").click(function(){
    			$(".group_name_div").find("input").val("");
    			$(".group_name_div").toggle();	
    			$(".wrong_group_msg").hide();
    			$(".right_group_msg").hide();
    		});
			
    		
    		//输入组名焦点移出事件
    		$(".group_name_div input").blur(function(){
    			
    			var group_name = $(this).val().trim();
    			//var group_name = $(".group_name_div").find("input").val().trim();
    			if(group_name == ""){
    				$(".wrong_group_msg").html("组名不能为空！！！").show();
    				return;
    			}else{ //检查该组名是否已经创建
    				checkGroup(context, user_id, group_name);
    			}
    		});
    		
    		//点击编辑按钮加载修改组名页面
    		$("#friend_list").on("click", ".edit_group_name", function(){
    			var group_name = $(this).nextAll("a").html();
    			$("#change_group_div").hide();
    			$("#show_share_area").hide();
    			$("#user_info").hide();
    			$("#edit_group_div").show();
    			$(".edit_group_input").val(group_name);
    			$(".edit_group_button").data("old_group_name", group_name);
    		});
    		
    		//点击确定创建按钮添加组名
    		$(".sure_create_group").click(function(){
    			var display = $(".right_group_msg").css("display");
    			if(display == 'none'){
    				$(".wrong_group_msg").html("组名不合法！！").show();
    				return;
    			}else{ //只有right_group_msg 显示的时候才真正创建组名
    				addGroup (context, user_id);
    			}
    			
    		});
    		
    		//点击确认更改按钮修改组名
    		$(".edit_group_button").click(function(){
    			var group_name = $(this).prev("input").val();
    			var old_group_name = $(this).data("old_group_name");
    			groupRename(context, user_id, old_group_name, group_name);
    		});
    		
    		//点击差号删除分组
    		$("#friend_list").on("click", ".delete_group_image", function(){
    			var group_name = $(this).next("a").html();
    			checkGroupToRename (context, user_id, group_name);
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
						<li><a id="user_info_a" href="javascript:;">个人信息</a></li>
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
                    <div class="tab-nav-content">
                        <image id="recycle_share" src="../image/recycle.png" class="content-tab active" width="20" higth="20"></image>
                    </div>
                </nav>
                <button class="tab-btn btn-right"><i class="icon-font">&#xe60f;</i></button>
			</div>
			<div id="show_share_area" class="layout-main-body">
			
				<ul id="share_list">
					
				</ul>
				<!-- <iframe class="body-iframe" name="iframe0" width="100%" height="99%" src="home.html" frameborder="0" data-id="home.html" seamless></iframe> -->
			</div>
			<div id="user_info" class="layout-main-body" style="display: none;">
				<h1>
					个人信息
				</h1>
				<ul id='user_info_list' ></ul>
			</div>
			<div id="change_group_div" class="layout-main-body" style="display: none;">
				<h1>
					重新分组
				</h1>
				<select id="group_select">
					
				</select>
				<button class="add_group">新建分组</button> <div class="group_name_div" style="display: none"><input class="group_name_input" type="text" ><button class="sure_create_group">确定创建</button></div></br>
				<div class="wrong_group_msg" style="color: red;display: none"></div>
				<div class="right_group_msg" style="color: green;display: none"></div>
				
				<button class="sure_group">确定</button>
				
			</div>
			<div id=edit_group_div class="layout-main-body" style="display: none;">
				<h1>
					更改组名
				</h1>
				<input class="edit_group_input" type="text" >
				<button class="edit_group_button">确认更改</button>
				<div class="wrong_group_msg" style="color: red;display: none"></div>
				<div class="right_group_msg" style="color: green;display: none"></div>
			</div>
			
		</section>
		<div class="layout-footer">@2017 0.1 mycodes</div>
	</div>
	<script type="text/javascript" src="../js/sccl.js"></script>
	<script type="text/javascript" src="../js/sccl-util.js"></script>
  </body>
</html>
