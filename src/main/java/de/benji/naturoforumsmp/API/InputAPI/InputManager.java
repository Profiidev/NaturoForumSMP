package de.benji.naturoforumsmp.API.InputAPI;

import de.benji.naturoforumsmp.API.GlobalManager;
import net.wesjd.anvilgui.AnvilGUI;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

public class InputManager {
    public InputManager() {}

    public void createInput(Player p, InputInfo inputInfo) {
        createInput(p, inputInfo, (slot, stateSnapshot) -> {
            if(slot != AnvilGUI.Slot.OUTPUT || stateSnapshot.getText().equals("")) {
                return Collections.emptyList();
            }

            return Arrays.asList(AnvilGUI.ResponseAction.close(), AnvilGUI.ResponseAction.run(() -> inputInfo.inputCallback.accept(stateSnapshot.getText(), stateSnapshot.getPlayer())));
        });
    }

    public void createIntInput(Player p, InputInfo inputInfo) {
        createInput(p, inputInfo, (slot, stateSnapshot) -> {
            if(slot != AnvilGUI.Slot.OUTPUT || stateSnapshot.getText().equals("")) {
                return Collections.emptyList();
            }

            try{
                int i = Integer.parseInt(stateSnapshot.getText());
            } catch (NumberFormatException e) {
                return  Collections.emptyList();
            }

            return Arrays.asList(AnvilGUI.ResponseAction.close(), AnvilGUI.ResponseAction.run(() -> inputInfo.inputCallback.accept(stateSnapshot.getText(), stateSnapshot.getPlayer())));
        });
    }

    public void createIntInput(Player p, InputInfo inputInfo, int min, int max) {
        createInput(p, inputInfo, (slot, stateSnapshot) -> {
            if(slot != AnvilGUI.Slot.OUTPUT || stateSnapshot.getText().equals("")) {
                return Collections.emptyList();
            }

            try{
                int i = Integer.parseInt(stateSnapshot.getText());
                if(i < min || i > max)
                    return Collections.emptyList();
            } catch (NumberFormatException e) {
                return  Collections.emptyList();
            }

            return Arrays.asList(AnvilGUI.ResponseAction.close(), AnvilGUI.ResponseAction.run(() -> inputInfo.inputCallback.accept(stateSnapshot.getText(), stateSnapshot.getPlayer())));
        });
    }

    private void createInput(Player p, InputInfo inputInfo, BiFunction<Integer, AnvilGUI.StateSnapshot, List<AnvilGUI.ResponseAction>> onClick) {
        new AnvilGUI.Builder()
                .onClick(onClick)
                .preventClose()
                .text("Input")
                .title("§6Set " + inputInfo.inputKey.name)
                .plugin(GlobalManager.getInstance())
                .open(p);
    }

    public static class InputInfo {
        public InputKey inputKey;
        public BiConsumer<String, Player> inputCallback;

        public InputInfo(InputKey inputKey, BiConsumer<String, Player> inputCallback) {
            this.inputKey = inputKey;
            this.inputCallback = inputCallback;
        }
    }
}
