package addon.brainsynder.tokenmanager;

import com.google.common.collect.Lists;
import me.realized.tokenmanager.api.TokenManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import simplepets.brainsynder.addon.presets.EconomyAddon;
import simplepets.brainsynder.api.Namespace;

import java.util.List;
import java.util.OptionalLong;
import java.util.UUID;

@Namespace(namespace = "TokenManager")
public class TokenManagerAddon extends EconomyAddon {
    private TokenManager manager = null;

    @Override
    public void init() {
        super.init();

        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("TokenManager");
        if ((plugin == null) || (!plugin.isEnabled())) return;
        manager = (TokenManager) Bukkit.getServer().getPluginManager().getPlugin("TokenManager");
    }

    @Override
    public boolean shouldEnable() {
        Plugin plugin = Bukkit.getPluginManager().getPlugin("TokenManager");
        if ((plugin != null) && plugin.isEnabled()) return true;
        System.out.println("[SimplePets TokenManagerAddon] You seem to be missing the TokenManager plugin...");
        System.out.println("[SimplePets TokenManagerAddon] Download it here: https://www.spigotmc.org/resources/8610/");
        return false;
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

    @Override
    public double getVersion() {
        return 0.1;
    }

    @Override
    public String getAuthor() {
        return "brainsynder";
    }

    @Override
    public List<String> getDescription() {
        return Lists.newArrayList(
                "&7This addon links into the TokenManager Plugin",
                "&7To make it possible to buy pets with in-game money"
        );
    }
}
