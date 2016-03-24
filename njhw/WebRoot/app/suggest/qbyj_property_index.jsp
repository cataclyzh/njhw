<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN" >
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html>
<head>
<%@ include file="/common/include/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>意见箱管理</title>
<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js"
	type="text/javascript"></script>
<link href="${ctx}/app/portal/toolbar/css/toolbar.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/app/suggest/css/qbyj_index.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/app/integrateservice/css/css.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/app/integrateservice/css/css_body.css" rel="stylesheet"
	type="text/css" />
<link href="${ctx}/app/integrateservice/css/css_index.css" rel="stylesheet"
	type="text/css" />		
<link href="${ctx}/app/integrateservice/css/wizard_css.css"
	rel="stylesheet" type="text/css" />
<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/easyui/easyloader.js"
	type="text/javascript"></script>
<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
<script type="text/javascript"
	src="${ctx}/app/portal/toolbar/showModel.js"></script>
<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
<!--<script src="${ctx}/scripts/placeholder/jquery.placeholder.1.3.min.js" type="text/javascript"></script>-->
<script type="text/javascript">
			
//搜索意见和回复
function searchReply(){
	var url = "${ctx}/app/suggest/replyList.act";
	var startDate = $("#startDate").val();
	var endDate = $("#endDate").val();
	var status = $("#status").val();
	var content = $("#content").val();
	var pageNo = $("#pageNo").val();
	var data = {startDate:startDate,endDate:endDate,status:status,content:content,pageNo:pageNo};
	sucFun = function (data){
		$("#qbyj_conter_div").html(data);
	};
	errFun = function (data){
		easyAlert('提示信息','加载数据出错!','info');
	};
	ajaxQueryHTML(url,data,sucFun,errFun);
}

function searchPageReply(){
	var url = "${ctx}/app/suggest/replyList.act";
	var startDate = $("#startDateHidden").val();
	var endDate = $("#endDateHidden").val();
	var status = $("#statusHidden").val();
	var content = $("#contentHidden").val();
	var pageNo = $("#pageNo").val();
	var data = {startDate:startDate,endDate:endDate,status:status,content:content,pageNo:pageNo};
	sucFun = function (data){
		$("#qbyj_conter_div").html(data);
	};
	errFun = function (data){
		easyAlert('提示信息','加载数据出错!','info');
	};
	ajaxQueryHTML(url,data,sucFun,errFun);
}

//点击搜索
function btnSearchSuggest(){
	$("#pageNo").val(1);
	searchReply();
}

$(function(){
	//初始意见箱管理列表
	searchReply();
	//$.Placeholder.init({ color : 'rgb(255, 255, 0)' });
	//$.Placeholder.cleanBeforeSubmit();
	
});

//分页跳转
function goPage(pageNo){
	if (null != pageNo){
		$("#pageNo").val(pageNo);
	}
	searchPageReply();
}

//显示隐藏回复
function toggleReply(sugid,obj){
	$("div[id^='"+sugid+"_']").toggle();
	
	if($(obj).hasClass('qbyj_no_a')){
		$(obj).attr("class","qbyj_no_as");
		$(obj).html("查看回复");
	}else{
		$(obj).attr("class","qbyj_no_a");
		$(obj).html("收起回复");
	}
	
}
	
//删除意见
function delSuggest(sugids,obj){
	openAdminConfirmWin(delSuggestFn,{sugids:sugids,obj:obj});
}

function delSuggestFn(params){
	//提示		
	easyConfirm('提示', '确定删除？', function(r){
		if (r){
			var url = "${ctx}/app/suggest/delSuggest.act";
	
			$.ajax({
				async:false,
			   type: "POST",
			   url: url,
			   data: {
			   	   sugids:params.sugids
			   },
			   success: function(msg){
			   searchReply();
			   	if(msg='success')
			   	{
			     easyAlert('提示信息','删除成功！','info');
			     
			     }else
			     {
			     	easyAlert("提示信息", "删除出错","info");
			     }
			     
			   }
			}); 	
		}else{
			return ;
		}
	});		

}


//删除回复
function delSuggestReply(repid,sugid,obj){
	openAdminConfirmWin(delSuggestReplyFn,{repid:repid,sugid:sugid,obj:obj});
}

function delSuggestReplyFn(params){
	//提示		
	easyConfirm('提示', '确定删除？', function(r){
		if (r){
			var url = "${ctx}/app/suggest/delSuggestReply.act";
	
			$.ajax({
			   async:false,
			   type: "POST",
			   url: url,
			   data: {
			   	   repid:params.repid
			   },
			   success: function(msg){
				   $("#div_"+params.sugid+"_id").html(msg);
				   easyAlert('提示信息','删除成功！','info');
				     
			   }
			}); 	
		}else{
			return ;
		}
	});		

}

//编辑答复，答复意见
function viewSuggestReply(sugId,repId){
	var title="";
	if(!sugId){
		sugId = "";
	}
	if(repId){
		title="编辑答复";
	}else{
		title="答复意见";
		repId = "";
	}
	var url = "";
	var pageNo = $("#pageNo").val();
	url = "${ctx}/app/suggest/findSuggestReplyById.act?sugid="+sugId+"&repid="+repId+"&pageNo="+pageNo;
	showShelter("700","510",url);
	//openEasyWin("qbyjEdit",title,url,"700","510",false);
}

function frameDialogClose (responseText,sugid){
	deleteDiv("fade");
	deleteDiv("Main_fade_iframe");
	$("#div_"+sugid+"_id").html(responseText);
}
</script>
</head>
<body>
	<div class="main_index">
		<c:if test="${param.type eq 'A'}">
			<jsp:include page="/app/integrateservice/headerWy.jsp">
			    <jsp:param value="/app/integrateservice/init.act" name="gotoParentPath"/>
			</jsp:include>
		</c:if>
		<c:if test="${param.type ne 'A'}">
			<jsp:include page="/app/integrateservice/header.jsp">
			    <jsp:param value="/app/pro/propertyIndex.act" name="gotoParentPath"/>
			</jsp:include>
		</c:if>
		<div class="index_bgs" style="padding: 0px 10px 10px 10px;">
			<div class="bgsgl_border_left">
				<!--此处写页面的标题 -->
				<div class="qbyj_bgsgl_border_right" style="background: url('${ctx}/styles/images/border_left.jpg') no-repeat scroll left top transparent;color: #FFFFFF;font-family:'微软雅黑';font-size: 18px;font-weight: bold;height: 36px;line-height: 36px;padding-left: 30px;">意见箱管理</div>
			</div>
			<div class="bgsgl_conter" style="min-height: 300px">
				<!--此处写页面的内容 -->
				<div class="qbyj_top">
				<h:panel id="query_panel" width="100%" title="查询条件">
					<table align="center" id="hide_table"  border="0" width="100%" class="form_table">
						<tr>
							<td width="10%">
								&nbsp; <input id="pageNo" value="${pageNo==null?1:pageNo}" type="hidden">
							</td>
							<td align="right">时间:</td>
							<td align="right" width="10%">
								<input class="qbyj_input_text" name="startDate" id="startDate" placeholder="输入时间 年-月-日" type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true,maxDate:'#F{$dp.$D(\'endDate\',{M:-0,d:0})||\'2020-10-01\'}',el:'startDate'})" /></td>
							<td width="1%">
								至&nbsp;&nbsp;
							</td>
							<td width="10%">
								<input class="qbyj_input_text" name="endDate" id="endDate" placeholder="输入时间 年-月-日" type="text"onclick="WdatePicker({dateFmt:'yyyy-MM-dd',isShowClear:true,readOnly:true,minDate:'#F{$dp.$D(\'startDate\',{M:-0,d:0})}',maxDate:'2020-10-01',el:'endDate'})" /></td>
							<td width="1%">
								&nbsp;&nbsp;
							</td>
							<td align="right">关键字:</td>
							<td width="10%">
								<input class="qbyj_input" name="content" id="content" placeholder="输入关键字" type="text" /></td>
							<td width="4%">
								状态
							</td>
							<td>
								<select class="qbyj_select" name="status" id="status">
									<option value="">全部</option>
									<option value="1">已回复</option>
									<option value="0">未回复</option>
								</select>
							</td>
							<td>
								<a href="javascript:void(0);" onclick="btnSearchSuggest();">
									<img src="images/qbyj_sou.jpg" width="65" height="22" border="0" />
								</a>
							</td>
						</tr>
					</table>
					</h:panel>
				</div>
				<div class="qbyj_conter" id="qbyj_conter_div">
				</div>
				<div class="bgsgl_clear"></div>
			</div>
		</div>
		<jsp:include page="/app/integrateservice/footer.jsp" />
	</div>
</body>
</html>