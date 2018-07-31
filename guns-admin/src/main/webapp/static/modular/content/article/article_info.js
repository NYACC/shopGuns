/**
 * 初始化优惠卷详情对话框
 */
var ArticleInfoDlg = {
    couponInfoData : {},
};


/**
 * 清除数据
 */
ArticleInfoDlg.clearData = function() {
    this.couponInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ArticleInfoDlg.set = function(key, val) {
    this.couponInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ArticleInfoDlg.get = function(key) {
    return $("#" + key).val();
}


/**
 * 收集数据
 */
ArticleInfoDlg.collectData = function() {
    this.set('id').set('title').set('shopIds');
    this.couponInfoData["content"]=CKEDITOR.instances.htmlContentT.getData();
}

/**
 * 添加提交
 */
ArticleInfoDlg.addSubmit = function () {
	this.clearData();
	this.collectData();
    var ajax = new $ax(Feng.ctxPath + "/article/add", function (data) {
        Feng.success("添加成功!");
        window.parent.Article.table.refresh();
        ArticleInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.couponInfoData);
    ajax.start();
}

/**
 * 修改提交
 */
ArticleInfoDlg.editSubmit = function () {
	this.clearData();
	this.collectData();
    var ajax = new $ax(Feng.ctxPath + "/article/edit", function (data) {
        Feng.success("修改成功!");
        window.parent.Article.table.refresh();
        ArticleInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.couponInfoData);
    ajax.start();
}



/**
 * 关闭此对话框
 */
ArticleInfoDlg.close = function () {
	parent.layer.close(window.parent.Article.layerIndex);
}

ArticleInfoDlg.selectShop = function () {
    var index = layer.open({
        type: 2,
        title: '选择门店',
        area: ['300px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/article/shop_select'
    });
    this.layerIndex = index;
};

ArticleInfoDlg.onClickCategory = function (e, treeId, treeNode) {
    $("#citySel").attr("value", instance.getSelectedVal());
    $("#categoryId").attr("value", treeNode.id);
    ArticleInfoDlg.hideDeptSelectTree();
};

/**
 * 显示树
 *
 * @returns
 */
ArticleInfoDlg.showCategorySelectTree = function () {
    var cityObj = $("#citySel");
    var cityOffset = $("#citySel").offset();
    $("#menuContent").css({
        left: cityOffset.left + "px",
        top:  cityOffset.top + cityObj.outerHeight()+ "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
};


/**
 * 隐藏树
 */
ArticleInfoDlg.hideDeptSelectTree = function () {
    $("#menuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
};

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(
            event.target).parents("#menuContent").length > 0)) {
    	ArticleInfoDlg.hideDeptSelectTree();
    }
}
