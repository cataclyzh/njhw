function loadBeforeStorageRecordList() {
	var layout = document.getElementById("layout");
	var box_view_storage_record = document.getElementById("box_view_storage_record");
	var back_view_storage_record = document.getElementById("back_view_storage_record");

	
	
	layout.style.display = "none";

	box_view_storage_record.style.display = "none";
	back_view_storage_record.style.display = "none";
}


//-----------------view
function storageRecordListViewOccur(){
	var layout = document.getElementById("layout");
	var box_view_storage_record = document.getElementById("box_view_storage_record");
	var back_view_storage_record = document.getElementById("back_view_storage_record");
	
	layout.style.display = "block";
	box_view_storage_record.style.display = "block";
	back_view_storage_record.style.display = "block";
	
}

function storageRecordListViewCancel(){
	var layout = document.getElementById("layout");
	var box_view_storage_record = document.getElementById("box_view_storage_record");
	var back_view_storage_record = document.getElementById("back_view_storage_record");
	
	layout.style.display = "none";
	box_view_storage_record.style.display = "none";
	back_view_storage_record.style.display = "none";
	
}