// 自定义Error，拓展Error



// 拓展Error
console.log('--------------------------');
class ValidationError extends Error {
    constructor(message) {
        super(message);
        this.name = "ValidationError";
    }
}

// function test() {
//     throw new ValidationError("Whoops");
// }
//
// try {
//     test();
// } catch (err) {
//     console.log(err.message);
//     console.log(err.name);
//     console.log(err.stack);
// }

function readUser(json) {
    let user = JSON.parse(json);
    if(!user.age) {
        throw new ValidationError("No field: age");
    }
    if(!user.name) {
        throw new ValidationError("No field: name");
    }
    return user;
}

try {
    let user = readUser('{"age": 25}');
} catch (err) {
    if(err instanceof ValidationError) {
        console.log("Invalid data: " + err.message);
    } else if(err instanceof SyntaxError) {
        console.log("JSON Syntax Error: " + err.message);
    } else {
        throw err;
    }
} finally {
    console.log("finally");
}



// 深入继承
// 针对缺少属性的错误来在制作一个更具体地Error类
console.log('--------------------------');
class PropertyRequiredError extends ValidationError {
    constructor(property) {
        super("No property: " + property);
        this.name = "PropertyRequiredError";
        this.property = property;
    }
}

function readJSON1(json) {
    let user = JSON.parse(json);
    if(!user.age) {
        throw new PropertyRequiredError("age");
    }
    if(!user.name) {
        throw new PropertyRequiredError("name");
    }
    return user;
}

try {
    let user = readJSON1('{"age": 25}');
} catch (err) {
    if(err instanceof ValidationError) {
        console.log("Invalid data: " + err.message);
        console.log(err.name);
        console.log(err.property);
    } else if (err instanceof SyntaxError) {
        console.log("JSON syntax error: " + err.message);
    } else {
        throw err;
    }
}



// 包装异常
// 1. 将创建一个新的类ReadError来表示一般的"数据读取"error
// 2. 函数readUser将捕获内部发生的数据读取error，例如ValidationError和SyntaxError，并生成一个ReadError来进行替代
// 3. 对象ReadError会把对原始error的引用保存在其cause属性中
// 之后调用readUser的代码只需要检查ReadError，而不必检查每种数据读取error，若想要了解更多error细节，可以检查readUser的cause属性
console.log('--------------------------');
class ReadError extends Error {
    constructor(message, cause) {
        super(message);
        this.cause = cause;
        this.name = 'ReadError';
    }
}

function validateUser(user) {
    if(!user.age) {
        throw new PropertyRequiredError("age");
    }
    if(!user.name) {
        throw new PropertyRequiredError("name");
    }
}

function readUser2(json) {
    let user;
    try {
        user = JSON.parse(json);
    } catch (err) {
        if( err instanceof SyntaxError) {
            throw new ReadError("Syntax Error", err);
        } else {
            throw err;
        }
    }

    try {
        validateUser(user);
    } catch (err) {
        if( err instanceof ValidationError) {
            throw new ReadError("Validation Error", err);
        } else {
            throw err;
        }
    }
}

try {
    readUser2('{bad json}');
} catch (err) {
    if(err instanceof ReadError) {
        console.log(err);
        console.log("Original error" + err.cause);
    } else {
        throw err;
    }

}



// 继承SyntaxError
// 创建一个继承自内建类SyntaxError的类FormatError，它应该支持message,name,stack属性
console.log('--------------------------');
class FormatError extends SyntaxError {
    constructor(message) {
        super(message);
        this.name = this.constructor.name;
    }
}

let err = new FormatError("formatting error");

console.log( err.message ); // formatting error
console.log( err.name ); // FormatError
console.log( err.stack ); // stack

console.log( err instanceof SyntaxError ); // true