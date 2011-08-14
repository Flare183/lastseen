package flare183.lastseen;

import java.io.File;
//import java.io.IOException;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.util.config.Configuration;

import java.util.Calendar;

public class LSPlayerListener extends PlayerListener implements CommandExecutor {

	// private final LastSeen myLs;
	private final Configuration myConfig;
	private final LastSeen ls;

	public LSPlayerListener(LastSeen myLs) {

		File configFile = new File("./plugins/LastSeen/times.yml");

		myConfig = new Configuration(configFile);
		myConfig.load();

		ls = myLs;

	}

	public void onPlayerQuit(PlayerQuitEvent event) {

		Calendar today = Calendar.getInstance();
		long msTime = today.getTimeInMillis();

		String time = "" + msTime;

		myConfig.setProperty(event.getPlayer().getName().toLowerCase()
				+ ".logout", time);
		myConfig.save();

	}

	@Override
	public boolean onCommand(CommandSender cs, Command c, String cmdAlias,
			String[] args) {

		String cmd, playerName;
		cmd = c.getName();
		if (cmd.equalsIgnoreCase("lastseen") && args.length > 0) {
			playerName = args[0];

			if (ls.getServer().getPlayer(playerName) == null) {
				long lastHere = 0;

				try {
					lastHere = Long.parseLong(myConfig.getString(playerName
							.toLowerCase() + ".logout"));
				} catch (Exception e) {
					lastHere = 0;
				}

				Calendar today = Calendar.getInstance();
				long cTime = today.getTimeInMillis();

				long ago = cTime - lastHere;

				if (lastHere != 0) {

					long days = ago / 86400000;
					ago = ago % 86400000;
					long hours = ago / 3600000;
					ago = ago % 3600000;
					long minutes = ago / 60000;

					if (days >= 1) {

						cs.sendMessage(playerName + " was last seen on dreamcraft " + days
								+ " days and " + hours + " hours ago.");

					} else if (hours >= 1) {

						cs.sendMessage(playerName + " was last seen on dreamcraft" + hours
								+ " hours and " + minutes + " minutes ago.");

					} else {
						cs.sendMessage(playerName + " was last seen on dreamcraft " + minutes
								+ " minutes ago.");
					}

					return true;

				} else {

					cs.sendMessage(playerName + " is not listed in my records.");
					return true;

				}

			} else {

				cs.sendMessage(playerName + " is online.");
				return true;
				
			}

		}

		return false;
	}

}
