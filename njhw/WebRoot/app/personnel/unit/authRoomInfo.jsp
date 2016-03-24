<%@ page language="java" contentType="text/html;charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr class="wizard_list_td">
		<c:forEach var="roomLock" items="${roomInfos}" varStatus="stat">
			<td style="width: 20%">
				<c:if test="${roomLock.IS_CHECKED ne '1'}">
					<input type="checkbox" value="${roomLock.NODE_ID}" name="_chk"
						id="_chkid" />${roomLock.NAME}
													</c:if>
				<c:if test="${roomLock.IS_CHECKED eq '1'}">
					<input type="checkbox" value="${roomLock.NODE_ID}" name="_chk"
						id="_chkid" checked />${roomLock.NAME}
													</c:if>
				<!-- 不够五列的行用空列补齐 -->
				<c:if test="${stat.last && stat.count%5 != 0}">
					<c:set value="${5-(stat.count%5)}" var="restTd" />
					<c:forEach var="x" begin="1" end="${restTd}" step="1">
						<td width="20%"></td>
					</c:forEach>
				</c:if>
				<!-- 5列换一行 -->
				<c:if test="${stat.count%5==0 && stat.count%10==0}">
					</tr>
					<tr class="wizard_list_td">
				</c:if>
				<c:if test="${stat.count%5==0 && stat.count%10!=0}">
					</tr>
					<tr class="wizard_list_tds">
				</c:if>
		</td>
		</c:forEach>
	</tr>
</table>