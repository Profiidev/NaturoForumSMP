package de.benji.naturoforumsmp.API.InputAPI;

public enum InputKey {
    Test("test"),
    Status_SetName("Statusname"),
    Status_SetDisplay("Display"),
    Status_SetHead("Headurl"),
    Status_editDisplay("Display"),
    Status_editHead("Headurl"),
    NPC_HeadRot("Headrotation (-180 - 180)"),
    NPC_Name("NPC Name"),
    NPC_SellAmount("Items per Sell (1 - max Stack)"),
    NPC_PayAmount("Items per Pay (1 - max Stack)");

    public final String name;

    InputKey(String s) {
        name = s;
    }
}
