/**
 * 初始化客户详情对话框
 */
var MemberInfoDlg = {
	memberInfoData : {},
};


/**
 * 清除数据
 */
MemberInfoDlg.clearData = function() {
    this.memberInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MemberInfoDlg.set = function(key, val) {
    this.memberInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
MemberInfoDlg.get = function(key) {
    return $("#" + key).val();
}


/**
 * 收集数据
 */
MemberInfoDlg.collectData = function() {
    this.set('id').set('name').set('phoneNum').set('sex').set('status').set('isCard').set('cardSn').set('cardLevel');
}

/**
 * 添加提交
 */
MemberInfoDlg.addSubmit = function () {
	this.clearData();
	this.collectData();
    var ajax = new $ax(Feng.ctxPath + "/member/add", function (data) {
        Feng.success("添加成功!");
        window.parent.Member.table.refresh();
        MemberInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.memberInfoData);
    ajax.start();
}

/**
 * 修改提交
 */
MemberInfoDlg.editSubmit = function () {
	this.clearData();
	this.collectData();
    var ajax = new $ax(Feng.ctxPath + "/member/edit", function (data) {
        Feng.success("修改成功!");
        window.parent.Member.table.refresh();
        MemberInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.memberInfoData);
    ajax.start();
}

MemberInfoDlg.selChange=function(){
	if($("#isCard").val()=="true"){
		$("#level").css('display','block');
	}else{
		$("#level").css('display','none'); 
	}
}

/**
 * 关闭此对话框
 */
MemberInfoDlg.close = function () {
    parent.layer.close(window.parent.Member.layerIndex);
    
}


