package de.benji.naturoforumsmp.API.MathExpression.Function;

import de.benji.naturoforumsmp.API.MathExpression.Exception.CalculatorException;
import de.benji.naturoforumsmp.API.MathExpression.ParserManager;

import java.util.ArrayList;
import java.util.List;

public class FunctionXs {

    /** setup. */
    public boolean degree;

    /**
     * f(x,y,z,...)
     */
    private final String f;

    /**
     * FunctionXs.
     *
     * @param f the f
     */
    public FunctionXs(final String f) {
        this.f = f.trim().replaceAll(" ", "").toLowerCase();
        degree = ParserManager.getInstance().isDeegre();
    }

    /**
     * getValue f(x0,y0,z0...)
     *
     * @param values (sort the values taking into account the variables)
     * @param variables x,y,z etc
     * @return the value
     * @throws CalculatorException the calculator exception
     */
    public double getValue(final List<Double> values, final List<String> variables) throws CalculatorException {
        final List<String> vars = new ArrayList<>();
        for (final String string : variables) {
            vars.add(string.trim().replaceAll(" ", "").toLowerCase());
        }
        return eval(f, values, vars);
    }

    /**
     * eval.
     *
     * @param f the f
     * @param values the values
     * @param variables the variables
     * @return the double
     * @throws CalculatorException the calculator exception
     */
    private double eval(final String f, final List<Double> values, final List<String> variables)
            throws CalculatorException {
        double value = 0;
        StringBuilder number = new StringBuilder();
        String function = "";
        boolean hasNumber = false;
        boolean hasFunction = false;

        for (int i = 0; i < f.length(); i++) {
            final char character = f.charAt(i);

            if (character >= '0' && character <= '9') {

                hasNumber = true;
                number.append(character);
                if (i == (f.length() - 1)) {
                    value = Double.parseDouble(number.toString());
                    number = new StringBuilder();
                    hasNumber = false;
                }

            } else if (character == '+') {

                if (hasNumber) {
                    final double numb = Double.parseDouble(number.toString());
                    final String new_f = f.substring(i + 1);
                    value = numb + eval(new_f, values, variables);
                    i += new_f.length();
                    hasNumber = false;
                    number = new StringBuilder();
                } else if (hasFunction) {
                    final String new_f = f.substring(i + 1);
                    value = eval(function, values, variables) + eval(new_f, values, variables);
                    i += new_f.length();
                    hasFunction = false;
                    function = "";

                } else {
                    final String new_f = f.substring(i + 1);
                    value = value + eval(new_f, values, variables);
                    i += new_f.length();
                }

            } else if (character == '*') {

                if (hasNumber) {
                    final double numb = Double.parseDouble(number.toString());
                    final String new_f = nextFunction(f.substring(i + 1));
                    value = numb * eval(new_f, values, variables);
                    i += new_f.length();
                    hasNumber = false;
                    number = new StringBuilder();
                } else if (hasFunction) {
                    final String new_f = nextFunction(f.substring(i + 1));
                    value = eval(function, values, variables) * eval(new_f, values, variables);
                    i += new_f.length();
                    hasFunction = false;
                    function = "";
                } else {
                    final String new_f = nextFunction(f.substring(i + 1));
                    value = value * eval(new_f, values, variables);
                    i += new_f.length();
                }

            } else if (character == '-') {

                if (hasNumber) {
                    final double numb = Double.parseDouble(number.toString());
                    final String new_f = nextMinusFunction(f.substring(i + 1));
                    value = numb - eval(new_f, values, variables);
                    i += new_f.length();
                    hasNumber = false;
                    number = new StringBuilder();
                } else if (hasFunction) {
                    final String new_f = nextMinusFunction(f.substring(i + 1));
                    value = eval(function, values, variables) - eval(new_f, values, variables);
                    i += new_f.length();
                    hasFunction = false;
                    function = "";

                } else {
                    final String new_f = nextMinusFunction(f.substring(i + 1));
                    value = value - eval(new_f, values, variables);
                    i += new_f.length();
                }

            } else if (character == '/') {

                if (hasNumber) {
                    final double numb = Double.parseDouble(number.toString());
                    final String new_f = nextFunction(f.substring(i + 1));
                    value = numb / eval(new_f, values, variables);
                    i += new_f.length();
                    hasNumber = false;
                    number = new StringBuilder();
                } else if (hasFunction) {
                    final String new_f = nextFunction(f.substring(i + 1));
                    value = eval(function, values, variables) / eval(new_f, values, variables);
                    i += new_f.length();
                    hasFunction = false;
                    function = "";

                } else {
                    final String new_f = nextFunction(f.substring(i + 1));
                    value = value / eval(new_f, values, variables);
                    i += new_f.length();
                }

            } else if (character == '^') {

                if (hasNumber) {
                    final double numb = Double.parseDouble(number.toString());
                    final String new_f = nextFunction(f.substring(i + 1));
                    value = StrictMath.pow(numb, eval(new_f, values, variables));
                    i += new_f.length();
                    hasNumber = false;
                    number = new StringBuilder();
                } else if (hasFunction) {
                    final String new_f = nextFunction(f.substring(i + 1));
                    value = StrictMath.pow(eval(function, values, variables), eval(new_f, values, variables));
                    i += new_f.length();
                    hasFunction = false;
                    function = "";

                } else {
                    final String new_f = nextFunction(f.substring(i + 1));
                    value = StrictMath.pow(value, eval(new_f, values, variables));
                    i += new_f.length();
                }

            } else if (character == '.') {
                if (i == (f.length() - 1)) {
                    throw new CalculatorException("The function is not well-formed");
                }
                if (hasNumber && (number.length() > 0)) {
                    number.append(character);
                }

            } else if (character == '(') {
                if (i == (f.length() - 1)) {
                    throw new CalculatorException("The function is not well-formed");
                }

                final String new_f = f.substring(i + 1, nextBracket(f));
                if (hasFunction) {
                    switch (function) {
                        case Constants.SIN:
                            if (degree) {
                                value = StrictMath.sin(StrictMath.toRadians(eval(new_f, values, variables)));
                            } else {
                                value = StrictMath.sin(eval(new_f, values, variables));
                            }

                            break;
                        case Constants.COS:
                            if (degree) {
                                value = StrictMath.cos(StrictMath.toRadians(eval(new_f, values, variables)));
                            } else {
                                value = StrictMath.cos(eval(new_f, values, variables));
                            }
                            break;
                        case Constants.TAN:
                            if (degree) {
                                value = StrictMath.tan(StrictMath.toRadians(eval(new_f, values, variables)));
                            } else {
                                value = StrictMath.tan(eval(new_f, values, variables));
                            }

                            break;
                        case Constants.SINH:
                            value = StrictMath.sinh(eval(new_f, values, variables));

                            break;
                        case Constants.COSH:
                            value = StrictMath.cosh(eval(new_f, values, variables));

                            break;
                        case Constants.TANH:
                            value = StrictMath.tanh(eval(new_f, values, variables));

                            break;
                        case Constants.ASIN:
                            if (degree) {
                                value = StrictMath.asin(eval(new_f, values, variables)) * (180 / StrictMath.PI);
                            } else {
                                value = StrictMath.asin(eval(new_f, values, variables));
                            }
                            break;
                        case Constants.ACOS:
                            if (degree) {
                                value = StrictMath.acos(eval(new_f, values, variables)) * (180 / StrictMath.PI);
                            } else {
                                value = StrictMath.acos(eval(new_f, values, variables));
                            }
                            break;
                        case Constants.ATAN:
                            if (degree) {
                                value = StrictMath.atan(eval(new_f, values, variables)) * (180 / StrictMath.PI);
                            } else {
                                value = StrictMath.atan(eval(new_f, values, variables));
                            }
                            break;
                        case Constants.LN:
                            value = StrictMath.log(eval(new_f, values, variables));
                            break;
                        case Constants.LOG:
                            value = StrictMath.log10(eval(new_f, values, variables));
                            break;
                        case Constants.SQRT:
                            value = StrictMath.sqrt(eval(new_f, values, variables));
                            break;
                        case Constants.CBRT:
                            value = StrictMath.cbrt(eval(new_f, values, variables));
                            break;
                        default:
                            throw new CalculatorException("The function is not well-formed");
                    }

                    hasFunction = false;
                    function = "";

                } else {
                    value = eval(new_f, values, variables);
                }
                i += new_f.length() + 1;

            } else if (character == ')') {
                throw new CalculatorException(" '(' is not finished ");

            } else if (isValidCharacter(character)) {
                function = function + character;
                hasFunction = true;

                if (i == (f.length() - 1)) {

                    if (Constants.E.equals(function)) {
                        value = StrictMath.E;
                    } else if (Constants.PI.equals(function)) {
                        value = StrictMath.PI;
                    } else {
                        if (function.length() == 1) {
                            final int n = variables.indexOf(function);
                            if (n >= 0) {
                                value = values.get(n);
                            } else {
                                throw new CalculatorException("function is not well defined");
                            }

                        } else {
                            throw new CalculatorException("function is not well defined");
                        }
                    }
                }
            } else {
                throw new CalculatorException("Invalid character:" + character);
            }
        }
        return value;
    }

    /**
     * nextFunction.
     *
     * @param f the f
     * @return the string
     * @throws CalculatorException the calculator exception
     */
    private String nextFunction(final String f) throws CalculatorException {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < f.length(); i++) {
            final char character = f.charAt(i);

            if (isValidNumericAndCharacter(character)) {
                result.append(character);
            } else if (character == '+' || character == '*' || character == '-' || character == '/') {
                i = f.length();
            } else if (character == '.' || character == '^') {
                result.append(character);
            } else if (character == '(') {
                final String new_f = f.substring(i, nextBracket(f) + 1);
                result.append(new_f);
                i = (i + new_f.length()) - 1;
            } else if (character == ')') {
                throw new CalculatorException(" '(' is not finished ");
            } else if (character == ' ') {
                result.append(character);
            } else {
                throw new CalculatorException("Invalid character:" + character);
            }
        }
        return result.toString();
    }

    /**
     * nextMinusFunction.
     *
     * @param f the f
     * @return the string
     * @throws CalculatorException the calculator exception
     */
    private String nextMinusFunction(final String f) throws CalculatorException {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < f.length(); i++) {
            final char character = f.charAt(i);

            if (isValidNumericAndCharacter(character)) {
                result.append(character);
            } else if (character == '+' || character == '-') {
                i = f.length();
            } else if (character == '*' || character == '/' || character == '.' || character == '^') {
                result.append(character);
            } else if (character == '(') {
                final String new_f = f.substring(i, nextBracket(f) + 1);
                result.append(new_f);
                i = (i + new_f.length()) - 1;
            } else if (character == ')') {
                throw new CalculatorException(" '(' is not finished ");
            } else if (character == ' ') {
                result.append(character);
            } else {
                throw new CalculatorException("Invalid character:" + character);
            }
        }
        return result.toString();

    }

    /**
     * isValidCharacter.
     *
     * @param character the character
     * @return true, if is valid character
     */
    private boolean isValidCharacter(final char character) {
        return character >= 'a' && character <= 'z';
    }

    /**
     * isValidNumericAndCharacter.
     *
     * @param character the character
     * @return true, if is valid numeric and character
     */
    private boolean isValidNumericAndCharacter(final char character) {
        return (character >= 'a' && character <= 'z') || (character >= '0' && character <= '9');
    }

    /**
     * nextBracket.
     *
     * @param f the f
     * @return the int
     * @throws CalculatorException the calculator exception
     */
    private int nextBracket(final String f) throws CalculatorException {
        int result = 0;
        int count = 0;
        for (int i = 0; i < f.length(); i++) {
            final char character = f.charAt(i);
            if (character == '(') {
                result = i;
                count++;
            } else if (character == ')') {
                result = i;
                count--;
                if (count == 0) {
                    return i;
                }
            } else {
                result = i;
            }
        }

        if (count != 0) {
            throw new CalculatorException("( is not finished");
        }
        return result;
    }

}