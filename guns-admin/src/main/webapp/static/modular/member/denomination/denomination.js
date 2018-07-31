/**
 * 面额初始化
 */
var Denomination = {
	id: "DenominationTable",	//表格id
	seItem: null,		//选中的条目
	table: null,
	layerIndex: -1
};

/**
 * 初始化面额的列
 */
Denomination.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '面额值', field: 'value', align: 'center', valign: 'middle', sortable: true},
        {title: '充值倍数', field: 'multiple', align: 'center', valign: 'middle', sortable: true},
        {title: '使用次数', field: 'times', align: 'center', valign: 'middle', sortable: true},]
};

/**
 * 点击添加面额
 */
Denomination.openAdd = function () {
    var index = layer.open({
        type: 2,
        title: '添加面额',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/denomination/toAdd'
    });
    //layer.full(index);
    this.layerIndex = index;
};


/**
 * 是否选中
 */
Denomination.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
    	Denomination.seItem = selected[0];
        return true;
    }
};

/**
 * 点击修改面额
 */
Denomination.openDetail = function () {
	if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '修改面额',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/denomination/toEdit/' + this.seItem.id
        });
        //layer.full(index);
        this.layerIndex = index;
    }
};

/**
 * 点击删除面额
 */
Denomination.delete = function () {
	if (this.check()) {
	    var denominationId = this.seItem.id;
	    var ajax = new $ax(Feng.ctxPath + "/denomination/delete", function (data) {
	        Feng.success("删除成功!");
	        Denomination.table.refresh();
	    }, function (data) {
	        Feng.error("删除失败!" + data.responseJSON.message + "!");
	    });
	    ajax.set("denominationId", denominationId);
	    ajax.start();
	}
};


$(function () {
    var defaultColunms = Denomination.initColumn();
    var table = new BSTable(Denomination.id, "/denomination/list", defaultColunms);
    table.setPaginationType("client");
    table.init();
    Denomination.table = table;
});
