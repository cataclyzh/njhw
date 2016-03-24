function viewLoadBeforeConferenceManager(){
var layout = document.getElementById("layout");
	
	var back_conference = document.getElementById("back_conference");

	var box_add_conference = document.getElementById("box_add_conference");
	var box_modify_conference = document.getElementById("box_modify_conference");	
	var box_view_conference = document.getElementById("box_view_conference");
	var box_value_conference = document.getElementById("box_value_conference");
	
	back_conference.style.display = "block";	
	layout.style.display = "block";

	box_add_conference.style.display = "none";
	box_modify_conference.style.display = "none";
	box_view_conference.style.display = "block";
	box_value_conference.style.display = "none";
}

function loadBeforeConferenceManager() {
	var layout = document.getElementById("layout");
	
	var back_conference = document.getElementById("back_conference");

	var box_add_conference = document.getElementById("box_add_conference");
	var box_modify_conference = document.getElementById("box_modify_conference");	
	var box_view_conference = document.getElementById("box_view_conference");
	var box_value_conference = document.getElementById("box_value_conference");
	
	back_conference.style.display = "none";	
	layout.style.display = "none";

	box_add_conference.style.display = "none";
	box_modify_conference.style.display = "none";
	box_view_conference.style.display = "none";
	box_value_conference.style.display = "none";
}

//-------------add
function conferenceManageAddOccur() {

	var layout = document.getElementById("layout");
	var box_add_conference = document.getElementById("box_add_conference");
	var back_conference = document.getElementById("back_conference");

	layout.style.display = "block";
	box_add_conference.style.display = "block";
	back_conference.style.display = "block";	
}

function conferenceManageAddCancel() {
	var layout = document.getElementById("layout");
	var box_add_conference = document.getElementById("box_add_conference");
	var back_conference = document.getElementById("back_conference");

	layout.style.display = "none";
	box_add_conference.style.display = "none";
	back_conference.style.display = "none";	
}

//------------modify
function conferenceManageModifyOccur() {

	var layout = document.getElementById("layout");
	var box_modify_conference = document.getElementById("box_modify_conference");
	var back_conference = document.getElementById("back_conference");

	layout.style.display = "block";
	box_modify_conference.style.display = "block";
	back_conference.style.display = "block";	
}

function conferenceManageModifyCancel() {
	var layout = document.getElementById("layout");
	var box_modify_conference = document.getElementById("box_modify_conference");
	var back_conference = document.getElementById("back_conference");

	layout.style.display = "none";
	box_modify_conference.style.display = "none";
	back_conference.style.display = "none";	
}


//----------------view
function conferenceManageViewOccur() {

	var layout = document.getElementById("layout");
	var box_view_conference = document.getElementById("box_view_conference");
	var back_conference = document.getElementById("back_conference");

	layout.style.display = "block";
	box_view_conference.style.display = "block";
	back_conference.style.display = "block";	
}

function conferenceManageViewCancel() {
	var layout = document.getElementById("layout");
	var box_view_conference = document.getElementById("box_view_conference");
	var back_conference = document.getElementById("back_conference");

	layout.style.display = "none";
	box_view_conference.style.display = "none";
	back_conference.style.display = "none";	
}

//---------------value
function conferenceManageValueOccur() {

	var layout = document.getElementById("layout");
	var box_value_conference = document.getElementById("box_value_conference");
	var back_conference = document.getElementById("back_conference");

	layout.style.display = "block";
	box_value_conference.style.display = "block";
	back_conference.style.display = "block";	
}

function conferenceManageValueCancel() {
	var layout = document.getElementById("layout");
	var box_value_conference = document.getElementById("box_value_conference");
	var back_conference = document.getElementById("back_conference");

	layout.style.display = "none";
	box_value_conference.style.display = "none";
	back_conference.style.display = "none";	
}