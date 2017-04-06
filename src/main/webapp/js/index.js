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
	
	var dataTree = data;
    var childArray = new Array();
    var childIndex =0;
    var result='';
    for(var i = 0;i<dataTree.length;i++){
    	if(dataTree[i].parent_id != 0){
    		childArray[childIndex] = dataTree[i];
    		childIndex++;
    	}else{
    		var sli = '<li id='+dataTree[i].friend_id+'><a><i class="icon-font">&#xe610</i><span>'+dataTree[i].friend_name+'</span></a></li>';
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
	    	
	    	result += '<li class="menu-header">' + sli;    	
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
			var sli = '<li id='+childArray[i].friend_id+'><a><i class="icon-font">&#xe610</i><span>'+childArray[i].friend_name+'</span></a></li>';
//			$li = $(sli);
//			$li.data("friend_id",childArray[i].friend_id);
			childResult += sli;    
		}
	}
	return childResult;
}

function loadHotShare(user_id ,index, context, user_name){
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




















