<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%@ taglib uri="/WEB-INF/tlds/pager1.tld" prefix="page" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>设备授权管理</title>
<script src="${ctx}/app/portal/toolbar/showModel.js" type="text/javascript" ></script>
<meta http-equiv="X-UA-Compatible" content="IE=8" />
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
<meta http-equiv="Cache-Control" content="no-store"/>
<meta http-equiv="Pragma" content="no-cache"/>
<meta http-equiv="Expires" content="0"/>
<script type="text/javascript">var _ctx = '${ctx}';</script>
<link href="${ctx}/styles/default.all.css" rel="stylesheet" type="text/css"/>
<link href="${ctx}/app/portal/toolbar/css/toolbar.css" rel="stylesheet" type="text/css"/>
<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
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
.table_btn_orgframe
{
    width: 68px;
    height: 22px;
    background:#8090b2;
	color:#fff;
	line-height:22px;
	text-align:center;
	font-family:"微软雅黑";
	margin-right:10px;
	float:right;
	cursor:pointer;
	text-decoration: none;
}
</style>	
</head>


<body style="background:#fff;height: 100%">
<div class="index_bgs">
	<div class="bgsgl_border_left">
	  	<div class="bgsgl_border_right">设备授权管理</div>
	</div>
	<div class="bgsgl_conter" style="height:570px">
	<form id="queryForm" action="deviceAuthManger.act" method="post"  autocomplete="off">
	<s:hidden name="PId"/>
	<s:hidden name="orgId"/>
	<s:hidden name="levelNum"/>
 	<h:panel id="query_panel"  width="100%"  title="查询条件">	
      <table align="center" id="hide_table"  border="0" width="100%" class="form_table">
        <tr>
          <td class="form_label" width="15%" align="left">
                         	姓名：
          </td>
          <td width="25%">
           <s:textfield name="displayName" id="displayName" theme="simple" size="20" cssStyle="height:15px"/>  
          </td>
          <td class="form_label" width="15%" align="left">
       	  	单位：       	  	
          </td>
          <td width="25%">
            <s:textfield name="orgName" id='orgName' theme="simple" size="20" cssStyle="height:15px"/>  
          </td>
          <td rowspan="2" align="left" width="20%">
          <div style="float:left">
          	<a href="javascript:void(0);" class="table_btn_orgframe" id="resetbutton"  onclick="reset()">重置</a>
          	<a href="javascript:void(0);" class="table_btn_orgframe" id="searchbutton"   onclick="querySubmit();">查询</a>
         </div>
         </td>
	</tr>
	<tr>
		  <td class="form_label" width="15%" align="left">
       	 	申请时间  	
          </td>
          <td width="25%">
           <input size="20" name="appTime" id="appTime" style="height:15px" value = "${appTime}"  type="text" onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss'})"/>
          </td>
          <td class="form_label" width="15%" align="left">
       	  	状态   	  	
          </td>
         <td width="25%">
            	<s:select list="#application.DICT_DEVICE_AUTH_STATUS"  cssStyle="width:60%;list-style:none;height:20px"  headerKey="" headerValue="全部" id = "status" listKey="dictcode" listValue="dictname" name="status"/>
          </td>
      
     </tr>     
      </table>      
   </h:panel>
	<page:pager id="list_panel" width="100%" buttons="" title="结果列表">
		<d:table name="page.result" id="row"  export="false" class="table">
		    <d:col property="DISPLAY_NAME"   class="display_leftalign"   title="名称"/>
		    <d:col property="APP_TIME" class="display_leftalign" title="申请时间"/>
			<d:col property="ORG_NAME" class="display_leftalign" title="单位"/>
	    	<d:col property="DEPARTMENT_NAME" class="display_leftalign" title="处室"/>
	    	<d:col property="APPSTATUS" class="display_leftalign" title="状态"/>
		</d:table>
   </page:pager>
</form>
		</div>
		<div class="clear"></div>
	</div>
<script type="text/javascript">
function reset()
{
  $("#displayName").val('');
  $("#orgName").val('');
  $("#status").val('');
  $("#appTime").val('');
  querySubmit();
}

function querySubmit()
{
   $("#queryForm").submit();
}

</script>
</body>
</html>
<s:actionmessage theme="custom" cssClass="success"/>
