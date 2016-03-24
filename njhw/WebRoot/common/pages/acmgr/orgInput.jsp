<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2010-08-31 15:27:22
- Description: 机构信息录入页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>机构信息录入</title>
	<%@ include file="/common/include/meta.jsp" %>
		<script type="text/javascript">
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#orgname").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate({
				//meta :"validate",// 采用meta String方式进行验证（验证内容与写入class中）
				errorElement :"div",// 使用"div"标签标记错误， 默认:"label"
				//wrapper:"li",// 使用"li"标签再把上边的errorELement包起来
				//errorClass :"div.error",// 错误提示的css类名"error"
				rules: {
					orgname: "required",
					//phone: "isTelPhone",
					email: "email",
					postcode:"isZipCode",
				<c:if test="${orgtype=='5' or orgtype=='6'  or orgtype=='8'}">
					taxno: {
						isNumOrLetter:true
						/**,
						remote: {
                			url: 'checkTaxnoInput.act',
               				data: {
	                    		taxno: function() { 
	                    			return $("#taxno").val(); 
	                    		},
                    			tempTaxno: function() { 
	                    			return $("#tempTaxno").val(); 
	                    		}
                    		}
                    	}**/
					},
				</c:if>
					/**account:"digits",
					remark:{
 	                    maxlength:500
                	},**/
					address:{
 	                    maxlength:200
                	}
					<c:if test="${parentOrgtype=='0'}">,
					company: "required"
					</c:if>
				},
				messages: {
					orgname: {
						required: "请输入机构名称!"
					}/**,
					taxno: {
						remote: "税号不能重复!"
					}**/
				}
			});					
			changebasiccomponentstyle();		
		});
	</script>
</head>
<body topmargin="0" leftmargin="0">
<form  id="inputForm" action="save.act"  method="post"  autocomplete="off" >
 <s:hidden name="parentid"/>
 <s:hidden name="orgid"/>
 <s:hidden name="treelevel"/>
 <s:hidden name="parentOrgtype"/>
 <s:hidden name="orgtype"/>
 <s:hidden name="tempTaxno"/>
    <table align="center" border="0" width="100%" class="form_table">
      <tr>
         <td class="form_label"><font style="color:red">*</font>机构名称：</td>
        <td>
         <s:textfield name="orgname" theme="simple" size="30" maxlength="20"/>
        </td>   
        <td class="form_label">机构编码：</td>
        <td>
           <s:textfield name="orgcode" theme="simple" size="30" maxlength="20"/>
        </td> 
      </tr>
      <tr>
        <td class="form_label">上级机构：</td>
        <td>
         <s:textfield name="parentOrgname" theme="simple" size="30" readonly="true"/>
        </td>   
        <td class="form_label">机构类型：</td>
        <td>
           <s:textfield name="orgtypename" theme="simple" size="30"  readonly="true"/>
        </td> 
      </tr>
<c:if test="${orgtype=='5' or orgtype=='6'  or orgtype=='8'}">
     <tr>
        <td class="form_label">纳税人识别号：</td>
        <td>
          <s:textfield name="taxno" theme="simple" size="30" maxlength="20"/>
        </td>      
         <td class="form_label">纳税人名称：</td>
        <td>
         	<s:textfield name="taxname" theme="simple" size="30" maxlength="20"/>
        </td>  
      </tr>
</c:if>
     <tr>
        <td class="form_label">联系人：</td>
        <td>
          <s:textfield name="linkman" theme="simple" size="30" maxlength="10"/>
        </td>      
         <td class="form_label">联系电话：</td>
        <td>
         	<s:textfield name="phone" theme="simple" size="30" maxlength="20"/>
        </td>  
      </tr>
     <tr>
         <td class="form_label">电子邮箱：</td>
        <td>
         	<s:textfield name="email" theme="simple" size="30" maxlength="40"/>
        </td>  
        <td class="form_label">邮政编码：</td>
        <td>
          <s:textfield name="postcode" theme="simple" size="30" maxlength="6"/>
        </td>   
      </tr>
     <tr>
        <td class="form_label">联系地址：</td>
        <td  colspan="3">
         <s:textarea  name="address" theme="simple" rows="2" cols="50"></s:textarea>
        </td> 
      </tr>
     <tr>
        <td class="form_label">开户行：</td>
        <td  colspan="3">
          <s:textfield name="bank" theme="simple" size="40" maxlength="50"/>
        </td>      
      </tr>
     <tr>    
         <td class="form_label">银行帐号：</td>
        <td  colspan="3">
         	<s:textfield name="account" theme="simple" size="40" maxlength="30"/>
        </td>  
      </tr>
     <tr>
        <td class="form_label">JCosmo：</td>
        <td colspan="3">
       	<s:checkboxlist name="extracttype" listKey="dictcode" listValue="dictname" 
       		list="#application.DICT_EXTRACT_TYPE" value="#request.selectList"/>
        </td>      
      </tr>
<c:if test="${parentOrgtype=='0'}">
     <tr>
        <td class="form_label"><font style="color:red">*</font>总部代码：</td>
        <td colspan="3">
       		<s:textfield name="company" theme="simple" size="30" maxlength="20"/>
        </td>      
      </tr>

     <tr>
         <td class="form_label">抽取软件地区：</td>
        <td  colspan="1">
          <s:select list="#application.DICT_EXTRACTSOFTZONE" name="extractsoftzone" 
          emptyOption="false" headerKey=""  headerValue="---请选择---" 
           listKey="dictcode" listValue="dictname"/>  
        </td>  
         <td class="form_label">扫描软件地区：</td>
        <td  colspan="1">
          <s:select list="#application.DICT_SCANSOFTZONE" name="scansoftzone" 
          emptyOption="false" headerKey=""  headerValue="---请选择---" 
           listKey="dictcode" listValue="dictname"/>  
        </td>  
      </tr>
</c:if>
      <tr>
        <td class="form_label">备注：</td>
        <td colspan="3">
          <s:textarea  name="remark" theme="simple" rows="4" cols="50"></s:textarea>
        </td>        
      </tr>
     <tr>
        <td class="form_label">扩展字段1：</td>
        <td>
         <s:textfield name="extf0" theme="simple" size="40" maxlength="200"/>
        </td> 
        <td class="form_label">扩展字段2：</td>
        <td>
         <s:textfield name="extf1" theme="simple" size="40" maxlength="200"/>
        </td>   
      </tr>
     <tr>
        <td class="form_label">扩展字段3：</td>
        <td>
         <s:textfield name="extf2" theme="simple" size="40" maxlength="200"/>
        </td> 
        <td class="form_label">扩展字段4：</td>
        <td>
         <s:textfield name="extf3" theme="simple" size="40" maxlength="200"/>
        </td>   
      </tr>
     <tr>
        <td class="form_label">扩展字段5：</td>
        <td>
         <s:textfield name="extf4" theme="simple" size="40" maxlength="200"/>
        </td> 
        <td class="form_label"></td>
        <td>
        </td>   
      </tr>
	</table>
   <table align="center" border="0" width="100%" class="form_table">  
      <tr class="form_bottom">
        <td colspan="6">
        <a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData();">保存</a>
        <a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="$('#inputForm').resetForm();">重置</a>
        </td>
      </tr>
  </table>
  </form>
</body>
</html>
<script type="text/javascript">
<c:if test="${isSuc=='true'}">
	easyAlert('提示信息','保存机构成功！','info',
	   function(){closeEasyWin('orgInput');}
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','保存机构失败！','error');
</c:if>
		function saveData(){
			$('#inputForm').submit();
		}
</script>

