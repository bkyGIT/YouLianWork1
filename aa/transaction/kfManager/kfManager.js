//根据查询条件获取有效值并绘制Highcharts
function getChart(kfMaxaccept, kfManagerMaxaccept) {
	var beginTime = $("#begin_time_param").datebox('getValue');
	var endTime = $("#end_time_param").datebox('getValue');
	var orderChannel = $("#order_channel").combobox("getValue");
	//oprId和oprName为客服管理员页面获取，客服登录无法获取这两个值，所以增加判断条件，否则页面F12报错
	if(kfMaxaccept == null || kfMaxaccept == ''){
		var oprMaxaccept = $("#opr_maxaccept").textbox('getValue');
	}
	
	//若kfId为'',证明是客服管理员登录,客服管理员可以根据客服ID查询某位(oprId)客服情况，若oprId为''，则查询所有
	//若kfId不为'',则证明是客服登录,客服根据个人ID,只能查询与自己相关信息
	var managerId = kfManagerMaxaccept;
	var userId = '';
	if(kfMaxaccept == null || kfMaxaccept == ''){
		userId = oprMaxaccept;
	}else{
		userId = kfMaxaccept;
	}
	
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
	
	var dataywpd = [];//客服未派单数量
	var datayqxpd = [];//客服取消派单
	var dataydwx = [];//待维修工单数量
	var dataywhf = [];//客服未回访
	var datayywc = [];//已完成工单数量
	
	var dateArr = new Array();
	dateArr = getDayAll(beginTime, endTime);
	
	//调取绘制图表方法
	getOrderCount(beginTime, endTime, userId, managerId , datax, datay, dataywpd, datayqxpd, dataydwx, dataywhf, datayywc, dateArr, orderChannel);
	getPhoneTime(beginTime, endTime, datax, datawy, dateArr, userId, managerId);
	//客服满意度占比饼状图，暂不需要
	//getPhoneDegree(beginTime, endTime, userId, managerId);  
	getOrderType(beginTime, endTime, userId, managerId, orderChannel);
	gerConverStatus(beginTime, endTime, userId, managerId);
}

//获取数据并绘制通话数量图表
function getOrderCount(beginTime, endTime, userId, managerId , datax, datay, dataywpd, datayqxpd, dataydwx, dataywhf, datayywc, dateArr, orderChannel){
	var test0 = [];
	$.ajax({
		url : webpath + "/kfManager/getOrderCount.action?beginTime="+beginTime+"&endTime="+endTime+"&userId="+userId+"&managerId="+managerId+"&orderChannel="+orderChannel,
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
			getWaitSend(beginTime, endTime, userId, managerId , datax, datay, dataywpd, datayqxpd, dataydwx, dataywhf, datayywc, dateArr, orderChannel);
		}
	});
}

//客服未派单
function getWaitSend(beginTime, endTime, userId, managerId , datax, datay, dataywpd, datayqxpd, dataydwx, dataywhf, datayywc, dateArr, orderChannel){
	var test1 = [];
	$.ajax({
		url : webpath + "/kfManager/getWaitSend.action?beginTime="+beginTime+"&endTime="+endTime+"&userId="+userId+"&managerId="+managerId+"&orderChannel="+orderChannel,
		cache: false,//不保存缓存
		type : "GET",
		dataType : "json",
		success : function(data) {
			for(var a=0; a<dateArr.length; a++){
				$.each(data, function(i, n) {
					test1[i] = n["MONTH"];
					if(datax[a]==test1[i]){
						dataywpd[a]=n["NUM"];
					}
				});
			}
			
			getCancelOrder(beginTime, endTime, userId, managerId , datax, datay, dataywpd, datayqxpd, dataydwx, dataywhf, datayywc, dateArr, orderChannel);
		}
	});
}

//客服取消派单
function getCancelOrder(beginTime, endTime, userId, managerId , datax, datay, dataywpd, datayqxpd, dataydwx, dataywhf, datayywc, dateArr, orderChannel){
	var test2 = [];
	$.ajax({
		url : webpath + "/kfManager/getCancelOrder.action?beginTime="+beginTime+"&endTime="+endTime+"&userId="+userId+"&managerId="+managerId+"&orderChannel="+orderChannel,
		cache: false,//不保存缓存
		type : "GET",
		dataType : "json",
		success : function(data) {
			for(var a=0; a<dateArr.length; a++){
				$.each(data, function(i, n) {
					test2[i] = n["MONTH"];
					if(datax[a]==test2[i]){
						datayqxpd[a]=n["NUM"];
					}
				});
			}
			
			getWaitFix(beginTime, endTime, userId, managerId , datax, datay, dataywpd, datayqxpd, dataydwx, dataywhf, datayywc, dateArr, orderChannel);
		}
	});
}

//待维护工单
function getWaitFix(beginTime, endTime, userId, managerId , datax, datay, dataywpd, datayqxpd, dataydwx, dataywhf, datayywc, dateArr, orderChannel){
	var test3 = [];
	$.ajax({
		url : webpath + "/kfManager/getWaitFix.action?beginTime="+beginTime+"&endTime="+endTime+"&userId="+userId+"&managerId="+managerId+"&orderChannel="+orderChannel,
		cache: false,//不保存缓存
		type : "GET",
		dataType : "json",
		success : function(data) {
			for(var a=0; a<dateArr.length; a++){
				$.each(data, function(i, n) {
					test3[i] = n["MONTH"];
					if(datax[a]==test3[i]){
						dataydwx[a]=n["NUM"];
					}
				});
			}
			
			getWaitReview(beginTime, endTime, userId, managerId , datax, datay, dataywpd, datayqxpd, dataydwx, dataywhf, datayywc, dateArr, orderChannel);
		}
	});
}

//客服未回访
function getWaitReview(beginTime, endTime, userId, managerId , datax, datay, dataywpd, datayqxpd, dataydwx, dataywhf, datayywc, dateArr, orderChannel){
	var test5 = [];
	$.ajax({
		url : webpath + "/kfManager/getWaitReview.action?beginTime="+beginTime+"&endTime="+endTime+"&userId="+userId+"&managerId="+managerId+"&orderChannel="+orderChannel,
		cache: false,//不保存缓存
		type : "GET",
		dataType : "json",
		success : function(data) {
			for(var a=0; a<dateArr.length; a++){
				$.each(data, function(i, n) {
					test5[i] = n["MONTH"];
					if(datax[a]==test5[i]){
						dataywhf[a]=n["NUM"];
					}
				});
			}
			
			getDataAccess(beginTime, endTime, userId, managerId , datax, datay, dataywpd, datayqxpd, dataydwx, dataywhf, datayywc, dateArr, orderChannel);
		}
	});
}

//查询已完成工单数量
function getDataAccess(beginTime, endTime, userId, managerId , datax, datay, dataywpd, datayqxpd, dataydwx, dataywhf, datayywc, dateArr, orderChannel){
	var test4 = [];
	$.ajax({
		url : webpath + "/kfManager/getAccessOrderNumByTime.action?beginTime="+beginTime+"&endTime="+endTime+"&userId="+userId+"&managerId="+managerId+"&orderChannel="+orderChannel,
		cache: false,//不保存缓存
		type : "GET",
		dataType : "json",
		success : function(data) {
			
			for(var a=0; a<dateArr.length; a++){
				$.each(data, function(i, n) {
					test4[i] = n["MONTH"];
					if(datax[a]==test4[i]){
						datayywc[a]=n["NUM"];
					}
				});
			}
			
			for(var a=0; a<datax.length; a++){
				if(datay[a]==null || datay[a]==''){
					datay[a]=0;
				}
				if(dataywpd[a]==null || dataywpd[a]==''){
					dataywpd[a]=0;
				}
				if(datayqxpd[a]==null || datayqxpd[a]==''){
					datayqxpd[a]=0;
				}
				if(dataydwx[a]==null || dataydwx[a]==''){
					dataydwx[a]=0;
				}
				if(datayywc[a]==null || datayywc[a]==''){
					datayywc[a]=0;
				}
				if(dataywhf[a]==null || dataywhf[a]==''){
					dataywhf[a]=0;
				}
			}
			
			orderCount(datax, datay, dataywpd, datayqxpd, dataydwx, dataywhf, datayywc);
		}
	});
}

//获取数据并绘制通平均话时间图表
function getPhoneTime(beginTime, endTime, datax, datawy, dateArr, userId, managerId){
	var test1 = [];
	var b = 0.0;
	$.ajax({
		url : webpath + "/kfManager/getPhoneTime.action?beginTime="+beginTime+"&endTime="+endTime+"&userId="+userId+"&managerId="+managerId,
		cache: false,//不保存缓存
		type : "GET",
		dataType : "json",
		success : function(data) {
			for(var a=0; a<dateArr.length; a++){
				$.each(data, function(i, n) {
					test1[i] = n["MONTH"];
					if(datax[a]==test1[i]){
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

/*function getPhoneDegree(beginTime, endTime, userId, managerId){
	var goodNum = 0;
	var okNum = 0;
	var noNum = 0;
	$.ajax({
		url : webpath + "/kfManager/getPhoneDegree.action?beginTime="+beginTime+"&endTime="+endTime+"&userId="+userId+"&managerId="+managerId,
		cache: false,//不保存缓存
		type : "GET",
		dataType : "json",
		success : function(data) {
			goodNum = data[0];
			okNum = data[1];
			noNum = data[2];
			
			phoneDegree(goodNum, okNum, noNum);
		}
	});
}*/

//获取工单类型
//用于替换客服满意度饼状图，暂不需要展示客服满意度功能
function getOrderType(beginTime, endTime, userId, managerId, orderChannel){
	var zxNum = 0;
	var whNum = 0;
	var jyNum = 0;
	var tsNum = 0;
	$.ajax({
		url : webpath + "/kfManager/getOrderType.action?beginTime="+beginTime+"&endTime="+endTime+"&userId="+userId+"&managerId="+managerId+"&orderChannel="+orderChannel,
		cache: false,//不保存缓存
		type : "GET",
		dataType : "json",
		success : function(data) {
			zxNum = data[0];
			whNum = data[1];
			jyNum = data[2];
			tsNum = data[3];
			
			orderType(zxNum, whNum, jyNum,tsNum);
		}
	});
}

function gerConverStatus(beginTime, endTime, userId, managerId){
	var access = 0;
	var unAccess = 0;
	
	$.ajax({
		url : webpath + "/kfManager/getConverStatus.action?beginTime="+beginTime+"&endTime="+endTime+"&userId="+userId+"&managerId="+managerId,
		cache: false,//不保存缓存
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
//工单数量
function orderCount(datax, datay, dataywpd, datayqxpd, dataydwx, dataywhf, datayywc) {
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
			name : '客服未派单',
			color: 'red',
			data : dataywpd
		},{
			name : '客服取消派单',
			color: 'rgba(248,161,63,1)',
			data : datayqxpd
		},{
			name : '待维护工单',
			color: '#91e8e1',
			data : dataydwx
		},{
			name : '待回访工单',
			color: 'rgba(165,170,217,1)',
			data : dataywhf
		},{
			name : '已完成工单数量',
			color: '#90ed7d',
			data : datayywc
		}]
	});
}

//通话平均时间
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
			text : '平均通话时长(总通话时长/总工单数量)：'
		},
		xAxis : {
			categories : datax
		},
		yAxis : {
			title : {
				text : '时间(秒)'
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
			name : '平均通话时长',
			color: '#90ed7d',
			data : datawy
		}]
	});
}

//客服满意度饼状图，暂不需要该功能
/*function phoneDegree(goodNum, okNum, noNum){
	$('#phoneDegree').highcharts({
		title : {
			style : {"color": "#333333", "fontSize": "15px"},
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
			//name : '客服回访满意度占比',
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
}*/

function orderType(zxNum, whNum, jyNum,tsNum){
	$('#phoneDegree').highcharts({
		title : {
			style : {"color": "#333333", "fontSize": "15px"},
			text : '受理工单类型占比'
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
		colors : [ '#058DC7', 'rgba(248,161,63,1)', '#91e8e1', 'red' ],
		series : [ {
			name: "受理工单类型",
			type : 'pie',
			//name : '客服回访满意度占比',
			data : [ 
			    ['咨询类', zxNum ],
			    [ '维护类', whNum ], 
			    [ '建议类', jyNum ], 
			    {
					name : '投诉类',
					y : tsNum,
					sliced : true, // 默认突出
					selected : true// 默认选中
			}, ]
		} ]
	});
}

//通话状态
function converStatus(access, unAccess){
	$('#orderStatus').highcharts({
		title : {
			style : {"color": "#333333", "fontSize": "15px"},
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
