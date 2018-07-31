/**
 * 促销初始化
 */
var Promotion = {
	id: "PromotionTable",	//表格id
	seItem: null,		//选中的条目
	table: null,
	layerIndex: -1
};

/**
 * 初始化表格的列
 */
Promotion.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '促销名称', field: 'name', align: 'center', valign: 'middle', sortable: true},
        {title: '所属门店', field: 'shopName', align: 'center', valign: 'middle', sortable: true},
        {title: '开始时间', field: 'startTime', align: 'center', valign: 'middle', sortable: true},
        {title: '结束时间', field: 'endTime', align: 'center', valign: 'middle', sortable: true},
        {title: '状态', field: 'statusName', align: 'center', valign: 'middle', sortable: true},
        {title: '操作', field: '', align: 'center', valign: 'middle', sortable: true,formatter : operateFormatter}]
};

function operateFormatter(value, row, index) {
    return '<button id="bindProduct" icon="fa-check" type="button" onclick="Promotion.openBind(\''+row.id+'\')" class="btn btn-primary btn-xs">关联商品</button>';
};

Promotion.openBind = function(id){
    var index = layer.open({
        type: 2,
        title: '绑定促销关联',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/promotion/toBind/' + id
    });
    layer.full(index);
    this.layerIndex = index;
    
}

/**
 * 点击添加
 */
Promotion.openAdd = function () {
    var index = layer.open({
        type: 2,
        title: '添加促销',
        area: ['800px', '440px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/promotion/toAdd'
    });
    //layer.full(index);
    this.layerIndex = index;
};


/**
 * 是否选中
 */
Promotion.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
    	Promotion.seItem = selected[0];
        return true;
    }
};

/**
 * 是否可以操作
 */
Promotion.isOperable = function () {
	if(new Date(Promotion.seItem.startTime)<new Date()){
		Feng.info("该促销已经开始，无法修改和删除！");
        return false;
	}else{
        return true;
    }
};

/**
 * 点击修改
 */
Promotion.openDetail = function () {
	if (this.check()&&this.isOperable()) {
        var index = layer.open({
            type: 2,
            title: '修改客户',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/promotion/toEdit/' + this.seItem.id
        });
        //layer.full(index);
        this.layerIndex = index;
    }
};



/**
 * 点击删除
 */
Promotion.disable = function () {
	if (this.check()&&this.isOperable()) {
		if((Promotion.seItem.status==1)){
			 var promotionId = this.seItem.id;
			 var ajax = new $ax(Feng.ctxPath + "/promotion/disable", function (data) {
			     Feng.success("停用成功!");
			     Promotion.table.refresh();
			 }, function (data) {
			     Feng.error("停用失败!" + data.responseJSON.message + "!");
			 });
			 ajax.set("id", promotionId);
			 ajax.start();
		}else{
			Feng.info("只有状态为“进行中”，才能停用");
		}
	}
};

/**
 * 点击删除
 */
Promotion.delete = function () {
	if (this.check()&&this.isOperable()) {
	    var promotionId = this.seItem.id;
	    var ajax = new $ax(Feng.ctxPath + "/promotion/delete", function (data) {
	        Feng.success("删除成功!");
	        Promotion.table.refresh();
	    }, function (data) {
	        Feng.error("删除失败!" + data.responseJSON.message + "!");
	    });
	    ajax.set("promotionId", promotionId);
	    ajax.start();
	}
};

/**
 * 查询列表
 */
Promotion.search = function () {
    var queryData = {};
    queryData['name'] = $("#name").val();
    Promotion.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Promotion.initColumn();
    var table = new BSTable(Promotion.id, "/promotion/list", defaultColunms);
    table.setPaginationType("client");
    table.init();
    Promotion.table = table;
});
