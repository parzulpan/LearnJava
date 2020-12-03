// 使用promise进行错误处理


cf = require('cross-fetch');


console.log('--------------------------');
cf.fetch('https://no-such-server.blabla') // rejects
    .then(response => response.json())
    .catch(err => console.log(err)) // TypeError: failed to fetch（这里的文字可能有所不同）

// 隐式try..catch
// Promise的执行者(executor)和promise的处理程序(handler)周围有一个"隐式的try...catch"。
// 如果发生异常，它就会被捕获，并被视为rejection进行处理。
console.log('--------------------------');
new Promise((resolve, reject) => {
    throw new Error("Whoops!");
}).catch(console.log); // Error: Whoops!

new Promise((resolve, reject) => {
    resolve("ok");
}).then((result) => {
    throw new Error("Whoops!"); // reject 这个 promise
}).catch(console.log); // Error: Whoops!

new Promise((resolve, reject) => {
    resolve("ok");
}).then((result) => {
    blabla(); // 没有这个函数
}).catch(console.log); // ReferenceError: blabla is not defined



// 再次抛出(Rethrowing)
// 执行流：catch -> catch -> then
console.log('--------------------------');
new Promise((resolve, reject) => {
    throw new Error("Whoops!");
}).catch(function(error) { // (*)
    if (error instanceof URIError) {
        // 处理它
    } else {
        console.log("Can't handle such error");
        throw error; // 再次抛出此 error 或另外一个 error，执行将跳转至下一个 catch
    }
}).then(function() {
    /* 不在这里运行 */
}).catch(error => { // (**)
    console.log(`The unknown error has occurred: ${error}`);
    // 不会返回任何内容 => 执行正常进行
});



// 未处理的rejection
// 如果出现error，promise的状态将变为"rejected"，然后执行应该跳转至最近的rejection处理程序，
// 如果无处理程序，JS引擎会跟踪此类rejection，会生成一个全局的error




// setTimeout中的错误
// .catch 会被触发么？
new Promise(function(resolve, reject) {
    setTimeout(() => {
        throw new Error("Whoops!");
    }, 1000);
}).catch(console.log);
// 解答：
// .catch不会被触发，虽然函数代码周围有个"隐式的try...catch"，所有的同步错误都得到处理，
// 但是这里的错误并不是在executor运行时生成的，而是在稍后生成的，所以promise无法处理它
