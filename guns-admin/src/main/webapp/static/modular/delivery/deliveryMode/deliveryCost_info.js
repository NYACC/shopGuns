/**
 * 初始化配送方式详情对话框
 */
var DeliveryCostInfoDlg = {
    deliveryCostInfoData : {},
};


/**
 * 清除数据
 */
DeliveryCostInfoDlg.clearData = function() {
    this.deliveryCostInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DeliveryCostInfoDlg.set = function(key, val) {
    this.deliveryCostInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DeliveryCostInfoDlg.get = function(key) {
    return $("#" + key).val();
}


/**
 * 收集数据
 */
DeliveryCostInfoDlg.collectData = function() {
    this.set('id').set('ykg').set('startPrice').set('addedWeight').set('addedPrice');
}


/**
 * 修改提交
 */
DeliveryCostInfoDlg.editSubmit = function () {
	this.clearData();
	this.collectData();
    var ajax = new $ax(Feng.ctxPath + "/deliveryMode/editCost", function (data) {
        Feng.success("修改成功!");
        window.parent.DeliveryCost.table.refresh();
        DeliveryCostInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.deliveryCostInfoData);
    ajax.start();
}



/**
 * 关闭此对话框
 */
DeliveryCostInfoDlg.close = function () {
	parent.layer.close(window.parent.DeliveryCost.layerIndex);
}

DeliveryCostInfoDlg.import=function(){
	var options = {  
            // 规定把请求发送到那个URL  
            url: "/deliveryMode/importCost",  
            // 请求方式  
            type: "post",  
            // 服务器响应的数据类型  
            dataType: "json",  
            // 请求成功时执行的回调函数  
            success: function(data, status, xhr) {
            	Feng.success("导入成功!");
            	DeliveryCostInfoDlg.close();
            }  
    };
    $("#form1").ajaxSubmit(options);
}

