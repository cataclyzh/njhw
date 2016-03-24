function loadBeforeStorageDeviceType() {
	var layout = document.getElementById("layout");
	var box_add_storage_device_type = document.getElementById("box_add_storage_device_type");
	var back_add_storage_device_type = document.getElementById("back_add_storage_device_type");
	
	var box_modify_storage_device_type = document.getElementById("box_modify_storage_device_type");
	var back_modify_storage_device_type = document.getElementById("back_modify_storage_device_type");

	var box_view_storage_device_type = document.getElementById("box_view_storage_device_type");
	var back_view_storage_device_type = document.getElementById("back_view_storage_device_type");
	
	layout.style.display = "none";

	box_add_storage_device_type.style.display = "none";
	back_add_storage_device_type.style.display = "none";
	
	box_modify_storage_device_type.style.display = "none";
	back_modify_storage_device_type.style.display = "none";
	
	box_view_storage_device_type.style.display = "none";
	back_view_storage_device_type.style.display = "none";

}


//-------------------------add
function storageDeviceTypeAddOccur() {

	var layout = document.getElementById("layout");
	var box_add_storage_device_type = document.getElementById("box_add_storage_device_type");
	var back_add_storage_device_type = document.getElementById("back_add_storage_device_type");

	layout.style.display = "block";
	box_add_storage_device_type.style.display = "block";
	back_add_storage_device_type.style.display = "block";
}

function storageDeviceTypeAddCancel() {
	var layout = document.getElementById("layout");
	var box_add_storage_device_type = document.getElementById("box_add_storage_device_type");
	var back_add_storage_device_type = document.getElementById("back_add_storage_device_type");

	layout.style.display = "none";
	box_add_storage_device_type.style.display = "none";
	back_add_storage_device_type.style.display = "none";
}


//--------------------------modify
function storageDeviceTypeModifyOccur() {

	var layout = document.getElementById("layout");
	var box_modify_storage_device_type = document.getElementById("box_modify_storage_device_type");
	var back_modify_storage_device_type = document.getElementById("back_modify_storage_device_type");

	layout.style.display = "block";
	box_modify_storage_device_type.style.display = "block";
	back_modify_storage_device_type.style.display = "block";
}

function storageDeviceTypeModifyCancel() {
	var layout = document.getElementById("layout");
	var box_modify_storage_device_type = document.getElementById("box_modify_storage_device_type");
	var back_modify_storage_device_type = document.getElementById("back_modify_storage_device_type");

	layout.style.display = "none";
	box_modify_storage_device_type.style.display = "none";
	back_modify_storage_device_type.style.display = "none";
}

//--------------------------view
function storageDeviceTypeViewOccur() {

	var layout = document.getElementById("layout");
	var box_view_storage_device_type = document.getElementById("box_view_storage_device_type");
	var back_view_storage_device_type = document.getElementById("back_view_storage_device_type");

	layout.style.display = "block";
	box_view_storage_device_type.style.display = "block";
	back_view_storage_device_type.style.display = "block";
}

function storageDeviceTypeViewCancel() {
	var layout = document.getElementById("layout");
	var box_view_storage_device_type = document.getElementById("box_view_storage_device_type");
	var back_view_storage_device_type = document.getElementById("back_view_storage_device_type");

	layout.style.display = "none";
	box_view_storage_device_type.style.display = "none";
	back_view_storage_device_type.style.display = "none";
}
