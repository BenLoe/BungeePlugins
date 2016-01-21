package me.Ben.BungeeComands;

import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

public class Events implements Listener{
	
	public static String newmotd = "unset";
	
	@EventHandler
	public void ProxyPing(ProxyPingEvent event){
		if (newmotd != "unset"){
			ServerPing newPing = event.getResponse();
			newPing.setDescription(newmotd.replaceAll("&", "\u00A7"));
			event.setResponse(newPing);
		}
	}
	
}
