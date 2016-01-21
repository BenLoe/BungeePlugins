package me.Ben.BungeeComands;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

public class CreateCommand extends Command{
	
	public CreateCommand() {
		super("createserver");
	}

	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length < 4){
			sender.sendMessage(new TextComponent(ChatColor.RED + "Incorect syntax: /createserver <name> <ip> <port> <motd>"));
			return;
		}
		String name = args[0];
		String ip = args[1];
		String port = args[2];
		String motd = "";
		for(int i = 3; i < args.length; i++) motd += (i != 3 ? " " : "") + args[i];
		ProxyServer ps = ProxyServer.getInstance();
		if (ps.getServers().containsKey(name)){
			sender.sendMessage(ChatColor.RED + "There is already a server with that name.");
			return;
		}
		int porti = 0;
		try {
			porti = Integer.parseInt(port);
		}catch (Exception e){
			sender.sendMessage(ChatColor.RED + "Port must be a number.");
			return;
		}
		for (ServerInfo si : ps.getServers().values()){
			if (si.getAddress().getHostName().equals(ip) && si.getAddress().getPort() == porti){
				sender.sendMessage(ChatColor.RED + "There is already a server with that ip/port combination.");
				return;
			}
		}
		InetSocketAddress address = new InetSocketAddress(ip, porti);
		ServerInfo info = ProxyServer.getInstance().constructServerInfo(name, address, motd, false);
        ps.getServers().put(name, info);
        try {
			Configuration config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(new File("config.yml"));
			config.set("servers." + name + ".motd", motd);
			config.set("servers." + name + ".address", ip + ":" + port);
			config.set("servers." + name + ".restricted", false);
			ConfigurationProvider.getProvider(YamlConfiguration.class).save(config, new File("config.yml"));
		} catch (IOException e) {
			sender.sendMessage(ChatColor.RED + "Didn't work.");
			e.printStackTrace();
		}
	}	
}
