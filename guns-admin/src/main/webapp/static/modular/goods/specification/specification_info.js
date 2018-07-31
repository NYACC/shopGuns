/**
 * 初始化通知详情对话框
 */
var SpecificationInfoDlg = {
    tagInfoData: {},
    editor: null,
    validateFields: {
        title: {
            validators: {
                notEmpty: {
                    message: '标题不能为空'
                }
            }
        }
    },
    specificationName: '',			//字典的名称
    mutiString: '',		//拼接字符串内容(拼接字典条目)
    categoryId:'',
    sequence:'',
    id:'',
    itemTemplate: $("#itemTemplate").html()
};

/**
 * 添加条目
 */
SpecificationInfoDlg.addItm = function () {
    $("#itemsArea").append(this.itemTemplate);
};

/**
 * 删除item
 */
SpecificationInfoDlg.deleteItm = function (event) {
    var obj = Feng.eventParseObject(event);
    obj.parent().parent().remove();
};
/**
 * 实时添加条目
 */
SpecificationInfoDlg.addItem = function () {
	//实时添加
	var pid=$("#id").val();
    var ajax = new $ax(Feng.ctxPath + "/specification/addItem", function (data) {
    	$("#itemsArea").append(SpecificationInfoDlg.itemTemplate);
    	$("#itemsArea").find("input[name='id']:last").val(data);
    	window.parent.Specification.table.refresh();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set('pid',pid);
    ajax.start();
};

/**
 * 实时删除item
 */
SpecificationInfoDlg.deleteItem = function (event) {
    var item = Feng.eventParseObject(event).parent().parent();
    //实时删除
    var itemId=$(item).find("input[name='id']").val();
    var ajax = new $ax(Feng.ctxPath + "/specification/deleteItem", function (data) {
        item.remove();
        window.parent.Specification.table.refresh();
    }, function (data) {
        Feng.error("删除失败!" + data.responseJSON.message + "!");
    });
    ajax.set('itemId',itemId);
    ajax.start();
};

/**
 * 清除为空的item Dom
 */
SpecificationInfoDlg.clearNullDom = function(){
    $("[name='dictItem']").each(function(){
        var num = $(this).find("[name='itemNum']").val();
        var name = $(this).find("[name='itemName']").val();
        if(num == '' || name == ''){
            $(this).remove();
        }
    });
};

/**
 * 清除数据
 */
SpecificationInfoDlg.clearData = function () {
    this.tagInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SpecificationInfoDlg.set = function (key, val) {
    this.tagInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
SpecificationInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
SpecificationInfoDlg.close = function () {
    parent.layer.close(window.parent.Specification.layerIndex);
}

/**
 * 收集数据
 */
SpecificationInfoDlg.collectData = function () {
	this.clearNullDom();
    var mutiString = "[";
    $("[name='specificationItem']").each(function(){
    	var id = $(this).find("[name='id']").val();
        var num = $(this).find("[name='itemNum']").val();
        var name = $(this).find("[name='itemName']").val();
        if(id==undefined){
        	 mutiString = mutiString + ('{"name":"'+name+'","sequence":"'+num+'"},');
        }else{
        	 mutiString = mutiString + ('{"id":"'+id+'","name":"'+name+'","sequence":"'+num+'"},');
        }
       
    });
    this.specificationName = $("#specificationName").val();
    this.mutiString = mutiString+"]";
    this.categoryId=$("#categoryId").val();
    this.sequence=$("#sequence").val();
    this.id=$("#id").val();
}

/**
 * 验证数据是否为空
 */
SpecificationInfoDlg.validate = function () {
    $('#noticeInfoForm').data("bootstrapValidator").resetForm();
    $('#noticeInfoForm').bootstrapValidator('validate');
    return $("#noticeInfoForm").data('bootstrapValidator').isValid();
};

/**
 * 提交添加
 */
SpecificationInfoDlg.addSubmit = function () {
	
	this.collectData();
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/specification/add", function (data) {
        Feng.success("添加成功!");
        window.parent.Specification.table.refresh();
        SpecificationInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set('name',this.specificationName);
    ajax.set('specificationValues',this.mutiString);
    ajax.set('sequence',this.sequence);
    ajax.set('categoryId',this.categoryId);
    ajax.start();
}

/**
 * 提交修改
 */
SpecificationInfoDlg.editSubmit = function () {

	this.collectData();
    var ajax = new $ax(Feng.ctxPath + "/specification/edit", function (data) {
        Feng.success("修改成功!");
        window.parent.Specification.table.refresh();
        SpecificationInfoDlg.close();
    }, function (data) {
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set('id',this.id);
    ajax.set('name',this.specificationName);
    ajax.set('specificationValues',this.mutiString);
    ajax.set('sequence',this.sequence);
    ajax.set('categoryId',this.categoryId);
    ajax.start();
}

$(function () {
	Feng.initValidator("tagInfoForm", SpecificationInfoDlg.validateFields);
});
