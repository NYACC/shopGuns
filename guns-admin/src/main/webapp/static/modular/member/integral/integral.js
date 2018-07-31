/**
 * 积分初始化
 */
var Integral = {
	id: "IntegralTable",	//表格id
	seItem: null,		//选中的条目
	table: null,
	layerIndex: -1
};

/**
 * 初始化表格的列
 */
Integral.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '客户昵称', field: 'memberName', align: 'center', valign: 'middle', sortable: true},
        {title: '客户手机', field: 'phoneNum', align: 'center', valign: 'middle', sortable: true},
        {title: '积分', field: 'integralValue', align: 'center', valign: 'middle', sortable: true},
        {title: '操作', field: '', align: 'center', valign: 'middle', sortable: true,formatter : operateFormatter, events : operateEvents}]
};
function operateFormatter(value, row, index) {
    return '<button id="integralLog" icon="fa-check" type="button" class="btn btn-primary btn-xs">查看日志</button>';
};

window.operateEvents = {
    "click #integralLog":function(e,value,row,index){
        var index = layer.open({
            type: 2,
            title: '积分详情',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/integral/detail/' + row.id
        });
        layer.full(index);
        this.layerIndex = index;
    }
}
/**
 * 查询积分列表
 */
Integral.search = function () {
    var queryData = {};
    queryData['phoneNum'] = $("#phoneNum").val();
    Integral.table.refresh({query: queryData});
};

/**
 * 是否选中
 */
Integral.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Integral.seItem = selected[0];
        return true;
    }
};

/**
 * 点击修该积分
 */
Integral.openUpdate = function () {
	if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '修改积分',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/integral/toEdit/' + this.seItem.id
        });
        this.layerIndex = index;
    }
};


$(function () {
    var defaultColunms = Integral.initColumn();
    var table = new BSTable(Integral.id, "/integral/list", defaultColunms);
    table.setPaginationType("client");
    table.init();
    Integral.table = table;
});
