<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2010-08-27 15:37:15
- Description: 机构维护页面
--%>
	<%--当前用户机构类型为根节点时可添加删除操作 并且不是   --%>
	<c:if test="${(_orgtype=='0' or _orgtype=='1') and parentOrgtype != '7' and parentOrgtype != '9' }">
		<c:set var="buttons" value="add,del"/>
	</c:if>
	<%--当前用户机构类型为总部时只能添加
	<c:if test="${_orgtype!='0' and treelevel!=2}">
		<c:set var="buttons" value="add"/>
	</c:if>--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>机构列表</title>	
	<%@ include file="/common/include/meta.jsp" %>
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
	<s:hidden name="parentid"/>
	<s:hidden name="treelevel"/>
	<s:hidden name="parentOrgtype"/>
	<s:hidden name="company"/>
 	<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center" id="hide_table"  border="0" width="100%" class="form_table">
        <tr>
          <td class="form_label" width="20%" align="left">
           	机构名称：
          </td>
          <td width="30%">
           <s:textfield name="orgname" theme="simple" size="30"/>  
          </td>
          <td class="form_label" width="20%" align="left">
       	  	机构编码：       	  	
          </td>
          <td width="30%">
            <s:textfield name="orgcode" theme="simple" size="30"/>  
          </td>
        </tr>     
        <tr>
          <td class="form_label" width="20%" align="left">
           	纳税人名称：
          </td>
          <td width="30%">
           <s:textfield name="taxname" theme="simple" size="30" />  
          </td>
          <td class="form_label" width="20%" align="left">
           	纳税人识别号：
          </td>
          <td width="30%">
           <s:textfield name="taxno" theme="simple" size="30" />  
          </td>
         
        </tr>  
        <tr>
          <td colspan="4" class="form_bottom">
          	<s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="width:20px" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />条/页&nbsp;&nbsp;
          	<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search"  onclick="querySubmit();">查询</a>
          	<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="resetCheckboxForm('#queryForm');">重置</a>
			<c:if test="${parentOrgtype=='1'}">
				<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton" iconCls="icon-ok" onclick="genTaxnoJson()">生成机构税号json</a>
				<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton" iconCls="icon-ok" onclick="genCodeJson()">生成机构代码json</a>
			</c:if>
          </td>
        </tr>
      </table>      
   </h:panel>
	<div id="pp" style="background:#efefef;border:1px solid #ccc;"></div>
	<h:page id="list_panel" width="100%" buttons="${buttons}" title="结果列表">
		<d:table name="page.result" id="row"  export="false" class="table"	requestURI="list.act?exFile=1">
			<d:col  style="width:30"  title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>
			<d:col  style="width:45"   class="display_centeralign"  title="选择<input type=\"checkbox\" id=\"checkAll\"/>" >
				<input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.orgid}"/>
			</d:col>	
		    <d:col property="orgcode"   class="display_centeralign"   title="机构编码"/>
		    <d:col property="orgname" class="display_leftalign" title="机构名称"/>
		    <d:col property="taxno" class="display_centeralign" title="纳税人识别号"/>
		    <d:col property="taxname" class="display_leftalign" title="纳税人名称"/>
		<c:if test="${parentOrgtype!='1'}">
			<d:col class="display_centeralign"  media="html" title="操作">
				<a href="javascript:void(0);" onclick="updateRecord('${row.orgid}')">[修改]</a>&nbsp;
			</d:col>	
		</c:if>			
		</d:table>
   </h:page>
	</form>
<script type="text/javascript">
	function addRecord(){
		var url = "";
		if($('#parentOrgtype').val()!='0'){
			url = "${ctx}/common/orgMgr/orgTypeSelect.act?parentid="
			 +$('#parentid').val()+"&treelevel="+$('#treelevel').val()
			 +"&parentOrgtype="+$('#parentOrgtype').val()+"&company="+$('#company').val();
		}else{
			url = "${ctx}/common/orgMgr/input.act?parentid="
			  +$('#parentid').val()+"&treelevel="+$('#treelevel').val()
			  +"&parentOrgtype="+$('#parentOrgtype').val();//+"&company="+$('#company').val();
		}
		openEasyWin("orgInput","录入机构信息",url,"800","450",true);
	}
	function updateRecord(id){
		var url = "${ctx}/common/orgMgr/input.act?orgid="+id+"&parentOrgtype="+$('#parentOrgtype').val();//+"&company="+$('#company').val();
		openEasyWin("orgInput","修改机构信息",url,"800","450",true);
	}
	function delRecord(){
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
	function getData(){ 
		$("#list").html("");//清空列表中的数据 
		//发送ajax请求
		$.getJSON("${ctx}/common/orgMgr/getJson.act",{orgname:$("#checkHidden").val(),orgcode:"20"},
			function(json){ 
				//循环取json中的数据,并呈现在列表中 
				$.each(json,function(i){ 
					$("#list").append("<li>机构名称:"+json[i].orgname+" 机构ID:"+json[i].orgid+"</li>") 
				}) 
			}
		)
	}
	function genTaxnoJson(){ 
		$.ajax({
		   type: "POST",
		   url: "${ctx}/common/orgMgr/getJson.act?type=1",
		   data: "company="+$('#company').val(),
		   dataType: 'json',
		   success: function(msg){
			if(msg['1']=="true"){
				easyAlert("提示信息", msg['msg'],"info");
			 }else{
				 easyAlert("提示信息", msg['msg'],"info");
			 }
		   },
		    error: function(msg, status, e){
				easyAlert("提示信息", "生成Json信息出错！","info");
			}
		});
	}
	function genCodeJson(){ 
		$.ajax({
		   type: "POST",
		   url: "${ctx}/common/orgMgr/getJson.act?type=2",
		   data: "company="+$('#company').val(),
		   dataType: 'json',
		   success: function(msg){
			if(msg['1']=="true"){
				easyAlert("提示信息", msg['msg'],"info");
			 }else{
				 easyAlert("提示信息", msg['msg'],"info");
			 }
		   },
		    error: function(msg, status, e){
				easyAlert("提示信息", "生成Json信息出错！","info");
			}
		});
	}
</script>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>
