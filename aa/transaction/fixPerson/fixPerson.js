//根据查询条件获取有效值并绘制Highcharts
function getChart(fixUserMaxaccept) {
	var beginTime = $("#begin_time_param").datebox('getValue');
	var endTime = $("#end_time_param").datebox('getValue');
	
	var userId = fixUserMaxaccept;
	var managerId = '';
	
	var a = new Date(beginTime);
	var b = new Date(endTime);
	
	var orderChannel = $("#order_channel").combobox("getValue");
	if(a.getTime() > b.getTime()){
		alert("结束时间不能早开始时间！！");
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
	var dateArr = new Array();
	dateArr = getDayAll(beginTime, endTime);
	
	var datay = [];//y轴：数量
    var dataydwh = [];//待维护工单数
	var dataywhf = [];//待客服回访工单数量
	var datayywc = [];//已完成工单数
	var datayno = [];//维护工单_不满意数量
	
	getTotalCount(beginTime, endTime, datax, datay, dataydwh, dataywhf, datayywc, dateArr, userId, managerId, orderChannel);
	getFixType(beginTime, endTime, userId, managerId, orderChannel);
	getFixDgree(beginTime, endTime, userId, managerId, orderChannel);
	getorderDegreeUnsatisfied(beginTime, endTime, userId, datax, datayno, dateArr, managerId, orderChannel);
	
}

//查询受理工单数量
function getTotalCount(beginTime, endTime, datax, datay, dataydwh, dataywhf, datayywc, dateArr, userId, managerId, orderChannel){
	var test0 = [];
	$.ajax({
		url : webpath + "/fixPerson/getOrderNumByTime.action?beginTime="+beginTime+"&endTime="+endTime+"&userId="+userId+"&managerId="+managerId+"&orderChannel="+orderChannel,
		cache: false,//不保存缓存
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
			getDataAccess(beginTime, endTime, datax, datay, dataydwh, dataywhf, datayywc, dateArr, userId, managerId, orderChannel);
		}
	});
}


//查询已完结工单数量
function getDataAccess(beginTime, endTime, datax, datay, dataydwh, dataywhf, datayywc, dateArr, userId, managerId, orderChannel){
	var test1 = [];
	$.ajax({
		url : webpath + "/fixPerson/getAccessOrderNumByTime.action?beginTime="+beginTime+"&endTime="+endTime+"&userId="+userId+"&managerId="+managerId+"&orderChannel="+orderChannel,
		cache: false,//不保存缓存
		type : "GET",
		dataType : "json",
		success : function(data) {
			for(var a=0; a<dateArr.length; a++){
				$.each(data, function(i, n) {
					test1[i] = n["MONTH"];
					if(datax[a]==test1[i]){
						datayywc[a]=n["NUM"];
					}
				});
			}
			getKFreview(beginTime, endTime, datax, datay, dataydwh, dataywhf, datayywc, dateArr, userId, managerId, orderChannel);
		}
	});
}

//查询待客服回访工单数量
function getKFreview(beginTime, endTime, datax, datay, dataydwh, dataywhf, datayywc, dateArr, userId, managerId, orderChannel){
	var test3 = [];
	$.ajax({
		url : webpath + "/fixPerson/getKFreview.action?beginTime="+beginTime+"&endTime="+endTime+"&userId="+userId+"&managerId="+managerId+"&orderChannel="+orderChannel,
		cache: false,//不保存缓存
		type : "GET",
		dataType : "json",
		success : function(data) {
			for(var a=0; a<dateArr.length; a++){
				$.each(data, function(i, n) {
					test3[i] = n["MONTH"];
					if(datax[a]==test3[i]){
						dataywhf[a]=n["NUM"];
					}
				});
			}
			getDataUnAccess(beginTime, endTime, datax, datay, dataydwh, dataywhf, datayywc, dateArr, userId, managerId, orderChannel);
		}
	});
}

//查询待维护工单数量
function getDataUnAccess(beginTime, endTime, datax, datay, dataydwh, dataywhf, datayywc, dateArr, userId, managerId, orderChannel){
	var test2 = [];
	$.ajax({
		url : webpath + "/fixPerson/getUnAccessOrderNumByTime.action?beginTime="+beginTime+"&endTime="+endTime+"&userId="+userId+"&managerId="+managerId+"&orderChannel="+orderChannel,
		cache: false,//不保存缓存
		type : "POST",
		dataType : "json",
		success : function(data) {
			for(var a=0; a<dateArr.length; a++){
				$.each(data, function(i, n) {
					test2[i] = n["MONTH"];
					if(datax[a]==test2[i]){
						dataydwh[a]=n["NUM"];
					}
				});
			}
			
			for(var a=0; a<datax.length; a++){
				if(datay[a]==null || datay[a]==''){
					datay[a]=0;
				}
				if(dataydwh[a]==null || dataydwh[a]==''){
					dataydwh[a]=0;
				}
				if(dataywhf[a]==null || dataywhf[a]==''){
					dataywhf[a]=0;
				}
				if(datayywc[a]==null || datayywc[a]==''){
					datayywc[a]=0;
				}
			}
			//绘制HighCharts图表
			start(datax, datay, dataydwh, dataywhf, datayywc);
		}
	});
}

//获取工单类型并绘制饼状图
function getFixType(beginTime, endTime, userId, managerId, orderChannel){
	var brNum = 0;
	var lsNum = 0;
	$.ajax({
		url : webpath + "/fixPerson/getFixType.action?beginTime="+beginTime+"&endTime="+endTime+"&userId="+userId+"&managerId="+managerId+"&orderChannel="+orderChannel,
		cache: false,//不保存缓存
		type : "GET",
		dataType : "json",
		success : function(data) {
			brNum = data[0];
			lsNum = data[1];
			fixType(brNum, lsNum);
		}
	});
}

//获取工单满意度并绘制饼状图
function getFixDgree(beginTime, endTime, userId, managerId, orderChannel){
	var goodNum = 0;
	var okNum = 0;
	var noNum = 0;
	$.ajax({
		url : webpath + "/fixPerson/getFixDgree.action?beginTime="+beginTime+"&endTime="+endTime+"&userId="+userId+"&managerId="+managerId+"&orderChannel="+orderChannel,
		cache: false,//不保存缓存
		type : "GET",
		dataType : "json",
		success : function(data) {
			goodNum = data[0];
			okNum = data[1];
			noNum = data[2];
			fixDgree(goodNum, okNum, noNum);
		}
	});
}

function getorderDegreeUnsatisfied(beginTime, endTime, userId, datax, datayno, dateArr, managerId, orderChannel){
	var test3 = [];
	$.ajax({
		url : webpath + "/fixPerson/getorderDegreeUnsatisfied.action?beginTime="+beginTime+"&endTime="+endTime+"&userId="+userId+"&managerId="+managerId+"&orderChannel="+orderChannel,
		cache: false,//不保存缓存
		type : "GET",
		dataType : "json",
		success : function(data) {
			
			for(var a=0; a<dateArr.length; a++){
				$.each(data, function(i, n) {
					test3[i] = n["MONTH"];
					if(datax[a]==test3[i]){
						datayno[a]=n["NUM"];
					}
				});
			}
			
			for(var a=0; a<datax.length; a++){
				if(datayno[a]==null || datayno[a]==''){
					datayno[a]=0;
				}
			}
			orderDegreeUnsatisfied(datax, datayno);
		}
	});
}

//Highcharts线性图表
function start(datax, datay, dataydwh, dataywhf, datayywc) {
	$('#line').highcharts({
		chart : {
			renderTo: 'container',
			type : 'column'
		},
		credits : {
			enabled : false
		// 去除水印
		},
		title : {
			align : 'left',
			style : {"color": "#333333", "fontSize": "15px"},
			text : '工单处理情况：'
		},
		xAxis : {
			categories : datax
		},
		yAxis : {
			title : {
				text : '数量'
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
			name : '受理工单总数量',
			data : datay
		},{
			name : '待维护工单数量',
			color: 'red',
			data : dataydwh
		},{
			name : '待客服回访工单数量',
			color: 'rgba(165,170,217,1)',
			data : dataywhf
		},{
			name : '已完结工单数量',
			color: '#90ed7d',
			data : datayywc
		}]
	});
}

//维护工单不满意数量
function orderDegreeUnsatisfied(datax, datayno) {
	$('#orderDegreeUnsatisfied').highcharts({
		chart : {
			renderTo: 'container',
			type : 'line'
		},
		credits : {
			enabled : false
		// 去除水印
		},
		title : {
			align : 'left',
			style : {"color": "#333333", "fontSize": "15px"},
			text : '已完结工单_不满意情况：'
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
			name : '不满意工单数量',
			color: 'red',
			data : datayno
		}]
	});
}

function fixType(brNum, lsNum){
	$('#fixType').highcharts({
		title : {
			style : {"color": "#333333", "fontSize": "15px"},
			text : '维护类型占比'
		},
		credits : {
			enabled : false// 去除水印
		},
		tooltip : {
			headerFormat : '{series.name}<br>',
			pointFormat : '{point.name}: <b>{point.percentage:.1f}%</b>'
		},
		plotOptions : {
			pie : {
				allowPointSelect : true, // 可以被选择
				cursor : 'pointer', // 鼠标样式
				dataLabels : {
					enabled : true,
					format : '<b>{point.name}</b>: {point.percentage:.1f} %',
					style : {
						color : (Highcharts.theme && Highcharts.theme.contrastTextColor)
								|| 'black'
					}
				}
			}
		},
		colors : [ '#058DC7', '#FF9655', 'red' ],
		series : [ {
			type : 'pie',
			name : '维护类型占比',
			data : [ 
			     [ '暖气不热', brNum ], 
			     [ '暖气漏水', lsNum ]
			]
		} ]
	});
}

function fixDgree(goodNum, okNum, noNum){
	$('#fixDgreePie').highcharts({
		title : {
			style : {"color": "#333333", "fontSize": "15px"},
			text : '已完结工单维护满意度占比'
		},
		credits : {
			enabled : false// 去除水印
		},
		tooltip : {
			headerFormat : '{series.name}<br>',
			pointFormat : '{point.name}: <b>{point.percentage:.1f}%</b>'
		},
		plotOptions : {
			pie : {
				allowPointSelect : true, // 可以被选择
				cursor : 'pointer', // 鼠标样式
				dataLabels : {
					enabled : true,
					format : '<b>{point.name}</b>: {point.percentage:.1f} %',
					style : {
						color : (Highcharts.theme && Highcharts.theme.contrastTextColor)
								|| 'black'
					}
				}
			}
		},
		colors : [ '#058DC7', '#FF9655', 'red' ],
		series : [ {
			type : 'pie',
			name : '维护满意度',
			data : [ [ '满意', goodNum ], [ '合格', okNum ], {
				name : '不满意',
				y : noNum,
				sliced : true, // 默认突出
				selected : true// 默认选中
			}, ]
		} ]
	});
}