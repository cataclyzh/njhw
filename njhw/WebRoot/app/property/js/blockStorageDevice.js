function loadBeforeStorageDevice() {
	var layout = document.getElementById("layout");
	var box_add_storage_device = document.getElementById("box_add_storage_device");
	var back_add_storage_device = document.getElementById("back_add_storage_device");
	
	var box_modify_storage_device = document.getElementById("box_modify_storage_device");
	var back_modify_storage_device = document.getElementById("back_modify_storage_device");
	
	
	var box_view_storage_device = document.getElementById("box_view_storage_device");
	var back_view_storage_device = document.getElementById("backv_view_storage_device");
	
	layout.style.display = "none";

	box_add_storage_device.style.display = "none";
	back_add_storage_device.style.display = "none";
	
	box_modify_storage_device.style.display = "none";
	back_modify_storage_device.style.display = "none";
	
	box_view_storage_device.style.display = "none";
	back_view_storage_device.style.display = "none";
	
}
//---------------------------------add
function loadAfterAddStorageDevice() {

	var layout = document.getElementById("layout");
	var box_add_storage_device = document.getElementById("box_add_storage_device");
	var back_add_storage_device = document.getElementById("back_add_storage_device");

	layout.style.display = "block";
	box_add_storage_device.style.display = "block";
	back_add_storage_device.style.display = "block";
}

function closeAfterAddStorageDevice() {
	var layout = document.getElementById("layout");
	var box_add_storage_device = document.getElementById("box_add_storage_device");
	var back_add_storage_device = document.getElementById("back_add_storage_device");

	layout.style.display = "none";
	box_add_storage_device.style.display = "none";
	back_add_storage_device.style.display = "none";
}




//-----------------------------------update
function loadAfterModifyStorageDevice() {

	var layout = document.getElementById("layout");
	var box_modify_storage_device = document.getElementById("box_modify_storage_device");
	var back_modify_storage_device = document.getElementById("back_modify_storage_device");

	layout.style.display = "block";
	box_modify_storage_device.style.display = "block";
	back_modify_storage_device.style.display = "block";
}

function closeAfterModifyStorageDevice() {
	var layout = document.getElementById("layout");
	var box_modify_storage_device = document.getElementById("box_modify_storage_device");
	var back_modify_storage_device = document.getElementById("back_modify_storage_device");

	layout.style.display = "none";
	box_modify_storage_device.style.display = "none";
	buttonbox_modify_storage_device.style.display = "none";
	back_modify_storage_device.style.display = "none";
}

//-----------------------------------view
function loadAfterViewStorageDevice() {

	var layout = document.getElementById("layout");
	var box_view_storage_device = document.getElementById("box_view_storage_device");
	var back_view_storage_device = document.getElementById("backv_view_storage_device");

	layout.style.display = "block";
	box_view_storage_device.style.display = "block";
	back_view_storage_device.style.display = "block";
}

function closeAfterViewStorageDevice() {
	var layout = document.getElementById("layout");
	var box_view_storage_device = document.getElementById("box_view_storage_device");
	var back_view_storage_device = document.getElementById("backv_view_storage_device");

	layout.style.display = "none";
	box_view_storage_device.style.display = "none";
	back_view_storage_device.style.display = "none";
}

