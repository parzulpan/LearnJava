// 函数绑定


// 当将对象方法作为回调进行传递，例如传递给setTimeout，会存在一个常见的问题:"丢失this"
// 丢失"this"
// 一旦方法被传递到与对象分开的某个地方，this就会丢失
// 一个典型的例子
console.log('--------------------------');
let user = {
    firstName: "John",
    sayHi() {
        console.log(`Hello, ${this.firstName}!`);
    }
};
setTimeout(user.sayHi, 1000); // Hello, undefined! 因为 setTimeout 获取到了函数 user.sayHi，但它和对象分离开了

// 解决方案1：包装器
console.log('--------------------------');
setTimeout(function() {
    user.sayHi();
}, 1000)
// 但是这个解决方案，有一个小漏洞，如果在setTimeout触发之前(有一秒的延迟！)user的值改变了，那么它将调用错误的对象

// 解决方案2：bind
// 提供了一个内建bind，它可以绑定this
// func.bind(context)的结果是一个特殊的类似于函数的"外来对象(exotic object)"，
// 它可以像函数一样被调用，并且透明地将调用传递给func并设定this=context
console.log('--------------------------');
let userB = {
    firstName: "John"
};
function func(phrase) {
    console.log(phrase + ', ' + this.firstName);
}
// 将 this 绑定到 user
let funcUser = func.bind(userB);
funcUser("Hello"); // Hello, John（参数 "Hello" 被传递，并且 this=user）


// 偏函数(partial functions)
console.log('--------------------------');
function mul(a, b) {
    return a * b;
}
// 它将调用传递到 mul，将 null 绑定为上下文，并将 2 绑定为第一个参数。并且，参数（arguments）均被“原样”传递。
let double = mul.bind(null, 2);
console.log( double(3) ); // = mul(2, 3) = 6
console.log( double(4) ); // = mul(2, 4) = 8
console.log( double(5) ); // = mul(2, 5) = 10
// 注意，实际上没有用到this，但是bind需要它，所以必须传入null之类的东西
// 为什么会创建一个偏函数？
// 1. 可以创建一个具有可读性高的名字(double, triple)的独立函数，并且不必每次都提供一个参数，因为参数是被绑定了的。
// 2. 当有一个非常通用的函数，并希望有一个通用型更低的该函数的变体时，偏函数就会非常有用


// 在没有上下文情况下的partial
// 当想绑定一些参数，但是没有上下文this，应该怎么办？
console.log('--------------------------');
function partial(func, ...argsBound) {
    return function(...args) { // (*)
        return func.call(this, ...argsBound, ...args);
    }
}
// 用法：
let userP = {
    firstName: "John",
    say(time, phrase) {
        console.log(`[${time}] ${this.firstName}: ${phrase}!`);
    }
};
// 添加一个带有绑定时间的 partial 方法
userP.sayNow = partial(userP.say, new Date().getHours() + ':' + new Date().getMinutes());
userP.sayNow("Hello");
// 类似于这样的一些内容：
// [10:00] John: Hello!


// 二次bind
// 输出将会是什么？
console.log('--------------------------');
function f() {
    console.log(this.name);
}
f = f.bind( {name: "John"} ).bind( {name: "Ann" } );
f();    // John
// 原因：f.bind()返回的外来绑定函数对象仅在创建的时候记忆上下文以及参数
// 一个函数不能被重绑定(re-bound)


// bind后的函数属性
// 函数的属性中有一个值。bind 之后它会改变吗？
console.log('--------------------------');
function sayHi() {
    console.log( this.name );
}
sayHi.test = 5;
let bound = sayHi.bind({
    name: "John"
});
console.log( bound.test ); // undefined
// 原因：bind的结果是另一个对象，它并没有test属性


