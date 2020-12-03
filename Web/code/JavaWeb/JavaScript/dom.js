window.onload = function() {
    // 查找#bj 节点
    document.getElementById("btn01").onclick = function () {
        let bj = document.getElementById("bj");
        alert(bj.innerHTML)
    };

    // 查找所有 li 节点
    document.getElementById("btn02").onclick = function () {
        let li = document.getElementsByTagName("li");
        alert(li.length);
    };

    // 查找 name=gender 的所有节点
    document.getElementById("btn03").onclick = function () {
        let gender = document.getElementsByName("gender");
        alert(gender.length)
    };

    // 查找#city 下所有 li 节点
    document.getElementById("btn04").onclick = function () {
        // 1. 获取 id 为 city 的节点
        // 2. 通过 city 节点.getElementsByTagName 按标签名查子节点
        let elementsByTagName = document.getElementById("city").getElementsByTagName("li");
        alert(elementsByTagName.length);
    };

    // 返回#city 的所有子节点
    document.getElementById("btn05").onclick = function () {
        let length = document.getElementById("city").childNodes.length;
        alert(length);  // 9
    };

    // 返回#os 的第一个子节点
    document.getElementById("btn06").onclick = function () {
        let firstChild = document.getElementById("os").firstChild;
        alert(firstChild.innerHTML)
    };

    // 返回#bj 的父节点
    document.getElementById("btn07").onclick = function () {
        let parentNode = document.getElementById("bj").parentNode;
        alert(parentNode.innerHTML);
    };

    // 返回#Linux 的前一个兄弟节点
    document.getElementById("btn08").onclick = function () {

    };

    // 返回#username 的 value 属性值
    document.getElementById("btn09").onclick = function () {

    };

    // 设置#username 的 value 属性值
    document.getElementById("btn10").onclick = function () {

    };

    // 返回#bj 的文本值
    document.getElementById("btn11").onclick = function () {

    };
};



