package party;

import java.io.Serializable;
import java.util.Vector;
import citizen.Candidate;

public class Party implements Serializable {
	private String name;
	private Identity essence;
	private Date dateEstablishing;
	private Vector<Candidate> candidatesList;
	private int numOfVotes;

	public Party(String name, Identity identity, Date date, Vector<Candidate> candidatesList) {
		this.name = name;
		this.essence = identity;
		this.dateEstablishing = date;
		this.candidatesList = candidatesList;
		this.numOfVotes = 0;
	}

	public String getName() {
		return name;
	}

	public Identity getEssence() {
		return essence;
	}

	public String getDateEstablishing() {
		return dateEstablishing.toString();
	}

	// sorting the candidates within the party by the number of votes
	public void sortByPrimaries() {
		int index = 0;
		Candidate hasTheMostVotes = candidatesList.get(0);
		while (index < candidatesList.size()) {
			for (int i = index; i < candidatesList.size(); i++) {
				if (candidatesList.get(i).getVotes() > hasTheMostVotes.getVotes()) {
					hasTheMostVotes = new Candidate(candidatesList.get(i));
					candidatesList.set(i, new Candidate(candidatesList.get(index)));
					candidatesList.set(i, new Candidate(hasTheMostVotes));
				} else {
					index++;
				}
			}
			index++;
		}
		int countTie = 1;
		index = 0;
		for (int i = 0; i < candidatesList.size(); i++) {
			if (index != i && candidatesList.get(index).getVotes() == candidatesList.get(i).getVotes()) {
				countTie++;
			}
		}
		if (countTie > 1) {
			System.out.println("the party has a tie between " + countTie + " candidates!");
		} else {
			System.out.println("the candidate " + hasTheMostVotes.getName() + " is in the lead!");
		}
	}
	public String sortByPrimeariesForView() {
		int index = 0;
		Candidate hasTheMostVotes = candidatesList.get(0);
		while (index < candidatesList.size()) {
			for (int i = index; i < candidatesList.size(); i++) {
				if (candidatesList.get(i).getVotes() > hasTheMostVotes.getVotes()) {
					hasTheMostVotes = new Candidate(candidatesList.get(i));
					candidatesList.set(i, new Candidate(candidatesList.get(index)));
					candidatesList.set(i, new Candidate(hasTheMostVotes));
				} else {
					index++;
				}
			}
			index++;
		}
		int countTie = 1;
		index = 0;
		for (int i = 0; i < candidatesList.size(); i++) {
			if (index != i && candidatesList.get(index).getVotes() == candidatesList.get(i).getVotes()) {
				countTie++;
			}
		}
		if (countTie > 1) {
			return("the party has a tie between " + countTie + " candidates!");
		} else {
			return("the candidate " + hasTheMostVotes.getName() + " is in the lead!");
		}
	}

	public Vector<Candidate> getCandidateList() {
		return candidatesList;
	}

	public int getNumOfVotes() {
		return numOfVotes;
	}

	// increasing the number of votes by one vote
	public void voteToParty() {
		numOfVotes++;
	}

	// adding candidate
	public void addCandidate(Candidate person) {
		if (candidatesList == null) {
			candidatesList = new Vector<Candidate>();
			candidatesList.add(person);
		} else {
			candidatesList.add(person);
		}
	}

	public String putInCandidate() {
		return (", also member of:" + this.getName() + ", party essence:" + this.essence.toString());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof Party) {
			Party other = (Party) obj;
			if (this.getName().equals(other.getName())) {
				return true;
			}
		}
		return false;
	}

	@Override
	// 2 different toString for 2 cases
	public String toString() {
		if (candidatesList == null) {
			StringBuffer strB = new StringBuffer();
			strB.append(
					"party name is:" + name + ", party identity:" + essence.getBelonging() + ", date of establishing:"
							+ dateEstablishing.toString() + ", number of votes:" + numOfVotes + "\n");
			strB.append("there is no candidates in the party yet!\n");
			return strB.toString();
		} else {
			StringBuffer strB = new StringBuffer();
			strB.append(
					"Party name is:" + name + ", party identity:" + essence.getBelonging() + ", date of establishing:"
							+ dateEstablishing.toString() + ", total number of votes:" + numOfVotes + "\n");
			strB.append("candidates information:\n");
			for (int i = 0; i < candidatesList.size(); i++) {
				strB.append(candidatesList.get(i).putInParty() + "\n");
			}
			return strB.toString();
		}
	}

}
