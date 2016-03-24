<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/include/metaIframe.jsp" %>
	<link href="${ctx}/styles/personinfo/css/info.css" rel="stylesheet" type="text/css" />
    <title>IP电话录入</title>
    <script type="text/javascript">	

    $(document).ready(function() {
        
		//聚焦第一个输入框
		$("#telNum").focus();
		//为inputForm注册validate函数
		$("#inputForm").validate({
			meta :"validate",// 采用meta String方式进行验证（验证内容与写入class中）
			errorElement :"div",// 使用"div"标签标记错误， 默认:"label"
			rules: {
				telType: {
					required: true
				},
				telNum: {
					required: true,
					remote: { //验证电话号码是否存在
　　						type:"POST",
　　						url:"${ctx}/app/per/checkTelNum.act",
						async:false,
　　 					data:{
　　 						telId:function(){return $("#telId").val();},
							telNum:function(){return $("#telNum").val();}
　　 					}
　　 				},
					digits:true
				},
				telMac: {
					remote: { //验证电话MAC是否存在
　　						type:"POST",
　　						url:"${ctx}/app/per/checkTelMac.act",
						async:false,
　　 					data:{
　　 						telId:function(){return $("#telId").val();},
							telMac:function(){return $("#telMac").val();}
　　 					}
　　 				} 
				}
			},
			messages:{
				telType:{
                    required: "资源类型不能为空！"
               	},
               	telNum:{
                    required: "电话号码不能为空！",
                    remote: "电话号码已经存在！",
                    digits: "请输入正确的电话号码！"
               	},
               	telMac:{
               		remote: "MAC地址已经存在！"
               	}
			}
		});
	});
    
		function saveData(){
		  $("#inputForm").valid();
		  if ($("#inputForm").valid()) {
		  	var frm = $('#inputForm');
 		  	frm.submit();
		  }
		}
	</script>
  </head>
  
  <body style="background:#fff;">
    <form  id="inputForm" action="saveIpTel.act"  method="post"  autocomplete="off" >
     <s:hidden name="telId" id="telId"/>
     <div class="infrom_ryjbxx">
    <table align="center" border="0" width="100%">
      <tr>
        <td class="form_label" width="50%"><div class="infrom_td"><font style="color:red">*</font>资源类型：</div></td>
			<td>
				<s:select list="#application.DICT_TEL_TYPE" cssStyle="width:141px;" headerKey="" headerValue="请选择" listKey="dictcode" listValue="dictname" name="telType" id="telType"/>
			</td>
      </tr> 
      <tr>
		<td class="form_label"><div class="infrom_td"><font style="color:red">*</font>电话号码：</div></td>
			<td>
				<s:textfield name="telNum" id="telNum" theme="simple" size="18" maxlength="20"/>
			</td>
	  </tr>
	  <tr>
        <td class="form_label"><div class="infrom_td">MAC地址：</div></td>
        <td>
        	<s:textfield name="telMac" theme="simple" size="18" maxlength="20"></s:textfield>
        </td> 
      </tr>
	</table>
	</div>
   <table align="center" border="0" width="100%" class="form_table">  
      <tr>
        <td colspan="2" align="center">
        	<a href="javascript:void(0);" class="infrom_a" style="width:70px;margin-left:10px;float:right;" id="searchbutton" onclick="$('#inputForm').resetForm();">重置</a>
        	<a href="javascript:void(0);" class="infrom_a" style="width:70px;float:right;" id="resetbutton" onclick="saveData();">保存</a>
        </td>
      </tr>
  </table>
  </form>
  </body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	easyAlert("提示信息","保存成功!","info",
	   function(){closeEasyWin('winId');}
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert("提示信息","保存失败!","error");
</c:if>
</script>