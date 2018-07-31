var BindInfoDlg = {
    id: "goodsTable",	//表格id
	table: null,
	bindId:"bindTable",
	bindTable:null,
	layerIndex: -1
};

BindInfoDlg.selectShop = function () {
    var index = layer.open({
        type: 2,
        title: '选择门店',
        area: ['300px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/advertising/shop_select'
    });
    this.layerIndex = index;
};

BindInfoDlg.onClickCategory = function (e, treeId, treeNode) {
    $("#citySel").attr("value", instance.getSelectedVal());
    $("#categoryId").attr("value", treeNode.id);
    BindInfoDlg.hideDeptSelectTree();
};

/**
 * 显示分类选择的树
 *
 * @returns
 */
BindInfoDlg.showCategorySelectTree = function () {
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
BindInfoDlg.hideDeptSelectTree = function () {
    $("#menuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
};

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(
            event.target).parents("#menuContent").length > 0)) {
    	BindInfoDlg.hideDeptSelectTree();
    }
}

$(function () {
	//初始化商品查询表
    var defaultColunms = BindInfoDlg.initColumn();
    var table = new BSTable(BindInfoDlg.id, "/advertising/goodsList", defaultColunms);
    table.setPaginationType("client");
    table.init();
    BindInfoDlg.table = table;
    
    //初始化促销绑定商品表
    var bindDefaultColunms = BindInfoDlg.initBindColumn();
    var bindTable = new BSTable(BindInfoDlg.bindId, "/advertising/bindPriceTag/"+$("#advertisingId").val(), bindDefaultColunms);
    bindTable.setPaginationType("client");
    bindTable.init();
    BindInfoDlg.bindTable = bindTable;
    
    $('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
    	BindInfoDlg.bindReflash();
    });
});

/**
 * 初始化表格的列
 */
BindInfoDlg.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '商品名称', field: 'goodsName', align: 'center', valign: 'middle', sortable: true},
        {title: '规格名称', field: 'productName', align: 'center', valign: 'middle', sortable: true},
        {title: '门店', field: 'shopName', align: 'center', valign: 'middle', sortable: true},
        {title: '操作', field: '', align: 'center', valign: 'middle', sortable: true,formatter : operateFormatter}]
};

function operateFormatter(value, row, index) {
    return '<button id="bindProduct" icon="fa-check" type="button" onclick="BindInfoDlg.bind(\''+row.id+'\')" class="btn btn-primary btn-xs">关联</button>';
};

/**
 * 查询
 */
BindInfoDlg.search = function () {
    var queryData = {};
    queryData['categoryId'] = $("#categoryId").val();
    queryData['brandId'] = $("#brandId").val();
    queryData['goodsName'] = $("#goodsName").val();
    queryData['shopIds'] = $("#shopIds").val();
    queryData['advertisingId'] = $("#advertisingId").val();
    BindInfoDlg.table.refresh({query: queryData});
};

BindInfoDlg.bind = function (priceTagId) {
	 var ajax = new $ax(Feng.ctxPath + "/advertising/bind", function (data) {
	     Feng.success("关联成功!");
	     BindInfoDlg.search();
	 }, function (data) {
	     Feng.error("关联失败!" + data.responseJSON.message + "!");
	 });
	 ajax.set('advertisingId',$("#advertisingId").val());
	 ajax.set('priceTagId',priceTagId);
	 ajax.start();	
}

/**
 * 初始化表格的列
 */
BindInfoDlg.initBindColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '商品名称', field: 'goodsName', align: 'center', valign: 'middle', sortable: true},
        {title: '规格名称', field: 'productName', align: 'center', valign: 'middle', sortable: true},
        {title: '门店名称', field: 'storesName', align: 'center', valign: 'middle', sortable: true},
        {title: '操作', field: '', align: 'center', valign: 'middle', sortable: true,formatter : operateBindFormatter}]
};

function operateBindFormatter(value, row, index) {
    return '<button  icon="fa-check" type="button" onclick="BindInfoDlg.unbind(\''+row.id+'\')" class="btn btn-primary btn-xs">解除关联</button>';
};

BindInfoDlg.unbind = function (priceTagId) {
	 var ajax = new $ax(Feng.ctxPath + "/advertising/unbind", function (data) {
	     Feng.success("解除成功!");
	     BindInfoDlg.bindReflash();
	     BindInfoDlg.search();
	 }, function (data) {
	     Feng.error("解除失败!" + data.responseJSON.message + "!");
	 });
	 ajax.set('priceTagId',priceTagId);
	 ajax.set('advertisingId',$("#advertisingId").val());
	 ajax.start();	
}
BindInfoDlg.bindReflash = function () {
	var queryData = {};
    BindInfoDlg.bindTable.refresh({query: queryData});
}