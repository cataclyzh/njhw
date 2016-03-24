<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<%@ include file="/common/include/meta.jsp"%>
		<script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
		<title>预约申请</title>
		<script type="text/javascript">
		$(document).ready(function() {
			//日期比较
			 jQuery.validator.methods.compareDate = function(value, element, param) {
	            var beginTime = jQuery(param).val();
	            return beginTime < value;
	        };
			
			$("#vsCommets").focus();
			$("#inputForm").validate({		// 为inputForm注册validate函数
				meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
				errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
				rules: {
					vsSt:{
						required: true,
					},
					vsEt:{
						required: true,
						compareDate: "#vsSt"
					},
					vsCommets:{
						maxlength:200
					}
				},
				messages:{
					vsEt:{
	                    required: "开始时间不能为空",
	               	},
	                vsEt:{
	                    required: "结束时间不能为空",
	                    compareDate: "结束日期必须大于开始日期!"
	               	},
	               	vsCommets:{
						maxlength: "备注长度不能大于200个字符"
					}
				}
			});
		});
		
		function showRequest(){
			 return $("#inputForm").valid();
		}
		
		function saveData(type){
			if (showRequest()) {
				if (type == "rep") {
					$('#inputForm').attr("action","repulsePopup.act");
				}
				
				var options = {
				    dataType:'text',
				    success: function(responseText) {
						if(responseText=='success') { 
							alert("处理成功！");
							window.parent.location.reload();
					 	} else if(responseText =='statusError') {
					 		alert("此访问申请状态已变更，不能继续操作！");
							window.parent.location.reload();
						} else if(responseText =='error') {
					 		alert("加载访客信息出错！");
						}
					}
				};
				$('#inputForm').ajaxSubmit(options);
			}
		}
	</script>
	<style type="text/css">
		.left_div table
		{
			width:100%;
		}
	</style>
	</head>

	<body>
		<div style="float: left; margin-right: 10px;width:330px;" class="left_div">
			<h:panel width="330px" title="访客照片">
				<c:if test="${imgSrc == ''}">暂无访客照片</c:if>
				<c:if test="${imgSrc != ''}">
					<img src="${imgSrc}" alt="访客照片" height="235px" width="330px"/>
				</c:if>
			</h:panel>
		</div>
		<div style="float: left;">
			<h:panel  width="auto" title="访问信息">
				<form id="inputForm" action="affirmPopup.act" method="post" autocomplete="off">
					<table align="center" border="0" width="100%" class="form_table">
						<input name="vsId" id="vsId" value="${vmVisitInfo.VS_ID}" type="hidden"/>
						<tr>
							<td class="form_label"> <font style="color: red"></font>访客姓名： </td>
							<td>${vmVisitInfo.VI_NAME}</td>
							<td class="form_label"> <font style="color: red"></font>黑名单： </td>
							<td>
								<c:if test="${vmVisitInfo.IS_BLACK eq '1'}">已加入</c:if>
								<c:if test="${vmVisitInfo.IS_BLACK eq '2'}">未加入</c:if>
							</td>
						</tr>
						<tr>
							<td class="form_label"> <font style="color: red"></font>身份证号： </td>
							<td colspan="3">${vmVisitInfo.RESIDENT_NO}</td>
						</tr>
						<tr>
							<td class="form_label"> <font style="color: red"></font>访问事由： </td>
							<td colspan="3">${vmVisitInfo.VS_INFO}</td>
						</tr>
						<tr>
							<td class="form_label"> <font style="color: red"></font>预约类型： </td>				 
							
							<c:choose>
								<c:when test="${vmVisitInfo.VS_TYPE eq '1'}">
									<td> 主动预约</td>					
								</c:when>
								<c:when test="${vmVisitInfo.VS_TYPE eq '2'}">
									<td> 被动预约 </td>						
								</c:when>
								<c:when test="${vmVisitInfo.VS_TYPE eq '3'}">
									<td> 前台预约 </td>	
								</c:when>
							</c:choose>
							
							<td class="form_label"> <font style="color: red"></font>访问人数： </td>
							<td>${vmVisitInfo.VS_NUM}</td>
						</tr>
						<tr>
							<td class="form_label"> <font style="color: red"></font>预约时段： </td>
							<td colspan="3">${vmVisitInfo.VSST} ~ ${vmVisitInfo.VSET}</td>
						</tr>
						<c:if test="${vmVisitInfo.VS_FLAG == '01'}">
							<tr>
								<td class="form_label" > <font style="color: red"></font>被访者确认预约时段： </td>
								<td colspan="3">
									<input onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" id="vsSt" name="vsSt" cssClass="Wdate" value="${vmVisitInfo.VSST}"/> - 
									<input onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm'})" id="vsEt" name="vsEt" cssClass="Wdate" value="${vmVisitInfo.VSET}"/>
								</td>
							</tr>
						</c:if>
						<tr>
							<td class="form_label"> <font style="color: red"></font>备注： </td>
							<td colspan="3">
								<c:if test="${vmVisitInfo.VS_FLAG == '01'}">
									<s:textarea name="vsCommets" id="vsCommets" cols="60" rows="2"></s:textarea>
								</c:if>
								<c:if test="${vmVisitInfo.VS_FLAG != '01'}">
									${vmVisitInfo.VS_COMMETS}
								</c:if>
							</td>
						</tr>
					</table>
					<c:if test="${vmVisitInfo.VS_FLAG == '01'}">
						<table align="center" border="0" width="100%" class="form_table">
							<tr class="form_bottom">
								<td colspan="4">
									<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData('aff');" id="affirmbut">确认</a>
									<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData('rep');" id="repulsebut">拒绝</a>
								</td>
							</tr>
						</table>
					</c:if>
				</form>
			</h:panel>
		</div>
	</body>
</html>