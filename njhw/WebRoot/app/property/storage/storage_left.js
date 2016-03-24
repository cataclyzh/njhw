(function() {
	'use strict';
	/**
	 * initial the page is fully loaded
	 */
	$(function($) {
		$("#btn_addDeviceType").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../pro/toAddDeviceType.act";
			$("#btn_addDeviceType").parent().attr("class","main1_addDeviceType_select");
			$("#btn_viewDeviceType").parent().attr("class","main1_viewDeviceType");
			$("#btn_addDevice").parent().attr("class","main1_addDevice");
			$("#btn_viewDevice").parent().attr("class","main1_viewDevice");
			$("#btn_viewStorage").parent().attr("class","main1_viewStorage");
			$("#btn_inOutStorage").parent().attr("class","main1_inOutStorage");
			
		});
				
		$("#btn_viewDeviceType").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../pro/deviceTypeList.act";
			$("#btn_addDeviceType").parent().attr("class","main1_addDeviceType");
			$("#btn_viewDeviceType").parent().attr("class","main1_viewDeviceType_select");
			$("#btn_addDevice").parent().attr("class","main1_addDevice");
			$("#btn_viewDevice").parent().attr("class","main1_viewDevice");
			$("#btn_viewStorage").parent().attr("class","main1_viewStorage");
			$("#btn_inOutStorage").parent().attr("class","main1_inOutStorage");
		});
				
		$("#btn_addDevice").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../pro/toAddDevice.act";
			$("#btn_addDeviceType").parent().attr("class","main1_addDeviceType");
			$("#btn_viewDeviceType").parent().attr("class","main1_viewDeviceType");
			$("#btn_addDevice").parent().attr("class","main1_addDevice_select");
			$("#btn_viewDevice").parent().attr("class","main1_viewDevice");
			$("#btn_viewStorage").parent().attr("class","main1_viewStorage");
			$("#btn_inOutStorage").parent().attr("class","main1_inOutStorage");
		});
		
		$("#btn_viewDevice").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../pro/deviceList.act";
			$("#btn_addDeviceType").parent().attr("class","main1_addDeviceType");
			$("#btn_viewDeviceType").parent().attr("class","main1_viewDeviceType");
			$("#btn_addDevice").parent().attr("class","main1_addDevice");
			$("#btn_viewDevice").parent().attr("class","main1_viewDevice_select");
			$("#btn_viewStorage").parent().attr("class","main1_viewStorage");
			$("#btn_inOutStorage").parent().attr("class","main1_inOutStorage");
		});
		
		$("#btn_viewStorage").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../pro/storageList.act";
			$("#btn_addDeviceType").parent().attr("class","main1_addDeviceType");
			$("#btn_viewDeviceType").parent().attr("class","main1_viewDeviceType");
			$("#btn_addDevice").parent().attr("class","main1_addDevice");
			$("#btn_viewDevice").parent().attr("class","main1_viewDevice");
			$("#btn_viewStorage").parent().attr("class","main1_viewStorage_select");
			$("#btn_inOutStorage").parent().attr("class","main1_inOutStorage");
		});
		
		$("#btn_inOutStorage").click(function() {
			window.parent.frames["main_frame_right"].location.href = "../../pro/inOutStorageList.act";
			$("#btn_addDeviceType").parent().attr("class","main1_addDeviceType");
			$("#btn_viewDeviceType").parent().attr("class","main1_viewDeviceType");
			$("#btn_addDevice").parent().attr("class","main1_addDevice");
			$("#btn_viewDevice").parent().attr("class","main1_viewDevice");
			$("#btn_viewStorage").parent().attr("class","main1_viewStorage");
			$("#btn_inOutStorage").parent().attr("class","main1_inOutStorage_select");
		});
	});
}()); // $(function()) 结束
