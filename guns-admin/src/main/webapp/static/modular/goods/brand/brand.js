/**
 * 品牌管理初始化
 */
var Brand = {
    id: "BrandTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Brand.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '品牌名称', field: 'name', align: 'center', valign: 'middle', sortable: true},
        {title: '排序', field: 'num', align: 'center', valign: 'middle', sortable: true},
        {title: '状态', field: 'statusName', align: 'center', valign: 'middle', sortable: true}];
};

/**
 * 检查是否选中
 */
Brand.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Brand.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加品牌
 */
Brand.openAddBrand = function () {
    var index = layer.open({
        type: 2,
        title: '添加品牌',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/brand/toAdd'
    });
    //layer.full(index);
    this.layerIndex = index;
};

/**
 * 点击查看品牌详情
 */
Brand.openBrandDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '品牌详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/brand/toEdit/' + Brand.seItem.id
        });
        //layer.full(index);
        this.layerIndex = index;
    }
};

/**
 * 删除品牌
 */
Brand.delete = function () {
    if (this.check()) {

        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/brand/delete", function () {
                Feng.success("删除成功!");
                Brand.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("brandId",Brand.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否刪除该品牌?", operation);
    }
};

/**
 * 查询品牌列表
 */
Brand.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Brand.table.refresh({query: queryData});
};
/**
 * 禁用品牌
 * @param userId
 */
Brand.freezeAccount = function () {
    if (this.check()) {
        var brandId = this.seItem.id;
        var ajax = new $ax(Feng.ctxPath + "/brand/disable", function (data) {
            Feng.success("禁用成功!");
            Brand.table.refresh();
        }, function (data) {
            Feng.error("禁用失败!" + data.responseJSON.message + "!");
        });
        ajax.set("brandId", brandId);
        ajax.start();
    }
};

/**
 * 启用品牌
 * @param userId
 */
Brand.unfreeze = function () {
    if (this.check()) {
        var brandId = this.seItem.id;
        var ajax = new $ax(Feng.ctxPath + "/brand/enable", function (data) {
            Feng.success("解除禁用成功!");
            Brand.table.refresh();
        }, function (data) {
            Feng.error("解除禁用失败!");
        });
        ajax.set("brandId", brandId);
        ajax.start();
    }
}
$(function () {
    var defaultColunms = Brand.initColumn();
    var table = new BSTable(Brand.id, "/brand/list", defaultColunms);
    table.setPaginationType("client");
    table.init();
    Brand.table = table;
});
