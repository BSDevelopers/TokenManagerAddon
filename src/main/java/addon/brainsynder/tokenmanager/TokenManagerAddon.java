package addon.brainsynder.tokenmanager;

import com.google.common.collect.Lists;
import me.realized.tokenmanager.api.TokenManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import simplepets.brainsynder.addon.presets.EconomyModule;
import simplepets.brainsynder.api.Namespace;
import simplepets.brainsynder.api.plugin.SimplePets;
import simplepets.brainsynder.debug.DebugBuilder;

import java.util.List;
import java.util.OptionalLong;
import java.util.UUID;

@Namespace(namespace = "TokenManager")
public class TokenManagerAddon extends EconomyModule {
    private TokenManager manager = null;

    @Override
    public void init() {
        super.init();

        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("TokenManager");
        if ((plugin == null) || (!plugin.isEnabled())) return;
        manager = (TokenManager) Bukkit.getServer().getPluginManager().getPlugin("TokenManager");
    }

    @Override
    public void cleanup() {
        super.cleanup();

        manager = null;
    }

    @Override
    public int getDefaultPrice() {
        return 200;
    }

    @Override
    public double getBalance(UUID uuid) {
        OptionalLong optional = manager.getTokens(Bukkit.getPlayer(uuid));
        if (optional.isPresent()) return optional.getAsLong();
        return 0;
    }

    @Override
    public void withdraw(UUID uuid, double amount) {
        manager.removeTokens(Bukkit.getPlayer(uuid), (long) amount);
    }
}
