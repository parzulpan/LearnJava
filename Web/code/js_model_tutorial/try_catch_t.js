// 错误处理，"try...catch"


console.log('--------------------------');
try {
    console.log("start of try runs");
    console.log("end of try runs");
} catch(err) {
    console.log("catch is ignored, because there are no errors");
}

// try...catch仅对运行时的error有效
// try...catch同步工作，如果在"计划的"代码中发生异常，例如setTimeout中，则try..catch不会捕获到异常
console.log('--------------------------');
try {
    setTimeout(function() {
        // noSuchVariable; // Error
    }, 1000);
} catch (e) {
    console.log("won`t work");  // 不会捕捉到
}
// 不会捕捉到的原因：
// 因为try..catch包裹了计划要执行的函数，该函数本身要稍后执行，这是引擎以及离开了try..catch结构
// 为了捕获到计划函数中的异常，那么try..catch必须在这个函数内
setTimeout(function() {
    try {
        noSuchVariable;
    } catch (e) {
        console.log("error is caught here!");
    }
}, 1000);


// Error对象
// 发生错误时，JS生成一个包含有关其详细信息的对象，然后将该对象作为参数传递给catch
// 对于所有内建的error，error对象具有的主要属性：
// 1. name -- Error名称，例如对于一个未定义的变量，名称是"ReferenceError"
// 2. message -- 关于error的详细文字描述
// 3. stack -- 当前的调用栈，用于调试目的的一个字符串，其中包含有关导致error的嵌套调用序列的信息
console.log('--------------------------');
try {
    // lalala;
} catch (err) {
    console.log(err.name);
    console.log(err.message);
    console.log(err.stack);

    // console.log(err);
}


// 可选的"catch"绑定
// 这是该语言的最新功能。旧的浏览器可能需要使用polyfill
// 如果我们不需要 error 的详细信息，catch 也可以忽略它：

try {
    // ...
} catch { // <-- 没有 (err)
          // ...
}


// 使用"try...catch"
console.log('--------------------------');
let trueJson = '{"name":"John", "age": 30}';
let badJson = "{bad json}";
try {
    let user = JSON.parse(badJson);
    // let user = JSON.parse(trueJson);
    console.log(user.name);
} catch (e) {
    console.log( "Our apologies, the data has errors, we'll try to request it one more time." );
    console.log( e.name );
    console.log( e.message );
}


// 抛出自定义的error，使用"Throw"操作符
console.log('--------------------------');
let noNameJson = '{"age": 30}';
try {
    let user = JSON.parse(noNameJson);    // 没有error，但是缺少 name 属性对我们来说确实是个 error

    // 抛出自定义Error
    if (!user.name) {
        throw new SyntaxError("Incomplete data: no name");
    }

    console.log(user.name);
} catch (e) {
    console.log("Json Error: " + e.message);
}


// 在次抛出(Rethrowing)
// catch应该只处理它知道的error，并"抛出"所有其他error
// "再次抛出(rethrowing)"技术可以被解释为：
// 1. Catch捕获所有error
// 2. 在catch(err) {...} 块中，对error对象err进行分析
// 3. 如果不知道如何处理它，就throw error
console.log('--------------------------');
function readData() {
    try {
        let user = JSON.parse(noNameJson);    // 没有error，但是缺少 name 属性对我们来说确实是个 error

        // 抛出自定义Error
        if (!user.name) {
            throw new SyntaxError("Incomplete data: no name");
        }

        // 预料之外的error
        babab();

        console.log(user.name);
    } catch (e) {
        if( e.name === "SyntaxError") {
            console.log("Json Error: " + e.message);
        } else {
            throw e;
        }

    }
}

try {
    readData();
} catch (e) {
    console.log( "External catch got: " + e );
}


// try...catch...finally
// finally如果存在，则在所有情况下都会被执行：
// 1. try之后，如果没有error
// 2. catch之后，如果没有error
// finally子句通常用在：当我们开始做某事的时候，希望无论出现什么情况都要完成某个任务
// 测量一个函数执行所需要的时间
console.log('--------------------------');
let num = 35;
let diff, result;
function fib(n) {
    if( n < 0 || Math.trunc(n) !== n) {
        throw new Error("Must not be negative, and also an integer.");
    }
    return n <= 1 ? n : fib(n-1) + fib(n-2);
}
let startTime = Date.now();
try {
    result = fib(num);
} catch (e) {
    result = 0;
} finally {
    diff = Date.now() - startTime;
}
console.log(result || "error");
console.log(`execution took ${diff}ms`);
