package de.benji.naturoforumsmp.API.MathExpression;

public class ParserManager {

    /** The deegre. */
    private final boolean deegre = false;

    // ..... Other configuration values //

    /** The instance. */
    private static ParserManager instance = null;

    /**
     * Instantiates a new parser manager.
     */
    protected ParserManager() {

    }

    /**
     * getInstance.
     *
     * @return single instance of ParserManager
     */
    public static ParserManager getInstance() {
        if (instance == null) {
            instance = new ParserManager();
        }
        return instance;
    }

    /**
     * isDeegre.
     *
     * @return true, if is deegre
     */
    public boolean isDeegre() {
        return deegre;
    }

}