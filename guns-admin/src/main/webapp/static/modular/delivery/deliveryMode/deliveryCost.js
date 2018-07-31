/**
 * 配送价格初始化
 */
var DeliveryCost = {
	id: "DeliveryCostTable",	//表格id
	seItem: null,		//选中的条目
	table: null,
	layerIndex: -1,
	userId:null
};

/**
 * 初始化表格的列
 */
DeliveryCost.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '派货地区', field: 'deliveryAreaName', align: 'center', valign: 'middle', sortable: true},
        {title: '配送地区', field: 'areaName', align: 'center', valign: 'middle', sortable: true},
        {title: '是否配送', field: 'isdelivery', align: 'center', valign: 'middle', sortable: true},
        {title: '首重', field: 'ykg', align: 'center', valign: 'middle', sortable: true},
        {title: '首价', field: 'startPrice', align: 'center', valign: 'middle', sortable: true},
        {title: '续重', field: 'addedWeight', align: 'center', valign: 'middle', sortable: true},
        {title: '续价', field: 'addedPrice', align: 'center', valign: 'middle', sortable: true},
        {title: '修改时间', field: 'modifyTime', align: 'center', valign: 'middle', sortable: true},];
};



/**
 * 是否选中
 */
DeliveryCost.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
    	DeliveryCost.seItem = selected[0];
        return true;
    }
};


/**
 * 点击修改
 */
DeliveryCost.openDetail = function () {
	if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '修改配送方式',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/deliveryMode/toCostEdit/' + this.seItem.id
        });
        //layer.full(index);
        this.layerIndex = index;
    }
};



/**
 * 查询列表
 */
DeliveryCost.search = function () {
    var queryData = {};
    queryData['province'] = $("#province").val();
    queryData['city'] = $("#city").val();
    queryData['county'] = $("#county").val();
    queryData['modeId'] = $("#id").val();
    queryData['deliveryPro'] = $("#deliveryPro").val();
    queryData['deliveryCity'] = $("#deliveryCity").val();
    queryData['deliveryCou'] = $("#deliveryCou").val();
    queryData['isdelivery'] = $("#isdelivery").val();
    DeliveryCost.table.refresh({query: queryData});
};

$(function () {
	$("#province").on("change",DeliveryCost.provinceChange);
	$("#deliveryPro").on("change",DeliveryCost.deliveryProChange);
    var defaultColunms = DeliveryCost.initColumn();
    var table = new BSTable(DeliveryCost.id, "/deliveryMode/costs", defaultColunms);
    table.setPaginationType("client");
    table.init();
    DeliveryCost.table = table;
    //初始化省
    var ajax = new $ax(Feng.ctxPath + "/area/province", function (data) {
    	var content="<option value=''>请选择省</option>";
    	for(var i=0;i<data.length;i++){
    		content+="<option value='"+data[i].id+"'>"+data[i].name+"</option>"
    	}
    	$("#province").html(content);
    	$("#deliveryPro").html(content);
    }, function (data) {
    });
    
    ajax.start();
});

DeliveryCost.provinceChange=function(){
	//初始化市
    var ajax = new $ax(Feng.ctxPath + "/area/city", function (data) {
    	var content="<option value=''>请选择市</option>";
    	for(var i=0;i<data.length;i++){
    		content+="<option value='"+data[i].id+"'>"+data[i].name+"</option>"
    	}
    	$("#city").html(content);
    	DeliveryCost.cityChange();
    }, function (data) {
    });
    ajax.set("province",$("#province").val());
    ajax.start();
}

DeliveryCost.cityChange=function(){
	//初始化市
    var ajax = new $ax(Feng.ctxPath + "/area/county", function (data) {
    	var content="<option value=''  >请选择区/县</option>";
    	for(var i=0;i<data.length;i++){
    		content+="<option value='"+data[i].id+"'>"+data[i].name+"</option>"
    	}
    	$("#county").html(content);
    }, function (data) {
    });
    ajax.set("city",$("#city").val());
    ajax.start();
}

DeliveryCost.deliveryProChange=function(){
	//初始化市
    var ajax = new $ax(Feng.ctxPath + "/area/city", function (data) {
    	var content="<option value=''>请选择市</option>";
    	for(var i=0;i<data.length;i++){
    		content+="<option value='"+data[i].id+"'>"+data[i].name+"</option>"
    	}
    	$("#deliveryCity").html(content);
    	DeliveryCost.cityChange();
    }, function (data) {
    });
    ajax.set("province",$("#deliveryPro").val());
    ajax.start();
}

DeliveryCost.deliveryCityChange=function(){
	//初始化市
    var ajax = new $ax(Feng.ctxPath + "/area/county", function (data) {
    	var content="<option value=''  >请选择区/县</option>";
    	for(var i=0;i<data.length;i++){
    		content+="<option value='"+data[i].id+"'>"+data[i].name+"</option>"
    	}
    	$("#deliveryCou").html(content);
    }, function (data) {
    });
    ajax.set("city",$("#deliveryCity").val());
    ajax.start();
}

DeliveryCost.downloadTemplate=function(){
	location.href=Feng.ctxPath + "/deliveryMode/template?province="+$("#province").val()
	+"&city="+$("#city").val()+"&county="+$("#county").val()+"&modeId="+$("#id").val()+"&deliveryPro="+$("#deliveryPro").val()+
	"&deliveryCity="+$("#deliveryCity").val()+"&deliveryCou="+$("#deliveryCou").val()+"&isdelivery="+$("#isdelivery").val();
}
DeliveryCost.openImport = function(){
    var index = layer.open({
        type: 2,
        title: '导入优惠码',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/deliveryMode/toImport/' + $("#id").val()
    });
    //layer.full(index);
    this.layerIndex = index;
}