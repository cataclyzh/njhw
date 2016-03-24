<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title>CMS内容详情</title>
		<%@ include file="/common/include/meta.jsp"%>
<script src="${ctx}/app/portal/toolbar/showModel.js" type="text/javascript" ></script>
		<script type="text/javascript" src="${ctx }/ckeditor/ckeditor.js"></script>
		<script type="text/javascript" src="${ctx }/ckfinder/ckfinder.js"></script>
		<script src="${ctx}/scripts/validation/card.js" type="text/javascript"></script>
		<script type="text/javascript">
			$(document).ready(function() {
				jQuery.validator.addMethod("isMobile", function(value, element) {
					var mobileZZ = /^0{0,1}(((13[0-9]{1})|(14[0-9]{1})|(18[0-9]{1})|(15[0-9]{1}))+\d{8})$/; 
					return mobileZZ.test(value.replace(/(^\s*)|(\s*$)/g, ""));
				});
				
				// 身份证号码验证 
				jQuery.validator.addMethod("isIdCardNo", function(value, element) {
					return this.optional(element) || idCardNoUtil.checkIdCardNo(value);     
				}, "请输入正确的身份证号码");
				
				$("#cmsContentDetailaForm").validate({		// 为inputForm注册validate函数
					meta :"validate",			// 采用meta String方式进行验证（验证内容与写入class中）
					errorElement :"div",		// 使用"div"标签标记错误， 默认:"label"
					rules: {
						cartName: {
							required: true,
							maxlength: 20
						},
						cartPhone: {
							required: true,
							isMobile:true
						},
						cartNum:{
							required: true,
							isIdCardNo: true
						}
					},
					messages:{
						cartName: {
							required: "请输入姓名",
							maxlength: "姓名长度不能大于20个字符"
						},
						cartPhone: {
							required: "请输入手机号",
							isMobile: "请输入正确的手机号"
						},
						cartNum:{
						  required: "请输入身份证号",
						  isIdCardNo: "请输入正确的身份证号码"
						}
					}
				});
			});
			
			function saveData(){
				if (showRequest()) {
					var options = {
					    dataType:'text',
					    success: function(responseText) { 
							if(responseText=='success') {
								easyAlert('提示信息', '认领成功！', "info", function() {
									closeEasyWin('DetailId');
								});
							 } else if(responseText =='error') {
					 			easyAlert("提示信息", "保存出错！","info");
							 } 
						}
					};
					$('#cmsContentDetailaForm').ajaxSubmit(options);
				}
			}
			
			function showRequest(){
				 return $("#cmsContentDetailaForm").valid();
			}
				
			function closeWin() {
				closeEasyWin('DetailId');
			}
		</script>
	</head>
	<body style="background: #fff;">
		<form id="cmsContentDetailaForm" action="saveClaim.act"  method="post"  autocomplete="off">
			<input type="hidden" name="id" id="id" value="${articleDetail.id}" />
			<table align="center" border="0" width="100%" class="form_table">
				<tr>
					<td colspan="2"> 发布信息 </td>
				</tr>
				<tr>
					<td class="form_label" width="150px;"> 标题： </td>
					<td align="left"> ${articleDetail.title} </td>
				</tr>
				<tr>
					<td class="form_label">内容： </td>
					<td align="left"> ${articleDetail.content} </td>
				</tr>
				<c:if test="${articleDetail.mid != '1'}">	<!-- 其他分类 -->
					<tr class="form_bottom">
						<td colspan="2">
							<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" onclick="closeWin()">关闭</a>
						</td>
					</tr>
				</c:if>
				
				<c:if test="${articleDetail.mid == '1'}">	<!-- 失物招领 -->
					<tr>
						<td colspan="2"> 认领人信息 </td>
					</tr>
					<c:if test="${articleDetail.cartFlag == '0'}">	<!-- 未领 -->
						
						<tr>
							<td class="form_label">姓名： </td>
							<td align="left"><input id="cartName" name="cartName" size="20" maxlength="20" /> </td>
						</tr>
						<tr>
							<td class="form_label">手机号： </td>
							<td align="left"><input id="cartPhone" name="cartPhone"  size="20" maxlength="20"/> </td>
						</tr>
						<tr>
							<td class="form_label">身份证号： </td>
							<td align="left"><input id="cartNum" name="cartNum"  size="20" maxlength="20"/> </td>
						</tr>
						<tr>
							<td class="form_label">备注： </td>
							<td align="left"><textarea rows="3" cols="40" id="cartMemo" name="cartMemo"></textarea> </td>
						</tr>
						<tr class="form_bottom">
							<td colspan="2">
								<a href="javascript:void(0);" class="easyui-linkbutton" iconCls="icon-save" onclick="saveData();">认领</a>
								<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" onclick="parent.closeIframe()">关闭</a>
							</td>
						</tr>
					</c:if>
					<c:if test="${articleDetail.cartFlag == '1'}">	<!-- 已领-->
						<tr>
							<td class="form_label">姓名：</td>
							<td align="left">${articleDetail.cartName}</td>
						</tr>
						<tr>
							<td class="form_label">手机号： </td>
							<td align="left">${articleDetail.cartPhone}</td>
						</tr>
						<tr>
							<td class="form_label">身份证号： </td>
							<td align="left">${articleDetail.cartNum}</td>
						</tr>
						<tr>
							<td class="form_label">认领时间： </td>
							<td align="left">${cartDate }</td>
						</tr>
						<tr>
							<td class="form_label">办理人： </td>
							<td align="left">${articleDetail.cartUname}</td>
						</tr>
						<tr>
							<td class="form_label">备注： </td>
							<td align="left">${articleDetail.cartMemo}</td>
						</tr>
						<tr class="form_bottom">
							<td colspan="2">
								<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" onclick="parent.closeIframe()">关闭</a>
							</td>
						</tr>
					</c:if>
				</c:if>
			</table>
		</form>
	</body>
</html>