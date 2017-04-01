<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>upload</title>
 	<script type="text/javascript" src ="../js/jquery-1.9.1.min.js" ></script>
    <script type="text/javascript">
    	$(function(){
    		var session = "${sessionScope.user_id}";
    		alert("upload:"+"${sessionScope.user_name}");
    		if(undefined == session ||"" == session){
    			window.location.href="../account/toLogin";
    		} 
    		$("#add_image").click(function(){
    			var input = '<div>上传图片：<input type="file" name="share_image"/><a href="javascript:void(0);">X</a><br/></div>';
    			$("#image_div").append(input);
    		})
    		$("#image_div").on("click","a", function(){
    			$(this).parent().remove();
    		});
    		$("#share_btn").click(function(){
    			var form = new FormData(document.getElementById("share_form"));
    			$.ajax({
    				url:"${pageContext.request.contextPath}/share/sendShare",
    				type:"post",
    				dataType : "json",
    				processData:false,
                    contentType:false,
    				data: form,
    				success : function(data){
    					if(data.status == 0){
    						alert(data.msg);
    						window.location.href="${pageContext.request.contextPath}/account/redirectIndex";
    					}
    				}
    			});
    		});
    		
    	});
    </script>
</head>
<body>
	<%-- <form action="<%=request.getContextPath()%>/share/sendShare" 
		method="post" enctype="multipart/form-data"> --%>
	<form id="share_form" enctype="multipart/form-data">
		文字信息：<input type="text" name="share_msg"/><br/>
		<div id="image_div">
			上传图片：<input type="file" name="share_image" /><br/>
		</div>
		<a href="javascript:void(0);" id="add_image">+</a><br/>
		添加tag,不同的tag用","分隔:<input type="text" name="share_tag" /><br/>
		<!-- 上传文件2：<input type="file" name="file2" /><br/> -->
		<input id="share_btn" type="button" value="提交" />
	</form>
	<%-- <img alt="" src="<%=request.getContextPath()%>/upload/2/0/5494db00-fca1-4a16-868e-33b8fe4f6d4amoon_bg.jpg">	 --%>
</body>
</html>