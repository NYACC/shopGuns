var CategoryInfoDlg = {
    categoryInfoData: {},
};

CategoryInfoDlg.onClickCategory = function (e, treeId, treeNode) {
    $("#citySel").attr("value", instance.getSelectedVal());
    $("#categoryId").attr("value", treeNode.id);
    CategoryInfoDlg.hideDeptSelectTree();
};

/**
 * 显示部门选择的树
 *
 * @returns
 */
CategoryInfoDlg.showCategorySelectTree = function () {
    var cityObj = $("#citySel");
    var cityOffset = $("#citySel").offset();
    $("#menuContent").css({
        left: cityOffset.left + "px",
        top:  cityOffset.top + cityObj.outerHeight()+ "px"
    }).slideDown("fast");

    $("body").bind("mousedown", onBodyDown);
};


/**
 * 隐藏部门选择的树
 */
CategoryInfoDlg.hideDeptSelectTree = function () {
    $("#menuContent").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);// mousedown当鼠标按下就可以触发，不用弹起
};

function onBodyDown(event) {
    if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(
            event.target).parents("#menuContent").length > 0)) {
        CategoryInfoDlg.hideDeptSelectTree();
    }
}




/**
 * 日志管理初始化
 */
var Specification = {
    id: "SpecificationTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1,
    categoryId:""
};

/**
 * 初始化表格的列
 */
Specification.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
        {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
        {title: '规格名称', field: 'name', align: 'center', valign: 'middle', sortable: true},
        {title: '规格项', field: 'itemsName', align: 'center', valign: 'middle', sortable: true},
        {title: '绑定的分类', field: 'categoryName', align: 'center', valign: 'middle', sortable: true},
        {title: '排序', field: 'sequence', align: 'center', valign: 'middle'},]
};
/**
 * 点击添加
 */
Specification.openAdd = function () {
    var index = layer.open({
        type: 2,
        title: '添加规格组',
        area: ['800px', '560px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/specification/toAdd'
    });
    this.layerIndex = index;
};

/**
 * 点击修改按钮时
 * @param userId 管理员id
 */
Specification.openDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '编辑规格组',
            area: ['800px', '450px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/specification/toEdit/' + this.seItem.id
        });
        this.layerIndex = index;
    }
};
/**
 * 删除标签
 */
Specification.delete = function () {
    if (this.check()) {

        var operation = function(){
            var ajax = new $ax(Feng.ctxPath + "/specification/delete", function () {
                Feng.success("删除成功!");
                Specification.table.refresh();
            }, function (data) {
                Feng.error("删除失败!" + data.responseJSON.message + "!");
            });
            ajax.set("specificationId",Specification.seItem.id);
            ajax.start();
        };

        Feng.confirm("是否刪除该规格组以及该规格组下的所有规格项?", operation);
    }
};
/**
 * 检查是否选中
 */
Specification.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
    	Specification.seItem = selected[0];
        return true;
    }
};
/**
 * 查询部门列表
 */
Specification.search = function () {
    var queryData = {};
    queryData['name'] = $("#name").val();
    queryData['categoryId'] = Specification.categoryId;
    Specification.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Specification.initColumn();
    var table = new BSTable(Specification.id, "/specification/list", defaultColunms);
    var ztree = new $ZTree("treeDemo", "/category/tree");
    ztree.bindOnClick(CategoryInfoDlg.onClickCategory);
    ztree.init();
    instance = ztree;
    
    table.setPaginationType("client");
    Specification.table = table.init();
    
    var ztree = new $ZTree("categoryTree", "/category/tree");
    ztree.bindOnClick(Specification.onClickSort);
    ztree.init();
});

Specification.onClickSort = function (e, treeId, treeNode) {
	Specification.categoryId = treeNode.id;
	Specification.search();
};
