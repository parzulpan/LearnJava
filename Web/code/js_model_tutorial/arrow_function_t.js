'use strict'

let sum = (a, b) => a + b;

alert(sum(1, 2))

let sum2 = (a, b) => {
    let c = a + b;
    return c ** 2;
};

alert(sum2(3, 4));


let ask = (question, yes, no) => {
    if (confirm(question)) {
        yes();
    }
    else {
        no();
    }
};


ask("Du you know?", () => alert("yes!"), () => alert("no!"));