/**
 * 出初始化评价
 */
var Evaluation = {
    id: "EvaluationTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Evaluation.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '评价人', field: 'memberName', align: 'center', valign: 'middle', sortable: true},
        {title: '规格名称', field: 'specName', align: 'center', valign: 'middle', sortable: true},
        {title: '等级', field: 'levelName', align: 'center', valign: 'middle', sortable: true},
        {title: '评价内容', field: 'detail', align: 'center', valign: 'middle', sortable: true},
        {title: '时间', field: 'createTime', align: 'center', valign: 'middle', sortable: true}];
};

/**
 * 查询出入库单列表
 */
Evaluation.search = function () {
    var queryData = {};
    Evaluation.table.refresh({query: queryData});
};

$(function () {
    var defaultColumns = Evaluation.initColumn();
    var table = new BSTable(Evaluation.id, "/evaluation/list", defaultColumns);
    table.setPaginationType("client");
    table.init();
    Evaluation.table = table;

});

