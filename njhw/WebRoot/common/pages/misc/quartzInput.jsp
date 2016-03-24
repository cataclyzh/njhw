<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<%--
- Author: WXJ
- Date: 2011-04-27 15:27:22
- Description: 录入页面
--%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>录入任务</title>
	<%@ include file="/common/include/meta.jsp" %>
	<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
    <script src="${ctx}/scripts/custom/dateUtil.js" type="text/javascript"></script>		
	<script type="text/javascript">
		$(document).ready(function() {
			//聚焦第一个输入框
			$("#taxno").focus();
			//为inputForm注册validate函数
			$("#inputForm").validate({
				//meta :"validate",// 采用meta String方式进行验证（验证内容与写入class中）
				errorElement :"div",// 使用"div"标签标记错误， 默认:"label"
				rules: {
					triggerName: "required",
					startTime:"required",
					endTime:"required",
					repeatCount:{
						number:true
					},
					repeatInterval:{
						required:true,
						number:true
					}
				}
			});					
			changebasiccomponentstyle();		
		});
		function saveData(){
			var frm = $('#inputForm');
			frm.submit();
		}
		function showCtt(obj){
			var val = $(obj).val();
			if(val=='1'){
				$("#fxTab").show();
				$("#clTab").hide();
			}else{
				$("#clTab").show();
				$("#fxTab").hide();
			}
		}
		function chgMode(obj){
			var val = $(obj).val();
			if(val=='2'){
				$("#weektr").show();
				$("#monthtr").hide();
				$("#yeartr").hide();
			}else if(val=='3'){
				$("#weektr").hide();
				$("#monthtr").show();
				$("#yeartr").hide();
			}else if(val=='4'){
				$("#weektr").hide();
				$("#monthtr").hide();
				$("#yeartr").show();
			}
		}
		</script>
</head>
<body topmargin="0" leftmargin="0">
<form  id="inputForm" action="save.act"  method="post"  autocomplete="off" >
    <table align="center" border="0" width="100%" class="form_table">
      <tr>
         <td class="form_label"  width="100">任务名称：</td>
        <td>
         <s:textfield name="triggerName" theme="simple" size="25" maxlength="20"/>         	
        </td>   
         <td class="form_label">Spring_JOB_ID：</td>
        <td>
         <s:textfield name="jobName" theme="simple" size="25" maxlength="20"/>         	
        </td>   
      </tr>
      <tr>
        <td class="form_label">开始时间：</td>
        <td>
						<s:date id="format_startTime" name="startTime" format="yyyy-MM-dd HH:mm:ss"/>
						<jsp:include page="/common/include/chooseDateTime.jsp">
						    <jsp:param name="date" value="startTime"/>
						    <jsp:param name="format_date" value="${format_invveridateStr}"/>
						</jsp:include>
        </td> 
        <td class="form_label">结束时间：</td>
        <td>
						<s:date id="format_endTime" name="endTime" format="yyyy-MM-dd HH:mm:ss"/>
						<jsp:include page="/common/include/chooseDateTime.jsp">
						    <jsp:param name="date" value="endTime"/>
						    <jsp:param name="format_date" value="${format_invveridateStr}"/>
						</jsp:include>
        </td> 
      </tr>
      <tr>
        <td class="form_label">任务分组：</td>
        <td colspan="3"><%--,'group_0':'苏宁接口导入','group_1':'苏宁接口经销导出','group_2':'苏宁接口代销导出','group_3':'苏宁接口认证导出' --%>
        <s:select name="triggerGroup" list="#{'default':'默认'}" theme="simple" ></s:select>
        </td> 
      </tr>
       <tr>
        <td class="form_label">触发模式：</td>
        <td  colspan="3">
        	<s:radio list="#{'1':'固定时刻触发','0':'日历周期触发 '}" name="triggerType" required="true" onclick="showCtt(this)" listKey="key" listValue="value" value="1"></s:radio> 
        </td> 
      </tr>      

	</table>
    <table align="center" border="0" width="100%" class="form_table" id="fxTab">  
       <tr>
        <td class="form_label"  width="100">间隔时间：</td>
        <td colspan="3">
           <s:textfield name="repeatInterval" theme="simple" size="25" maxlength="20"/> 单位：秒 
        </td> 
      </tr>
      <tr>
        <td class="form_label">执行次数：</td>
        <td colspan="3">
           <s:textfield name="repeatCount" theme="simple" size="25" maxlength="20"/>如果为空表示不限次数，直到结束时间为止
        </td> 
      </tr>
  </table>
  <%-- “-1”表示不限次数，直到结束时间为止 --%>
   <table align="center" border="0" width="100%" class="form_table" style="display:none"  id="clTab">  
      <tr>
        <td class="form_label" width="100">触发时间：</td>
        <td colspan="2">
        <s:select name="hour" list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5','6':'6','7':'7','8':'8','9':'9',
        '10':'10','11':'11','12':'12','13':'13','14':'14','15':'15','16':'16','17':'17','18':'18','19':'19',
        '20':'20','21':'21','22':'22','23':'23'}"  theme="simple" listKey="key" listValue="value"/>时
		<s:select name="minute" list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5','6':'6','7':'7','8':'8','9':'9',
        '10':'10','11':'11','12':'12','13':'13','14':'14','15':'15','16':'16','17':'17','18':'18','19':'19',
        '20':'20','21':'21','22':'22','23':'23','24':'24','25':'25','26':'26','27':'27','28':'28','29':'29',
        '30':'30','31':'31','32':'32','33':'33','34':'34','35':'35','36':'36','37':'37','38':'38','39':'39',
        '40':'40','41':'41','42':'42','43':'43','44':'44','45':'45','46':'46','47':'47','48':'48','49':'49',
        '50':'50','51':'51','52':'52','53':'53','54':'54','55':'55','56':'56','57':'57','58':'58','59':'59'}"  theme="simple" listKey="key" listValue="value"/>分
        <s:select name="second" list="#{'0':'0','1':'1','2':'2','3':'3','4':'4','5':'5','6':'6','7':'7','8':'8','9':'9',
        '10':'10','11':'11','12':'12','13':'13','14':'14','15':'15','16':'16','17':'17','18':'18','19':'19',
        '20':'20','21':'21','22':'22','23':'23','24':'24','25':'25','26':'26','27':'27','28':'28','29':'29',
        '30':'30','31':'31','32':'32','33':'33','34':'34','35':'35','36':'36','37':'37','38':'38','39':'39',
        '40':'40','41':'41','42':'42','43':'43','44':'44','45':'45','46':'46','47':'47','48':'48','49':'49',
        '50':'50','51':'51','52':'52','53':'53','54':'54','55':'55','56':'56','57':'57','58':'58','59':'59'}"  theme="simple" listKey="key" listValue="value"/>秒
        </td>
        
      </tr>
      <tr>
        <td class="form_label" width="100">循环模式：</td>
        <td width="100" colspan="2">
        	<s:radio list="#{'1':'每日','2':'每周','3':'每月','4':'每年'}" value="1" onclick="chgMode(this)" name="clType"></s:radio>
        </td> 
      </tr>
      <tr id="weektr" style="display:none">
        <td class="form_label">循环值：</td> 
        <td colspan="2">
        	<s:checkboxlist name="week" list="#{'0':'星期日','1':'星期一','2':'星期二','3':'星期三','4':'星期四','5':'星期五','6':'星期六'}"></s:checkboxlist>
        </td>
      </tr>
      <tr id="monthtr" style="display:none">
        <td class="form_label">循环值：</td> 
        <td colspan="2">
        	每月第<s:select name="monthday" list="#{'1':'1','2':'2','3':'3','4':'4','5':'5','6':'6','7':'7','8':'8','9':'9',
        '10':'10','11':'11','12':'12','13':'13','14':'14','15':'15','16':'16','17':'17','18':'18','19':'19',
        '20':'20','21':'21','22':'22','23':'23','24':'24','25':'25','26':'26','27':'27','28':'28','29':'29',
        '30':'30','31':'31'}" theme="simple" ></s:select>
        	日
        </td>
      </tr>
      <tr id="yeartr" style="display:none">
        <td class="form_label">循环值：</td> 
        <td colspan="2">
        	每年<s:select name="month" list="#{'1':'1','2':'2','3':'3','4':'4','5':'5','6':'6','7':'7','8':'8','9':'9',
        '10':'10','11':'11','12':'12',
        '30':'30','31':'31'}" theme="simple" ></s:select>
        	月<s:select name="day" list="#{'1':'1','2':'2','3':'3','4':'4','5':'5','6':'6','7':'7','8':'8','9':'9',
        '10':'10','11':'11','12':'12','13':'13','14':'14','15':'15','16':'16','17':'17','18':'18','19':'19',
        '20':'20','21':'21','22':'22','23':'23','24':'24','25':'25','26':'26','27':'27','28':'28','29':'29',
        '30':'30','31':'31'}" theme="simple" ></s:select>
        	日
        </td>
      </tr>
  </table>
   <table align="center" border="0" width="100%" class="form_table">  
      <tr class="form_bottom">
        <td colspan="6">
        	<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData();">保存</a>&nbsp;&nbsp;&nbsp;&nbsp;
      		<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="$('#inputForm').resetForm();">重置</a>       
        </td>
      </tr>
  </table>
  </form>
</body>
</html>
<script type="text/javascript">

<c:if test="${isSuc=='true'}">
	easyAlert('提示信息','保存成功！','info',
	   function(){closeEasyWin('winId');}
	);
</c:if>
<c:if test="${isSuc=='false'}">
	easyAlert('提示信息','保存失败！','error');
</c:if>

</script>

