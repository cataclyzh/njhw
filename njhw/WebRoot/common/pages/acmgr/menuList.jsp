<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2010-08-31 19:27:22
- Description:功能菜单列表管理页面
--%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>功能菜单列表</title>	
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
		
		function openDesign(id,name,version,state,modify,processesId,flag){
			var deptId=1;
			var bundleId=1;
			if(deptId!=""&&bundleId!=""&&processesId!=""){
				var url="${ctx}/flowdesign/webflow.html?id="+id+"&name="+name+"&version="+version+"&state="+state+"&modify="+modify+"&deptId="+deptId+"&bundleId="+bundleId+"&processesId="+processesId;
				window.open(url);
			}
		}
	</script>
<style>
</style>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" >
	<form id="queryForm" action="list.act" method="post"  autocomplete="off">
	<s:hidden name="parentid"/>
	<s:hidden name="orgid"/>
	
 	<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center"  id="hide_table" border="0" width="100%" class="form_table">
        <tr>
          <td class="form_label" width="20%" align="left">
           	功能菜单名称：
          </td>
          <td width="30%">
           <s:textfield name="menuname" theme="simple" size="18"/>  
          </td>
          <td class="form_label" width="20%" align="left">
       	  	功能菜单编码：       	  	
          </td>
          <td width="30%">
            <s:textfield name="menucode" theme="simple" size="18"/>  
          </td>
        </tr>     
        <tr>
          <td colspan="4" class="form_bottom">
            <s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="width:20px" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />条/页&nbsp;&nbsp;
     href="javascript:void(0);" href="#" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search" onclick="querySubmit();">查询</ahref="javascript:void(0);" href="#" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="resetCheckboxForm('#queryForm');">重置</a>
          </td>
        </tr>
      </table>      
   </h:panel>
	<h:page id="list_panel" width="100%" buttons="add,del" title="结果列表">		
		<d:table name="page.result" id="row"  export="false" class="table" requestURI="list.act?exFile=1">	
			<d:col style="width:30" title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>
			<d:col  style="width:45"   class="display_centeralign"   title="选择<input type=\"checkbox\" id=\"checkAll\"/>" >
				<input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.menuid}"/>
			</d:col>
		    <d:col property="menucode"   class="display_centeralign"   title="菜单编码"/>
		    <d:col property="menuname" class="display_leftalign" title="菜单名称"/>
		    <d:col property="menulabel" class="display_leftalign" title="菜单显示名称"/>
			<d:col class="display_centeralign"  media="html" title="操作">				href="javascript:void(0);" href="#" onclick="updateRecord('${row.menuid}')">[修改]</a>&nbsp;
			</d:col>	
			<d:col class="display_centeralign"  media="html" title="流程href="javascript:void(0);"<a href="#" onclick="openDesign('4028814d3285ce32013285ef36e90020','fabuwendang','0','1','0','4028814d3285ce32013285eed047001f','')">[定义]</a>&nbsp;
			</d:col>			
		</d:table>
   </h:page>
	</form>
<script type="text/javascript">
	function addRecord(){
		var url = "${ctx}/common/menuMgr/input.act?orgid="+$('#orgid').val()+"&parentid="+$('#parentid').val();
		openEasyWin("winId","录入功能菜单信息",url,"800","400",true);
	}
	function updateRecord(id){
		var url = "${ctx}/common/menuMgr/input.act?menuid="+id;
		openEasyWin("winId","修改功能菜单信息",url,"800","400",true);
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
</script>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>
