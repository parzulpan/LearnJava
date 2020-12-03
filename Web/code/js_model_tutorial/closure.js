// 闭包

// JS是一种非常面向函数的语言...

// 嵌套函数
console.log('--------------------------');
function makeCounter() {
    let count = 0;

    return function() {
        return count++;
    };
}
let counter = makeCounter();
console.log( counter() ); // 0
console.log( counter() ); // 1
console.log( counter() ); // 2


// 深入理解语法环境
// 一. 变量
// 在JS中，每个运行的函数，代码块{ ... }以及整个脚本，都有一个被称为词法环境(Lexical Environment)的内部(隐藏)的关联对象
// 词法环境对象由两部分组成：
// 1. 环境记录 - 一个存储所有局部变量作为其属性(包括一些其他信息，例如this的值)的对象
// 2. 对外部词法环境的引用，与代码外部相关联
// 一个"变量"只是环境记录这个特殊的内部对象的一个属性，"获取或修改变量"意味着"获取或修改词法环境的一个属性"

// 二. 函数声明
// 一个函数其实就是一个值，就像变量一样，不同之处在于，函数声明的初始化会被立即完成
// 当创建了一个词法环境时，函数声明会立即变成即用型函数(不像let那样直到声明处才可用)，这就是为什么可以在(函数声明)的定义之前调用函数声明
// 正常来说，这种行为仅适用于函数声明，而不适用于将函数分配给变量的函数表达式

// 三. 内部和外部的词法环境
// 在一个函数运行时，在调用刚开始时，会自动创建一个新的词法环境以存储这个调用的局部变量和参数
console.log('--------------------------');
let phrase = "Hello";
function say(name) {
    console.log(`${phrase}, ${name}`);
}
say("PPP");
// 在这个函数调用期间，有两个词法环境：内部一个(用于函数调用)和外部一个(全局)
// 1. 内部语法环境与say的当前执行相对应。它具有一个单独的属性:name，函数的参数。
// 2. 外部词法环境是全局词法环境，它具有phrase变量和函数本身。


// 四. 返回函数
console.log('--------------------------');
function makeCounter1() {
    let count = 0;
    return function() {
        return count++;
    };
}
let counter1 = makeCounter1();
console.log( counter1() );


// 闭包
// 闭包是指内部函数总是可以访问其所在的外部函数中声明的变量和参数，即使在其外部函数被返回(寿命终结)了之后。
// 在JS中，所有函数都是天生闭包的。也就是说：JS中的函数会自动通过隐藏的属性记住创建它们的位置，所以它们都可以访问外部变量。
// 这块得细品，https://zh.javascript.info/closure


// 垃圾集
// 通常，函数调用完成后，会将词法环境和其中的所有变量从内存中删除，因为没有任何对它们的引用了，
// 与JS中的其他对象一样，词法环境仅在可达时才会被保留在内存中。


// 实际开发中的优化
// 理论上当函数可达时，它外部的所在变量也都将存在，但在实际中，JS引擎会试图优化它，它会分析变量的使用情况，
// 如果从代码可以明显看出未使用外部变量，那么就会将其删除。
// 但这样的做法，在V8(Chrome, Opera)中的一个重要的副作用，即此类变量在调试中将不可用


// 代码块
// 可以使用"空"的代码块将变量隔离到"局部作用域"中
console.log('--------------------------');
{
    // 用局部变量完成一些不应该被外面访问的工作
    let message = "hello";
    console.log(message);
}
// console.log(message); // Error: message is not defined


// 函数会选择哪里的内容？
// 函数 sayHiA 使用外部变量。当函数运行时，将使用哪个值？
console.log('--------------------------');
let nameA = 'PPP';
function sayHiA() {
    console.log("Hi, ", nameA);
}
nameA = 'XXX';
sayHiA(); // XXX
// 函数将从内到外依次在对应的词法环境中寻找目标数量，它使用最新的值
// 旧变量值不会保存在任何地方，当一个函数想要一个变量时，它会从自己的词法环境或外部词法环境中获取当前值


// Counter是独立的吗？
console.log('--------------------------');
function makeCounter2() {
    let count = 0;

    return function() {
        return count++;
    };
}
let counter22 = makeCounter2();
let counter222 = makeCounter2();
console.log( counter22() ); // 0
console.log( counter22() ); // 1
console.log( counter222() ); // ?
console.log( counter222() ); // ?
// 函数counter22和counter222是通过makeCounter的不同调用创建的，因此它们具有独立的外部词法环境，每一个都有自己的count


// 闭包sum
// 编写一个像sum(a)(b) = a + b这样的sum函数
console.log('--------------------------');
function sum(a) {
    return function(b) {
        return a + b;   // 从外部词法环境获得"a"
    };
}
console.log(sum(1)(2));
console.log(sum(5)(-1));



