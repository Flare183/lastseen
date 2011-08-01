package flare183.lastseen;

import org.bukkit.event.Event;
import org.bukkit.plugin.java.JavaPlugin;

public class LastSeen extends JavaPlugin {

	public final LSPlayerListener pl = new LSPlayerListener(this);

	public void onDisable() {

		System.out.println("LastSeen: Disabled");

	}

	public void onEnable() {

		getCommand("lastseen").setExecutor(pl);
		this.getServer()
				.getPluginManager()
				.registerEvent(Event.Type.PLAYER_QUIT, pl,
						Event.Priority.Monitor, this);
		System.out.println("LastSeen: Enabled");

	}

}
