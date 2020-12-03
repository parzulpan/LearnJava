// 映射和集合(Map和Set)


// Map是一个带有键的数据项的集合，就像Object一样，但是他们最大的差别是Map允许任何类型的键(key)
// 支持的方法和属性：
// 1. new Map() 创建map
// 2. map.set(key, value) 根据键存储值
// 3. map.get(key) 根据键来返回值，如果map中不存在对应的key，则返回undefined
// 4. map.has(key) 根据key存在则true，否则返回false
// 5. map.delete(key) 删除指定键的值
// 6. map.clear() 清空map
// 7. map.size 返回当前元素个数
console.log('--------------------------')
let mapT = new Map();
mapT.set('1', 'str1');
mapT.set(1, 'num1');
mapT.set(true, 'bool1');
console.log(mapT.get('1'));
console.log(mapT.get(1));
console.log(mapT.get(2));
console.log(mapT.size);
console.log(mapT.has('1'));
console.log(mapT.has(2));
console.log(mapT.delete(1));
console.log(mapT.size);
console.log(mapT.clear());
console.log(mapT.size);


// Map使用对象作为键
console.log('--------------------------')
let par = {name: 'par'}
let visitsCountMap = new Map();
visitsCountMap.set(par, 123);
console.log(visitsCountMap.get(par))


// 每一次map.set调用都会返回map本身，所以可以进行链式调用
console.log('--------------------------')
mapT.set(4, 'num4')
    .set(5, 'num5')
    .set(6, 'num6');
console.log(mapT.size);


// Map迭代
// 1. map.keys() 遍历并返回所有的键
// 2. map.values() 遍历并返回所有的值
// 3. map.entries() 遍历并返回所有的实体，[key, value]，for .. of 在默认情况下使用的就是这个
// 4. Map内置的 forEach 方法
console.log('--------------------------')
let recipeMap = new Map([
    ['cucumber', 500],
    ['tomatoes', 350],
    ['onion', 50]
]);
//
console.log('--------------------------')
for (let vegetable of recipeMap.keys()) {
    console.log(vegetable);
}
//
console.log('--------------------------')
for (let amount of recipeMap.values()) {
    console.log(amount);
}
//
console.log('--------------------------')
for (let entry of recipeMap) {
    console.log(entry);
}
//
console.log('--------------------------')
recipeMap.forEach( (value, key, map) => {
    console.log(`${key}: ${value}`); // cucumber: 500 etc
});


// Object.entries：从对象创建 Map
console.log('--------------------------')
let objToMap = {
    name: "John",
    age: 30
};
let objMap = new Map(Object.entries(objToMap));
console.log( objMap.get('name') ); // John


// Object.fromEntries：从 Map 创建对象
console.log('--------------------------')
let mapToObj = new Map();
mapToObj.set('banana', 1);
mapToObj.set('orange', 2);
mapToObj.set('meat', 4);
let mapObj = Object.fromEntries(mapToObj);
console.log(mapObj.orange);


// Set是一个特殊的类型集合，值的集合(没有键)，它的每一个值只能出现一次
// 支持的方法和属性：
// 1. new Set(iterable) 创建一个set，如果提供了一个iterable对象(通常是数组)，将会从数组里面复制值到set中
// 2. set.add(value) 添加一个值，返回set本身
// 3. set.delete(value) 删除值，如果value在这个方法调用的时候存在则返回true，否则返回false
// 4. set.has(value) 如果value在set中，返回true，否则返回false
// 5. set.clear() 清空set
// 6. set.size 返回元素个数
console.log('--------------------------')
let set = new Set();
let john = { name: "John" };
let pete = { name: "Pete" };
let mary = { name: "Mary" };
// visits，一些访客来访好几次
set.add(john);
set.add(pete);
set.add(mary);
set.add(john);
set.add(mary);
// set 只保留不重复的值
console.log( set.size ); // 3
for (let user of set) {
    console.log(user.name); // John（然后 Pete 和 Mary）
}


// Set 迭代（iteration）
console.log('--------------------------')
let setI = new Set(["oranges", "apples", "bananas"]);
for (let value of setI) {
    console.log(value);
}
// 与 forEach 相同：
set.forEach((value, valueAgain, setI) => {
    console.log(value);
});
// forEach 的回调函数有三个参数，是为了与 Map 兼容。当然，这看起来确实有些奇怪。
// 但是这对在特定情况下轻松地用 Set 代替 Map 很有帮助，反之亦然。
// Map 中用于迭代的方法在 Set 中也同样支持：


// 过滤数组中的唯一元素
// 定义 arr 为一个数组。创建一个函数 unique(arr)，该函数返回一个由 arr 中所有唯一元素所组成的数组。
console.log('--------------------------');
function unique(arr) {
    return Array.from(new Set(arr));
}
let values = ["Hare", "Krishna", "Hare", "Krishna",
    "Krishna", "Krishna", "Hare", "Hare", ":-O"
];
console.log( unique(values) ); // Hare, Krishna, :-O


// 过滤字谜
// 写一个函数 aclean(arr)，它返回被清除了字谜（anagrams）的数组。
// 对于所有的字谜（anagram）组，都应该保留其中一个词，但保留的具体是哪一个并不重要
console.log('--------------------------');
function aclean(arr) {
    let map = new Map();

    for (let word of arr) {
        // 将单词split成字母，对字母进行排序，之后join回来
        let sorted = word.toLowerCase().split('').sort().join('');
        map.set(sorted, word)
    }
    // map.keys() 返回的是可迭代对象而非数组
    // 使用方法 Array.from 来将它转换为数组
    return Array.from(map.values());
}
let arrAClean = ["nap", "teachers", "cheaters", "PAN", "ear", "era", "hectares"];
console.log( aclean(arrAClean))



