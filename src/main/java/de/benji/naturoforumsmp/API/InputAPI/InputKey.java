package de.benji.naturoforumsmp.API.InputAPI;

public enum InputKey {
    Test("test"),
    Status_SetName("Statusname"),
    Status_SetDisplay("Display"),
    Status_SetHead("Headurl"),
    Status_editDisplay("Display"),
    Status_editHead("Headurl");

    public final String name;

    InputKey(String s) {
        name = s;
    }
}
