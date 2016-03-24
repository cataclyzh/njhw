<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>
<h:page id="list_panel" width="100%" buttons="" title="">		
		<d:table name="page.result" id="row"  export="false" class="table" requestURI="list.act?exFile=1">
			<d:col style="width:30" title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>
		    <d:col property="dictname" class="display_leftalign" title="字典名称"/>
		    <d:col property="dictcode" class="display_leftalign" title="字典编码"/>
		    <d:col property="sortno"   class="display_centeralign"   title="排序"/>
		</d:table>
</h:page>