// Promisification



// "Promisification"是用于一个简单转换的一个长单词，它指将一个接受回调的函数转换为一个返回promise的函数
// 由于许多函数和库都是基于回调的，因此，在实际开发中经常会需要这种转换，因为使用promise更加方便，故将基于回调的函数和库promisify(promise化)是有意义的

function loadScript(src, callback) {
    let script = document.createElement('script');
    script.src = src;

    script.onload = () => callback(null, script);
    script.onerror = () => callback(new Error(`Script load error for ${src}`));

    document.head.append(script);
}
// 用法：
// loadScript('path/script.js', (err, script) => {...})

// 将其promisify化，能达到同样的效果，但它只接受src(没有回调)并返回promise
let loadScriptPromise = function(src) {
    return new Promise((resolve, reject) => {
        loadScript(src, (err, script) => {
            if(err)
                reject(err);
            else
                resolve(script);
        });
    })
}
// 用法：
// loadScriptPromise('path/script.js').then(...)

// 在实际开发中，可能需要promisify很多函数，使用一个helper很有意义，
// 将其称为promisify(f): 它接受一个需要被promisify的函数f，并返回一个包装(wrapper)
function promisify(f) {
    return function (...args) {     // 返回一个包装函数(wrapper-function)
        return new  Promise((resolve, reject) => {
            function callback(err, ...result) {    // 对f的自定义的回调
                if(err)
                    reject(err);
                else {
                    // // 如果 manyArgs 被指定，则使用所有回调的结果 resolve
                    resolve(manyArgs ? results : results[0]);
                }
            }

            args.push(callback);    // 将自定义的回调附加到f参数的末尾

            f.call(this, ...args);  // 调用原始的函数
        });

    };
}
// 用法：
// f = promisify(f, true);
// f(...)
//     .then(arrayOfResults => ..., err => ...)


// 注意：Promisification是一种很好的方法，特别是在使用async/await的时候，但是不是回调的完全替代
// 一个promise可能只有一个结果，但从技术上将，一个回调将函数可能被调用多次，
// 因此Promisification仅适用于调用一次回调的函数，进一步调用将被忽略
