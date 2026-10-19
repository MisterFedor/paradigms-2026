package expression.exceptions;

import expression.*;

import java.util.List;

public class ExpressionParser implements ListParser {


    @Override
    public ListExpression parse(String expression, List<String> variables) {
//        System.err.println(expression);
        return new mainParser(new StringSource(expression), variables).parseExpression();
    }

    private static class mainParser extends BaseParser {

        protected mainParser(CharSource source, List<String> variables) {
            super(source);
        }

        private GeneralExpr parseExpression() {
            skipWhitespace();
            final GeneralExpr result = parseSubAdd();
            skipWhitespace();
            if (!eof()) {
                if (test(')')) {
                    throw new BracketsException("Missing '('");
                } else if (test('[') || test(']')) {
                    throw new BracketsException("Wrong brackets. You must use only '(' and ')'");
                }
                throw new IllegalInputException("Illegal input in: " + getPos() + ": " + "'" + ch + "'");
            }
            return result;
        }

        private GeneralExpr parseSubAdd() {
            GeneralExpr operand = parseMulDiv();
            while (true) {
                skipWhitespace();
                if (take('-')) {
                    operand = new CheckedSubtract(operand, parseMulDiv());
                } else if (take('+')) {
                    operand = new CheckedAdd(operand, parseMulDiv());
                } else {
                    return operand;
                }
            }
        }

        private GeneralExpr parseMulDiv() {
            GeneralExpr operand = parseElements();
            while (true) {
                skipWhitespace();
                if (take('*')) {
                    operand = new CheckedMultiply(operand, parseElements());
                } else if (take('/')) {
                    operand = new CheckedDivide(operand, parseElements());
                } else {
                    return operand;
                }
            }
        }

        private GeneralExpr parseElements() {
            skipWhitespace();
            if (test(')') || test(END) || test('+') || test('*')
                    || test('/')) {
                throw new MissingArgumentException("Missing argument, check your input in: " + getPos());
            }
            if (test('[') || test(']')) {
                throw new BracketsException("Wrong brackets. You must use only '(' and ')'");
            }

            /*
            cbrt
             */
            if (take('∛')) { // :NOTE: ∛(1 + 2)
                return new CheckedCbrt(parseElements());
            }
            /*
            abs
             */
            if (take('‖')) {
                GeneralExpr result = parseSubAdd();
                skipWhitespace();
                expect('‖');
                return new CheckedAbs(result);
            }
            /*
            -const, unary minus
             */
            if (take('-')) {
                if (between('0', '9')) {
                    String number = parseNumber();
                    if (number.equals("2147483648")) {
                        return new Const(Integer.MIN_VALUE);
                    } else {
                        return new Const(parseIntSafe("-" + number, "Constant out of int range"));
                    }
                }
                return new CheckedNegate(parseElements());
            }
            /*
            ( )
             */
            if (take('(')) {
                GeneralExpr result = parseSubAdd();
                skipWhitespace();
                if (!take(')')) {
                    if (test(END)) {
                        throw new BracketsException("Missing ')'");
                    }
                    if (test(']')) {
                        throw new BracketsException("Wrong brackets. You must use only '(' and ')'");
                    }
                    throw new IllegalInputException("Illegal input in: " + getPos() + ": " + "'" + ch + "'");
                }
                return result;
            }
            /*
            variables with $
             */
            if (take('$')) {
                if (!between('0', '9')) {
                    throw new MissingArgumentException("Missing argument, check your input in: " + getPos());
                }
                String variable = parseNumber();
                return new Variable(parseIntSafe(variable, "Variable out of int range"));
            }
            /*
            const
             */
            if (between('0', '9')) {
                String number = parseNumber();
                return new Const(parseIntSafe(number, "Constant out of int range"));
            }
            throw new IllegalInputException("Illegal input in: " + getPos() + ": " + "'" + ch + "'");
        }

        private String parseNumber() {
            StringBuilder number = new StringBuilder();
            while (between('0', '9')) {
                number.append(take());
            }
            return number.toString();
        }

        private void skipWhitespace() {
            while (Character.isWhitespace(ch)) {
                take();
            }
        }

        private int parseIntSafe(final String str, final String message) {
            try {
                return Integer.parseInt(str);
            } catch (NumberFormatException e) {
                throw new ConstantOverflowException(message);
            }
        }
    }
}


