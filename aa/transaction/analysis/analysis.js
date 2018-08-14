//根据查询条件获取有效值并绘制Highcharts
function getChart() {
	var beginTime = $("#begin_time_param").datebox('getValue');
	var endTime = $("#end_time_param").datebox('getValue');
	
	var a = new Date(beginTime);
	var b = new Date(endTime);
	if(a.getTime() > b.getTime()){
		alert("结束时间不能早开始时间！");
	}
	
	if((beginTime==null||beginTime=='') && (endTime==null || endTime=='')){
		alert("请填写查询时间！");
		getTime();
		var now = new Date();
		//格式化日，如果小于9，前面补0
		var day = ("0" + now.getDate()).slice(-2);
		//格式化月，如果小于9，前面补0
		var month = ("0" + (now.getMonth() + 1)).slice(-2);
		//拼装完整日期格式
		beginTime = now.getFullYear()+"-"+(month)+"-"+(day) ;
		endTime = getBeforeDate(7);
	}else if((endTime==null || endTime=='')){
		endTime = new Date().format().toString();
		$("#end_time_param").datebox('setValue',endTime);
	}else if((beginTime==null || beginTime=='')){
		var lastMonth = getAfterDate(endTime, 30);
		$("#begin_time_param").datebox('setValue',lastMonth);
		beginTime = lastMonth;
	}
	
	//总工单数
	var datax = [];//x轴：时间  yyyy-mm-dd
	var test0 = [];
	var dateArr = new Array();
	dateArr = getDayAll(beginTime, endTime);
	
	var datay = [];//y轴：数量
	//已完成工单数
	var datady = [];
	//待完成工单数
	var datawy = [];
	$.ajax({
		url : webpath + "/order/getOrderNumByTime.action?beginTime="+beginTime+"&endTime="+endTime,
		type : "GET",
		dataType : "json",
		success : function(data) {
			for(var a=0; a<dateArr.length; a++){
				datax.push(dateArr[a]);
				$.each(data, function(i, n) {
					test0[i] = n["MONTH"];
					if(datax[a]==test0[i]){
						datay[a]=n["NUM"];
					}
				});
			}
			//依次调用方法，传递获取所有需要数据
			getDataAccess(beginTime, endTime, datax, datay, datady, datawy, dateArr);
		}
	});
}

//查询已完成工单数量
function getDataAccess(beginTime, endTime, datax, datay, datady, datawy, dateArr){
	var test1 = [];
	$.ajax({
		url : webpath + "/order/getAccessOrderNumByTime.action?beginTime="+beginTime+"&endTime="+endTime,
		cache: false,//不保存缓存
		type : "GET",
		dataType : "json",
		success : function(data) {
			
			for(var a=0; a<dateArr.length; a++){
				$.each(data, function(i, n) {
					test1[i] = n["MONTH"];
					if(datax[a]==test1[i]){
						datady[a]=n["NUM"];
					}
				});
			}
			getDataUnAccess(beginTime, endTime, datax, datay, datady, datawy, dateArr);
		}
	});
}

//查询待完成工单数量
function getDataUnAccess(beginTime, endTime, datax, datay, datady, datawy, dateArr){
	var test2 = [];
	$.ajax({
		url : webpath + "/order/getUnAccessOrderNumByTime.action?beginTime="+beginTime+"&endTime="+endTime,
		cache: false,//不保存缓存
		type : "GET",
		dataType : "json",
		success : function(data) {
			for(var a=0; a<dateArr.length; a++){
				$.each(data, function(i, n) {
					test2[i] = n["MONTH"];
					if(datax[a]==test2[i]){
						datawy[a]=n["NUM"];
					}
				});
			}
			
			for(var a=0; a<datax.length; a++){
				if(datay[a]==null || datay[a]==''){
					datay[a]=0;
				}
				if(datady[a]==null || datady[a]==''){
					datady[a]=0;
				}
				if(datawy[a]==null || datawy[a]==''){
					datawy[a]=0;
				}
			}
			//绘制HighCharts图表
			start(datax,datay,datady,datawy);
		}
	});
}

//Highcharts线性图表
function start(datax,datay,datady,datawy) {
	$('#line').highcharts({
		chart : {
			renderTo: 'container',
			type : 'line'
		},
		credits : {
			enabled : false
		// 去除水印
		},
		title : {
			text : '年度工单状态一览表'
		},
		xAxis : {
			categories : datax
		},
		yAxis : {
			title : {
				text : '工单数量(单)'
			}
		},
		//设置滚动条    
	    scrollbar: {
	        enabled: true
	    },
		plotOptions : {
			line : {
				dataLabels : {
					// 开启数据标签
					enabled : true
				},
				// 关闭鼠标跟踪，对应的提示框、点击事件会失效
				enableMouseTracking : false
			}
		},
		series : [ {
			name : '工单总数',
			data : datay
		},{
			name : '已完成工单数量',
			color: '#90ed7d',
			data : datady
		},{
			name : '待完成工单数量',
			color: 'red',
			data : datawy
		}]
	});
}
