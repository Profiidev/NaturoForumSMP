package de.benji.naturoforumsmp.Msg.Main;

public enum MsgStyles {
    Black("0"),
    Gray("8"),
    Light_Grey("7"),
    White("f"),
    Blue("1"),
    Magenta("5"),
    Pink("d"),
    Red("4"),
    Orange("6"),
    Yellow("e"),
    Lime("a"),
    Green("2"),
    Light_Blue("b"),
    Cyan("3"),
    Bold("l"),
    Italic("o"),
    Bold_Italic("l§o"),
    None("r");

    public final String key;

    MsgStyles(String key) {
        this.key = key;
    }

    public static MsgStyles fromChar(char c) {
        for(MsgStyles style: MsgStyles.values()) {
            if(style.key.equals(String.valueOf(c)))
                return style;
        }
        return None;
    }
}
