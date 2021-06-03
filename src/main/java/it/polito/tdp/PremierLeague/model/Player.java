package it.polito.tdp.PremierLeague.model;

public class Player {
	Integer playerID;
	String name;
	Double avg;
	int titolarita;
	
	

	public int getMinplayed() {
		return titolarita;
	}



	public void setMinplayed(int minplayed) {
		this.titolarita = minplayed;
	}



	public Player(Integer playerID, String name, Double avg) {
		super();
		this.playerID = playerID;
		this.name = name;
		this.avg = avg;
	
	}
	
	

	public Double getAvg() {
		return avg;
	}

	public void setAvg(Double avg) {
		this.avg = avg;
	}

	public Integer getPlayerID() {
		return playerID;
	}
	public void setPlayerID(Integer playerID) {
		this.playerID = playerID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((playerID == null) ? 0 : playerID.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Player other = (Player) obj;
		if (playerID == null) {
			if (other.playerID != null)
				return false;
		} else if (!playerID.equals(other.playerID))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return playerID + " - " + name;
	}
	
	
	
}
