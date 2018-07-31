/**
 * 余额初始化
 */
var Balance = {
	id: "BalanceTable",	//表格id
	seItem: null,		//选中的条目
	table: null,
	layerIndex: -1
};

/**
 * 初始化表格的列
 */
Balance.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '余额账户', field: 'balanceSn', align: 'center', valign: 'middle', sortable: true},
        {title: '客户昵称', field: 'memberName', align: 'center', valign: 'middle', sortable: true},
        {title: '客户手机号', field: 'phoneNum', align: 'center', valign: 'middle', sortable: true},
        {title: '余额', field: 'balance', align: 'center', valign: 'middle', sortable: true},
        {title: '操作', field: 'num', align: 'center', valign: 'middle', sortable: true,formatter : operateFormatter, events : operateEvents},]
};

function operateFormatter(value, row, index) {
    return '<button id="balanceLog" icon="fa-check" type="button" class="btn btn-primary btn-xs">查看日志</button>';
};

window.operateEvents = {
"click #balanceLog":function(e,value,row,index){
    var index = layer.open({
        type: 2,
        title: '余额详情',
        area: ['800px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/balance/detail/' + row.id
    });
    layer.full(index);
    this.layerIndex = index;
}
}

/**
 * 点击余额充值
 */
Balance.openRecharge = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '余额充值',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/balance/balance_recharge/' + this.seItem.id
        });
        //layer.full(index);
        this.layerIndex = index;
    }
};


/**
 * 查询客户列表
 */
Balance.search = function () {
    var queryData = {};
    queryData['phoneNum'] = $("#phoneNum").val();
    Balance.table.refresh({query: queryData});
};

/**
 * 是否选中
 */
Balance.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
    	Balance.seItem = selected[0];
        return true;
    }
};

$(function () {
    var defaultColunms = Balance.initColumn();
    var table = new BSTable(Balance.id, "/balance/list", defaultColunms);
    table.setPaginationType("client");
    table.init();
    Balance.table = table;
});
