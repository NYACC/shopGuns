/**
 * 初始化余额详情对话框
 */
var BalanceInfoDlg = {
    balanceInfoData : {},
};


/**
 * 清除数据
 */
BalanceInfoDlg.clearData = function() {
    this.balanceInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BalanceInfoDlg.set = function(key, val) {
    this.balanceInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BalanceInfoDlg.get = function(key) {
    return $("#" + key).val();
}


/**
 * 收集数据
 */
BalanceInfoDlg.collectData = function() {
    this.set('id').set('money');
}

/**
 * 添加提交
 */
BalanceInfoDlg.recharge = function () {
	this.clearData();
	this.collectData();
    var ajax = new $ax(Feng.ctxPath + "/balance/recharge", function (data) {
        Feng.success("充值成功!");
        window.parent.Balance.table.refresh();
        BalanceInfoDlg.close();
    }, function (data) {
        Feng.error("充值失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.balanceInfoData);
    ajax.start();
}


/**
 * 关闭此对话框
 */
BalanceInfoDlg.close = function () {
    parent.layer.close(window.parent.Balance.layerIndex);
    
}


