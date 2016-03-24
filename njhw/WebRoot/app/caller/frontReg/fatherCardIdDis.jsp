<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<%--
- Author: sqs
- Date: 2013-03-19 Description: Ip电话分配
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>访客卡号分配</title>
	<%@ include file="/common/include/header-meta.jsp" %>
	<link href="${ctx}/scripts/widgets/dtree/styles/dhtmlxtree.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/scripts/widgets/dtree/styles/dhtmlxmenu.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/scripts/widgets/easyui/themes/icon.css" rel="stylesheet" type="text/css"/>
	<link href="${ctx}/styles/personinfo/css/info.css" rel="stylesheet" type="text/css" />
	<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/easyui/easyloader.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/dtree/scripts/dhtmlxcommon.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/widgets/dtree/scripts/dhtmlxtree.js" type="text/javascript"></script>	
	<script src="${ctx}/scripts/widgets/dtree/scripts/dhtmlxmenu.js" type="text/javascript"></script>
	<script src="${ctx}/scripts/basic/jquery.js.gzip" type="text/javascript"></script>	
	<script src="${ctx}/scripts/basic/common.js" type="text/javascript"></script>
	<script type="text/javascript">
		
			function closeWindow()
			    {
			     
			       var list= $('input:radio[name="_chk"]:checked').val(); 
		           if(null == list){ 
		                 easyAlert("提示信息", "请选择一个访客卡号！","info");
		                return false; 
		           } 
			       
			       $("input[name='_chk']").each(function(index,domEle){
				      if(domEle.checked)
				      {   
				         var num = $(":radio:checked + span").text() 
				      	  	 parent.$("#cardId").val(this.value);
				      	  	 parent.$("#fatherCardId").val(num);
				         parent.$("#fatherCardIdDis").window("close");
						
					  }
				   }
			      )
			    }
	function cancleWindow()
	{    
      	  	  parent.$("cardId").val(''); 
			  parent.$("fatherCardId").val('');  
		 parent.$("#fatherCardIdDis").window("close");
	}
	</script>
</head>
	  
	 <body topmargin="0" leftmargin="0" rightmargin="0" style="background: #fff;">	
	 <div id="page_div" class="room_list_container">
				<table width="100%" border="0" cellspacing="0" cellpadding="0" align="center">
					<tr class="odd">
					<c:if test="${null != telList}">   
					<c:forEach var="ipp" items="${telList}" varStatus="stat">
						<td width="25%">
						
						<input type="radio"  value="${ipp.VISID}" name="_chk" id="chk_"><span>${ipp.IDCRADNUM}</span></input>
						<!-- 不够四列的行用空列补齐 -->  
						<c:if test="${stat.last && stat.count%4 != 0}">  
						<c:set value="${4-(stat.count%4)}" var="restTd"/>                                    
						<c:forEach var="x" begin="1" end="${restTd}" step="1">  
						<td width="25%"></td>  
						</c:forEach>  
						</c:if>  
						<!-- 4列换一行 -->  
					
							<c:if test="${stat.count%4==0 && stat.count%8==0}">
								</tr>
								<tr class="odd">
							</c:if>
							<c:if test="${stat.count%4==0 && stat.count%8!=0}">
								</tr>
								<tr class="even">
							</c:if>
						</td>
	                 </c:forEach>  
	                 </c:if>
	                 <c:if test="${empty telList}">
	                 	<td> 本单位没有可分配的号码</td>
	                 </c:if>
	                </tr>     
				</table>
				<br>
				<br>
					</div>
				<table align="center" border="0" style="width:100%;height:30px;">
					<tr>
						<td  style="text-align:center;">
							<a href="javascript:void(0);" class="buttonFinish"  style="float:right;margin-right:10px" onclick="cancleWindow();">取消</a>	
							<a href="javascript:void(0);" class="buttonFinish" style="float:right;margin-right:10px" onclick="closeWindow();">确定</a>
						</td>
						
					</tr>
				</table>
			
</body>

</html>

