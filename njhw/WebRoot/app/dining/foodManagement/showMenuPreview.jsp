<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/pager.tld" prefix="w" %>

<div style="height: 10px;"></div>
<c:forEach var="menu" items="${result}" varStatus="stat">
	<div style="width:25%;height:128px;float:left;text-align:center;color:#808080;">
		<div style="width:118px;height:78px;margin:0 auto;text-align:left;">
			<div style="width:118px;height:78px;margin-top:19px;">
				<img name="img_chk" src="${menu.FD_PHOTO1}" width="118" height="78" style="z-index:-1;position:absolute;"/>
				<div class="close-button"></div>
			</div>
			<input type="hidden" name="fdId" value="${menu.FD_ID}"/>
		</div>
		<font style="font-weight:bold;">${menu.FD_CLASS}</font><br/>${menu.FD_NAME}
	</div>
</c:forEach>  
<style type="text/css" charset="utf-8">
.close-button{
	cursor:pointer;
	z-index:1;
	position:absolute;
	margin-top:-19px;
	margin-left:99px;
	width:37px;
	height:37px;
	background:url('${ctx}/app/portal/toolbar/images/close_showModel.png') 0 0 no-repeat
}
.close-button:hover{
	background:url('${ctx}/app/portal/toolbar/images/close_showModel.png') 0 -100px no-repeat
}
</style>

<script type="text/javascript">
	$('.close-button').each(function(i,item) {
		$(item).click(function() {
			for(var i=0;i<selectArr.length;i++){
				if ($(this).parent().parent().find("input").val() == selectArr[i]) {
					selectArr.splice(i,1);
					break;
				}
			}
			
			$(this).parent().parent().parent().remove();
		});
	});
</script>