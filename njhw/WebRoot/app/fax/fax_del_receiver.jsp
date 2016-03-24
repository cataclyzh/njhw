<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>


<html xmlns="http://www.w3.org/1999/xhtml">


	<head>


    <title>My JSP 'fax_add_receiver.jsp' starting page</title>
    
	<%@ include file="/common/include/meta.jsp"%>	
	<link rel="stylesheet"	href="${ctx}/styles/jquery-ui-theme/custom-theme/jquery-ui-1.10.3.custom.css" />
	<script src="${ctx}/scripts/jquery-ui/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
	<script src="${ctx}/app/integrateservice/paddressbook/js/queryString.js" type="text/javascript"></script>

	
	<script type="text/javascript">
	
		$(function(){
			$("input[type=submit],input[type=button],input[type=reset],a, button").button().click(function(event) {
				//event.preventDefault();
			});
			var receiver = "";
			var hidden = "";
			var rec = "";
			var hid = "";
			
			
			if($.trim(getQueryString("receiver")) != ""){
				
				receiver = getQueryString("receiver");
				//console.log(unescape(receiver));				
				rec = receiver.split(";");
			}
			if($.trim(getQueryString("hidden")) != ""){
				
				hidden = getQueryString("hidden");
				hid = hidden.split(";");
			}
			
			//显示数据
			
			var start = "<tr>";
			var end = "</tr>";
			var contents = "";
			
			if (hid != "" && rec != ""){
			
			
				for (var i=0;i<rec.length;i++){
					
					//如果有区号跟分机号码，需要判断
					
					contents += start ;
					//contents += "<td>" +  "<input type='text' name='area' style='width: 50px;border:none;' />" + "</td>";
					contents += "<td class='even'>" + "<input type='text' style='text-align:center;' name='num' onfocus='this.blur();' value='" +  rec[i] + "'/></td>";
					contents += "<td class='odd'>" + "<a href='javascript:void(0);' class='del' name='" + hid[i] + "'>删除</a> "  + "</td>";
					//contents += "<td>" +  "<input type='text' name='extension' style='width: 100px;' />" + "</td>";
					contents += end ;
				}
				
				
				$("#del-receiver-table").append(contents);
				
				$(".del").click(function(){
					
					//删除当前行
					$(this).parent().parent().remove();
					//删除对应真实收件人数据
					var name = $(this).attr("name");
					//删除数组元素 index = 索引   1 = 元素个数 
					//hid.splice(index,1);
					
					//var regex = new RegExp("(" + name+ "\;*)");
					var regex = new RegExp("(" + name+ "\;{0,1})");
					hidden = hidden.replace(regex,"");
					
					if (hidden.charAt(hidden.length-1) == ";"){
						
						hidden = hidden.substring(0,hidden.length-1); 
					}
					//修改隐藏域值
					$("#data").val(hidden); 
				});			
				
				/**
				* 设置参数到隐藏域
				* 点击确定要获取此参数
				*/
				$("#data").val(hidden);
				$("input[type=submit],input[type=button],input[type=reset],a, button").button().click(function(event) {
				//event.preventDefault();
				});
			}
		});
	
	</script>
	
	<style type="text/css">
		body{
			overflow:auto;
		}
		#del-receiver-table{
			
/* 			height:200px;
			overflow-y:scroll; */
			
			list-style: none outside none;
		    margin: 0;
		    padding: 0;
		   /* width:100%;*/
		    padding-top:10px;
		
		}
		.even{
			
			width:60%;
			text-align:center;
		
		}
		.odd{
			width:40%;
			text-align:center;
		}
		/* .del{
			border: 1px solid #8090B4;
		    color: orange;
		    display: inline-block;
		    height: 22px;
		    line-height: 22px;
		    text-align: center;
		    text-decoration: none;
		    width: 50px;
		} */
		/* a:hover{
			/*color:red;*/
			background:powderblue;
		} */
 		input[type="text"] {
		
			text-align:center;
			height: 18px;
			line-height:18px;
			
		} 
	
	</style>

  </head>
  
  <body style="background:none;color:#999999;">
  	
  	<input type="hidden" id="data" />
  
  	<center>
    <table id="del-receiver-table"  style="width: 100%;">
    
    
        	<tr>
    	
    		<th class="even">
    			
    			收件人
    		
    		</th>
    		<th class="odd">
    			
    			操作
    		
    		</th>
    	
    	</tr>
    
    
    		<!-- 
    	<tr>
    		<td>
    			
    			<input type="text" value="+86" id="international-prefix" style="width: 50px;"/> -
    		
    		</td>
    		-->
    		<!-- 
    		<td>
    			
    			<input type="text" id="area-code" style="width: 50px;"/> -
    		
    		</td>
    		<td>
    			
    			<input type="text" id="fax-number" style="width: 100px;"/> -
    		
    		</td>
    		<td>
    			
    			<input type="text" id="extension-number" style="width: 100px;"/> 
    		
    		</td>
    		-->
    		<!-- 
    		<td>
    			
    			<input type="button" value="确定" id="confirm-receiver">
    		
    		</td>
    	
    	</tr>
    		 -->
    
    </table>
    </center>
  </body>
</html>
