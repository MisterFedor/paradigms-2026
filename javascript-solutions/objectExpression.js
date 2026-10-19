"use strict";

function NaryOperation(operands) {
    this.operands = operands;
};
NaryOperation.prototype.evaluate = function (...args) {
    let values = this.operands.map(operand => operand.evaluate(...args));
    return this.operationFunc(...values);
};
NaryOperation.prototype.toString = function () {
    return this.operands.map(operand => operand.toString()).join(" ") + " " + this.operation;
};
NaryOperation.prototype.prefix = function () {
    return "(" + this.operation + " " + this.operands.map(operand => operand.prefix()).join(" ") + ")";
};

const makeOperation = function (operationFunc, operation) {
    const Operation = function (...operands) {
        NaryOperation.call(this, operands);
    }
    Operation.prototype = Object.create(NaryOperation.prototype);
    Operation.prototype.constructor = Operation;
    Operation.prototype.operationFunc = operationFunc;
    Operation.prototype.operation = operation;

    return Operation;
}

const Add = makeOperation((first, second) => first + second, "+");
const Subtract = makeOperation((first, second) => first - second, "-");
const Multiply = makeOperation((first, second) => first * second, "*");
const Divide = makeOperation((first, second) => first / second, "/");
const Negate = makeOperation((first) => -first, "negate");
const Wrap = makeOperation((x, min, max) => x - Math.floor((x - min) / (max - min)) * (max - min), "wrap");

const ArcTan12 = function (...operands) {
    NaryOperation.call(this, operands);
};
ArcTan12.prototype = Object.create(NaryOperation.prototype);
ArcTan12.prototype.constructor = ArcTan12;
ArcTan12.prototype.operationFunc = function (...args) {
    if (args.length === 1) {
        return Math.atan(args[0]);
    } else {
        return Math.atan2(args[0], args[1]);
    }
};
ArcTan12.prototype.operation = "atan12";


const makeError = function (errorName) {
    const MyError = function (message) {
        Error.call(this);
        this.name = errorName;
        this.message = message;
    }
    MyError.prototype = Object.create(Error.prototype);
    MyError.prototype.constructor = MyError;

    return MyError;
}

class Variable {
    constructor(letter) {
        this.letter = letter;
    }
    evaluate(...args) {
        switch (this.letter) {
            case "x":
                return args[0];
            case "y":
                return args[1];
            case "z":
                return args[2];
        }
    }
    toString() {
        return this.letter.toString();
    }
    prefix() {
        return this.letter.toString();
    }
};

class Const {
    constructor(operand) {
        this.operand = operand;
    }
    evaluate(...args) {
        return this.operand;
    }
    toString() {
        return this.operand.toString();
    }
    prefix() {
        return this.operand.toString();
    }
};

const operations = {
    '+': [Add, [2]],
    '-': [Subtract, [2]],
    '*': [Multiply, [2]],
    '/': [Divide, [2]],
    "negate": [Negate, [1]],
    "wrap": [Wrap, [3]],
    "atan12": [ArcTan12, [1, 2]],
};

const brackets = {
    "(": ")",
    "[": "]",
    "{": "}",
    "<": ">",
    "«": "»",
};

const closeBrackets = Object.values(brackets);

function parse(expression) {
    let expr = expression.trim().split(/\s+/);
    let stack = [];

    for (let element of expr) {
        switch (element) {
            case "x":
            case "y":
            case "z": {
                stack.push(new Variable(element));
                break;
            }
            default: {
                if (element in operations) {
                    let [Operation, arity] = operations[element];
                    let operands = stack.splice(-arity);
                    stack.push(new Operation(...operands));
                } else {
                    stack.push(new Const(parseInt(element, 10)));
                }
            }
        }
    }
    let result = stack.pop()
    return result;
};


const UnknownOperationError = makeError("UnknownOperationError");
const BracketsError = makeError("BracketsError");
const UnexpectedExpressionError = makeError("UnexpectedExpressionError");

function isSpace(ch) {
    return /\s/.test(ch);
}
function parseInputExpr(inputExpr) {
    let parsed = [];
    let element = "";

    for (let i = 0; i < inputExpr.length; i++) {
        let symbol = inputExpr[i];

        if (isSpace(symbol)) {
            if (element.length > 0) {
                parsed.push(element);
                element = "";
            }
            continue
        }

        if (symbol in brackets || closeBrackets.includes(symbol)) {
            if (element.length > 0) {
                parsed.push(element);
                element = "";
            }
            parsed.push(symbol);
            continue;
        }

        element += symbol;
    }
    if (element.length > 0) {
        parsed.push(element);
    }

    return parsed;
}

function parsePrefix(expression) {
    if (expression.trim().length === 0) {
        throw new UnexpectedExpressionError("Input expression is empty.")
    };

    let expr = parseInputExpr(expression);
    let position = 0;

    function parseExpr() {
        let element = expr[position++];
        if (element === "x" || element === "y" || element === "z") {
            return new Variable(element);
        }
        if (element in brackets) {
            let closeBracket = brackets[element];
            let operation = expr[position++];
            if (!(operation in operations)) {
                throw new UnknownOperationError("Unknown operation " + operation + " in pos: " + position);
            }
            let [Operation, arity] = operations[operation];
            let operands = [];
            while (expr[position] !== closeBracket) {
                operands.push(parseExpr());
            }
            if (expr[position] !== closeBracket) {
                throw new BracketsError("Mismatch correct close bracket.");
            }
            if (!arity.includes(operands.length)) {
                throw new UnexpectedExpressionError("Wrong amount of arguments for operation " + operation);
            }
            position++;
            return new Operation(...operands);
        }
        if (closeBrackets.includes(element)) {
            throw new BracketsError("Extra close bracket.");
        }
        if (!isNaN(element)) {
            return new Const(parseInt(element, 10));
        }
        throw new UnexpectedExpressionError("Unexpected element of expression in pos: " + position);
    }


    let result = parseExpr();
    if (position !== expr.length) {
        throw new UnexpectedExpressionError("General problems with expression. Extra elements.");
    }
    return result;
}
