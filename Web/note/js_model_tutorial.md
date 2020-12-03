# js_model_tutorial

## 前言

现代 JavaScript 教程的学习笔记，它是一份不错的学习资源，感谢开源。
[中文链接](https://zh.javascript.info/)

## 基础

---

### 函数

[代码示例]()

* 函数的声明方式

    ```javascript
    function name(parameters, delimited, by, comma) {
    /* code */
    }
    ```

* 作为参数传递给函数的值，会被复制到函数的局部变量。
* 函数可以访问外部变量。但它只能从内到外起作用。函数外部的代码看不到函数内的局部变量。
* 函数可以返回值。如果没有返回值，则其返回的结果是 undefined。
* 建议在函数中主要使用局部变量和参数，而不是外部变量。
* 函数命名
  * 函数名应该清楚地描述函数的功能。当我们在代码中看到一个函数调用时，一个好的函数名能够让我们马上知道这个函数的功能是什么，会返回什么。
  * 一个函数是一个行为，所以函数名通常是动词。
  * 目前有许多优秀的函数名前缀，如 create…、show…、get…、check… 等等。使用它们来提示函数的作用。

---

### 函数表达式

[代码示例]()

* 函数是值。它们可以在代码的任何地方被分配，复制或声明。
* 如果函数在主代码流中被声明为单独的语句，则称为“函数声明”。
* 如果该函数是作为表达式的一部分创建的，则称其“函数表达式”。
* 在执行代码块之前，内部算法会先处理函数声明。所以函数声明在其被声明的代码块内的任何位置都是可见的。
* 函数表达式在执行流程到达时创建。

---

### 箭头函数

[代码示例]()

* 不带花括号：`(...args) => expression` — 右侧是一个表达式：函数计算表达式并返回其结果。
* 带花括号：`(...args) => { body }` — 花括号允许我们在函数中编写多个语句，但是我们需要显式地 return 来返回一些内容。

---

### 对象

[代码示例]()

* 对象是具有一些特殊特性的关联数组
* 存储属性(键值对)
  * 属性的键必须是字符串或者symbol
  * 值可以是任何类型
* 访问属性
  * 点符号：obj.property
  * 方括号：obj["property"]
* 其他操作
  * 删除属性：delete obj.property
  * 检查是否存在给定键的属性："key" in obj
  * 遍历对象：for(let key in obj)循环
* 对象是通过引用被赋值或复制的。即变量存储的不是”对象的值“，而是值的”引用“(内存地址)。
* JS中其他类型的对象
  * Array用于存储有序数据集合
  * Date用于存储时间日期
  * Error用于存储错误信息

---

### 垃圾回收

[代码示例]()

* 垃圾回收是自动完成的，我们不能强制执行或是阻止执行。
* 当对象是可达状态时，它一定是存在与内存中的。
* 被引用与可访问(从一个根)不同：一直相互连接的对象可能整体都不可达。

---

### Symbol 类型

[代码示例]()

* Symbol是唯一标识符的基本类型
* Symbol是使用带有可选描述(name)的Symbol()调用创建的
* Symbol总有不同的值，即使它们有相同的名字。如果希望同名的Symbol相等，那么应该使用全局注册表：Symbol.for(key)返回一个以key作为名字的全局Symbol。
使用Symbol.for多次调用key相同的Symbol时，返回的就是同一个Symbol
* Symbol的两个主要的使用场景：
  * “隐藏”对象属性。如果想要向“属于”另一个脚本或者库的对象添加一个属性，可以创建一个Symbol并
    使用它作为属性的键。Symbol属性不会出现在for .. in中，因此它不会意外地被与其他属性一起处理。
    并且，它不会被直接访问，因为另一个脚本没有我们地symbol。因此，该属性将受到保护，防止被意外
    或重写
  * JS使用了许多系统地Symbol，这些Symbol可以作为Symbol.*访问
* 从技术上来讲，Symbol不是100%隐藏的

---

### 对象方法,"this"

[代码示例]()

* 存储在对象属性中的函数被称为"方法"
* 方法允许对象进行像object.doSomething()这样的操作
* 方法可以讲对象引用为this
* this的值是在程序运行时得到的
* 一个函数在声明时，可能就使用了this，但是这个this只有在函数被调用时才会有值
* 可以在对象之间复制函数
* 以"方法"的语法调用函数时：object.method()，调用过程中的this值是object

---

### 对象-原始值转换

[代码示例]()

* 对象到原始值的转换，是由许多期望以原始值作为值得内建函数和运算符自动调用的，包括三种类型：
  * "string" 对于alert和其他需要字符串的操作
  * "number" 对于数学运算
  * "default" 少数运算符
* 规范明确描述了哪些运算符使用哪个hint。很少有运算符"不知道期望什么"并使用"default"hint。
通常对于内建对象，"default"hint的处理方式与"number"相同，因此实践中，最后两个hint常常结合在一起
转换算法是：
  * 调用`obj[Symbol.toPrimitive](hint)`如果这个方法存在
  * 否则，如果hint是"string"
    * 尝试obj.toString()和obj.valueOf()，无论那个存在
  * 否则，如果 hint 是 "number" 或者 "default"
    * 尝试 obj.valueOf() 和 obj.toString()，无论哪个存在。

---

### 构造器和操作符"new"

[代码示例]()

* 构造函数，或简称构造器，就是常规函数，但有个共同约定，就是其命名首字母要大写
* 构造函数只能使用new来调用。这样的调用意味着在开始时创建了空的this，并在最后返回填充了值的this
* 当一个函数被使用new操作符执行时，它按照以下步骤：
  * 一个新的空对象被创建并被分配给this
  * 函数体执行。通常它会修改this，为其添加新的属性。
  * 返回this的值

---

### 数字类型

[代码示例]()

* 要写很多零的数字
  * 将"e"和0的数量附加到数字后。123e6 <=> 123000000
  * "e"后面的负数将使数字除以1后面接着给定数量的零的数字。123e-6 <=> 0.000123
* 对于不同的数字系统
  * 可以直接在十六进制(0x)，八进制(0o)和二进制(0b)系统中写入数字
  * parseInt(str, base)将字符串str解析为给定的base数字系统中的整数，2 <= base <= 36
  * num.toString(base)将数字转换为在给定的base数字系统中的字符串
* 要将12pt和100px之类的值转换为数字
  * 使用parseInt/parseFloat进行"软"转换，它从字符串中读取数字，然后返回在发生error前可以读取到的值
  * 使用Math.floor、Math.ceil、Math.trunc、Math.round或者num.toFixed(precision)进行舍入
  * 请确保记住使用小数时会损失精度

---

### 字符串

[代码示例]()

* 有3种类型的引号，反引号允许字符串跨越多行并可以使用${...}在字符串中嵌入表达式
* JavaScript 中的字符串使用的是 UTF-16 编码
* 可以使用像 \n 这样的特殊字符或通过使用 \u... 来操作它们的 unicode 进行字符插入
* 获取字符时，使用 []
* 获取子字符串，使用 slice 或 substring 或 substr
* 字符串的大/小写转换，使用：toLowerCase/toUpperCase
* 查找子字符串时，使用 indexOf 或 includes/startsWith/endsWith 进行简单检查
* 根据语言比较字符串时使用 localeCompare，否则将按字符代码进行比较
* 其他几种有用的字符串方法
  * str.trim() — 删除字符串前后的空格 (“trims”)
  * str.repeat(n) — 重复字符串 n 次

---

**数组**

[代码示例]()
* 数组是一种特殊的对象，适用于存储和管理有序的数据项
* 声明：let arr = [item1, item2, item3]; let arr = new Array(item1, item2, item3);
* 调用 new Array(number) 会创建一个给定长度的数组，但不含有任何项
* length属性是数组的长度，准确地说，它是数组最后一个数字索引值加一，它由数组方法自动调整
* 手动缩短length，那么数组就会被截断，且不可逆
* 用操作双端队列的方式使用数组
    * push(items)在末端添加items项
    * pop()从末端移除并返回该元素
    * unshift(items)从首端添加items项
    * shift()从首端移除并返回该元素
* 遍历数组的方法
    * for(let i = 0; i < arr.length; i++) 运行最快，可兼容旧版本浏览器
    * for(let item of arr) 现代语法，只能访问items
    * for(let i in arr) 永远不要使用这个

---

**数组方法**

[代码示例](js/array_methods_t.js)
* 添加/删除元素
    * push(items) 向尾端添加元素
    * pop() 从尾端提取一个元素
    * shift() 从首端提取一个元素
    * unshift(items) 向首端添加元素
    * splice(pos, deleteCount, items) 从index开始删除deleteCount个元素，并在当前位置插入items
    * slice(start, end) 创建一个新数组，将从位置start到位置end(但不包括end)的元素复制进行
    * concat(items) 返回一个新数组：复制当前数组的新元素，并向其中添加items。如果items中的任意一项是一个数组，那么就取其元素
* 搜索元素
    * indexOf/lastIndexOf(item, pos) 从位置pos开始搜索item，搜索到则返回该项的索引，否则返回-1
    * includes(value) 如果数组有value，则返回true，否则返回false
    * find/filter(func) 通过func过滤元素，返回使func返回true的第一个值/所有值
    * findIndex() 和find()类似，但是返回索引而不是值
* 遍历元素
    * forEach(func)  对每个元素都调用func，不返回任何内容
* 转换数组
    * map(func) 根据对每个元素调用func的结果创建一个新数组
    * sort(func) 对数组进行原位排序，然后返回它
    * reverse() 对数组进行原位反转，然后返回它
    * split/join 将字符串转换为数组并返回它
    * reduce(func, initial) 通过对每个元素调用func计算数组的单个值，并在调用之间传递中间结果
* 其他
    * Array.isArray(arr) 检查arr是否是一个数组

---

**可迭代对象**

[代码示例]()
* 可以应用 for..of 的对象被称为可迭代的
* 技术上来说，可迭代对象必须实现 Symbol.iterator 方法
    * obj[Symbol.iterator] 的结果被称为迭代器(iterator)，由它处理进一步的迭代过程
    * 一个迭代器必须由next()方法，它返回一个{done: Boolean, value: any}对象，
    这里的done: true表明迭代结束，否则value就是下一个值
* Symbol.iterator方法会被 for..of 自动调用，但是也可以直接调用
* 内置的可迭代对象例如字符串和数组，都实现了Symbol.iterator
* 字符串迭代器能够识别代理对，即UTF-16拓展字符
* 有索引属性和length属性的对象被称为类数组对象，这种对象可能还具有其他属性和方法，但是没有数组的内建方法
* Array.from(obj[, mapFn, thisArg])将可迭代对象或者类数组对象obj转化为真正的数组Array，然后就可以对它应用数组的方法，可选参数mapFn和thisArg允许将函数应用到每个元素

---

**映射和集合**

[代码示例]()
* Map 是一个带键的数据项的集合
    * new Map() 创建map
    * map.set(key, value) 根据键存储值
    * map.get(key) 根据键来返回值，如果map中不存在对应的key，则返回undefined
    * map.has(key) 根据key存在则true，否则返回false
    * map.delete(key) 删除指定键的值
    * map.clear() 清空map
    * map.size 返回当前元素个数
* Map 与普通对象 Object 的不同点
    * 任何键、对象都可以作为键
    * 有其他的便捷方法，如 size 属性
* Set 是一组唯一值的集合
    * new Set(iterable) 创建一个set，如果提供了一个iterable对象(通常是数组)，将会从数组里面复制值到set中
    * set.add(value) 添加一个值，返回set本身
    * set.delete(value) 删除值，如果value在这个方法调用的时候存在则返回true，否则返回false
    * set.has(value) 如果value在set中，返回true，否则返回false
    * set.clear() 清空set
    * set.size 返回元素个数
* 在 Map 和 Set 中迭代总是按照值插入的顺序进行的，所以不能说这些集合是无序的，但是不能对元素进行重新排序，也不能直接按其编号来获取元素。

---

**弱映射和弱集合**

[代码示例]()
* WeakMap是类似于Map的集合，它仅允许对象作为键，并且一旦通过其他方式无法访问它们，
便会将它们与其关联值一同删除
* WeakSet是类似于Set的集合，它仅存储对象，并且一旦通过其他方式无法访问他们们，便会将其删除
* 均不支持引用所有键或其计数的方法和属性，仅允许单个操作
* WeakMap和WeakSet被用作“主要”对象存储之外的“辅助”数据结构，一旦将对象从主存储器中删除，
如果该对象仅被用作WeakMap或WeakSet的键，那么它将被自动清除

---

**解构赋值**

[代码示例]()
* 解构赋值可以立即将一个对象或数组映射到多个变量上
* 解析对象的完整语法：
    * let {prop: varName = default, ...rest} = object;
    * 这表示属性prop会被赋值给变量varName，如果没有这个属性的话，就会使用default，
    没有对应映射的对象属性将被复制到rest对象
* 解析数组的完整语法：
    * let [item1 = default, item2, ...rest] = array;
    * 数组的第一个元素被赋值给item1，第二个元素被赋值给item2，剩下的所有元素被复制到另一个数组rest
* 从嵌套数对象/数组中提取数据也是可以的，此时等号左侧必须和等号右侧有相同的结构

---

**时间和日期**

[代码示例]()
* 在JS中，日期和时间使用Date对象来表示，但是不能只创建日期，或者只创建时间，Date对象总是同时创建两者
* 月份从0开始计数，即一月是0
* 一周的某一天 getDay() 同样是从0开始计算，0代表星期日
* 当设置了超出范围的组件时，Date会自我进行校准，这一点对于日/月/小时的加减很有用
* 日期可以相减，得到的是以毫秒表示的两者的差值。因为当Date被转换为数字时，Date对象会被转换为时间戳
* 使用 Date.now() 可以更快地获取当前时间的时间戳
* 和其他系统不同，JS中的时间戳以毫秒为单位，而不是秒

---

**JSON方法，toJSON**

[代码示例]()
* JSON是一种数据格式，具有自己的独立标准和大多数编程语言的库
* JSON支持object、array、string、number、boolean、null
* JS提供序列化成JSON的方法 JSON.stringify 和解析JSON的方法 JSON.parse
* 这两种方法都支持用于只能读/写的转换函数
* 如果一个对象具有toJSON，那么它会被JSON.stringify调用

---

**递归和堆栈**

[代码示例]()
* 递归式编程的一个术语，表示从自身调用函数。递归函数可用于以更优雅的函数解决问题
* 递归定义的数据结构是指可以使用自身来定义的数据结构
* 任何递归函数都可以被重写为迭代形式，有时这是在优化代码时需要做的，但对于大多数任务来说，递归方法足够快，并且容易编写和维护

---

**Rest参数与Spread语法**

[代码示例]()
* 当在代码中看到"..."时，它要么是rest参数，要么就是spread语法
* 简单的区分：
    * 若 ... 出现在函数参数列表的最后，那么它就是rest参数，它会把参数列表中剩余的参数收集到一个数组中
    * 若 ... 出现在函数调用或类似的表达式中，那它就是spread语法，它会把一个数组展开为列表
* 使用场景：
    * Rest参数用于创建可接受任意数量参数的函数
    * Spread语法用于将数组传递给通常需要含有许多参数的列表的函数
* "旧式的" arguments(类数组对象) 依然能获取函数调用中的所有参数，但不推荐，推荐使用Rest参数

---

**闭包**

[代码示例]()
* 这部分需要再多看看

---

**旧时的"var"**

[代码示例]()
* var与let/const 有两个主要的区别
    * var 声明的变量没有块级作用域，它们的最小作用域就是函数级作用域
    * var 变量声明在函数开头就会被处理(脚本启动对应全局变量)

---

**全局对象**

[代码示例]()
* 全局对象包含应该在任何位置都可见的变量
    * 其中包括JS的内建方法
* 全局对象有一个通用名称globalThis
    * 但是更常见的是使用“老式”的环境特定（environment-specific）的名字，
    例如 window（浏览器）和 global（Node.js）。
    由于 globalThis 是最近的提议，因此在 non-Chromium Edge 中不受支持（但可以进行 polyfills）
* 仅当值对于项目而言确实是全局时，才应将其存储在全局对象中，并保持其数量最少
* 在浏览器中，除非使用modules。否则使用var声明的全局函数和变量会变成全局对象的属性
* 为了使代码面向未来并更易于理解，应该使用直接的方式访问全局对象的属性，如window.x

---

**函数对象，NFE**

[代码示例]()
* 函数就是对象
* 一些属性
    * name -- 函数的名字。通常取自函数定义，但如果函数定义时没设定函数名，JS会尝试通过函数的上下文猜一个函数名
    * length -- 函数定义时入参的个数，Rest参数不参与计数
* 如果函数是通过函数表达式的形式被声明的，并且附带了名字，那么它就被称为命名函数表达式，这个名字可以用于在该函数内部进行指调用，例如递归调用等

---

**"new Function" 语法**

[代码示例]()
* 使用 new Function 创建的函数，它的 [[Environment]] 指向全局词法环境，而不是函数所在的外部词法环境
* 不能在 new Function 中直接使用外部变量
* 这有助于降低我们代码出错的可能
* 从代码架构上讲，显式地使用参数传值是一种更好的方法，并且避免了与使用压缩程序而产生冲突的问题

---

**调度：setTimeout 和 setInterval**

[代码示例]()
* setTimeout(func, delay, ...args)和setInterval(func, delay, ...args)方法允许在delay毫秒之后允许func一次或者以delay毫秒为时间间隔周期性运行func
* 要取消函数的执行，应该调用clearInterval/clearTimeout，并将setInterval/setTimeout返回的值作为入参传入
* 嵌套的setTimeout比setInterval用起来更加灵活，允许更精确地设置两次执行之间的时间
* 零延时调度setTimeout(func, 0)(或者setTimeout(func))用来调度需要尽快执行的调用，但是会在当前脚本执行完成后进行调度
* 浏览器会将setTimeout或setInterval的五层或者更多层嵌套调用(调用五次之后)的最小延时限制在4ms，这是一个历史遗留问题
* 注意，所有的调度方法都不能保证确切的延时，例如，浏览器内的计时器可能由于许多原因而变慢
    * CPU过载
    * 浏览器页签处于后台模式
    * 笔记本电脑用的电池供电，使用电池供电会以降低性能为代价提升续航

---

**装饰者模式和转发，call/apply**

[代码示例]()
* 装饰器是一个围绕改变函数行为的包装器，其主要工作任由该函数来完成
* 装饰器可以被看作是可以添加到函数的"features"或"aspects"，可以添加一个或添加多个，而这一切都无需更改其代码
* 为了实现cachingDecorator，可以运用以下方法
    * func.call(context, arg1, arg2) -- 给定的上下文和参数调用func
    * func.apply(context, args) -- 调用func将context作为this和类数组的args传递给参数列表
* 通用的呼叫转移(call forwarding)通常是使用apply完成的
* 一个方法借用(method borrowing)，就是从一个对象中获取一个方法，并在另一个对象的上下文中"调用"它。
采用数组方法并将它们应用于参数arguments是很常见的。另一种方法是使用Rest参数对象，该对象是一个真正的数组。

---

**函数绑定**

[代码示例]()
* 方法func.bind(context, ...args)返回函数func的"绑定的变体"，它绑定了上下文this和第一个参数
* 通常应用bind来绑定对象方法的this，这样就可以把它们传递到其他地方使用
* 当绑定一个现有的函数的某些参数时，绑定后的函数被称为partial
* 当不想一遍又一遍重复相同的参数时，partial非常有用

---

**深入理解箭头函数**

[代码示例]()
* 箭头函数没有this、arguments，这对装饰器来说非常有用
* 箭头函数也不能使用new进行调用
* 箭头函数也没有super
* 箭头函数时针对那些没有自己的"上下文"，但在当前上下文中起作用的短代码的

---

**属性标志和属性描述符**

[代码示例](js/property_descriptors.js)
* 对象属性，除value外，还有三个特殊的特性，也就是所谓的"标志"
    * writable -- 如果为true，则值可以被修改，否则它只是可读的
    * enumerable -- 如果为true，则会被在循环中列出，否则不会被列出
    * configurable -- 如果为true，则此特性可以被删除，这些属性也可以被修改，否则不可以
    * 以上，默认都为true

---

**属性的getter和setter**

[代码示例](js/property_accessors_t.js)
* 有两种类型的属性
    * 数据属性，目前使用的所有属性都是数据属性
    * 访问器属性，本质上是用于获取和设置值的函数，但从外部代码来看就像常规属性
* 访问器描述符与数据属性不同，对于它，没有value和writable，但是有get 和 set 函数

---

**原型继承**

[代码示例](js/prototype_inheritance_t.js)
* 在JS中，所有的对象都有一个隐藏的[[Prototype]]属性，它要么是另一个对象，要么就是null
* 可以使用obj.__proto__访问它
* 通过[[Prototype]]引用的对象被称为"原型"
* 想要读取obj的一个属性或者调用一个方法，并且它不存在，那么JS就会尝试在原型中查找它
* 写/删除操作直接在对象上进行，它们不使用原型(假设它是数据类型，不是setter)
* 如果想调用obj.method()，而且method是从原型中获取的，this仍然会引用obj，因此，方法始终是与当前对象一起使用，即使方法是继承的
* for .. in 循环在其自身和继承的属性上进行迭代，所有其他的键/值获取方法仅对对象本身起作用

---

**F.prototype**

[代码示例](js/function_prototype_t.js)
* F.prototype属性(不要把它与[[Prototype]]弄混了)在new F被调用时为新对象的[[Prototype]]赋值
* F.prototype的值要么是一个对象，要么就是null，其他值都不起作用
* "prototype"属性仅在设置了一个构造函数，并通过new调用时，才具有这种特殊的影响

---

**原生的类型**

[代码示例](js/native_prototypes_t.js)
* 所有的内建对象收遵循相同的模式(pattern)
    * 方法都存储在prototype中(Array.prototype、Object.prototype、Date.prototype等)
    * 对象只存储数据本身(数组元素、对象属性、日期)
* 原始数据类型也将方法存储在包装器对象的prototype中：Number.prototype、String.prototype、Boolean.prototype。只有undefined和null没有包装器对象
* 内建原型可以被修改或被用新的方法填充，但是不建议更改它们，唯一允许的情况可能是，当添加一个还没有被JS引擎支持，但已经被加入JS规范的新标准中，才可能允许这样做

---

**原生方法，没有__proto__的对象**

[代码示例](js/prototype_methods_t.js)
* 设置和直接访问原型的现代方法：
    * Object.create(proto, [description]) -- 利用给定的proto作为 [[Prototype]] (可以是null)和可选的属性描述符来创建一个任务
    * Object.getPrototypeOf(obj) -- 返回对象obj的 [[Prototype]] (与__proto__的getter相同)
    * Object.setPrototypeOf(obj. proto) -- 将对象obj的 [[Prototype]] 设置为proto(与__proto__的setter相同)
* 如果要将一个用户生成的键放入一个对象，那么内建的__proto__ getter/setter 是不安全的
* 可以使用Object.create(null)创建一个没有__proto__的"very plain"对象，或者对此场景坚持使用Map对象即可

---

**Class基本语法**

[代码示例](js/class_t.js)
* 基本的类语法
```javascript
class MyClass {
  prop = value; // 属性

  constructor(...) { // 构造器
    // ...
  }

  method(...) {} // method

  get something(...) {} // getter 方法
  set something(...) {} // setter 方法

  [Symbol.iterator]() {} // 有计算名称（computed name）的方法（此处为 symbol）
  // ...
}
```
* 技术上来说，MyClass 是一个函数（提供作为 constructor 的那个），而 methods、getters 和 settors 都被写入了 MyClass.prototype。

---

**类继承**

[代码示例](js/class_inheritance_t.js)
* 想要扩展一个类：class Child extends Parent:
    * 则意味着Child.prototype.__proto__将是Parent.prototype，所有方法会被继承
* 重写一个constructor
    * 在使用this之前，必须在Child的constructor中将父constructor调用super()
* 重写一个方法
    * 可以在一个Child方法中使用super.method()来调用Parent方法
* 内部
    * 方法在内部的[[HomeObject]]属性中记住了它们的类/对象，这就是super如何解析父方法的
    * 将一个带有super的方法从一个对象复制到另一个对象是不安全的
* 箭头函数是没有自己的this或者super，所以它们能融入到就近的上下文中，像透明似的

---

**静态属性和静态方法**

[代码示例](js/static_propertird_methods_t.js)
* 静态方法被用于实现整个类的功能，与具体的类示例无关
* 在类生命中，它们都被用关键字static进行了标记
* 静态属性被用于但想要存储类级别的数据时，而不是绑定到实例
* 从技术上讲，静态声明与直接给类本身赋值相同
* 静态属性和方法都是可以被继承的

---

**私有的和受保护的属性和方法**

[代码示例](js/private_protected_properties_methods_t.js)
* OOP而言，内部接口和外部接口的划分被称为封装
* 封装的优点
    * 保护用户，使他们不会误伤自己
    * 可支持性
    * 严格界定内部接口，class的开发人员可以自由地更改其内部属性和方法，甚至无需通知用户
    * 隐藏复杂性
    * 当实施细节被隐藏，并提供了简单且有据可查地外部接口时，总是方便的
* 为了隐藏内部接口，使用了受保护或私有的属性
    * 受保护的字段以_开头，这是一个约定
    * 私有字段以#开头，确保只能从类的内部访问它们
* 目前，在各浏览器中不支持私有字段，但可以用 polyfill 解决，即自行解决

---

**扩展内建类**

[代码示例](js/extend_natives_t.js)
* 内建的类，例如Array、Map等也都是可以扩展的
* 如果想要map或filter这样的内建方法返回常规数组，可以在Symbol.species中返回Array
* 内建对象有它们自己的静态方法，例如Object.keys、Array.isArray等，原生的类互相扩展，通常，当一个类扩展另一个类时，静态方法和非静态方法都会被继承，但内建类是一个例外，它们相互间不继承静态方法

---

**类检查:"instanceof"**

[代码示例](js/instanceof_t.js)
* typeof用于原始数据类型，返回值为string
* {}.toString用于原始数据类型和内建对象，包括Symbol.toStringTag属性的对象，返回string
* instanceof用于对象，返回true/false
* 从技术上讲，{}.toString 是一种"更高级的"typeof
* 当使用类的层次结构，并想要对该类进行检查，同时考虑继承时，这种场景下instanceof操作确实很出色

---

**Mixin模式**

[代码示例](js/mixins_t.js)
* Mixin是一个通用的面向对象编程术语：一个包含其他类的方法的类
* JS不支持多重继承，但是可以通过将方法拷贝到原型来实现mixin
* 可以使用mixin作为一种通过添加多种行为来扩充类的方法
* 如果Mixins意外覆盖了现有类的方法，那么它们可能会成为一个冲突点。因此，通常应该仔细考虑mixin的命名方法，以最大程度地降低发生这种冲突的可能性

---

**错误处理，"try...catch"**

[代码示例](js/try_catch_t.js)
* try...catch结构允许处理执行过程中出现的error，从字面上看，它允许"尝试"允许代码并"捕获"其中可能发生的错误
``` javascript
try {
  // 执行此处代码
} catch(err) {
  // 如果发生错误，跳转至此处
  // err 是一个 error 对象
} finally {
  // 无论怎样都会在 try/catch 之后执行
}
```
* 可能没有catch部分或者没有finally，所以try...catch或者try...finally都是可用的
* Error对象包括下列属性：
    * message -- 人类可读的error信息
    * name -- 具有error名称的字符串(Error构造器的名称)
    * stack(没有标准化，但是得到了很好的支持)，即Error发生时的调用栈
* 可以使用throw操作符来生成自定义的error，从技术上讲，throw的参数可以是任何东西，但通常是继承自内建的Error类的error对象
* 再次抛出是一中错误处理的重要模式：catch块通常期望并知道如何处理特定的error类型，因此它应该再次抛出它不知道的error

---

**自定义Error，扩展Error**

[代码示例](js/custom_errors_t.js)
* 可以正常地从Error和其他内建的error类进行继承，但需要注意部分属性以及不忘了调用super
* 可以用instanceof来检查特定的error，但是对于一些第三方库的error，由于没有简单的方法获取它的类，可以将name属性用于这一类的检查
* 包装异常是一项广泛应用的技术：用于出入低级别异常并创建高级别error而不是各种低级别error的函数

---

**回调介绍**

[代码示例](js/callbacks_t.js)
* 一些函数支持异步行为，即现在开始执行的行为，但是它们在稍后才会完成
* 由于脚本是"异步"调用，所以不会等到脚本加载完成才执行
* 异步执行某项功能的函数应该提供一个callback参数用于在相应事件完成时调用
* 避免"回调地狱"式的编码方式

---

**Promise**

[代码示例](js/promise_basics_t.js)
* "生产者代码"：会做一些事儿，并且会需要一些时间，例如，通过网络加载数据的代码
* "消费者代码"：想要在"生产者代码"完成哦你工作的第一时间就能获得其工作成果，许多函数可能都需要这个结果
* Promise是将"生产者代码"和"消费者代码"连接在一起的特殊的JS的对象
* 由new Promise构造器返回的promise对象具有以下内部属性：
    * state -- 最初是"pending"，然后再resolve被调用时变为"fulfilled"，或者在reject被调用时变成"rejected"
    * result -- 最初是undefined，然后在resolve(value)被调用时变为value，或者在reject(error)被调用时变成error
* Promises和Callbacks对比：
    * Promises允许按照自然顺序进行编码，允许loadScript和.then来处理结果；
    * 而Callbacks在调用loadScript(script, callback)时，在处理的地方必须有一个callback函数，即在调用loadScript之前，必须知道如何处理结果
    * Promises可以根据需要在promise上多次调用.then，每次调用，都会在"订阅列表"中添加一个新的"分析"，一个新的订阅函数，即Promise链
    * 而Callbacks只能有一个回调  

---

**Promise链**

[代码示例](js/promise_chaining_t.js)
* 如果.then(或catch/finally都可以)处理程序(handler)返回一个promise，那么链的其余部分将会等待，直到它状态变为settled。当它被settled后，其result(或error)将被进一步传递下去

---

**使用promise进行错误处理**

[代码示例](js/promise_error_handling_t.js)
* .catch处理promise中的各种error：在reject()调用中的，或者在处理程序(handler)中抛出的(throw)error
* 应该将.catch准确地放在想要处理的error上，并知道如何处理这些error的地方。处理程序应该分析error(也可以自定义error类来帮助分析)并再次抛出未知的error
* 如果没有办法从error中恢复的话，不使用.catch也可以
* 在任何情况下都应该有unhandled rejection事件处理程序(用于浏览器，以及其他环境的模拟)，以跟踪未处理的error并告知用户/服务器有关信息，以使应用程序永远不会死掉

---

**Promise API**

[代码示例](js/promise_api_t.js)
* Promise类有5种静态方法
    * Promise.all(promises) —— 等待所有 promise 都 resolve 时，返回存放它们结果的数组。如果给定的任意一个 promise 为 reject，那么它就会变成 Promise.all 的 error，所有其他 promise 的结果都会被忽略
    * Promise.allSettled(promises)（ES2020 新增方法）—— 等待所有 promise 都 settle 时，并以包含以下内容的对象数组的形式返回它们的结果：
      * state: "fulfilled" 或 "rejected"
      * value（如果 fulfilled）或 reason（如果 rejected）
    * Promise.race(promises) —— 等待第一个 settle 的 promise，并将其 result/error 作为结果
    * Promise.resolve(value) —— 使用给定 value 创建一个 resolved 的 promise
    * Promise.reject(error) —— 使用给定 error 创建一个 rejected 的 promise

---

**Promisify**

[代码示例](js/promisify_t.js)
* "Promisification"是用于一个简单转换的一个长单词，它指将一个接受回调的函数转换为一个返回promise的函数
* 由于许多函数和库都是基于回调的，因此，在实际开发中经常会需要这种转换，因为使用promise更加方便
* 在实际开发中，可能需要promisify很多函数，使用一个helper很有意义，将其称为promisify(f): 它接受一个需要被promisify的函数f，并返回一个包装(wrapper)
* 注意：Promisification是一种很好的方法，特别是在使用async/await的时候，但是不是回调的完全替代，一个promise可能只有一个结果，但从技术上将，一个回调将函数可能被调用多次，因此Promisification仅适用于调用一次回调的函数，进一步调用将被忽略

---

**微任务(Microtask)**

[代码示例](js/microtask_queue_t.js)
* Promise处理始终时异步的，因为所有promise行为都会通过内部的"promise jobs"队列，即微任务队列
    * 队列(queue)是先进先出的：首先进入队列的任务会首先运行
    * 只有在JS引擎中没有其他任务在运行时，才开始执行任务队列中的任务
* .then/catch/finally 处理程序（handler）总是在当前代码完成后才会被调用
* 如果需要确保一段代码在 .then/catch/finally 之后被执行，可以将它添加到链式调用的 .then 中
* 在大多数 JavaScript 引擎中（包括浏览器和 Node.js），微任务（microtask）的概念与“事件循环（event loop）”和“宏任务（macrotasks）”紧密相关

---

**Async/await**

[代码示例](js/async_await_t.js)
* 函数前面的关键字async有两个作用
    * 让这个函数总是返回一个promise
    * 允许在该函数内使用await
* Promise前的关键字await使JS引擎等待该promise settle，然后：
    * 如果有error，就会抛出异常 -- 就像那里调用了throw error 一样
    * 否则，就返回结果
* 关键字async和await在一起能很好的编写异步代码的框架，且代码易于阅读和编写
* 有了async/await之后，几乎就不需要使用promise.then/catch，但是不要忘了它们本质上是promise的，有时候(例如在函数最外层作用域)不得不使用它们
* 当需要同时等待多个任务时，别忘了Promise.all

---

**Generator**

[代码示例](js/generator_t.js)
* Generator是通过generator函数function* f(...) {...}创建的
* 在generator(仅在)内部，存在yield操作
* 外部代码和generator可能会通过next/yield调用交换结果
* 在现代JS中，generator很少被使用，但有时却非常有用，因为函数在执行过程中与调用代码交换数据的能力是非常独特的，而且它们非常适合创建可迭代对象

---

**Async iterator 和 generator**

[代码示例](js/async_iterators_generators_t.js)
* Iterator 和 Async Iterator 的 区别
    * 提供iterator的对象方法
        * Iterator -- Symbol.iterator
        * Async Iterator -- Symbol.asyncIterator
    * next() 返回的值
        * Iterator -- 任意值
        * Async Iterator -- Promise
    * 进行循环使用时
        * Iterator -- for .. of
        * Async Iterator -- for await .. of
* Iterable 和 Async Iterable 的 区别
    * 提供iterator的对象方法
        * Iterable -- Symbol.iterator
        * Async Iterable -- Symbol.asyncIterator
    * next() 返回的值
        * Iterable -- {value: ..., done: true/false}
        * Async Iterable -- resolve成{value: ..., done: true/false}的Promise
* Generator 和 Async Generator 的 区别
    * 声明方式
        * Generator -- function* 
        * Async Generator -- async function*
    * next() 返回的值
        * Generator -- {value: ..., done: true/false}
        * Async Generator -- resolve成{value: ..., done: true/false}的Promise
在现代JS中，generator很少被使用，但有时却非常有用，因为函数在执行过程中与调用代码交换数据的能力是非常独特的，而且它们非常适合创建可迭代对象

---

### 模板简介

[代码示例](js/module_intro_t.js)

* 一个模块就是一个文件，浏览器需要使用 <script type="module"> 以使 import/export 可以工作
* 模块相较于常规脚本的区别：
    * 默认是延迟解析的(deferred)
    * Async 可用于内联脚本
    * 要从另一个源（域/协议/端口）加载外部脚本，需要 CORS header
    * 重复的外部脚本会被忽略
* 模块具有自己的本地顶级作用域，并可以通过 import/export 交换功能
* 模块始终使用 use strict
* 模块代码只执行一次。导出仅创建一次，然后会在导入之间共享

---

### 导入和导出

[代码示例](js/import_export_t.js)

---

### 动态导入

[代码示例](js/modules_dynamic_imports_t.js)


## 浏览器

## 其他