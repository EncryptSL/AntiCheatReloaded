/*
 * AntiCheatReloaded for Bukkit and Spigot.
 * Copyright (c) 2020 Rammelkast
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package com.rammelkast.anticheatreloaded;

import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.rammelkast.anticheatreloaded.managers.AntiCheatManager;

public class AntiCheatReloaded extends JavaPlugin {
	
	/**
	 * ProtocolLib's manager object
	 */
	private ProtocolManager protocolManager;
	/**
	 * The AntiCheatLogger object
	 */
	private AntiCheatLogger logger;
	/**
	 * The AntiCheatManager object
	 */
	private AntiCheatManager manager;

	@Override
	public void onEnable() {
		try {
			this.logger = new AntiCheatLogger(this);
		} catch (IOException e) {
			this.logWarning("Could not access ACR's logger, defaulting to Bukkit's logger");
		}
		
		if (!setupProtocolLib()) {
			this.logError("ACR will NOT function without ProtocolLib, shutting down!");
			Bukkit.getPluginManager().disablePlugin(this);
			return;
		}
		
		this.manager = new AntiCheatManager();
	}

	@Override
	public void onDisable() {
		
	}
	
	private boolean setupProtocolLib() {
		if (Bukkit.getPluginManager().getPlugin("ProtocolLib") == null) {
			return false;
		}
		return (this.protocolManager = ProtocolLibrary.getProtocolManager()) == null;
	}

	/**
	 * Returns the AntiCheatManager object
	 * @return the AntiCheatManager object
	 */
	public AntiCheatManager getManager() {
		return this.manager;
	}
	
	/**
	 * Logs a message to the ACR or Bukkit logger
	 * @param message The loggable message
	 */
	public void logInfo(String message) {
		if (this.logger == null) {
			this.getLogger().info(message);
			return;
		}
		this.logger.info(message);
	}
	
	/**
	 * Logs a message to the ACR or Bukkit logger
	 * @param message The loggable message
	 */
	public void logWarning(String message) {
		if (this.logger == null) {
			this.getLogger().warning(message);
			return;
		}
		this.logger.info(message);
	}
	
	/**
	 * Logs a message to the ACR or Bukkit logger
	 * @param message The loggable message
	 */
	public void logError(String message) {
		if (this.logger == null) {
			this.getLogger().severe(message);
			return;
		}
		this.logger.info(message);
	}
	
	/**
	 * Returns ProtocolLib's manager object
	 * @return ProtocolLib's manager object
	 */
	public ProtocolManager getProtocolManager() {
		return this.protocolManager;
	}
	
	public static AntiCheatReloaded getPlugin() {
		return (AntiCheatReloaded) Bukkit.getPluginManager().getPlugin("AntiCheatReloaded");
	}
	
}
