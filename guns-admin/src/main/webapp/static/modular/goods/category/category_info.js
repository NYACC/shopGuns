/**
 * 初始化详情对话框
 */
var CategoryInfoDlg = {
    categoryInfoData: {},
    editor: null,
    validateFields: {
        title: {
            validators: {
                notEmpty: {
                    message: '标题不能为空'
                }
            }
        }
    },
};

CategoryInfoDlg.onClickCategory = function (e, treeId, treeNode) {
    $("#citySel").attr("value", instance.getSelectedVal());
    $("#categoryId").attr("value", treeNode.id);
    CategoryInfoDlg.hideDeptSelectTree();
};

/**
 * 显示分类选择的树
 *
 * @returns
 */
CategoryInfoDlg.showCategorySelectTree = function () {
    var cityObj = $("#citySel");
    var cityOffset = $("#citySel").offset();
    $("#menuContent").css({
        left: cityOffset.left + "px",
        top: cityOffset.top + cityObj.outerHeight()+ "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
};

/**
 * 隐藏分类选择的树
 */
CategoryInfoDlg.hideDeptSelectTree = function () {
    $("#menuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
};

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(
            event.target).parents("#menuContent").length > 0)) {
        CategoryInfoDlg.hideDeptSelectTree();
    }
}


/**
 * 清除数据
 */
CategoryInfoDlg.clearData = function () {
    this.categoryInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CategoryInfoDlg.set = function (key, val) {
    this.categoryInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CategoryInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
CategoryInfoDlg.close = function () {
    parent.layer.close(window.parent.Category.layerIndex);
}

/**
 * 收集数据
 */
CategoryInfoDlg.collectData = function () {
	this.set('name').set('num').set('id').set('imgUrl');
}

/**
 * 验证数据是否为空
 */
CategoryInfoDlg.validate = function () {
    $('#noticeInfoForm').data("bootstrapValidator").resetForm();
    $('#noticeInfoForm').bootstrapValidator('validate');
    return $("#noticeInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
CategoryInfoDlg.addSubmit = function () {
	
	this.collectData();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/category/add", function (data) {
        Feng.success("添加成功!");
        CategoryInfoDlg.close();
        window.parent.Category.flash("firstCategory",0);
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.categoryInfoData);
    ajax.set('pid',$("#categoryId").val());
    ajax.start();
}

/**
 * 提交修改
 */
CategoryInfoDlg.editSubmit = function () {

	this.collectData();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/category/edit", function (data) {
        Feng.success("修改成功!");
        CategoryInfoDlg.close();
        window.parent.Category.flash("firstCategory",0);
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.categoryInfoData);
    ajax.set('pid',$("#categoryId").val());
    ajax.start();
}

$(function () {
	Feng.initValidator("tagInfoForm", CategoryInfoDlg.validateFields);
	var ztree = new $ZTree("treeDemo", "/category/tree");
    ztree.bindOnClick(CategoryInfoDlg.onClickCategory);
    ztree.init();
    instance = ztree;
    // 初始化头像上传
    var avatarUp = new $WebUpload("imgUrl",'/category/upload');
    avatarUp.init();
});

