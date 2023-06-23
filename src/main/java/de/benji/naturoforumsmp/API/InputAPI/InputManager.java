package de.benji.naturoforumsmp.API.InputAPI;

import de.benji.naturoforumsmp.API.GlobalManager;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;

public class InputManager {
    public InputManager() {}

    public void createInput(Player p, InputInfo inputInfo) {
        new AnvilGUI.Builder()
                .onClick((slot, stateSnapshot) -> {
                    if(slot != AnvilGUI.Slot.OUTPUT || stateSnapshot.getText().equals("")) {
                        return Collections.emptyList();
                    }

                    return Arrays.asList(AnvilGUI.ResponseAction.close(), AnvilGUI.ResponseAction.run(() -> inputInfo.inputCallback.input(stateSnapshot.getText(), stateSnapshot.getPlayer())));
                })
                .preventClose()
                .text(inputInfo.inputKey.name)
                .title("§6Set " + inputInfo.inputKey.name)
                .plugin(GlobalManager.getInstance())
                .open(p);
    }

    public static class InputInfo {
        public InputKey inputKey;
        public InputCallback inputCallback;

        public InputInfo(InputKey inputKey, InputCallback inputCallback) {
            this.inputKey = inputKey;
            this.inputCallback = inputCallback;
        }
    }

    @FunctionalInterface
    public interface InputCallback {
        void input(String s, Player p);
    }
}
