package ballot;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Vector;
import citizen.Citizen;
import citizen.unAdultException;
import party.Party;

public class GenericBallot<T extends Citizen> implements Serializable {
	private static int count = 1000;
	private int serialNumber;
	private String address;
	private boolean isCoronaBallot;
	private boolean isArmyBallot;
	private Vector<T> allCitizens;
	private Vector<Integer> allVotes;
	private Vector<Party> allParties;

	public GenericBallot(String address, boolean isUnHealthy, boolean isArmyBallot, Vector<Party> listOfParties) {
		serialNumber = count++;
		this.address = address;
		this.isCoronaBallot = isUnHealthy;
		this.isArmyBallot = isArmyBallot;
		this.allCitizens = new Vector<T>();
		this.allVotes = new Vector<Integer>();
		for (int i = 0; i < listOfParties.size(); i++) {
			allVotes.add(0);
		}
		this.allParties = listOfParties;
	}

	// checking if the citizen can vote according to live time
	// we will operate this method as a condition for voting at part 8 of the menu
	public void isAdult() throws unAdultException {
		for (int i = 0; i < allCitizens.size(); i++) {
			LocalDateTime forCheck = LocalDateTime.now();
			if ((allCitizens.get(i).getDateOfBirth().getYears()) > forCheck.getYear() - 18) {
				throw new unAdultException();
			} else {
				if (allCitizens.get(i).getDateOfBirth().getMonthes() > forCheck.getMonthValue()) {
					throw new unAdultException();
				} else {
					if (allCitizens.get(i).getDateOfBirth().getDays() > forCheck.getDayOfMonth()) {
						throw new unAdultException();
					}
				}
			}
		}
	}

	public String getAddress() {
		return address;
	}

	public Vector<T> getAllCitizens() {
		return allCitizens;
	}

	// adding citizen to the list of citizens of the ballot
	public void addCitizen(T person) {
		allCitizens.add(person);
		person.setBallot(this);
	}

	public Vector<Integer> getAllVotes() {
		return allVotes;
	}

	public int getSerialNumber() {
		return serialNumber;
	}

	public boolean getIsArmyBallot() {
		return isArmyBallot;
	}

	public boolean getIsCoronaBallot() {
		return isCoronaBallot;
	}

	// there is no need for all of the data so we picked what's more relevant for
	// target object
	public String putInCitizen() {
		return "ballot location:" + this.getAddress() + ", with serial number:" + this.getSerialNumber();
	}

	// shows the vote percentage by the number of people who can vote
	public void showVotePrecentage() {
		int countCanVote = 0, actuallyVoted = 0;
		if (!allCitizens.isEmpty()) {
			for (int i = 0; i < allCitizens.size(); i++) {
				if (allCitizens.get(i).canVote()) {
					countCanVote++;
				}
			}
			for (int i = 0; i < allCitizens.size(); i++) {
				if (allCitizens.get(i).getHasAlreadyVoted()) {
					actuallyVoted++;
				}
			}
			if (countCanVote == 0) {
				System.out.println("the vote precentage for the ballot " + this.getSerialNumber() + " is:0.0%");
			} else {
				System.out.println("the vote precentage for the ballot " + this.getSerialNumber() + " is:"
						+ ((double) actuallyVoted / (double) countCanVote) * 100 + "%");
			}
		} else {
			System.out.println("the ballot " + this.getSerialNumber() + " has no voters yet!");
		}
	}

	public String showVotePrecentageForView() {
		int countCanVote = 0, actuallyVoted = 0;
		StringBuffer strB = new StringBuffer();
		if (!allCitizens.isEmpty()) {
			for (int i = 0; i < allCitizens.size(); i++) {
				if (allCitizens.get(i).canVote()) {
					countCanVote++;
				}
			}
			for (int i = 0; i < allCitizens.size(); i++) {
				if (allCitizens.get(i).getHasAlreadyVoted()) {
					actuallyVoted++;
				}
			}
			if (countCanVote == 0) {
				strB.append("the vote precentage for the ballot " + this.getSerialNumber() + " is:0.0%\n");
			} else {
				strB.append("the vote precentage for the ballot " + this.getSerialNumber() + " is:"
						+ ((double) actuallyVoted / (double) countCanVote) * 100 + "%\n");
			}
		} else {
			strB.append("the ballot " + this.getSerialNumber() + " has no voters yet!\n");
		}
		return (strB.toString());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		GenericBallot<T> other = (GenericBallot<T>) obj;
		if (address.equals(other.getAddress())) {
			return true;
		}
		return false;
	}

	@Override
	// 2 different toString for 2 cases
	public String toString() {
		StringBuffer strB = new StringBuffer();
		if (allCitizens.isEmpty()) {
			strB.append("ballot information is:\n");
			strB.append("serial number:" + serialNumber + ", address:" + address + "\n");
			strB.append("the citizens information is:\n");
			strB.append("there is no citizens in the ballot yet!\n");
			return strB.toString();
		} else {
			if (isArmyBallot) {
				if (isCoronaBallot) {
					strB.append("the ballot is an corona-army ballot, ballot information is:\n");
					strB.append("serial number:" + serialNumber + ", address:" + address + "\n");
					strB.append("the citizens information is:\n");
					for (int i = 0; i < allCitizens.size(); i++) {
						strB.append(allCitizens.get(i).putInBallot() + "\n");
					}
					return strB.toString();
				} else {
					strB.append("the ballot is a army ballot, ballot information is:\n");
					strB.append("serial number:" + serialNumber + ", address:" + address + "\n");
					for (int i = 0; i < allCitizens.size(); i++) {
						strB.append(allCitizens.get(i).putInBallot() + "\n");
					}
					return strB.toString();
				}
			} else {
				if (isCoronaBallot) {
					strB.append("the ballot is a corona, ballot information is:\n");
					strB.append("serial number:" + serialNumber + ", address:" + address + "\n");
					strB.append("the citizens information is:\n");
					for (int i = 0; i < allCitizens.size(); i++) {
						strB.append(allCitizens.get(i).putInBallot() + "\n");
					}
					return strB.toString();
				} else {
					strB.append("ballot information is:\n");
					strB.append("serial number:" + serialNumber + ", address:" + address + "\n");
					strB.append("the citizens information is:\n");
					for (int i = 0; i < allCitizens.size(); i++) {
						strB.append(allCitizens.get(i).putInBallot() + "\n");
					}
					return strB.toString();
				}
			}
		}
	}

}
