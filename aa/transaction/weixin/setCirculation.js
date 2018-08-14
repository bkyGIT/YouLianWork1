function loadCir(){
	$.ajax({
		url : webpath + "/weixin/getCirType.action",
		type : "post",
		dataType : "json",
		success : function(data) {
			var resultCode = data.resultCode;
			var resultMsg = data.resultMsg;
			var resultData = data.resultData;
			
			if(resultData != null){
				if(resultData.CIR_TYPE == "0"){
					$("#self_work").attr("checked", true);
				}else{
					$("#other_work").attr("checked", true);
				}
			}else{
				$("#other_work").attr("checked", true);
			}
		}
	});
}

//保存流转方式
function saveCir(){
	
	var cirType = $("input[name='work_type']:checked").val();
	
	$.ajax({
		url : webpath + "/weixin/setCirType.action",
		type : "post",
		dataType : "json",
		data: {"cirType": cirType},
		success : function(data) {
			var resultCode = data.resultCode;
			var resultMsg = data.resultMsg;
			var resultData = data.resultData;
			$.messager.alert(' ', resultMsg, 'info');
		}
	});
}