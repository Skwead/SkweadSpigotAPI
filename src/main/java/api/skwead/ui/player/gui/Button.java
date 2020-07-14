package api.skwead.ui.player.gui;

import api.skwead.ui.Action;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;

@SuppressWarnings("unused")
public class Button{
    private ItemStack btn;
    private Map<ClickType, Action<InventoryClickEvent>> actions;

    /**
     * Creates a GUI button based on the {@link ItemStack} provided
     * @param btn the {@link ItemStack} that will show up in the UI.
     */
    public Button(ItemStack btn) {
        this.btn = btn;
    }

    /**
     * Registers an action bind to a click type.
     * It will ovewrite previous binds to that click type.
     * @param clickType The click type that activates the action
     * @param onClickAction The action to be executed
     */
    public void addAction(ClickType clickType, Action<InventoryClickEvent> onClickAction){
        actions.put(clickType, onClickAction);
    }

    public ItemStack getBtn() {
        return btn;
    }

    public Map<ClickType, Action<InventoryClickEvent>> getActions() {
        return actions;
    }

    public void setBtn(ItemStack btn) {
        this.btn = btn;
    }

    public void setActions(Map<ClickType, Action<InventoryClickEvent>> actions) {
        this.actions = actions;
    }
}
