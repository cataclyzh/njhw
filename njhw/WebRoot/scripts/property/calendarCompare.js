function calendarCompare(startTimeStr, endTimeStr) {
	var startTimeArray = startTimeStr.split("-");
	var starTime = new Date(startTimeArray[0], startTimeArray[1],
			startTimeArray[2]);
	var starTimeStamp = starTime.getTime();

	var endTimeArray = endTimeStr.split("-");
	var endTime = new Date(endTimeArray[0], endTimeArray[1], endTimeArray[2]);
	var endTimeStamp = endTime.getTime();

	if (starTimeStamp >= endTimeStamp) {

		alert('结束时间不能小于开始时间，请重新选择！');

		return false;
	} else
		return true;
}