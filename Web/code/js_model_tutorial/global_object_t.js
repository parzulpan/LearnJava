// 全局对象


// 全局对象提供可在任何地方使用的变量和函数，默认情况下，这些全局变量内置于语言或者环境中
// 在浏览器中，它的名字是"window"，在Node.js中，它的名字是"global"，其他情况下，一般叫做"globalThis"
let gVar = 5;
console.log(globalThis.gVar);   // undefined（不会成为全局对象的属性）


// 使用polyfills
// 使用全局对象来测试对现代语言功能的支持
// 测试是否存在内建的 Promise 对象（在版本特别旧的浏览器中不存在）：
if (!window.Promise) {
    alert("Your browser is really old!");
}