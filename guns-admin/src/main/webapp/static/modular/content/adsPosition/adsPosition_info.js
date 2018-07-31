/**
 * 初始化优惠卷详情对话框
 */
var AdsPositionInfoDlg = {
    adsPositionInfoData : {},
};


/**
 * 清除数据
 */
AdsPositionInfoDlg.clearData = function() {
    this.adsPositionInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AdsPositionInfoDlg.set = function(key, val) {
    this.adsPositionInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AdsPositionInfoDlg.get = function(key) {
    return $("#" + key).val();
}


/**
 * 收集数据
 */
AdsPositionInfoDlg.collectData = function() {
    this.set('id').set('name').set('tip');
}

/**
 * 添加提交
 */
AdsPositionInfoDlg.addSubmit = function () {
	this.clearData();
	this.collectData();
    var ajax = new $ax(Feng.ctxPath + "/adsPosition/add", function (data) {
        Feng.success("添加成功!");
        window.parent.AdsPosition.table.refresh();
        AdsPositionInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.adsPositionInfoData);
    ajax.start();
}

/**
 * 修改提交
 */
AdsPositionInfoDlg.editSubmit = function () {
	this.clearData();
	this.collectData();
    var ajax = new $ax(Feng.ctxPath + "/adsPosition/edit", function (data) {
        Feng.success("修改成功!");
        window.parent.AdsPosition.table.refresh();
        AdsPositionInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.adsPositionInfoData);
    ajax.start();
}



/**
 * 关闭此对话框
 */
AdsPositionInfoDlg.close = function () {
	parent.layer.close(window.parent.AdsPosition.layerIndex);
}



