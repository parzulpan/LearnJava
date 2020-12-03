// 微任务(Microtask)



// Promise的处理程序(handlers) .then、.catch、.finally都是异步的
// 即便一个promise立即被resolve，.then、.catch、.finally下面的代码也会在这些处理程序之前被执行



// 微任务队列(Microtask queue)
// 异步任务需要适当的管理。为此，ECMA 标准规定了一个内部队列 PromiseJobs，通常被称为"微任务队列(microtask queue)"(ES8术语)
// 1. 队列(queue)是先进先出的：首先进入队列的任务会首先运行
// 2. 只有在JS引擎中没有其他任务在运行时，才开始执行任务队列中的任务



// 未处理的rejection
// 如果一个promise的error未被在微任务队列的末尾进行处理，则会出现"未处理的rejection"
// 正常来说，如果预期可能会发生错误，那么就在promise链上添加.catch来处理error
console.log('--------------------------')
let promise = Promise.reject(new Error("Promise Failed"));
promise.catch(err => console.log("caught"));
// 不会运行：error已经被处理
window.addEventListener('unhandledrejection', event => console.log(event.reason));


// 如何忘记添加.catch，那么，微任务队列清空后，JS引擎会触发下面这事件
console.log('--------------------------')
let promiseA = Promise.reject(new  Error("Promise FailedA!"));
// Promise Failed!
window.addEventListener("unhandledrejection", event => console.log(event.reason));


// 如果迟一点再处理这个error
console.log('--------------------------')
let promiseR = Promise.reject(new Error("Promise FailedR!"));
setTimeout(() => promise.catch(err => console.log('caught')), 1000);
// Error：Promise Failed!
window.addEventListener("unhandledrejection", event => console.log(event.reason));



// 当微服务队列中的任务都完成时，才会完成unhandledrejection：引擎会检查promise，
// 如果promise中任意一个出现"rejected"状态，unhandledrejection 事件就会被触发
