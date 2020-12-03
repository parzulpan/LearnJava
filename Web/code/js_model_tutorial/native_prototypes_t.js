// 原生的类型


// Object.prototype
console.log('--------------------------');
let obj = {};
console.log(obj);
console.log(obj.__proto__ === Object.prototype); // true
// obj.toString === obj.__proto__.toString == Object.prototype.toString


// 其他内建原型
// 其他内建对象，像Array、Date、Function及其他，都在prototype上挂载了方法
// 但创建一个数组[1, 2, 3]，在内部会默认使用new Array()构造器，因此Array.prototype变成了这个数组的prototype，并为这个数组提供操作方法。
// 按照规范，所有的内建原型顶端都是Object.prototype，即一些都从对象继承而来
console.log('--------------------------');
let arr = [1, 2, 3];
// 它继承自 Array.prototype？
console.log( arr.__proto__ === Array.prototype ); // true
// 接下来继承自 Object.prototype？
console.log( arr.__proto__.__proto__ === Object.prototype ); // true
// 原型链的顶端为 null。
console.log( arr.__proto__.__proto__.__proto__ ); // null
console.log('--------------------------');
function f() {}
console.log(f.__proto__ === Function.prototype); // true
console.log(f.__proto__.__proto__ === Object.prototype); // true, inherit from objects


// 给函数添加一个"f.defer(ms)"的方法
// 在所有函数的原型中添加defer(ms)方法，该方法将在ms毫秒后运行该函数
console.log('--------------------------');
Function.prototype.defer = function(ms) {
    setTimeout(this, ms);
};
function ff() {
    console.log("Hello");
}
ff.defer(1000);


// 将装饰者"defer()"添加到函数
// 在所有函数的原型中添加 defer(ms) 方法，该方法返回一个包装器，将函数调用延迟 ms 毫秒
console.log('--------------------------');
Function.prototype.defer1 = function(ms) {
    let f = this;
    return function(...args) {
        setTimeout(() => f.apply(this, args), ms);
    }
};
function ff1(a, b) {
    console.log(a + b);
}
ff1.defer1(1000)(1, 2);





