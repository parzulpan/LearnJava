// 调度：setTimeout 和 setInterval


// 有时并不想立即执行一个函数，而是等待一段时间之后再执行，这就是所谓的"计划调用(scheduling a call)"
// 目前有两种方式可以实现：
// 1. setTimeout 允许将函数推迟到一段时间间隔之后执行
// 2. setInterval 允许重复允许一个函数，从一段时间间隔之后开始允许，之后以该事件间隔连续重复执行该函数


// setTimeout
// 语法：let timerId = setTimeout(func|code, [delay], [arg1], [arg2], ...)
// 参数说明： func|code：想要执行的函数或代码字符串，一般传入的是函数。
// delay：执行前的延时，以毫秒为单位，默认值为0。
// arg1，arg2…：要传入被执行函数（或代码字符串）的参数列表
console.log('--------------------------');
function sayHi(phrase, who) {
    console.log(phrase + ': ' + who);
}
setTimeout(sayHi, 4000, "Hello", "PPP");
// setTimeout(sayHi(), 4000, "Hello", "PPP");
// 这样不行，因为 setTimeout 期望得到一个对函数的引用。
// 而这里的 sayHi() 很明显是在执行函数，所以实际上传入 setTimeout 的是 函数的执行结果。


// 用clearTimeout来取消调度
// setTimeout在调用时会返回一个"定时器标识符(timer identifier)"
console.log('--------------------------');
let timerId = setTimeout(() => console.log("never happens"), 4000);
console.log(timerId);   // 定时器标识符
clearTimeout(timerId);
console.log(timerId);   // 还是这个标识符（并没有因为调度被取消了而变成 null）


// setInterval
// 语法：let timerId = setInterval(func|code, [delay], [arg1], [arg2], ...)
// 参数意义和setTimeout类似
// 每 2 秒重复一次
console.log('--------------------------');
let timerIdI = setInterval(() => console.log('tick'), 2000);
// 5 秒之后停止
setTimeout(() => { clearInterval(timerIdI); console.log('stop'); }, 5000);


// 嵌套的setTimeout
// 周期性调度有两种方式。
// 一种是使用 setInterval，另外一种就是嵌套的 setTimeout
/** instead of:
 let timerId = setInterval(() => alert('tick'), 2000);
 */
console.log('--------------------------');
let timerIdT = setTimeout(function tick() {
    console.log('tick');
    timerIdT = setTimeout(tick, 2000); // (*)
}, 2000);
// 嵌套的 setTimeout 要比 setInterval 灵活得多。
// 采用这种方式可以根据当前执行结果来调度下一次调用，因此下一次调用可以与当前这一次不同。


// 垃圾回收和 setInterval/setTimeout 回调（callback）
// 当一个函数传入setInterval/setTimeout时，将为其创建一个内部引用，并保存在调度程序中，
// 这样的话，即使这个函数没有其他引用，也能防止垃圾回收器(GC)将其回收
// 对于setInterval，传入的函数也是一直存在于内存中，直到clearInterval被调用
// 不过这样有一个副作用，如果函数引用了外部变量(闭包)，那么只要这个函数还存在，外部变量也会随之存在，它们可能比函数本身占用更多的内存，
// 因此，当不在需要调度函数时，最好取消它，即使这是个占用内存很小的函数


// 零延时的 setTimeout
// 特殊的用法：setTimeout(func, 0) or setTimeout(func)
// 这样调度可以让func尽快执行，但是只有在当前正在执行的脚本执行完成后，调度程序才会调用它
// 下面这段代码会先输出 “Hello”，然后立即输出 “World”
console.log('--------------------------');
setTimeout(() => console.log("World"));
console.log("Hello");


// 每秒输出一次
// 编写一个函数printNumbers(form, to)，使其每秒输出一个数字，数字从from开始，到to结束
// 使用 setInterval
console.log('--------------------------');
function printNumbers1(from, to) {
    let current = from;

    let timerID = setInterval(function() {
        console.log(current);
        if(current === to) {
            clearInterval(timerID);
        }
        current++;
    }, 1000)
}
printNumbers1(5, 10)

// 使用嵌套的 setTimeout
console.log('--------------------------');
function printNumbers2(from, to) {
    let current = from;

    setTimeout(function go() {
        console.log(current);
        if (current < to) {
            setTimeout(go, 1000);
        }
        current++;
    }, 1000);
}
// 用例：
printNumbers2(5, 10)


// setTimeout会显示什么
// 下面代码中使用 setTimeout 调度了一个调用，然后需要运行一个计算量很大的 for 循环，这段运算耗时超过 100 毫秒。
// 调度的函数会在何时运行？
// 1. 循环执行完成后。
// 2. 循环执行前。
// 3. 循环刚开始时。
// 控制台会显示什么？
console.log('--------------------------');
let i = 0;
setTimeout(() => console.log(i), 100); // ?
// 假设这段代码的运行时间 >100ms
for(let j = 0; j < 100000000; j++) {
    i++;
}
// 任何 setTimeout 都只会在当前代码执行完毕之后才会执行。
//  控制台会显示100000000