package me.Ben.BungeeComands;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class MotdCommand extends Command{
	
	public MotdCommand() {
		super("setmotd");
	}

	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length == 0){
			sender.sendMessage(new TextComponent(ChatColor.RED + "Incorect syntax: /setmotd <message>"));
			return;
		}
		String motd = "";
		for(int i = 0; i < args.length; i++) motd += (i != 0 ? " " : "") + args[i];
		try {		
			Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File("config.yml"));
			ArrayList<HashMap<String,Object>> al = (ArrayList<HashMap<String, Object>>) config.get("listeners");
			al.get(0).put("motd", motd);
			config.set("listeners", al);
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File("config.yml"));
			Events.newmotd = motd;
			sender.sendMessage(new TextComponent(ChatColor.GREEN + "Global motd set to '" + motd.replaceAll("&", "§") + ChatColor.GREEN + "'"));
		} catch (IOException e) {
			sender.sendMessage(new TextComponent(ChatColor.RED + "Didn't work."));
			e.printStackTrace();
		}
	}
	
	
	
}
