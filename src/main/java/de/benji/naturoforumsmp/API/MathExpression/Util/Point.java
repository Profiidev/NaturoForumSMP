package de.benji.naturoforumsmp.API.MathExpression.Util;

import de.benji.naturoforumsmp.API.MathExpression.Function.Complex;

public class Point {

    /**
     * The var.
     */
    private String var;

    /**
     * The value.
     */
    private Double value;

    /**
     * The complex value.
     */
    private Complex complexValue;

    /**
     * The string value.
     */
    private String stringValue;

    /**
     * The complex.
     */
    private boolean complex;

    /**
     * Instantiates a new point.
     */
    public Point() {

    }

    /**
     * Instantiates a new point.
     *
     * @param var   the var
     * @param value the value
     */
    public Point(final String var, final Double value) {
        this.var = var;
        this.value = value;
        complex = false;
    }

    /**
     * Instantiates a new point.
     *
     * @param var   the var
     * @param value the value
     */
    public Point(final String var, final Complex value) {
        this.var = var;
        complexValue = value;
        complex = true;
    }

    /**
     * Instantiates a new point.
     *
     * @param var   the var
     * @param value the value
     */
    public Point(final String var, final String value) {
        this.var = var;
        stringValue = value;

    }

    /**
     * getter var.
     *
     * @return the var
     */
    public String getVar() {
        return var;
    }

    /**
     * getter Value.
     *
     * @return the value
     */
    public Double getValue() {
        return value;
    }

    /**
     * getter complexValue.
     *
     * @return the complex value
     */
    public Complex getComplexValue() {
        return complexValue;
    }

    /**
     * isComplex.
     *
     * @return true, if is complex
     */
    public boolean isComplex() {
        return complex;
    }

    /**
     * Gets the string value.
     *
     * @return the string value
     */
    public String getStringValue() {
        return stringValue;
    }

}