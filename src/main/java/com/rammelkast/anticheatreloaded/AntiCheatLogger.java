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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.Plugin;

public class AntiCheatLogger {

	public static final String ACR_TAG = "[AntiCheatReloaded] ";
	public static final String FILE_NAME = "anticheatreloaded.log";
	
	private final FileWriter fileWriter;
	
	public AntiCheatLogger(Plugin plugin) throws IOException {
		this.fileWriter = new FileWriter(new File(plugin.getDataFolder(), FILE_NAME), true);
	}
	
	public void end() throws IOException {
		this.fileWriter.close();
	}
	
	public void info(String message) {
		Bukkit.getConsoleSender().sendMessage(ACR_TAG + message);
		this.writeToFile(ACR_TAG + "[Info] " + message);
	}
	
	public void warning(String message) {
		Bukkit.getConsoleSender().sendMessage(ChatColor.YELLOW + ACR_TAG + message);
		this.writeToFile(ACR_TAG + "[Warning] " + message);
	}
	
	public void error(String message) {
		Bukkit.getConsoleSender().sendMessage(ChatColor.RED + ACR_TAG + message);
		this.writeToFile(ACR_TAG + "[Error] " + message);
	}

	private void writeToFile(String message) {
		if (this.fileWriter == null)
			return;
		try {
			this.fileWriter.write(message);
		} catch (IOException e) {}
	}
	
}
