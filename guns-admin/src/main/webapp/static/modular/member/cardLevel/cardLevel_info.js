/**
 * 初始化会员卡等级详情对话框
 */
var CardLevelInfoDlg = {
    cardLevelInfoData : {},
};


/**
 * 清除数据
 */
CardLevelInfoDlg.clearData = function() {
    this.memberCardInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardLevelInfoDlg.set = function(key, val) {
    this.cardLevelInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CardLevelInfoDlg.get = function(key) {
    return $("#" + key).val();
}


/**
 * 收集数据
 */
CardLevelInfoDlg.collectData = function() {
    this.set('id').set('discount').set('integralProp').set('num').set('name');
}

/**
 * 添加提交
 */
CardLevelInfoDlg.addSubmit = function () {
	this.clearData();
	this.collectData();
    var ajax = new $ax(Feng.ctxPath + "/cardLevel/add", function (data) {
        Feng.success("添加成功!");
        window.parent.CardLevel.table.refresh();
        CardLevelInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cardLevelInfoData);
    ajax.start();
}

/**
 * 修改提交
 */
CardLevelInfoDlg.editSubmit = function () {
	this.clearData();
	this.collectData();
    var ajax = new $ax(Feng.ctxPath + "/cardLevel/edit", function (data) {
        Feng.success("修改成功!");
        window.parent.CardLevel.table.refresh();
        CardLevelInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.cardLevelInfoData);
    ajax.start();
}


/**
 * 关闭此对话框
 */
CardLevelInfoDlg.close = function () {
    parent.layer.close(window.parent.CardLevel.layerIndex);
    
}


