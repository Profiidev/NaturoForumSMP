package de.benji.naturoforumsmp.API.MathExpression.Util;

import de.benji.naturoforumsmp.API.MathExpression.Function.Complex;

public class ParserResult {

    /** The value. */
    private Double value;

    /** The complex value. */
    private Complex complexValue;

    /** The complex. */
    private boolean complex;

    /**
     * Gets the value.
     *
     * @return the value
     */
    public Double getValue() {
        return value;
    }

    /**
     * Sets the value.
     *
     * @param value the new value
     */
    public void setValue(final Double value) {
        this.value = value;
        complex = false;
    }

    /**
     * Gets the complex value.
     *
     * @return the complex value
     */
    public Complex getComplexValue() {
        return complexValue;
    }

    /**
     * Sets the complex value.
     *
     * @param complexValue the new complex value
     */
    public void setComplexValue(final Complex complexValue) {
        this.complexValue = complexValue;
        complex = true;
    }

    /**
     * Checks if is complex.
     *
     * @return true, if is complex
     */
    public boolean isComplex() {
        return complex;
    }

}