package me.Ben.BungeeComands;

import java.util.Set;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

public class SendCommand extends Command{

	public SendCommand() {
		super("sendp");
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void execute(CommandSender sender, String[] args) {
		if (args.length != 2){
			sender.sendMessage(ChatColor.RED + "Incorect syntax: /sendp <player/server/all> <server>");
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
		
		if (!servers.contains(args[1])){
			sender.sendMessage(ChatColor.RED + "Your second argument is not a valid server.");
		}
		
		ServerInfo server = ps.getServerInfo(args[1]);
		if (all){
			for (ProxiedPlayer p : ps.getPlayers()){
				p.connect(server);
			}
			sender.sendMessage(ChatColor.GREEN + "Sent all players (" + ps.getPlayers().size() + ") on the network to '" + server.getName() + "'.");
		}else if (wasserver){
			ServerInfo server1 = ps.getServerInfo(args[0]);
			for (ProxiedPlayer p : server1.getPlayers()){
				p.connect(server);
			}
			sender.sendMessage(ChatColor.GREEN + "Sent all players (" + server1.getPlayers().size() + ") from '" + server1.getName() + "' to '" + server.getName() + "'.");
		}else if (wasplayer){
			ProxiedPlayer p = ps.getPlayer(args[0]);
			p.connect(server);
			sender.sendMessage(ChatColor.GREEN + "Sent '" + args[0] + "' to '" + server.getName() + "'.");
		}
	}
	
}
