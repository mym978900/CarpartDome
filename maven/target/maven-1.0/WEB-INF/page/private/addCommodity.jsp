<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>新增货架</title>
    <meta name="decorator" content="default"/>
    <link href="${ctx}/css/index.css" rel="stylesheet" type="text/css" />
    <script src="${ctx}/js/jquery.min.js" type="text/javascript"></script>
    <script src="${ctx}/js/treeTable/jquery.treeTable.js" type="text/javascript"></script>
    <script src="${ctx}/js/zTree/js/jquery.ztree.core-3.5.js" type="text/javascript"></script>
    <link href="${ctx}/js/zTree/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css"/>
    <link href="${ctx}/js/treeTable/themes/vsStyle/treeTable.css" rel="stylesheet" type="text/css"/>
    <%--<style type="text/css">.table td i{margin:0 2px;}</style>--%>
    <SCRIPT type="text/javascript">
        <!--
        var setting = {
            view: {
                dblClickExpand: false,
                selectedMulti:false
            },
            data: {
                simpleData: {
                    enable: true
                }
            },
            callback: {
                //beforeClick: beforeClick,
                onClick: onClick
            }
        };

        var zNodes =[
            <c:forEach items="${menuList}" var="menu">{id:"${menu.id}", pId:"${menu.parent.id}", name:"${menu.name}"},
            </c:forEach>]

        function beforeClick(treeId, treeNode) {
            var check = (treeNode && !treeNode.isParent);
            if (!check) alert("");
            return check;
        }

        function onClick(e, treeId, treeNode) {
            var zTree = $.fn.zTree.getZTreeObj("menuTree"),
                    nodes = zTree.getSelectedNodes(),
                    v = "";

             //alert(nodes[0].name+" "+nodes[0].mid);
           var nName=nodes[0].name;
             var nId=nodes[0].id;


            nodes.sort(function compare(a,b){return a.id-b.id;});
            for (var i=0, l=nodes.length; i<l; i++) {
                v += nodes[i].name + ",";
            }
            if (v.length > 0 ) v = v.substring(0, v.length-1);
            //cityObj.attr("value", v);
            var parentMenuObj = $("#parentMenuObj");
            var parentIdObj = $("#parentId");

            parentMenuObj.val(nName);
            parentIdObj.val(nId);

            hideMenu();
        }

        function showMenu() {
            var cityObj = $("#parentMenuObj");
            var cityOffset = $("#parentMenuObj").offset();
            $("#menuContent").css({left:cityOffset.left + "px", top:cityOffset.top + cityObj.outerHeight() + "px"}).slideDown("fast");

            $("body").bind("mousedown", onBodyDown);

        }
        function hideMenu() {
            $("#menuContent").fadeOut("fast");
            $("body").unbind("mousedown", onBodyDown);
        }
        function onBodyDown(event) {
            if (!(event.target.id == "menuBtn" || event.target.id == "menuContent" || $(event.target).parents("#menuContent").length>0)) {
                hideMenu();
            }
        }

        $(document).ready(function(){
            var tree=$.fn.zTree.init($("#menuTree"), setting, zNodes);
            tree.expandAll(true);
            var node = tree.getNodeByParam("id", "${menuObj.parent.id}");
            tree.selectNode(node);
            //try{tree.checkNode(node, true, false);}catch(e){}
        });
        //-->
        
         function checkform(){
            carModelId=$("#parentMenuObj").val();
            carPartsId=$("#partId").val();
            title=$("#title").val();

            if(carModelId.length ==0){
                alert("请选择汽车品牌！");
                return false;
            }
            if(carPartsId.length == 0){
                alert("请选择配件类别！");
                return false;
            }
            if(title.length == 0){
                alert("标题不能为空");
                return false;
            }

            return true;
        }
        function uploadImg() {
            //debugger
            $.ajax({
                type: "post",
                url: '${ctx}/private/addCommodity',
                data: new FormData($('#uploadForm')[0]),//https://developer.mozilla.org/zh-CN/docs/Web/API/FormData/Using_FormData_Objects
                processData: false,//不希望转换的信息  https://segmentfault.com/q/1010000007410014
                contentType: false,//默认情况下会将发送的数据序列化contentType = "application/x-www-form-urlencoded"https://segmentfault.com/q/1010000007410014
                success: function (data) {
                    $("#img").html("上传成功");
                    alert(data);
                    $("#img").attr("src", data);
                }

            });
        }
    </SCRIPT>


    <style type="text/css">
        ul.ztree {margin-top: 10px;border: 1px solid #617775;background: #f0f6e4;width:220px;height:230px;overflow-y:scroll;overflow-x:auto;}
        ul.log {border: 1px solid #617775;background: #f0f6e4;width:300px;height:170px;overflow: hidden;}
        ul.log.small {height:45px;}
        ul.log li {color: #666666;list-style: none;padding-left: 10px;}
        ul.log li.dark {background-color: #E3E3E3;}

        #menuEdit td{
            line-height: 32px;
        }
        
    </style>

</head>
<body >
    
    <body style="background:#efeeec;">
    ${userId}
<div class="pagebody6" style="padding:0px;">
        <form id="uploadForm" enctype="multipart/form-data">
        <h1 style="font-size:30px;"><span style="margin-left:150px">新增商品</span></h1>
        <div class="margin1 padding3"><span>汽车品牌</span>
                <p style="display:inline-block">
                                        <span><input type="text" class="xinzeng" id="parentMenuObj" name="" value="" readonly="true" onclick="showMenu(); return false;"></span>
                                    </p>   <input type="hidden" id="parentId" name="carmodelId" value="${menuObj.parent.id}">
                                    &nbsp;<a id="menuBtn" style="margin-top:4px;" href="#" onclick="showMenu(); return false;">选择</a>
        </div>
        <div class="margin1 padding3"><span>配件类别</span>
                <select class="xinzeng" id="partId" name="carpartsId" onchange="carPartsChange()">
                        <option value="">请选择</option>
                        <c:forEach var="cp" items="${accessoriescategoryList}">
                                <option value="${cp.id}">${cp.name}</option>
                        </c:forEach>
                </select>
        </div>
        <div class="margin1 padding3"><span>商品标题</span>
                <input type="text" id="title" class="xinzeng" name="title" />
        </div>
        <div class="margin1 padding3"><span>图片路径</span>
                <input class="xinzeng" type="file" name="file" id="button" value="选择上传" />
        </div>

        <div class="margin1 padding3">
                <span class="padding4">货物描述</span>
                <textarea id="content" name="content" class="miaoshu"></textarea>
        </div>
            <button id="upload" type="button"  onclick="uploadImg()" >添加到我的货架</button>
            <div id="menuContent" class="menuContent" style="display:none; position: absolute;">
                            <ul id="menuTree" class="ztree" style="margin-top:0;"></ul>
                        </div>
        </form>
    <img src="${imgurl}" alt="" width="100px" height="100px" id="img"/>
    </div>
</body>
</body>
</html>
