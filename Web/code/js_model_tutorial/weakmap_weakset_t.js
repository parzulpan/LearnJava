// 弱映射和弱集合(WeakMap和WeakSet)

// 从JS的垃圾回收知识可知，JS引擎在值可访问(并可能被使用)时将其存储在内存中
let john = { name: "John" };
// 该对象能被访问，john 是它的引用
// 覆盖引用
john = null;
// 该对象将会被从内存中清除


let johnA = { name: "JohnA" };
let arrayA = [ johnA ];
johnA = null; // 覆盖引用
// john 被存储在数组里, 所以它不会被垃圾回收机制回收
// 我们可以通过 arrayA[0] 来获取它
console.log(arrayA[0]);


// 分析可知，使用对象作为常规 Map 的键，那么当 Map 存在时，该对象也将存在。它会占用内存，并且应该不会被（垃圾回收机制）回收。
console.log('--------------------------');
let par = { name: "par" };
let map = new Map();
map.set(par, "...");
par = null; // 覆盖引用
// par 被存储在 map 中，
// 我们可以使用 map.keys() 来获取它
console.log(map.keys());


// WeakMap 和 Map 的第一个不同点就是，WeakMap 的键必须是对象，不能是原始值
// 如果在 weakMap 中使用一个对象作为键，并且没有其他对这个对象的引用 — 该对象将会被从内存（和map）中自动清除。
let johnB = { name: "JohnB" };
let weakMap = new WeakMap();
weakMap.set(johnB, "...");
johnB = null; // 覆盖引用
// john 被从内存中删除了！


// 使用案例：额外的数据
// WeakMap 的主要应用场景是 额外数据的存储
// 有用于处理用户访问计数的代码。收集到的信息被存储在 map 中：一个用户对象作为键，其访问次数为值。
// 当一个用户离开时（该用户对象将被垃圾回收机制回收），这时就不再需要他的访问次数了。
// 📁 visitsCount.js
let visitsCountMap = new WeakMap(); // weakmap: user => visits count
// 递增用户来访次数
function countUser(user) {
    let count = visitsCountMap.get(user) || 0;
    visitsCountMap.set(user, count + 1);
}

// 📁 main.js
let johnC = { name: "JohnC" };
countUser(johnC); // count his visits
// 不久之后，john 离开了
johnC = null;


// 使用案例：缓存
// 当一个函数的结果需要被记住（“缓存”），这样在后续的对同一个对象的调用时，就可以重用这个被缓存的结果。
// 📁 cache.js
let cache = new WeakMap();
// 计算并记结果
function process(obj) {
    if (!cache.has(obj)) {
        let result = /* calculate the result for */ obj;

        cache.set(obj, result);
    }

    return cache.get(obj);
}

// 📁 main.js
let obj = {/* some object */};
let result1 = process(obj);
let result2 = process(obj);
// ……稍后，我们不再需要这个对象时：
obj = null;
// 无法获取 cache.size，因为它是一个 WeakMap，
// 要么是 0，或即将变为 0
// 当 obj 被垃圾回收，缓存的数据也会被清除


// 可以将用户添加到 WeakSet 中，以追踪访问过我们网站的用户
console.log('--------------------------');
let visitedSet = new WeakSet();
let AA = {name: "Aa" };
let BB = {name: "Bb" };
let CC = {name: "Cc" };
visitedSet.add(AA);
visitedSet.add(BB);
visitedSet.add(AA);
// 检查 AA 是否来访过？
console.log(visitedSet.has(AA));
// 检查 CC 是否来访过？
console.log(visitedSet.has(CC));
AA = null;
// visitedSet 将被自动清理


// 存储unread标识
// 有一个 messages 数组
console.log('--------------------------');
let messages = [
    {text: "Hello", from: "John"},
    {text: "How goes?", from: "John"},
    {text: "See you soon", from: "Alice"}
];
// 你的代码可以访问它，但是 message 是由其他人的代码管理的。该代码会定期添加新消息，删除旧消息，但是你不知道这些操作确切的发生时间。
// 当一个消息被从 messages 中删除后，它应该也从你的数据结构中消失。
// 不能修改 message 对象，例如向其添加我们的属性。因为它们是由其他人的代码管理的，修改该数据可能会导致不好的后果。
let readMessages = new WeakSet();
// 两个消息已读
readMessages.add(messages[0]);
readMessages.add(messages[1]);
// 再读一遍第一条消息
readMessages.add(messages[0]);
// 回答：message[0] 已读？
console.log("Read messages 0: " + readMessages.has(messages[0]));
// 现在 readMessages 只有一个元素（技术上来讲，内存可能稍后才会被清理）
messages.shift()
console.log("Read messages 0: " + readMessages.has(messages[0]));
console.log("Read messages 1: " + readMessages.has(messages[1]));


// 保存阅读日期
let messagesA = [
    {text: "Hello", from: "John"},
    {text: "How goes?", from: "John"},
    {text: "See you soon", from: "Alice"}
];
let readMap = new WeakMap();
readMap.set(messagesA[0], new Date(2017, 1, 1));
console.log(readMap.get(messagesA[0]));