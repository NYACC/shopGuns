/**
 * 会员卡初始化
 */
var MemberCard = {
	id: "MemberCardTable",	//表格id
	seItem: null,		//选中的条目
	table: null,
	layerIndex: -1,
   queryData : {},
};

/**
 * 初始化表格的列
 */
MemberCard.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '会员卡号', field: 'cardSn', align: 'center', valign: 'middle', sortable: true},
        {title: '客户昵称', field: 'memberName', align: 'center', valign: 'middle', sortable: true},
        {title: '会员卡等级', field: 'cardLevel', align: 'center', valign: 'middle', sortable: true},
        {title: '会员卡状态', field: 'status', align: 'center', valign: 'middle', sortable: true}]
};

/**
 * 点击添加会员卡
 */
MemberCard.openAdd = function () {
    var index = layer.open({
        type: 2,
        title: '添加会员卡',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/memberCard/toAdd'
    });
    //layer.full(index);
    this.layerIndex = index;
};


/**
 * 查询会员卡列表
 */
MemberCard.search = function () {
    MemberCard.queryData={};
    MemberCard.queryData['cardSn'] = $("#cardSn").val();
    MemberCard.queryData['cardLevelId'] = $("[name='level']").val();
    MemberCard.queryData['status'] = $("[name='status']").val();
    MemberCard.table.refresh({query: MemberCard.queryData});
};

/**
 * 是否选中
 */
MemberCard.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        MemberCard.seItem = selected[0];
        return true;
    }
};

/**
 * 点击修改会员卡
 */
MemberCard.openDetail = function () {
	if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '修改会员卡',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/memberCard/toEdit/' + this.seItem.id
        });
        //layer.full(index);
        this.layerIndex = index;
    }
};
/**
 * 点击导出会员卡信息
 */
MemberCard.output = function () {
    location.href=Feng.ctxPath + "/memberCard/memberCard_output?cardSn="+this.queryData.cardSn+"&cardLevelId="+this.queryData.cardLevelId
        +"&status="+this.queryData.status;
};

$(function () {
    var defaultColunms = MemberCard.initColumn();
    var table = new BSTable(MemberCard.id, "/memberCard/list", defaultColunms);
    table.setPaginationType("client");
    table.init();
    MemberCard.table = table;
});
