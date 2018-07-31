/**
 * 价格标签初始化
 */
var PriceTagInfo = {
    id: "priceTagTable",	//表格id
    table: null,
    products:null,
    priceTags:null,
    layerIndex: -1,
    priceTagData:[],
    currentFieldName:"" //当前填值的input框对应的列的field的名称

};
PriceTagInfo.toHtml = function(data){
    for(var i =0;i<data.items.length;i++){
        $("#tabs").append(this.getTabHtml(i,data.items[i].name));
        $("#tab-content").append(this.getTabContent(i));
        this.tableHtml("priceTagTable"+(i+1),data.items[i].items);
    }
}
/**
 * 获取标签选项页
 * @param index
 * @param tabName
 */
PriceTagInfo.getTabHtml = function(index,_name){
    var content=$("<div></div>");
    var _li=$("<li></li>");
    if(index==0){
        _li.attr("class","active");
    }
    var _a=$("<a></a>").attr("data-toggle","tab").attr("href","#tab-"+(index+1)).attr("aria-expanded","true").html(_name);
    _li.append(_a);
    content.append(_li);
    return content.html();
}
PriceTagInfo.getTabContent = function(index){
    var content=$("<div></div>");
    var _div=$("<div></div>").attr("id","tab-"+(index+1)).attr("class","tab-pane").css("margin-top","10px");
    if(index==0){
        _div.attr("class","tab-pane active");
    }
    var _table=$("<table></table>").attr("id","priceTagTable"+(index+1));
    _div.append(_table);
    content.append(_div);
    return content.html();
}
/**
 * 生成门店价格标签列表
 * @param tableId
 */
PriceTagInfo.tableHtml = function(tableId,priceTags){
    var defaultColunms = this.initColumn();
    $('#' + tableId).bootstrapTable({
        data:priceTags,
        columns: defaultColunms,		//列数组
        height:550,
        // onClickCell:function(field,value,row,$element){
        //     PriceTagInfo.currentFieldName=field;
        // }
    });
}
/**
 * 门店标签页列表
 * 初始化表格的列
 */
PriceTagInfo.initColumn = function () {
    return [
        {field: 'id', visible:false},
        {title: '门店', field: 'storesName', align: 'center', valign: 'middle', sortable: true},
        {title: '市场价', field: 'marketPrice', align: 'center', valign: 'middle', sortable: true,formatter:getMarketPriceInput},
        {title: '销售价', field: 'price', align: 'center', valign: 'middle', sortable: true,formatter:getPriceInput},
        {title: '商品积分', field: 'integral', align: 'center', valign: 'middle', sortable: true,formatter:getIntegralInput},
        {title: '库存', field: 'inventory', align: 'center', valign: 'middle', sortable: true,formatter:getInventoryInput},
        {title: '库存预警值', field: 'threshold', align: 'center', valign: 'middle', sortable: true,formatter:getThresholdInput},
        {title: '上架/下架', field: 'marketable', align: 'center', valign: 'middle', sortable: true,formatter:getCheckbox}];
};

function getMarketPriceInput(value, row, index){
    return '<input class="form-control"  type="text" name="marketPrice" value="'+(typeof(value)=='undefined'?'':value)+'" onkeyup="PriceTagInfo.setTagValue(this,\''+row.id+'\')">';
}
function getPriceInput(value, row, index){
    return '<input class="form-control"  type="text" name="price" value="'+(typeof(value)=='undefined'?'':value)+'" onkeyup="PriceTagInfo.setTagValue(this,\''+row.id+'\')">';
}
function getIntegralInput(value, row, index){
    return '<input class="form-control"  type="text" name="integral" value="'+(typeof(value)=='undefined'?'':value)+'" onkeyup="PriceTagInfo.setTagValue(this,\''+row.id+'\')">';
}
function getInventoryInput(value, row, index){
    return '<input class="form-control"  type="text" readonly="readonly" name="inventory" value="'+(typeof(value)=='undefined'?'':value)+'" onkeyup="PriceTagInfo.setTagValue(this,\''+row.id+'\')">';
}
function getThresholdInput(value, row, index){
    return '<input class="form-control"  type="text" name="threshold" value="'+(typeof(value)=='undefined'?'':value)+'" onkeyup="PriceTagInfo.setTagValue(this,\''+row.id+'\')">';
}
function getCheckbox(value, row, index){
    if(value ==1){
        return'<input  type="checkbox" checked name="marketable" class="marketable i-checks" id="'+row.id+'">上架';
    }
    return'<input  type="checkbox" name="marketable" class="marketable i-checks" id="'+row.id+'" >上架';
}

PriceTagInfo.setTagValue=function(obj,_id){
    var _value=$(obj).val();
    var _name=$(obj).attr("name");
    if($(obj).attr("type")=="checkbox"){
        _value=0;
        if(obj.checked){
            _value=1;
        }
    }
    productTabs.putValue(_id,_value,_name);
}

function  ProductTabs() {
    this.items =[];
    this.init = function(){
        //初始化规格组
        products.init(PriceTagInfo.products);
        for(var i=0;i<products.items.length;i++){
            var productTab=new ProductTab(products.items[i]);
            this.items.push(productTab);
        }
    }
    this.putValue = function(_id,_value,_name){
        this.items.forEach(function(productTab){
            productTab.items.forEach(function(priceTag){
                if(priceTag.id==_id){
                    priceTag[_name]=_value;
                    priceTag.isEdit=true;
                }
            })
        })
    }
}
function ProductTab(data){
    this.id= data.id;
    this.name=data.getName();
    this.items=[];
    for(var i=0;i<PriceTagInfo.priceTags.length;i++){
        if(PriceTagInfo.priceTags[i].productId==this.id){
            this.items.push(new priceTag(PriceTagInfo.priceTags[i]));
        }
    }
}
function priceTag(data){
    this.id=data.id;
    this.goodsId=data.goodsId;
    this.productId=data.productId;
    this.marketPrice=data.marketPrice;
    this.price=data.price;
    this.storesId=data.storesId;
    this.inventory=data.inventory;
    this.threshold=data.threshold;
    this.marketable=data.marketable;
    this.integral=data.integral;
    this.storesName=data.storesName;
    this.isEdit = false;

}
PriceTagInfo.collectData = function () {
    productTabs.items.forEach(function(productTab){
        productTab.items.forEach(function(priceTag){
            if(priceTag.isEdit){
               PriceTagInfo.priceTagData.push(priceTag);
            }
        })
    })
}
PriceTagInfo.clearData = function(){
    this.priceTagData=[];
}
/**
 * 提交修改数据
 */
PriceTagInfo.editSubmit = function(){
    this.clearData();
    this.collectData();
    console.log(PriceTagInfo.priceTagData);
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/priceTag/edit", function (data) {
        Feng.success("修改成功!");
        window.parent.Goods.table.refresh();
        GoodsInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set("priceTags",JSON.stringify(this.priceTagData));
    ajax.start();
}

var productTabs=new ProductTabs();
$(function () {
    productTabs.init();
    PriceTagInfo.toHtml(productTabs);
    $('input[type="checkbox"]').on('ifChanged', function () {
        PriceTagInfo.setTagValue(this,$(this).attr("id"));
    }).iCheck({
        checkboxClass: 'icheckbox_square-green'
    });
});