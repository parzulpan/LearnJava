// Async/await
cf = require('cross-fetch');


// Async/await是一种以更舒适的方式使用promise的一种特殊语法，同时它也非常容易理解和使用



// Async
// 这个关键字可以被放置在一个函数前面
console.log('--------------------------')
async function f() {
    return 1;
}
// 表达了这个函数总是返回一个promise，其他值将自动被包装在一个resolved的promise中
f().then(console.log);



// Await
// 只在async函数内工作 let value = await promise;
// 它让JS引擎等待知道promise完成(settle)并返回结果
async function ff() {
    let promise = new Promise(function(resolve, reject) {
        setTimeout(() => resolve("done"), 1000)
    });
    let tipP = "--------------------------"
    // 等待，直到 promise resolve (*)

    let tip = await tipP;
    let result = await promise;
    console.log(tip);
    console.log(result)
}
ff();

// 注意：await字面的意思就是让JS引擎等待直到promise settle，然后以promise的结果继续执行，这个行为不会耗费任何CPU资源



// await不能再顶层代码运行
// 用在顶层代码中会报语法错误
// let response = await fetch('https://www.baidu.com');
// let user = await response.json();
// 可以将其包裹在一个匿名 async 函数中
(async () => {
    let tipP = "--------------------------"
    let response = await cf.fetch('https://www.baidu.com');
    let tip = await tipP;
    let status = await response.status;
    console.log(tip);
    console.log(status)
})();



// await 接受 "thenables"
// 像 promise.then 那样，await 允许使用 thenable 对象（那些具有可调用的 then 方法的对象）。
// 这里的想法是，第三方对象可能不是一个 promise，但却是 promise 兼容的：如果这些对象支持 .then，那么就可以对它们使用 await。
class Thenable {
    constructor(num) {
        this.num = num;
    }
    then(resolve, reject) {
        console.log(resolve);
        // 1000ms 后使用 this.num*2 进行 resolve
        setTimeout(() => resolve(this.num * 2), 1000); // (*)
    }
}
async function fff() {
    let tipP = "--------------------------"
    let tip = await tipP;
    // 等待 1 秒，之后 result 变为 2
    let result = await new Thenable(1);
    console.log(tip);
    console.log(result);
}
fff();



// Error处理
// 如果一个promise正常resolve，await promise返回的就是其结果，但是如果promise被reject，它将throw这个error
async function fE() {
    await Promise.reject(new Error("Whoops!"));
}

async function fE1() {
    throw new Error("Whoops!");
}
// fE()和fE1()完全等效
// fE().catch(console.log);



// 用async/await来重写
// 使用 async/await 而不是 .then/catch
console.log('--------------------------')
function loadJson(url) {
    return cf.fetch(url)
        .then(response => {
            if (response.status === 200) {
                return response.json();
            } else {
                throw new Error(response.value.status);
            }
        })
}

loadJson('no-such-user.json')
    .catch(console.log); // Error: 404

// 改写
console.log('--------------------------')
async function loadJsonA(url) { // (1)
    let response = await cf.fetch(url); // (2)

    if (response.status === 200) {
        let json = await response.json(); // (3)
        return json;
    }

    throw new Error(response.value.status);
}
loadJsonA('no-such-user.json')
    .catch(console.log); // Error: 404 (4)



// 在非 async 函数中调用 async 函数
// 有一个“普通”函数。如何在这个函数中调用 async 函数并使用其结果？
async function wait() {
    await new Promise(resolve => setTimeout(resolve, 1000));

    return 10;
}

function fNoWait() {
    // ...这里怎么写？
    // 需要调用 async wait() 并等待以拿到结果 10
    // 记住，不能使用 "await"
    // 解决: 只需要把 async 调用当作 promise 对待，并在它的后面加上 .then 即可
    wait().then(result => console.log(result));
}
fNoWait();
// 在函数最外层作用域不得不的使用 promise.then/catch
wait().then(result => console.log(result));
