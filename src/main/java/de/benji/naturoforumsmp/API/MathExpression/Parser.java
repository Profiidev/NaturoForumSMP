package de.benji.naturoforumsmp.API.MathExpression;

import de.benji.naturoforumsmp.API.MathExpression.Exception.CalculatorException;
import de.benji.naturoforumsmp.API.MathExpression.Function.Complex;
import de.benji.naturoforumsmp.API.MathExpression.Function.ComplexFunction;
import de.benji.naturoforumsmp.API.MathExpression.Function.FunctionX;
import de.benji.naturoforumsmp.API.MathExpression.Function.FunctionXs;
import de.benji.naturoforumsmp.API.MathExpression.Util.ParserResult;
import de.benji.naturoforumsmp.API.MathExpression.Util.Point;

import java.util.ArrayList;
import java.util.List;

public class Parser {

    /**
     * Eval.
     * <p>
     * fx = 1 +2+ cos(0.5) --> real functions or fx = 1+j +cos(0.5) --> complex functions
     *
     * @param function the function
     * @return the parser result
     */
    public static ParserResult eval(final String function) throws CalculatorException {

        ParserResult result = new ParserResult();
        FunctionX f_x;

        if ((function != null) && !function.isEmpty()) {
            if ((function.toLowerCase().contains("j")) && !function.toLowerCase().contains("x")) {

                result = eval(function, new Point("x", new Complex(1, 0)));
            } else if (!function.toLowerCase().contains("x")) {
                f_x = new FunctionX(function);
                result.setValue(f_x.getF_xo(0));

            } else {
                throw new CalculatorException("function is not well defined");
            }
        }

        return result;

    }

    /**
     * Eval
     * <p>
     * fx = 1 + 2*x +y ; x = 2, y = 1+j --> real functions or complex functions with real or complex vars
     *
     * @return the parser result: complex or real value
     */
    public static ParserResult eval(final String function, final Point... values) throws CalculatorException {

        final ParserResult result = new ParserResult();
        FunctionX f_x;
        FunctionXs f_xs;
        ComplexFunction complexFunction;

        if ((function != null) && !function.isEmpty()) {

            if (Parser.pointIsComplex(values) || function.toLowerCase().contains("j")) { // Complex

                complexFunction = new ComplexFunction(function);
                final List<Complex> valuesList = pointToComplexValue(values);
                final List<String> varsList = pointToVar(values);
                try {
                    result.setComplexValue(complexFunction.getValue(valuesList, varsList));
                } catch (final CalculatorException e) {
                    e.printStackTrace();
                }

            } else {

                try {

                    if (values.length == 1) {

                        f_x = new FunctionX(function);

                        if ((values[0].getStringValue() != null && !values[0].getStringValue().isEmpty())) {
                            final ParserResult evaluatedValue = Parser.eval(values[0].getStringValue());
                            result.setValue(f_x.getF_xo(evaluatedValue.getValue()));

                        } else {
                            result.setValue(f_x.getF_xo(values[0].getValue()));
                        }

                    } else if (values.length > 1) {
                        f_xs = new FunctionXs(function);
                        final List<Double> valuesList = pointToValue(values);
                        final List<String> varsList = pointToVar(values);
                        result.setValue(f_xs.getValue(valuesList, varsList));
                    }

                } catch (final CalculatorException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * PointToValue.
     *
     * @param values the values
     * @return the list
     */
    private static List<Double> pointToValue(final Point... values) throws CalculatorException {
        final List<Double> result = new ArrayList<>();
        for (Point value : values) {
            if ((value.getStringValue() != null && !value.getStringValue().isEmpty())) {
                final ParserResult evaluatedValue = Parser.eval(value.getStringValue());
                result.add(evaluatedValue.getValue());
            } else {
                result.add(value.getValue());
            }
        }
        return result;
    }

    /**
     * PointToComplexValue.
     *
     * @param values the values
     * @return the list
     */
    private static List<Complex> pointToComplexValue(final Point... values) throws CalculatorException {
        final List<Complex> result = new ArrayList<>();
        for (Point value : values) {
            if (value.isComplex() && (value.getStringValue() == null || value.getStringValue().isEmpty())) {
                result.add(value.getComplexValue());
            } else if ((value.getStringValue() != null && !value.getStringValue().isEmpty())) {
                final ParserResult evaluatedValue = Parser.eval(value.getStringValue());
                if (evaluatedValue.isComplex()) {
                    result.add(evaluatedValue.getComplexValue());
                } else {
                    result.add(new Complex(evaluatedValue.getValue(), 0));
                }
            } else {
                result.add(new Complex(value.getValue(), 0));
            }

        }
        return result;
    }

    /**
     * pointIsComplex.
     *
     * @param values the values
     * @return true, if successful
     */
    private static boolean pointIsComplex(final Point... values) throws CalculatorException {

        boolean result = false;
        for (Point value : values) {
            if (value.isComplex() && (value.getStringValue() == null || value.getStringValue().isEmpty())) {
                result = true;
                break;
            } else {
                if (value.getStringValue() != null && !value.getStringValue().isEmpty()) {
                    final ParserResult evaluatedValue = Parser.eval(value.getStringValue());
                    if (evaluatedValue.isComplex()) {
                        result = true;
                        break;

                    }

                }
            }

        }
        return result;
    }

    /**
     * PointToVar.
     *
     * @param values the values
     * @return the list
     */
    private static List<String> pointToVar(final Point... values) {
        final List<String> result = new ArrayList<>();
        for (Point value : values) {
            result.add(value.getVar());
        }
        return result;
    }
}
