// "new Function" 语法


// 语法
// let func = new Function ([arg1, arg2, ...argN], functionBody);
// 该函数是通过使用参数arg1...argN和给定的functionBody创建的
console.log('--------------------------');
let sum = new Function('a', 'b', 'return a + b');
console.log(sum(1, 2));
// 这种方法最大的不同在于，它实际上是通过运行时通过参数传递过了哦的字符串创建的
// 使用new Function 创建函数的应用场景非常特殊，比如在复杂的Web应用程序中，需要从服务器获取代码或者动态地从模板编译函数时才会使用


// 闭包
// 通常，闭包是指使用一个特殊地属性[[Environment]]来记录函数自身地创建时的环境的函数，它具体指向了函数创建时的词法环境。
// 但是如果使用new function 创建一个函数，那么该函数的[[Environment]]并指向当前的词法环境，而是指向全局环境

// 由于历史原因，下面的声明的含义相同
new Function('a', 'b', 'return a + b'); // 基础语法
new Function('a,b', 'return a + b'); // 逗号分隔
new Function('a , b', 'return a + b'); // 逗号和空格分隔