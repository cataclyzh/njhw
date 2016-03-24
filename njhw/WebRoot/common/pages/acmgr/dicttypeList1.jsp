<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/pager.tld" prefix="w" %>
<%--
- Author: WXJ
- Date: 2010-09-06 15:37:15
- Description: 业务字典类型页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/include/meta.jsp" %>
	<title>业务字典类型列表</title>	
			<script type="text/javascript">
	$(document).ready(function() {
			var chk_options = { 
				all:'#checkAll',
				item:'.checkItem'
			};
			$(this).chk_init(chk_options);
			//聚焦第一个输入框
			$("#dicttypename").focus();
			changebasiccomponentstyle();			
			changedisplaytagstyle();
			changecheckboxstyle();
		});
	</script>
<style>
</style>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" >
<input type="hidden" id="dicttypenameHidden" value="${dicttypename }"/>
<input type="hidden" id="dicttypecodeHidden" value="${dicttypecode }"/>
   <table class="main_table" border="0" style="border-collapse: collapse;">
	<tr class="table_header">
		<td>
			<span class="table_span">类型名称</span>
		</td>
		<td>
			<span class="table_span">类型编码</span>
		</td>
		<td>
			<span class="table_span">操作</span>
		</td>
	</tr>
	<c:forEach items="${page.result}" var="dict" varStatus="logStatus">
		<tr>
			<td>
				${dict.dicttypename }
			</td>
			<td>
				${dict.dicttypecode }
			</td>
			<td>
				<a href="javascript:void(0);" onclick="dictDeta('${dict.dicttypeid}')">[详细]</a>
			</td>
		</tr>
	</c:forEach>
</table>
<w:pager pageSize="${page.pageSize}" pageNo="${page.pageNo}"  url="${ctx}/common/dictMgr/list.act" recordCount="${page.totalCount}"/>
<script type="text/javascript">
	function addRecord(){
		var url = "${ctx}/common/dictMgr/dicttypeinput.act";
		openEasyWin("winId","录入字典类型信息",url,"600","400",true);
	}
	function updateRecord(id){
		var url = "${ctx}/common/dictMgr/dicttypeinput.act?dicttypeid="+id;
		openEasyWin("winId","修改字典类型信息",url,"600","400",true);
	}
	function delRecord(){
		if($("input[@type=checkbox]:checked").size()==0){
			easyAlert('提示信息','请勾选要删除的记录！','info');
		}else{
			
		
		
			easyConfirm('提示', '确定删除？', function(r){
				if (r){
					$('#queryForm').attr("action","deleteType.act");
					$('#queryForm').submit();
				}
			});		
		}
    }
	function refreshCache(){
		$('#queryForm').attr("action","refreshCache.act");
		$('#queryForm').submit();
    }
	function dictDeta(id){
		var url = "${ctx}/common/dictMgr/dictdetaDia.act?dicttype="+id;
		openEasyWin("dictInput","业务字典明细",url,"450","450",false);
	}

	function checkDetas(){ 
		//发送ajax请求
		$.getJSON("${ctx}/common/dictMgr/getDetas.act",{orgname:"test",orgcode:"20"},
			function(json){ 
				//循环取json中的数据,并呈现在列表中 
				$.each(json,function(i){ 
					$("#list").append("<li>机构名称:"+json[i].orgname+" 机构ID:"+json[i].orgid+"</li>") 
				}) 
			}
		)
	}
</script>
</body>
</html>
