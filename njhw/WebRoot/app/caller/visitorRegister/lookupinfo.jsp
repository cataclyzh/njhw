<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/include/meta.jsp" %>
    <title>发放临时卡</title>
    <script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
    <script type="text/javascript">	
	
	</script>
  </head>
  
  <body>
  

	
	<table align="center" border="0" width="100%" class="form_table">
	<input type="hidden" name="viId" id="viId" value="${map.VI_ID}"/>
	
      <tr>
	        <td class="form_label"><font style="color:red"></font>访客姓名：</td>
	        <td>
	       
	         ${map.VI_NAME}
	        </td>   
	     
	        <td class="form_label">身份证号：</td>
	        <td>
	       ${map.RESIDENT_NO }
	        </td>
	       <td class="form_label">手机号：</td>
	        <td>
	           ${map.VI_MOBILE }
	        </td>
        </tr>
        <tr>
         <td class="form_label">邮箱：</td>
	        <td>
	            ${map.VI_MAIL }
	        </td>
       		
	           <td class="form_label">车牌：</td>
	        <td>
	           ${map.PLATE_NUM }
	        </td>
	        <td class="form_label">受访者姓名：</td>
	        <td>
	            ${map.USER_NAME}
	        </td>
      </tr> 
      <tr>
     
      <td class="form_label">机构名：</td>
	        <td>
	           ${map.ORG_NAME}
	            
	        </td>
	      
	        <td class="form_label">来访人数：</td>
	        <td>
	          ${map.VS_NUM}
	        </td>
	         <td class="form_label">访问时间：</td>
	        <td>
	            ${map.VS_ST}
	        </td>
      </tr>
       <tr>
      
      <td class="form_label">结束时间：</td>
	        <td>
	           ${map.VS_ET}
	        </td>
	      <c:choose>
		    <c:when test="${map.CARD_TYPE eq '2'}">
		      
		   <td class="form_label">卡类型：</td>
	        <td>
	           <label>临时卡 </label>
	        </td>
            </c:when>
              <c:otherwise>
			
			   <td class="form_label">卡类型：</td>
	        <td>
	           <label> 市民卡 </label>
	        </td>
			  </c:otherwise>

	      </c:choose>    
	        <td class="form_label">卡号：</td>
	        <td>
	           <label>${map.CARD_ID}</label>
	        </td>
      
      </tr>
     
      <tr>
      <c:choose>
							<c:when test="${map.VS_RETURN eq '1'}">

								<td class="form_label">
									状态 ：
								</td>
								<td>
									<label>
										归还
									</label>
								</td>
							</c:when>
							<c:when test="${map.VS_RETURN eq '2'}">

								<td class="form_label">
									状态 ：
								</td>
								<td>
									<label>
										未归还
									</label>
								</td>
							</c:when>
							<c:otherwise>
							<td class="form_label">
									状态 ：
								</td>
								<td>
									<label>
										未绑定
									</label>
								</td>
							</c:otherwise>
						</c:choose>
         <td class="form_label">来访理由：</td>
	        <td>
	          <textarea rows="" cols="" readonly="readonly">${map.VS_INFO}</textarea> 
	        </td>
      </tr>
          <tr><td class="form_label" colspan="8">副卡列表</td></tr>
       <tr>
      
	      <td class="form_label"> 已绑定副卡 ：</td>
	        <td>
	           <label>${num}</label>张
	        </td>
	     
      </tr>
      <c:if test="${null!=vvaList}"> 
      <c:forEach items="${vvaList}" var="vva" varStatus="num">  
  
      <tr>
        <td  class="form_label"> 姓名 ：</td>
	        <td>
	           ${vva.vaBak1}
	        </td>
       <td  class="form_label"> 身份证号 ：</td>
	        <td>
	           ${vva.residentNo }
	        </td>
       <td class="form_label"> 副卡号 ：</td>
	        <td>
	           ${vva.cardId}
	        </td>
	        <c:choose>
		    <c:when test="${vva.vaReturn eq '1'}">
		      
		   <td class="form_label">状态 ：</td>
	        <td>
	           <label>归还 </label>
	        </td>
            </c:when>
              <c:otherwise>
			
			   <td class="form_label">状态 ：</td>
	        <td>
	           <label> 未归还 </label>
	        </td>
			  </c:otherwise>

	      </c:choose>    
   
      </tr>      
      </c:forEach>
      </c:if>
	</table> 
  </body>
</html>

