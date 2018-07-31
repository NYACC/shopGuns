/**
 * 初始化客户详情对话框
 */
var MemberCardInfoDlg = {
    memberCardInfoData : {},
};


/**
 * 清除数据
 */
MemberCardInfoDlg.clearData = function() {
    this.memberCardInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MemberCardInfoDlg.set = function(key, val) {
    this.memberCardInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MemberCardInfoDlg.get = function(key) {
    return $("#" + key).val();
}


/**
 * 收集数据
 */
MemberCardInfoDlg.collectData = function() {
    this.set('id').set('cardLevel').set('cardAmount').set('memberId').set('memberName');
}

/**
 * 添加提交
 */
MemberCardInfoDlg.addSubmit = function () {
	this.clearData();
	this.collectData();
    var ajax = new $ax(Feng.ctxPath + "/memberCard/add", function (data) {
        Feng.success("添加成功!");
        window.parent.MemberCard.table.refresh();
        MemberCardInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.memberCardInfoData);
    ajax.start();
}

/**
 * 修改提交
 */
MemberCardInfoDlg.editSubmit = function () {
	this.clearData();
	this.collectData();
    var ajax = new $ax(Feng.ctxPath + "/memberCard/edit", function (data) {
        Feng.success("修改成功!");
        window.parent.MemberCard.table.refresh();
        MemberCardInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.memberCardInfoData);
    ajax.start();
}

/**
 * 关闭此对话框
 */
MemberCardInfoDlg.close = function () {
    parent.layer.close(window.parent.MemberCard.layerIndex);
    
}


