/**
 * 初始化品牌详情对话框
 */
var AuditInfoDlg = {
	orderInfoData : {},
	orderItemsId:"OrderItemsTable",
	orderItems:null,
};
/**
 * 初始化审核订单明细表格的列
 */
AuditInfoDlg.initItemColumn = function () {
    return [
        {field: 'selectItem', radio: true,visible:false,},
        {title: '商品规格名称', field: 'goodsName', align: 'center', valign: 'middle', sortable: false,},
        {title: '单价', field: 'actualPrice', align: 'center', valign: 'middle', sortable: true},
        {title: '数量', field: 'quantity', align: 'center', valign: 'middle', sortable: true},
        {title: '小计', field: 'amount', align: 'center', valign: 'middle', sortable: true}];
};

/**
 * 获取对话框的数据
 */
AuditInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 设置对话框中的数据
 */
AuditInfoDlg.set = function(key, val) {
    this.orderInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 审核通过
 */
AuditInfoDlg.auditPass = function () {
    var ajax = new $ax(Feng.ctxPath + "/orderAudit/auditPass", function (data) {
        Feng.success("审核成功!");
        window.parent.OrderAudit.table.refresh();
        AuditInfoDlg.close();
    }, function (data) {
        Feng.error("审核失败!" + data.responseJSON.message + "!");
    });
    ajax.set("orderId",$("#id").val());
    ajax.set("remark",$("#remark").val());
    ajax.start();
}

/**
 * 审核不通过
 */
AuditInfoDlg.auditUnPass = function () {
    var ajax = new $ax(Feng.ctxPath + "/orderAudit/auditUnPass", function (data) {
        Feng.success("审核成功!");
        window.parent.OrderAudit.table.refresh();
        AuditInfoDlg.close();
    }, function (data) {
        Feng.error("审核失败!" + data.responseJSON.message + "!");
    });
    ajax.set("orderId",$("#id").val());
    ajax.set("remark",$("#remark").val());
    ajax.start();
}

/**
 * 关闭此对话框
 */
AuditInfoDlg.close = function () {
    parent.layer.close(1);
    
}

$(function() {
	var defaultColunms = AuditInfoDlg.initItemColumn();
    $('#' + AuditInfoDlg.orderItemsId).bootstrapTable({
        data:AuditInfoDlg.orderItems,
        columns: defaultColunms,		//列数组
        height:500,
    });
});
