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
    <script type="text/javascript">
    	$(function(){
    		var user_id = $.cookie("user_id");
    		alert(user_id);
    		if(user_id == null){
    			window.location.href="login.jsp";
    		}
    		
    		var groups = new Array();
    		
    		$.ajax({
    			url:"http://localhost:8080/findfriend/friend/loadGroup",
    			type:"post",
    			data:{"user_id" : user_id},
    			dataType:"json",
    			success:function(result){
    				var data = result.data;
    				for(var i=0; i < data.length; i++){
    					groups[i] = data[i];
    				}
    			}
    		});
    		alert(groups);
    		$.ajax({
    			url:"http://localhost:8080/findfriend/friend/loadFriend",
    			type:"post",
    			data:{"user_id" : user_id},
    			dataType:"json",
    			success:function(result){
    				if(result.status == 0){
    					var list = result.data;
    					appendMenu(list);
    				}
    			}
    		});
    		
    		
    		 //创建子菜单
    	    function createChild(childArray,group_name){
    	    	var childResult='';
    	    	for(var i =0;i<childArray.length;i++){
    	    		if(childArray[i].group_name == group_name){
    	    			var sli = '<li><a><i class="icon-font">&#xe610</i><span>'+childArray[i].friend_name+'</span></a></li>';
    					$li = $(sli);
    					$li.data("friend_id",childArray[i].friend_id);
    	    			childResult += sli;    			 
    	    		}
    	    	}
    	    	return childResult;
    	    }
    	    
    	    //拼接菜单 
    		function appendMenu(data){	
    			var dataTree = data;
    		    var childArray = new Array();
    		    var childIndex =0;
    		    var result='';
    		    for(var i = 0;i<dataTree.length;i++){
    		    	if(dataTree[i].parent_id != 0){
    		    		childArray[childIndex] = dataTree[i];
    		    		childIndex++;
    		    	}else{
    		    		var sli = '<li><a><i class="icon-font">&#xe610</i><span>'+dataTree[i].friend_name+'</span></a></li>';
    					$li = $(sli);
    					$li.data("friend_id",dataTree[i].friend_id);
    	    			result += sli;  
    		    	}
    		    }
    		   
    		    for(var k =0;k<groups.length;k++){
    		    	if(groups[k] != null){
    		    		var sli = '<a>'+groups[k]+'</a>';
        				/*$li = $(sli);
        				$li.data("group_id",parentArray[i].group_id);*/
        		    	
        		    	result += '<li class="menu-header">' + sli;    	
        		    	result +='<ul class="menu-item-child">';
        		    	result += createChild(childArray,groups[k]);
        		    	result +='</ul></li>';
    		    	}
    		    	
    		    }
    		    $(".side-menu").append(result);	  
    		}
    		
    		$(".side-menu > li").addClass("menu-item");
    		
    		
    	});
    </script>
  </head>
  
  <body>
	<div class="layout-admin">
		<header class="layout-header">
			<span class="header-logo">findfriend</span> 
			<a class="header-menu-btn" href="javascript:;"><i class="icon-font">&#xe600;</i></a>
			<ul class="header-bar">
				<li class="header-bar-role" ><a href="javascript:;">${user_name}</a></li>
				<li class="header-bar-nav">
					<a href="javascript:;">admin<i class="icon-font" style="margin-left:5px;">&#xe60c;</i></a>
					<ul class="header-dropdown-menu">
						<li><a href="javascript:;">个人信息</a></li>
						<!-- <li><a href="javascript:;">切换账户</a></li> -->
						<li><a href="javascript:;">退出</a></li>
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
			<ul class="side-menu">
			  
			</ul>
		</aside>
		
		<div class="layout-side-arrow"><div class="layout-side-arrow-icon"><i class="icon-font">&#xe60d;</i></div></div>
		
		<section class="layout-main">
			<div class="layout-main-tab">
				<button class="tab-btn btn-left"><i class="icon-font">&#xe60e;</i></button>
                <nav class="tab-nav">
                    <div class="tab-nav-content">
                        <a href="javascript:;" class="content-tab active" data-id="home.html">首页</a>
                    </div>
                </nav>
                <button class="tab-btn btn-right"><i class="icon-font">&#xe60f;</i></button>
			</div>
			<div class="layout-main-body">
				<iframe class="body-iframe" name="iframe0" width="100%" height="99%" src="home.html" frameborder="0" data-id="home.html" seamless></iframe>
			</div>
		</section>
		<div class="layout-footer">@2016 0.1 www.mycodes.net</div>
	</div>
	<script type="text/javascript" src="../js/sccl.js"></script>
	<script type="text/javascript" src="../js/sccl-util.js"></script>
  </body>
</html>
