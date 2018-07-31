/**
 * 出入库单初始化
 */
var Inventory = {
    id: "InventoryTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Inventory.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '编号', field: 'sn', align: 'center', valign: 'middle', sortable: true},
        {title: '规格条码', field: 'barcode', align: 'center', valign: 'middle', sortable: true},
        {title: '商品规格名称', field: 'goodsName', align: 'center', valign: 'middle', sortable: true},
        {title: '操作类型', field: 'type', align: 'center', valign: 'middle', sortable: true},
        {title: '入库数量', field: 'amount', align: 'center', valign: 'middle', sortable: true},
        {title: '出库数量', field: 'amount', align: 'center', valign: 'middle', sortable: true},
        {title: '操作员', field: 'operatorName', align: 'center', valign: 'middle', sortable: true},
        {title: '门店', field: 'shopName', align: 'center', valign: 'middle', sortable: true},
        {title: '创建时间', field: 'createTime', align: 'center', valign: 'middle', sortable: true},
        {title: '操作', align: 'center', valign: 'middle', sortable: true,formatter : operateFormatter, events : operateEvents}];
};

function operateFormatter(value, row, index) {
    return '<button  id="inventory" icon="fa-check" type="button" class="btn btn-primary btn-xs">查看详情</button>';
};

window.operateEvents = {
    "click #inventory":function(e,value,row,index){
        var index = layer.open({
            type: 2,
            title: '出入库单详情',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/inventory/detail/'+row.id
        });
        //layer.full(index);
        this.layerIndex = index;
    }
}

Inventory.openAddInventory=function(type){
    var title = "";
    if(type==0){
        title="商品出库";
    }else {
        title = "商品入库";
    }
	 var index = layer.open({
	     type: 2,
	     title: title,
	     area: ['800px', '420px'], //宽高
	     fix: false, //不固定
	     maxmin: true,
	     content: Feng.ctxPath + '/inventory/toInventory/'+type
	 });
	 layer.full(index);
	 this.layerIndex = index;
}

/**
 * 查询出入库单列表
 */
Inventory.search = function () {
    var queryData = {};
    queryData['goodsName'] = $("#goodsName").val();
    queryData['barcode'] = $("#barcode").val();
    queryData['shopName'] = $("#shopName").val();
    queryData['operator'] = $("#operator").val();
    queryData['type'] = $("#type").val();
    queryData['startTime'] = $("input[name='start']").val();
    queryData['endTime'] = $("input[name='end']").val();
    Inventory.table.refresh({query: queryData});
};

$(function () {
    var defaultColumns = Inventory.initColumn();
    var table = new BSTable(Inventory.id, "/inventory/list", defaultColumns);
    table.setPaginationType("client");
    table.init();
    Inventory.table = table;

});

