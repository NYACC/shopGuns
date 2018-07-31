/**
 * 初始化面额详情对话框
 */
var DenominationInfoDlg = {
	denominationInfoData : {},
};


/**
 * 清除数据
 */
DenominationInfoDlg.clearData = function() {
    this.denominationInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DenominationInfoDlg.set = function(key, val) {
    this.denominationInfoData[key] = (typeof va == "undefined") ? $("#" + key).val() : va;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
DenominationInfoDlg.get = function(key) {
    return $("#" + key).val();
}


/**
 * 收集数据
 */
DenominationInfoDlg.collectData = function() {
    this.set('id').set('multiple').set('value').set('times');
}

/**
 * 添加提交
 */
DenominationInfoDlg.addSubmit = function () {
	this.clearData();
	this.collectData();
    var ajax = new $ax(Feng.ctxPath + "/denomination/add", function (data) {
        Feng.success("添加成功!");
        window.parent.Denomination.table.refresh();
        DenominationInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.denominationInfoData);
    ajax.start();
}

/**
 * 修改提交
 */
DenominationInfoDlg.editSubmit = function () {
	this.clearData();
	this.collectData();
    var ajax = new $ax(Feng.ctxPath + "/denomination/edit", function (data) {
        Feng.success("修改成功!");
        window.parent.Denomination.table.refresh();
        DenominationInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.denominationInfoData);
    ajax.start();
}


/**
 * 关闭此对话框
 */
DenominationInfoDlg.close = function () {
    parent.layer.close(window.parent.Denomination.layerIndex);
    
}


