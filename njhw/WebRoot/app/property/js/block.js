function consumablesInfo(index) {
	var deviceTypeSelect = document.getElementById("deviceTypeSelect");
	var deviceSelect = document.getElementById("deviceSelect");
	var deviceTypeId = deviceTypeSelect.options[deviceTypeSelect.selectedIndex].id;
	var deviceTypeName = deviceTypeSelect.options[deviceTypeSelect.selectedIndex].value;
	var deviceId = deviceSelect.options[deviceSelect.selectedIndex].id;
	var deviceName = deviceSelect.options[deviceSelect.selectedIndex].value;
	var deviceNumber = document.getElementById("deviceNumber").value;
	
	var consumablesInfo = {
			'index': index,
			'deviceTypeId' : deviceTypeId,
			'deviceTypeName' : deviceTypeName,
			'deviceId' : deviceId,
			'deviceName' : deviceName,
			'deviceNumber' : deviceNumber
	};
	
	return consumablesInfo;
	
}
function loadBefore() {
	var layout = document.getElementById("layout");
	var box_complete = document.getElementById("box_complete");
	var back_complete = document.getElementById("back_complete");

	var box_add = document.getElementById("box_add");
	var back_add = document.getElementById("back_add");

	var box_distribution = document.getElementById("box_distribution");

	var back_distribution = document.getElementById("back_distribution");

	var box_evaluate = document.getElementById("box_evaluate");
	var back_evaluate = document.getElementById("back_evaluate");
	var index = 0;
	
	var plusDeviceComplete = document.getElementById("CompletePlusButton");
	
	
	
	
	

	plusDeviceComplete.onclick = function() {
		var deviceTypeSelect = document.getElementById("deviceTypeSelect");
		var deviceSelect = document.getElementById("deviceSelect");
		var deviceTypeId = deviceTypeSelect.options[deviceTypeSelect.selectedIndex].id;
		var deviceTypeName = deviceTypeSelect.options[deviceTypeSelect.selectedIndex].value;
		var deviceId = deviceSelect.options[deviceSelect.selectedIndex].id;
		var deviceName = deviceSelect.options[deviceSelect.selectedIndex].value;
		var deviceNumber = document.getElementById("deviceNumber").value;
		var consumableInfo = consumablesInfo(index);
		index++;
		plusDevice(consumableInfo);
	}

	layout.style.display = "none";

	box_complete.style.display = "none";
	back_complete.style.display = "none";

	box_add.style.display = "none";
	back_add.style.display = "none";

	box_distribution.style.display = "none";
	back_distribution.style.display = "none";

	box_evaluate.style.display = "none";
	back_evaluate.style.display = "none";

}

function loadAfterComplete() {

	var layout = document.getElementById("layout");
	var box_complete = document.getElementById("box_complete");
	var back_complete = document.getElementById("back_complete");

	layout.style.display = "block";
	box_complete.style.display = "block";
	buttonbox_complete.style.display = "block";
	back_complete.style.display = "block";
}

function loadAfterAdd() {

	var layout = document.getElementById("layout");
	var box_add = document.getElementById("box_add");
	var back_add = document.getElementById("back_add");

	layout.style.display = "block";
	box_add.style.display = "block";
	buttonbox_add.style.display = "block";
	back_add.style.display = "block";
}

function loadAfterDistribution() {

	var layout = document.getElementById("layout");
	var box_distribution = document.getElementById("box_distribution");
	var back_distribution = document.getElementById("back_distribution");

	layout.style.display = "block";
	box_distribution.style.display = "block";
	back_distribution.style.display = "block";
}

function loadAfterEvaluate() {

	var layout = document.getElementById("layout");
	var box_evaluate = document.getElementById("box_evaluate");
	var back_evaluate = document.getElementById("back_evaluate");

	layout.style.display = "block";
	box_evaluate.style.display = "block";
	back_evaluate.style.display = "block";

}

function closeAfterComplete() {
	var layout = document.getElementById("layout");
	var box_complete = document.getElementById("box_complete");
	var back_complete = document.getElementById("back_complete");

	layout.style.display = "none";
	box_complete.style.display = "none";
	back_complete.style.display = "none";

}

function closeAfterAdd() {
	var layout = document.getElementById("layout");
	var box_back = document.getElementById("box_back");
	var back_back = document.getElementById("back_back");

	layout.style.display = "none";
	box_add.style.display = "none";
	back_add.style.display = "none";

}

function closeAfterDistribution() {
	var layout = document.getElementById("layout");
	var box_distribution = document.getElementById("box_distribution");
	var back_distribution = document.getElementById("back_distribution");

	layout.style.display = "none";
	box_distribution.style.display = "none";
	back_distribution.style.display = "none";

}

function closeAfterEvaluate() {
	var layout = document.getElementById("layout");
	var box_evaluate = document.getElementById("box_evaluate");
	var back_evaluate = document.getElementById("back_evaluate");

	layout.style.display = "none";
	box_evaluate.style.display = "none";
	back_evaluate.style.display = "none";

}

function plusDevice(consumableInfo) {
	deviceTypeName = consumableInfo.deviceTypeName;
	deviceName = consumableInfo.deviceName;
	deviceNumber = consumableInfo.deviceNumber;
	var hiddenone = document.getElementById("hiddenone").value;
	var hiddentwo = document.getElementById("hiddentwo").value;
	var hiddenthree = document.getElementById("hiddenthree").value;
	var numbertest = /^[0-9]*[1-9][0-9]*$/;

	if (deviceTypeName.indexOf("请选择设备类型") > 0) {
		hiddenone = "false";
	} else {
		hiddenone = "true";
	}
	if (deviceName.indexOf("请选择设备名称") > 0) {
		hiddentwo = "false";
	} else {
		hiddentwo = "true";
	}
	if (deviceNumber.indexOf("请输入设备数量") > 0) {
		hiddenthree = "false";
	} else if (!numbertest.test(deviceNumber)) {
		hiddenthree = "false";
	} else {
		hiddenthree = "true";
	}

	if (hiddenone == "true" && hiddentwo == "true" && hiddenthree == "true") {
		
		draw(consumableInfo);

	} else {
		alert("请填写正确的设备信息！");
	}
}


function draw(consumableInfo) {
	deviceName = consumableInfo.deviceName;
	deviceNumber = consumableInfo.deviceNumber;

	var newDiv = document.createElement("div");
	newDiv.setAttribute("name", "deviceInfoId");
	
	newDiv.setAttribute("consumableInfo", consumableInfo);

	newDiv.style.float = "left";
	newDiv.style.width = "210px";
	newDiv.style.height = "24px";
	newDiv.style.background = "url(http://localhost:8080/xcds/images/tang_back1.jpg)";
	newDiv.style.color = "#808080";
	newDiv.style.paddingLeft = "10px";
	newDiv.style.marginLeft = "10px";
	newDiv.style.marginTop = "3px";
	newDiv.style.paddingTop = "3px";

	var newDivClose = document.createElement("divclose");
	newDivClose.style.float = "left";
	newDivClose.style.width = "25px";
	newDivClose.style.height = "24px";
	newDivClose.style.background = "url(http://localhost:8080/xcds/images/tang_back2.jpg)";
	newDivClose.style.color = "#808080";
	newDivClose.style.marginTop = "3px";
	newDivClose.style.paddingTop = "3px";

	document.getElementById("tang_back_display").appendChild(newDiv);
	newDiv.innerHTML = deviceName + " * " + deviceNumber;
	document.getElementById("tang_back_display").appendChild(newDivClose);
    
	
	newDivClose.onclick = function() {
		document.getElementById("tang_back_display").removeChild(newDivClose);
		document.getElementById("tang_back_display").removeChild(newDiv);
	}
}


function getAllDiv(){
	var displayAll=document.getElementById("device_display");
	var text=document.getElementById("hiddentext").value;
	var str=document.getElementsByName("deviceInfoId").value;
	
	alert(text+str);
	
	text=text+str;
	document.getElementById("hiddentext").value=text;
	alert(text);
	
}



function plusUser() {

	var repair = document.getElementById("DistributionRepairUserName").value;
	var hiddenrepaire = document.getElementById("hiddenrepaire").value;

	if (repair.indexOf("请选择维修人员") > 0) {
		hiddenrepaire = "false";
	} else {
		hiddenrepaire = "true";
	}

	if (hiddenrepaire == "true") {
		var divUser = document.createElement("divUser");
		divUser.style.float = "left";
		divUser.style.width = "50px";
		divUser.style.height = "24px";
		divUser.style.background = "url(http://localhost:8080/xcds/images/tang_back.jpg)";
		divUser.style.color = "#808080";
		divUser.style.paddingLeft = "10px";
		divUser.style.marginLeft = "10px";
		divUser.style.marginTop = "3px";
		divUser.style.paddingTop = "3px";

		var divUserClose = document.createElement("divUserClose");
		divUserClose.style.float = "left";
		divUserClose.style.width = "25px";
		divUserClose.style.height = "24px";
		divUserClose.style.background = "url(http://localhost:8080/xcds/images/tang_back2.jpg)";
		divUserClose.style.color = "#808080";
		divUserClose.style.marginTop = "3px";
		divUserClose.style.paddingTop = "3px";

		document.getElementById("tang_back_user_display").appendChild(divUser);
		divUser.innerHTML = repair;
		document.getElementById("tang_back_user_display").appendChild(
				divUserClose);

	} else {
		alert("请选择维修人员！");
	}

	divUserClose.onclick = function() {
		document.getElementById("tang_back_user_display").removeChild(
				divUserClose);
		document.getElementById("tang_back_user_display").removeChild(divUser);

	}

}

/*
 * 
 * $(document).ready(function(){ //设备添加
 * 
 * $("#CompleteForm").validate({ meta :"validate",// 采用meta
 * String方式进行验证（验证内容与写入class中） errorElement :"alert",// 使用"div"标签标记错误，
 * 默认:"label" rules: { DeviceTypeBox:"required", DeviceTypeNameBox:"required",
 * DeviceTypeNumberBox:{ required:true, digits:true } }, messages:{
 * DeviceTypeBox:"请选择设备类型！", DeviceTypeNameBox:"请选择设备名称！",
 * DeviceTypeNumberBox:"请选择设备数量！" } }); });
 * $("#CompletePlusButton").click(function () { var
 * newDiv=document.createElement("div"); newDiv.style.float="left";
 * newDiv.style.width="210px"; newDiv.style.height="24px";
 * newDiv.style.background="url(http://localhost:8080/xcds/images/tang_back1.jpg)";
 * newDiv.style.color="#808080"; newDiv.style.paddingLeft="10px";
 * newDiv.style.marginLeft="10px"; newDiv.style.marginTop="3px";
 * newDiv.style.paddingTop="3px";
 * 
 * 
 * var newDivClose=document.createElement("divclose");
 * newDivClose.style.float="left"; newDivClose.style.width="25px";
 * newDivClose.style.height="24px";
 * newDivClose.style.background="url(http://localhost:8080/xcds/images/tang_back2.jpg)";
 * newDivClose.style.color="#808080"; newDivClose.style.marginTop="3px";
 * newDivClose.style.paddingTop="3px";
 * 
 * 
 * document.getElementById("tang_back_display").appendChild(newDiv);
 * newDiv.innerHTML=devicename+" * "+devicenumber;
 * document.getElementById("tang_back_display").appendChild(newDivClose);
 * 
 * }); $("#newDivClose").click(function () {
 * document.getElementById("tang_back_display").removeChild(newDivClose);
 * document.getElementById("tang_back_display").removeChild(newDiv);
 * 
 * });
 * 
 * 
 * 
 * 
 * $(document).ready(function(){ //设备添加
 * 
 * $("#DistributionForm").validate({ meta :"validate",// 采用meta
 * String方式进行验证（验证内容与写入class中） errorElement :"alert",// 使用"div"标签标记错误，
 * 默认:"label" rules: { DistributionRepairUserName:"required" }, messages:{
 * DistributionRepairUserName:"请选择维修人员！" } }); });
 * $("#DistributionRepairUserPlus").click(function () { var
 * divUser=document.createElement("divUser"); divUser.style.float="left";
 * divUser.style.width="50px"; divUser.style.height="24px";
 * divUser.style.background="url(http://localhost:8080/xcds/images/tang_back.jpg)";
 * divUser.style.color="#808080"; divUser.style.paddingLeft="10px";
 * divUser.style.marginLeft="10px"; divUser.style.marginTop="3px";
 * divUser.style.paddingTop="3px";
 * 
 * 
 * var divUserClose=document.createElement("divUserClose");
 * divUserClose.style.float="left"; divUserClose.style.width="25px";
 * divUserClose.style.height="24px";
 * divUserClose.style.background="url(http://localhost:8080/xcds/images/tang_back2.jpg)";
 * divUserClose.style.color="#808080"; divUserClose.style.marginTop="3px";
 * divUserClose.style.paddingTop="3px";
 * 
 * 
 * document.getElementById("tang_back_user_display").appendChild(divUser);
 * divUser.innerHTML=repair;
 * document.getElementById("tang_back_user_display").appendChild(divUserClose);
 * 
 * }); $("#divUserClose").click(function () {
 * document.getElementById("tang_back_user_display").removeChild(divUserClose);
 * document.getElementById("tang_back_user_display").removeChild(divUser);
 * 
 * });
 * 
 */