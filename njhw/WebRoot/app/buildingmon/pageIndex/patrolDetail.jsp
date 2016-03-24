<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
		<title>巡查信息详情</title>
		<%@ include file="/common/include/meta.jsp"%>
		<script src="${ctx}/scripts/basic/jquery.js.gzip"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/widgets/easyui/easyloader.js"
			type="text/javascript"></script>
		<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
		<script type="text/javascript"
			src="${ctx}/app/portal/toolbar/showModel.js"></script>
		<script src="${ctx}/app/portal/js/AjaxUtil.js" type="text/javascript"></script>
		<script src="${ctx}/app/suggest/js/suggesst.js" type="text/javascript"></script>
		<script type="text/javascript">
		document.domain = "njnet.gov.cn";
		</script>
		<script type="text/javascript">
		  function logout(){  
			  $(this).dialog("close");
		  } 
					
		</script>
		<style type="text/css">
			.textfield { 
				width:240px;
			}
			.textarea { 
				width:360px;
				height:120px;
			}
	    </style>
	</head>
	<body style="overflow:auto;background: #f1f8fd;">
	<div class="main_left_main1">
        <div class="main_conter">
            <div class="main1_main2_right">
			   <div class="main2_border_list">
			     <p>异常详情</p>
			   </div>
               <form action="" method="post">
                   <div class="show_from">
                       <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr>
						    <td width="20%">人员姓名：</td>
						    <td width="80%"><input name="" readonly="readonly" type="text" class="show_inupt" value="${patrolMap.USERNAME}" /></td>
						  </tr>
						  <tr>
						    <td>部门名称：</td>
						    <td width="80%"><input name="" readonly="readonly" type="text" class="show_inupt" value="${patrolMap.ORGNAME}" /></td>
						  </tr>
						  <tr>
						    <td valign="top">当班路线：</td>
						    <td width="80%"><input name="" readonly="readonly" type="text" class="show_inupt" value="${patrolMap.LINE}" /></td>
						  </tr>
						  <tr>
						    <td valign="top">当班时段：</td>
						    <td width="80%"><input name="" readonly="readonly" type="text" class="show_inupt" value="${patrolMap.STARTDATE} ~ ${patrolMap.ENDDATE}"/></td>
						  </tr>
						  <tr>
						    <td valign="top">是否缺勤：</td>
						    <td width="80%"><input name="" readonly="readonly" type="text" class="show_inupt" value="${patrolMap.ISDUTY}" /></td>
						  </tr>
						  <tr>
						    <td valign="top">未到达点：</td>
						    <td width="80%"><input name="" readonly="readonly" type="text" class="show_inupt" value="${patrolMap.NODES}" maxlength="100"/></td>
						  </tr>
                       </table>
                   </div>
                   <div class="show_pop_bottom"><a style="cursor: pointer;" onclick="window.parent.closeIframe()">关闭窗口</a></div>
               </form>
            </div>
            <div class="clear"></div>
        </div>
    </div>
    <div style="display: none" id="sendDiv"></div>
    </body>
</html>

