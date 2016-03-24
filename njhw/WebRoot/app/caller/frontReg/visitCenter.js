window.onload = inits;
function inits()
{
	document.getElementById("left_main").style.height = document.getElementById("right_main").offsetHeight - 2 + "px";
}
// 显示与隐藏
function show_hide()
{
	document.getElementById("show_hide2").style.display = "block";
	document.getElementById("show_hide").style.display = "none";
	document.getElementById("left_main").style.display = "none";
	document.getElementById("right_main").style.left = "0";
	document.getElementById("right_main").style.width = "100%";
}
// 显示与隐藏
function show_hide2()
{
	document.getElementById("show_hide").style.display = "block";
	document.getElementById("show_hide2").style.display = "none";
	document.getElementById("left_main").style.display = "block";
	document.getElementById("right_main").style.left = "55%";
	document.getElementById("right_main").style.width = "45%";
}