/**
 * 文章初始化
 */
var Article = {
	id: "ArticleTable",	//表格id
	seItem: null,		//选中的条目
	table: null,
	layerIndex: -1,
	userId:null
};

/**
 * 初始化表格的列
 */
Article.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: '标题', field: 'title', align: 'center', valign: 'middle', sortable: true},
        {title: '作者', field: 'creator', align: 'center', valign: 'middle', sortable: true},
        {title: '创建时间', field: 'createTime', align: 'center', valign: 'middle', sortable: true},
        {title: '操作', field: '', align: 'center', valign: 'middle', sortable: true,formatter : operateFormatter}]
};

function operateFormatter(value, row, index) {
    return '<button icon="fa-check" type="button" onclick="Article.lookLink(\''+row.id+'\')" class="btn btn-primary btn-xs">查看链接</button>';
};

Article.lookLink = function(id){
    var index = layer.open({
        type: 2,
        title: '查看链接',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/Article/toImport/' + id
    });
    //layer.full(index);
    this.layerIndex = index;
    
}

/**
 * 点击添加
 */
Article.openAdd = function () {
    var index = layer.open({
        type: 2,
        title: '添加文章',
        area: ['800px', '440px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/article/toAdd'
    });
    layer.full(index);
    this.layerIndex = index;
};


/**
 * 是否选中
 */
Article.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
    	Article.seItem = selected[0];
        return true;
    }
};

/**
 * 是否可以操作
 */
Article.isOperable = function () {
	if(Article.seItem.creatorId==this.userId){
        return true;
	}else{
		Feng.info("你不是作者，无法修改和删除！");
        return false;
    }
};

/**
 * 点击修改
 */
Article.openDetail = function () {
	if (this.check()&&this.isOperable()) {
        var index = layer.open({
            type: 2,
            title: '修改文章',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/article/toEdit/' + this.seItem.id
        });
        layer.full(index);
        this.layerIndex = index;
    }
};


/**
 * 点击删除
 */
Article.delete = function () {
	if (this.check()&&this.isOperable()) {
	    var articleId = this.seItem.id;
	    var ajax = new $ax(Feng.ctxPath + "/article/delete", function (data) {
	        Feng.success("删除成功!");
	        Article.table.refresh();
	    }, function (data) {
	        Feng.error("删除失败!" + data.responseJSON.message + "!");
	    });
	    ajax.set("articleId", articleId);
	    ajax.start();
	}
};

/**
 * 查询列表
 */
Article.search = function () {
    var queryData = {};
    queryData['title'] = $("#title").val();
    Article.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Article.initColumn();
    var table = new BSTable(Article.id, "/article/list", defaultColunms);
    table.setPaginationType("client");
    table.init();
    Article.table = table;
});
