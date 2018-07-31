/**
 * 初始化商品详情对话框
 */
var GoodsInfoDlg = {
    goodsInfoData: {},
    zTreeInstance: null,
    specItemMap: {},
    paramJson: null,
    specJson: null,
    productJson: null,
    specImg:null,
    removeProducts:[],
    imgTemplate: $("#imgTemplate").html(),
    validateFields: {
        goodsName: {
            validators: {
                notEmpty: {
                    message: '商品名称不能为空'
                }
            }
        },
        category: {
            validators: {
                notEmpty: {
                    message: '分类不能为空'
                }
            }
        },
        brand: {
            validators: {
                notEmpty: {
                    message: '品牌不能为空'
                }
            }
        },
        marketPrice: {
            validators: {
                notEmpty: {
                    message: '市场价不能为空'
                }
            }
        },
        price: {
            validators: {
                notEmpty: {
                    message: '销售价不能为空'
                }
            }
        }
    }

};

/**
 * 清除数据
 */
GoodsInfoDlg.clearData = function () {
    this.goodsInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
GoodsInfoDlg.set = function (key, val) {
    this.goodsInfoData[key] = (typeof value == "undefined") ? $("#" + key).val() : value;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
GoodsInfoDlg.get = function (key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
GoodsInfoDlg.close = function () {
    parent.layer.close(window.parent.Goods.layerIndex);
}
/**
 * 选择分类
 */
var Goods = {
    layerIndex: -1
};
Goods.selectCategory = function () {
    var index = layer.open({
        type: 2,
        title: '选择分类',
        area: ['300px', '450px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/goods/category_select'
    });
    this.layerIndex = index;
};
/**
 * 点击上传图片
 */
GoodsInfoDlg.openUpload = function () {
    var index = layer.open({
        type: 2,
        title: '上传图片',
        area: ['800px', '560px'], //宽高
        fix: false, //不固定
        maxmin: true,
        end:function(){
        	GoodsInfoDlg.imgLoad();
        },
        content: Feng.ctxPath + '/goods/goods_upload',
        
    });
    this.layerIndex = index;
};
/**
 * 图片相册加载
 */
GoodsInfoDlg.imgLoad = function () {
	//清空相册
	$("#goodsImgs").html("");
	//获取图片
    var ajax = new $ax(Feng.ctxPath + "/goods/imgLoad", function (data) {
    	if(data[0]!=null){
        	for(var i=0; i<data.length; i++){
            	$("#goodsImgs").append(GoodsInfoDlg.imgTemplate);
            	$("#goodsImgs").find("input[name='imgItemId']:last").val(data[i].id);
            	$("#goodsImgs").find("a[name='imgItem']:last").attr("href",Feng.ctxPath + '/kaptcha/' + data[i].url);
            	$("#goodsImgs").find("img[name='imgItem']:last").attr("src",Feng.ctxPath + '/kaptcha/' + data[i].url);
            }
        }
    }, function (data) {
        Feng.error("图片加载失败!" + data.responseJSON.message + "!");
    });
    var imgs = $("#imgs").val();
    imgs = imgs.substring(0, imgs.lastIndexOf(','));
    ajax.set("imgIds", imgs);
    ajax.start();
};
/**
 * 删除图片
 */
GoodsInfoDlg.deleteImg=function(obj){
	var imgItem =$(obj).parent().parent().parent().parent();
	var imgId=$(imgItem).find("input[name='imgItemId']").val();
	var imgs = $("#imgs").val();
	imgs=imgs.replace(imgId+",","");
	$("#imgs").val(imgs);
	$(imgItem).remove();
}
/**
 * 收集数据
 */
GoodsInfoDlg.collectData = function () {
    //收集参数json数据
    paramGroups.init(JSON.parse(localStorage.getItem("paramGroups")));
    this.paramJson = JSON.stringify(paramGroups.getSelectParamGroups());
    //收集参数json数据
    specifications.init(JSON.parse(localStorage.getItem("specGroups")));
    this.specJson = JSON.stringify(specifications.getSelectSpecGroups());
    //收集规格商品的json数据
    this.productJson = localStorage.getItem("products");
}
/**
 * 验证数据是否为空
 */
GoodsInfoDlg.validate = function () {
    $('#goodsInfoForm').data("bootstrapValidator").resetForm();
    $('#goodsInfoForm').bootstrapValidator('validate');
    return $("#goodsInfoForm").data('bootstrapValidator').isValid();
}

/**
 * 提交添加商品
 */
GoodsInfoDlg.addSubmit = function () {
    $('a[href="#tab-1"]').click();
    this.clearData();
    this.collectData();
    if (!this.validate()) {
        return;
    }
    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/goods/add", function (data) {
        Feng.success("添加成功!");
        window.parent.Goods.table.refresh();
        GoodsInfoDlg.close();
    }, function (data) {
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.goodsInfoData);
    ajax.set("name", $("#goodsName").val());
    ajax.set("sn", $("#sn").val());
    ajax.set("brandId", $("#brand").val());
    ajax.set("unit", $("#unit").val());
    ajax.set("paramItems", this.paramJson);
    ajax.set("specItems", this.specJson);
    ajax.set("images", $("#imgs").val());
    ajax.set("specs", this.productJson);
    ajax.set("categoryIds", $("#categoryIds").val());
    ajax.set("tagIds", $("#tagIds").val());
    ajax.set("marketPrice", $("#marketPrice").val());
    ajax.set("price", $("#price").val());
    ajax.set("detail", CKEDITOR.instances.htmlContentT.getData());
    ajax.start();
}
$(function () {
    Feng.initValidator("goodsInfoForm", GoodsInfoDlg.validateFields);
    GoodsInfoDlg.tagSelect();
});


/**
 * 标签多选框点击事件
 */
GoodsInfoDlg.tagSelect = function () {
    $('#tags input').on('ifChanged', function () {
        var tags = $('#tags').find('input[type=checkbox]:checked');
        var tagIds = [];
        for (var i = 0; i < tags.length; i++) {
            tagIds.push($(tags[i]).val());
        }
        $("#tagIds").val(tagIds.join(","));
    }).iCheck({
        checkboxClass: 'icheckbox_square-green',
        radioClass: 'iradio_square-green',
    });
}
/**
 * 切换到参数tab
 */
$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
    $("#param").html("");
    if ($(e.target).html() == "参数配置" && $(".paramGroup").length < 1) {
        paramGroups.init(JSON.parse(localStorage.getItem("paramGroups")));
        $("#param").html(paramGroups.toHtml());
        $("#paramItems").html(paramGroups.getSelectParamGroupsHTML());
        $('.paramGroup').on('ifChanged', function () {
            paramGroups.select($(this).attr("value"));
            $("#paramItems").html(paramGroups.getSelectParamGroupsHTML());
        }).iCheck({
            checkboxClass: 'icheckbox_square-green'
        });
    }
});

/**
 * 参数选择
 * @type {{items: Array}}
 */
function paramGroups() {
    this.items = [];

    this.init = function (data) {
        this.items = [];
        for (var i = 0; i < data.length; i++) {
            this.append(data[i]);
        }
    }

    this.append = function (data) {
        for (var i = 0; i < this.items.length; i++) {
            if (this.items[i].id == data.id) {
                return;
            }
        }
        this.items.push(new paramGroup(data));
    };
    /**
     * 生成参数组页面
     * @returns {string}
     */
    this.toHtml = function () {
        var content = '';
        for (var i = 0; i < this.items.length; i++) {
            content += this.items[i].toHtml();
        }
        return content == '' ? "<div>找不到参数，请先为商品选择分类（分类必须有参数）后，再来填写参数。</div>" : content;
    };

    this.select = function (_id) {
        this.items.forEach(function (paramGroup) {
            if (paramGroup.id == _id) {
                paramGroup.isSelected = !paramGroup.isSelected;
                return;
            }
        });
        localStorage.setItem("paramGroups", JSON.stringify(paramGroups.items));
    }
    this.getSelectParamGroups = function () {
        return this.items.filter(function (paramGroup) {
            return paramGroup.isSelected;
        })
    }

    this.getSelectParamGroupsHTML = function () {
        var selectParamGroups = paramGroups.getSelectParamGroups();
        var itemsHtml = '<table class="table"><thead><tr><th></th><th>值</th></tr></thead><tbody>';
        for (var i = 0; i < selectParamGroups.length; i++) {
            itemsHtml += selectParamGroups[i].getItemsHTML();
        }
        itemsHtml += '</tbody></table>';
        return itemsHtml;
    }

    this.putItemValue = function (_id, _value) {
        this.items.forEach(function (paramGroup) {
            paramGroup.items.forEach(function (item) {
                if (item.id == _id) {
                    item.setValue(_value);
                    return;
                }
            })
        });
        localStorage.setItem("paramGroups", JSON.stringify(paramGroups.items));
    }
};

var paramGroups = new paramGroups();

function paramGroup(data) {
    this.id = data.id;
    this.name = data.name;
    this.categoryId = data.categoryId;
    this.isSelected = data.isSelected ? data.isSelected : false;
    this.items = [];
    for (var i = 0; i < data.items.length; i++) {
        this.items.push(new paramItem(data.items[i]));
    }

    this.toHtml = function () {
        var _input = $("<input />")
            .addClass("paramGroup i-checks")
            .attr("type", "checkbox")
            .val(this.id);
        if (this.isSelected) {
            _input.attr("checked", "true");
        }
        var _span = $("<span />").text(this.name);

        var _lable = $("<lable></lable>");
        _lable.append(_input, _span);

        var content = $("<div></div>").css("margin-top", "15px");
        content.append(_lable);

        return content.html();
    };

    this.getItemsHTML = function () {
        var content = '';
        for (var j = 0; j < this.items.length; j++) {
            content += this.items[j].toHtml();
        }
        return content;
    }
};

function paramItem(item) {
    this.id = item.id;
    this.name = item.name;
    this.pid = "";
    this.value = item.value ? item.value : "";

    this.setValue = function (_value) {
        this.value = _value;
    }

    this.toHtml = function () {
        var content = '<tr name="params">' +
            '<td>' +
            '<input type="hidden" name="id" value="' + this.id + '">' +
            '<label class="control-label">' + this.name + '</label>' +
            '</td>' +
            '<td>' +
            '<input name="value" type="text" value="' + this.value + '" onkeyup="paramGroups.putItemValue(\'' + this.id + '\',this.value)">' +
            '</td>' +
            '</tr>'
        return content;
    };
}

/**
 * 切换到规格tab
 */
$('a[data-toggle="tab"]').on('shown.bs.tab', function (e) {
    $("#spec").html("");
    if ($(e.target).html() == "规格配置" && $(".specDiv").length < 1) {
        specifications.init(JSON.parse(localStorage.getItem("specGroups")));
        $("#spec").html(specifications.toGroupHtml());
        products.init(JSON.parse(localStorage.getItem("products")));
        $("#specItems").html(products.toHtml());
        $("#specItems input").on('keyup', function () {
            products.putProductValue($(this).parent().parent().find("label").html(),
                $(this).attr("name"), $(this).val());
        })
        //规格多选框点击事件
        $('.specItem').on('ifChanged', function () {
            specifications.select($(this).attr("value"));
            products.create(specifications.getSelectSpecGroups());
            $("#specItems").html(products.toHtml());
            $("#specItems input").on('keyup', function () {
                products.putProductValue($(this).parent().parent().find("label").html(),
                    $(this).attr("name"), $(this).val());
            });
            // 初始化头像上传
            var avatarUp = new $WebUpload("avatar",'/goods/uploadSpecImg');
            avatarUp.init();
        }).iCheck({
            checkboxClass: 'icheckbox_square-green'
        });
    }
});


/**
 * 规格对象组
 */
function specifications() {
    this.items = [];
    this.init = function (data) {
        this.items = [];
        for (var i = 0; i < data.length; i++) {
            this.append(data[i]);
        }
    }
    this.append = function (data) {
        for (var i = 0; i < this.items.length; i++) {
            if (this.items[i].id == data.id) {
                return;
            }
        }
        this.items.push(new specify(data));
    };
    this.toGroupHtml = function () {
        var content = "";
        for (var i = 0; i < this.items.length; i++) {
            content += '<div class="specDiv" style="margin-top:15px;">' + this.items[i].name + ':';
            content += this.items[i].toHtml();
            content += "</div>";
        }
        return content == "" ? "<div >找不到规格，请先为商品选择分类（分类必须有规格）后，再来填写规格。</div>" : content;
    }
    //勾选时修改isSelected属性
    this.select = function (_id) {
        this.items.forEach(function (specify) {
            specify.items.forEach(function (specifyItems) {
                if (specifyItems.id == _id) {
                    specifyItems.isSelected = !specifyItems.isSelected;
                    return;
                }
            })
        });
        localStorage.setItem("specGroups", JSON.stringify(specifications.items));
    }
    this.getSelectSpecGroups = function () {
        var specs = []
        this.items.forEach(function (_specify) {
            if (_specify.getSelectItems().length > 0) {
                var temp = new specify(_specify);
                temp.items = [].concat(_specify.getSelectItems());
                specs.push(temp);
            }
        });
        return specs;
    }

}

var specifications = new specifications();

//规格组
function specify(data) {
    this.id = data.id;
    this.name = data.name;
    this.categoryId = data.categoryId;
    //规格项
    this.items = [];
    for (var i = 0; i < data.items.length; i++) {
        this.items.push(new specifyItems(data.items[i]));
    }
    this.toHtml = function () {
        var content = "";
        for (var i = 0; i < this.items.length; i++) {
            content += this.items[i].toHtml();
        }
        return content;
    }

    this.getSelectItems = function () {
        return this.items.filter(function (specifyItem) {
            return specifyItem.isSelected;
        })
    }
}

function specifyItems(item) {
    this.id = item.id;
    this.pid = item.pid;
    this.name = item.name;
    this.isSelected = item.isSelected ? item.isSelected : false;
    this.toHtml = function () {
        var _input = $("<input/>").addClass("specItem i-checks").attr("type", "checkbox").val(this.id);
        if (this.isSelected) {
            _input.attr("checked", "true");
        }
        var _span = $("<span/>").text(this.name);
        var _lable = $("<lable><lacle/>");
        _lable.append(_input, _span);
        return _lable.html();
    };
}

/**
 * 产品规格对象
 */
var products = new products();

function products() {
    this.items = [];
    this.init = function (data) {
        this.items = [];
        for (var i = 0; i < data.length; i++) {
            this.items.push(new product(data[i]));
        }
    }
    this.create = function (data) {
        var _items = [];
        if (data.length > 0) {
            var groups = this.getGroups(data);
            for (var i = 0; i < groups.length; i++) {
                var p = new product(groups[i]);
                if (this.isExist(p)) {
                    p.init(this.getProduct(p));
                }
                _items.push(p);
            }
            this.items = [].concat(_items);
            localStorage.setItem("products", JSON.stringify([].concat(this.items)));
        }else{
        	this.items=[];
        }
    }
    this.toHtml = function () {
        var specItems = '<table class="table"><thead><tr><th></th><th>规格图片</th><th>条码</th><th>编号</th><th>'
            + '市场价</th><th>销售价</th><th>重量</th><th></th></tr></thead><tbody>';
        for (var i = 0; i < this.items.length; i++) {
            specItems += this.items[i].toHtml();
        }

        specItems += '</tbody></table>';
        return specItems;
    }

    this.getGroups = function (data) {
        var groups = [];

        var firstLevelItems = [].concat(data[0].items);
        if (data.length == 1) {
            firstLevelItems.forEach(function (item) {
                if (item instanceof Array) {
                    groups.push(item);
                } else {
                    var temp = [].concat(item);
                    groups.push(temp);
                }
            });

            return groups;
        }

        var secondLevelItems = [].concat(data[1].items);

        for (var i = 0; i < firstLevelItems.length; i++) {
            for (var j = 0; j < secondLevelItems.length; j++) {
                var temp = [].concat(firstLevelItems[i]);
                temp = temp.concat(secondLevelItems[j]);
                groups.push(temp);
            }
        }

        data.splice(1, 1);
        data[0].items = [].concat(groups);
        return this.getGroups(data);
    }

    this.putProductValue = function (specItems, _name, _value) {
        this.items.forEach(function (product) {
            if (product.getName() == specItems) {
                product[_name] = _value;
            }
        });
        localStorage.setItem("products", JSON.stringify([].concat(products.items)));
    }

    this.removeItem = function (group) {
        var _items = [];
        for (var i = 0; i < group.length; i++) {
            _items = _items.concat(group[i].items);
        }

        for (var i = 0; i < this.items.length; i++) {
            this.items[i].removeItem(_items);
        }
        this.repetition();
    }
    //去重
    this.repetition = function () {
        var hash = {};
        this.items = this.items.reduce(function (item, next) {
            hash[next.getName()] ? '' : hash[next.getName()] = true && item.push(next);
            return item
        }, [])
    }

    this.isExist = function (_product) {
        var flag = false;
        this.items.forEach(function (_item) {
            if (_item.getName() == _product.getName()) {
                flag = true;
            }
        })

        return flag;
    }

    this.getProduct = function (_product) {
        var item = null;
        this.items.forEach(function (_item) {
            if (_item.isExist(_product)) {
                item = _item;
            }
        })

        return item;
    }
}

function product(data) {
	this.id=data.id;
    this.specItems = [];
    if (typeof(data.specItems) == "undefined") {
        this.specItems = this.specItems.concat(data);
    } else {
        if (typeof(data.specItems) == "string") {
            data.specItems = JSON.parse(data.specItems);
        }
        this.specItems = this.specItems.concat(data.specItems);
    }
    this.barcode = data.barcode;
    this.sn = data.sn;
    this.marketPrice = data.marketPrice;
    this.price = data.price;
    this.weight = data.weight;
    this.image=data.image;
    this.imageUrl = '';
    if(typeof(data.image)=="undefined"){
    	this.imageUrl="/kaptcha/girl.gif";
    }else{
    	this.imageUrl=Feng.ctxPath + '/kaptcha/' + data.imageUrl;
    }
   
    this.init = function (data) {
    	this.id=data.id;
        this.specItems = [];
        if (typeof(data.specItems) == "undefined") {
            this.specItems = this.specItems.concat(data);
        } else {
            this.specItems = this.specItems.concat(data.specItems);
        }
        this.barcode = data.barcode;
        this.sn = data.sn;
        this.marketPrice = data.marketPrice;
        this.price = data.price;
        this.weight = data.weight;
        this.image=data.image;
        this.imageUrl = '';
        if(typeof(data.image)=="undefined"){
        	this.imageUrl="/kaptcha/girl.gif";
        }else{
        	this.imageUrl=data.imageUrl;
        }
        this.name=this.getName();
    }

    this.clear = function () {
        this.specItems = [];
        this.barcode = "";
        this.sn = "";
        this.marketPrice = "";
        this.price = "";
        this.weight = "";
        this.name="";
    }
   

    this.toHtml = function () {
        var _name = $("<td></td>").append(
            $("<label></label>").addClass("control-label").html(this.getName()));
       
        var image = $("<td></td>").append(
        		$("<img/>").attr("onclick","GoodsInfoDlg.spec_upload(this)").attr("src",this.imageUrl).attr("height","40px").attr("width","40px"));
        
        var _barcode = $("<td></td>").append($("<input/>").attr("type", "text").attr("name", "barcode").attr("value", this.barcode));

        var _sn = $("<td></td>").append($("<input/>").attr("type", "text").attr("name", "sn").attr("value", this.sn));

        var _marketPrice = $("<td></td>").append(
            $("<input/>").attr("type", "text").attr("name", "marketPrice").attr("value", this.marketPrice));

        var _price = $("<td></td>").append($("<input/>").attr("type", "text").attr("name", "price").attr("value", this.price));

        var _weight = $("<td></td>").append($("<input/>").attr("type", "text").attr("name", "weight").attr("value", this.weight));
        
	    var _tr = $("<tr></tr>").append(_name,image, _barcode, _sn, _marketPrice, _price, _weight);

        var content = $("<div></div>").append(_tr);
        return content.html();
    }

    this.getName = function () {
        var _name = "";
        this.specItems.forEach(function (item) {
            _name += item.name+",";
        });
        if(_name != ""){
        	_name= _name.substring(0, _name.lastIndexOf(',')); 
        }
        return _name;
    }
    this.name=this.getName();
    this.removeItem = function (_items) {
        for (var i = 0; i < _items.length; i++) {
            var _specItems = this.specItems.filter(function (_item) {
                return _item.id != _items[i].id;
            })
            this.specItems = [].concat(_specItems);
        }
    }

    this.isExist = function (_product) {
        return this.getName() == _product.getName();
    }
}
GoodsInfoDlg.spec_upload=function(obj){
	$("#spec_upload").click();
	this.specImg=$(obj);
}
GoodsInfoDlg.uploadSpecImg=function(){
	var options = {
            // 规定把请求发送到那个URL  
            url: Feng.ctxPath + "/goods/upload",
            // 请求方式  
            type: "post",  
            // 服务器响应的数据类型  
            dataType: "json",  
            // 请求成功时执行的回调函数  
            success: function(data, status, xhr) {
            	GoodsInfoDlg.loadSpecImg(data);
            }  
    };
    $("#form").ajaxSubmit(options);
}
GoodsInfoDlg.loadSpecImg=function(img){
	var ajax = new $ax(Feng.ctxPath + "/goods/imgLoad", function (data) {
    	if(data[0]!=null){
    		var specName=GoodsInfoDlg.specImg.parent().parent().find("label").html()
    		
    		products.putProductValue(specName,"imageUrl",Feng.ctxPath + '/kaptcha/' + data[0].url);
    		products.putProductValue(specName,"image",data[0].id);
    		$("#form").find("input").val("");
    		$("#specItems").html(products.toHtml());
        }
    }, function (data) {
        Feng.error("图片加载失败!" + data.responseJSON.message + "!");
    });
	ajax.set("imgIds", img);
    ajax.start();
}
