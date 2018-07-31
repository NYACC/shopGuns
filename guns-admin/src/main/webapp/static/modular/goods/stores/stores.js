/**
 * 门店管理初始化
 */
var Stores = {
    id: "StoresTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Stores.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '名称', field: 'name', align: 'center', valign: 'middle', sortable: true},
        {title: '地址', field: 'address', align: 'center', valign: 'middle', sortable: true},
        {title: '电话', field: 'phone', align: 'center', valign: 'middle', sortable: true},
        {title: '排序', field: 'num', align: 'center', valign: 'middle', sortable: true}
        ];
};

/**
 * 检查是否选中
 */
Stores.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Stores.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加门店
 */
Stores.openAddStores = function () {
    var index = layer.open({
        type: 2,
        title: '添加门店',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/stores/toAdd'
    });
    layer.full(index);
    this.layerIndex = index;
};

/**
 * 点击查看门店详情
 */
Stores.openStoresDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '门店详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/stores/toEdit/' + Stores.seItem.id
        });
        layer.full(index);
        this.layerIndex = index;
    }
};

/**
 * 删除门店
 */
Stores.delete = function () {
    if (this.check()) {

        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/stores/delete", function () {
                Feng.success("删除成功!");
                Stores.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("storesId",Stores.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否刪除该门店?", operation);
    }
};

/**
 * 查询门店列表
 */
Stores.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Stores.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Stores.initColumn();
    var table = new BSTable(Stores.id, "/stores/list", defaultColunms);
    table.setPaginationType("client");
    table.init();
    Stores.table = table;
});
