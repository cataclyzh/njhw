 function calendarCompare() {
	 
	var a=document.getElementById("repairStartTime").value; 
	var arr = a.split("-");
    var starttime = new Date(arr[0], arr[1], arr[2]);
    var starttimes = starttime.getTime();

	var b=document.getElementById("repairEndTime").value; 
    var arrs = b.split("-");
    var lktime = new Date(arrs[0], arrs[1], arrs[2]);
    var lktimes = lktime.getTime();

    if (starttimes >= lktimes) {

        alert('起始时间大于截止时间，请重新选择！');      

        return false;
    }
    else
        return true;

}
 
 function calendarCompareStorage() {
	 
		var a=document.getElementById("storageRecordListStratTime").value; 
		var arr = a.split("-");
	    var starttime = new Date(arr[0], arr[1], arr[2]);
	    var starttimes = starttime.getTime();

		var b=document.getElementById("storageRecordListEndTime").value; 
	    var arrs = b.split("-");
	    var lktime = new Date(arrs[0], arrs[1], arrs[2]);
	    var lktimes = lktime.getTime();

	    if (starttimes >= lktimes) {

	        alert('起始时间大于截止时间，请重新选择！');      

	        return false;
	    }
	    else
	        return true;

	}
 
 /*
 $(document).ready(function(){
		//日期比较
		 jQuery.validator.methods.compareDate = function(value, element, param) {
         var beginTime = jQuery(param).val();
        // var date1 = new Date(Date.parse(beginTime.replace("-", "/")));
        // var date2 = new Date(Date.parse(value.replace("-", "/")));
         if(beginTime=="" || value==""){
         	return true;
         }else{
         	return beginTime < value;
         }
     };
		
		$("#queryForm").validate({
			meta :"validate",// 采用meta String方式进行验证（验证内容与写入class中）
			errorElement :"div",// 使用"div"标签标记错误， 默认:"label"
			rules: {
				endTime:{
					compareDate: "#beginTime"
				}
			},
			messages:{ 
             endTime:{
                 compareDate: "结束日期必须大于开始日期!"
            		 }
			}
		});
	});
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
	
	function querySubmit(){
		$("#queryForm").submit();
	}
	function resetForm(){
		$("#beginTime").val('');
		$("#endTime").val('');
	} 
 */