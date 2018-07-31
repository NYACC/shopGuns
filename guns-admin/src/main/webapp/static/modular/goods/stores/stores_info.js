/**
 * 初始化门店详情对话框
 */
var StoresInfoDlg = {
    storesInfoData : {},
    zTreeInstance : null,
    validateFields: {
        	name: {
            validators: {
                notEmpty: {
                    message: '门店名称不能为空'
                }
            }
        },
        address: {
            validators: {
                notEmpty: {
                    message: '门店地址不能为空'
                }
            }
        },
        num: {
            validators: {
                notEmpty: {
                    message: '门店排序不能为空'
                }
            }
        },
        phone: {
            validators: {
                notEmpty: {
                    message: '门店电话不能为空'
                }
            }
        }
    }
};

/**
 * 清除数据
 */
StoresInfoDlg.clearData = function() {
    this.brandInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StoresInfoDlg.set = function(key, val) {
    this.storesInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
StoresInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
StoresInfoDlg.close = function() {
    parent.layer.close(window.parent.Stores.layerIndex);
}

/**
 * 收集数据
 */
StoresInfoDlg.collectData = function() {
    this.set('id').set('name').set('address').set('num').set('phone').set('lng').set('lat');
    this.storesInfoData['provinceId']=$("#province").val();
    this.storesInfoData['cityId']=$("#city").val();
    this.storesInfoData['countyId']=$("#county").val();
}

/**
 * 验证数据是否为空
 */
StoresInfoDlg.validate = function () {
    $('#storesInfoForm').data("bootstrapValidator").resetForm();
    $('#storesInfoForm').bootstrapValidator('validate');
    return $("#storesInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 提交添加门店
 */
StoresInfoDlg.addSubmit = function() {
    this.clearData();
    this.collectData();

    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/stores/add", function(data){
        Feng.success("添加成功!");
        window.parent.Stores.table.refresh();
        StoresInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.storesInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
StoresInfoDlg.editSubmit = function() {
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/stores/edit", function(data){
        Feng.success("修改成功!");
        window.parent.Stores.table.refresh();
        StoresInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.storesInfoData);
    ajax.start();
}

$(function() {
    Feng.initValidator("storesInfoForm", StoresInfoDlg.validateFields);
    //初始化省
    var ajax = new $ax(Feng.ctxPath + "/area/province", function (data) {
    	var content="<option value=''>请选择省</option>";
    	for(var i=0;i<data.length;i++){
    		content+="<option value='"+data[i].id+"'>"+data[i].name+"</option>"
    	}
    	$("#province").html(content);
    }, function (data) {
    });
    ajax.start();
});

StoresInfoDlg.provinceChange=function(){
	//初始化市
    var ajax = new $ax(Feng.ctxPath + "/area/city", function (data) {
    	var content="<option value=''>请选择市</option>";
    	for(var i=0;i<data.length;i++){
    		content+="<option value='"+data[i].id+"'>"+data[i].name+"</option>"
    	}
    	$("#city").html(content);
    }, function (data) {
    });
    ajax.set("province",$("#province").val());
    ajax.start();
}

StoresInfoDlg.cityChange=function(){
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

StoresInfoDlg.countyChange=function(){
	map.centerAndZoom($("#county").find("option:selected").text(),12);
	myValue=$("#county").find("option:selected").text();
	setPlace();
}
