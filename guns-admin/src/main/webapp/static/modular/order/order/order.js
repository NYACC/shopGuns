/**
 * 订单初始化
 */
var Order = {
	id: "OrderTable",	//表格id
	seItem: null,		//选中的条目
	table: null,
	layerIndex: -1
};

/**
 * 初始化表格的列
 */
Order.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '编号', field: 'sn', align: 'center', valign: 'middle', sortable: true},
        {title: '应付款', field: 'due', align: 'center', valign: 'middle', sortable: true},
        {title: '实付款', field: 'actualPay', align: 'center', valign: 'middle', sortable: true},
        {title: '顾客', field: 'customerName', align: 'center', valign: 'middle', sortable: true},
        {title: '收货人', field: 'consigneeName', align: 'center', valign: 'middle', sortable: true},
        {title: '支付方式', field: 'payment', align: 'center', valign: 'middle', sortable: true},
        {title: '配送方式', field: 'dilivery', align: 'center', valign: 'middle', sortable: true},
        {title: '状态', field: 'statusName', align: 'center', valign: 'middle', sortable: true},
        {title: '创建日期', field: 'createTime', align: 'center', valign: 'middle', sortable: true},
        {title: '所属门店', field: 'shopName', align: 'center', valign: 'middle', sortable: true},
        {title: '操作', field: 'num', align: 'center', valign: 'middle', sortable: true,formatter : operateFormatter, events : operateEvents}];
};
function operateFormatter(value, row, index) {
        return '<button id="orderDetail" icon="fa-check" type="button" class="btn btn-primary btn-xs">查看详情</button>';
};

window.operateEvents = {
    "click #orderDetail":function(e,value,row,index){
        var index = layer.open({
            type: 2,
            title: '订单详情',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/order/detail/' + row.id
        });
        layer.full(index);
        this.layerIndex = index;
    }
}
/**
 * 点击添加订单
 */
Order.openAddOrder = function () {
    var index = layer.open({
        type: 2,
        title: '添加订单',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/order/toAdd'
    });
    layer.full(index);
    this.layerIndex = index;
};


/**
 * 查询订单列表
 */
Order.search = function () {
    var queryData = {};
    queryData['customerName'] = $("#customerName").val();
    queryData['sn'] = $("#sn").val();
    queryData['status'] = $("#status").val();
    Order.table.refresh({query: queryData});
};

/**
 * 是否选中
 */
Order.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
    	Order.seItem = selected[0];
        return true;
    }
};

/**
 * 点击修改订单
 */
Order.openOrderDetail = function () {
	if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '编辑规格组',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/order/toEdit/' + this.seItem.id
        });
        layer.full(index);
        this.layerIndex = index;
    }
};


$(function () {
    var defaultColunms = Order.initColumn();
    var table = new BSTable(Order.id, "/order/list", defaultColunms);
    table.setPaginationType("client");
    table.init();
    Order.table = table;
});
