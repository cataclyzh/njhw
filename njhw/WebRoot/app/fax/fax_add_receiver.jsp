<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/common/include/taglibs.jsp"%>


<html xmlns="http://www.w3.org/1999/xhtml">


	<head>

    <title>My JSP 'fax_add_receiver.jsp' starting page</title>
    
	<%@ include file="/common/include/meta.jsp"%>


	<style type="text/css">
	
		input[type="text"] {
		
			text-align:center;
			height: 18px;
			line-height:18px;
			
		}
		
		#add-receiver-table{
		
			padding-top:15px;
			
		}
		
		
		.dialog-current{
		
			border:1px solid blue;
		}
		
	
	</style>
	
	<script type="text/javascript">
	
		$(function(){
		
/* 	
			$("input[type=text]").focus(function(){
				
				$(this).addClass("dialog-current");
			});
			
			$("input[type=text]").blur(function(){
				
				$(this).removeClass("dialog-current");
			}); */
			//$("#fax-number,#extension-number").val("");
		
		});
	
	</script>

  </head>
  
  <body style="background:none;color:#999999;">
  	<center>
    <table id="add-receiver-table" >
    
    
        	<tr>
    	
    		<!--  
    		<th style="margin-left:0px;display:inline-block;">
    			
    			国际代码
    		
    		</th>
    		-->
    		<th>
    			
    			区号
    		
    		</th>
    		<th>
    			
    			传真号码
    		
    		</th>
    		<th>
    			
    			分机号码
    		
    		</th>
    	
    	</tr>
    
    
    	<tr>
    		<!-- 
    		<td>
    			
    			<input type="text" value="+86" id="international-prefix" style="width: 50px;"/> -
    		
    		</td>
    		-->
    		<td>
    			
    			<input type="text" value="025" id="area-code" style="width: 50px;"/> -
    		
    		</td>
    		<td>
    			
    			<input type="text" id="fax-number" style="width: 100px;"/> -
    		
    		</td>
    		<td>
    			
    			<input type="text" id="extension-number" style="width: 100px;"/> 
    		
    		</td>
    		<!-- 
    		<td>
    			
    			<input type="button" value="确定" id="confirm-receiver">
    		
    		</td>
    		 -->
    	
    	</tr>
    
    </table>
    </center>
  </body>
</html>
