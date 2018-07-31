/**
 * 初始化发货单详情对话框
 */
var ShippingInfoDlg = {
    orderItems:null,
	shippingItems : [],
};
ShippingInfoDlg.toHtml = function (data) {
    var content = $("<div></div>");
    var _table = $("<table></table>").addClass("table invoice-table");
    var _thead = $("<thead></thead>");
    var _tr = $("<tr></tr>");
    var _th1 = $("<th></th>").html("发货清单");
    var _th2 = $("<th></th>").html("商品单价");
    var _th3 = $("<th></th>").html("购买数量");
    var _th4 = $("<th></th>").html("发货数量");
    var _th5 = $("<th></th>").html("商品总价");
    var _th6 = $("<th></th>").html("规格id").css("display","none");
    var _th7 = $("<th></th>").html("商品id").css("display","none");
    var _tbody= $("<tbody></tbody>");
    for(var i=0;i<data.length;i++){
        var _trbody=$("<tr></tr>").addClass("items");
        var _td1 =$("<td></td>");
        var _div1=$("<div></div>");
        var _strong = $("<strong></strong>").text(data[i].goodsName);
        _td1.append(_div1.append(_strong));
        var _td2 =$("<td></td>").text(data[i].unitPrice);
        var _td3 =$("<td></td>").text(data[i].quantity);
        var _td4 =$("<td></td>");
        var _div2=$("<div></div>").addClass("col-sm-offset-11");
        var _input=$("<input/>").attr("type","text").addClass("form-control").attr("name","shipQuantity")
            .attr("value",data[i].shipQuantity==null?0:data[i].shipQuantity);
        _td4.append(_div2.append(_input));
        var _td5 =$("<td></td>").text(data[i].amount);
        var _td6 =$("<td></td>").css("display","none").text(data[i].productId);
        var _td7 =$("<td></td>").css("display","none").text(data[i].goodsId);
        _trbody.append(_td1);
        _trbody.append(_td2);
        _trbody.append(_td3);
        _trbody.append(_td4);
        _trbody.append(_td5);
        _trbody.append(_td6);
        _trbody.append(_td7);
        _tbody.append(_trbody);
    }
    _tr.append(_th1);
    _tr.append(_th2);
    _tr.append(_th3);
    _tr.append(_th4);
    _tr.append(_th5);
    _tr.append(_th6);
    _tr.append(_th7);
    _thead.append(_tr);
    _table.append(_thead);
    _table.append(_tbody);
    content.append(_table);
    $("#shippingItems").html(content.html());
}
function shippingItem(data){
    this.goodsName = data.find("strong").html();
    this.unitPrice = data.find("td:eq(1)").text();
    this.quantity = data.find("td:eq(2)").text();
    this.shipQuantity = data.find("input").val();
    this.amount = data.find("td:eq(4)").text();
    this.productId=data.find("td:eq(5)").text();
    this.goodsId=data.find("td:eq(6)").text();
}
ShippingInfoDlg.collectItem = function(){
    $(".items").each(function(){
        ShippingInfoDlg.shippingItems.push(new shippingItem($(this)));
    });
}

/**
 * 提交发货单
 */
ShippingInfoDlg.addSubmit = function() {
    this.collectItem();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/shipping/add", function(data){
        Feng.success("发货成功!");
        window.parent.Shipping.table.refresh();
        ShippingInfoDlg.close();
    },function(data){
        Feng.error("发货失败!" + data.responseJSON.message + "!");
    });
    ajax.set("userName",$("#userName").text());
    ajax.set("orderId",$("#orderId").val());
    ajax.set("ShippingId",$("#ShippingId").val());
    ajax.set("logisticsFirm",$("#logisticsFirm").val());
    ajax.set("logisticsNum",$("#logisticsNum").val());
    ajax.set("sn",$("#ShippingSn").val());
    ajax.set("items",JSON.stringify(this.shippingItems));
    ajax.start();
}

/**
 * 关闭此对话框
 */
ShippingInfoDlg.close = function () {
    parent.layer.close(1);
}

$(function() {
    ShippingInfoDlg.toHtml(ShippingInfoDlg.orderItems);
    $("input[name='shipQuantity']").on("blur",function(){
        var reg = /^[0-9]$/;
        if(reg.test($(this).val())){
            if($(this).val()>$(this).parent().parent().prev().text()){
            Feng.alert("发货数量不能大于购买数量！",null);}
        }else{
            Feng.alert("发货数量只能为数字！",null);
        }
    })
});
