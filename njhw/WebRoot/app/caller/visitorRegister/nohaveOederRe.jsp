<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/include/meta.jsp" %>
	<title>访客预约</title>
	<script src="${ctx}/scripts/validation/card.js" type="text/javascript"></script>
    <script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
    <script type="text/javascript">
	function easyAlert(title,msg,type,fn){
		using('messager', function () {
			$.messager.alert(title,msg,type,fn);
		});
	}
	$(document).ready(function() {
		var message = $("#message").val();
		if (message != "") {
			easyAlert("提示信息","保存成功!");
		}
		//下拉列表框必选项的
		jQuery.validator.methods.compareValue = function(value, element) {
		    if(value=="0"){
				return false;
			}
			return true;
		};
		//日期比较
		jQuery.validator.methods.compareDate = function(value, element, param) {
			var beginTime = jQuery(param).val();
	          //  var date1 = new Date(Date.parse(beginTime.replace("-", "/")));
	           // var date2 = new Date(Date.parse(value.replace("-", "/")));
			return beginTime < value;
		};
		// 身份证号码验证 
		jQuery.validator.addMethod("isIdCardNo", function(value, element) { 
			return this.optional(element) || idCardNoUtil.checkIdCardNo(value);     
		}, "请正确输入您的身份证号码"); 
		// 手机号码验证 
		jQuery.validator.addMethod("isMobile", function(value, element) { 
			var length = value.length; 
			var mobile = /^(((13[0-9]{1})|(14[0-9]{1})|(18[0-9]{1})|(15[0-9]{1}))+\d{8})$/; 
			return this.optional(element) || (length == 11 && mobile.test(value)); 
		}, "请正确填写您的手机号码"); 
			
		//聚焦第一个输入框
		$("#cardId").focus();
			
		//为inputForm注册validate函数
		$("#inputForm").validate({
			meta :"validate",// 采用meta String方式进行验证（验证内容与写入class中）
			errorElement :"div",// 使用"div"标签标记错误， 默认:"label"
			rules: {
				userName: {
					required: true
				},
				outId: {
					compareValue:false
				},
				cardId:{
					required: true,
					isIdCardNo: true
				},
				orgId:{
						required: true
					},
					
					comeWhy:{
						required: true
					},
					comeNum:{
						required: true
						//digits:true,
						//min:1
					},
					phone:{
						required: true,
						isMobile:true
					},
					beginTime:{
						required: true
					},
					endTime:{
						required: true,
						compareDate: "#beginTime"
					},
					
					orgId:{
						compareValue:true
					},
					fatherCardId:{
					required: true
					}
				},
				messages:{ 
					cardId:{
					 isIdCardNo:"请输入正确的身份证号"
					 },
					beginTime:{
						 required: "开始时间不能为空"
					 },
	                endTime:{
	                    required: "结束时间不能为空",
	                    compareDate: "结束日期必须大于开始日期!"
	               	},
	               	comeNum:{
	               	 required: "来访人数不能为空"
	               	},
	               	fatherCardId:{
					required: "临时卡不能为空"
					},
			        orgId:{compareValue:"请选择单位"},
			        outId:{compareValue:"请选择受访者"}
				}
			});
		});
		
		function tableValue(){
		   var res=true;
			var residentNo="";
			var cardId="";
			var userName="";
	
			$("#fied_table tr").each(function(i,item){
			if($($(item).find("td input")[0]).val()=="" || $($(item).find("td input")[1]).val()=="" || $($(item).find("td input")[2]).val()==""){
					res=false
					//alert(res);
		}
				userName += $($(item).find("td input")[0]).val() + ",";
				residentNo += $($(item).find("td input")[1]).val() + ",";
				cardId += $($(item).find("td input")[2]).val() + ",";
			});
		
			var userName1= userName.substr(0, userName.length - 1);
			var residentNo1 = residentNo.substr(0, residentNo.length - 1);
			var cardId1=cardId.substr(0, cardId.length - 1);
	     	//alert(userName1+"/"+residentNo1+"/"+cardId1);
	     	 $("#residentNonum").val(residentNo1);
              $("#cardIdnum").val(cardId1);
             $("#userNamenum").val(userName1);
	    return res;
			
		}

		
		function saveData(){
	 // var orgname =$("#orgId").find("option:selected").text();	  
	  //var outname = $("#outId").find("option:selected").text();
	 // alert(orgname);
	//  alert(outname);
	  // $("#orgName").val(orgname);
	 //  $("#outName").val(outname);

		  if($("#comeNum").val()>1){
		//   alert("走人数大于1");
		   if(tableValue()){
		   $("#comeNum").val($("#fied_table tr").length+1);
		   var frm = $('#inputForm');
		   frm.submit();
		   }else{
			   easyAlert("提示信息","请填写完整的绑卡参数!");
			 }		   
		   }else{
		//   alert(12334);
			    var frm = $('#inputForm');
			    frm.submit();
			 }
		}
		
 
		// 组织机构得到用户
			function orgUser() {
				var orgId = $("#orgId").val();
				if (orgId > 0) {
					$.ajax({
			            type: "POST",
			            url: "${ctx}/app/visitor/getUsersByOrgId.act",
			            data:{orgId: orgId},
			            dataType: 'json',
			            async : false,
			            success: function(json){
			            
			            	if (json.status == 0) {
  				    		
  				    		$("#outId").empty();
  				    			
  								$.each(json.list,function(i){
  									$("#outId").append("<option value="+json.list[i][0]+">"+json.list[i][1]+"</option>"); 
  								});
  				    		}
			             }
			         });
				}
			}
		

			function ajaxNvrCard(id){
				var res = true;
				if(id !=""){
					var url = "${ctx}/app/caller/checkCityCar.act";
					var data = {nvrCard:id};
					sucFun = function (data){
						if(data.state == "error"){
						
							 res = false;
			              }else{return true;}
					};
					errFun = function (data){
						
					};
					ajaxQueryJSON(url,data,sucFun,errFun);
				}
				return res;
			}
		//
		
		/**
		* 人数改变事件
		*/
		function txtRelativeChange(){
			var txtRelativeNum = $("#comeNum").val();
			if (txtRelativeNum > 1){
				$("#div_band_relative_card").show();
			} else {
		     	
				$("#div_band_relative_card").hide();
				//$("#div_band_relative_card  input").clear();
				
			}
		}
		  
		 function selectVisit(){
		 
	     var residentId = $("#cardId").val();
	       $.ajax({
	             type:"POST",
	             url:"${ctx}/app/visitor/loadVisiInfo.act",
	             data:{residentId:residentId},
	             dataType: 'json',
			     async : false,
			     success: function(json){
	             if (json.status == 0) {
	              $("#userName").val(json.VIName);
					$("#phone").val(json.ViMobile);
					$("#carId").val(json.PlateNum);
						
	          }
	          }
	      });
		 }
		  
		 //验证省份证号;
		function validationCard(obj){
		var vaidRes=true;
		if(idCardNoUtil.checkIdCardNo(obj.value))
		{
          vaidRes;
           
		}else{	
		obj.value="";
		obj.focus();
		vaidRes=false;
		alert("请输入正确的省份证号!");
		
		}
		} 
		  
		  
	</script>
  </head>
  
  <body>
  <input type="hidden" id="message" value="${meg}"/>
    <form  id="inputForm" action="savenoOrder.act?message=1"  method="post"  autocomplete="off" >
	<s:hidden name="vsId"/>
	<s:hidden name="viId"/>	
	

	<table align="center" border="0" width="100%" class="form_table">
      <tr>
	        <td class="form_label" id="order_cardId" ><font style="color:red">*</font>身份证号：</td>
	        <td id="order_cardId1">
	           <s:textfield name="cardId" id="cardId"  theme="simple" size="18" maxlength="20"  onblur="javascript:selectVisit();"/>
	        
	        </td>
	          <td class="form_label"><font style="color:red">*</font>访客姓名：</td>
	        <td>
	         <s:textfield name="userName" id="userName" theme="simple" size="18" maxlength="20"/>
	        </td> 
	        <td class="form_label"><font style="color:red">*</font>预约时间段：</td>
	        <td>
	        
	        
	        <s:textfield  
			onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',disabledDates:['%y-%M-%d 07\:..\:..','%y-%M-%d 06\:..\:..','%y-%M-%d 05\:..\:..','%y-%M-%d 04\:..\:..','%y-%M-%d 03\:..\:..','%y-%M-%d 02\:..\:..','%y-%M-%d 01\:..\:..','%y-%M-%d 00\:..\:..','%y-%M-%d 19\:..\:..','%y-%M-%d 20\:..\:..','%y-%M-%d 21\:..\:..','%y-%M-%d 22\:..\:..','%y-%M-%d 23\:..\:..']})" 
			id="beginTime" name="beginTime" cssClass="Wdate">
			</s:textfield>
			-
			<s:textfield  
			onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',disabledDates:['%y-%M-%d 07\:..\:..','%y-%M-%d 06\:..\:..','%y-%M-%d 05\:..\:..','%y-%M-%d 04\:..\:..','%y-%M-%d 03\:..\:..','%y-%M-%d 02\:..\:..','%y-%M-%d 01\:..\:..','%y-%M-%d 00\:..\:..','%y-%M-%d 19\:..\:..','%y-%M-%d 20\:..\:..','%y-%M-%d 21\:..\:..','%y-%M-%d 22\:..\:..','%y-%M-%d 23\:..\:..']})" 
			id="endTime" name="endTime" cssClass="Wdate">
			</s:textfield>
	        
	        
<!--	           <s:date id="format_startTime" name="beginTime" format="yyyy-MM-dd HH:mm"/>-->
<!--				<jsp:include page="/common/include/chooseDateTime.jsp">-->
<!--				    <jsp:param name="date" value="beginTime"/>-->
<!--				    <jsp:param name="format_date" value="${format_invveridateStr}"/>-->
<!--				</jsp:include>-->
<!--				--->
<!--				<s:date id="format_endTime" name="endTime" format="yyyy-MM-dd HH:mm"/>-->
<!--				<jsp:include page="/common/include/chooseDateTime.jsp">-->
<!--				    <jsp:param name="date" value="endTime"/>-->
<!--				    <jsp:param name="format_date" value="${format_invveridateStr}"/>-->
<!--				</jsp:include>-->
	        </td>
       </tr>
       <tr>
        	<td class="form_label"><font style="color:red">*</font>手机号：</td>
	        <td>
	           <s:textfield name="phone" id="phone" theme="simple" size="18" maxlength="20"/>
	        </td>
       		 <td class="form_label"><font style="color:red">*</font>来访人数：</td>
	        <td>
	           <s:textfield name="comeNum" id="comeNum" theme="simple" onblur="javascript:txtRelativeChange();" size="18" maxlength="20" onkeyup="value=value.replace(/[^\d]/g,'');"/>
	        </td>
	         <td class="form_label">车牌号：</td>
     		 <td>
          		 <s:textfield name="carId" id="carId" theme="simple" size="18" maxlength="20" />
          		
        	</td>
      </tr> 
      <tr>
       
        <td class="form_label"><font style="color:red">*</font>受访者单位：</td>
        <td>
          <select id="orgId" name="orgId" onchange="javascript:orgUser();" class="input_textarea">
				<option value="0">请选择...</option>
				<c:forEach items="${orgs}" var="org">
					<option value="${org.orgId}" >${org.name}</option>
				</c:forEach>
			</select>
        </td> 
         <td class="form_label"><font style="color:red">*</font>受访者姓名：</td>
        <td>
          <select class="input_textarea" id="outId" name="outId">
				<option value="0">请选择...</option>		
			</select>
        </td> 
        
          <td class="form_label"><font style="color:red">*</font>临时卡号：</td>
         <td>
             <s:textfield name="fatherCardId" theme="simple" size="18" maxlength="20"/>

          </td>
        
        </tr>
        <tr>
      <td class="form_label"><font style="color:red">*</font>来访事由：</td>
        <td>
         <s:textarea name="comeWhy" cssClass="input_textarea" cols="30" rows="5"></s:textarea>
        </td> 
      </tr>
	</table>	
	
	<div id="div_band_relative_card" style="display:none;">
	<input type="hidden" name="residentNonum" id="residentNonum"/>	
     <input type="hidden" name="cardIdnum" id="cardIdnum"/>	
      <input type="hidden" name="userNamenum" id="userNamenum"/>	
	<a href="javaScript:addfield()">增绑定附卡</a>
	<table align="center" border="0" width="100%" class="form_table" id="fied_table">
	  <tr>
          <td class="form_label"><font style="color:red">*</font>姓名：</td>
          <td>
          	<input name="quserName" theme="simple" size="18" maxlength="20"/>
          </td>
          <td class="form_label"><font style="color:red">*</font>身份证号：</td>
          <td>
          	<input name="qresidentNo" theme="simple" size="18" maxlength="20" onblur="javascript:validationCard(this);"/>
          </td>
           <td class="form_label"><font style="color:red">*</font>副卡号：</td>
          <td>
          	<input name="qcardId" theme="simple" size="18" maxlength="20"/>
          </td>
          <td>
          	
          </td>
      </tr>
	</table>
	</div>
	
	
   <table align="center" border="0" width="100%" class="form_table">  
      <tr class="form_bottom">
        <td colspan="6">
      		<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData();">保存</a>    
      		
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

	//增加一行
	function addfield() {
		var tr = '<tr><td class="form_label"><font style="color:red">*</font>姓名：</td><td><input name="quserName" theme="simple" size="18" maxlength="20"/></td><td class="form_label"><font style="color:red">*</font>身份证号：</td><td><input name="qresidentNo" theme="simple" size="18" maxlength="20" onblur="javascript:validationCard(); /></td><td class="form_label"><font style="color:red">*</font>副卡号：</td><td><input name="qcardId" theme="simple" size="18" maxlength="20"/></td><td><a onclick="javascript:removeRow(this);" href="javascript:void(0);">取消</a></td></tr>';
		$("#fied_table").append(tr);
	}
	/*删除一行*/
	function removeRow(obj){
		$(obj).parent().parent().remove();
	}	
</script>
