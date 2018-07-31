/**
 * 初始化通知详情对话框
 */
var TagInfoDlg = {
    tagInfoData: {},
    editor: null,
    validateFields: {
        title: {
            validators: {
                notEmpty: {
                    message: '标题不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
TagInfoDlg.clearData = function () {
    this.tagInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TagInfoDlg.set = function (key, val) {
    this.tagInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
TagInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
TagInfoDlg.close = function () {
    parent.layer.close(window.parent.Tag.layerIndex);
}

/**
 * 收集数据
 */
TagInfoDlg.collectData = function () {
    this.set('name').set('sequence').set('id');
}

/**
 * 验证数据是否为空
 */
TagInfoDlg.validate = function () {
    $('#noticeInfoForm').data("bootstrapValidator").resetForm();
    $('#noticeInfoForm').bootstrapValidator('validate');
    return $("#noticeInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
TagInfoDlg.addSubmit = function () {
	
    this.clearData();
    this.collectData();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/tag/add", function (data) {
        Feng.success("添加成功!");
        window.parent.Tag.table.refresh();
        TagInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.tagInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
TagInfoDlg.editSubmit = function () {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/tag/edit", function (data) {
        Feng.success("修改成功!");
        window.parent.Tag.table.refresh();
        TagInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.tagInfoData);
    ajax.start();
}

$(function () {
	Feng.initValidator("tagInfoForm", TagInfoDlg.validateFields);
    //初始化编辑器
    var E = window.wangEditor;
    var editor = new E('#editor');
    editor.create();
    editor.txt.html($("#contentVal").val());
    TagInfoDlg.editor = editor;
});
