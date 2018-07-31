/**
 * 广告位初始化
 */
var Advertising = {
	id: "AdvertisingTable",	//表格id
	seItem: null,		//选中的条目
	table: null,
	layerIndex: -1,
	userId:null
};

/**
 * 初始化表格的列
 */
Advertising.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '广告名称', field: 'name', align: 'center', valign: 'middle', sortable: true},
        {title: '链接', field: 'link', align: 'center', valign: 'middle', sortable: true},
        {title: '广告位', field: 'positionName', align: 'center', valign: 'middle', sortable: true},
        {title: '排序', field: 'sequence', align: 'center', valign: 'middle', sortable: true},
        {title: '创建时间', field: 'createTime', align: 'center', valign: 'middle', sortable: true},
        {title: '操作', field: '', align: 'center', valign: 'middle', sortable: true,formatter : operateFormatter}];
};

function operateFormatter(value, row, index) {
    return '<button id="bindProduct" icon="fa-check" type="button" onclick="Advertising.openBind(\''+row.id+'\')" class="btn btn-primary btn-xs">关联商品</button>';
};

Advertising.openBind = function(id){
    var index = layer.open({
        type: 2,
        title: '绑定广告关联',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/advertising/toBind/' + id
    });
    layer.full(index);
    this.layerIndex = index;
    
}

/**
 * 点击添加
 */
Advertising.openAdd = function () {
    var index = layer.open({
        type: 2,
        title: '添加广告',
        area: ['800px', '440px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/advertising/toAdd'
    });
    //layer.full(index);
    this.layerIndex = index;
};


/**
 * 是否选中
 */
Advertising.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
    	Advertising.seItem = selected[0];
        return true;
    }
};


/**
 * 点击修改
 */
Advertising.openDetail = function () {
	if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '修改广告',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/advertising/toEdit/' + this.seItem.id
        });
        //layer.full(index);
        this.layerIndex = index;
    }
};


/**
 * 点击删除
 */
Advertising.delete = function () {
	if (this.check()) {
	    var advertisingId = this.seItem.id;
	    var ajax = new $ax(Feng.ctxPath + "/advertising/delete", function (data) {
	        Feng.success("删除成功!");
	        Advertising.table.refresh();
	    }, function (data) {
	        Feng.error("删除失败!" + data.responseJSON.message + "!");
	    });
	    ajax.set("advertisingId", advertisingId);
	    ajax.start();
	}

};

/**
 * 查询列表
 */
Advertising.search = function () {
    var queryData = {};
    console.log($("#position").val());
    queryData['positionId'] = $("#position").val();
    Advertising.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Advertising.initColumn();
    var table = new BSTable(Advertising.id, "/advertising/list", defaultColunms);
    table.setPaginationType("client");
    table.init();
    Advertising.table = table;
});
