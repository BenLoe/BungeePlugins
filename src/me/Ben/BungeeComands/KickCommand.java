package me.Ben.BungeeComands;

import java.util.Set;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class KickCommand extends Command{

	public KickCommand() {
		super("kick");
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length < 2){
			sender.sendMessage(ChatColor.RED + "Incorect syntax: /kickp <player/server/all> <Reason>");
			return;
		}
		ProxyServer ps = ProxyServer.getInstance();
		Set<String> servers = ps.getServers().keySet();
		boolean wasserver = false;
		boolean wasplayer = false;
		boolean all = false;
		if (args[0].equalsIgnoreCase("all")){
			all = true;
		}else if (servers.contains(args[0])){
			wasserver = true;
		}else if (ps.getPlayer(args[0]) != null){
			wasplayer = true;
		}
		
		if (!all && !wasserver && !wasplayer){
			sender.sendMessage(ChatColor.RED + "Your first argument was neither a player name, a server name or 'all'.");
			return;
		}
		
		String reason = "";
		for(int i = 1; i < args.length; i++) reason += (i != 1 ? " " : "") + args[i];
		
		if (all){
			for (ProxiedPlayer p : ps.getPlayers()){
				if (sender.getName() != p.getName()){
					p.disconnect(reason);
				}
			}
			sender.sendMessage(ChatColor.GREEN + "Kicked all players (" + ps.getPlayers().size() + ") on the network.");
		}else if (wasserver){
			ServerInfo server = ps.getServerInfo(args[0]);
			for (ProxiedPlayer p : server.getPlayers()){
					p.disconnect(reason);
			}
			sender.sendMessage(ChatColor.GREEN + "Kicked all players (" + server.getPlayers().size() + ") on '" + server.getName() + "'.");
		}else if (wasplayer){
			ProxiedPlayer p = ps.getPlayer(args[0]);
			p.disconnect(reason);
			sender.sendMessage(ChatColor.GREEN + "Kicked " + args[0] + ".");
		}
	}
	
}
