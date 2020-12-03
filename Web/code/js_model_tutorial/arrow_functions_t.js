// 深入理解箭头函数


// 箭头函数没有"this"
// 箭头函数没有this，如果访问this，则会从外部获取
// 使用它在对象方法内部进行迭代
console.log('--------------------------');
let group = {
    title: "Our Group",
    students: ["john", "ann", 'ppp'],

    showList() {
        this.students.forEach(
            student => console.log(this.title + ": " + student)
        );
    }
};
group.showList();
// 不具有this自然也就意味着另一个限制：箭头函数不能用作构造器，不能用new调用它们


// 箭头函数和bind
// 箭头函数=>和使用.bind(this)调用的常规函数之间有细微的差别：
// 1. .bind(this) 创建了一个该函数的"绑定版本"
// 2. 箭头函数=>没有创建任何绑定，箭头函数只是没有this，this的查找和常规变量的搜索方式完全相同，即在外部词法环境中查找


// 箭头函数没有"arguments"
// 当需要是使用当前的this和arguments转换一个调用时，这对装饰器来说非常有用
// defer(f, ms) 获得了一个函数，并返回一个包装器，该包装器将调用延迟 ms 毫秒
console.log('--------------------------');
function defer(f, ms) {
    return function() {
        setTimeout(() => f.apply(this, arguments), ms);
    };
}
function sayHiC(who) {
    console.log("Hello, " + who);
}
let sayHiCDeferred = defer(sayHiC, 2000);
sayHiCDeferred("Pin");