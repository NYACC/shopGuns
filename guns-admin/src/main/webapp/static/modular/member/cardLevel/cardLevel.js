/**
 * 会员卡等级初始化
 */
var CardLevel = {
	id: "CardLevelTable",	//表格id
	seItem: null,		//选中的条目
	table: null,
	layerIndex: -1
};

/**
 * 初始化表格的列
 */
CardLevel.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '等级名称', field: 'name', align: 'center', valign: 'middle', sortable: true},
        {title: '折扣', field: 'discount', align: 'center', valign: 'middle', sortable: true},
        {title: '积分比例', field: 'integralProp', align: 'center', valign: 'middle', sortable: true},
        {title: '排序', field: 'num', align: 'center', valign: 'middle', sortable: true},]
};

/**
 * 点击添加等级
 */
CardLevel.openAdd = function () {
    var index = layer.open({
        type: 2,
        title: '添加等级',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/cardLevel/toAdd'
    });
    //layer.full(index);
    this.layerIndex = index;
};


/**
 * 是否选中
 */
CardLevel.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
    	CardLevel.seItem = selected[0];
        return true;
    }
};

/**
 * 点击修改等级
 */
CardLevel.openDetail = function () {
	if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '修改客户',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/cardLevel/toEdit/' + this.seItem.id
        });
        //layer.full(index);
        this.layerIndex = index;
    }
};

/**
 * 点击删除等级
 */
CardLevel.delete = function () {
	if (this.check()) {
	    var cardLevelId = this.seItem.id;
	    var ajax = new $ax(Feng.ctxPath + "/cardLevel/delete", function (data) {
	        Feng.success("删除成功!");
	        CardLevel.table.refresh();
	    }, function (data) {
	        Feng.error("删除失败!" + data.responseJSON.message + "!");
	    });
	    ajax.set("cardLevelId", cardLevelId);
	    ajax.start();
	}
};


$(function () {
    var defaultColunms = CardLevel.initColumn();
    var table = new BSTable(CardLevel.id, "/cardLevel/list", defaultColunms);
    table.setPaginationType("client");
    table.init();
    CardLevel.table = table;
});
