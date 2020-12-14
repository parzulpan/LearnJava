# HTML&CSS 基础

## HTML 基础

### HTML 标签

* HTML标题：HTML 标题（Heading）是通过 `h1 - h6` 等标签进行定义的。
* HTML段落： HTML 段落是通过 `p` 标签 进行定义的。
* HTML链接：HTML 链接是通过 `a` 标签 进行定义的。

    ```html
    <a href="http://www.w3school.com.cn">This is a link</a>
    ```

* HTML图像：HTML 图像是通过 `img` 标签进行定义的。

    ```html
    <img src="w3school.jpg" width="104" height="142" />
    ```

### HTML 元素

* `< body >` 元素 ： 定义了 HTML 文档的主体。
* `< html >` 元素：定义了整个 HTML 文档。
* 空标签：没有内容的 HTML 元素被称为空元素，常见的空标签有 `br`

### HTML 属性

#### 常用的全局属性

属性名称 | 含义
:-- | :---
class | 规定元素的一个或多个类名（引用样式表中的类）
id | 规定元素的唯一 id
lang | 规定元素内容的语言
style | 规定元素的行内 CSS 样式

#### 常用的 window 事件属性

属性名称 | 含义
:-- | :---
onload | 页面结束加载之后触发

#### 常用的 Form 事件属性

属性名称 | 含义
:-- | :---
onblur | 元素失去焦点时运行的脚本
onfocus | 当元素获得焦点时运行的脚本
oninput | 当元素获得用户输入时运行的脚本
onreset | 表单中的重置按钮被点击时触发 HTML5 中不支持
onselect | 在元素中文本被选中后触发
onsubmit | 在提交表单时触发

#### 常用的 Keyboard 事件属性

属性名称 | 含义
:-- | :---
onkeydown | 在用户按下按键时触发
onkeypress | 在用户敲击按钮时触发
onkeyrealse | 当用户释放按键时触发

#### 常用的 Mouse 事件属性

属性名称 | 含义
:-- | :---
onclick | 元素上发生鼠标点击时触发
ondblclick | 元素上发生鼠标双击时触发
ondrag | 元素被拖动时运行的脚本
onmousedown | 当元素上按下鼠标按钮时触发
onmousemove | 当鼠标指针移动到元素上时触发
onmouseout | 当鼠标指针移出元素时触发
onmouseover | 当鼠标指针移动到元素上时触发
onmouseup | 当在元素上释放鼠标按钮时触发
onscroll | 当元素滚动条被滚动时运行的脚本

#### 常用的表单属性

属性名称 | 含义
:-- | :---
form | 定义供用户输入的 HTML 表单
input | 定义输入控件
button | 定义按钮
select | 定义选择列表（下拉列表）
option | 定义选择列表中的选项
label | 定义 input 元素的标注
datalist | 定义下拉列表

#### 常用的列表属性

属性名称 | 含义
:-- | :---
ul | 定义无序列表
ol | 定义有序列表
li | 定义列表的项目
dl | 定义定义列表
dt | 定义定义列表中的项目

#### 常用的表格属性

属性名称 | 含义
:-- | :---
table | 定义表格
th | 定义表格中的表头单元格
tr | 定义表格中的行
td | 定义表格中的单元
thead | 定义表格中的表头内容
tbody | 定义表格中的主体内容
col | 定义表格中一个或多个列的属性值

#### 其他常见属性

属性名称 | 含义
:-- | :---
audio | 定义声音内容
br | 定义简单的折行
canvas | 定义图形
div | 定义文档中的节
diglog | 定义对话框或窗口
em | 定义强调文本
hr | 定义水平线
i | 定义斜体字
img | 定义图像
map | 定义图像映射
meta | 定义关于 HTML 文档的元信息
nar | 定义导航链接
param | 定义对象的参数

### HTML 引用

* 用于短引用的 HTML 标签：`q`

    ```html
    <q>构建人与自然和谐共存的世界。</q>
    ```

* 用于长引用的 HTML 标签：`blockquote`

    ```html
    <blockquote cite="http://www.worldwildlife.org/who/index.html">
    五十年来，WWF 一直致力于保护自然界的未来。
    世界领先的环保组织，WWF 工作于 100 个国家，
    并得到美国一百二十万会员及全球近五百万会员的支持。
    </blockquote>
    ```

### HTML 图像

* 图像标签（`img`）和源属性（`src`）：i**mg 是空标签，意思是说，它只包含属性，并且没有闭合标签**。要在页面上显示图像，你需要使用源属性（src）。src 指 “source”。源属性的值是图像的 URL 地址。

    ```html
    <img src="url" />
    ```

* 替换文本属性（`alt`）:alt 属性用来为图像定义一串预备的可替换的文本。替换文本属性的值是用户定义的。

    ```html
    <img src="boat.gif" alt="Big Boat">
    ```

### HTML 头部元素

* `head` 元素是所有头部元素的容器：`head` 内的元素可包含脚本，指示浏览器在何处可以找到样式表，提供元信息等等。以下标签都可以添加到 `head` 部分：`title`、`base`、`link`、`meta`、`script` 以及 `style`
* `title 标签`：定义文档的标题，title 在所有 HTML/XHTML 文档中都是必需的
* `base 元素`：base 标签为页面上的所有链接规定默认地址或默认目标（target）
  
    ```html
    <head>
    <base href="http://www.w3school.com.cn/images/" />
    <base target="_blank" />
    </head>
    ```

* `link 元素`：link 标签定义文档与外部资源之间的关系，最常用于连接样式表

    ```html
    <head>
    <link rel="stylesheet" type="text/css" href="mystyle.css" />
    </head
    ```

* `style 元素`：style 标签用于为 HTML 文档定义样式信息
* `meta 元素`：元数据（metadata）是关于数据的信息。meta标签提供关于 HTML 文档的元数据。元数据不会显示在页面上，但是对于机器是可读的。典型的情况是，meta 元素被用于规定页面的描述、关键词、文档的作者、最后修改时间以及其他元数据。meta标签始终位于 head 元素中。元数据可用于浏览器（如何显示内容或重新加载页面），搜索引擎（关键词），或其他 web 服务

## HTML URL

* URL 也被称为网址，用于定位万维网上的资源
* 语法规则：`scheme://host.domain:port/path/filename`
  * `scheme` - 定义因特网服务的类型。常见的类型有 http https ftp file
  * `host` - 定义域主机，http 的默认主机是 www
  * `domain` - 定义因特网域名，比如 w3school.com.cn
  * `port` - 定义主机上的端口号，http 的默认端口号是 80
  * `path` - 定义服务器上的路径，如果省略，则资源必须位于网站的根目录中
  * `filename` - 定义资源的名称

## HTML 表单

### 表单基础

* HTML 表单用于搜集不同类型的用户输入
* HTML 表单包含表单元素，表单元素指的是不同类型的 **input 元素**、**复选框**、**单选按钮**、**提交按钮**等

### HTML 输入类型

> input 元素有很多形态，根据不同的 type 属性来区分。

* text     定义供文本输入的单行输入字段
* password 定义密码字段，password 字段中的字符会被做掩码处理
* submit   定义提交表单数据至表单处理程序的按钮
* radio    定义单选按钮
* checkbox 定义复选框
* button   定义按钮
* number   用于应该包含数字值的输入字段，可以对数字做出如下限制：
  * disabled   规定输入字段应该被禁用
  * max        规定输入字段的最大值
  * maxlength  规定输入字段的最大字符数
  * min        规定输入字段的最小值
  * pattern    规定通过其检查输入值的正则表达式
  * readonly   规定输入字段为只读的
  * required   规定输入字段为必须的
  * size       规定输入字段的宽度，以字符计
  * step       规定输入字段的合法数字间隔
  * value      规定输入字段的默认值
* HTML5新增的输入类型：color、date、datetime、datetime-local、email、month、number、range、search、tel、time、url、week

## XHTML 基础

* XHTML 是 HTML 与 XML 的结合物
* XHTML 是更严格更纯净的 HTML 代码
* XHTML 指可扩展超文本标签语言（EXtensible HyperText Markup Language）
* XHTML 元素必须被正确地嵌套
* XHTML 元素必须被关闭
* 标签名必须用小写字母
* XHTML 文档必须拥有一个根元素
* 属性名称必须小写，属性值必须加引号，属性不能简写

## CSS 基础

**概述**：

* CSS 指层叠样式表 （Cascading Style Sheets）
* 样式定义如何显示 HTML 元素
* 样式通常存储在样式表中
* 把样式添加到 HTML 4.0 中，是为了解决内容与表现分离的问题
* 外部样式表可以极大提高工作效率
* 外部样式表通常存储在 CSS 文件中

**基础语法**：

* 由两个主要的部分构成：**选择器**，以及一条或多条**声明**
* 选择器通常是需要改变样式的 HTML 元素
* 每条声明由一个属性和一个值组成
  * 属性（property）是您希望设置的样式属性
  * 每个属性有一个值

**高级语法**：

* **选择器分组**：可以对选择器进行分组，这样，被分组的选择器就可以分享相同的声明，用逗号将需要分组的选择器分开

## CSS 选择器

### 派生选择器

* 派生选择器允许根据文档的上下文关系来确定某个标签的样式，通过合理地使用派生选择器，可以使 HTML 代码变得更加整洁

    ```css
    /* 列表中的 strong 元素变为斜体字 */
    li strong {
        font-style: italic;
        font-weight: normal;
    }
    ```

### id 选择器

* id 选择器可以为标有特定 id 的 HTML 元素指定特定的样式
* **id 属性只能在每个 HTML 文档中出现一次**
* id 选择器以 `#` 来定义

    ```css
    #sidebar {
        border: 1px dotted #000;
        padding: 10px;
    }
    ```

* 在现代布局中，**id 选择器常常用于建立派生选择器**。即使被标注为 sidebar 的元素只能在文档中出现一次，这个 id 选择器作为派生选择器也可以被使用很多次
  
    ```css
    #sidebar p {
        font-style: italic;
        text-align: right;
        margin-top: 0.5em;
    }

    #sidebar h2 {
        font-size: 1em;
        font-weight: normal;
        font-style: italic;
        margin: 0;
        line-height: 1.5;
        text-align: right;
    }
    ```

### 类选择器

* 类选择器以一个**点号**显示，类名的第一个字符**不能使用数字**
* 和 id 选择器 一样，class 选择器也可被用作派生选择器

    ```css
    .center {text-align: center}
    ```

### 属性选择器

* 对带有指定属性的 HTML 元素设置样式

    ```css
    [title] {color:red;}
    ```

* 属性和值选择器

    ```css
    [title=W3School] {border:5px solid blue;}
    ```

### 后代选择器

* 后代选择器又称为包含选择器
* 后代选择器可以选择作为某元素后代的元素
  
    ```css
    div.sidebar {background:blue;}
    ```

### 子元素选择器

* 与后代选择器相比，子元素选择器只能选择作为某元素**子元素**的元素

    ```css
    h1 > strong {color:red;}
    ```

* 可以结合后代选择器的子元素选择器

    ```css
    /* 选择器会选择作为 td 元素子元素的所有 p 元素，这个 td 元素本身从 table 元素继承，该 table 元素有一个包含 company 的 class 属性 */
    table.company td > p
    ```

### 相邻兄弟选择器

* 相邻兄弟选择器可选择紧接在另一元素后的元素，且二者有相同父元素

    ```css
    /* 增加紧接在 h1 元素后出现的段落的上边距 */
    h1 + p {margin-top:50px;}
    ```

### 伪类和伪元素

**伪类**：

* :active 向被激活的元素添加样式
* :focus 向拥有键盘输入焦点的元素添加样式
* :hover 当鼠标悬浮在元素上方时向元素添加样式
* :link 向未被访问的连接添加样式
* :visited 向已被访问的连接添加样式
* :first-child 向元素的第一个子元素添加样式
* lang 向带有指定 lang 属性的元素添加样式

**伪元素**：

* first-letter 向文本的第一个字母添加特殊样式
* first-line 向文本的首行添加特殊样式
* before 在元素之前添加内容
* after 在元素之后添加内容

## CSS 样式

### 背景

* **设置背景色**：可以使用 `background-color` 属性为元素设置背景色，这个属性接受任何合法的颜色值
* **设置背景图像**：要把图像放入背景，需要使用 `background-image` 属性。background-image 属性的默认值是 `none`，表示背景上没有放置任何图像。如果需要设置一个背景图像，必须为这个属性设置一个 URL 值
* **设置背景重复**：如果需要在页面上对背景图像进行**平铺**，可以使用 `background-repeat` 属性。`repeat-x` 和 `repeat-y` 分别导致图像只在水平或垂直方向上重复，`no-repeat` 则不允许图像在任何方向上平铺
* **设置背景定位**：可以利用 `background-position` 属性改变图像在背景中的位置。为 background-position 属性提供值**有很多方法**。首先，可以使用一些**关键字**：`top`、`bottom`、`left`、`right` 和 `center`。通常，这些关键字会成对出现，不过也不总是这样。还可以使用**长度值**，如 100px 或 5cm，最后也可以使用**百分数值**
* **设置背景关联**：如果文档比较长，那么当文档向下滚动时，背景图像也会随之滚动。当文档滚动到超过图像的位置时，图像就会消失。您可以通过 `background-attachment` 属性防止这种滚动。通过这个属性，可以声明图像相对于可视区是固定的（`fixed`），因此不会受到滚动的影响

### 文本

文本属性：

* `color` 设置文本颜色
* `direction` 设置文本方向
* l`ine-height` 设置行高
* `letter-spacing` 设置字符间距
* `text-align` 对齐元素中的文本
* `text-decoration` 向文本添加修饰
* `text-indent` 缩进元素中文本的首行
* `text-shadow` 设置文本阴影。CSS2 包含该属性，但是 CSS2.1 没有保留该属性
* `text-transform` 控制元素中的字母
* `unicode-bidi` 设置文本方向
* `white-space` 设置元素中空白的处理方式
* `word-spacing` 设置字间距

### 字体

在 CSS 中，有两种不同类型的字体系列名称：

* 通用字体系列 - 拥有相似外观的字体系统组合（比如 "Serif" 或 "Monospace"）
* 特定字体系列 - 具体的字体系列（比如 "Times" 或 "Courier"）

除了各种特定的字体系列外，CSS 定义了 5 种通用字体系列：

* Serif 字体
* Sans-serif 字体
* Monospace 字体
* Cursive 字体
* Fantasy 字体

字体属性：

* `font` 简写属性。作用是把所有针对字体的属性设置在一个声明中
* `font-family` 设置字体系列
* `font-size` 设置字体的尺寸
* `font-size-adjust` 当首选字体不可用时，对替换字体进行智能缩放。（CSS2.1 已删除该属性。）
* `ont-stretch` 对字体进行水平拉伸。（CSS2.1 已删除该属性。）
* `font-style` 设置字体风格
* `font-variant` 以小型大写字体或者正常字体显示文本
* `font-weight` 设置字体的粗细

### 链接

链接的四种状态：

* `a:link` - 普通的、未被访问的链接
* `a:visited` - 用户已访问的链接
* `a:hover` - 鼠标指针位于链接的上方
* `a:active` - 链接被点击的时刻

当为链接的不同状态设置样式时，请按照以下**次序规则**：

* `a:hover` 必须位于 a:link 和 a:visited 之后
* `a:active` 必须位于 a:hover 之后

### 列表

列表属性：

* `list-style` 简写属性。用于把所有用于列表的属性设置于一个声明中
* `list-style-image` 将图象设置为列表项标志
* `list-style-position` 设置列表中列表项标志的位置
* `list-style-type` 设置列表项标志的类型

### 表格

表格属性：

* `border-collapse` 设置是否把表格边框合并为单一的边框。
* `border-spacing` 设置分隔单元格边框的距离。
* `caption-side` 设置表格标题的位置。
* `empty-cells` 设置是否显示表格中的空单元格。
* `table-layout` 设置显示单元、行和列的算法

## 轮廓

边框属性：

* outline 在一个声明中设置所有的轮廓属性
* outline-color 设置轮廓的颜色
* outline-style 设置轮廓的样式
* outline-width 设置轮廓的宽度

## CSS 框模型

### 概述

![CSS框模型](CSS框模型.png)

* 内边距、边框和外边距都是**可选的**，默认值是零
* 在 CSS 中，width 和 height 指的是内容区域的宽度和高度。增加内边距、边框和外边距不会影响内容区域的尺寸，但是会增加元素框的总尺寸

### 内边距 padding

* CSS padding 属性定义元素的内边距。padding 属性接受长度值或百分比值，但**不允许使用负值**
* 还可以按照上、右、下、左的顺序分别设置各边的内边距，各边均可以使用不同的单位或百分比值

    ```css
    h1 {padding: 10px 0.25em 2ex 20%;}
    ```

* 元素的内边距设置百分数值。百分数值是相对于其父元素的 width 计算的，这一点与外边距一样。所以，如果父元素的 width 改变，它们也会改变

### 边框 border

* 元素的边框就是围绕元素内容和内边据的一条或多条线

边框属性：

* `border` 简写属性，用于把针对四个边的属性设置在一个声明。
* `border-style` 用于设置元素所有边框的样式，或者单独地为各边设置边框样式。
* `border-width` 简写属性，用于为元素的所有边框设置宽度，或者单独地为各边边框设置宽度。
* `border-color` 简写属性，设置元素的所有边框中可见部分的颜色，或为 4 个边分别设置颜色。
* `border-bottom` 简写属性，用于把下边框的所有属性设置到一个声明中。
* `border-bottom-color` 设置元素的下边框的颜色。
* `border-bottom-style` 设置元素的下边框的样式。
* `border-bottom-width` 设置元素的下边框的宽度。
* `border-left 简写属性`，用于把左边框的所有属性设置到一个声明中。
* `border-left-color` 设置元素的左边框的颜色。
* `border-left-style` 设置元素的左边框的样式。
* `border-left-width` 设置元素的左边框的宽度。
* `border-right` 简写属性，用于把右边框的所有属性设置到一个声明中。
* `border-right-color` 设置元素的右边框的颜色。
* `border-right-style` 设置元素的右边框的样式。
* `border-right-width` 设置元素的右边框的宽度。
* `border-top` 简写属性，用于把上边框的所有属性设置到一个声明中。
* `border-top-color` 设置元素的上边框的颜色。
* `border-top-style` 设置元素的上边框的样式。
* `border-top-width` 设置元素的上边框的宽度。

### 外边距 margin

* 设置外边距的最简单的方法就是使用 `margin` 属性：margin 属性接受任何长度单位，可以是像素、英寸、毫米或 em，margin 可以设置为 auto

## CSS 定位

### 概念

* CSS 定位属性允许你对元素进行定位
* 定位的基本思想很简单，它允许你定义元素框相对于其正常位置应该出现的位置，或者相对于父元素、另一个元素甚至浏览器窗口本身的位置
* 通过使用 `position` 属性，可以选择 4 种不同类型的定位，这会影响元素框生成的方式：
  * `static` 元素框正常生成。块级元素生成一个矩形框，作为文档流的一部分，行内元素则会创建一个或多个行框，置于其父元素中。
  * `relative` 元素框偏移某个距离。元素仍保持其未定位前的形状，它原本所占的空间仍保留。
  * `absolute` 元素框从文档流完全删除，并相对于其包含块定位。包含块可能是文档中的另一个元素或者是初始包含块。元素原先在正常文档流中所占的空间会关闭，就好像元素原来不存在一样。元素定位后生成一个块级框，而不论原来它在正常流中生成何种类型的框。
  * `fixed` 元素框的表现类似于将 position 设置为 absolute，不过其包含块是视窗本身

定位属性：

* `position` 把元素放置到一个静态的、相对的、绝对的、或固定的位置中。
* `top` 定义了一个定位元素的上外边距边界与其包含块上边界之间的偏移。
* `right` 定义了定位元素右外边距边界与其包含块右边界之间的偏移。
* `bottom` 定义了定位元素下外边距边界与其包含块下边界之间的偏移。
* `left` 定义了定位元素左外边距边界与其包含块左边界之间的偏移。
* `overflow` 设置当元素的内容溢出其区域时发生的事情。
* `clip` 设置元素的形状。元素被剪入这个形状之中，然后显示出来。
* `vertical-alignv` 设置元素的垂直对齐方式。
* `z-index` 设置元素的堆叠顺序。

### 相对定位 relative

设置为相对定位的元素框会偏移某个距离。元素仍然保持其未定位前的形状，它原本所占的空间仍保留。

如果将 top 设置为 20px，那么框将在原位置顶部下面 20 像素的地方。如果 left 设置为 30 像素，那么会在元素左边创建 30 像素的空间，也就是将元素向右移动。

```css
#box_relative {
  position: relative;
  left: 30px;
  top: 20px;
}
```

### 绝对定位 absolute

设置为绝对定位的元素框从文档流完全删除，并相对于其包含块定位，包含块可能是文档中的另一个元素或者是初始包含块。元素原先在正常文档流中所占的空间会关闭，就好像该元素原来不存在一样。元素定位后生成一个块级框，而不论原来它在正常流中生成何种类型的框。

### 浮动 float

浮动的框可以向左或向右移动，直到它的外边缘碰到包含框或另一个浮动框的边框为止。

由于浮动框不在文档的普通流中，所以文档的普通流中的块框表现得就像浮动框不存在一样。

## CSS 高级

### 水平对齐

* **对齐块元素**：块元素指的是占据全部可用宽度的元素，并且在其前后都会换行
* 使用 `margin` 属性来水平对齐：可通过将左和右外边距设置为 “auto”，来对齐块元素
* 使用 `position` 属性进行左右对齐
* 使用 `float` 属性来进行左右对齐

## 练习和总结

[参考连接](https://www.w3school.com.cn/css/index.asp)
