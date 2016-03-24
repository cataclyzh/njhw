<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<title>已收传真页面</title>
		<link type="text/css" rel="stylesheet" href="css/getFax.css" />
		<link type="text/css" rel="stylesheet" href="css/table.css" />
		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js"
			type="text/javascript"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
		<script>
    	function goPage(pageNo) {
    		$("#pageNo").val(pageNo);
    		searchData();
    	}
    	
    	function showRequest(){
    	
    	}
    	function searchData(){
	    	var options = {
				type:'post',
				dataType:'html',
				beforeSubmit:showRequest,
				success: showResponse
			}; 
    		$('#receiveFax').ajaxSubmit(options);
    	}
    	/**
    	* 异步请求结果
    	*/
		function showResponse(responseText,statusText) {
		   if(statusText=="success"){
			 	$("#receive_fax_list_page").replaceWith(responseText);
		   } else {
				alert("查询失败!");
		   }
	    }
	    
	    $(function(){
	    	searchData();
	    });
	    
	    
	      function delRecord(){
			var ids = "";
			$("input[id^='chk_']:checked").each(function(i) {
				ids += ($(this).val() + ",");
			});
		
			
			$.ajax({
				type: "POST",
				url: "${ctx}/app/fax/deleteReceiveFax.act",
				data: {"ids": ids},
				dataType: 'text',
				async : false,
				success: function(msg){
					  if(msg == "success"){
			
						searchData();
					  } else {
						 easyAlert("提示信息", "删除失败！","info");
					  }
				 },
				 error: function(msg, status, e){
					 easyAlert("提示信息", "验证分配出错！","info");
				 }
			 });
	    }
	    /**
	    * 同步已发传真
	    */
	    function synReceiveFax(){
	    	var data = null;
			var sucFun = function(data) {
				if (data.result=='1'){
					alert("同步传真成功!");
				} else {
					alert("同步传真失败!");
				}
			};
			var errFun = function() {
				alert("服务器异常,同步传真失败!");
			};
			var url = "${ctx}/app/fax/synReceiveFax.act";
			ajaxQueryJSON(url,data,sucFun,errFun);
	    }
    </script>

	</head>
	<body>
		<div class="main">
			<div class="main_left_top_span">
				<span>已收传真</span>
			</div>
			<div class="main_top">
				<div class="main_top_btn1" onclick="delRecord()"></div>
				<input type="button" value="同步" class="main_top_btn3" onclick = "javascript:synReceiveFax();"/>
				<form action="receiveFax.act" id="receiveFax">
					<table class="main_top_table">
						<tr>
							<td>
								<input type="hidden" id="pageNo" name="pageNo" value="1" />
								<span>时间</span>
							</td>
							<td>
								<input type="text"
									onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss' })"
									id="receiveFaxStartDate" name="receiveFaxStartDate" class="table_input" />
									<span>-</span>
									<input type="text"
									onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss' })"
									id="receiveFaxEndtDate" name="receiveFaxEndtDate" class="table_input" />
							</td>
							<td style="padding-left: 10px" id="callerNumber"
								name="callerNumber">
								<span>主叫号</span>
							</td>
							<td>
								<input type="text" class="table_input" />
							</td>
							<td style="padding-left: 10px">
								<span>状态</span>
							</td>
							<td>
								<select id="sendsStauts" name="sendsStauts">
									<option value="">
										全部
									</option>
									<option value="1xx">
										1xx
									</option>
									<option value="200">
										200成功
									</option>
									<option value="3xx">
										3xx失败待重试
									</option>
									<option value="4xx">
										4xx最终失败
									</option>
								</select>
							</td>
						</tr>
					</table>
				</form>
				<div class="main_top_btn2" onclick="searchData();"></div>
			</div>
			<div id="receive_fax_list_page">

			</div>
	</body>

</html>