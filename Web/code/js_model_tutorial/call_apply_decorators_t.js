// 装饰器模式和转发，call/apply


// 透明缓存
// 假设有一个CPU重负载的函数 slow(x)，但是它的结果时稳定的，即对于相同的x，它总是返回相同的结果
// 如果经常调用该函数，可能希望将结果缓存下来，以避免在重新计算上花费额外的时间
// 但是不是将这个功能添加到slow()中，而是创建一个包装器(wrapper)函数，该函数增加了缓存功能，这将带来许多好处
console.log('--------------------------');
function slow(x) {
    // 这里可能会有重负载的CPU密集型工作
    console.log(`Called with ${x}`);
    return x;
}

// 装饰器，一个特殊的函数，接受另一个函数并改变它的行为
function cachingDecorator(func) {
    let cache = new Map();

    return function(x) {
        // 如果缓存中有对应结果则从缓存中读取结果
        if (cache.has(x)) {
            return cache.get(x);
        }

        // 否则就调用func
        let result = func(x);

        //  然后将结果缓存(记住)下来
        cache.set(x, result);
        return result;
    };
}
slow = cachingDecorator((slow));
console.log(slow(1));
console.log("Again: " + slow(1));
console.log(slow(2));
console.log("Again: " + slow(2));
// 使用分离的cachingDecorator而不是改变slow本身的代码有以下几个好处：
// 1. cachingDecorator是可重用的，可以将其应用于另一个函数
// 2. 缓存逻辑是独立的，它没有增加slow本身的复杂性
// 3. 如果需要，可以组合多个装饰器，其他装饰器将遵循同样的逻辑


// 使用"func.call"设定上下文
// 上面提到的缓存装饰器不适用于对象方法
// 在下面的例子中，worker.slow()在装饰后停止工作
// 将对worker.slow的结果进行缓存
console.log('--------------------------');
let worker = {
    someMethod() {
        return 1;
    },

    slow(x) {
        // 可怕的CPU过载任务
        console.log(`Called with ${x}`);
        return x * this.someMethod();   //(*)
    }
};
function cachingDecorator1(func) {
    let cache = new Map();

    return function(x) {
        // 如果缓存中有对应结果则从缓存中读取结果
        if (cache.has(x)) {
            return cache.get(x);
        }

        // 否则就调用func
        // let result = func(x);   //(**) ERROR
        let result = func.call(this, x);

        //  然后将结果缓存(记住)下来
        cache.set(x, result);
        return result;
    };
}
console.log(worker.slow(1));    // 原始方法有效
worker.slow = cachingDecorator1(worker.slow);   //现在对其缓存
console.log(worker.slow(1));
console.log(worker.slow(2)); // Error: Cannot read property 'someMethod' of undefined
// 错误分析：
// 原因在于包装器将原始函数调用func(x)，并且，当这样调用时，函数将得到this = undefined
// 包装器将调用传递给原始方法，但是没有上下文this，因此发生了错误
// 错误解决：
// 有一个特殊的内置函数方法func.call(context, ..args)，它允许调用一个显式设置this的函数
// 语法：func.call(context, arg1, arg2, ...)
// 理解this是如何被传递的
// 1. 在经过装饰后，worker.slow现在是包装器function(x) { ... }
// 2. 当worker.slow(2)执行时，包装器将2作为参数，并且this=worker(它是点符号.之前的对象)
// 3. 在包装器内部，假设结果尚未缓存，func.call(this, x)将当前的this(=worker)和当前的参数(=2)传递给原始方法


// 使用"func.apply"来传递多参数
// 一个更加强大的装饰器，可以传递多参数
console.log('--------------------------');
let workerPower = {
    slow(min, max) {
        console.log(`Called with ${min}, ${max}`);
        return min + max;
    }
}
function cachingDecoratorPower(func, hash) {
    let cache = new Map();
    return function() {
        // 调用hash来从arguments创建一个单独的键
        let key = hash(arguments);  // (*)

        if(cache.has(key)) {
            return cache.get(key);
        }

        // 将包装器获得上下文和所有参数(不仅仅是第一个参数)传递给原始函数
        // let result = func.call(this, ...arguments); //(*)
        let result = func.apply(this, arguments);   // 与使用 call 相同

        cache.set(key, result);
        return result;
    };
}
function hash(args) {
    return args[0] + ',' + args[1];
}
workerPower.slow = cachingDecoratorPower(workerPower.slow, hash);
console.log(workerPower.slow(3, 5));
console.log(("Again " + workerPower.slow(3, 5)))


// 装饰器和函数属性
// 通常用装饰的函数替换一个函数或一个方法是安全的，除了一些小东西，如果原始函数有属性，则装饰后的函数将不再提供这些属性，因为这是装饰者。
// 一些包装器可能会提供自己的属性，存在一种创建装饰者的方法，该装饰者可保留对函数属性的访问权限，但这需要使用特殊的Proxy对象来包装函数。


// 间谍装饰器
// 创建一个装饰器spy(func)，它应该返回一个包装器，该包装器将所有对函数的调用保存在其calls属性中，每个调用都保存为一个参数数组
// P.S. 该装饰器有时对于单元测试很有用，它的高级形式是Sinon.JS库中的sinon.spy
// 由spy(f)返回的装饰器应存储所有参数，然后使用f.apply转发调用
console.log('--------------------------');
function spy(func) {

    function wrapper(...args) {
        wrapper.calls.push(args);
        return func.apply(this, args);
    }

    wrapper.calls = [];
    return wrapper;
}
function work(a, b) {
    console.log( a + b ); // work 是一个任意的函数或方法
}
work = spy(work);
work(1, 2); // 3
work(4, 5); // 9
for (let args of work.calls) {
    console.log( 'call:' + args.join() ); // "call:1,2", "call:4,5"
}


// 延时装饰器
// 创建一个装饰器delay(f, ms)，该装饰器将f的每次调用延时ms毫秒
console.log('--------------------------');
function delay(f, ms) {

    return function(...args) {
        let savedThis = this;
        setTimeout(function() {
            f.apply(savedThis, args);
        }, ms);
    }
}
let f1000 = delay(console.log, 1000);
f1000("test");
let f2000 = delay(console.log, 2000);
f2000("test1", "test2");


// 去抖装饰器
// debounce(f, ms)装饰器的结果应该是一个包装器，该包装器最多允许每隔ms毫秒将调用传递给f一次
// 即当调用debounce函数时，它保证之后所有在距离上一次调用的时间间隔少于ms毫秒的调用都会被忽略
// 在实际中，对于那些用于检索/更新某些内容的函数而言，当知道在短时间内不会什么新内容的时候时，debounce就显得很有用。
console.log('--------------------------');
function debounce(f, ms) {
    // 当isCoolDown是false时，准备好执行，当isCoolDown是true时，等待时间结束
    let isCoolDown = false;

    return function() {
        // 所有调用都被忽略
        if (isCoolDown) return;

        f.apply(this, arguments);

        isCoolDown = true;

        // 在给定延时结束后，将其恢复为false
        setTimeout(() => isCoolDown = false, ms);
    }
}
let f = debounce(console.log, 1000);
f(1); // 立即执行
f(2); // 被忽略
console.log( () => f(3), 100); // 被忽略（只过去了 100 ms）
console.log( () => f(4), 1100); // 运行
console.log( () => f(5), 1500); // 被忽略（距上一次运行不超过 1000 ms）


// 节流装饰器
// 创建一个"节流"装饰器throttle(f, ms) -- 返回一个包装器，最多每隔1ms将调用传递给f一次，那些属于"冷却"期的调用将被忽略
// 与 debounce 的区别 —— 如果被忽略的调用是冷却期间的最后一次，那么它会在延时结束时执行