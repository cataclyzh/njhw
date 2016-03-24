<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="/common/include/taglibs.jsp"%>
<%@ include file="/common/include/meta.jsp"%>
	<script src="${ctx}/scripts/ca/highcharts.js"
			type="text/javascript"></script>	
		<script src="${ctx}/scripts/ca/exporting.js"
			type="text/javascript"></script>	
				
						<link type="text/css" rel="stylesheet"
			href="${ctx}/app/portal/visitcenter/css/vmCount.css" />
		<script type="text/javascript">

		$(function () {
		
			/*刷新访客统计信息*/
	        $('#cont').highcharts({
	            chart: {
	            	backgroundColor:null,
	                plotBackgroundColor: null,
	                plotBorderWidth: null,
	                plotShadow: true,
	                width:180,
	                height:180,
	                margin:[5,0,0,0]
	            },
				credits:{
					enabled: false
				},
				exporting:{
					enabled: false
				},
				title: {  
                	text: '',  
                	verticalAlign:'bottom',  
                	y:-60  
            	},  
	           tooltip: {//鼠标移动到每个饼图显示的内容  
	           	enable:false,
                pointFormat: null,  
                percentageDecimals: 1
               },  
	            legend: {
	            layout:'vertical',
	            layout:true,
	            enabled:true
	            },
	            plotOptions: {  
	                pie: {  
	                    size:'40%',  
	                    borderWidth: 0,  
	                    allowPointSelect: true,  
	                    cursor: 'pointer',  
	                    dataLabels: {  
	                    enabled: true,  
	                    color: '#000',                        
	                    distance: 10,//通过设置这个属性，将每个小饼图的显示名称和每个饼图重叠  
	                    style: {                              
	                        fontSize: '10px',  
	                        lineHeight: '14px'  
	                    },  
	                    formatter: function(index) {      
	                            //return  '<span style="color:#00008B;font-weight:bold">' + this.point.name + '</span>';  
	                            return this.point.name+":"+this.percentage+"%";
	                    }  
	                  },  
	                 padding:5 
	                }  
            	}, 
            	
	            series: [{
	                type: 'pie',
	                name: 'Browser share',
	                data: [  
                    {name:'内部',color:'#3DA9FF',y:${insider}},  
                    {name:'外部',color:'#008FE0',y:${outsider}},
                    {name:'空',color:'#008FE0',y:${kong}}
                	]  
	            }]
	        });
	    });
		</script>	
			
<div class="right_main_bottom" id="right_bottom">

	<div class="right_main_bottom_left" id="cont"></div>
	<div class="right_main_bottom_left2">
		<span>${vmList.expiredCount}</span>

	</div>
	<div class="right_main_bottom_left3">
		<div class="right_main_bottom_left3_span">
			<span class="bottom_span1" id="vm">${vmList.vmCount}</span>
			<span class="bottom_span2">${vmList.vmVisiting}</span>
			<span class="bottom_span2">${vmList.vmLeave}</span>
			
		</div>
		<div class="right_main_bottom_left4">
			<div class="right_main_bottom_left4_span">
				<span id="users">${vmList.userCount}</span>
				<span>${vmList.userVisiting}</span>
				<span>${vmList.userLeave}</span>
			</div>
		</div>
	</div>
</div>

