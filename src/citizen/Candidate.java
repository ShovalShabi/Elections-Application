package citizen;

import java.io.Serializable;
import ballot.GenericBallot;
import party.Date;
import party.Party;

public class Candidate extends Citizen implements Serializable {

	private Party party;
	private int votes;

	public Candidate(String name, Date dateOfBirth, String idNumber, GenericBallot<Citizen> ballot, int numOfQuarantinedDays,
			boolean isInQuarantine, Party party, int votes, boolean hasAlreadyVoted, boolean withProtectiveSuit)
			throws IdException {
		super(name, dateOfBirth, idNumber, ballot, numOfQuarantinedDays, isInQuarantine, hasAlreadyVoted,
				withProtectiveSuit);
		this.party = party;
		this.votes = votes;
	}

	// copy constructor
	public Candidate(Candidate forCopy) {
		super(forCopy);
		this.party = forCopy.getParty();
		this.votes = forCopy.getVotes();
	}

	public void setParty(Party party) {
		this.party = party;
	}

	public Party getParty() {
		return party;
	}

	public int getVotes() {
		return votes;
	}

	// there is no need for all of the data so we picked what's more relevant for
	// target object
	public String putInParty() {
		return "Candidates's name:" + name + ", date Of Birth:" + dateOfBirth.toString() + ", idNumber:" + idNumber
				+ ", isInQuarantine:" + isInQuarantine + ", num of quarantined days:" + numOfQuarantinedDays
				+ ", number of votes within the party:" + votes;
	}

	@Override
	public String toString() {
		return "Candidates's name:" + name + ", date Of Birth:" + dateOfBirth.toString() + ", idNumber:" + idNumber
				+ ", isInQuarantine:" + isInQuarantine + ", num of quarantined days:" + numOfQuarantinedDays+", number of votes within the party:" + votes + " embedded to "
				+ ballot.putInCitizen() + ", withProtectiveSuit:" + withProtectiveSuit + ", does he/she voted:"
				+ hasAlreadyVoted + party.putInCandidate();
	}

}
