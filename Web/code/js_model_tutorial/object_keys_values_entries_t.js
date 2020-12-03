// Object.keys，values，entries


// 对于普通对象，下列方法是可用的
// 1. Object.keys(obj) —— 返回一个包含该对象所有的键的数组。
// 2. Object.values(obj) —— 返回一个包含该对象所有的值的数组。
// 3. Object.entries(obj) —— 返回一个包含该对象所有 [key, value] 键值对的数组。
let user = {
    name: "John",
    age: 30
};
console.log(Object.keys(user));
console.log(Object.values(user));
console.log(Object.entries(user));


// 转换数组
// 对象缺少数组存在的许多方法，例如map和filter等
// 想应用它们，那么可以在 Object.entries 之后使用 Object.fromEntries：
// 1. 使用Object.entries(obj)从obj获取键/值对组成的数组
// 2. 对该数组使用数组方法，例如map
// 3. 对结果数组使用Object.fromEntries(array)方法，将结果转回成对象
// 有一个带有价格的对象，并想将它们加倍
console.log('--------------------------');
let prices = {
    banana: 1,
    orange: 2,
    meat: 4,
};
let doublePrice = Object.fromEntries(
    // 转换为数组，之后使用 map 方法，然后通过 fromEntries 再转回到对象
    Object.entries(prices).map(([key, value]) => [key, value*2])
);
console.log(doublePrice.meat);


// 属性求和
// 有一个带有任意数量薪水的 salaries 对象。
// 编写函数 sumSalaries(salaries)，该函数使用 Object.values 和 for..of 循环返回所有薪水的总和。
// 如果 salaries 是空对象，那么结果必须是 0。
console.log('--------------------------');
function sumSalaries(salaries) {
    let sum = 0;
    for( let salary of Object.values(salaries)) {
        sum += salary;
    }
    return sum;
}
let salaries = {
    "John": 100,
    "Pete": 300,
    "Mary": 250
}
console.log(sumSalaries(salaries));


// 计算属性数量
// 写一个函数 count(obj)，该函数返回对象中的属性的数量
console.log('--------------------------');
function count(obj) {
    return Object.keys(obj).length;
}
console.log(count(salaries));