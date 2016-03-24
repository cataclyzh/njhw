<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<%@ taglib uri="/WEB-INF/tlds/pager.tld" prefix="w" %>
<div id="page_div">
		<table width="700px" class="panel-table">
		
		<tr id="roomTR">   
	       
	        <td width="100%" colspan="3">
			<table width="100%" border="1" bordercolor="#a0c6e5" style="border-collapse:collapse;" align="center">
					<tr>    
					<c:forEach var="ipp" items="${page.result}" varStatus="stat">
						<td style="border: solid 1px #a0c6e5; height: 20px;width:20%;text-align:center;"  id="td_${ipp.TEL_ID}">
						<c:if test="${ipp.YN eq '1'}">
						&nbsp;&nbsp;${ipp.TEL_NUM}
						</c:if>
						<c:if test="${ipp.YN eq '2'}">
							&nbsp;&nbsp;${ipp.TEL_NUM}
						</c:if>
						<c:if test="${ipp.YN eq '3'}">
							&nbsp;&nbsp;${ipp.TEL_NUM}
						</c:if>
						<c:if test="${ipp.YN eq '4'}">
							&nbsp;&nbsp;${ipp.TEL_NUM}
						</c:if>
						<div class="tooltip" id="tooltip_${ipp.TEL_ID}">
							<c:if test="${ipp.YN=='4'}">
								所属单位：${ipp.ORGNAME}<br/>
								所属个人：${ipp.USERNAME}<br/>
							</c:if>
							<c:if test="${ipp.YN=='2'}">
								所属单位：无<br/>
								所属个人：无<br/>
							</c:if>
							<c:if test="${ipp.YN=='1'}">
								所属单位：${ipp.ORGNAME}<br/>
								所属个人：无<br/>
							</c:if>
							<c:if test="${ipp.YN=='3'}">
								所属单位：${ipp.ORGNAME}<br/>
								所属个人：无<br/>
							</c:if>
							
								资源类型：
								<c:if test="${ipp.TEL_TYPE=='1'}">
									IP电话
								</c:if>
								<c:if test="${ipp.TEL_TYPE=='2'}">
									传真
								</c:if>
								<c:if test="${ipp.TEL_TYPE=='3'}">
									网络传真
								</c:if>
							
						</div>
						<!-- 不够四列的行用空列补齐 -->  
						<c:if test="${stat.last && stat.count%5 != 0}">  
						<c:set value="${5-(stat.count%5)}" var="restTd"/>                                    
						<c:forEach var="x" begin="1" end="${restTd}" step="1">  
						<td style="border: solid 1px #a0c6e5; height: 20px;width:20%;"></td>  
						</c:forEach>  
						</c:if>  
						<!-- 4列换一行 -->  
						<c:if test="${stat.count%5==0}">  
						</tr><tr>  
						</c:if>
						</td>
	                 </c:forEach>  
	                </tr>     
				</table>
 		</td>
 		</tr>
 		</table>
 		<w:pager pageSize="${page.pageSize}" pageNo="${page.pageNo}"  recordShowNum="3" url="${ctx}/app/per/ajaxRefreshTell.act" recordCount="${page.totalCount}"/>
</div>