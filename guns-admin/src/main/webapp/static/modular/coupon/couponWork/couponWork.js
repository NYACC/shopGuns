/**
 * 定时任务初始化
 */
var CouponWork = {
	id: "CouponWorkTable",	//表格id
	seItem: null,		//选中的条目
	table: null,
	layerIndex: -1
};

/**
 * 初始化表格的列
 */
CouponWork.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '任务时间', field: 'name', align: 'center', valign: 'middle', sortable: true},
        {title: '生产的卡卷', field: 'amountNum', align: 'center', valign: 'middle', sortable: true},
        {title: '数量', field: 'receivedNum', align: 'center', valign: 'middle', sortable: true},
        {title: '是否执行', field: 'unreceiveNum', align: 'center', valign: 'middle', sortable: true},
        {title: '创建时间', field: '', align: 'center', valign: 'middle', sortable: true,formatter : operateFormatter}]
};

function operateFormatter(value, row, index) {
    return '<button id="import" icon="fa-check" type="button" onclick="CouponWork.openImport(\''+row.id+'\')" class="btn btn-primary btn-xs">导入优惠码</button>';
};

CouponWork.openImport = function(id){
    var index = layer.open({
        type: 2,
        title: '导入优惠码',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/CouponWork/toImport/' + id
    });
    //layer.full(index);
    this.layerIndex = index;
    
}

/**
 * 点击添加
 */
CouponWork.openAdd = function () {
    var index = layer.open({
        type: 2,
        title: '添加定时任务',
        area: ['800px', '440px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/CouponWork/toAdd'
    });
    //layer.full(index);
    this.layerIndex = index;
};


/**
 * 是否选中
 */
CouponWork.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
    	CouponWork.seItem = selected[0];
        return true;
    }
};

/**
 * 是否可以操作
 */
CouponWork.isOperable = function () {
	if(CouponWork.seItem.amountNum>0){
		Feng.info("定时任务已被生成，无法修改和删除！");
        return false;
	}else{
        return true;
    }
};

/**
 * 点击修改
 */
CouponWork.openDetail = function () {
	if (this.check()&&this.isOperable()) {
        var index = layer.open({
            type: 2,
            title: '修改定时任务',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/CouponWork/toEdit/' + this.seItem.id
        });
        //layer.full(index);
        this.layerIndex = index;
    }
};

/**
 * 点击生成定时任务
 */
CouponWork.openProduce = function () {
	if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '生成定时任务',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/CouponWork/toProduce/' + this.seItem.id
        });
        //layer.full(index);
        this.layerIndex = index;
    }
};

/**
 * 点击导出定时任务
 */
CouponWork.openExport = function () {
	if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '导出定时任务',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/CouponWork/toExport/' + this.seItem.id
        });
        //layer.full(index);
        this.layerIndex = index;
    }
};

/**
 * 点击删除
 */
CouponWork.delete = function () {
	if (this.check()&&this.isOperable()) {
	    var CouponWorkId = this.seItem.id;
	    var ajax = new $ax(Feng.ctxPath + "/CouponWork/delete", function (data) {
	        Feng.success("删除成功!");
	        CouponWork.table.refresh();
	    }, function (data) {
	        Feng.error("删除失败!" + data.responseJSON.message + "!");
	    });
	    ajax.set("CouponWorkId", CouponWorkId);
	    ajax.start();
	}
};


$(function () {
    var defaultColunms = CouponWork.initColumn();
    var table = new BSTable(CouponWork.id, "/couponWork/list", defaultColunms);
    table.setPaginationType("client");
    table.init();
    CouponWork.table = table;
});
