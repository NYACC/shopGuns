/**
 * 审核订单初始化
 */
var OrderAudit = {
	id: "OrderTable",	//表格id
	seItem: null,		//选中的条目
	table: null,
	layerIndex: -1
};

/**
 * 初始化表格的列
 */
OrderAudit.initColumn = function () {
    return [
        {field: 'selectItem', radio: true,visible:false,},
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
        return '<button id="audit" icon="fa-check" type="button" class="btn btn-primary btn-xs">审核</button>';
};

window.operateEvents = {
    "click #audit":function(e,value,row,index){
        var index = layer.open({
            type: 2,
            title: '订单审核',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/orderAudit/audit/' + row.id
        });
        layer.full(index);
        this.layerIndex = index;
    }
}


$(function () {
    var defaultColunms = OrderAudit.initColumn();
    var table = new BSTable(OrderAudit.id, "/orderAudit/list", defaultColunms);
    table.setPaginationType("client");
    table.init();
    OrderAudit.table = table;
});
