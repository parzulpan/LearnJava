// 回调介绍


// 一些函数支持异步行为，即现在开始执行的行为，但是它们在稍后才会完成，例如setTimeout函数就是
// 一个具体的例子，使用给定的src加载脚本
function loadScript(src) {
    // 创建一个<script>标签，并将其附加到页面
    // 这将使得具有给定src的脚本开始加载，并在加载完成后运行
    let script = document.createElement('script');
    script.src = src;
    document.head.append(script);
}

loadScript('./js/hello.js');
// 由于脚本是"异步"调用，所以不会等到脚本加载完成才执行
alert("由于脚本是\"异步\"调用，所以不会等到脚本加载完成才执行！\n我会先执行！");


// 自然情况下，浏览器可能没有时间加载脚本，希望了解脚本何时加载完成，以使用其中的新函数和变量
alert('--------------------------');
function loadScript1(src, callback) {
    let script = document.createElement('script');
    script.src = src;

    // 第二参数是一个函数(通常是匿名函数)，该函数会在行为(action)完成时运行
    script.onload = () => callback(script);

    document.head.append(script);
}

// 这被称为"基于回调"的异步编程风格。异步执行某项功能的函数应该提供一个callback参数用于在相应事件完成时调用
loadScript1('./js/hello.js', script = function() {
    alert(`回调函数执行了：the script ${script.src} is loaded`);

});



// 在回调中回调
// 如何依次加载两个脚本，自然的解决方案是将第二个loadScript调用放入回调中，在外部loadScript执行完成时，内部回调就会被回调
// 但是回调太多，这样的解决方案就不够好了！



// 处理Error
alert('--------------------------');
function loadScript2(src, callback) {
    let script = document.createElement('script');
    script.src = src;

    // 第二参数是一个函数(通常是匿名函数)，该函数会在行为(action)完成时运行
    script.onload = () => callback(null, script);

    // 跟踪加载错误
    script.onerror = () => callback(new Error(`Script load error for ${src}`));

    document.head.append(script);
}

// 加载成功时，它会调用 callback(null, script)，否则调用 callback(error)
loadScript2('./js/hello.js', function(err, script) {
    if (err) {
        alert(`处理了error！${err}`);
    } else {
        alert(`加载成功！${script}`)
    }
})
// 说明，其实在loadScript所使用的方案很普通，它被称为"Error优先回调"风格
// 1. callback的第一个参数是为error而保留的，一旦出现error，callback就会被调用
// 2. 第二个参数(和下一个参数，如果需要的话)用于成功的结果，此时callback(null, result1, result2..)就会被调用



// 回调地狱(厄运金字塔)
function loadScriptH(src, callback) {
    let script = document.createElement('script');
    script.src = src;

    // 第二参数是一个函数(通常是匿名函数)，该函数会在行为(action)完成时运行
    script.onload = () => callback(null, script);

    // 跟踪加载错误
    script.onerror = () => callback(new Error(`Script load error for ${src}`));

    document.head.append(script);
}

loadScriptH('1.js', function(error, script) {

    if (error) {
        handleError(error);
    } else {
        // ...
        loadScriptH('2.js', function(error, script) {
            if (error) {
                handleError(error);
            } else {
                // ...
                loadScriptH('3.js', function(error, script) {
                    if (error) {
                        handleError(error);
                    } else {
                        // ...加载完所有脚本后继续 (*)
                    }
                });

            }
        })
    }
});
// 如果调用嵌套的增加，代码层次变得更深，维护难度也随之增加，尤其是我们使用的是可能包含了很多循环和条件语句的真实代码
// 更好的编码方式
loadScriptH('1.js', step1);

function step1(error, script) {
    if (error) {
        handleError(error);
    } else {
        // ...
        loadScriptH('2.js', step2);
    }
}

function step2(error, script) {
    if (error) {
        handleError(error);
    } else {
        // ...
        loadScriptH('3.js', step3);
    }
}

function step3(error, script) {
    if (error) {
        handleError(error);
    } else {
        // ...加载完所有脚本后继续 (*)
    }
}