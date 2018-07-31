/**
 * 客户初始化
 */
var Payment = {
	id: "PaymentTable",	//表格id
	seItem: null,		//选中的条目
	table: null,
	layerIndex: -1
};

/**
 * 初始化表格的列
 */
Payment.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '订单编号', field: 'outTradeNo', align: 'center', valign: 'middle', sortable: true},
        {title: '支付单号', field: 'transactionId', align: 'center', valign: 'middle', sortable: true},
        {title: '付款账户', field: 'memberPhone', align: 'center', valign: 'middle', sortable: true},
        {title: '支付金额', field: 'settlementTotalFee', align: 'center', valign: 'middle', sortable: true},
        {title: '付款银行', field: 'bankType', align: 'center', valign: 'middle', sortable: true},
        {title: '支付时间', field: 'timeEnd', align: 'center', valign: 'middle', sortable: true},]
};

/**
 * 查询客户列表
 */
Payment.search = function () {
    var queryData = {};
    queryData['outTradeNo'] = $("#outTradeNo").val();
    queryData['transactionId'] = $("#transactionId").val();
    Payment.table.refresh({query: queryData});
};

/**
 * 是否选中
 */
Payment.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
    	Payment.seItem = selected[0];
        return true;
    }
};

$(function () {
    var defaultColunms = Payment.initColumn();
    var table = new BSTable(Payment.id, "/payment/list", defaultColunms);
    table.setPaginationType("client");
    table.init();
    Payment.table = table;
});
