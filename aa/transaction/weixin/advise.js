function submit_advise(){
	$("#advise_form").form("submit", {
		onSubmit : function() {
			return $(this).form('enableValidation').form('validate');
		},
		success : function(data) {
			data = eval("(" + data + ")");
			$.messager.alert(' ', data.resultMsg, 'info', function(){
				window.location.reload();
			});
		}

	});
}