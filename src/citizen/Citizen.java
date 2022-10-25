package citizen;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Vector;
import ballot.GenericBallot;
import party.Date;

public class Citizen implements Serializable {

	protected String name;
	protected Date dateOfBirth;
	protected String idNumber;
	protected GenericBallot<?> ballot;
	protected int numOfQuarantinedDays;
	protected boolean isInQuarantine;
	protected boolean hasAlreadyVoted;
	protected boolean withProtectiveSuit;

	public Citizen(String name, Date dateOfBirth, String idNumber, GenericBallot<?> ballot, int numOfQuarantinedDays,
			boolean isInQuarantine, boolean hasAlreadyVoted, boolean withProtectiveSuit) throws IdException {
		this.name = name;
		this.dateOfBirth = dateOfBirth;
		this.idNumber = checkIdNumber(idNumber);
		this.ballot = ballot;
		this.numOfQuarantinedDays = numOfQuarantinedDays;
		this.isInQuarantine = isInQuarantine;
		this.hasAlreadyVoted = false;
		this.withProtectiveSuit = withProtectiveSuit;
	}

	public Citizen(Citizen forCopy) {
		this.name = forCopy.getName();
		this.dateOfBirth = forCopy.getDateOfBirth();
		this.idNumber = forCopy.getIdNumber();
		this.ballot = forCopy.getBallot();
		this.isInQuarantine = forCopy.getIsInQuarantine();
	}

	// the exception for id number that has less than 9 digits
	public String checkIdNumber(String idNumber) throws IdException {
		if (idNumber.length() < 9) {
			throw new IdException();
		}
		return idNumber;
	}

	// Checks the accurate age in order to determine if the person is a soldier
	public boolean isSoldier() {
		LocalDateTime forCheck = LocalDateTime.now();
		if (dateOfBirth.getYears() < forCheck.getYear() - 18 && dateOfBirth.getYears() > forCheck.getYear() - 21) {
			return true;
		} else {
			if (dateOfBirth.getYears() == forCheck.getYear() - 18) {
				if (dateOfBirth.getMonthes() < forCheck.getMonthValue()) {
					return true;
				}
				if (dateOfBirth.getMonthes() == forCheck.getMonthValue()) {
					if (getDateOfBirth().getDays() <= forCheck.getDayOfMonth()) {
						return true;
					}
				}
			}
			if (dateOfBirth.getYears() == forCheck.getYear() - 21) {
				if (dateOfBirth.getMonthes() > forCheck.getMonthValue()) {
					return true;
				}
				if (dateOfBirth.getMonthes() == forCheck.getMonthValue()) {
					if (getDateOfBirth().getDays() >= forCheck.getDayOfMonth()) {
						return true;
					}
				}
			}
		}
		return false;
	}

	// checks if the person is under aged
	public void isUnderEighteen(Date date) throws unAdultException {
		LocalDateTime forCheck = LocalDateTime.now();
		if (dateOfBirth.getYears() > forCheck.getYear() - 18) {
			throw new unAdultException();
		}
		if (dateOfBirth.getYears() == forCheck.getYear() - 18) {
			if (dateOfBirth.getMonthes() > forCheck.getMonthValue()) {
				throw new unAdultException();
			}
			if (dateOfBirth.getMonthes() == forCheck.getMonthValue()) {
				if (dateOfBirth.getDays() > forCheck.getDayOfMonth()) {
					throw new unAdultException();
				}
			}
		}
	}

	public void setIsInQuarantine(int quarantineOver) {
		if (quarantineOver == 1) {
			this.isInQuarantine = true;
		} else {
			if (quarantineOver == 2) {
				this.isInQuarantine = false;
			}
		}
	}

	public boolean getIsInQuarantine() {
		return isInQuarantine;

	}

	public boolean getWithProtectiveSuit() {
		return withProtectiveSuit;
	}

	public void setWithProtectiveSuit(int withProtectiveSuit) {
		if (withProtectiveSuit == 1) {
			this.withProtectiveSuit = true;
		} else {
			if (withProtectiveSuit == 2) {
				this.withProtectiveSuit = false;
			}
		}
	}

	public String getName() {
		return name;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public GenericBallot<?> getBallot() {
		return ballot;
	}

	public void setBallot(GenericBallot<?> temp) {
		this.ballot = temp;
	}

	public boolean hasBallot() {
		if (this.ballot == null) {
			return false;
		} else {
			return true;
		}
	}

	public boolean getHasAlreadyVoted() {
		return hasAlreadyVoted;
	}

	// method that returns true if the the person has corona virus and protective
	// suit
	// or true if the person is older than 18
	public boolean canVote() {
		LocalDateTime realTime = LocalDateTime.now();
		if (this.getDateOfBirth().getYears() < realTime.getYear() - 18) {
			if (this.isInQuarantine) {
				if (this.withProtectiveSuit) {
					return true;
				} else {
					return false;
				}
			}
			return true;
		} else {
			if (this.getDateOfBirth().getYears() == realTime.getYear() - 18) {
				if (this.getDateOfBirth().getMonthes() <= realTime.getMonthValue()) {
					if (this.getDateOfBirth().getDays() <= realTime.getDayOfMonth()) {
						if (this.isInQuarantine) {
							if (this.withProtectiveSuit) {
								return true;
							} else {
								return false;
							}
						}
						return true;
					}
				}
			}
		}
		return false;
	}

	// vote method updates the voting status within the specific ballot
	public void vote(int selected) {
		if (hasAlreadyVoted == false) {
			hasAlreadyVoted = true;
			Vector<Integer> temp = ballot.getAllVotes();
			int vote = temp.get(selected);
			vote++;
			temp.setElementAt(vote, selected);
		}

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj instanceof Citizen) {
			Citizen other = (Citizen) obj;
			if (other.getIdNumber().equals(this.getIdNumber())) {
				return true;
			}
		}
		return false;
	}

	// there is no need for all of the data so we picked what's more relevant for
	// target object
	public String putInBallot() {
		return "citizen's name:" + name + ", date of birth:" + dateOfBirth.toString() + ", idNumber:" + idNumber
				+ ", isInQuarantine:" + isInQuarantine + ", num of quarantined days:" + numOfQuarantinedDays
				+ ", withProtectiveSuit:" + withProtectiveSuit + ", does he/she voted:" + hasAlreadyVoted;
	}

	@Override
	public String toString() {
		return "Citizen's name:" + name + ", date Of Birth:" + dateOfBirth.toString() + ", idNumber:" + idNumber
				+ ", isInQuarantine:" + isInQuarantine + ", number of days quaranrined:" + numOfQuarantinedDays
				+ " embedded to " + ballot.putInCitizen() + ", withProtectiveSuit: " + withProtectiveSuit
				+ ", does he/she voted:" + hasAlreadyVoted;
	}

}
