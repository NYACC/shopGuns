/**
 * 参数管理初始化
 */
var Param = {
    id: "ParamTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Param.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '名称', field: 'name', align: 'center', valign: 'middle', sortable: true},
        {title: '参数项', field: 'itemName', align: 'center', valign: 'middle', sortable: true},
        {title: '绑定分类', field: 'sortName', align: 'center', valign: 'middle', sortable: true}];
};

/**
 * 检查是否选中
 */
Param.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if (selected.length == 0) {
        Feng.info("请先选中表格中的某一记录！");
        return false;
    } else {
    	Param.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加参数
 */
Param.openAddParam = function () {
    var index = layer.open({
        type: 2,
        title: '添加参数',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/param/toAdd'
    });
    this.layerIndex = index;
};

/**
 * 打开查看参数详情
 */
Param.openParamDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '参数详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/param/toEdit/' + Param.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除参数
 */
Param.delete = function () {
    if (this.check()) {

        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/param/delete", function (data) {
                Feng.success("删除成功!");
                Param.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("paramId", Param.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否刪除参数 " + Param.seItem.name + "?", operation);
    }
};

/**
 * 查询参数列表
 */
Param.search = function () {
    var queryData = {};
    queryData['categoryId'] = Param.categoryId;
    queryData['condition'] = $("#condition").val();
   Param.table.refresh({query: queryData});
};

Param.onClickSort = function (e, treeId, treeNode) {
    Param.categoryId = treeNode.id;
    Param.search();
};

$(function () {
    var defaultColunms =Param.initColumn();
    var table = new BSTable(Param.id, "/param/list", defaultColunms);
    table.setPaginationType("client");
   Param.table = table.init();
   var ztree = new $ZTree("categoryTree", "/category/tree");
   ztree.bindOnClick(Param.onClickSort);
   ztree.init();
});
