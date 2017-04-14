var groups = new Array();
function loadGroup(user_id, context){
	$.ajax({
		url: context+"/friend/loadGroup",
		type:"post",
		async: false,
		data:{"user_id" : user_id},
		dataType:"json",
		success:function(result){
			var data = result.data;
			for(var i=0; i < data.length; i++){
				groups[i] = data[i];
			}
		}
	});
}

function loadFriend(user_id, context){
	$.ajax({
		url:context+"/friend/loadFriend",
		type:"post",
		async: false,
		data:{"user_id" : user_id},
		dataType:"json",
		success:function(result){
			if(result.status == 0){
				var list = result.data;
				appendMenu(list);
			}
		}
	});
}

//拼接菜单 
function appendMenu(data){	
	$(".side-menu").empty();
	var dataTree = data;
    var childArray = new Array();
    var childIndex =0;
    var result='';
    for(var i = 0;i<dataTree.length;i++){
    	if(dataTree[i].parent_id != 0){
    		childArray[childIndex] = dataTree[i];
    		childIndex++;
    	}else{
    		var sli = '<li id='+dataTree[i].friend_id+'><a><image class="friend_info" src="../image/user.png" width="18" height="18"/><span>'+dataTree[i].friend_name+'</span><image class="change_group" src="../image/move.png" width="18" height="18"/></a></li>';
//			$li = $(sli);
//			$li.data("friend_id",dataTree[i].friend_id);
			result += sli;
    	}
    }
   
    for(var k =0;k<groups.length;k++){
    	if(groups[k] != null){
    		var sli = '<a>'+groups[k]+'</a>';
			/*$li = $(sli);
			$li.data("group_id",parentArray[i].group_id);*/
	    	
	    	result += '<li class="menu-header"><image class="edit_group_name" src="../image/edit.png"  width="15" height="15" /><image class="delete_group_image" src="../image/delete.png"  width="15" height="15"/>' + sli;    	
	    	result +='<ul class="menu-item-child">';
	    	//result += createChild(childArray,groups[k]);
	    	result += createChild(childArray,groups[k]);
	    	result +='</ul></li>';
	    	$result = $(result);
	    	
    	}
    	
    }
    $(".side-menu").append($result);	   
}

//创建子菜单
function createChild(childArray,group_name){
	var childResult='';
	for(var i =0;i<childArray.length;i++){
		if(childArray[i].group_name == group_name){
			var sli = '<li id='+childArray[i].friend_id+'><a><image class="friend_info" src="../image/user.png" width="18" height="18"/><span>'+childArray[i].friend_name+'</span><image class="change_group" src="../image/move.png" width="18" height="18"/></a></li>';
//			$li = $(sli);
//			$li.data("friend_id",childArray[i].friend_id);
			childResult += sli;    
		}
	}
	return childResult;
}

function loadHotShare(user_id ,index, context, user_name){
	$("#show_share_area").show();
	$("#edit_group_div").hide();
	$("#user_info").hide();
	$("#change_group_div").hide();
	$("#share_list").empty();
	//加载热门动态
	$.ajax({
		url : context+"/share/loadHotShare",
		type : "post",
		dataType : "json",
		data : {"user_id": user_id, "index" : index},
		success: function(result){
			appendShareList(result, context, user_name);
		}
	});
}

function loadSelfShare (context, user_id, user_name , index){
	$.ajax({
		url:context+"/share/loadShare",
		type:"post",
		dataType:"json",
		data:{"user_id":user_id, "index":index},
		success:function(result){
			appendShareList(result, context, user_name);
		}
	});
}

function loadFriendShare(user_id, index, context, user_name){
	$.ajax({
		url:context+"/share/loadFriendShare",
		type:"post",
		dataType:"json",
		data:{"user_id":user_id, "index": index},
		success: function(result){
			appendShareList(result, context, user_name);
		}
	});
}

function appendShareList(result, context, user_name){
	if(result.status == 0){
		var data = result.data;
		for(var i=data.length-1; i>=0; i--){
			var talk_list = data[i].talk_list;
			var li = "";
			li += "<li><span>"+data[i].user_name+"</span><br/><span>"+data[i].share_msg+"</span><br/>";
			//拼接动态图片
			for(var j=0; j<data[i].images.length; j++){	
				li += "<image src='"+context+"/upload/"+data[i].user_id+"/"+data[i].images[j]+"'></image><br/>";
			}
			//拼接动态创建时间和点赞信息
			li += "<span>"+data[i].creatime+"</span><br/><span><image src='../image/greed.png' width='13' height='13'/><span>"+data[i].endorse_count+"</span></span><br/><p>";
			//拼接评论信息
			if(talk_list != null && talk_list.length > 0){
				for(var j=0; j<talk_list.length; j++){
					li += "<div class='talk_div' id='talk_id_"+talk_list[j].talk_id+"'><a class='talk_user_name'>"+talk_list[j].talk_user_name+" </a><span class='talk_info'> : "+talk_list[j].talk_info+" </span><br/><span class='talk_creatime'> "+talk_list[j].talk_creatime+" </span>";
					if(user_name == talk_list[j].talk_user_name){
						li +="<a class='delete_talk'>  删除  </a>"
					}
					li += "</div><br/>";
				}
			}
			//判断动态的状态是正常状态还是删除状态
			if(data[i].share_status == 0){
				//判断动态是否为当前用户自己的
				if(user_name == data[i].user_name){
					li += "<button class='delete_share'>删除动态<button>";
				}
				li += "</p><br/>";
				li += "<button class='show_edit_talk'>发表评论</button>";
			}else{
				li += "<button class='real_delete_share'>彻底删除<button></p><br/>"
				li += "<button class='reset_share'>撤回动态</button>";
			}
			//拼接隐藏评论区
			li += "<br/><div class='user_send_div_hide' style='display:none;'><input type='text' class='user_talk_info'  /><button class='send_user_talk'>发表</button></div></li>";
			$li = $(li);
			$li.data("user_id", data[i].user_id);  //该动态的所属用户
			$li.data("share_id", data[i].share_id);
			$("#share_list").append($li);
		}
	}else{
		alert(result.msg);
	}
}

function loadSpecificShare(context, friend_id, index, user_name){
	$.ajax({
		url:context+"/share/loadSpecificShare",
		type:"post",
		dataType:"json",
		data:{"friend_id":friend_id, "index" : index},
		success:function(result){
			appendShareList(result, context, user_name);
		}
	});
}

function clickGreed(context, share_id, user_id){
	$.ajax({
		url: context+"/share/clickGreed",
		type:"post",
		dataType:"json",
		data:{"share_id": share_id, "user_id":user_id},
		success:function(result){
			if(result.status == 0){
				var endorse_count = result.data;
				$endorse_count.html(endorse_count);
			}
		}
	});
}

function send_talk(context, share_id, talk_info){
	$.ajax({
		url:context+"/talk/sendTalk",
		type:"post",
		dataType:"json",
		data:{"share_id":share_id, "talk_info":talk_info},
		success:function(result){
			alert(result.msg);
			/*TODO 动态对应的tab上应该是有选中样式的，应该动态模拟
			  	   现在只是固定加载热门动态	
			*/
			$("#hot_share").trigger("click");
		}
	});
}

function deleteTalk(context, talk_id){
	$.ajax({
		url:context+"/talk/deleteTalk",
		type:"post",
		dataType:"json",
		data:{"talk_id":talk_id},
		success:function(result){
				alert(result.msg);
				/*TODO 动态对应的tab上应该是有选中样式的，应该动态模拟
			  	   现在只是固定加载热门动态	
				*/
				
				$("#hot_share").trigger("click");
		}
	});  
}

function loadTalkedShare(context, user_id, index, user_name){
	$.ajax({
		url:context + "/share/loadTalkedShare",
		type:"post", 
		dataType:"json",
		data:{"talk_user_id":user_id, "index":index},
		success:function(result){
			appendShareList(result, context, user_name);
		}
	});
}

function loadGreatShare(context, user_id, index, user_name){
	$.ajax({
		url: context+"/share/loadGreatShare",
		type:"post",
		dataType:"json",
		data:{"user_id":user_id, "index":index},
		success:function(result){
			appendShareList(result, context, user_name);
		}
	});
}

function resetShare(context, user_id, share_id){
	$.ajax({
		url:context + "/share/resetShare",
		type:"post",
		dataType:"json",
		data:{"user_id" : user_id, "share_id" : share_id},
		success:function(result){
			alert(result.msg);
			$("#recycle_share").trigger("click");
		}
	});
}

function loadRecycleShare(context, user_id, user_name){
	$.ajax({
		url:context + "/share/loadRecycleShare",
		type:"post",
		dataType:"json",
		data:{"user_id":user_id},
		success:function(result){
			appendShareList(result, context, user_name);
		}
	});
}

function Share2Recycle(context, user_id, share_id){
	$.ajax({
		url:context + "/share/Share2Recycle",
		type:"post",
		dataType:"json",
		data:{"user_id": user_id, "share_id" : share_id},
		success:function(result){
			alert(result.msg);
			$("#user_share").trigger("click");
		}
	});
}

function deleteShare(context, user_id, share_id){
	$.ajax({
		url:context + "/share/deleteShare",
		type:"post",
		dataType:"json",
		data:{"user_id" : user_id, "share_id" : share_id},
		success:function(result){
			alert(result.msg);
			$("#recycle_share").trigger("click");
		}
	});
}


//刷新当前页面
function refresh(){
    window.location.reload();//刷新当前页面.
     
    //或者下方刷新方法
    //parent.location.reload()刷新父亲对象（用于框架）--需在iframe框架内使用
    // opener.location.reload()刷新父窗口对象（用于单开窗口
    //top.location.reload()刷新最顶端对象（用于多开窗口）
}

function checkGroup(context, user_id, group_name){
	
	$.ajax({
		url:context+"/friend/checkGroup",
		type:"post",
		dataType:"json",
		data:{"user_id":user_id, "group_name":group_name},
		success:function(result){
			if(result.status == 0){
				$(".wrong_group_msg").hide();
				$(".right_group_msg").html(result.msg).show();
			}else{
    			$(".right_group_msg").hide();
				$(".wrong_group_msg").html(result.msg).show();
			}
		}
	});
}

function loadGroupSelect(context, user_id){
	$("#group_select").empty();
	$(".wrong_group_msg").hide();
	$("#group_select").append("<option value='no_group'>不分组</option>");
	//加载所有组名
	$.ajax({
		url: context+"/friend/loadGroup",
		type:"post",
		data:{"user_id" : user_id},
		dataType:"json",
		success:function(result){
			var data = result.data;
			var option;
			for(var i=0; i<data.length; i++){
				option = "<option value='"+data[i]+"'>"+data[i]+"</option>";
				$("#group_select").append(option);
			}
		}
	});
}


function deleteGroup (context, user_id, group_name){
	$.ajax({
		url: context + "/friend/deleteGroup",
		type:"post",
		dataType:"json",
		data:{"user_id":user_id, "group_name":group_name},
		success:function(result){
			alert(result.msg);
			refresh();
		}
	});
}

function checkGroupToRename (context, user_id, old_group_name, group_name){
	if(group_name == old_group_name){
		alert("修改成功！！");
		refresh();
	}else{
		$.ajax({
			url:context+"/friend/checkGroup",
			type:"post",
			dataType:"json",
			data:{"user_id":user_id, "group_name":group_name},
			success:function(result){
				if(result.status == 0){
					groupRename (context, user_id, old_group_name, group_name);
				}else{
	    			$(".right_group_msg").hide();
					$(".wrong_group_msg").html(result.msg).show();
				}
			}
		});
	}
}

function groupRename (context, user_id, old_group_name, group_name){
	$.ajax({
		url:context + "/friend/groupRename",
		type:"post",
		dataType:"json",
		data:{"user_id" : user_id, "old_group_name" : old_group_name, "new_group_name" : group_name},
		success:function(result2){
			alert(result2.msg);
			refresh();
		}
	});
}

function addGroup (context, user_id){
	var group_name = $(".group_name_div").find("input").val().trim();
	$.ajax({
		url:context+"/friend/addGroup",
		type:"post",
		dataType:"json",
		data:{"user_id":user_id, "group_name" :group_name},
		success:function(result){
			alert(result.msg);
			loadGroup(user_id, context);
			loadFriend(user_id, context);
			loadGroupSelect(context, user_id);
			$(".add_group").trigger("click");
		}
	}); 
}

function changeGroup (context, user_id, friend_id, group_name){
	$.ajax({
		url:context+"/friend/changeGroup",
		type:"post",
		dataType:"json",
		data:{"user_id":user_id, "friend_id": friend_id, "group_name" : group_name},
		success:function(result){
			alert(result.msg);
			refresh();
		}
	});
}

function loadFriendInfo(context, user_id, friend_id){
	$.ajax({
		url:context+"/friend/loadFriendInfo",
		type:"post",
		dataType:"json",
		data:{"user_id":user_id, "friend_id":friend_id},
		success:function(result){
			if(result.status == 0){
				var friend = result.data.friend;
				var hobbys = result.data.hobby;
				var friend_sex = friend.friend_sex;
				var info_li = "<li>用户名：<p>"+friend.friend_name+"</p></br>";
				info_li += "昵称：<p>"+friend.nick_name+"</p></br>";
				if(friend_sex == 1){
					info_li += "性别：<p>男</p></br>";
				}else{
					info_li += "性别：<p>女</p></br>";
				}
				info_li += "电话号码：<p>"+friend.friend_phonenum+"</p></br>";
				info_li += "爱好：</br>";
				for(var i=0; i<hobbys.length; i++){
					info_li += "<p>"+hobbys[i].hobby_name+"</p></br>";
				}
				info_li += "</li>";
				$info_li = $(info_li);
				$info_li.data("friend_id", friend.friend_id);
				$("#user_info_list").append($info_li);
			}
		}
	});
}

function queryUserInfo(context, user_id){
	$.ajax({
		url:context+"/account/queryUserInfo",
		type:"post",
		dataType:"json",
		data:{"user_id":user_id},
		success:function(result){
			if(result.status == 0){
				var user = result.data.user;
				var hobbys = result.data.hobby;
				var user_sex = user.user_sex;
				var info_li = "<li>用户名：<p>"+user.user_name+"</p></br>";
				info_li += "昵称：<input type='text' class='nick_name' name='nick_name' value='"+user.nick_name+"'></br>";
				if(user_sex == 1){
					info_li += "性别：</br><label><input class='user_sex' name='user_sex' type='radio' value='1' checked/>男 </label> <label><input class='user_sex' name='user_sex' type='radio' value='0' />女</label> </br>";
				}else{
					info_li += "性别：</br><label><input class='user_sex' name='user_sex' type='radio' value='1' />男 </label> <label><input class='user_sex' name='user_sex' type='radio' value='0' checked/>女</label> </br>";
				}
				info_li += "电话号码：<input type='text' class='user_phone_num' name='user_phone_num' value='"+user.user_phone_num+"'></br>";
				info_li += "爱好：<a href='javascript:;' class='create_new_hobby'>+</a></br><div class='hobby_div'>";
				for(var i=0; i<hobbys.length; i++){
					info_li += "<div><input type='text' class='user_hobby' value='"+hobbys[i].hobby_name+"'><a class='delete_hobby' href='javascript:;'>X</a></br></div>";
				}
				info_li += "</div><button class='edit_submit'>修改</button></li>";
				$info_li = $(info_li);
				$info_li.data("user_id", user.user_id);
				$("#user_info_list").append($info_li);
			}
		}
	});
}

function updateUserInfo(user_id, nick_name, user_sex, user_phone_num, string_hobby){
	$.ajax({
		url:context+"/account/updateUserInfo",
		type:"post",
		dataType:"json",
		data:{"user_id":user_id, "nick_name":nick_name, "user_sex" : user_sex, "user_phone_num": user_phone_num, "string_hobby":string_hobby},
		success:function(result){
			alert(result.msg);
		}
	});
}
