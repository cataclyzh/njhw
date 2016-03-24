	$(function(){		
			//取json文件进行联想输入
			$.getJSON('../../common/files/getOrgJson.act?type=3', function(data) { 
				$('#orgcode').bind('paste', function(e) {
				       return false;
				});
				$("#orgcode").autocomplete(data, {
					minChars: 1,
					matchCase:false,
					autoFill: false,
					mustMatch:true,
					max: 20,
					width: 350,
					scrollHeight: 150,
					formatItem: function(row, i, max,term) {
						var v = $("#orgcode").val();	
						if(row.orgcode.indexOf(v) == 0 ){
							return   row.orgcode + "&nbsp(&nbsp"+ row.taxname+"&nbsp)";
						}
						else
							return false;
					},
					formatMatch: function(row, i, max) {
						return  row.orgcode + "&nbsp(&nbsp"+ row.taxname+"&nbsp)";
					},
					formatResult: function(row) {
						return row.orgcode;
					}
				});
		}); 
});
			