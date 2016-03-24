<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/common/include/meta.jsp" %>
    <title>发放临时卡</title>
    <script src="${ctx}/scripts/widgets/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
    <script type="text/javascript">	
		$(document).ready(function(){
			var chk_options = { 
				all:'#checkAll',
				item:'.checkItem'
			};
			$(this).chk_init(chk_options);
			changebasiccomponentstyle();
			changedisplaytagstyle();
			changecheckboxstyle();
		});
		
		function saveData(){
			var frm = $('#inputForm');
			frm.submit();
		}
		
		
	
		
		function querySubmit(){
	
	$("#queryForm").submit(); 
	}	
		
		
	</script>
  </head>
  
<body topmargin="0" leftmargin="0" rightmargin="0">
	
	 <s:hidden name="vi_Id"/>
 	<h:panel id="query_panel" width="100%" title="查询条件">	
 	<form id="queryForm" action="visitorList1.act" method="post"  autocomplete="off">
      <table align="center"  id="hide_table" border="0" width="100%" class="form_table">
       
        <tr>   
          <td class="form_label" width="10%" align="left">
                         访客姓名：
          </td>
          <td width="20%">
           <s:textfield name="viName" theme="simple" size="18" value=""/>  
          </td>
          
           <td class="form_label"><font style="color:red">*</font>卡号：</td>
        <td>
           
              <s:textfield name="excardId" theme="simple" size="18" value=""/>  
        </td> 
          <td class="form_label" width="10%" align="left">
       	  	预约开始时间：       	  	
          </td>
          <td>
	         	<s:textfield onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="vsSt" name="vsSt" cssClass="Wdate">
				</s:textfield>
			
          </td>
         <td class="form_label" width="10%" align="left">
       	  	预约结束时间：       	  	
          </td>
          <td>
	         	
				<s:textfield onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})" id="vsEt" name="vsEt" cssClass="Wdate">
				</s:textfield>
          </td>
           <td class="form_label" width="10%" align="left">
       	     身份证号：      	 
          </td>
          <td width="20%">
            <s:textfield name="residentNo" theme="simple" size="18"/>  
          </td>
          

        </tr> 
      
        <tr>
          <td colspan="8" class="form_bottom" >
            <s:textfield name="page.pageSize" id="pageSize" theme="simple" cssStyle="width:20px" onblur="return checkPageSize();" onkeyup="value=value.replace(/[^\d]/g,'');" />条/页&nbsp;&nbsp;
          	<a href="javascript:void(0);" class="easyui-linkbutton" id="searchbutton" iconCls="icon-search" onclick="querySubmit();">查询</a>
          	<a href="javascript:void(0);" class="easyui-linkbutton" id="resetbutton" iconCls="icon-reload" onclick="resetCheckboxForm('#queryForm');">重置</a>
          </td>
        </tr>
      </table>     
      </form>
   </h:panel>
	<h:page id="list_panel" width="100%" title="结果列表">		
		<d:table name="page.result" id="row" uid="row" export="false" class="table"	requestURI="list.act?exFile=1">	
			<d:col style="width:30" title="序号" class="display_centeralign">
				${row_rowNum+((page.pageNo-1)*page.pageSize)}
			</d:col>
				
		    <d:col property="VI_NAME"   class="display_centeralign"   title="访客姓名"/>
		    <d:col property="RESIDENT_NO" class="display_leftalign" title="身份证"/>
		    <d:col property="CARD_TYPE" dictTypeId="CARD_TYPE" class="display_leftalign" title="卡类型"/>
		    <d:col property="CARD_ID"  class="display_leftalign" title="卡号"/>
		    <d:col property="VS_ST" class="display_centeralign" title="预约时间"/>
		    <d:col property="VS_TYPE" dictTypeId="VS_TYPE" class="display_centeralign" title="预约类型"/>
		  
		    <d:col property="VS_NUM" class="display_centeralign" title="访客人数"/>
		    <d:col property="VS_FLAG" dictTypeId="REG_VISIT_FLAG" class="display_centeralign" title="来访状态"/>
		     <d:col class="display_centeralign"  media="html" title="操作">	
				 <a href="javascript:void(0);" onclick="updateRecord2('${row.VS_ID}')">[退卡]</a>&nbsp;	
			</d:col>
	  
		</d:table>
   </h:page>
<script type="text/javascript">

	function updateRecord2(id){
		//alert(id);
		//查看
		var url = "${ctx}/app/visitor/getAllInfor1.act?vsId="+id;
		
		openEasyWin("winIdss","查看发卡信息",url,"800","400",true);
	}
	function delRecord(){
		if($("input[@type=checkbox]:checked").size()==0){
			easyAlert('提示信息','请勾选要删除的记录！','info');
		}else{
			easyConfirm('提示', '确定删除？', function(r){
				if (r){
					$('#queryForm').attr("action","delete.act");
					$('#queryForm').submit();
				}
			});		
		}
    }

    

</script>
</body>
</html>

