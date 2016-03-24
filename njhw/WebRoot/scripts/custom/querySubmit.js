	$(function(){		
			//为inputForm注册validate函数
			$("#queryForm").validate({
				errorElement :"div",
				onblur : false,
				onkeyup:false,
				rules: {
//					buytaxno: {
//						required:true,
//						isNumOrLetter:true,
//						sbhLength:true
//					}
				},
				messages: {
//					buytaxno: {
//						required:　"请输入税号，支持联想输入!"
//					}
				},
				submitHandler: function(form) {
						$("#loadingdiv").show();
						//disableButton();
						if(!($.browser.msie&&parseInt($.browser.version)<=6)){
							$('a.easyui-linkbutton').linkbutton('disable');
						}
						form.submit();
				}
			});
			//取json文件进行联想输入
			$.getJSON('../../common/files/getOrgJson.act?type=0', function(data) { 
				$('#buytaxno').bind('paste', function(e) {
				       return false;
				});
				$("#buytaxno").autocomplete(data, {
					minChars: 1,
					matchCase:false,//不区分大小写
					autoFill: false,
					mustMatch:true,
					max: 20,
					width: 350,//add by sjy 设置宽度
					scrollHeight: 150,
					formatItem: function(row, i, max,term) {
						var v = $("#buytaxno").val();		
						if(row.taxno.indexOf(v) == 0 ){
							return   row.taxno + "&nbsp(&nbsp"+ row.taxname+"&nbsp)";
						}
						else
							return false;
					},
					formatMatch: function(row, i, max) {
						return  row.taxno + "&nbsp(&nbsp"+ row.taxname+"&nbsp)";
					},
					formatResult: function(row) {
						return row.taxno;
					}
				});
		}); 
});
			