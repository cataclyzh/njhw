<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/pager.tld" prefix="w" %>
		<table width="100%">
		<tr>   
	       
	        <td width="100%">
			<table width="100%" style="border-collapse:collapse;color:#808080;" align="center">
					<tr>    
					<c:forEach var="menu" items="${page.result}" varStatus="stat">
						<td style="border: none; height: 110px;width:25%;text-align:center;">
						<div style="width:118px;height:78px;margin:0 auto;margin-top:5px;">
							<c:if test="${empty menu.CHK}">
							<div style="width:118px;height:78px;position:absolute;z-index:0"></div>
							<img name="img_chk" src="${menu.FD_PHOTO1}" width="118" height="78" style="z-index:-1;position:absolute;"/>
							<input type="checkbox" value="${menu.FD_ID}" name="chk" id="chk_" style="margin-top:63px;margin-left:103px;float:right;z-index:1;position:absolute;""/>
							</c:if>
							<c:if test="${menu.CHK == '1'}">
							<div style="width:118px;height:78px;position:absolute;z-index:0;background:black;filter:alpha(opacity=40);opacity:0.40;"></div>
							<img name="img_chk" src="${menu.FD_PHOTO1}" width="118" height="78" style="z-index:-1;position:absolute;"/>
							<input type="checkbox" value="${menu.FD_ID}" name="chk" id="chk_" checked style="margin-top:63px;margin-left:103px;float:right;z-index:1;position:absolute;""/>
							</c:if>
						</div>
						<font style="font-weight:bold;">${menu.FD_CLASS}</font><br/>${menu.FD_NAME}
						<!-- 不够四列的行用空列补齐 -->  
						<c:if test="${stat.last && stat.count%4 != 0}">  
						<c:set value="${4-(stat.count%4)}" var="restTd"/>                                    
						<c:forEach var="x" begin="1" end="${restTd}" step="1">  
						<td style="border: none; height: 110px;width:25%;background:#F6F5F1;"></td>  
						</c:forEach>  
						</c:if>  
						<!-- 4列换一行 -->  
						<c:if test="${stat.count%4==0}">
						</tr><tr>  
						</c:if>
						</td>
	                 </c:forEach>  
	                </tr>     
				</table>
 		</td>
 		</tr>
 		</table>
 		<w:pager pageSize="${page.pageSize}" pageNo="${page.pageNo}"  recordShowNum="3" url="${ctx}/app/din/ajaxRefreshMenu.act" recordCount="${page.totalCount}"/>
 		
<script type="text/javascript">
	$('input[name="chk"]').each(function(i,item) {
		$(item).click(function() {
			if (this.checked) {
				$(this).parent().find("div").css({"background":"black","filter":"alpha(opacity=40);", "opacity":"0.40"});
				selectArr.push($(item).val());
				if (selectArr[0] == '') {
					selectArr.splice(0,1);
				}
			} else {
				$(this).parent().find("div").css({"background":"none","filter":"alpha(opacity=40);", "opacity":"0.40"});
				for(var i=0;i<selectArr.length;i++){
					if ($(item).val() == selectArr[i]) {
						selectArr.splice(i,1);
						break;
					}
				}
			}
		});
		$(item).parent().find("img").click(function() {
			$(item).attr("checked", "checked");
			$(item).parent().find("div").css({"background":"black","filter":"alpha(opacity=40);", "opacity":"0.40"});
			selectArr.push($(item).val());
			if (selectArr[0] == '') {
				selectArr.splice(0,1);
			}
		});
		$(item).parent().find("div").click(function() {
			$(item).removeAttr("checked");
			$(item).parent().find("div").css({"background":"none","filter":"alpha(opacity=40);", "opacity":"0.40"});
			for(var i=0;i<selectArr.length;i++){
				if ($(item).val() == selectArr[i]) {
					selectArr.splice(i,1);
					break;
				}
			}
		});
	});
</script>