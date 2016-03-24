<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2010-08-31 15:37:15
- Description: 用户维护页面
--%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>用户列表</title>	
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
<style>
</style>
</head>
<body topmargin="0" leftmargin="0" rightmargin="0" >
	<form id="queryForm" action="list.act" method="post"  autocomplete="off">
	 <s:hidden name="orgid"/>
 	<h:panel id="query_panel" width="100%" title="查询条件">	
      <table align="center"  id="hide_table" border="0" width="100%" class="form_table">
        <tr>
          <td class="form_label" width="20%" align="left">
           	用户名称：
          </td>
          <td width="30%">
           <s:textfield name="username" theme="simple" size="18"/>  
          </td>
          <td class="form_label" width="20%" align="left">
       	  	用户编码：       	  	
          </td>
          <td width="30%">
            <s:textfield name="usercode" theme="simple" size="18"/>  
          </td>
        </tr>     
        <tr>
          <td colspan="4" class="form_bottom">
            <s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="width:20px" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />条/页&nbsp;&nbsp;
          	<ahref="javascript:void(0);"" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search" onclick="querySubmit();">查询</a>
          	<ahref="javascript:void(0);"" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="resetCheckboxForm('#queryForm');">重置</a>
          </td>
        </tr>
      </table>      
   </h:panel>
	<h:page id="list_panel" width="100%" buttons="add,del" title="结果列表">		
		<d:table name="page.result" id="row" uid="row" export="false" class="table"	requestURI="list.act?exFile=1">	
			<d:col style="width:30" title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>
			<d:col   style="width:45"   class="display_centeralign"  title="选择<input type=\"checkbox\" id=\"checkAll\"/>" >
				<input type="checkbox" name="_chk" id="_chk" class="checkItem" value="${row.userid}"/>
			</d:col>	
		    <d:col property="usercode"   class="display_centeralign"   title="用户编码"/>
		    <d:col property="username" class="display_leftalign" title="用户名称"/>
		    <d:col property="loginname" class="display_centeralign" title="登录名"/>
		   <%--<d:col property="birthday" class="display_centeralign"  format="{0,date,yyyy年MM月dd日 }"  title="出生日期"/>
 			<d:col property="sex" dictTypeId="SEX" class="display_centeralign" title="性别"/> --%>
			<d:col class="display_centeralign"  media="html" title="操作">				
				<ahref="javascript:void(0);"" onclick="updateRecord('${row.userid}')">[修改]</a>&nbsp;	
				<c:if test="${row.status eq '3'}">
				<span id="unlock_${row.loginname}">
			href="javascript:void(0);"="#" onclick="unlock('${row.loginname}')">[解锁]</a>&nbsp;
				</span>
				</c:if>
			</d:col>				
		</d:table>
   </h:page>
	</form>
<script type="text/javascript">
	function addRecord(){
		var url = "${ctx}/common/userMgr/input.act?orgid="+$('#orgid').val();
		openEasyWin("winId","录入用户信息",url,"800","400",true);
	}
	function updateRecord(id){
		var url = "${ctx}/common/userMgr/input.act?userid="+id+"&orgid="+$('#orgid').val();
		openEasyWin("winId","修改用户信息",url,"800","400",true);
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

	//解锁用户
    function unlock(loginname){
        $.ajax({
            type: "POST",
            url: "${ctx}/common/userMgr/unlock.act",
            data: "loginname="+loginname,
            dataType: 'text',
            async : false,
            success: function(msg){
                  if(msg == "true"){
                	  easyAlert("提示信息", "解锁成功！","info");
                	  $("#unlock_" + loginname).hide();
                  }
                  else{
                      easyAlert("提示信息", "解锁失败！","info");
                  }
             },
             error: function(msg, status, e){
                 easyAlert("提示信息", "解锁出错！","info");
             }
         });
    }

</script>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>
