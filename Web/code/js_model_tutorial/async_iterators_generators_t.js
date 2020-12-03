// Async iterator 和 generator
cf = require('cross-fetch');



// Async iterator 异步迭代器
// 一个"常规的"可迭代对象
console.log('--------------------------');
let range = {
    from: 0,
    to: 5,

    [Symbol.iterator] () {
        return {
            current: this.from,
            last: this.to,

            next() {
                if(this.current < this.last) {
                    return {done:false, value:this.current++};
                } else {
                    return {done:true};
                }
            }
        };
    }
};
for(let value of range) {
    console.log(value);
}

// 为了使对象可以异步迭代，
// 1. 需要使用Symbol.asyncIterator 取代 Symbol.iterator
// 2. next() 方法应该返回一个promise
// 3. 应该使用 for await (let item of iterable) 循环来迭代这样的对象
console.log('--------------------------');
let rangeAsync = {
    from: 0,
    to: 5,

    [Symbol.asyncIterator] () {
        return {
            current: this.from,
            last: this.to,

            async next() {
                await new Promise(resolve => setTimeout(resolve, 1000));

                if (this.current < this.last) {
                    return {done: false, value: this.current++};
                } else {
                    return {done: true};
                }
            }
        };
    }
};
(async () => {
    for await (let value of rangeAsync) {
        console.log(value);
    }
})();



// Async generator 异步生成器
console.log('--------------------------');
function* generatorSequence(start, end) {
    for(let i = start; i < end; i++) {
        yield i;
    }
}
for (let value of generatorSequence(0, 5)) {
    console.log(value);
}

// 改为异步生成器
// 加async 关键字，然后就能在 generator 内部使用 await，并依赖于 promise 和其他异步函数
console.log('--------------------------');
async function* generatorSequenceAsync(start, end) {
    for (let i = start; i<= end; i++) {
        await new Promise(resolve => setTimeout(resolve, 3000));

        yield i;
    }
}
(async () => {
    let generator = generatorSequenceAsync(0, 5);
    for await (let value of generator) {
        console.log(value);
    }
})();



// Async iterable
// 易知，要使一个对象可迭代，需要给它添加Symbol.iterator
// 对于Symbol.iterator来说，一个通常的做法是返回一个generator，而不是返回一个带有next()方法的普通对象
console.log('--------------------------');
let rangeGenerator = {
    from: 0,
    to: 5,

    *[Symbol.iterator] () {
        for (let value = this.from; value < this.to; value++) {
            yield value;
        }
    }
};
for (let value of rangeGenerator) {
    console.log(value);
}

// 给generator加上异步行为
console.log('--------------------------');
let rangeGeneratorAsync = {
    from: 0,
    to: 5,

    async *[Symbol.asyncIterator] () {
        for(let value = this.from; value < this.to; value++) {
            await new Promise(resolve => setTimeout(resolve, 5000));

            yield value;
        }
    }
};
(async () => {
    for await (let value of rangeGeneratorAsync) {
        console.log(value);
    }
})();



// 实际的例子
// 目前，有很多在线服务都是发送的分页数据，例如：当需要一个用户列表时，一个请求只返回一个预定义数量的用户，即一页，并提供指向下一页的url
// GitHub 允许使用相同的分页提交(paginated fashion)的方式找回 commit：
// 1. 应该提交一个请求到这种格式的URL: https://api.github.com/repos/<repo>/commits
// 2. 它返回一个包含 30 条 commit 的 JSON，并在返回的 Link header 中提供了指向下一页的链接
// 3. 然后可以将该链接用于下一个请求，以获取更多 commit，以此类推。
console.log('--------------------------');
async function* fetchCommits(repo) {
    let url = `https://api.github.com/repos/${repo}/commits`;

    while (url) {
        const response = await cf.fetch(url, {
            headers: {'User-Agent': 'Our script'},
        });

        const body = await response.json();

        let nextPage = response.headers.get('Link').match(/<(.*?)>; rel="next"/);
        nextPage = nextPage && nextPage[1];

        for (let commit of body) {
            yield commit;
        }
    }
}
(async () => {

    let count = 0;

    for await (const commit of fetchCommits('javascript-tutorial/en.javascript.info')) {

        console.log(commit.author.login);

        if (++count === 10) { // 在获取了 10 个 commit 时停止
            break;
        }
    }

})();