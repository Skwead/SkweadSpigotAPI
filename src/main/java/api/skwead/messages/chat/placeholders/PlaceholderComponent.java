package api.skwead.messages.chat.placeholders;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.function.BiFunction;

public class PlaceholderComponent {

    private final String name;
    private final BiFunction<JavaPlugin, Player, String> getValue;

    public PlaceholderComponent(String name, BiFunction<JavaPlugin, Player, String> getValue) {
        this.name = name;
        this.getValue = getValue;
    }

    public String getValue(JavaPlugin plugin, Player player){
        return getValue.apply(plugin, player);
    }

    public String getName() {
        return this.name;
    }
}
