// Promise API
cf = require('cross-fetch');



// 在Promise类中，有5中静态方法
// Promise.all
// 假设希望并行执行多个promise，并等待所有promise都准备就绪
// let promise = Promise.all([...])，
// 它接受一个promise数组作为参数(从技术上讲，它可以是任何可迭代的数据，但通常是一个数组)，并返回一个新的promise
// 当所有给定的promise都被settled时，新的promise才会resolve，并且其结果数组将成为新的promise的结果

// 下面的 Promise.all 在 3 秒之后被 settled，然后它的结果就是一个 [1, 2, 3] 数组
console.log('--------------------------');
Promise.all([
    new Promise(resolve => setTimeout(() =>resolve(1), 3000)),  //1
    new Promise(resolve => setTimeout(() =>resolve(2), 3000)),  //2
    new Promise(resolve => setTimeout(() =>resolve(3), 3000)),  //3
]).then(console.log);
// 注意：结果数组中元素的顺序与其在源promise中的顺序相同

// 一个常见的技巧是，将一个任务数据组映射(map)到一个promise数组，然后将其包装到Promise.all
console.log('--------------------------');
let urls = [
    'https://www.baidu.com/',
    'https://github.com/',
    'https://stackoverflow.com/',
];
// 将每个url映射(map)到fetch的promise中
let requests = urls.map(url => cf.fetch(url));
// Promise.all等待所有任务都resolved
Promise.all(requests)
    .then(responses => responses.forEach(
        response => console.log(`${response.url}: ${response.status}`)
    ));
// 注意：如果任意一个promise被reject，由Promise.all返回的promise就会立即reject，完全忽略列表中其他的 promise，并且带有的就是这个error
// 通常，Promise.all(...)接受可迭代对象(iterable)的promise，但是如果这些对象中的任意一个都不是promise，那么它将被"按原样"传递给结果数组



// Promise.allSettled
// 它等待所有的promise都被settle，无论结果如何，使得数组具有：
// 1. {status: "fulfilled", value: result} 对于成功的响应
// 2. {status: "rejected", reason: error} 对于error
// 想要获取(fetch)多个用户的信息，即使其中有一个请求失败，仍然需要成功的
console.log('--------------------------');
let urlsS = [
    'https://www.baidu.com/',
    'https://github.com/',
    'https://error.error/',
];
Promise.allSettled(urlsS.map(url => cf.fetch(url)))
    .then(results => {
        results.forEach((result, num) => {
            if(result.status === "fulfilled") {
                console.log(`${urlsS[num]}: ${result.status}, value: ${result.value.status}`);
            }
            if(result.status === "rejected") {
                console.log(`${urlsS[num]}: ${result.status}, reason: ${result.reason}`);
            }
        });
    });
// results output:
// https://www.baidu.com/: fulfilled, value: 200
// https://github.com/: fulfilled, value: 200
// https://error.error/: rejected, reason: FetchError: request to https://error.error/ failed, reason: getaddrinfo ENOTFOUND error.error



// 自定义allSettled
// 如果浏览器不支持 Promise.allSettled，很容易进行 polyfill:
if(!Promise.allSettled) {
    Promise.allSettled = function(promises) {
        return Promise.all(promises.map(p => Promise.resolve(p).then(value => ({
            state: 'fulfilled',
            value
        }), reason => ({
            state: 'rejected',
            reason
        }))));
    };
}



// Promise.race
// 它与Promise.all类似，当只等待第一个settled的promise并获取其结果/error
console.log('--------------------------');
Promise.race([
    new Promise((resolve, reject) => setTimeout(() => resolve(1), 1000)),
    new Promise((resolve, reject) => setTimeout(() => resolve(3), 3000)),
    new Promise((resolve, reject) => setTimeout(() => reject(new Error("Whoops!")), 2000)),

]).then(console.log); // 1



// 在现代的代码中，很少需要使用 Promise.resolve 和 Promise.reject 方法，因为 async/await 语法使它们变得有些过时了
// Promise.resolve(value) 用结果 value 创建一个 resolved 的 promise
// let promise = new Promise(resolve => resolve(value));

// Promise.reject(error) 用 error 创建一个 rejected 的 promise
// let promise = new Promise((resolve, reject) => reject(error));