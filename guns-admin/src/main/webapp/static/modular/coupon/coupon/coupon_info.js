/**
 * 初始化优惠卷详情对话框
 */
var CouponInfoDlg = {
    couponInfoData : {},
};


/**
 * 清除数据
 */
CouponInfoDlg.clearData = function() {
    this.couponInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CouponInfoDlg.set = function(key, val) {
    this.couponInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
CouponInfoDlg.get = function(key) {
    return $("#" + key).val();
}


/**
 * 收集数据
 */
CouponInfoDlg.collectData = function() {
    this.set('id').set('name').set('useStart').set('useEnd').set('receiveStart').set('receiveEnd').set('model')
    .set('fulfil').set('reduce').set('discount').set('remark');
}

/**
 * 添加提交
 */
CouponInfoDlg.addSubmit = function () {
	this.clearData();
	this.collectData();
    var ajax = new $ax(Feng.ctxPath + "/coupon/add", function (data) {
        Feng.success("添加成功!");
        window.parent.Coupon.table.refresh();
        CouponInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.couponInfoData);
    ajax.set("shopIds",$("#shopIds").val());
    ajax.start();
}

/**
 * 修改提交
 */
CouponInfoDlg.editSubmit = function () {
	this.clearData();
	this.collectData();
    var ajax = new $ax(Feng.ctxPath + "/coupon/edit", function (data) {
        Feng.success("修改成功!");
        window.parent.Coupon.table.refresh();
        CouponInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.couponInfoData);
    ajax.start();
}

/**
 * 提交生成
 */
CouponInfoDlg.produce = function () {
    var ajax = new $ax(Feng.ctxPath + "/coupon/produce", function (data) {
        Feng.success("生成成功!");
        window.parent.Coupon.table.refresh();
        CouponInfoDlg.close();
    }, function (data) {
        Feng.error("生成失败!" + data.responseJSON.message + "!");
    });
    ajax.set("couponId",$("#couponId").val());
    ajax.set("quantity",$("#quantity").val());
    ajax.set("isSend",$("#isSend").val());
    ajax.start();
}
/**
 * 提交导出
 */
CouponInfoDlg.export = function () {
	location.href=Feng.ctxPath + "/coupon/export?couponId="+$("#couponId").val()
	+"&quantity="+$("#quantity").val();
}

/**
 * 关闭此对话框
 */
CouponInfoDlg.close = function () {
	parent.layer.close(window.parent.Coupon.layerIndex);
}

CouponInfoDlg.selChange=function(){
	if($("#model").val()=="2"){
		$("#_discount").css('display','block');
		$("#_reduce").css('display','none');
	}else{
		$("#_discount").css('display','none');
		$("#_reduce").css('display','block');
	}
}
CouponInfoDlg.sendChange=function(){
	if($("#isSend").val()=="true"){
		$("#quantity").val('');
		$("#quantity").attr("readonly","true"); 
	}else{
		$("#quantity").attr("readonly",false); 
	}
}

CouponInfoDlg.import=function(){
	var options = {  
            // 规定把请求发送到那个URL  
            url: "/coupon/importCode",  
            // 请求方式  
            type: "post",  
            // 服务器响应的数据类型  
            dataType: "json",  
            // 请求成功时执行的回调函数  
            success: function(data, status, xhr) {
            	window.parent.Coupon.table.refresh();
            	CouponInfoDlg.close();
            }  
    };
    $("#form1").ajaxSubmit(options);
}

CouponInfoDlg.selectShop = function () {
    var index = layer.open({
        type: 2,
        title: '选择门店',
        area: ['300px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/coupon/shop_select'
    });
    this.layerIndex = index;
};

CouponInfoDlg.onClickCategory = function (e, treeId, treeNode) {
    $("#citySel").attr("value", instance.getSelectedVal());
    $("#categoryId").attr("value", treeNode.id);
    CouponInfoDlg.hideDeptSelectTree();
};

/**
 * 显示树
 *
 * @returns
 */
CouponInfoDlg.showCategorySelectTree = function () {
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
CouponInfoDlg.hideDeptSelectTree = function () {
    $("#menuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
};

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(
            event.target).parents("#menuContent").length > 0)) {
    	CouponInfoDlg.hideDeptSelectTree();
    }
}