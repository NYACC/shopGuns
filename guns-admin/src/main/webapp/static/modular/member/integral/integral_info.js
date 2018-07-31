/**
 * 初始化积分详情对话框
 */
var IntegralInfoDlg = {
    integralInfoData : {},
};


/**
 * 清除数据
 */
IntegralInfoDlg.clearData = function() {
    this.integralInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
IntegralInfoDlg.set = function(key, val) {
    this.integralInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
IntegralInfoDlg.get = function(key) {
    return $("#" + key).val();
}


/**
 * 收集数据
 */
IntegralInfoDlg.collectData = function() {
    this.set('id').set('memberName').set('phoneNum').set('integralValue');
}


/**
 * 修改提交
 */
IntegralInfoDlg.editSubmit = function () {
	this.clearData();
	this.collectData();
    var ajax = new $ax(Feng.ctxPath + "/integral/edit", function (data) {
        Feng.success("修改成功!");
        window.parent.Integral.table.refresh();
        IntegralInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.integralInfoData);
    ajax.start();
}

/**
 * 关闭此对话框
 */
IntegralInfoDlg.close = function () {
    parent.layer.close(window.parent.Integral.layerIndex);
    
}


