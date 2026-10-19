"use strict";

const cnst = (a) => (...args) => a
const variable = (letter) => (...args) => {
    switch (letter) {
        case "x":
            return args[0];
        case "y":
            return args[1];
        case "z":
            return args[2];
    }
}
const binaryOperation = (operation) => (a, b) => (...args) => operation(a(...args), b(...args));
const unaryOperation = (operation) => (a) => (...args) => operation(a(...args));
const add = binaryOperation((a, b) => a + b);
const subtract = binaryOperation((a, b) => a - b);
const multiply = binaryOperation((a, b) => a * b);
const divide = binaryOperation((a, b) => a / b);
const negate = unaryOperation((a) => -a);
const sin = unaryOperation(Math.sin);
const cos = unaryOperation(Math.cos);

const one = cnst(1);
const two = cnst(2);
const three = cnst(3);




// let expr = subtract(
//     multiply(
//         cnst(2),
//         variable("x")
//     ),
//     cnst(3)
// );

// function println(x) {
//     console.log(x);
// }
// println(expr(5));