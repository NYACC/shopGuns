/**
 * 客户初始化
 */
var Refund = {
	id: "RefundTable",	//表格id
	seItem: null,		//选中的条目
	table: null,
	layerIndex: -1
};

/**
 * 初始化表格的列
 */
Refund.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '订单编号', field: 'outTradeNo', align: 'center', valign: 'middle', sortable: true},
        {title: '支付单号', field: 'transactionId', align: 'center', valign: 'middle', sortable: true},
        {title: '退款单号', field: 'refundId', align: 'center', valign: 'middle', sortable: true},
        {title: '支付金额', field: 'settlementTotalFee', align: 'center', valign: 'middle', sortable: true},
        {title: '退款金额', field: 'settlementRefundFee', align: 'center', valign: 'middle', sortable: true},
        {title: '退款时间', field: 'successTime', align: 'center', valign: 'middle', sortable: true},]
};

/**
 * 查询客户列表
 */
Refund.search = function () {
    var queryData = {};
    queryData['outTradeNo'] = $("#outTradeNo").val();
    queryData['transactionId'] = $("#transactionId").val();
    Refund.table.refresh({query: queryData});
};

/**
 * 是否选中
 */
Refund.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
    	Refund.seItem = selected[0];
        return true;
    }
};

$(function () {
    var defaultColunms = Refund.initColumn();
    var table = new BSTable(Refund.id, "/refund/list", defaultColunms);
    table.setPaginationType("client");
    table.init();
    Refund.table = table;
});
