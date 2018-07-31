$(function() {
    Feng.initValidator("goodsInfoForm", GoodsInfoDlg.validateFields);
    GoodsInfoDlg.init();
});
GoodsInfoDlg.init=function(){
	$("#brand option[value="+$("#brandId").val()+"]").attr('selected', true);
    var tagIds=$("#tagIds").val();
    if(tagIds.indexOf("[")!=-1){
    	tagIds=tagIds.substring(tagIds.indexOf("[")+1,tagIds.lastIndexOf("]"));
    }
    $("#tagIds").val(tagIds);
    GoodsInfoDlg.imgLoad();
    GoodsInfoDlg.getParamData();
    GoodsInfoDlg.getSpecData();
    localStorage.setItem("products", $("#products").val());
}

/**
 *获取参数数据
 */
GoodsInfoDlg.getParamData = function(){
    var ajax = new $ax(Feng.ctxPath + "/goods/paramItems", function (data) {
        paramGroups.init(data);
        var selectedParam=JSON.parse($("#params").val());
        for(var i=0;i<selectedParam.length;i++){
            paramGroups.select(selectedParam[i].id);
            for(var j=0;j<selectedParam[i].items.length;j++){
                paramGroups.putItemValue(selectedParam[i].items[j].id,selectedParam[i].items[j].value);
            }
        }
        localStorage.setItem("paramGroups",JSON.stringify(paramGroups.items));
    }, function (data) {
        Feng.error("获取参数失败!" + data.responseJSON.message + "!");
    });
    ajax.set("categoryIds", $("#categoryIds").val());
    ajax.start();
}
/**
 *获取规格数据
 */
GoodsInfoDlg.getSpecData = function(){
    var ajax = new $ax(Feng.ctxPath + "/goods/specItems", function (data) {
        specifications.init(data);
        var selectedSpec=JSON.parse($("#specs").val());
        for(var i=0;i<selectedSpec.length;i++){
            for(var j=0;j<selectedSpec[i].items.length;j++){
                specifications.select(selectedSpec[i].items[j].id);
            }
        }
        //products.create(specifications.getSelectSpecGroups());
        localStorage.setItem("specGroups",JSON.stringify(specifications.items));
        //slocalStorage.setItem("products", JSON.stringify(products.items));
    }, function (data) {
        Feng.error("获取规格失败!" + data.responseJSON.message + "!");
    });
    ajax.set("categoryIds", $("#categoryIds").val());
    ajax.start();
}
/**
 * 提交修改商品
 */
GoodsInfoDlg.editSubmit = function () {
    $('a[href="#tab-1"]').click();
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/goods/edit", function (data) {
        Feng.success("修改成功!");
        window.parent.Goods.table.refresh();
        GoodsInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set("id",$("#id").val());
    ajax.set(this.goodsInfoData);
    ajax.set("name", $("#goodsName").val());
    ajax.set("sn", $("#sn").val());
    ajax.set("brandId", $("#brand").val());
    ajax.set("unit", $("#unit").val());
    ajax.set("paramItems", this.paramJson);
    ajax.set("specItems",this.specJson);
    ajax.set("images", $("#imgs").val());
    ajax.set("specs", this.productJson);
    var categoryIds=$("#categoryIds").val();
    if(categoryIds.indexOf("[")!=-1){
        categoryIds=categoryIds.substring(categoryIds.indexOf("[")+1,categoryIds.lastIndexOf("]"));
    }
    ajax.set("categoryIds", categoryIds);
    ajax.set("tagIds", $("#tagIds").val());
    ajax.set("marketPrice", $("#marketPrice").val());
    ajax.set("price", $("#price").val());
    ajax.set("detail", CKEDITOR.instances.htmlContentT.getData());
    ajax.start();
}

