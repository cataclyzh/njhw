<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/include/meta.jsp" %>
    <title>发放临时卡</title>
   
    <script type="text/javascript">	
	function easyAlert(title,msg,type,fn){
    	using('messager', function () {
    		$.messager.alert(title,msg,type,fn);
    	});	
    }
    $(document).ready(function() {
    //聚焦第一个输入框
			$("#cardId").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate({
				meta :"validate",// 采用meta String方式进行验证（验证内容与写入class中）
				errorElement :"div",// 使用"div"标签标记错误， 默认:"label"
				rules: {
					cardId: {
						required: true
					}
					},
					
				messages:{ 
					cardId:{
					 required:"请输临时卡号"
					 }

				}
			});
		});
    function saveData(){
    $('#inputForm').submit(); 
    }
	</script>
  </head>
  
  <body>
  

	
	<table align="center" border="0" width="100%" class="form_table">
	
	
      <tr>
	        <td class="form_label"><font style="color:red"></font>访客姓名：</td>
	        <td>
	       
	        ${map.VI_NAME}
	        </td>   
	        
	        <td class="form_label">身份证号：</td>
	        <td>
	           ${map.RESIDENT_NO }
	        </td>
	       <td class="form_label">手机号：</td>
	        <td>
	            ${map.VI_MOBILE }
	        </td>
        </tr>
        <tr>
         <td class="form_label">邮箱：</td>
	        <td>
	          ${map.VI_MAIL }
	        </td>
       		
	           <td class="form_label">车牌：</td>
	        <td>
	            ${map.PLATE_NUM }
	        </td>
	          <td class="form_label">受访者姓名：</td>
	        <td>
	           ${map.USER_NAME}
	        </td>
	        
      </tr> 
      <tr>
     
      <td class="form_label">机构名：</td>
	        <td>
	           ${map.ORG_NAME}
	        </td>
	         <td class="form_label">访问时间：</td>
	        <td>
	            ${map.VS_ST}
	        </td>
      <td class="form_label">结束时间：</td>
	        <td>
	           ${map.VS_ET}
	        </td>
	       
      </tr>
       <tr>
       <td class="form_label">来访人数：</td>
	        <td>
	           ${map.VS_NUM}
	        </td>
        <td class="form_label">来访理由：</td>
	        <td>
	          <textarea rows="" cols="" readonly="readonly"> ${map.VS_INFO}</textarea>
	        </td>
      </tr>
	</table>
	  <form  id="inputForm" action="getVisitByid.act"  method="post"  autocomplete="off" >
	   <input type="hidden" name="vsId" id="vsId" value="${map.VS_ID}"/>
	<table>
	    
	        <tr>
	         <td>
	        卡号：  <input type="text" name="cardId" id ="cardId" value="${map.CARD_ID}"/>
	        </td>
	        
	        </tr>
	       
	        
	       
	</table>

   <table align="center" border="0" width="100%" class="form_table">  
      <tr class="form_bottom">
        <td colspan="6">
      		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData();">发放临时卡</a>    
      		
        </td>
      </tr>
  </table>
  </form>
  <input type="hidden" name="vsids" id="vsids" value="${vsids}"/>
  </body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	easyConfirm('提示', '发放成功，是否绑定副卡？', function(r){
				if (r){
				 var vsId=$("#vsids").val();
            
	            url="${ctx}/app/visitor/getvisifoByid1.act?vsId="+vsId;
		     	openEasyWin("winId","发放副卡",url,"800","400",true);
		     	closeEasyWin('rfid');
				}else{
				closeEasyWin('rfid');
				
				}
			});		
	
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert("提示信息","发放失败!","error");
	closeEasyWin('winId');
 
</c:if>
</script>
