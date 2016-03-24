function loadBeforeStorageList() {
	var layout = document.getElementById("layout");
	var box_storage_in = document.getElementById("box_storage_in");
	var back_storage_in = document.getElementById("back_storage_in");
	
	var box_storage_out = document.getElementById("box_storage_out");
	var back_storage_out = document.getElementById("back_storage_out");
	
	var box_storage_modify = document.getElementById("box_storage_modify");
	var back_storage_modify = document.getElementById("back_storage_modify");
	
	
	layout.style.display = "none";

	box_storage_in.style.display = "none";
	back_storage_in.style.display = "none";
	
	box_storage_out.style.display = "none";
	back_storage_out.style.display = "none";

	box_storage_modify.style.display = "none";
	back_storage_modify.style.display = "none";
}


//----------------------in
function storageListInOccur(){
	var layout = document.getElementById("layout");
	var box_storage_in = document.getElementById("box_storage_in");
	var back_storage_in = document.getElementById("back_storage_in");
	
	layout.style.display = "block";
    
	box_storage_in.style.display = "block";
	back_storage_in.style.display = "block";
    timeContol();

}

function storageListInCancel(){
	var layout = document.getElementById("layout");
	var box_storage_in = document.getElementById("box_storage_in");
	var back_storage_in = document.getElementById("back_storage_in");
	
	layout.style.display = "none";

	box_storage_in.style.display = "none";
	back_storage_in.style.display = "none";
}

//--------------------out
function storageListOutOccur(){
	var layout = document.getElementById("layout");
	var box_storage_out = document.getElementById("box_storage_out");
	var back_storage_out = document.getElementById("back_storage_out");
	
	layout.style.display = "block";
    
	box_storage_out.style.display = "block";
	back_storage_out.style.display = "block";
    timeContol();

}

function storageListOutCancel(){
	var layout = document.getElementById("layout");
	var box_storage_out = document.getElementById("box_storage_out");
	var back_storage_out = document.getElementById("back_storage_out");
	
	layout.style.display = "none";

	box_storage_out.style.display = "none";
	back_storage_out.style.display = "none";
}


//--------------------modify
function storageListModifyOccur(){
	var layout = document.getElementById("layout");
	var box_storage_modify = document.getElementById("box_storage_modify");
	var back_storage_modify = document.getElementById("back_storage_modify");
	
	layout.style.display = "block";
    
	box_storage_modify.style.display = "block";
	back_storage_modify.style.display = "block";

}

function storageListModifyCancel(){
	var layout = document.getElementById("layout");
	var box_storage_modify = document.getElementById("box_storage_modify");
	var back_storage_modify = document.getElementById("back_storage_modify");
	
	layout.style.display = "none";

	box_storage_modify.style.display = "none";
	back_storage_modify.style.display = "none";
}




//-------------------time control

function timeContol(){
	var timeInsert=new Date();
	var timeYear=timeInsert.getFullYear();
	var timeMonth=timeInsert.getMonth()+1;
	var timeDay=timeInsert.getDate();
	if(timeMonth<10){
	timeMonth="0"+timeMonth;
	}
	if(timeDay<10){
	timeDay="0"+timeDay;
	}
	document.getElementById("storageListInTime").value=timeYear+"-"+timeMonth+"-"+timeDay;
	document.getElementById("storageListOutTime").value=timeYear+"-"+timeMonth+"-"+timeDay;

}
