alert('--------------------------');

function sumSum(a) {
    let currentSum = a;

    function f(b) {
        currentSum += b;
        // 没有递归调用，只是返回了函数自己
        return f;
    }

    f.toString  = function() {
        return currentSum;
    }

    return f;
}

alert(sumSum(1)(2));
alert(sumSum(1)(2)(3));
alert(sumSum(5)(-1)(2));
alert(sumSum(6)(-1)(-2)(-3));
alert(sumSum(0)(1)(2)(3)(4)(5));