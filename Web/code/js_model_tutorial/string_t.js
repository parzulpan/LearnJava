// 首字母大写
// 写一个函数 ucFirst(str)，并返回首字母大写的字符串 str
export function ucFirst(str) {
    if(!str) return str;

    return str[0].toUpperCase() + str.slice(1);
}


// 检查spam
// 写一个函数 checkSpam(str)，如果 str 包含 viagra 或 XXX 就返回 true，否则返回 false，函数必须不区分大小写
function checkSpam(str) {
    let lowerStr = str.toLowerCase();

    return lowerStr.includes('viagra') || lowerStr.includes('xxx');
}
console.log(checkSpam("buy viagra now!"));
console.log(checkSpam("free xxx"));
console.log(checkSpam("instanceof"));


// 截断文本
// 创建函数 truncate(str, maxlength) 来检查 str 的长度，
// 如果超过 maxlength —— 应使用 "…" 来代替 str 的结尾部分，长度仍然等于 maxlength。
// 函数的结果应该是截断后的文本（如果需要的话）
function truncate(str, maxLength) {
    return (str.length > maxLength) ? str.slice(0, maxLength-1) + `...`: str;
}
console.log(truncate("What I'd like to tell on this topic is:", 20));
console.log(truncate("Hi everyone!", 20));


// 提取货币
// 有以 "$120" 这样的格式表示的花销。意味着：先是美元符号，然后才是数值。
// 创建函数 extractCurrencyValue(str) 从字符串中提取数值并返回。
function extractCurrencyValue(str) {
    return +str.slice(1);
}


