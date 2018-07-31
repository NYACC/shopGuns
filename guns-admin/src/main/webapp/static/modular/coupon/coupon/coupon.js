/**
 * 优惠卷初始化
 */
var Coupon = {
	id: "CouponTable",	//表格id
	seItem: null,		//选中的条目
	table: null,
	layerIndex: -1
};

/**
 * 初始化表格的列
 */
Coupon.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '名称', field: 'name', align: 'center', valign: 'middle', sortable: true},
        {title: '所属门店', field: 'shopName', align: 'center', valign: 'middle', sortable: true},
        {title: '总数量', field: 'amountNum', align: 'center', valign: 'middle', sortable: true},
        {title: '已领取数量', field: 'receivedNum', align: 'center', valign: 'middle', sortable: true},
        {title: '未领取数量', field: 'unreceiveNum', align: 'center', valign: 'middle', sortable: true},
        {title: '已使用数量', field: 'usedNum', align: 'center', valign: 'middle', sortable: true},
        {title: '操作', field: '', align: 'center', valign: 'middle', sortable: true,formatter : operateFormatter}]
};

function operateFormatter(value, row, index) {
    return '<button id="import" icon="fa-check" type="button" onclick="Coupon.openImport(\''+row.id+'\')" class="btn btn-primary btn-xs">导入优惠码</button>';
};

Coupon.openImport = function(id){
    var index = layer.open({
        type: 2,
        title: '导入优惠码',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/coupon/toImport/' + id
    });
    //layer.full(index);
    this.layerIndex = index;
    
}

/**
 * 点击添加
 */
Coupon.openAdd = function () {
    var index = layer.open({
        type: 2,
        title: '添加优惠卷',
        area: ['800px', '440px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/coupon/toAdd'
    });
    //layer.full(index);
    this.layerIndex = index;
};


/**
 * 是否选中
 */
Coupon.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
    	Coupon.seItem = selected[0];
        return true;
    }
};

/**
 * 是否可以操作
 */
Coupon.isOperable = function () {
	if(Coupon.seItem.amountNum>0){
		Feng.info("优惠卷已被生成，无法修改和删除！");
        return false;
	}else{
        return true;
    }
};

/**
 * 点击修改
 */
Coupon.openDetail = function () {
	if (this.check()&&this.isOperable()) {
        var index = layer.open({
            type: 2,
            title: '修改客户',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/coupon/toEdit/' + this.seItem.id
        });
        //layer.full(index);
        this.layerIndex = index;
    }
};

/**
 * 点击生成优惠卷
 */
Coupon.openProduce = function () {
	if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '生成优惠卷',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/coupon/toProduce/' + this.seItem.id
        });
        //layer.full(index);
        this.layerIndex = index;
    }
};

/**
 * 点击导出优惠卷
 */
Coupon.openExport = function () {
	if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '导出优惠卷',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/coupon/toExport/' + this.seItem.id
        });
        //layer.full(index);
        this.layerIndex = index;
    }
};

/**
 * 点击删除
 */
Coupon.delete = function () {
	if (this.check()&&this.isOperable()) {
	    var couponId = this.seItem.id;
	    var ajax = new $ax(Feng.ctxPath + "/coupon/delete", function (data) {
	        Feng.success("删除成功!");
	        Coupon.table.refresh();
	    }, function (data) {
	        Feng.error("删除失败!" + data.responseJSON.message + "!");
	    });
	    ajax.set("couponId", couponId);
	    ajax.start();
	}
};


$(function () {
    var defaultColunms = Coupon.initColumn();
    var table = new BSTable(Coupon.id, "/coupon/list", defaultColunms);
    table.setPaginationType("client");
    table.init();
    Coupon.table = table;
});
