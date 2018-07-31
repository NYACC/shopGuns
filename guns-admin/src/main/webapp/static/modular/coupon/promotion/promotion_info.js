/**
 * 初始化促销详情对话框
 */
var PromotionInfoDlg = {
    promotionInfoData : {},
    id: "goodsTable",	//表格id
	table: null,
	bindId:"bindTable",
	bindTable:null,
	layerIndex: -1
};


/**
 * 清除数据
 */
PromotionInfoDlg.clearData = function() {
    this.promotionInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PromotionInfoDlg.set = function(key, val) {
    this.promotionInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PromotionInfoDlg.get = function(key) {
    return $("#" + key).val();
}


/**
 * 收集数据
 */
PromotionInfoDlg.collectData = function() {
    this.set('id').set('name').set('startTime').set('endTime').set('model')
    .set('fulfil').set('reduce').set('discount').set('remark').set('couponId');
}

/**
 * 添加提交
 */
PromotionInfoDlg.addSubmit = function () {
	this.clearData();
	this.collectData();
    var ajax = new $ax(Feng.ctxPath + "/promotion/add", function (data) {
        Feng.success("添加成功!");
        window.parent.Promotion.table.refresh();
        PromotionInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set("shopIds",$("#shopIds").val());
    ajax.set(this.promotionInfoData);
    ajax.start();
}

/**
 * 修改提交
 */
PromotionInfoDlg.editSubmit = function () {
	this.clearData();
	this.collectData();
    var ajax = new $ax(Feng.ctxPath + "/promotion/edit", function (data) {
        Feng.success("修改成功!");
        window.parent.Promotion.table.refresh();
        PromotionInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.promotionInfoData);
    ajax.start();
}


/**
 * 关闭此对话框
 */
PromotionInfoDlg.close = function () {
	parent.layer.close(window.parent.Promotion.layerIndex);
}

PromotionInfoDlg.selChange=function(){
	if($("#model").val()=="2"){
		$("#_discount").css('display','block');
		$("#_reduce").css('display','none');
		$("#_coupon").css('display','none');
	}else if($("#model").val()=="1"){
		$("#_discount").css('display','none');
		$("#_reduce").css('display','block');
		$("#_coupon").css('display','none');
	}else{
		$("#_discount").css('display','none');
		$("#_reduce").css('display','none');
		$("#_coupon").css('display','block');
	}
}
PromotionInfoDlg.sendChange=function(){
	if($("#isSend").val()=="true"){
		$("#quantity").val('');
		$("#quantity").attr("readonly","true"); 
	}else{
		$("#quantity").attr("readonly",false); 
	}
}

PromotionInfoDlg.selectShop = function () {
    var index = layer.open({
        type: 2,
        title: '选择门店',
        area: ['300px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/promotion/shop_select'
    });
    this.layerIndex = index;
};

PromotionInfoDlg.onClickCategory = function (e, treeId, treeNode) {
    $("#citySel").attr("value", instance.getSelectedVal());
    $("#categoryId").attr("value", treeNode.id);
    PromotionInfoDlg.hideDeptSelectTree();
};

/**
 * 显示分类选择的树
 *
 * @returns
 */
PromotionInfoDlg.showCategorySelectTree = function () {
    var cityObj = $("#citySel");
    var cityOffset = $("#citySel").offset();
    $("#menuContent").css({
        left: cityOffset.left + "px",
        top:  cityOffset.top + cityObj.outerHeight()+ "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
};


/**
 * 隐藏部门选择的树
 */
PromotionInfoDlg.hideDeptSelectTree = function () {
    $("#menuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
};

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(
            event.target).parents("#menuContent").length > 0)) {
    	PromotionInfoDlg.hideDeptSelectTree();
    }
}

$(function () {
	//初始化商品查询表
    var defaultColunms = PromotionInfoDlg.initColumn();
    var table = new BSTable(PromotionInfoDlg.id, "/promotion/goodsList", defaultColunms);
    table.setPaginationType("client");
    table.init();
    PromotionInfoDlg.table = table;
    
    //初始化促销绑定商品表
    var bindDefaultColunms = PromotionInfoDlg.initBindColumn();
    var bindTable = new BSTable(PromotionInfoDlg.bindId, "/promotion/bindPriceTag/"+$("#promotionId").val(), bindDefaultColunms);
    bindTable.setPaginationType("client");
    bindTable.init();
    PromotionInfoDlg.bindTable = bindTable;
    
    $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
    	PromotionInfoDlg.bindReflash();
    });
});

/**
 * 初始化表格的列
 */
PromotionInfoDlg.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '商品名称', field: 'goodsName', align: 'center', valign: 'middle', sortable: true},
        {title: '规格名称', field: 'productName', align: 'center', valign: 'middle', sortable: true},
        {title: '门店', field: 'shopName', align: 'center', valign: 'middle', sortable: true},
        {title: '操作', field: '', align: 'center', valign: 'middle', sortable: true,formatter : operateFormatter}]
};

function operateFormatter(value, row, index) {
    return '<button id="bindProduct" icon="fa-check" type="button" onclick="PromotionInfoDlg.bind(\''+row.id+'\')" class="btn btn-primary btn-xs">关联</button>';
};

/**
 * 查询
 */
PromotionInfoDlg.search = function () {
    var queryData = {};
    queryData['categoryId'] = $("#categoryId").val();
    queryData['brandId'] = $("#brandId").val();
    queryData['goodsName'] = $("#goodsName").val();
    queryData['shopIds'] = $("#shopIds").val();
    queryData['promotionId'] = $("#promotionId").val();
    PromotionInfoDlg.table.refresh({query: queryData});
};

PromotionInfoDlg.bind = function (priceTagId) {
	 var ajax = new $ax(Feng.ctxPath + "/promotion/bind", function (data) {
	     Feng.success("关联成功!");
	     PromotionInfoDlg.search();
	 }, function (data) {
	     Feng.error("关联失败!" + data.responseJSON.message + "!");
	 });
	 ajax.set('promotionId',$("#promotionId").val());
	 ajax.set('priceTagId',priceTagId);
	 ajax.start();	
}

/**
 * 初始化表格的列
 */
PromotionInfoDlg.initBindColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '商品名称', field: 'goodsName', align: 'center', valign: 'middle', sortable: true},
        {title: '规格名称', field: 'productName', align: 'center', valign: 'middle', sortable: true},
        {title: '门店名称', field: 'storesName', align: 'center', valign: 'middle', sortable: true},
        {title: '操作', field: '', align: 'center', valign: 'middle', sortable: true,formatter : operateBindFormatter}]
};

function operateBindFormatter(value, row, index) {
    return '<button  icon="fa-check" type="button" onclick="PromotionInfoDlg.unbind(\''+row.id+'\')" class="btn btn-primary btn-xs">解除关联</button>';
};

PromotionInfoDlg.unbind = function (priceTagId) {
	 var ajax = new $ax(Feng.ctxPath + "/promotion/unbind", function (data) {
	     Feng.success("解除成功!");
	     PromotionInfoDlg.bindReflash();
	     PromotionInfoDlg.search();
	 }, function (data) {
	     Feng.error("解除失败!" + data.responseJSON.message + "!");
	 });
	 ajax.set('priceTagId',priceTagId);
	 ajax.set('promotionId',$("#promotionId").val());
	 ajax.start();	
}
PromotionInfoDlg.bindReflash = function () {
    PromotionInfoDlg.bindTable.refresh();
}