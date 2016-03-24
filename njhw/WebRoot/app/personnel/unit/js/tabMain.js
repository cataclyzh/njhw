function btn_hover(dom, num, num1)
{
	if(num1 == 0)
		dom.style.backgroundPosition = "-250px -" + num*100 + "px";
	else
        dom.style.backgroundPosition = "0px -" + num*100 + "px";
}