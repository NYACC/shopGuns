/**
 * 广告位初始化
 */
var AdsPosition = {
	id: "AdsPositionTable",	//表格id
	seItem: null,		//选中的条目
	table: null,
	layerIndex: -1,
	userId:null
};

/**
 * 初始化表格的列
 */
AdsPosition.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '名称', field: 'name', align: 'center', valign: 'middle', sortable: true},
        {title: '创建者', field: 'creator', align: 'center', valign: 'middle', sortable: true},
        {title: '创建时间', field: 'createTime', align: 'center', valign: 'middle', sortable: true}];
};


/**
 * 点击添加
 */
AdsPosition.openAdd = function () {
    var index = layer.open({
        type: 2,
        title: '添加广告位',
        area: ['800px', '440px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/adsPosition/toAdd'
    });
    //layer.full(index);
    this.layerIndex = index;
};


/**
 * 是否选中
 */
AdsPosition.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
    	AdsPosition.seItem = selected[0];
        return true;
    }
};


/**
 * 点击修改
 */
AdsPosition.openDetail = function () {
	if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '修改广告位',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/adsPosition/toEdit/' + this.seItem.id
        });
        //layer.full(index);
        this.layerIndex = index;
    }
};


/**
 * 点击删除
 */
AdsPosition.delete = function () {
	if (this.check()) {
		var adsPositionId = this.seItem.id;
        var operation = function(){
     	    var ajax = new $ax(Feng.ctxPath + "/adsPosition/delete", function (data) {
     	        Feng.success("删除成功!");
     	        AdsPosition.table.refresh();
     	    }, function (data) {
     	        Feng.error("删除失败!" + data.responseJSON.message + "!");
     	    });
     	    ajax.set("adsPositionId", adsPositionId);
     	    ajax.start();
        };
        Feng.confirm("是否删除该广告位?如果删除，则该广告位下的广告都会被删除。", operation);
    }
};

/**
 * 查询列表
 */
AdsPosition.search = function () {
    var queryData = {};
    queryData['title'] = $("#title").val();
    AdsPosition.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = AdsPosition.initColumn();
    var table = new BSTable(AdsPosition.id, "/adsPosition/list", defaultColunms);
    table.setPaginationType("client");
    table.init();
    AdsPosition.table = table;
});
