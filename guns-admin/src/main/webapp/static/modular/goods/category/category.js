/**
 * 分类管理初始化
 */
var Category = {
    
};


/**
 * 点击添加
 */
Category.openAdd = function () {
    var index = layer.open({
        type: 2,
        title: '添加分类',
        area: ['800px', '560px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/category/toAdd'
    });
    this.layerIndex = index;
};

/**
 * 点击修改按钮时
 * @param userId 管理员id
 */
Category.openDetail = function (obj) {
	var id=$(obj).parent().find("select").val()[0];
    if (id!=null) {
        var index = layer.open({
            type: 2,
            title: '编辑分类',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/category/toEdit/' + id
        });
        this.layerIndex = index;
    }
};
/**
 * 停用分类
 */
Category.disable = function (obj) {
	var select=$(obj).parent().find("select");
	var id=select.val()[0];
	var level=select.attr('id');
	var pid=select.next().val();
	if (id!=null) {
        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/category/disable", function () {
                Feng.success("停用成功!");
                Category.flash(level,pid);
            }, function (data) {
                Feng.error("停用失败!" + data.responseJSON.message + "!");
            });
            ajax.set("categoryId",id);
            ajax.start();
        };
		Feng.confirm("是否停用该标签?", operation);
   }else{
	   Feng.info("请先选中表格中的某一记录！");
   }
};

/**
 * 启用分类
 */
Category.enable = function (obj) {
	var select=$(obj).parent().find("select");
	var id=select.val()[0];
	var level=select.attr('id');
	var pid=select.next().val();
	if (id!=null) {
        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/category/enable", function () {
                Feng.success("启用成功!");
                Category.flash(level,pid);
            }, function (data) {
                Feng.error("启用失败!" + data.responseJSON.message + "!");
            });
            ajax.set("categoryId",id);
            ajax.start();
        };
		Feng.confirm("是否启用该标签?", operation);
   }else{
	   Feng.info("请先选中表格中的某一记录！");
   }
};

/**
 * 重新加载分类
 */
Category.flash=function(level,pid){
	if(level=="firstCategory"){
		Category.firstLevelLoad(pid);
	}
	if(level=="secondCategory"){
		Category.secondLevelLoad(pid);
	}
	if(level=="threeCategory"){
		Category.threeLevelLoad(pid);
	}
}

/**
 * 查询分类列表
 */
Category.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Tag.table.refresh({query: queryData});
};

Category.optionHtml=function(data){
	var _option = $("<option></option>")
	.val(data.id)
	.text(data.name+'('+data.statusName+')');
    var content=$("<div></div>");
    content.append(_option);
    return content.html();
}

Category.threeLevelLoad=function(pid){
	var ajax = new $ax(Feng.ctxPath + "/category/list", function (data) {
    	var content="";
    	for(var i=0;i<data.length;i++){
    		content+=Category.optionHtml(data[i]);
    	}
        $("#threeCategory").html(content);
    }, function (data) {
    	Feng.error("数据加载失败!" + data.responseJSON.message + "!");
    });
    ajax.set("pid",pid);
    ajax.start();
}

Category.secondLevelLoad=function(pid){
	$("#threeCategory").html("");
	var ajax = new $ax(Feng.ctxPath + "/category/list", function (data) {
    	var content="";
    	for(var i=0;i<data.length;i++){
    		content+=Category.optionHtml(data[i]);
    	}
        $("#secondCategory").html(content);
        $("#secondCategory").next().val(pid);
    }, function (data) {
    	Feng.error("数据加载失败!" + data.responseJSON.message + "!");
    });
    ajax.set("pid",pid);
    ajax.start();
}

Category.firstLevelLoad=function(){
	$("#secondCategory").html("");
	$("#threeCategory").html("");
	var ajax = new $ax(Feng.ctxPath + "/category/list", function (data) {
    	var content="";
    	for(var i=0;i<data.length;i++){
	        content+=Category.optionHtml(data[i]);
    	}
        $("#firstCategory").html(content);
    }, function (data) {
    	Feng.error("数据加载失败!" + data.responseJSON.message + "!");
    });
    ajax.set("pid",0);
    ajax.start();
}

$(function () {
    Category.firstLevelLoad();
});
