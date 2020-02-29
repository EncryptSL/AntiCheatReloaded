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

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class User {

	private final UUID uuid;
	private final String name;
	private final int entityId;
	
	private boolean waitingForLevelSync;
	private long levelSyncTimestamp;
	
	public User(UUID uuid) {
		this.uuid = uuid;
		this.name = getPlayer() != null && getPlayer().isOnline() ? getPlayer().getName() : Bukkit.getOfflinePlayer(this.uuid).getName();
		this.entityId = getPlayer() != null && getPlayer().isOnline() ? getPlayer().getEntityId() : -1;
	}
	
	/**
	 * Get the User UUID
	 * @return User UUID
	 */
	public UUID getUuid() {
		return uuid;
	}

	/**
	 * Get the User name
	 * @return User name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Get the user entity ID
	 * @return User entity ID
	 */
	public int getEntityId() {
		return entityId;
	}

	/**
	 * Returns if the player is waiting for the world to sync
	 * @return if the player is waiting for the world to sync
	 */
	public boolean isWaitingForLevelSync() {
		return waitingForLevelSync;
	}

	/**
	 * Returns when the players world was synchronized
	 * @return when the players world was synchronized
	 */
	public long getLevelSyncTimestamp() {
		return levelSyncTimestamp;
	}

	/**
	 * Get the Bukkit player
	 * @return Player
	 */
	public Player getPlayer() {
		return Bukkit.getPlayer(this.uuid);
	}
	
	public void levelSynchronized(long timestamp) {
		this.waitingForLevelSync = false;
		this.levelSyncTimestamp = timestamp;
	}
}
