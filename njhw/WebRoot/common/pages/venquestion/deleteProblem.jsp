<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: XieRX
- Date: 2012-7-10
- Description: 问题帖删除页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>问题帖列表</title>	
	<%@ include file="/common/include/meta.jsp" %>
	<script type="text/javascript" src="${ctx}/scripts/custom/display.js"></script>
	<script src="${ctx}/tesco/scripts/dateUtil.js" type="text/javascript"></script>
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
<body topmargin="0" leftmargin="0" rightmargin="0" style="background: #f1f8fd;">
<div id="list"></div>
	<form id="queryForm" action="list.act" method="post"  autocomplete="off">
 	<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center" id="hide_table"  border="0" width="100%" class="form_table">
         <tr>
			<td class="form_label" align="left">问题分类： </td>
			<td><span class="form_label">
			  <s:select list="#application.DICT_QUEST_TYPE" name="questType" cssClass="input180box" emptyOption="false" headerKey=""  headerValue="全部" 
		           			listKey="dictcode" listValue="dictname"/>
			</span></td>
			<td class="form_label" >提出日期：</td>
			<td>
				<s:textfield name="dateStart" theme="simple" maxlength="10" size="12" onblur="requireDate(\"%{#request.dateStart}\");" cssClass="Wdate"
            	 onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',isShowClear:false,errDealMode:1})"/>
				至 
				<s:textfield name="dateEnd" theme="simple" maxlength="10" size="12" onblur="requireDate(\"%{#request.dateEnd}\");" cssClass="Wdate" 
             onclick="WdatePicker({skin:'whyGreen',dateFmt:'yyyy-MM-dd',isShowClear:false,errDealMode:1})"/>
			</td>
		  </tr>
			<tr>
				<!-- 
					<td class="form_label" width="20%" align="left">状态：</td>
				<td width="30%"><span class="form_label">
				  <s:select list="#application.DICT_QUEST_STATUS" name="questStatus" cssClass="input180box" emptyOption="false" headerKey=""  headerValue="全部" 
		           			listKey="dictcode" listValue="dictname"/>
				</span></td>
				 -->
				 <td class="form_label" width="20%" align="left">问题号：</td>
				
				<td width="30%"><s:textfield name="questCode" theme="simple" size="30"/>  </td>
				<td class="form_label" align="left">优先级： </td>
				<td><span class="form_label">
                  <s:select list="#application.DICT_QUEST_LEVEL" name="questLevel" cssClass="input180box" emptyOption="false" headerKey=""  headerValue="全部" 
		           			listKey="dictcode" listValue="dictname"/>
                </span></td>
			</tr>
        <tr>
          <td colspan="4" class="form_bottom">
          	<s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="width:20px" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />条/页&nbsp;&nbsp;
          	<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search"  onclick="querySubmit();">查询</a>
          	<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="resetCheckboxForm('#queryForm');">重置</a>
          </td>
        </tr>
      </table>      
   </h:panel>
	<div id="pp" style="background:#efefef;border:1px solid #ccc;"></div>
	<h:page id="list_panel" width="100%" buttons="del" title="结果列表">
		<d:table name="page.result" id="row"  export="false" class="table"	requestURI="list.act?exFile=1">
			<d:col  style="width:30"  title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>
			<d:col  style="width:45" class="display_centeralign"  title="选择<input type=\"checkbox\" id=\"checkAll\"/>" >
				<input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.questId}"/>
			</d:col>	
			<d:col property="questCode"   class="display_centeralign"   title="问题号"/>
		    <d:col property="questType" dictTypeId="QUEST_TYPE" class="display_leftalign" title="问题帖分类"/>
		    <d:col property="questLevel" dictTypeId="QUEST_LEVEL" class="display_leftalign" title="问题帖优先级"/>
		    <d:col property="questTitle" class="display_centeralign"   title="问题帖标题"/>
		    <d:col property="questUser" class="display_centeralign"   title="问题提出人"/>
		    <d:col property="questStatus" dictTypeId="QUEST_STATUS" class="display_leftalign" title="帖子状态"/>
		    <d:col property="questTime" class="display_centeralign"   title="创建时间"/>
			<d:col class="display_centeralign"  media="html" title="问题帖内容">
				<a href="javascript:void(0);" onclick="selectRecord('${row.questId}')">[查看]</a>&nbsp; 
			</d:col>
		</d:table>
   </h:page>
	</form>

	<script type="text/javascript">
	function selectRecord(id){
		var url = "${ctx}/common/ques/showProblem.act?questId="+id;
		openEasyWin("showProblem","问题帖内容",url,"800","450",true);
	}
	function delRecord(){
		 /*
		 var cbs = $("input[@type=checkbox]:checked");  
		 alert(cbs);
		    var v = "";  
		    alert(cbs.length);
		    for(var i = 0; cbs && i < cbs.length; i++) {  
		        v += cbs[i].value;  
		    } 
		    alert(v);
		
		    var checkedCompanyIds = $("input:checked[name='_chk']");
			 $.each(checkedCompanyIds, function(index,item){
	             var checkedValue = $(item).val();
	             alert(checkedValue);
	         }    
		 
			 */ 
		if($("input[@type=checkbox]:checked").size()==0){
			easyAlert('提示信息','请勾选要删除的记录！','info');
		}else{
			easyConfirm('提示', '确定删除？', function(r){
				if (r){
					$('#queryForm').attr("action","delete.act");
					$('#queryForm').submit();
				}
			});		
		}
    }
</script>
 
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>
