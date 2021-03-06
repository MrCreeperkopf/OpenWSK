package net.thecobix.openwsk.team;

/*
 * OpenWSK WarShip Fight System by St0n3gr1d
   Copyright (C) 2016 St0n3gr1d
   
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
public class TeamPlayer {

	private PlayerRole role;
	private String playerName;
	
	public TeamPlayer(String name) {
		this.playerName = name;
	}
	
	public String getPlayerName() {
		return playerName;
	}
	
	public PlayerRole getRole() {
		return role;
	}
	
	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}
	
	public void setRole(PlayerRole role) {
		this.role = role;
	}
	
}
