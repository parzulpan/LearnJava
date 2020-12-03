// 私有的和受保护的属性和方法


// 内部接口和外部接口
// 在面向对象的编程中，属性和方法分为两组
// 1. 内部接口 -- 可以通过该类的其他方法访问，但不能从外部访问的方法和属性
// 2. 外部接口 -- 也可以从类的外部访问的方法和属性
// 在JS中，有两种类型的对象字段(属性和方法)
// 1. 公共的：可以从任何地方访问，它们构成了外部接口
// 2. 私有的：只能从类的内部访问，它们构成了内部接口


// 受保护的"waterAmount"
console.log('--------------------------');
class CoffeeMachine {
    // 属性waterAmount和power时公共的，可以轻松地外部讲它们get/set成任何值
    waterAmount = 0;    // 内部的水量

    constructor(power) {
        this.power = power;
        console.log(`Created a coffee-machine, power: ${power}`);
    }

}
// 创建咖啡机
let coffeeMachine = new CoffeeMachine(100);
// 加水
coffeeMachine.waterAmount = 200;
console.log(coffeeMachine.waterAmount);

// 受保护的属性通常以下划线_作为前缀
console.log('--------------------------');
class CoffeeMachine1 {
    _waterAmount = 0;    // 内部的水量

    constructor(power) {
        this._power = power;
    }

    set waterAmount(amount) {
        if (amount < 0) throw new Error('waterAmount Error');
        this._waterAmount = amount;
    }

    get waterAmount() {
        return this._waterAmount;
    }

}
// 创建咖啡机
let coffeeMachine1 = new CoffeeMachine1(1000);
// 加水
// coffeeMachine1.waterAmount = -100;   // Error
console.log(coffeeMachine1.waterAmount);


// 只读的"power"
// 有时候一个属性必须只能被在创建时进行设置，之后不再被修改，要做到这个，只需要设置getter，而不设置setter
console.log('--------------------------');
class CoffeeMachine2 {
    // ...

    constructor(power) {
        this._power = power;
    }

    get power() {
        return this._power;
    }

}
// 创建咖啡机
let coffeeMachine2 = new CoffeeMachine2(100);
console.log(`Power is: ${coffeeMachine2.power}W`); // 功率是：100W
coffeeMachine2.power = 25; // Error（没有 setter）
console.log(coffeeMachine2.power);  // 100


// 私有的"#waterLimit"
// 它为私有属性和方法提供语言支持
// 私有属性和方法应该以 # 开头，它们只有类的内部可被访问
// 注意：目前，在各浏览器中不支持私有字段，但可以用 polyfill 解决，即自行解决
console.log('--------------------------');
class CoffeeMachine3 {
    #waterLimit = 200;

    // #checkWater(value) {
    //     if ( value < 0 ) throw new Error("Error");
    //     if ( value > this.#waterLimit ) throw new Error("Too much water");
    // }

    // 使 waterAmount 成为 #waterAmount 的一个访问器
    set waterAmount(value) {
        if ( value < 0 ) throw new Error("Error");
        if ( value > this.#waterLimit ) throw new Error("Too much water");
        this.#waterLimit = value;
    }

    get waterAmount() {
        return this.#waterLimit;
    }

}
let coffeeMachine3 = new CoffeeMachine3();
// 不能从类的外部访问类的私有属性和方法
// coffeeMachine3.#checkWater();   // Error
// coffeeMachine3.#waterLimit = 1000;  // Error
coffeeMachine3.waterAmount = 100;
console.log(coffeeMachine3.waterAmount);
