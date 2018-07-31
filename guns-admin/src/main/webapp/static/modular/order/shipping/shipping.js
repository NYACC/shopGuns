/**
 * 发货单初始化
 */
var Shipping = {
	id: "ShippingTable",	//表格id
	seItem: null,		//选中的条目
	table: null,
	layerIndex: -1
};

/**
 * 初始化表格的列
 */
Shipping.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '订单编号', field: 'sn', align: 'center', valign: 'middle', sortable: true},
        {title: '收货人姓名', field: 'consigneeName', align: 'center', valign: 'middle', sortable: true},
        {title: '收货人电话', field: 'phoneNum', align: 'center', valign: 'middle', sortable: true},
        {title: '配送方式', field: 'bill', align: 'center', valign: 'middle', sortable: true},
        {title: '订单状态', field: 'statusName', align: 'center', valign: 'middle', sortable: true},
        {title: '门店', field: 'shopName', align: 'center', valign: 'middle', sortable: true},
        {title: '创建日期', field: 'createTime', align: 'center', valign: 'middle', sortable: true},
        {title: '操作', field: '', align: 'center', valign: 'middle', sortable: true,formatter : operateFormatter, events : operateEvents}];
};

function operateFormatter(value, row, index) {
    return '<button  id="shipping" icon="fa-check" type="button" class="btn btn-primary btn-xs">发货</button>';
};

window.operateEvents = {
	    "click #shipping":function(e,value,row,index){
	        var index = layer.open({
	            type: 2,
	            title: '发货单',
	            area: ['800px', '450px'], //宽高
	            fix: false, //不固定
	            maxmin: true,
	            content: Feng.ctxPath + '/shipping/toAdd/'+row.id
	        });
	        layer.full(index);
	        this.layerIndex = index;
	    }
}
$(function () {

    var defaultColunms = Shipping.initColumn();
    var table = new BSTable(Shipping.id, "/shipping/list", defaultColunms); 
    table.setPaginationType("client");
    table.init();
    Shipping.table = table;

});
