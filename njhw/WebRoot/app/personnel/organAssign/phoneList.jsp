<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>
		<table width="100%" class="panel-table">
		<tr>
		 <td class="form_label" style="color:#808080;font-size:13px;text-align:left;">
	        	全选<input type ="checkbox" style ="margin-top:5px" id="phone_check_all" onclick="javascript:chk_all();"/>
	        </td>
	       <td class="form_label" style ="padding-bottom:10px;text-align:left;">
	       </td>
	     </tr>
		<tr id="roomTR">   
	        <td width="100%" colspan="3">
	        <div id="phoneList" style="height:400px;overflow:auto;">
			<table width="100%" border="1" bordercolor="#a0c6e5" style="border-collapse:collapse;" align="center">
					<tr>    
					<c:forEach var="ipp" items="${result}" varStatus="stat">
						<td style="border: solid 1px #a0c6e5; height: 20px;width:20%;text-align:center;" id="td_${ipp.TEL_ID}">
						<input type="checkbox" value="${ipp.TEL_ID}_${ipp.TEL_NUM}_${ipp.YN}" name="chk" id="chk_${ipp.TEL_ID}"/>&nbsp;&nbsp;<label for="chk_${ipp.TEL_ID}">${ipp.TEL_NUM}</label>
						<div class="tooltip" id="tooltip_${ipp.TEL_ID}">
							<c:if test="${ipp.YN=='4'}">
								已被分配到个人用户<br/>用户姓名：${ipp.USERNAME}<br/>
							</c:if>
							<c:if test="${ipp.YN=='1'}">
								尚未分配到个人用户<br/>
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
			</div>
 		</td>
 		</tr>
 		</table>