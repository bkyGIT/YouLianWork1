<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String webpath = request.getContextPath();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>菜单管理</title>
<script type="text/javascript" src="<%=webpath%>/transaction/menu/menu.js"></script>

<script type="text/javascript">
	var webpath = '<%=webpath%>';
	$(function() {
		$("#work_panel").hide();
		//绑定树时间
		menuOnCliek();
	});
	
	function menuOnCliek() {
		$("#menuList").tree({
			onClick : function(node) {
				showMenuInfo(node.id);
			}
		});

	}
	//获取菜单信息
	function showMenuInfo(menuID) {
		$.ajax({
			url : webpath + "/menu/showMenuInfo.action",
			type : "post",
			dataType : "json",
			data : {
				"menuID" : menuID
			},
			success : function(data) {
				var menuInfo = data[0];
				//设置菜单等级默认值
				$("#menu_level").val(menuInfo.menuLevel);
				//设置不同级别菜单显示不同操作按钮
				if (menuInfo.menuLevel == "0") {
					$("#work_panel").show();
					$("#add_child_nemu").show();
				} else {
					$("#work_panel").show();
					$("#add_child_nemu").hide();
				}
				
				//展示菜单信息
				$("#menu_name").textbox("setValue", menuInfo.menuName);
				$("#menu_id").text(menuInfo.maxaccept);
				$("#menu_bak").val(menuInfo.maxaccept);
				$("#menu_url").textbox("setValue", menuInfo.menuUrl);
				
				if(menuInfo.isShow == "0"){
					$("#is_show").attr("checked", "checked");
				}else{
					$("#not_show").attr("checked", "checked");
				}
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
					<span class="name">菜单管理</span>
				</div>

				<div class="easyui-layout" id="main_layout" style="width:100%;height:92%;">
					<!-- 菜单列表 -->
					<div id="menu_tree" data-options="region:'west'" title="菜单列表"
						style="width:15%;padding:10px">
						<div class="easyui-panel" style="padding:5px">
							<ul class="easyui-tree" id="menuList"
								data-options="url:'<%=webpath%>/menu/menuList.action',method:'post',animate:true,lines:true"></ul>
						</div>
					</div>

					<!-- 中央区域 -->
					<div data-options="region:'center'"
						style="width:70%;padding:10px;background-color: rgba(232, 232, 232, 1);">
						<div id="work_panel"  style="width:100%;height:92%;">
							<!--查询-->
							<div class="top_search">
								<span class="menu_before"></span>
								<a href="javascript:beforeDeleteMenu()" class="easyui-linkbutton menu_botton" data-options="iconCls:'icon-cancel'">删除菜单</a> 
								<a href="javascript:showAddMenu(1)" class="easyui-linkbutton menu_botton" data-options="iconCls:'icon-add'" id="add_child_nemu">添加下级菜单</a>
								<a href="javascript:showAddMenu(0)" class="easyui-linkbutton menu_botton" data-options="iconCls:'icon-add'" id="add_top_nemu">添加一级菜单</a>
								<a href="javascript:saveMenu('save');" class="easyui-linkbutton menu_botton" data-options="iconCls:'icon-save'">保存菜单</a>
							</div>

							<!-- 展示区域 -->
							<div class="space_hx">&nbsp;</div>
							<form id="menu_form" action="" method="post">
								<input type="hidden" id="menu_level">
								<table cellpadding="0" cellspacing="0" class="list_hy"
									style="width: 80%;margin-left:0px;">
									<tr>
										<td><input class="easyui-textbox" name="menu_name"
											style="width:300px"
											data-options="label:'菜单名称:',required:true" id="menu_name">
										</td>
										<td><span>菜单编号:</span><span style="margin-left: 34px;" id="menu_id"></span><input type="hidden" name="menu_id" id="menu_bak"/>
										</td>
									</tr>
									<tr>
										<td><input class="easyui-textbox" name="menu_url"
											style="width:300px"
											data-options="label:'菜单地址:'" id="menu_url">
										</td>
										<td>是否显示:
											<input type="radio" name="show_flag" id="is_show" value="0" style="margin-left:34px;"/>&nbsp;&nbsp;是
											<input type="radio" name="show_flag" id="not_show" value="1" style="margin-left:10px;"/>&nbsp;&nbsp;否
										</td>
									</tr>
								</table>
							</form>
							
							<!-- 添加菜单区域 -->
							<div id="add_dialog" class="easyui-window" title="添加菜单" data-options="modal:true,closed:true,iconCls:'icon-save'" style="width:700px;height:200px;padding:10px;">
								<form id="add_menu_form" action="" method="post">
								<input type="hidden" id="add_flag" name="add_flag">
								<input type="hidden" name="add_menu_id" id="add_menu_bak"/>
								<table cellpadding="0" cellspacing="0" class="list_hy"
									style="width: 80%;margin-left:0px;">
									<tr>
										<td><input class="easyui-textbox" name="menu_name"
											style="width:300px"
											data-options="label:'菜单名称:',required:true" id="menu_name">
										</td>
										<td>是否显示:
											<input type="radio" name="show_flag" id="add_not_show" checked="checked" value="0" style="margin-left:34px;"/>&nbsp;&nbsp;是
											<input type="radio" name="show_flag" id="add_is_show" value="1" style="margin-left:10px;"/>&nbsp;&nbsp;否
										</td>
									</tr>
									<tr>
										<td><input class="easyui-textbox" name="menu_url"
											style="width:300px"
											data-options="label:'菜单地址:'" id="menu_url">
										</td>
										<td>
										</td>
									</tr>
								</table>
							</form>
							<div data-options="region:'south',border:false" style="text-align:left;padding:5px 0 0;">
								<a class="easyui-linkbutton" data-options="iconCls:'icon-ok'" href="javascript:void(0)" onclick="javascript:addMenu()" style="width:80px">保存</a>
								<a class="easyui-linkbutton" data-options="iconCls:'icon-cancel'" href="javascript:void(0)" onclick="javascript:$('#add_dialog').window('close')" style="width:80px">取消</a>
							</div>
							</div>
						</div>

					</div>
					
				</div>

			</div>
		</div>
	</div>
	<!-- 添加弹窗 -->
	<div id="result_dialog" class="easyui-dialog" title=" " style="width:300px;height:150px;padding:10px"
			data-options="closed:true,
				buttons: [{
					text:'确认',
					iconCls:'icon-ok',
					handler:function(){
						window.location.reload();
					}
				}]
			">
		
	</div>
	
	<!-- 删除前弹窗 -->
	<div id="before_delete_dialog" class="easyui-dialog" title=" " style="width:300px;height:150px;padding:10px"
			data-options="closed:true,
				buttons: [{
					text:'确认',
					iconCls:'icon-ok',
					handler:function(){
						$('#before_delete_dialog').window('close');
						deleteMenu();
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
