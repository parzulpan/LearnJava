// 递归和堆栈


// 执行上下文和堆栈
// 有关正在运行的函数的执行过程的相关信息被存储在其执行上下文中
// 执行上下文是一个内部的数据结构，它包含有关函数执行时的详细细节：当前控制流所在的位置、当前的变量、this的值以及其他的一些内部细节
// 一个函数调用仅具有一个与其关联的执行上下文
// 当一个函数进行嵌套调用是，它会发生以下事：
// 1. 当前函数被暂停
// 2. 与它关联的执行上下文被一个叫做执行上下文堆栈的数据结构保存
// 3. 执行嵌套调用
// 4. 嵌套调用结束后，从堆栈中恢复之前的执行上下文，并从停止的位置恢复外部函数


// 递归遍历
console.log('--------------------------');
let company = { // 是同一个对象，简介起见被压缩了
    sales: [{name: 'John', salary: 1000}, {name: 'Alice', salary: 1600 }],
    development: {
        sites: [{name: 'Peter', salary: 2000}, {name: 'Alex', salary: 1800 }],
        internals: [{name: 'Jack', salary: 1300}]
    }
};
// 用来完成任务的函数
function sumSalaries(department) {
    if (Array.isArray(department)) { // 情况（1）
        return department.reduce((prev, current) => prev + current.salary, 0); // 求数组的和
    } else { // 情况（2）
        let sum = 0;
        for (let subdep of Object.values(department)) {
            sum += sumSalaries(subdep); // 递归调用所有子部门，对结果求和
        }
        return sum;
    }
}
console.log(sumSalaries(company)); // 7700


// 对数字求和到给定值
// 编写一个函数sumTo(n)计算1+2+3+...+n的和
// 用三种方式实现：
// 1. 使用循环
console.log('--------------------------');
function sumTo1(n) {
    let sum = 0;
    for(let i = 0; i <= n; ++i) {
        sum += i;
    }
    return sum;
}
console.log(sumTo1(100));
// 2. 使用递归，对n > 1执行sumTo(n) = n + sumTo(n-1)
function sumTo2(n) {
    if(n === 1) return 1;
    return n + sumTo2(n-1);
}
console.log(sumTo2(100));
// 3. 使用等差数列求和公式
function sumTo3(n) {
    return n * (n + 1) / 2;
}
console.log(sumTo3(100));


// 计算阶乘
console.log('--------------------------');
function factorial(n) {
    return (n !== 1) ? n * factorial(n - 1) : 1;
}
console.log(factorial(10));


// 斐波那契数
// 自下而上的动态规划
console.log('--------------------------');
function fib(n) {
    let a = 1;
    let b = 1;
    for (let i = 3; i < n; ++i) {
        let c = a + b;
        a = b;
        b = c;
    }
    return b;
}
console.log(fib(3));
console.log(fib(7));
console.log(fib(77));
console.log(fib(99));


// 输出一个单链表
// 编写一个可以逐个输出链表元素的函数 printList(list)
// 循环解法
console.log('--------------------------');
let list = {
    value: 1,
    next: {
        value: 2,
        next: {
            value: 3,
            next: {
                value: 4,
                next: null
            }
        }
    }
};
function printList(list) {
    let tmp = list;
    while (tmp) {
        console.log(tmp.value);
        tmp = tmp.next;
    }
}
printList(list);

// 递归解法
console.log('--------------------------');
let list1 = {
    value: 1,
    next: {
        value: 2,
        next: {
            value: 3,
            next: {
                value: 4,
                next: null
            }
        }
    }
};
function printList1(list) {
    console.log(list.value); // 输出当前元素
    if (list.next) {
        printList1(list.next); // 链表中其余部分同理
    }
}
printList1(list1);


// 反向输出单链表
// 循环解法
console.log('--------------------------');
let list2 = {
    value: 1,
    next: {
        value: 2,
        next: {
            value: 3,
            next: {
                value: 4,
                next: null
            }
        }
    }
};
function printReverseList(list) {
    let arr = [];
    let tmp = list;
    while (tmp) {
        arr.push(tmp.value);
        tmp = tmp.next;
    }
    for (let i = arr.length - 1; i >= 0; i--) {
        console.log( arr[i] );
    }
}
printReverseList(list2);

// 递归解法
console.log('--------------------------');
let list3 = {
    value: 1,
    next: {
        value: 2,
        next: {
            value: 3,
            next: {
                value: 4,
                next: null
            }
        }
    }
};
function printReverseList2(list) {

    if (list.next) {
        printReverseList(list.next);
    }

    console.log(list.value);
}
printReverseList2(list3);