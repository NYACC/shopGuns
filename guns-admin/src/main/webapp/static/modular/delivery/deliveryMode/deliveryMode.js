/**
 * 配送方式初始化
 */
var DeliveryMode = {
	id: "DeliveryModeTable",	//表格id
	seItem: null,		//选中的条目
	table: null,
	layerIndex: -1,
	userId:null
};

/**
 * 初始化表格的列
 */
DeliveryMode.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '名称', field: 'name', align: 'center', valign: 'middle', sortable: true},
        {title: '排序', field: 'sequence', align: 'center', valign: 'middle', sortable: true},
        {title: '创建时间', field: 'createTime', align: 'center', valign: 'middle', sortable: true},
        {title: '操作', field: '', align: 'center', valign: 'middle', sortable: true,formatter : operateFormatter}];
};

function operateFormatter(value, row, index) {
    return '<button  icon="fa-check" type="button" onclick="DeliveryMode.openCost(\''+row.id+'\')" class="btn btn-primary btn-xs">配置配送费</button>';
};

DeliveryMode.openCost = function(id){
    var index = layer.open({
        type: 2,
        title: '配置配送费',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/deliveryMode/toCost/' + id
    });
    layer.full(index);
    this.layerIndex = index;
    
}


/**
 * 点击添加
 */
DeliveryMode.openAdd = function () {
    var index = layer.open({
        type: 2,
        title: '添加配送方式',
        area: ['800px', '440px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/deliveryMode/toAdd'
    });
    //layer.full(index);
    this.layerIndex = index;
};


/**
 * 是否选中
 */
DeliveryMode.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
    	DeliveryMode.seItem = selected[0];
        return true;
    }
};


/**
 * 点击修改
 */
DeliveryMode.openDetail = function () {
	if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '修改配送方式',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/deliveryMode/toEdit/' + this.seItem.id
        });
        //layer.full(index);
        this.layerIndex = index;
    }
};


/**
 * 点击删除
 */
DeliveryMode.delete = function () {
	if (this.check()) {
		var deliveryModeId = this.seItem.id;
        var operation = function(){
     	    var ajax = new $ax(Feng.ctxPath + "/deliveryMode/delete", function (data) {
     	        Feng.success("删除成功!");
     	        DeliveryMode.table.refresh();
     	    }, function (data) {
     	        Feng.error("删除失败!" + data.responseJSON.message + "!");
     	    });
     	    ajax.set("deliveryModeId", deliveryModeId);
     	    ajax.start();
        };
        Feng.confirm("是否删除该配送方式?如果删除，则该配送方式下的配送费信息都会被删除。", operation);
    }
};

/**
 * 查询列表
 */
DeliveryMode.search = function () {
    var queryData = {};
    queryData['title'] = $("#title").val();
    DeliveryMode.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = DeliveryMode.initColumn();
    var table = new BSTable(DeliveryMode.id, "/deliveryMode/list", defaultColunms);
    table.setPaginationType("client");
    table.init();
    DeliveryMode.table = table;
});
