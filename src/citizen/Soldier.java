package citizen;

import java.io.Serializable;
import ballot.GenericBallot;
import party.Date;

public class Soldier extends Citizen implements Serializable , CarryWeaponable{

	public Soldier(String name, Date dateOfBirth, String idNumber, GenericBallot<Citizen> ballot, int numOfQuarantinedDays,
			boolean isInQuarantine, boolean hasAlreadyVoted, boolean withProtectiveSuit) throws IdException {
		super(name, dateOfBirth, idNumber, ballot, numOfQuarantinedDays, isInQuarantine, hasAlreadyVoted,
				withProtectiveSuit);
	}

	public void carryWeapon() {
		System.out.println("I'm carrying weapon!");
	}

	@Override
	public String toString() {
		return "Soldier's name:" + name + ", dateOfBirth:" + dateOfBirth + ", idNumber:" + idNumber + ", embedded to "
				+ ballot.putInCitizen() + ", isInQuarantine:" + isInQuarantine + ", number of quarantined days:"
				+ numOfQuarantinedDays + ", does he/she voted:" + hasAlreadyVoted + ", withProtectiveSuit:"
				+ withProtectiveSuit;
	}

}
