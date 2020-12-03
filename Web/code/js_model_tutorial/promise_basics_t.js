// Promise


// 几个概念
// "生产者代码"：会做一些事儿，并且会需要一些时间，例如，通过网络加载数据的代码。
// "消费者代码"：想要在"生产者代码"完成哦你工作的第一时间就能获得其工作成果，许多函数可能都需要这个结果。
// Promise是将"生产者代码"和"消费者代码"连接在一起的特殊的JS的对象。

// Promise对象的构造器(constructor)语法：
// let promise = new Promise( function(resolve, reject) { // executor } );
// 传递给new Promise的函数被称为executor，当new Promise被创建，executor会自动运行并尝试执行一项工作，
// 尝试结束后，如果成功则调用resolve，如果出现error则调用reject

// 由new Promise构造器返回的promise对象具有以下内部属性：
// 1. state -- 最初是"pending"，然后再resolve被调用时变为"fulfilled"，或者在reject被调用时变成"rejected"
// 2. result -- 最初是undefined，然后在resolve(value)被调用时变为value，或者在reject(error)被调用时变成error
console.log('--------------------------');
let promise = new Promise(function(resolve, reject) {
    // 当promise被构造完成时，自动执行此函数

    // 1秒后发出工作已经被完成的信号，并带有结果"done"
    setTimeout(() =>resolve("done"), 1000);

    // 1秒后发出工作已经被完成的信号，并带有error
    setTimeout(() =>reject(new Error("Whoops")), 1000);
})
// 注意：这儿只能有一个结果或者error，executor只能调用一个resolve或者reject，任何状态的更改都是最终的，
// 所有其他的再对resolve和reject的调用都会被忽略
// 宗旨：一个被executor完成的工作只能有一个结果或者error



// 消费者：then，catch，finally
// Promise对象充当的是"生产者"(executor)和"消费者"之间的连接，
// 后者将接收结果或者error，可以通过使用.then、.catch和.finally方式为消费者进行注册

// then
console.log('--------------------------');
let promiseResolved = new Promise(function(resolve, reject) {
   setTimeout(() => resolve("done!"), 1000);
});
promiseResolved.then(
    result => console.log(result),  // 运行
    error => console.log(error)     // 不运行
)

let promiseReject = new Promise(function(resolve, reject) {
    setTimeout(() => reject(new Error("Whoops!")), 1000);
});
promiseReject.then(
    result => console.log(result),  // 不运行
    error => console.log(error)     // 运行
)

// catch
// 如果我们值对error感兴趣，可以直接使用.catch
console.log('--------------------------');
let promiseCatch = new Promise( function(resolve, reject) {
    setTimeout(() => reject(new Error("Whoops!!!")), 1000)
})
promiseCatch.catch(console.log)   // .catch(f)调用是.then(null, f)的完全模拟，它只是一个简写形式

// finally
// .finally(f)调用与.then(f, f)类似，在某种意义上，f总是在promise被settled时运行：即promise被resolve或reject
// finally是执行清理的很好的处理程序(handler)
// 它和.then(f, f)的区别：
// 1. .finally处理程序(handler)没有参数。
// 2. .finally处理程序将结果和error传递给了then
// 3. .finally(f)是比.then(f, f)更加方便地语法，即无需重复函数f
console.log('--------------------------');
new Promise(function(resolve, reject) {
    setTimeout(() => resolve("result"), 2000)
})
    .finally(() => console.log("Promise ready resolve"))
    .then(() => console.log(result));   // <-- .then 对结果进行处理

new Promise(function(resolve, reject) {
    throw new Error("error");
})
    .finally(() => console.log("Promise ready Error"))
    .catch(err => console.log(err));    // <-- .catch 对 error 对象进行处理



// Promise编写异步代码 loadScript
// 基于回调函数的变体
console.log('--------------------------');
function loadScript(src, callback) {
    let script = document.createElement('script');
    script.src = src;

    script.onload = () => callback(null, script);
    script.onerror = () => callback(new Error(`Script load error for ${src}`));

    document.head.append(script);
}

// Promise
function loadScriptPromise(src) {
    return new Promise(function(resolve, reject) {
        let script = document.createElement('script');
        script.src = src;

        script.onload = () => resolve(script);
        script.onerror = () => reject(new Error(`Script load error for ${src}`));

        document.head.append(script);
    });
}

let testPromise = loadScriptPromise("https://cdnjs.cloudflare.com/ajax/libs/lodash.js/4.17.11/lodash.js")
testPromise.then(() => console.log(`${script.src} is loaded`), error => console.log(`Error: ${error.message}`));

// Promises和Callbacks对比：
// Promises允许按照自然顺序进行编码，允许loadScript和.then来处理结果；
// 而Callbacks在调用loadScript(script, callback)时，在处理的地方必须有一个callback函数，即在调用loadScript之前，必须知道如何处理结果
// Promises可以根据需要在promise上多次调用.then，每次调用，都会在"订阅列表"中添加一个新的"分析"，一个新的订阅函数，即Promise链
// 而Callbacks只能有一个回调



// 用promise重新解决
// 下列这段代码会输出什么？
console.log('--------------------------');
let promiseT1 = new Promise(function(resolve, reject) {
    resolve(1);

    setTimeout(() => resolve(2), 1000);
});
promiseT1.then(console.log);    // 输出1，第二个对 resolve 的调用会被忽略，因为只有第一次对 reject/resolve 的调用才会被处理。进一步的调用都会被忽略。



//基于promise的延时
// 内建函数 setTimeout 使用了回调函数。请创建一个基于 promise 的替代方案。
// 函数 delay(ms) 应该返回一个 promise。这个 promise 应该在 ms 毫秒后被 resolve，所以可以向其中添加 .then
console.log('--------------------------');
function delay(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
}
delay(3000).then(() => console.log('runs after 3 seconds'));