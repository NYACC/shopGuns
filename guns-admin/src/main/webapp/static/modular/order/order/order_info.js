/**
 * 初始化品牌详情对话框
 */
var OrderInfoDlg = {
	orderInfoData : {},
	orderItemsId:"OrderItemsTable",
	logId:"OrderLogTable",
	shopItemsJson: null,
	orderItems:null,
	itemTemplate: $("#itemTemplate").html()
};
/**
 * 添加条目
 */
OrderInfoDlg.addItem = function () {
    $("#orderItems").append(this.itemTemplate);
};

/**
 * 删除item
 */
OrderInfoDlg.deleteItem = function (event) {
    var obj = Feng.eventParseObject(event);
    obj.parent().parent().remove();
};

/**
 * 收集结算数据
 */
OrderInfoDlg.collectAccountData = function () {
	this.shopItemsJson=null;
    var mutiString = "[";
    $("[name='orderItem']").each(function(){
    	var goodsId = $(this).find("[name='goodsId']").val();
    	var productId = $(this).find("[name='productId']").val();
        var quantity = $(this).find("[name='quantity']").val();
        var goodsName = $(this).find("[name='goodsName']").val();
        mutiString = mutiString + ('{"goodsId":"'+goodsId+'","productId":"'+productId+'","goodsName":"'+goodsName+'","quantity":"'+quantity+'"},');
    });
    this.shopItemsJson = mutiString+"]";
};

/**
 * 结算
 */
OrderInfoDlg.shopListSubmit = function () {
	this.collectAccountData();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/account/shopListSubmit", function (data) {
    	$("#sn").html(data.sn);
    	$("#createTime").html(data.createTime);
    	$("#shopName").html(data.shop.name);
    	$("#packages").html(data.packages);
    	$("#due").html(data.due);
    	$("#actualPay").html(data.actualPay);
    	$("#customerName").val(data.member.name);
    	$("#statusName").val(data.statusName);
    	$("#id").val(data.id);
    	OrderInfoDlg.orderInfoData=data;
        Feng.success("结算成功!");
        $('a[href="#tab-2"]').tab('show');
    }, function (data) {
        Feng.error("结算失败!" + data.responseJSON.message + "!");
    });
    ajax.set("shopItemsJson",this.shopItemsJson);
    ajax.set("customerId","1");
    ajax.set("sn",$("#sn").html());
    ajax.set("shopId","1");
    ajax.start();
}

/**
 * 初始化订单明细表格的列
 */
OrderInfoDlg.initItemColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '商品规格名称', field: 'goodsName', align: 'center', valign: 'middle', sortable: true},
        {title: '单价', field: 'actualPrice', align: 'center', valign: 'middle', sortable: true},
        {title: '数量', field: 'quantity', align: 'center', valign: 'middle', sortable: true},
        {title: '小计', field: 'amount', align: 'center', valign: 'middle', sortable: true},];
};

/**
 * 初始化订单日志表格的列
 */
OrderInfoDlg.initLogColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '操作', field: 'logname', align: 'center', valign: 'middle', sortable: true},
        {title: '操作人', field: 'userName', align: 'center', valign: 'middle', sortable: true},
        {title: '操作内容', field: 'message', align: 'center', valign: 'middle', sortable: true},
        {title: '时间', field: 'createtime', align: 'center', valign: 'middle', sortable: true}];
};

/**
 * 获取对话框的数据
 */
OrderInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 清除数据
 */
OrderInfoDlg.clearData = function() {
    this.orderInfoData = {};
}
/**
 * 收集数据
 */
OrderInfoDlg.collectData = function() {
    this.set('id').set('consigneeName').set('phoneNum').set('address').set('remark');
}
/**
 * 设置对话框中的数据
 */
OrderInfoDlg.set = function(key, val) {
    this.orderInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 提交修改
 */
OrderInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/order/edit", function(data){
        Feng.success("修改成功!");
        window.parent.Order.table.refresh();
        OrderInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.orderInfoData);
    ajax.set("sn",$("#sn").html());
    ajax.start();
}


/**
 * 提交添加
 */
OrderInfoDlg.addSubmit = function() {
    this.collectData();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/order/add", function(data){
        Feng.success("添加成功!");
        window.parent.Order.table.refresh();
        OrderInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    this.orderInfoData.sn=$("#sn").html();
    ajax.set("orderJson",JSON.stringify(this.orderInfoData));
    ajax.start();
}

/**
 * 关闭此对话框
 */
OrderInfoDlg.close = function () {
    parent.layer.close(window.parent.Order.layerIndex);
}

$(function() {
	var defaultColunms = OrderInfoDlg.initItemColumn();
    $('#' + OrderInfoDlg.orderItemsId).bootstrapTable({
        data:OrderInfoDlg.orderItems,
        columns: defaultColunms,		//列数组
        height:500,
    });

    var logColunms = OrderInfoDlg.initLogColumn();
    var logTable = new BSTable(OrderInfoDlg.logId, "/order/orderLogs/"+$("#sn").html(), logColunms);
    logTable.setPaginationType("client");
    logTable.init();
});
