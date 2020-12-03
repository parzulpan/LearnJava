cf = require('cross-fetch');
// Promise链


console.log('--------------------------');
new Promise(function(resolve, reject) {
    setTimeout(() => resolve(1), 1000);
}).then(function(result) {
    console.log(result);    // 1
    return result * 2;
}).then(function(result) {
    console.log(result);    // 2
    return result * 2;
}).then(function(result) {
    console.log(result);    // 4
    return result * 2;
});
// 它的理念是将result通过.then处理程序(handler)链进行传递
// 运行流程如下：
// 1. 初始promise在1秒后进行resolve
// 2. 然后.then处理程序(handler)被调用
// 3. 它返回的值被传入下一个.then处理程序(handler)
// 之所以能这么运行，是因为对promise.then的调用会返回一个promise

// 一个常见错误：将多个.then添加到一个promise上，但这并不是promise chaining
console.log('--------------------------');
let promise = new Promise(function(resolve, reject) {
    setTimeout(() => resolve(1), 2000);
});
promise.then(function(result) {
    console.log(result);      // 1
    return result * 2;
});
promise.then(function(result) {
    console.log(result);      // 1
    return result * 2;
});
promise.then(function(result) {
    console.log(result);      // 1
    return result * 2;
});



// 返回promise
// .then(handler) 中所使用的处理程序（handler）可以创建并返回一个 promise
console.log('--------------------------');
new Promise(function(resolve, reject) {
    setTimeout(() => resolve(1), 1000);
}).then(function(result) {
    console.log(result); // 1
    return new Promise((resolve, reject) => { // (*)
        setTimeout(() => resolve(result * 20), 1000);
    });
}).then(function(result) { // (**)
    console.log(result); // 20
    return new Promise((resolve, reject) => {
        setTimeout(() => resolve(result * 20), 1000);
    });
}).then(function(result) {
    console.log(result); // 400
});



// 更复杂的示例：fetch
// 在前端中，promise经常被用于网络编程，使用fetch方法从远程服务器加载用户信息，它有很多可选的参数
// 语法：let promise = fetch(url)
// 执行这条语句，向url发送网络请求并返回一个promise，当远程服务器返回header时，该promise用使用一个response对象进行resolve
console.log('--------------------------');
cf.fetch('/article/promise-chaining/user.json')
    // 当远程服务器响应时，下面的.then开始执行
    .then(function(response) {
        // 当user.json加载完成时，response.text()会返回一个新的promise
        // 该promise以加载的user.json为result进行resolve
        return response.text();
    })
    .then(function(text) {
        // 远程文件的内容
        console.log(text);
    });

// 将加载好的用户信息发到一个GitHub请求，加载用户个人资料并显示头像
cf.fetch('/article/promise-chaining/user.json')
    // 将其加载为JSON
    .then(response => response.json())
    // 发送一个到GitHub的请求
    .then(user => fetch(`https://api.github.com/users/${user.name}`))
    // 将响应加载为JSON
    .then(response => response.json())
    // 显示头像图片3秒
    .then(githubUser => {
        let img = document.createElement('img');
        img.src = githubUser.avatar_url;
        img.className = "promise-avatar-example";
        document.head.append(img);

        setTimeout(() => img.remove(), 3000);
    });

// 如果.then(或catch/finally都可以)处理程序(handler)返回一个promise，那么链的其余部分将会等待，直到它状态变为settled。
// 当它被settled后，其result(或error)将被进一步传递下去



// Promise：then对比catch
// 这两个代码片段是否相等？换句话说，对于任何处理程序（handler），它们在任何情况下的行为都相同吗？
// promise.then(f1).catch(f2);     // (*)
// promise.then(f1, f2);   // (**)
// 解答:
// 它们不相等，不同之处在于，(*)如果f1中出现error，那么在这儿它会被.catch处理，(**)则不会
// 因为error沿着链传递的，而在(**)中，f1下面没有链。

