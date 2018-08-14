/*


//-------------------------------------------------------------------------------------------------------
//※※ 注释：客服人员与客服管理员暂时公用一个js(kfManager.js),在kfManager.js中增加了判断条件，以便区分客服与客服管理员
//-------------------------------------------------------------------------------------------------------



//根据查询条件获取有效值并绘制Highcharts
function getChart(userId) {
	var beginTime = $("#begin_time_param").datebox('getValue');
	var endTime = $("#end_time_param").datebox('getValue');
	
	var a = new Date(beginTime);
	var b = new Date(endTime);
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
		endTime = getBeforeDate(30);
	}else if((endTime==null || endTime=='')){
		endTime = new Date().format().toString();
		$("#end_time_param").datebox('setValue',endTime);
	}else if((beginTime==null || beginTime=='')){
		var lastMonth = getAfterDate(endTime, 30);
		$("#begin_time_param").datebox('setValue',lastMonth);
		beginTime = lastMonth;
	}
	
	var datax = []; //x轴：时间 
	var datay = []; //当前月份通话数量
	var datawy = [];//当前月份通话时间
	var dateArr = new Array();
	dateArr = getDayAll(beginTime, endTime);
	
	//调取绘制图表方法
	getOrderCount(beginTime, endTime, datax, datay, dateArr, userId);
	getPhoneTime(beginTime, endTime, datax, datawy, dateArr, userId);
	getPhoneDegree(beginTime, endTime, userId);
	gerConverStatus(beginTime, endTime, userId);
}

//获取数据并绘制通话数量图表
function getOrderCount(beginTime, endTime, datax, datay, dateArr, userId){
	var test0 = [];
	$.ajax({
		url : webpath + "/kfManager/getOrderCount.action?beginTime="+beginTime+"&endTime="+endTime+"&userId="+userId,
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
			for(var a=0; a<datax.length; a++){
				if(datay[a]==null || datay[a]==''){
					datay[a]=0;
				}
			}
			//绘制HighCharts图表
			orderCount(datax,datay);
		}
	});
}

//获取数据并绘制通平均话时间图表
function getPhoneTime(beginTime, endTime, datax, datawy, dateArr, userId){
	var test1 = [];
	$.ajax({
		url : webpath + "/kfManager/getPhoneTime.action?beginTime="+beginTime+"&endTime="+endTime+"&userId="+userId,
		type : "GET",
		dataType : "json",
		success : function(data) {
			for(var a=0; a<dateArr.length; a++){
				$.each(data, function(i, n) {
					test1[i] = n["MONTH"];
					if(datax[a]==test1[i]){
						alert(n["TIME"]);
						datawy[a]=n["TIME"];
					}
				});
			}
			for(var a=0; a<datax.length; a++){
				if(datawy[a]==null || datawy[a]==''){
					datawy[a]=0;
				}
			}
			phoneTime(datax,datawy);
		}
	});
}

function getPhoneDegree(beginTime, endTime, userId){
	var goodNum = 0;
	var okNum = 0;
	var noNum = 0;
	$.ajax({
		url : webpath + "/kfManager/getPhoneDegree.action?beginTime="+beginTime+"&endTime="+endTime+"&userId="+userId,
		type : "GET",
		dataType : "json",
		success : function(data) {
			goodNum = data[0];
			okNum = data[1];
			noNum = data[2];
			
			phoneDegree(goodNum, okNum, noNum);
		}
	});
}

function gerConverStatus(beginTime, endTime, userId){
	var access = 0;
	var unAccess = 0;
	
	$.ajax({
		url : webpath + "/kfManager/getConverStatus.action?beginTime="+beginTime+"&endTime="+endTime+"&userId="+userId,
		type : "GET",
		dataType : "json",
		success : function(data) {
			access = data[0];
			unAccess = data[1];
			
			converStatus(access, unAccess);
		}
	});
}

//Highcharts线性图表
function orderCount(datax,datay) {
	$('#phoneCount').highcharts({
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
			text : '工单数量：'
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
			name : '工单数量',
			data : datay
		}]
	});
}

//Highcharts线性图表
function phoneTime(datax, datawy) {
	$('#phoneTime').highcharts({
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
			text : '通话时长：'
		},
		xAxis : {
			categories : datax
		},
		yAxis : {
			title : {
				text : '时间(min)'
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
			name : '通话时长',
			color: '#90ed7d',
			data : datawy
		}]
	});
}

function phoneDegree(goodNum, okNum, noNum){
	$('#phoneDegree').highcharts({
		title : {
			text : '客服满意度占比'
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
			name : '客服回访满意度占比',
			data : [ 
			    ['满意', goodNum ],
			    [ '合格', okNum ], 
			    {
					name : '不满意',
					y : noNum,
					sliced : true, // 默认突出
					selected : true// 默认选中
			}, ]
		} ]
	});
}

function converStatus(access, unAccess){
	$('#orderStatus').highcharts({
		title : {
			text : '通话状态占比'
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
			name : '通话状态',
			data : [ 
			   [ '正常接听', access ],
			   {
					name : '未接听',
					y : unAccess,
					sliced : true, // 默认突出
					selected : true// 默认选中
			}, ]
		} ]
	});
}
*/