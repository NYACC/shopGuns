/**
 * 初始化参数详情对话框
 */
var ParamInfoDlg = {
    count: $("#itemSize").val(),
    paramName: '',			//参数的名称
    mutiString: '',		//拼接字符串内容(拼接参数条目)
    categoryId:'',    //分类的Id
    itemTemplate: $("#itemTemplate").html()
};

/**
 * item获取新的id
 */
ParamInfoDlg.newId = function () {
    if(this.count == undefined){
        this.count = 0;
    }
    this.count = this.count + 1;
    return "paramItem" + this.count;
};

/**
 * 关闭此对话框
 */
ParamInfoDlg.close = function () {
    parent.layer.close(window.parent.Param.layerIndex);
};

/**
 * 添加条目
 */
ParamInfoDlg.addItem = function () {
    $("#itemsArea").append(this.itemTemplate);
    var ajax = new $ax(Feng.ctxPath + "/param/addItem", function (data) {
        $("#paramItem").attr("id", data);
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.start();
};

/**
 * 删除item
 */
ParamInfoDlg.deleteItem = function (event) {
    var obj = Feng.eventParseObject(event);
    var ajax = new $ax(Feng.ctxPath + "/param/deleteItem", function (data) {
        obj.parent().parent().remove();
    }, function (data) {
        Feng.error("添加失败!" +obj.parent().prev().find("input").value()+ data.responseJSON.message + "!");
    });
    ajax.set('ItemId',obj.parent().parent().attr("id"));
    ajax.start();
};

/**
 * 清除为空的item Dom
 */
ParamInfoDlg.clearNullDom = function(){
    $("[name='ParamItem']").each(function(){
        var num = $(this).find("[name='itemNum']").val();
        var name = $(this).find("[name='itemName']").val();
        if(num == '' || name == ''){
            $(this).remove();
        }
    });
};
function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(
            event.target).parents("#menuContent").length > 0)) {
        ParamInfoDlg.hideCategorySelectTree();
    }
}
/**
 * 点击分类input框时
 *
 * @param e
 * @param treeId
 * @param treeNode
 * @returns
 */
ParamInfoDlg.onClickCategory = function (e, treeId, treeNode) {
    $("#categorySel").attr("value", instance.getSelectedVal());
    $("#categoryid").attr("value", treeNode.id);
};

/**
 * 显示分类选择的树
 *
 * @returns
 */
ParamInfoDlg.showCategorySelectTree = function () {
    var cityObj = $("#categorySel");
    var cityOffset = $("#categorySel").offset();
    $("#menuContent").css({
        left: cityOffset.left + "px",
        top: cityOffset.top + cityObj.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
};
/**
 * 显示分类详情分类选择的树
 *
 * @returns
 */
ParamInfoDlg.showInfoCategorySelectTree = function () {
    var cityObj = $("#categorySel");
    var cityPosition = $("#categorySel").position();
    $("#menuContent").css({
        left: cityPosition.left + "px",
        top: cityPosition.top + cityObj.outerHeight() + "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
};
/**
 * 隐藏分类选择的树
 */
ParamInfoDlg.hideCategorySelectTree = function () {
    $("#menuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
};
$(function () {
    var ztree = new $ZTree("treeDemo", "/category/tree");
    ztree.bindOnClick(ParamInfoDlg.onClickCategory);
    ztree.init();
    instance = ztree;
});
/**
 * 收集添加参数的数据
 */
ParamInfoDlg.collectData = function () {
    this.clearNullDom();
    var mutiString = "";
    $("[name='paramItem']").each(function(){
        var num = $(this).find("[name='itemNum']").val();
        var name = $(this).find("[name='itemName']").val();
        var id = $(this).find("[name='itemNum']").parent().parent().attr("id");
        mutiString += "{'id':'"+id+"','num':'"+num+ "','name':'" + name+"'},";
    });
    this.paramName = $("#paramName").val();
    this.mutiString = "["+mutiString.substring(0,mutiString.lastIndexOf(","))+"]";
    this.categoryId=$("#categoryid").attr("value");
};


/**
 * 提交添加参数
 */
ParamInfoDlg.addSubmit = function () {
    this.collectData();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/param/add", function (data) {
        Feng.success("添加成功!");
        window.parent.Param.table.refresh();
        ParamInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set('paramName',this.paramName);
    ajax.set('paramValues',this.mutiString);
    ajax.set('categoryId',this.categoryId);
    ajax.start();
};

/**
 * 提交修改
 */
ParamInfoDlg.editSubmit = function () {
    this.collectData();
    var ajax = new $ax(Feng.ctxPath + "/param/edit", function (data) {
        Feng.success("修改成功!");
        window.parent.Param.table.refresh();
        ParamInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set('paramId',$("#paramId").val());
    ajax.set('paramName',this.paramName);
    ajax.set('paramValues',this.mutiString);
    ajax.set('categoryId',this.categoryId);
    ajax.start();
};
