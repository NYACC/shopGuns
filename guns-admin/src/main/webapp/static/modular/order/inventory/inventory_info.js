/**
 * 初始化品牌详情对话框
 */
var InventoryInfoDlg = {
	id:"priceTagTable",
	table:null,
	productId:null,
    goodsName:null,
    operatorId:$("#operatorId").val(),
    type:$("#type").val(),
    barcode:null,
};

/**
 * 提交添加
 */
InventoryInfoDlg.addSubmit = function () {
    this.collectData();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/order/add", function (data) {
        Feng.success("添加成功!");
        window.parent.Order.table.refresh();
        InventoryInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    this.orderInfoData.sn = $("#sn").html();
    ajax.set("orderJson", JSON.stringify(this.orderInfoData));
    ajax.start();
}

//product测试
var productBsSuggest = $("#product").bsSuggest({
    indexId: 3, //data.value 的第几个数据，作为input输入框的内容
    indexKey: 0, //data.value 的第几个数据，作为input输入框的内容
    allowNoKeyword: false, //是否允许无关键字时请求数据
    multiWord: true, //以分隔符号分割的多关键字支持
    separator: ",", //多关键字支持时的分隔符，默认为空格
    getDataMethod: "url", //获取数据的方式，总是从 URL 获取
    effectiveFields: ["barcode", "goodsName"],
    effectiveFieldsAlias: {
        goodsName: "商品名称",
        barcode: "商品条码"
    },
    showHeader: true,
    url:Feng.ctxPath + '/inventory/findProduct/',
    processData: function (json) { // url 获取数据时，对数据的处理，作为 getData 的回调函数
        var i, len, data = {
            value: []
        };

        if (!json || json.length == 0) {
            return false;
        }

        len = json.length;

        for (i = 0; i < len; i++) {
            data.value.push({
                "barcode": json[i].barcode,
                "goodsName": json[i].goodsName,
                "goodsId": json[i].goodsId,
                "id": json[i].id,
                "sn": json[i].sn
            });
        }
        return data;
    }

}).on('onSetSelectValue', function (e, keyword) {
    InventoryInfoDlg.productId = keyword.id;
    InventoryInfoDlg.barcode = keyword.key;
    console.log(keyword);
    var ajax = new $ax(Feng.ctxPath + "/inventory/getGoodsName", function (data) {
        $("#goodsName").val(data);
        InventoryInfoDlg.goodsName=data;
    }, function (data) {
        Feng.error("商品名称获取失败!" + data.responseJSON.message + "!");
    });
    ajax.set("productId", keyword.id);
    ajax.start();
    InventoryInfoDlg.reflash();
});

/**
 * 获取价格标签列表
 */
InventoryInfoDlg.reflash = function () {
    var queryData = {};
    queryData['productId'] = this.productId;
    InventoryInfoDlg.table.refresh({query: queryData});
};

InventoryInfoDlg.initColumn = function () {
    return [
        {field: 'selectItem', radio: true, visible: false},
        {title: '门店', field: 'storesName', align: 'center', valign: 'middle', sortable: true},
        {title: '库存预警值', field: 'threshold', align: 'center', valign: 'middle', sortable: true},
        {title: '当前库存', field: 'inventory', align: 'center', valign: 'middle', sortable: true},
        {title: InventoryInfoDlg.type==0?'出库数量':'入库数量', align: 'center', valign: 'middle', sortable: true, formatter: operateFormatter}];
};

function operateFormatter(value, row, index) {
    inventorys.items.push(new Inventory(row));
    return '<input type="number" onblur="InventoryInfoDlg.changeAmount(\''+row.shopId+'\',\''+row.inventory+'\',this)" value="0" />';
};

InventoryInfoDlg.changeAmount=function(shopId,inventory,obj){
	if(InventoryInfoDlg.type==0){
        if(inventory<$(obj).val()){
            Feng.alert("库存不足！");
            $(obj).val(0);
        }
    }
    inventorys.setAmount(shopId,$(obj).val());

}

var inventorys = new Inventorys();
 function Inventorys() {
    this.items = [];
    this.setAmount=function(shopId,amount){
    	for(var i=0; i<this.items.length; i++){
    		if(this.items[i].shopId==shopId){
    			this.items[i].amount=amount;
    		}
    	}
    }
    this.setRemark=function () {
        for(var i=0; i<this.items.length; i++){
            this.items[i].remark=$('#remark').val();
        }
    }
};

function Inventory(data){
    this.shopId=data.shopId;
    this.amount=0;
    this.operatorId=InventoryInfoDlg.operatorId;
    this.type=InventoryInfoDlg.type;
    this.goodsName=InventoryInfoDlg.goodsName;
    this.barcode=InventoryInfoDlg.barcode;
    this.remark='';
    this.createTime=$("#createTime").html();
}

/**
 * 关闭此对话框
 */
InventoryInfoDlg.close = function () {
    parent.layer.close(window.parent.Inventory.layerIndex);
}

InventoryInfoDlg.addSubmit =function(){
	var ajax = new $ax(Feng.ctxPath + "/inventory/add", function (data) {
		Feng.success("添加成功!");
        window.parent.Inventory.table.refresh();
        InventoryInfoDlg.close();
	}, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
	ajax.set("productId", InventoryInfoDlg.productId);
	inventorys.setRemark();
	ajax.set("inventorys",JSON.stringify(inventorys.items));
    ajax.start();
}

$(function () {
    var defaultColunms = InventoryInfoDlg.initColumn();
    var table = new BSTable(InventoryInfoDlg.id, "/inventory/findPriceTag", defaultColunms);
    table.setPaginationType("client");
    table.init();
    InventoryInfoDlg.table = table;
});
