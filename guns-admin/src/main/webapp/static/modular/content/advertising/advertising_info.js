/**
 * 初始化优惠卷详情对话框
 */
var AdvertisingInfoDlg = {
    advertisingInfoData : {},
};


/**
 * 清除数据
 */
AdvertisingInfoDlg.clearData = function() {
    this.advertisingInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AdvertisingInfoDlg.set = function(key, val) {
    this.advertisingInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
AdvertisingInfoDlg.get = function(key) {
    return $("#" + key).val();
}


/**
 * 收集数据
 */
AdvertisingInfoDlg.collectData = function() {
    this.set('id').set('name').set('picture').set('link').set('positionId').set('shopId').set('sequence');
}

/**
 * 添加提交
 */
AdvertisingInfoDlg.addSubmit = function () {
	this.clearData();
	this.collectData();
    var ajax = new $ax(Feng.ctxPath + "/advertising/add", function (data) {
        Feng.success("添加成功!");
        window.parent.Advertising.table.refresh();
        AdvertisingInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.advertisingInfoData);
    ajax.start();
}

/**
 * 修改提交
 */
AdvertisingInfoDlg.editSubmit = function () {
	this.clearData();
	this.collectData();
    var ajax = new $ax(Feng.ctxPath + "/advertising/edit", function (data) {
        Feng.success("修改成功!");
        window.parent.Advertising.table.refresh();
        AdvertisingInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.advertisingInfoData);
    ajax.start();
}



/**
 * 关闭此对话框
 */
AdvertisingInfoDlg.close = function () {
	parent.layer.close(window.parent.Advertising.layerIndex);
}

$(function() {
    // 初始化头像上传
    var avatarUp = new $WebUpload("picture",'/advertising/upload?type=' + $("#type").val());
    avatarUp.init();
});

AdvertisingInfoDlg.positionChange = function(){
	$("#tip").text(tips[$('#positionId option:selected') .val()-1]);
	$("#type").val($('#positionId option:selected') .val());
}


