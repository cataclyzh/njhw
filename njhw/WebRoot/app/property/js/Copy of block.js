//function consumablesInfo(index) {
//	var deviceTypeSelect = document.getElementById("deviceTypeSelect");
//	var deviceSelect = document.getElementById("deviceSelect");
//	var deviceTypeId = deviceTypeSelect.options[deviceTypeSelect.selectedIndex].id;
//	var deviceTypeName = deviceTypeSelect.options[deviceTypeSelect.selectedIndex].value;
//	var deviceId = deviceSelect.options[deviceSelect.selectedIndex].id;
//	var deviceName = deviceSelect.options[deviceSelect.selectedIndex].value;
//	var deviceNumber = document.getElementById("deviceNumber").value;
//	
//	var consumablesInfo = {
//			'index': index,
//			'deviceTypeId' : deviceTypeId,
//			'deviceTypeName' : deviceTypeName,
//			'deviceId' : deviceId,
//			'deviceName' : deviceName,
//			'deviceNumber' : deviceNumber
//	};
//	
//	return consumablesInfo;
//	
//}
//function loadBefore() {
//	var layout = document.getElementById("layout");
//	var box_complete = document.getElementById("box_complete");
//	var buttonbox_complete = document.getElementById("buttonbox_complete");
//	var back_complete = document.getElementById("back_complete");
//
//	var box_add = document.getElementById("box_add");
//	var buttonbox_add = document.getElementById("buttonbox_add");
//	var back_add = document.getElementById("back_add");
//
//	var box_distribution = document.getElementById("box_distribution");
//	var buttonbox_distribution = document
//			.getElementById("buttonbox_distribution");
//	var back_distribution = document.getElementById("back_distribution");
//
//	var box_evaluate = document.getElementById("box_evaluate");
//	var buttonbox_evaluate = document.getElementById("buttonbox_evaluate");
//	var back_evaluate = document.getElementById("back_evaluate");
//
//	var deviceArray = new Array();
//	var index = 0;
//	var plusDeviceComplete = document.getElementById("CompletePlusButton");
//	
//	
//	
//	
//	
//
//	plusDeviceComplete.onclick = function() {
//		var deviceTypeSelect = document.getElementById("deviceTypeSelect");
//		var deviceSelect = document.getElementById("deviceSelect");
//		var deviceTypeId = deviceTypeSelect.options[deviceTypeSelect.selectedIndex].id;
//		var deviceTypeName = deviceTypeSelect.options[deviceTypeSelect.selectedIndex].value;
//		var deviceId = deviceSelect.options[deviceSelect.selectedIndex].id;
//		var deviceName = deviceSelect.options[deviceSelect.selectedIndex].value;
//		var deviceNumber = document.getElementById("deviceNumber").value;
//		var consumableInfo = consumablesInfo(index);
//		index++;
//		deviceArray=plusDevice(consumableInfo,deviceArray);
//	}
//
//	layout.style.display = "none";
//
//	box_complete.style.display = "none";
//	buttonbox_complete.style.display = "none";
//	back_complete.style.display = "none";
//
//	box_add.style.display = "none";
//	buttonbox_add.style.display = "none";
//	back_add.style.display = "none";
//
//	box_distribution.style.display = "none";
//	buttonbox_distribution.style.display = "none";
//	back_distribution.style.display = "none";
//
//	box_evaluate.style.display = "none";
//	buttonbox_evaluate.style.display = "none";
//	back_evaluate.style.display = "none";
//
//}
//
//function loadAfterComplete() {
//
//	var layout = document.getElementById("layout");
//	var box_complete = document.getElementById("box_complete");
//	var buttonbox_complete = document.getElementById("buttonbox_complete");
//	var back_complete = document.getElementById("back_complete");
//
//	layout.style.display = "block";
//	box_complete.style.display = "block";
//	buttonbox_complete.style.display = "block";
//	back_complete.style.display = "block";
//}
//
//function loadAfterAdd() {
//
//	var layout = document.getElementById("layout");
//	var box_add = document.getElementById("box_add");
//	var buttonbox_add = document.getElementById("buttonbox_add");
//	var back_add = document.getElementById("back_add");
//
//	layout.style.display = "block";
//	box_add.style.display = "block";
//	buttonbox_add.style.display = "block";
//	back_add.style.display = "block";
//}
//
//function loadAfterDistribution() {
//
//	var layout = document.getElementById("layout");
//	var box_distribution = document.getElementById("box_distribution");
//	var buttonbox_distribution = document
//			.getElementById("buttonbox_distribution");
//	var back_distribution = document.getElementById("back_distribution");
//
//	layout.style.display = "block";
//	box_distribution.style.display = "block";
//	buttonbox_distribution.style.display = "block";
//	back_distribution.style.display = "block";
//}
//
//function loadAfterEvaluate() {
//
//	var layout = document.getElementById("layout");
//	var box_evaluate = document.getElementById("box_evaluate");
//	var buttonbox_evaluate = document.getElementById("buttonbox_evaluate");
//	var back_evaluate = document.getElementById("back_evaluate");
//
//	layout.style.display = "block";
//	box_evaluate.style.display = "block";
//	buttonbox_evaluate.style.display = "block";
//	back_evaluate.style.display = "block";
//
//}
//
//function closeAfterComplete() {
//	var layout = document.getElementById("layout");
//	var box_complete = document.getElementById("box_complete");
//	var buttonbox_complete = document.getElementById("buttonbox_complete");
//	var back_complete = document.getElementById("back_complete");
//
//	layout.style.display = "none";
//	box_complete.style.display = "none";
//	buttonbox_complete.style.display = "none";
//	back_complete.style.display = "none";
//
//}
//
//function closeAfterAdd() {
//	var layout = document.getElementById("layout");
//	var box_back = document.getElementById("box_back");
//	var buttonbox_back = document.getElementById("buttonbox_back");
//	var back_back = document.getElementById("back_back");
//
//	layout.style.display = "none";
//	box_add.style.display = "none";
//	buttonbox_add.style.display = "none";
//	back_add.style.display = "none";
//
//}
//
//function closeAfterDistribution() {
//	var layout = document.getElementById("layout");
//	var box_distribution = document.getElementById("box_distribution");
//	var buttonbox_distribution = document
//			.getElementById("buttonbox_distribution");
//	var back_distribution = document.getElementById("back_distribution");
//
//	layout.style.display = "none";
//	box_distribution.style.display = "none";
//	buttonbox_distribution.style.display = "none";
//	back_distribution.style.display = "none";
//
//}
//
//function closeAfterEvaluate() {
//	var layout = document.getElementById("layout");
//	var box_evaluate = document.getElementById("box_evaluate");
//	var buttonbox_evaluate = document.getElementById("buttonbox_evaluate");
//	var back_evaluate = document.getElementById("back_evaluate");
//
//	layout.style.display = "none";
//	box_evaluate.style.display = "none";
//	buttonbox_evaluate.style.display = "none";
//	back_evaluate.style.display = "none";
//
//}
//
//function plusDevice(consumableInfo,deviceArray) {
//	deviceTypeName = consumableInfo.deviceTypeName;
//	deviceName = consumableInfo.deviceName;
//	deviceNumber = consumableInfo.deviceNumber;
//	var hiddenone = document.getElementById("hiddenone").value;
//	var hiddentwo = document.getElementById("hiddentwo").value;
//	var hiddenthree = document.getElementById("hiddenthree").value;
//	var numbertest = /^[0-9]*[1-9][0-9]*$/;
//
//	if (deviceTypeName.indexOf(") > 0) {
//		hiddenone = "false";
//	} else {
//		hiddenone = "true";
//	}
//	if (deviceName.indexOf(") > 0) {
//		hiddentwo = "false";
//	} else {
//		hiddentwo = "true";
//	}
//	if (deviceNumber.indexOf(") > 0) {
//		hiddenthree = "false";
//	} else if (!numbertest.test(deviceNumber)) {
//		hiddenthree = "false";
//	} else {
//		hiddenthree = "true";
//	}
//
//	if (hiddenone == "true" && hiddentwo == "true" && hiddenthree == "true") {
//		deviceArray.push(consumableInfo);
//		for ( var i = 0; i < deviceArray.length; i++) {
//			alert(deviceArray.length);
//			alert(deviceArray[i].deviceTypeId);
//			alert(deviceArray[i].deviceTypeName);
//			alert(deviceArray[i].deviceId);
//			alert(deviceArray[i].deviceName);
//			alert(deviceArray[i].deviceNumber);
//		}
//		draw(consumableInfo);
//
//	} else {
//		alert(");
//	}
//	return deviceArray;
//}
//
//
//function draw(consumableInfo,deviceArray) {
//	deviceName = consumableInfo.deviceName;
//	deviceNumber = consumableInfo.deviceNumber;
//
//	var newDiv = document.createElement("div");
//
//	newDiv.style.float = "left";
//	newDiv.style.width = "210px";
//	newDiv.style.height = "24px";
//	newDiv.style.background = "url(http://localhost:8080/xcds/images/tang_back1.jpg)";
//	newDiv.style.color = "#808080";
//	newDiv.style.paddingLeft = "10px";
//	newDiv.style.marginLeft = "10px";
//	newDiv.style.marginTop = "3px";
//	newDiv.style.paddingTop = "3px";
//
//	var newDivClose = document.createElement("divclose");
//	newDivClose.style.float = "left";
//	newDivClose.style.width = "25px";
//	newDivClose.style.height = "24px";
//	newDivClose.style.background = "url(http://localhost:8080/xcds/images/tang_back2.jpg)";
//	newDivClose.style.color = "#808080";
//	newDivClose.style.marginTop = "3px";
//	newDivClose.style.paddingTop = "3px";
//
//	document.getElementById("tang_back_display").appendChild(newDiv);
//	newDiv.innerHTML = deviceName + " * " + deviceNumber;
//	document.getElementById("tang_back_display").appendChild(newDivClose);
//
//	
//	newDivClose.onclick = function(deviceArray) {
//		document.getElementById("tang_back_display").removeChild(newDivClose);
//		document.getElementById("tang_back_display").removeChild(newDiv);
//		deviceArray=deviceArray.splice(consumableInfo.index,consumableInfo.index);
//		return deviceArray;
//
//	}
//}
//
//function plusUser() {
//
//	var repair = document.getElementById("DistributionRepairUserName").value;
//	var hiddenrepaire = document.getElementById("hiddenrepaire").value;
//
//	if (repair.indexOf() > 0) {
//		hiddenrepaire = "false";
//	} else {
//		hiddenrepaire = "true";
//	}
//
//	if (hiddenrepaire == "true") {
//		var divUser = document.createElement("divUser");
//		divUser.style.float = "left";
//		divUser.style.width = "50px";
//		divUser.style.height = "24px";
//		divUser.style.background = "url(http://localhost:8080/xcds/images/tang_back.jpg)";
//		divUser.style.color = "#808080";
//		divUser.style.paddingLeft = "10px";
//		divUser.style.marginLeft = "10px";
//		divUser.style.marginTop = "3px";
//		divUser.style.paddingTop = "3px";
//
//		var divUserClose = document.createElement("divUserClose");
//		divUserClose.style.float = "left";
//		divUserClose.style.width = "25px";
//		divUserClose.style.height = "24px";
//		divUserClose.style.background = "url(http://localhost:8080/xcds/images/tang_back2.jpg)";
//		divUserClose.style.color = "#808080";
//		divUserClose.style.marginTop = "3px";
//		divUserClose.style.paddingTop = "3px";
//
//		document.getElementById("tang_back_user_display").appendChild(divUser);
//		divUser.innerHTML = repair;
//		document.getElementById("tang_back_user_display").appendChild(
//				divUserClose);
//
//	} else {
//		alert("");
//	}
//
//	divUserClose.onclick = function() {
//		document.getElementById("tang_back_user_display").removeChild(
//				divUserClose);
//		document.getElementById("tang_back_user_display").removeChild(divUser);
//
//	}
//
//}
//
///*
// * 
// * $(document).ready(function(){ //
// * 
// * $("#CompleteForm").validate({ meta :"validate",// meta
// * String errorElement :"alert",// 
// * од:"label" rules: { DeviceTypeBox:"required", DeviceTypeNameBox:"required",
// * DeviceTypeNumberBox:{ required:true, digits:true } }, messages:{
// * DeviceTypeBox:"", DeviceTypeNameBox:"",
// * DeviceTypeNumberBox:"" } }); });
// * $("#CompletePlusButton").click(function () { var
// * newDiv=document.createElement("div"); newDiv.style.float="left";
// * newDiv.style.width="210px"; newDiv.style.height="24px";
// * newDiv.style.background="url(http://localhost:8080/xcds/images/tang_back1.jpg)";
// * newDiv.style.color="#808080"; newDiv.style.paddingLeft="10px";
// * newDiv.style.marginLeft="10px"; newDiv.style.marginTop="3px";
// * newDiv.style.paddingTop="3px";
// * 
// * 
// * var newDivClose=document.createElement("divclose");
// * newDivClose.style.float="left"; newDivClose.style.width="25px";
// * newDivClose.style.height="24px";
// * newDivClose.style.background="url(http://localhost:8080/xcds/images/tang_back2.jpg)";
// * newDivClose.style.color="#808080"; newDivClose.style.marginTop="3px";
// * newDivClose.style.paddingTop="3px";
// * 
// * 
// * document.getElementById("tang_back_display").appendChild(newDiv);
// * newDiv.innerHTML=devicename+" * "+devicenumber;
// * document.getElementById("tang_back_display").appendChild(newDivClose);
// * 
// * }); $("#newDivClose").click(function () {
// * document.getElementById("tang_back_display").removeChild(newDivClose);
// * document.getElementById("tang_back_display").removeChild(newDiv);
// * 
// * });
// * 
// * 
// * 
// * 
// * $(document).ready(function(){ //
// * 
// * $("#DistributionForm").validate({ meta :"validate",//

// "label" rules: { DistributionRepairUserName:"required" }, messages:{
// * DistributionRepairUserName:"" } }); });
// * $("#DistributionRepairUserPlus").click(function () { var
// * divUser=document.createElement("divUser"); divUser.style.float="left";
// * divUser.style.width="50px"; divUser.style.height="24px";
// * divUser.style.background="url(http://localhost:8080/xcds/images/tang_back.jpg)";
// * divUser.style.color="#808080"; divUser.style.paddingLeft="10px";
// * divUser.style.marginLeft="10px"; divUser.style.marginTop="3px";
// * divUser.style.paddingTop="3px";
// * 
// * 
// * var divUserClose=document.createElement("divUserClose");
// * divUserClose.style.float="left"; divUserClose.style.width="25px";
// * divUserClose.style.height="24px";
// * divUserClose.style.background="url(http://localhost:8080/xcds/images/tang_back2.jpg)";
// * divUserClose.style.color="#808080"; divUserClose.style.marginTop="3px";
// * divUserClose.style.paddingTop="3px";
// * 
// * 
// * document.getElementById("tang_back_user_display").appendChild(divUser);
// * divUser.innerHTML=repair;
// * document.getElementById("tang_back_user_display").appendChild(divUserClose);
// * 
// * }); $("#divUserClose").click(function () {
// * document.getElementById("tang_back_user_display").removeChild(divUserClose);
// * document.getElementById("tang_back_user_display").removeChild(divUser);
// * 
// * });
// * 
// */