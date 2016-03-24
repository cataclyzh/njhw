	$(function(){		
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
			