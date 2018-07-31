/**
 * 初始化品牌详情对话框
 */
var OrderInfoDlg = {
	orderInfoData : {},
	orderItemsId:"OrderItemsTable",
	orderItems:null,
    shippings:null
};

/**
 * 初始化订单明细表格的列
 */
OrderInfoDlg.initItemColumn = function () {
    return [
        {field: 'selectItem', radio: true,visible:false},
        {title: '商品规格名称', field: 'goodsName', align: 'center', valign: 'middle', sortable: false},
        {title: '单价', field: 'actualPrice', align: 'center', valign: 'middle', sortable: true},
        {title: '数量', field: 'quantity', align: 'center', valign: 'middle', sortable: true},
        {title: '小计', field: 'amount', align: 'center', valign: 'middle', sortable: true}];
};

/**
 * 获取对话框的数据
 */
OrderInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 设置对话框中的数据
 */
OrderInfoDlg.set = function(key, val) {
    this.orderInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

$(function() {
	var defaultColunms = OrderInfoDlg.initItemColumn();
    $('#' + OrderInfoDlg.orderItemsId).bootstrapTable({
        data:OrderInfoDlg.orderItems,
        columns: defaultColunms,		//列数组
        height:500,
    });
});
