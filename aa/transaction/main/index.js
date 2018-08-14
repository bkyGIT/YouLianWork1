function cancel(){
	alert(1);
}
var myCodeCache={};
function getCodeName(codetype,codevalue){
	if(myCodeCache&&myCodeCache[codetype]&&myCodeCache[codetype][codevalue]){
		
	}	else{
		$.ajax({
			url : webpath + "/code/"+codetype+"/"+codevalue+".action",
			type : "post",
			dataType : "json",
			async:false,
			success : function(data) {
				if(!myCodeCache[codetype]){
					myCodeCache[codetype]={}
				}
				myCodeCache[codetype][codevalue]=data["CODE_NAME"];
			}
		});
	}
	return myCodeCache[codetype][codevalue];		
}