<%--
- Author: ycw
- Date: 2012-7-10
- Description: 回复问题贴初始化主页面
--%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title> 回复问题贴初始化主页面</title>
	<%@ include file="/common/include/meta.jsp" %>
	<script src="${ctx}/scripts/custom/querySubmit.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
    <script src="${ctx}/scripts/custom/dateUtil.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function(){
		var chk_options = { 
			all:'#checkAll',
			item:'.checkItem'
		};
		$(this).chk_init(chk_options);
		changebasiccomponentstyle();
		changedisplaytagstyle();
		changecheckboxstyle();
	});
</script>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" bottommargin="0">
<form id="queryForm" action="queryAllQuestions.act" method="post" autocomplete="off">
   <h:panel id="query_panel" width="100%" title="查询条件">		
	 <table align="center" id="hide_table"  border="0" width="100%" class="form_table">
		  <tr>
				<td class="form_label" align="left">问题分类： </td>
				<td>
					<span class="form_label">
						  <s:select list="#application.DICT_QUEST_TYPE" name="questType" cssClass="input180box" 
						            emptyOption="false" headerKey=""  headerValue="全部" listKey="dictcode" listValue="dictname"/>
					</span>
				</td>
				<td class="form_label" >提出日期：</td>
				<td>
					<div id="recordTimeDiv" style="">
						<jsp:include page="/common/include/chooseDateRange.jsp">
							 <jsp:param name="dateStart" value="tquestModel.questTimeStart"/>
							<jsp:param name="dateEnd" value="tquestModel.questTimeEnd"/>
						</jsp:include>   
       				</div>   
			    </td>
		  </tr>
		   <tr>
				<td class="form_label" width="20%" align="left">状态：</td>
				<td width="30%">
					<span class="form_label">
					  	  <s:select name="questStatus"  id="questStatus"  headerValue="待审核" cssClass="input180box" 
						      list="#{'1':'待回复'}" 
						      value="#{'1':'待回复'}"/>
					</span>
				</td>
				<td class="form_label" align="left">优先级： </td>
				<td>
					<span class="form_label">
	                  <s:select list="#application.DICT_QUEST_LEVEL" name="questLevel" cssClass="input180box" 
					            emptyOption="false" headerKey=""  headerValue="全部" listKey="dictcode" listValue="dictname"/>
	                </span>
                </td>
			</tr>
			<tr class="form_bottom">
					<td colspan="4" class="form_bottom">
						<s:textfield name="page.pageSize" theme="simple" id="pageSize" cssStyle="width:20px" onblur="checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" maxlength="3"/>条/页&nbsp;&nbsp;
			          	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-search" onclick="submitForm()">查询</a>
			          	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-reload" onclick="$('#queryForm').resetForm();">重置</a>
						&nbsp;&nbsp;&nbsp;&nbsp;
						<span id="loadingdiv" class="icon-loding" style="display: none">正在执行请稍后</span>
					</td>
			</tr>
		</table>
	</h:panel>
	<h:page id="list_panel" width="100%"  title="查询结果">
		<div style="OVERFLOW-y:auto;overflow-x:hidden;">
			<d:table name="page.result" id="row"  export="false" class="table" requestURI="">
			    <d:col title="序号" class="display_centeralign">
						${row_rowNum+((page.pageNo-1)*page.pageSize)}
				</d:col>
				<d:col class="display_centeralign" title="选择  " >
					<input type="checkbox" name="_chk" class="checkItem" value="${row.QUEST_ID}"/>
				</d:col>	
			    <d:col property="QUEST_CODE"   class="display_centeralign"   title="问题号"/>
			    <d:col property="QUEST_TYPE" class="display_leftalign" dictTypeId="QUEST_TYPE"   title="问题帖分类"/>
			    <d:col property="QUEST_LEVEL" class="display_leftalign" dictTypeId="QUEST_LEVEL"   title="问题帖优先级"/>
			    <d:col property="QUEST_TITLE" class="display_leftalign" title="问题帖标题"/>
			    <d:col property="QUEST_USER" class="display_leftalign" title="问题提出人"/>
			    <d:col property="QUEST_TIME" class="display_leftalign" title="创建时间"/>
			    <d:col property="QUEST_STATUS" class="display_leftalign" dictTypeId="QUEST_STATUS" title="帖子状态"/>
			    <d:col class="display_centeralign"  media="html" title="帖子内容">
		    		<a href="javascript:void(0);" onclick="detail('${row.QUEST_ID}')">[查看]</a>&nbsp;
	    		</d:col>
			</d:table>
	    </div>
   </h:page>
	</form>
</body>
</html>
<script type="text/javascript">

//查询所有的问题贴列表
function submitForm(){
	$("#loadingdiv").show();
	disableButton();
	$("#pageNo").val("1");
    $("#queryForm")[0].submit();
}

//查看明细操作
function detail(id){
	var url = "${ctx}/common/answerQuestion/detail.act?questId="+id;
	openEasyWin("answer_question_detail","回复问题贴",url,"850","480",true);
}

</script>