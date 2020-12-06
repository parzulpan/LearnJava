# jQuery 基础

## 介绍

顾名思义，它是 JavaScript 和 查询，是辅助 JavaScript 开发的类库。

它的核心思想是 `write less, do more.` 所以它实现了很多浏览器的兼容问题。

它现在已经成为最流行的 JavaScript 库，在世界前 10000 个访问最多的网站中，有超过 55% 在使用它。

它是免费、开源的，它的语法设计可以使开发更加便捷，例如操作文档对象、选择 DOM 元素、制作动画效果、事件处理、使用 Ajax 以及其他功能。

## 初体验

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>jQuery 体验</title>
<!--    CDN-->
<!--    <script src="https://cdn.bootcdn.net/ajax/libs/jquery/3.5.1/jquery.js"></script>-->
    <script type="text/javascript" src="../lib/jquery.js"></script>
    <script type="text/javascript">
        // 原生方法给按钮绑定单击事件
        window.onload = function () {
            // dom 对象
            let btn01 = document.getElementById("btn01");
            btn01.onclick = function () {
                alert("JS 原生的单击事件")
            }
        };

        // jQuery 给按钮绑定单击事件
        $(function () {
            // jQuery 对象
            let $btn02 = $("#btn02");
            $btn02.click(function () {
                alert("jQuery 原生的单击事件")
            })
        });
    </script>
</head>
<body>
    <button id="btn01">按钮1</button>
    <button id="btn02">按钮2</button>

</body>
</html>
```

## 核心函数

`$` 是 jQuery 核心函数，能完成 jQuery 的很多功能。`$()` 就是调用 `$` 这个函数。

对于 `$(Obj)`：

* 当 Obj 为一个 **函数** 时，表示页面加载完成之后，相当于 `window.inload = function(){}`
* 当 Obj 为一个 **HTML 字符串** 时，会为我们创建这个 HTML 标签对象
* 当 Obj 为一个 **选择器字符串** 时：
  * Obj 为 **id 属性值**时（`$(#id 属性值)`），即 id 选择器，会根据 id 属性查询标签对象
  * Obj 为 **class 属性值**时（`$(.class 属性值)`），即 class 选择器，会根据 class 属性查询标签对象
  * Obj 为 **标签名**时（`$(标签名)`），即标签名选择器，会根据标签名查询标签对象
* 当 Obj 为一个 **函数** 时，会把这个 DOM 对象 转换为 jQuery 对象

## jQuery 对象 和 DOM 对象

**DOM 对象**：

* DOM 对象 Alert 出来的效果是：`[object HTML标签名Element]`
* 通过 `getElementById()` 查询出来的标签对象是 DOM 对象
* 通过 `getElementsByName()` 查询出来的标签对象是 DOM 对象
* 通过 `getElementsByTagName()` 查询出来的标签对象是 DOM 对象
* 通过 `createElement()` 方法创建的对象，是 DOM 对象
* DOM 对象不能使用 jQuery 对象的属性和方法
* DOM 对象 转换为 jQuery 对象 -> `$( DOM 对象 )`

**jQuery 对象**：

* jQuery 对象 Alert 出来的效果是：`[object Object]`
* 通过 jQuery 提供的 API 创建的对象，是 jQuery 对象
* 通过 jQuery 包装的 DOM 对象，也是 jQuery 对象
* 通过 jQuery 提供的 API 查询到的对象，是 jQuery 对象
* jQuery 对象不能使用 DOM 对象的属性和方法
* jQuery 对象 转换为 DOM 对象 -> `jQuery 对象[下标]`

**jQuery 对象 是 DOM 对象的数组 和 jQuery 提供的一系列功能函数。**

### jQuery 选择器

#### 基本选择器

* `#ID 选择器`：根据 id 查找标签对象
* `.class 选择器`：根据 class 查找标签对象
* `element 选择器`：根据标签名查找标签对象
* `* 选择器`：表示任意的，所有的元素
* `selector1，selector2 组合选择器`：合并选择器 1，选择器 2 的结果并返回

#### 层级选择器

* `ancestor descendant 后代选择器` ：在给定的祖先元素下匹配所有的后代元素
* `parent > child 子元素选择器`：在给定的父元素下匹配所有的子元素
* `prev + next 相邻元素选择器`：匹配所有紧接在 prev 元素后的 next 元素
* `prev ~ sibings 之后的兄弟元素选择器`：匹配 prev 元素之后的所有 siblings 元素

#### 过滤选择器

**基本过滤器**：

* `:first` 获取第一个元素
* `:last` 获取最后个元素
* `:not(selector)` 去除所有与给定选择器匹配的元素
* `:even` 匹配所有索引值为偶数的元素，从 0 开始计数
* `:odd` 匹配所有索引值为奇数的元素，从 0 开始计数
* `:eq(index)` 匹配一个给定索引值的元素
* `:gt(index)` 匹配所有大于给定索引值的元素
* `:lt(index)` 匹配所有小于给定索引值的元素
* `:header` 匹配如 h1, h2, h3 之类的标题元素
* `:animated` 匹配所有正在执行动画效果的元素

**内容过滤器**：

* `:contains(text)` 匹配包含给定文本的元素
* `:empty` 匹配所有不包含子元素或者文本的空元素
* `:parent` 匹配含有子元素或者文本的元素
* `:has(selector)` 匹配含有选择器所匹配的元素的元素

**属性过滤器**：

* `[attribute]` 匹配包含给定属性的元素
* `[attribute=value]` 匹配给定的属性是某个特定值的元素
* `[attribute!=value]` 匹配所有不含有指定的属性，或者属性不等于特定值的元素。
* `[attribute^=value]` 匹配给定的属性是以某些值开始的元素
* `[attribute$=value]` 匹配给定的属性是以某些值结尾的元素
* `[attribute*=value]` 匹配给定的属性是以包含某些值的元素
* `[attrSel1][attrSel2][attrSelN]` 复合属性选择器，需要同时满足多个条件时使用

**表单过滤器**：

* `:input` 匹配所有 input, textarea, select 和 button 元素
* `:text` 匹配所有文本输入框
* `:password` 匹配所有的密码输入框
* `:radio` 匹配所有的单选框
* `checkbox` 匹配所有的复选框
* `:submit` 匹配所有提交按钮
* `:image` 匹配所有 img 标签
* `:reset` 匹配所有重置按钮
* `:button` 匹配所有 `input type=button` 按钮
* `:file` 匹配所有 `input type=file` 文件上传
* `:hidden` 匹配所有不可见元素 `display:none` 或 `input type=hidden`

**表单对象属性过滤器**：

* `:enabled` 匹配所有可用元素
* `:disabled` 匹配所有不可用元素
* `:checked` 匹配所有选中的单选，复选，和下拉列表中选中的 option 标签对象
* `:selected` 匹配所有选中的 option

### jQuery 元素筛选

* `eq()` 获取给定索引的元素 功能跟 `:eq()` 一样
* `first()` 获取第一个元素 功能跟 `:first` 一样
* `last()` 获取最后一个元素 功能跟 `:last` 一样
* `filter(exp)` 留下匹配的元素
* `is(exp)` 判断是否匹配给定的选择器，只要有一个匹配就返回 true
* `has(exp)` 返回包含有匹配选择器的元素的元素 功能跟 `:has` 一样
* `not(exp)` 删除匹配选择器的元素 功能跟 `:not` 一样
* `children(exp)` 返回匹配给定选择器的子元素 功能跟 `parent>child` 一样
* `find(exp)` 返回匹配给定选择器的后代元素 功能跟 `ancestor descendant` 一样
* `next()` 返回当前元素的下一个兄弟元素 功能跟 `prev + next` 功能一样
* `nextAll()` 返回当前元素后面所有的兄弟元素 功能跟 `prev ~ siblings` 功能一样
* `nextUntil()` 返回当前元素到指定匹配的元素为止的后面元素
* `parent()` 返回父元素
* `prev(exp)` 返回当前元素的上一个兄弟元素
* `prevAll()` 返回当前元素前面所有的兄弟元素
* `prevUnit(exp)` 返回当前元素到指定匹配的元素为止的前面元素
* `siblings(exp)` 返回所有兄弟元素
* `add()` 把 add 匹配的选择器的元素添加到当前 jquery 对象中

### jQuery 属性操作

* `html()` 设置和获取起始标签和结束标签中的内容。跟 dom 属性 innerHTML 一样。
* `text()` 设置和获取起始标签和结束标签中的文本。跟 dom 属性 innerText 一样。
* `val()` 获取匹配元素集中第一个元素的当前值，或设置每个匹配元素的值。跟 dom 属性 value 一样。**它可以同时设置多个表单项的选中状态**。
* `attr()` 获取匹配元素集中第一个元素的属性值，或为每个匹配元素设置一个或多个属性。不推荐操作 checked、readOnly、selected、disabled 等。它还可以操作非标准的属性，比如自定义属性。
* `prop()` 获取匹配元素集中第一个元素的属性值，或为每个匹配元素设置一个或多个属性。只推荐操作 checked、readOnly、selected、disabled 等。

**不传参数是获取值，传入参数是设置值。**

```html
<<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>jQuery 属性操作</title>
    <script type="text/javascript" src="../lib/jquery.js"></script>
    <script type="text/javascript">
        $(function () {
            // 批量操作单选按钮选中状态
            $(":radio").val(["radio2"]);

            // 批量操作多选按钮选中状态
            $(":checkbox").val(["checkbox3", "checkbox2"]);

            // 批量操作多选下拉框选中状态
            $("#multiple").val(["mul2", "mul3", "mul4"]);

            // 批量操作单选下拉框选中状态
            $("#single").val(["sin2"]);

            //
            alert($("#multiple").val());    // mul2,mul3,mul4

            // 也可以简化操作
            $(":radio, :checkbox, #multiple, #single").val(["radio2", "checkbox3", "checkbox2", "mul2", "mul3", "mul4", "sin2"])

        })
    </script>
</head>
<body>
    单选：
    <input name="radio" type="radio" value="radio1">radio1
    <input name="radio" type="radio" value="radio2">radio2
    <br>
    多选：
    <input name="checkbox" type="checkbox" value="checkbox1">checkbox1
    <input name="checkbox" type="checkbox" value="checkbox2">checkbox2
    <input name="checkbox" type="checkbox" value="checkbox3">checkbox3
    <br>
    下拉多选：
    <select id="multiple" multiple="multiple" size="4">
        <option value="mul1">mul1</option>
        <option value="mul2">mul2</option>
        <option value="mul3">mul3</option>
        <option value="mul4">mul4</option>
    </select>
    <br>
    <br>
    <br>
    下拉单选：
    <select id="single">
        <option value="sin1">sin1</option>
        <option value="sin2">sin2</option>
        <option value="sin3">sin3</option>
    </select>

</body>
</html>
```

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>jQuery 属性操作 - 选项 </title>
    <script type="text/javascript" src="../lib/jquery.js"></script>
    <script type="text/javascript">
        $(function () {
            // 给全选按钮绑定单击事件
            $("#checkedAllBtn").click(function () {
                $(":checkbox").prop("checked", true);
            });

            // 给全不选按钮绑定单击事件
            $("#checkedNoBtn").click(function () {
                $(":checkbox").prop("checked", false);
            });

            // 给反选按钮绑定单击事件
            // 查询全部的球类的复选框
            $("#checkedRevBtn").click(function () {
                $(":checkbox[name='items']").each(function () {
                    // 在 each 遍历的 function 函数中，有一个 this 对象，这个 this 对象是当前正在遍历到的 dom 对象
                    this.checked = !this.checked;
                });

                // 检查是否是满选
                let allCount = $(":checkbox[name='items']").length;
                let checkedCount = $(":checkbox[name='items']:checked").length;
                $("#checkAllBox").prop("checked", allCount === checkedCount);
            });

            // 提交按钮的单击事件
            $("#sendBtn").click(function () {
                //
                $(":checkbox[name='items']:checked").each(function () {
                    alert(this.value);
                })
            });

            // 给全选/全不选单选框绑定单击事件
            $("#checkAllBox").click(function () {
                $(":checkbox[name='items']").prop("checked", this.checked);
            })

            // 给全部球类绑定单击事件
            $(":checkbox[name='items']").click(function () {
                let allCount = $(":checkbox[name='items']").length;
                let checkedCount = $(":checkbox[name='items']:checked").length;
                $("#checkAllBox").prop("checked", allCount === checkedCount);
            })
        });
    </script>
</head>
<body>
    <from method = "post" action="">
        您爱好的运动是？<input type="checkbox" id="checkAllBox">全选/全不选
        <br>
        <input type="checkbox" name="items" value="篮球">篮球
        <input type="checkbox" name="items" value="足球">足球
        <input type="checkbox" name="items" value="羽毛球">羽毛球
        <input type="checkbox" name="items" value="乒乓球">乒乓球
        <br>
        <input type="button" id="checkedAllBtn" value="全 选">
        <input type="button" id="checkedNoBtn" value="全不选">
        <input type="button" id="checkedRevBtn" value="反 选">
        <input type="button" id="sendBtn" value="提 交">
    </from>
</body>
</html>
```

## DOM 增删改

* `appendTo` 例如 a.appendTo(b)，把 a 插入到 b 所有子元素末尾，成为最后一个子元素
* `prependTo()` 例如 a.prependTo(b)，把 a 插入到 b 所有子元素前面，成为第一个子元素
* `insertAfter()` 例如 a.insertAfter(b)，得到 ba
* `insertBefore()` 例如 a.insertBefore(b)，得到 ab
* `replaceWith()` 例如 a.replaceWith(b)，用 a 替换掉 b
* `replaceAll()` 例如 a.replaceAll(b)，用 a 替换掉所有 b
* `remove()` 例如 a.remove()，删除 a 标签
* `empty()` 例如 a.empty()，清空 a 标签里的内容

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>从左到右，从右到左</title>
    <style type="text/css">
        select {
            width: 100px;
            height: 140px;
        }

        div {
            width: 130px;
            float: left;
            text-align: center;
        }
    </style>
    <script type="text/javascript" src="../lib/jquery.js"></script>
    <script type="text/javascript">
        $(function () {
            // 第一个按钮
            $("button:eq(0)").click(function () {
                $("select:eq(0) option:selected").appendTo($("select:eq(1)"));
            });

            // 第二个按钮
            $("button:eq(1)").click(function () {
                $("select:eq(0) option").appendTo($("select:eq(1)"));
            });

            // 第三个按钮
            $("button:eq(2)").click(function () {
                $("select:eq(1) option:selected").appendTo($("select:eq(0)"));
            });

            // 第四个按钮
            $("button:eq(3)").click(function () {
                $("select:eq(1) option").appendTo($("select:eq(0)"));
            });
        })
    </script>
</head>
<body>
    <div id="left">
        <select multiple="multiple" name="sel01">
            <option value="opt01">选项 1</option>
            <option value="opt02">选项 2</option>
            <option value="opt03">选项 3</option>
            <option value="opt04">选项 4</option>
            <option value="opt05">选项 5</option>
            <option value="opt06">选项 6</option>
            <option value="opt07">选项 7</option>
            <option value="opt08">选项 8</option>
        </select>

        <button>选中添加到右边</button>
        <button>全部添加到右边</button>
    </div>
    <div id="right">
        <select multiple="multiple" name="sel02">
        </select>
        <button>选中删除到左边</button>
        <button>全部删除到左边</button>
    </div>
</body>
</html>
```

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>动态添加、删除表格记录</title>
    <script type="text/javascript" src="../lib/jquery.js"></script>
    <script type="text/javascript">
        $(function () {
            // 删除函数
            let deleteFun = function () {
                let $trObj = $(this).parent().parent();
                let name = $trObj.find("td:first").text();

                if (confirm("您确定要删除 " + name + " 吗？")) {
                    $trObj.remove();
                }

                return false;
            };

            // 提交按钮点击事件
            $("#addEmpButton").click(function () {
                let empName = $("#empName").val();
                let email = $("#email").val();
                let salary = $("#salary").val();

                // 创建一个行标签对象，添加到显示数据的表格中
                let $trObj = $(
                "<tr>" +
                    "<td>" + empName + "</td>" +
                    "<td>" + email + "</td>" +
                    "<td>" + salary + "</td>" +
                    "<td><a href=\"deleteEmp?id=002\">Delete</a></td>" +
                "</tr>"
                );
                $trObj.appendTo($("#employeeTable"));

                // 给添加的行的 a 标签绑上事件
                $trObj.find("a").click(deleteFun)
            });

            // 给删除的 a 标签绑定单击事件
            $("a").click(deleteFun);

        })

    </script>
</head>
<body>

    <table id="employeeTable" border="2px">
        <tr>
            <th>Name</th>
            <th>Email</th>
            <th>Salary</th>
            <th>&nbsp;</th>
        </tr>
        <tr>
            <td>Tom</td>
            <td>tom@163.com</td>
            <td>18000</td>
            <td><a href="deleteEmp?id=001">Delete</a></td>
        </tr>
        <tr>
            <td>AAA</td>
            <td>AAA@163.com</td>
            <td>134000</td>
            <td><a href="deleteEmp?id=002">Delete</a></td>
        </tr>
        <tr>
            <td>PAf</td>
            <td>PAf@163.com</td>
            <td>20000</td>
            <td><a href="deleteEmp?id=003">Delete</a></td>
        </tr>
    </table>

    <div id="formDiv">
        <h4>添加新员工</h4>

        <table>
            <tr>
                <td class="word">name: </td>
                <td class="inp">
                    <input type="text" name="empName" id="empName">
                </td>
            </tr>
            <tr>
                <td class="word">email: </td>
                <td class="inp">
                    <input type="text" name="email" id="email">
                </td>
            </tr>
            <tr>
                <td class="word">salary: </td>
                <td class="inp">
                    <input type="text" name="salary" id="salary">
                </td>
            </tr>
            <tr>
                <td colspan="2" align="center">
                    <button id="addEmpButton" value="abc">
                        Submit
                    </button>
                </td>
            </tr>
        </table>

    </div>

</body>
</html>
```

## jQuery CSS样式操作

* `addClass()` 添加样式
* `removeClass()` 删除样式
* `toggleClass()` 有就删除，没有就添加样式
* `offset()` 获取和设置元素的坐标

## jQuery 动画操作

**基本动画**：

* `show()` 将隐藏的元素显示
* `hide()` 将可见的元素隐藏
* `toggle()` 可见就隐藏，不可见就显示
* 这三个函数都可以添加参数，第一个参数是**动画的执行的时长**，以毫秒为单位；第二个参数是**动画的回调函数**，即动画完成后自动调用的函数；

**淡入淡出动画**：

* `fadeIn()` 淡入（慢慢可见）
* `fadeOut()` 淡出（慢慢消失）
* `fadeTo()` 在指定时长内慢慢的将透明度修改到指定的值。0 透明，1 完全可见，0.5 半透明
* `fadeToggle()` 淡入/淡出切换

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>CSS_动画 品牌展示</title>
<!--    1.点击按钮的时候，隐藏和显示卡西欧之后的品牌。-->
<!--    2.当显示全部内容的时候，按钮文本为“显示精简品牌” 然后，小三角形向上。所有品牌产品为默认颜色。-->
<!--    3.当只显示精简品牌的时候，要隐藏卡西欧之后的品牌，按钮文本为“显示全部品牌” 然后小三形向下。-->
<!--    并且把佳能，尼康的品牌颜色改为红色（给 li 标签添加 promoted 样式即可）-->
    <style type="text/css">
        * {
            margin: 0;
            padding: 0;
        }

        body {
            font-size: 12px;
            text-align: center;
        }

        a {
            color: #0044DD;
            text-decoration: none;
        }

        a:hover {
            color: #FF5500;
            text-decoration: underline;
        }

        .subCategoryBox {
            width: 600px;
            text-align: center;
            margin: 40px auto 0;
        }

        .subCategoryBox ul {
            list-style: none;
        }

        .subCategoryBox ul li {
            display: block;
            float: left;
            width: 200px;
            line-height: 20px;
        }

        .showMore, .showLess {
            clear: both;
            text-align: center;
            padding-top: 10px;
        }

        .showMore a, .showLess a {
            display: block;
            width: 120px;
            margin: 0 auto;
            line-height: 24px;
            border: 1px solid #AAAAAA;
        }

        .showMore a span {
            padding-left: 15px;
            background: url("../img/down.gif") no-repeat 0 0;
        }

        .showLess a span {
            padding-left: 15px;
            background: url("../img/up.gif") no-repeat 0 0;
        }

        .promoted a {
            color: #FF5500;
        }

    </style>
    <script type="text/javascript" src="../lib/jquery.js"></script>
    <script type="text/javascript">
        $(function () {
            // 基本初始状态
            $("li:gt(5):not(:last)").hide();

            // 给按钮绑定单击事件
            $("div div a").click(function () {
                // 让某些品牌，显示，或隐藏
                $("li:gt(5):not(:last)").toggle();

                // 判断 品牌，当前是否可见
                if ($("li:gt(5):not(:last)").is(":hidden")) {
                    // 品牌隐藏的状态 ：1 显示全部品牌 == 角标向下 showmore
                    $("div div a span").text("显示全部品牌");

                    $("div div").removeClass();
                    $("div div").addClass("showMore");

                    // 去掉高亮
                    $("li:contains('索尼')").removeClass("promoted");
                } else {
                    // 品牌可见的状态：2 显示精简品牌 == 角标向上 showless
                    $("div div a span").text("显示精简品牌");

                    $("div div").removeClass();
                    $("div div").addClass("showLess");

                    // 加高亮
                    $("li:contains('索尼')").addClass("promoted");
                }

                return false;
            })
        })
    </script>
</head>
<body>

    <div class="subCategoryBox">
        <ul>
            <li><a href="#">佳能</a><i>(30440)</i></li>
            <li><a href="#">索尼</a><i>(27220) </i></li>
            <li><a href="#">三星</a><i>(20808) </i></li>
            <li><a href="#">尼康</a><i>(17821) </i></li>
            <li><a href="#">松下</a><i>(12289) </i></li>
            <li><a href="#">卡西欧</a><i>(8242) </i></li>
            <li><a href="#">富士</a><i>(14894) </i></li>
            <li><a href="#">柯达</a><i>(9520) </i></li>
            <li><a href="#">宾得</a><i>(2195) </i></li>
            <li><a href="#">理光</a><i>(4114) </i></li>
            <li><a href="#">奥林巴斯</a><i>(12205) </i></li>
            <li><a href="#">明基</a><i>(1466) </i></li>
            <li><a href="#">爱国者</a><i>(3091) </i></li>
            <li><a href="#">其它品牌相机</a><i>(7275) </i></li>
        </ul>
        <div class="showMore">
            <a href="more.html"><span>显示全部品牌</span></a>
        </div>
    </div>

</body>
</html>
```

## jQuery 事件操作

**`$(function(){})` 和 `window.onload = function(){}` 的区别？**

* **触发时机**：jQery 的页面加载完成之后是浏览器的内核解析完页面的标签创建好 DOM 对象之后就会马上执行；而原生 JS 还需要等标签显示时需要的内通加载完成。
* **触发顺序**：jQery 的页面加载完成之后先执行；而原生 JS 在之后。
* **执行次数**：jQery 的页面加载完成之后是把注册的全部函数，依次全部执行；而原生 JS 只会执行最后一次的赋值函数。

**jQuery 中其他的事件处理方法**：

* `click()` 绑定单击事件，以及触发单击事件，传入参数就是绑定，否则就是触发
* `mouseover()` 鼠标移入事件
* `mouseout()` 鼠标移出事件
* `bind()` 可以给元素一次性绑定一个或多个事件
* `one()` 使用上跟 bind 一样，但是 one 方法绑定的事件只会响应一次
* `unbind()` 跟 bind 方法相反的操作，解除事件的绑定
* `live()` 也是用来绑定事件，可以用来绑定选择器匹配的所有元素的事件，哪怕这个元素是后面动态创建出来的也有效

**事件的冒泡**：

* 事件的冒泡是指父子元素同时监听同一个事件时，当触发子元素的事件的时候，同一个事件也被传递到了父元素的事件里去响应。
* 在子元素事件函数体内，`return false;` 可以阻止事件的冒泡传递。

**JavaScript 事件对象**：

* 事件对象，是封装有触发的事件信息的一个 JavaScript 对象。
* 在给元素绑定事件的时候，在事件的 `function( event )` 参数列表中添加一个参数，这个参数名，我们习惯取名为 event。这个 event 就是 JavaScript 传递参事件处理函数的事件对象。
  * 原生 JS 获取事件对象：

    ```javascript
    window.onload = function () {
        document.getElementById("areaDiv").onclick = function (event) {
            console.log(event);
        }
    }
    ```

  * jQuery 获取事件对象：

    ```javascript
    $(function () {
        $("#areaDiv").click(function (event) {
            console.log(event);
        });
    });
    ```

  * 使用 bind 同时对多个事件绑定同一个函数，获取当前操作是什么事件：

    ```javascript
    $("#areaDiv").bind("mouseover mouseout",function (event) {
        if (event.type == "mouseover") {
            console.log("鼠标移入");
        } else if (event.type == "mouseout") {
            console.log("鼠标移出");
        }
    });
    ```

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>事件 图片跟随</title>
    <style type="text/css">
        body {
            text-align: center;
        }

        #small {
            margin-top: 150px;
        }

        #showBig {
            position: absolute;
            display: none;
        }
    </style>
    <script type="text/javascript" src="../lib/jquery.js"></script>
    <script type="text/javascript">
        $(function () {
            $("#small").bind("mouseover mouseout mousemove", function (event) {
                if (event.type === "mouseover") {
                    $("#showBig").show();
                } else if (event.type === "mousemove") {
                    console.log(event);
                    $("#showBig").offset({
                        left: event.pageX + 10,
                        top: event.pageY + 10
                    });
                } else if (event.type === "mouseout") {
                    $("#showBig").hide();
                }
            });
        });
    </script>
</head>
<body>
    <img id="small" src="../img/small.jpg">

    <div id="showBig">
        <img src="../img/big.jpg">
    </div>
</body>
</html>
```

## 练习和总结
