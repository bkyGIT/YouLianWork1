<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String webpath = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>知识库配置</title>
<script type="text/javascript" src="<%=webpath%>/transaction/knowledge/knowledge.js"></script>

<script type="text/javascript">
	var webpath = '<%=webpath%>';
	$(function() {
		$("#work_panel").hide();
		//绑定树时间
		menuOnCliek();
	});
	
	function menuOnCliek() {
		$("#knowledge_tree").tree({
			onClick : function(node) {
				loadPage(node.id);
			}
		});

	}
</script>
<style type="text/css">
.top_search {
	height: 40px;
	margin: 0px auto;
	border-radius: 3px;
	-moz-border-radius: 3px;
	-webkit-border-radius: 3px;
	background: #FFF;
	margin-top: 20px;
	text-align: left;
	line-height: 40px;
}

.menu_before {
	margin-left: 30px;
}

.menu_botton {
	margin-left: 10px;
}
</style>
</head>

<body>
	<div id="right_ctn">
		<div class="right_m">
			<div class="hy_list">
				<div class="box_t">
					<span class="name">知识库配置</span>
				</div>

				<div class="easyui-layout" id="main_layout" style="width:100%;height:92%;">
					<!-- 知识库配置 -->
					<div data-options="region:'west'" title="知识库配置"
						style="width:15%;padding:10px">
						<div class="easyui-panel" style="padding:5px">
							<ul class="easyui-tree" id="knowledge_tree"
								data-options="url:'<%=webpath%>/knowledge/knowledgeList.action',method:'post',animate:true,lines:true"></ul>
						</div>
					</div>

					<!-- 中央区域 -->
					<div data-options="region:'center'"
						style="width:70%;padding:10px;background-color: rgba(232, 232, 232, 1);">
						<div id="work_panel"  style="width:100%;height:92%;">
							<!--操作条-->
							<div class="top_search">
								<span class="knowledge_before"></span>
								<a href="javascript:showAddKnowledge()" class="easyui-linkbutton menu_botton" data-options="iconCls:'icon-add'">添加</a>
								<a href="javascript:beforeDeleteKnowledge()" class="easyui-linkbutton menu_botton" data-options="iconCls:'icon-cancel'">删除</a> 
								<a href="javascript:showEditKnowledge()" class="easyui-linkbutton menu_botton" data-options="iconCls:'icon-add'">修改</a>
							</div>

							<!-- 展示区域 -->
							<div class="space_hx">&nbsp;</div>
							<table id="knowledge_list_table" style="width:100%;height:98%;"></table>
						</div>

					</div>
					
				</div>

			</div>
		</div>
	</div>
	<!-- 添加弹窗区域 -->
	<div id="add_knowledge_dialog" class="easyui-window" title="添加人员"
		data-options="modal:true,closed:true,iconCls:'icon-save',footer:'#add_footer'"
		style="width:700px;height:300px;padding:10px;">
		<form id="add_knowledge_form"
			action="<%=webpath%>/knowledge/addKnowledge.action" method="post">
			<input type="hidden" id="knowledge_code" name="knowledge_code"/>
			<table cellpadding="0" cellspacing="0" class="list_hy"
				style="width: 99%;margin-left:0px;">
				<tr>
					<td><input class="easyui-textbox" name="knowledge_name"
						style="width:300px;"
						data-options="label:'编码名称:',required:true"
						id="knowledge_name"></td>
					<td><input class="easyui-textbox"
						name="knowledge_id" style="width:300px;"
						data-options="label:'编码:',required:true,validType:'integer'"
						id="knowledge_id"></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="add_footer"
		style="text-align:left;float: right;padding: 5px 0 0;border: none;">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
			href="javascript:void(0)" onclick="javascript:addKnowledge()"
			style="width:80px">保存</a> <a class="easyui-linkbutton"
			data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
			onclick="javascript:$('#add_knowledge_dialog').window('close')"
			style="width:80px">取消</a>
	</div>
	
	<!-- 修改弹窗区域 -->
	<div id="edit_knowledge_dialog" class="easyui-window" title="添加人员"
		data-options="modal:true,closed:true,iconCls:'icon-save',footer:'#edit_footer'"
		style="width:700px;height:300px;padding:10px;">
		<form id="edit_knowledge_form"
			action="<%=webpath%>/knowledge/editKnowledge.action" method="post">
			<input type="hidden" id="knowledge_maxaccept" name="knowledge_maxaccept"/>
			<table cellpadding="0" cellspacing="0" class="list_hy"
				style="width: 99%;margin-left:0px;">
				<tr>
					<td><input class="easyui-textbox" name="edit_name"
						style="width:300px;"
						data-options="label:'编码名称:',required:true"
						id="edit_name"></td>
					<td><input class="easyui-textbox"
						name="edit_id" style="width:300px;"
						data-options="label:'编码:',required:true,validType:'integer'"
						id="edit_id"></td>
				</tr>
			</table>
		</form>
	</div>
	<div id="edit_footer"
		style="text-align:left;float: right;padding: 5px 0 0;border: none;">
		<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'"
			href="javascript:void(0)" onclick="javascript:editKnowledge()"
			style="width:80px">保存</a> <a class="easyui-linkbutton"
			data-options="iconCls:'icon-cancel'" href="javascript:void(0)"
			onclick="javascript:$('#edit_knowledge_dialog').window('close')"
			style="width:80px">取消</a>
	</div>
	
	<!-- 删除前弹窗 -->
	<div id="before_delete_dialog" class="easyui-dialog" title=" " style="width:300px;height:150px;padding:10px"
			data-options="closed:true,
				buttons: [{
					text:'确认',
					iconCls:'icon-ok',
					handler:function(){
						$('#before_delete_dialog').window('close');
						deleteKnowledge();
					}
				},{
					text:'取消',
					handler:function(){
						$('#before_delete_dialog').window('close');
					}
				}]
			">
		
	</div>
</body>
</html>
