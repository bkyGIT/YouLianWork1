function getQuery(){
	getChart();
}

function getChart(){
	var beginTime = $("#begin_time_param").datebox('getValue');
	var endTime = $("#end_time_param").datebox('getValue');
	
	
}

function pie(){
	$('#left').highcharts({
		title : {
			text : 'xxx客服满意度占比'
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
			data : [ [ '满意', 45.0 ], [ '合格', 26.8 ], {
				name : '不满意',
				y : 12.8,
				sliced : true, // 默认突出
				selected : true// 默认选中
			}, ]
		} ]
	});
}

function column(){
	$('#right').highcharts({
		chart: {
			type: 'column'
		},
		credits : {
			enabled : false// 去除水印
		},
		title: {
			text: '客服绩效嵌套图'
		},
		xAxis: {
				categories: []
		},
		yAxis: [{
				min: 0,
				title: {
						text: '电话量(次数)'
				}
		}, {
				title: {
						text: '时间 (min)'
				},
				opposite: true
		}],
		legend: {
				shadow: false
		},
		tooltip: {
				shared: true
		},
		plotOptions: {
				column: {
						grouping: false,
						shadow: false,
						borderWidth: 0
				}
		},
		series: [{
				name: '接电次数',
				color: 'rgba(165,170,217,1)',
				data: [150, 73, 20],
				pointPadding: 0.3, // 通过 pointPadding 和 pointPlacement 控制柱子位置
				pointPlacement: -0.2
		}, {
				name: '未接次数',
				color: 'red',
				data: [10, 30, 5],
				pointPadding: 0.4,
				pointPlacement: -0.2
		}, {
				name: '平均时长',
				color: 'rgba(248,161,63,1)',
				data: [183.6, 178.8, 198.5],
				tooltip: {  // 为当前数据列指定特定的 tooltip 选项
						valueSuffix: ' min'
				},
				pointPadding: 0.3,
				pointPlacement: 0.2,
				yAxis: 1  // 指定数据列所在的 yAxis
		}]
	});
}

