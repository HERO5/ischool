/// <reference path="common.js" />
//定义几个项目的全局变量[如果用户修改了项目的名称，请更新以下几个变量]
var adminName = 'admin';
var interfaceName = 'interface';
var gatewayName = 'gateway';

window.language = "chinese";
window.isLocalhost = window.location.hostname == "localhost";
window.ctx = "";
if (window.location.protocol == "file:") {
    var url = window.location.href.split("/iSchool/");
    if (url.length == 2) {
        window.ctx = url[0] + "/";
    }
}else {
    //window.ctx = window.location.protocol + "//" + window.location.hostname + ":8080/"+gatewayName+"/";
    window.ctx = window.location.protocol + "//" + window.location.hostname +":" + window.location.port + "/"+gatewayName+"/";
}

window.root = window.ctx + "pages/";
window.static = window.ctx + "static/";
window.notImgUrl = window.static + "images/img_null.png";

//window.server = window.location.protocol + "//" + window.location.hostname +":8080/";
window.server = window.location.protocol + "//" + window.location.hostname + ":" + window.location.port + "/";
window.serverPath = window.server + interfaceName+"/";
//后台上传的图片地址
window.serverUploadPath = window.server + adminName+"/";

window.routerList = {
    index: window.root + "index/index.html",
    contactUs: window.root + "contactUs/contactUs.html",
    module: window.root + "module/module.html",
    menuAndTextlist: window.root + "menuAndTextlist/menuAndTextlist.html",
    menuAndDetail: window.root + "menuAndDetail/menuAndDetail.html"
}

window.path = {
    knowledgeListAll: window.serverPath + "knowledge/api/get",
    knowledgeListStuid: window.serverPath + "knowledge/api/getByStuId"
}

// jc.require.url.push(window.static + "js/jquery.pagination.js");
 jc.require.url.push(window.static + "js/jquery.nicescroll.min.js");

// $.ajax({
//     url: window.path["cmsApiTheme"],
//     data: null,
//     success: function (data) {
//         jc.require.url.push(window.static + data.data.themeUrl);
//     },
//     error: function () {
//         console.log("Theme CSS get error"+ jc.param.stringify(data));
//     },
//
//     async: false
// });

// jc.require.url.push(window.static + "js/dynamic-style.js");
// jc.require.url.push(window.static + "js/uiExtend.js");

jc.require.success = function () {
    if ($("html").niceScroll) {
        $("html").niceScroll({ zindex: 9999, autohidemode: false, cursorwidth: "4px", cursorcolor: "#333", cursorborder: 0, cursoropacitymax: 0.8 });
    }
}

window.arraySortASC = function (array, keyName) {
    if (!array) return array;
    array.sort(function (num1, num2) {
        return parseInt(num1[keyName]) - parseInt(num2[keyName]);
    });
    return array;
}


window.resource = function (path, data, fnSuccess, async) {
    var _data = data;
    if (async == undefined) {
        async = true;
    }
    $.ajax({
        url: window.path[path],
        data: data,
        success: function (res) {
            if (!fnSuccess) return false;
            if (res.success) {
                var data = res.data;
                fnSuccess(data);
                /*if (isLocalhost) {
                    console.log("%c isSuccessTrue : " + window.path[path] + "?" + jc.param.stringify(_data), "color: green", data);
                }
                else {
                    console.log("%c isSuccessFail : " + window.path[path] + "?" + jc.param.stringify(_data), "color: red", res.msg);
                }*/
            }
            else {
                alert("isNull : " + window.path[path] + "?" + jc.param.stringify(_data));
            }
        },
        error: function () {
            console.log("%c isError : " + window.path[path] + "?" + jc.param.stringify(_data), "color: red", res.msg);
        },

        async: async
    });
}

window.router = function (routerListName, param, only) {
    var nowParam = jc.param.getObject();
    var resultParam = "";
    if (!routerListName) {
        routerListName = "index";
    }
    if (only) {
        nowParam = param;
    }
    else {
        for (var attr in param) {
            nowParam[attr] = param[attr];
        }
    }
    resultParam = jc.param.stringify(nowParam);
    if (resultParam) {
        resultParam = "?" + resultParam;
    }
    if (!nowParam.language) {
        resultParam += "&language="+window.language;
    }
    window.location.href = window.routerList[routerListName] + resultParam;
}

// window.reloadPage = function(pageName) {
// 	window.location.href = pageName;
// }