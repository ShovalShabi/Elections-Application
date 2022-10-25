package elections;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Scanner;
import java.util.Vector;
import ballot.GenericBallot;
import citizen.Candidate;
import citizen.CarryWeaponable;
import citizen.Citizen;
import citizen.MisMatchObjectException;
import citizen.MySet;
import citizen.Soldier;
import mvc.ElectionsListener;
import party.Date;
import party.DateException;
import party.Party;

public class Elections implements Serializable {
	private MySet<Citizen> allCitizens;
	private Vector<Party> allParties;
	private Vector<GenericBallot<? extends Citizen>> allBallots;
	private Date electionsDate;
	private Vector<ElectionsListener> listeners;

	public Elections(MySet<Citizen> listOfcitizens, Vector<Party> allParties, Vector<GenericBallot<?>> listOfBallots)
			throws DateException {
		this.allCitizens = listOfcitizens;
		this.allParties = allParties;
		this.allBallots = listOfBallots;
		LocalDateTime currentDateTime = LocalDateTime.now();
		this.electionsDate = new Date(currentDateTime.getDayOfMonth(), currentDateTime.getMonthValue(),
				currentDateTime.getYear());
		this.listeners = new Vector<ElectionsListener>();
	}

	public MySet<Citizen> getAllCitizens() {
		return allCitizens;
	}

	//connecting model to listener
	public void registerListener(ElectionsListener listener) {
		listeners.add(listener);
	}

	//for view-return String information about all last results of the election from saved file
	public String readLastResults() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream inFile = new ObjectInputStream(new FileInputStream("elections.data"));
		Elections temp = (Elections) inFile.readObject();
		StringBuffer strB = new StringBuffer();
		strB.append(temp.allBallotsInfo() + "\n");
		strB.append(temp.allCitizensInfo() + "\n");
		strB.append(temp.allPartiesInfo() + "\n");
		strB.append(temp.showResultsOfTheElectionsForView() + "\n");
		inFile.close();
		return (strB.toString());
	}

	//for view-return String information about all ballots
	public String allBallotsInfo() {
		StringBuffer strB = new StringBuffer();
		for (int i = 0; i < allBallots.size(); i++) {
			strB.append(allBallots.get(i).toString() + "\n");
		}
		return (strB.toString());
	}

	//for view-return String information about all citizens
	public String allCitizensInfo() {
		StringBuffer strB = new StringBuffer();
		for (int i = 0; i < allCitizens.getData().size(); i++) {
			strB.append(allCitizens.get(i).toString() + "\n");
		}
		return (strB.toString());
	}

	//for view-return String information about all parties
	public String allPartiesInfo() {
		StringBuffer strB = new StringBuffer();
		for (int i = 0; i < allParties.size(); i++) {
			if (allParties.get(i).getCandidateList() == null) {
				strB.append(allParties.get(i).toString() + "\n");
			} else {
				strB.append(allParties.get(i).sortByPrimeariesForView() + "\n");
				strB.append(allParties.get(i).toString() + "\n");
			}
		}
		return (strB.toString());
	}

	// this method gets Map of id's and party names and vote each party by id
	public void voteFromView(Map<String, String> voters) {
		for (Map.Entry<String, String> entry : voters.entrySet()) {
			int index = 0;
			for (int i = 0; i < allParties.size(); i++) {
				if (entry.getValue().equals(allParties.get(i).getName())) {
					index = i;
					allParties.get(i).voteToParty();
					break;
				}
			}
			for (int i = 0; i < allCitizens.getData().size(); i++) {
				if (entry.getKey().equals(allCitizens.get(i).getIdNumber())) {
					allCitizens.get(i).vote(index);
					break;
				}
			}
		}
	}

	//for view-return String information about the current elections
	public String showResultsOfTheElectionsForView() {
		StringBuffer strB = new StringBuffer();
		strB.append("for the elections that happened at date " + electionsDate.toString() + " the results are:\n");
		strB.append("The vote precentage from each ballot for the elections is:\n");
		strB.append("\n");
		for (int i = 0; i < allBallots.size(); i++) {
			strB.append(allBallots.get(i).showVotePrecentageForView() + "\n");
		}
		strB.append("\n");
		Vector<Integer> votesPerParty = new Vector<Integer>(allParties.size());
		for (int k = 0; k < allParties.size(); k++) {
			if (allParties.get(k).getCandidateList() == null) {
				strB.append(
						"the party " + allParties.get(k).getName() + " is a new party and has no candidates yet!\n");
				strB.append("there is no votes for party with no candidates\n");
				strB.append("\n");
				votesPerParty.add(k, 0);
			} else {
				strB.append(allParties.get(k).sortByPrimeariesForView() + "\n");
				strB.append(allParties.get(k).toString() + "\n");
				strB.append("The voting status for the party from each ballot is:\n");
				strB.append("\n");
				int sum = 0;
				for (int j = 0; j < allBallots.size(); j++) {
					Vector<Integer> calc = allBallots.get(j).getAllVotes();
					sum = sum + calc.get(k);
					strB.append(calc.get(k) + " votes from the ballot:" + allBallots.get(j).getSerialNumber() + "\n");
				}
				votesPerParty.add(k, sum);
				strB.append("\n");
				strB.append("The total votes for the party is:" + allParties.get(k).getNumOfVotes() + "\n");
				strB.append("-------------------------------------------------------------------------\n");
				strB.append("\n");
			}
		}
		int indexWinningParty = 0, sameVotes = 1;
		for (int i = 0; i < votesPerParty.size(); i++) {
			if (votesPerParty.get(i) > votesPerParty.get(indexWinningParty)) {
				indexWinningParty = i;
			}
		}
		for (int i = 0; i < votesPerParty.size(); i++) {
			if ((votesPerParty.get(i) == votesPerParty.get(indexWinningParty)) && (i != indexWinningParty)) {
				sameVotes++;
			}
		}
		if (sameVotes > 1) {
			strB.append("there is a tie between " + sameVotes + " parties with the same number of votes!\n");
			strB.append("\n");
		} else {
			strB.append("the leading party in elections for this moment is: "
					+ allParties.get(indexWinningParty).getName() + "\n");
			strB.append("\n");
		}
		return (strB.toString());
	}

	// adding citizen/soldier to the list of citizens
	public void addCitizen(Citizen person) throws MisMatchObjectException {
		boolean flag;
		if (person instanceof CarryWeaponable) {
			flag = allCitizens.add(person);
			if (flag) {
				System.out.println("the soldier has been added successfully!");
			} else {
				System.out.println("there is no option to enter the same person into the database more than once");
			}
		} else {
			flag = allCitizens.add(person);
			if (flag) {
				System.out.println("the citizen has been added successfully!");
			} else {
				System.out.println("there is no option to enter the same person into the database more than once");
			}
		}
	}

	// adding candidate to the list of citizens
	public void addCandidate(Candidate person, Party party) throws MisMatchObjectException {
		boolean flag = allCitizens.add(person);
		if (flag) {
			for (int i = 0; i < allParties.size(); i++) {
				if (allParties.get(i) == party) {
					allParties.get(i).addCandidate(person);
				}
			}
			System.out.println("the candidate has been added to party successfully!");
		} else {
			System.out.println("there is no option to enter the same person into the database more than once");
		}

	}

	public Vector<Party> getAllParties() {
		return allParties;
	}

	// adding party to the list of parties
	public void addParty(Party party) {
		if (!allParties.contains(party)) {
			allParties.add(party);
			for (int i = 0; i < allBallots.size(); i++) {
				allBallots.get(i).getAllVotes().add(0);
			}
			System.out.println("the party has been added successfully!");
		} else {
			System.out.println("there is no option to enter party with the same name");
		}

	}

	public Vector<GenericBallot<?>> getAllBallots() {
		return allBallots;
	}

	// adding ballot to the list of ballots
	public void addBallot(GenericBallot<?> ballot) {
		if (!allBallots.contains(ballot)) {
			allBallots.add(ballot);
			System.out.println("the ballot has been added successfully!");
		} else {
			System.out.println("there is no option to enter ballot with the same address");
		}
	}

	// showing information of all of the citizens
	public void showAllCitizens() {
		System.out.println(allCitizens.toString());
	}

	// showing information of all of the parties
	public void showAllParties() {
		for (int i = 0; i < allParties.size(); i++) {
			if (allParties.get(i).getCandidateList() != null) {
				allParties.get(i).sortByPrimaries();
			}
			System.out.println(allParties.get(i));
		}
	}

	// showing information of all of the ballots
	public void showAllBallots() {
		for (int i = 0; i < allBallots.size(); i++) {
			System.out.println(allBallots.get(i));
		}
	}

	// the method make the candidate choose the party that he/she wants
	public Party candidateChooseParty(Scanner input) {
		int countTheActualParties = 0, partyChoice;
		System.out.println("please choose for the candidate which party he/she runs for:");
		for (int i = 0; i < allParties.size(); i++) {
			countTheActualParties++;
			System.out.printf("for the party " + allParties.get(i).getName() + " choose %d \n", i + 1);
		}
		System.out.println("");
		System.out.println("your choice:");
		partyChoice = input.nextInt();
		boolean flag = false;
		while (flag == false) {
			if (partyChoice <= 0 || partyChoice > countTheActualParties) {
				System.out.print("please choose again number between 1 to " + countTheActualParties + ":");
				partyChoice = input.nextInt();
				System.out.println("");
			} else {
				flag = true;
			}
		}
		partyChoice--;
		return allParties.get(partyChoice);
	}

	// this method sort the candidates, citizens and soldiers to ballot randomly
	public void sortToBallots() {
		for (int i = 0; i < allCitizens.getData().size(); i++) {
			if (allCitizens.get(i).hasBallot() == false) {
				if (allCitizens.get(i).getIsInQuarantine()) {
					if (allCitizens.get(i) instanceof CarryWeaponable) {
						randomizeBallot(allCitizens.get(i), 4);
					} else {
						randomizeBallot(allCitizens.get(i), 1);
					}
				} else {
					if (allCitizens.get(i) instanceof CarryWeaponable) {
						randomizeBallot(allCitizens.get(i), 2);
					} else {
						randomizeBallot(allCitizens.get(i), 3);
					}
				}
			}
		}

	}

	// helper method of sortToBallots
	public void randomizeBallot(Citizen person, int choice) {
		if (choice == 1) {
			int countCorona = 0;
			for (int i = 0; i < allBallots.size(); i++) {
				if (allBallots.get(i).getIsCoronaBallot() && allBallots.get(i).getIsArmyBallot() == false) {
					countCorona++;
				}
			}
			int indexRun = 0;
			Vector<GenericBallot<Citizen>> tempC = new Vector<GenericBallot<Citizen>>(countCorona);
			while (countCorona > 0) {
				if (allBallots.get(indexRun).getIsCoronaBallot()
						&& allBallots.get(indexRun).getIsArmyBallot() == false) {
					tempC.add((GenericBallot<Citizen>) allBallots.get(indexRun));
					countCorona--;
				}
				indexRun++;
			}
			int result = (int) (Math.random() * (tempC.size()));
			tempC.get(result).addCitizen(person);
			person.setBallot(tempC.get(result));

		} else {
			if (choice == 2) {
				int countArmy = 0;
				for (int i = 0; i < allBallots.size(); i++) {
					if (allBallots.get(i).getIsArmyBallot() && allBallots.get(i).getIsCoronaBallot() == false) {
						countArmy++;
					}
				}
				int indexRun = 0;
				Vector<GenericBallot<Soldier>> tempA = new Vector<GenericBallot<Soldier>>(countArmy);
				while (countArmy > 0) {
					if (allBallots.get(indexRun).getIsArmyBallot()
							&& allBallots.get(indexRun).getIsCoronaBallot() == false) {
						tempA.add((GenericBallot<Soldier>) allBallots.get(indexRun));
						countArmy--;
					}
					indexRun++;
				}
				int result = (int) (Math.random() * (tempA.size()));
				tempA.get(result).addCitizen((Soldier) person);
			}
			if (choice == 3) {
				int countArmy = 0, countCorona = 0, countCoronaArmy = 0;
				for (int i = 0; i < allBallots.size(); i++) {
					if (allBallots.get(i).getIsCoronaBallot() && allBallots.get(i).getIsArmyBallot() == false) {
						countCorona++;
					}
					if (allBallots.get(i).getIsArmyBallot() && allBallots.get(i).getIsCoronaBallot() == false) {
						countArmy++;
					}
					if (allBallots.get(i).getIsArmyBallot() && allBallots.get(i).getIsCoronaBallot()) {
						countCoronaArmy++;
					}
				}
				int countRegular = allBallots.size() - countCorona - countArmy - countCoronaArmy;
				Vector<GenericBallot<Citizen>> tempR = new Vector<GenericBallot<Citizen>>(countRegular);
				for (int i = 0; i < allBallots.size(); i++) {
					if (allBallots.get(i).getIsCoronaBallot() || allBallots.get(i).getIsArmyBallot()) {
						continue;
					} else {
						tempR.add((GenericBallot<Citizen>) allBallots.get(i));
					}
				}
				int result = (int) (Math.random() * (tempR.size()));
				tempR.get(result).addCitizen(person);
				person.setBallot(tempR.get(result));
			}
			if (choice == 4) {
				int countCoronaArmy = 0;
				for (int i = 0; i < allBallots.size(); i++) {
					if (allBallots.get(i).getIsArmyBallot() && allBallots.get(i).getIsCoronaBallot()) {
						countCoronaArmy++;
					}
				}
				if (countCoronaArmy == 0) {
					allCitizens.getData().removeElement(person);
					System.out.println("we're sorrry there is no corona-army ballot yet!");
				}
				Vector<GenericBallot<Soldier>> tempCA = new Vector<GenericBallot<Soldier>>(countCoronaArmy);
				for (int i = 0; i < allBallots.size(); i++) {
					if (allBallots.get(i).getIsArmyBallot() && allBallots.get(i).getIsCoronaBallot()) {
						tempCA.add((GenericBallot<Soldier>) allBallots.get(i));
					}
				}
				int result = (int) (Math.random() * (tempCA.size()));
				tempCA.get(result).addCitizen((Soldier) person);
				person.setBallot(tempCA.get(result));
			}
		}
	}

	// the method that operates the election of the party for each citizen and
	// candidate
	public void elect(Scanner input) {
		sortToBallots();
		int choice = 0;
		boolean validChoice = false;
		for (int i = 0; i < allCitizens.getData().size(); i++) {
			if (allCitizens.get(i).getHasAlreadyVoted() == false && allCitizens.get(i).canVote()) {
				System.out.print(
						"hello " + allCitizens.get(i).getName() + " would you like to vote to a party?, 1-yes 2-no:");
				choice = 0;
				while (choice != 1 && choice != 2) {
					choice = input.nextInt();
					System.out.println("");
					switch (choice) {
					case 1:
						int countActual = 0;
						while (validChoice == false) {
							System.out.println("please choose from this list of party who you want to vote:");
							int partyChoice;
							for (int j = 0; j < allParties.size(); j++) {
								if (allParties.get(j).getCandidateList() != null) {
									countActual = j;
									System.out.printf("for party " + allParties.get(j).getName() + " press %d", j + 1);
									System.out.println("");
								}
							}
							System.out.print("your choice:");
							partyChoice = input.nextInt();
							System.out.println("");
							partyChoice--;
							if (partyChoice >= 0 && partyChoice <= countActual) {
								if (allParties.get(partyChoice).getCandidateList() == null) {
									System.out.println("please choose other number except this number bacause "
											+ allParties.get(partyChoice).getName() + " has no candidates yet!");
								} else {
									allCitizens.get(i).vote(partyChoice);
									this.allParties.get(partyChoice).voteToParty();
									validChoice = true;
								}
							} else {
								System.out.println("please choose again and select option 1 to " + (countActual + 1));
							}
						}
						validChoice = false;
						break;
					case 2:
						break;

					default:
						System.out.println("please enter: 1-yes 2- no");
						break;
					}
				}
			}
		}
	}

	// the method shows the result of the current elections: vote percentage, number
	// of votes for a party
	// from each ballot and the total votes as well, shows the status of the party
	// within the party like which
	// candidate is in the lead and which party is in the lead
	public void showResultsOfTheElections() {
		System.out.println("for the elections that happened at date " + electionsDate.toString() + " the results are:");
		System.out.println("The vote precentage from each ballot for the elections is:");
		System.out.println("");
		for (int i = 0; i < allBallots.size(); i++) {
			allBallots.get(i).showVotePrecentage();
			System.out.println("");
		}
		System.out.println("");
		Vector<Integer> votesPerParty = new Vector<Integer>(allParties.size());
		for (int k = 0; k < allParties.size(); k++) {
			if (allParties.get(k).getCandidateList() == null) {
				System.out.println(
						"the party " + allParties.get(k).getName() + " is a new party and has no candidates yet!");
				System.out.println("there is no votes for party with no candidates");
				System.out.println("");
				votesPerParty.add(k, 0);
			} else {
				allParties.get(k).sortByPrimaries();
				System.out.println("");
				System.out.println(allParties.get(k));
				System.out.println("");
				System.out.println("The voting status for the party from each ballot is:");
				System.out.println("");
				int sum = 0;
				for (int j = 0; j < allBallots.size(); j++) {
					Vector<Integer> calc = allBallots.get(j).getAllVotes();
					sum = sum + calc.get(k);
					System.out.println(calc.get(k) + " votes from the ballot:" + allBallots.get(j).getSerialNumber());
				}
				votesPerParty.add(k, sum);
				System.out.println("");
				System.out.println("The total votes for the party is:" + allParties.get(k).getNumOfVotes());
				System.out.println("-------------------------------------------------------------------------");
				System.out.println("");
			}
		}
		int indexWinningParty = 0, sameVotes = 1;
		for (int i = 0; i < votesPerParty.size(); i++) {
			if (votesPerParty.get(i) > votesPerParty.get(indexWinningParty)) {
				indexWinningParty = i;
			}
		}
		for (int i = 0; i < votesPerParty.size(); i++) {
			if ((votesPerParty.get(i) == votesPerParty.get(indexWinningParty)) && (i != indexWinningParty)) {
				sameVotes++;
			}
		}
		if (sameVotes > 1) {
			System.out.println("there is a tie between " + sameVotes + " parties with the same number of votes!");
			System.out.println("");
		} else {
			System.out.println(
					"the leading party in elections for this moment is:" + allParties.get(indexWinningParty).getName());
			System.out.println("");
		}

	}

	// this option will override the last data of the elections once the user exit
	// the menu at option "10"
	public void save() throws FileNotFoundException, IOException {
		ObjectOutputStream outFile = new ObjectOutputStream(new FileOutputStream("elections.data"));
		outFile.writeObject(this);
		outFile.close();
	}

	// this option will print all the data from the previous election round
	// by the order: ballots info-> citizens info-> parties info-> total results of
	// the last saved elections
	public void read() throws FileNotFoundException, IOException, ClassNotFoundException {
		ObjectInputStream inFile = new ObjectInputStream(new FileInputStream("elections.data"));
		Elections temp = (Elections) inFile.readObject();
		temp.showAllBallots();
		temp.showAllCitizens();
		temp.showAllParties();
		temp.showResultsOfTheElections();
		inFile.close();
	}

}
