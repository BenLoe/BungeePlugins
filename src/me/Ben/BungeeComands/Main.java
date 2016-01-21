package me.Ben.BungeeComands;

import net.md_5.bungee.api.plugin.Plugin;

public class Main extends Plugin{
	
	public void onEnable(){
		getProxy().getPluginManager().registerListener(this, new Events());
		getProxy().getPluginManager().registerCommand(this, new MotdCommand());
		getProxy().getPluginManager().registerCommand(this, new SendCommand());
		getProxy().getPluginManager().registerCommand(this, new KickCommand());
		getProxy().getPluginManager().registerCommand(this, new CreateCommand());
	}

}
