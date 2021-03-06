package net.thecobix.openwsk.team;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import net.thecobix.openwsk.arena.Arena;
import net.thecobix.openwsk.arena.ArenaState;
import net.thecobix.openwsk.events.FightQuitEvent;
import net.thecobix.openwsk.fight.Fight;
import net.thecobix.openwsk.fight.FightManager;
import net.thecobix.openwsk.main.OpenWSK;

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
public class Team {

	private String teamName;
	private Arena arena;
	private ArrayList<TeamPlayer> teamMembers = new ArrayList<TeamPlayer>();
	public String teamLeader;
	private int maxTeamSize = 10;
	private boolean isReady = false;
	
	public Team(String name, Arena arena) {
		this.teamName = name;
		this.arena = arena;
	}
	
	public String getTeamLeader() {
		return teamLeader;
	}
	
	public ArrayList<TeamPlayer> getTeamMembers() {
		return teamMembers;
	}
	
	public String getTeamName() {
		return teamName;
	}
	
	public void setTeamLeader(String teamLeader) {
		if(teamMembers.contains(teamLeader)) {
			this.teamLeader = teamLeader;
		} else {
			OpenWSK.Logger.log("Team", teamLeader+" is not in "+teamName+". Coud not set new Teamleader.", 1);
		}
	}
	
	public void setTeamMembers(ArrayList<TeamPlayer> teamMembers) {
		this.teamMembers = teamMembers;
	}
	
	public void setTeamName(String teamName) {
		this.teamName = teamName;
	}
	
	public boolean isTeamMember(String name) {
		return teamMembers.contains(name);
	}
	
	public void sendTeamMessage(String msg) {
		for(TeamPlayer i : teamMembers) {
			Player z = Bukkit.getPlayerExact(i.getPlayerName());
			if(z == null) {
				removePlayer(i.getPlayerName());
				continue;
			}
			if(z.isOnline() == false) {
				removePlayer(i.getPlayerName());
				continue;
			}
			z.sendMessage(msg);
		}
	}
	
	public void removePlayer(String name) {
		TeamPlayer sucken = null;
		for(TeamPlayer tp : teamMembers) {
			if(tp.getPlayerName().equals(name)) {
				sucken = tp;
			}
		}
		if(sucken.getRole() == PlayerRole.CAPTAIN) {
			arena.broadcastInside(OpenWSK.S_PREFIX+"�cDer Captain von "+teamName+" hat das Team verlassen!");
			teamLeader = null;
		}
		if(arena.getState() != ArenaState.IDLE) {
			arena.getScoreboard().removeTeamMember(sucken, teamName);
		}
		this.teamMembers.remove(sucken);
		for(Fight fi : FightManager.fights) {
			if(fi.getArena().getArenaName().equals(arena.getArenaName())) {
				if(arena.getState() == ArenaState.RUNNING) {
					if(teamMembers.isEmpty()) {
						if(teamName.equals("team1")) {
							Bukkit.getPluginManager().callEvent(new FightQuitEvent(fi, "�2Alle Teammitglieder von "+teamName+" sind offline!", arena.getTeam2(), this));
						} else {
							Bukkit.getPluginManager().callEvent(new FightQuitEvent(fi, "�2Alle Teammitglieder von "+teamName+" sind offline!", arena.getTeam1(), this));
						}
					}
				}
			}
		}
	}
	
	public boolean addPlayer(Player player) {
		return addPlayer(player.getName());
	}
	
	public boolean addPlayer(String name) {
		return addPlayer(name, false);
	}
	
	public boolean addPlayer(String name, boolean isLeader) {
		int i = teamMembers.size();
		i ++;
		if(i >= maxTeamSize) {
			return false;
		}
		TeamPlayer tp = new TeamPlayer(name);
		tp.setRole(PlayerRole.SCHUETZE);
		if(isLeader) {
			tp.setRole(PlayerRole.CAPTAIN);
			teamLeader = name;
			for(TeamPlayer d : getTeamMembers()) {
				if(d.getRole() == PlayerRole.CAPTAIN) {
					d.setRole(PlayerRole.SCHUETZE);
					Player z = Bukkit.getPlayerExact(d.getPlayerName());
					z.sendMessage(OpenWSK.S_PREFIX+"�cDu wurdest zum �6Sch�tze �cdegradiert.");
				}
			}
		}
		teamMembers.add(tp);
		return true;
	}
	
	public boolean addPlayer(Player player, boolean isLeader) {
		return addPlayer(player.getName(), isLeader);
	}
	
	public int getMaxTeamSize() {
		return maxTeamSize;
	}
	
	public void setMaxTeamSize(int maxTeamSize) {
		this.maxTeamSize = maxTeamSize;
	}
	
	public Arena getArena() {
		return arena;
	}
	
	public boolean isReady() {
		return isReady;
	}
	
	public void setReady(boolean isReady) {
		this.isReady = isReady;
	}
	
}
