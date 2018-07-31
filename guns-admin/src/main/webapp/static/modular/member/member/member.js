/**
 * 客户初始化
 */
var Member = {
	id: "MemberTable",	//表格id
	seItem: null,		//选中的条目
	table: null,
	layerIndex: -1
};

/**
 * 初始化表格的列
 */
Member.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '客户昵称', field: 'name', align: 'center', valign: 'middle', sortable: true},
        {title: '手机号码', field: 'phoneNum', align: 'center', valign: 'middle', sortable: true},
        {title: '性别', field: 'sexName', align: 'center', valign: 'middle', sortable: true},
        {title: '状态', field: 'statusName', align: 'center', valign: 'middle', sortable: true},
        {title: '会员卡号', field: 'cardSn', align: 'center', valign: 'middle', sortable: true},]
};

/**
 * 点击添加客户
 */
Member.openAdd = function () {
    var index = layer.open({
        type: 2,
        title: '添加客户',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/member/toAdd'
    });
    //layer.full(index);
    this.layerIndex = index;
};


/**
 * 查询客户列表
 */
Member.search = function () {
    var queryData = {};
    queryData['name'] = $("#name").val();
    queryData['phoneNum'] = $("#phoneNum").val();
    Member.table.refresh({query: queryData});
};

/**
 * 是否选中
 */
Member.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
    	Member.seItem = selected[0];
        return true;
    }
};

/**
 * 点击修改客户
 */
Member.openDetail = function () {
	if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '修改客户',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/member/toEdit/' + this.seItem.id
        });
        //layer.full(index);
        this.layerIndex = index;
    }
};

/**
 * 点击启用客户
 */
Member.enable = function () {
	if (this.check()) {
	    var memberId = this.seItem.id;
	    var ajax = new $ax(Feng.ctxPath + "/member/enable", function (data) {
	        Feng.success("启用成功!");
	        Member.table.refresh();
	    }, function (data) {
	        Feng.error("启用失败!" + data.responseJSON.message + "!");
	    });
	    ajax.set("memberId", memberId);
	    ajax.start();
	}
};

/**
 * 点击停用客户
 */
Member.disable = function () {
	if (this.check()) {
	    var memberId = this.seItem.id;
	    var ajax = new $ax(Feng.ctxPath + "/member/disable", function (data) {
	        Feng.success("停用成功!");
	        Member.table.refresh();
	    }, function (data) {
	        Feng.error("停用失败!" + data.responseJSON.message + "!");
	    });
	    ajax.set("memberId", memberId);
	    ajax.start();
	}
};

/**
 * 点击重置密码
 */
Member.resetPwd = function () {
	if (this.check()) {
	    var memberId = this.seItem.id;
	    var ajax = new $ax(Feng.ctxPath + "/member/resetPwd", function (data) {
	        Feng.success("密码重置成功!");
	    }, function (data) {
	        Feng.error("重置失败!" + data.responseJSON.message + "!");
	    });
	    ajax.set("memberId", memberId);
	    ajax.start();
	}
};

$(function () {
    var defaultColunms = Member.initColumn();
    var table = new BSTable(Member.id, "/member/list", defaultColunms);
    table.setPaginationType("client");
    table.init();
    Member.table = table;
});
