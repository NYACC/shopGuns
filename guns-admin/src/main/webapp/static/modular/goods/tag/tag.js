/**
 * 标签管理初始化
 */
var Tag = {
    id: "TagTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Tag.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '标签名称', field: 'name', align: 'center', valign: 'middle', sortable: true},
        {title: '创建时间', field: 'time', align: 'center', valign: 'middle', sortable: true},
        {title: '序号', field: 'sequence', align: 'center', valign: 'middle'},]
};
/**
 * 点击添加
 */
Tag.openAdd = function () {
    var index = layer.open({
        type: 2,
        title: '添加标签',
        area: ['800px', '560px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/tag/toAdd'
    });
    this.layerIndex = index;
};

/**
 * 点击修改按钮时
 * @param userId 管理员id
 */
Tag.openDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '编辑标签',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/tag/toEdit/' + this.seItem.id
        });
        this.layerIndex = index;
    }
};
/**
 * 删除标签
 */
Tag.delete = function () {
    if (this.check()) {

        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/tag/delete", function () {
                Feng.success("删除成功!");
                Tag.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("tagId",Tag.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否刪除该标签?", operation);
    }
};
/**
 * 检查是否选中
 */
Tag.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Tag.seItem = selected[0];
        return true;
    }
};
/**
 * 查询分类列表
 */
Tag.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Tag.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Tag.initColumn();
    var table = new BSTable(Tag.id, "/tag/list", defaultColunms);
    table.setPaginationType("client");
    Tag.table = table.init();
});
