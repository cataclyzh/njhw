<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/include/meta.jsp" %>
    <title>闸机测试</title>
    <script src="${ctx}/scripts/validation/card.js" type="text/javascript"></script>
    <script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
   
  </head>
  
  <body>
    <form  id="inputForm" action="gatesTest.act"  method="post"  autocomplete="off" >
	<table align="center" border="0" width="100%" class="form_table">
      <tr>
	        <td class="form_label"><font style="color:red">*</font>闸机号：</td>
	        
	        <td>
	         <s:textfield name="gatesNo" theme="simple" size="18" maxlength="20"/>
	        </td> 
        </tr>
        
        <tr>
         <td class="form_label" width="20%" align="left">
       	  	查询状态：       	  	
          </td>
          <td width="30%">
			<select id="state" name="state" >
				<option value="38">开门</option>
				<option value="39">关门</option>
				<option value="3c">查询状态</option>
			</select>
          </td>
        </tr>
     
     
         
	</table>
   <table align="center" border="0" width="100%" class="form_table">  
      <tr class="form_bottom">
        <td colspan="6">
        
                    <input type="submit"    class="easyui-linkbutton"  iconCls="icon-save" id="button" name="button" value="测试"/>   
             
      		<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="$('#inputForm').resetForm();">重置</a>  
        </td>
      </tr>
  </table>
  
  <table align="center" border="0" width="100%" class="form_table">
  	<tr>
  		<td>输出结果：</td>
  	</tr>
  	<tr>
  		<td><s:textarea name="result" cols="50" rows="10"></s:textarea></td>
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
