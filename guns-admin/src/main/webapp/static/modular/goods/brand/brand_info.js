/**
 * 初始化品牌详情对话框
 */
var BrandInfoDlg = {
    brandInfoData : {},
    zTreeInstance : null,
    validateFields: {
        	name: {
            validators: {
                notEmpty: {
                    message: '品牌名称不能为空'
                }
            }
        },
        logoPath: {
            validators: {
                notEmpty: {
                    message: '品牌logo不能为空'
                }
            }
        },
        num: {
            validators: {
                notEmpty: {
                    message: '品牌序号不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
BrandInfoDlg.clearData = function() {
    this.brandInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BrandInfoDlg.set = function(key, val) {
    this.brandInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
BrandInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
BrandInfoDlg.close = function() {
    parent.layer.close(window.parent.Brand.layerIndex);
}

/**
 * 收集数据
 */
BrandInfoDlg.collectData = function() {
    this.set('id').set('name').set('introduce').set('avatar').set('num');
}

/**
 * 验证数据是否为空
 */
BrandInfoDlg.validate = function () {
    $('#brandInfoForm').data("bootstrapValidator").resetForm();
    $('#brandInfoForm').bootstrapValidator('validate');
    return $("#brandInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 提交添加品牌
 */
BrandInfoDlg.addSubmit = function() {
    this.clearData();
    var markupStr = CKEDITOR.instances.htmlContentT.getData();
    $('#introduce').val(markupStr);
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/brand/add", function(data){
    	console.log(data);
        Feng.success("添加成功!");
        window.parent.Brand.table.refresh();
        BrandInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.brandInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
BrandInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/brand/edit", function(data){
        Feng.success("修改成功!");
        window.parent.Brand.table.refresh();
        BrandInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.brandInfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("brandInfoForm", BrandInfoDlg.validateFields);
    // 初始化头像上传
    var avatarUp = new $WebUpload("avatar",'/brand/upload');
    avatarUp.init();
});
