package elections;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Vector;
import ballot.GenericBallot;
import citizen.Candidate;
import citizen.Citizen;
import citizen.IdException;
import citizen.MisMatchObjectException;
import citizen.MySet;
import citizen.Soldier;
import citizen.unAdultException;
import party.Date;
import party.DateException;
import party.Identity;
import party.Party;

public class Program implements ElectionsView {

	private Elections goverment;

	public Program() {
		// start of hard-coded part by the constructor
		try {
			Citizen citizen1 = new Citizen("Yoni", new Date(12, 5, 1991), "205632147", null, 0, false, false, false);
			Citizen citizen2 = new Citizen("Shelev", new Date(28, 2, 2000), "208489875", null, 0, false, false, false);
			Citizen citizen3 = new Citizen("Yarden", new Date(11, 3, 1990), "208587412", null, 10, true, false, true);
			Citizen citizen4 = new Citizen("Dor", new Date(21, 11, 1996), "209854126", null, 8, true, false, true);
			Soldier soldier = new Soldier("Liran", new Date(8, 1, 2002), "207412365", null, 0, false, false, false);
			Party party1 = null;
			Candidate candidate1 = new Candidate("Bibi Netanyaho", new Date(12, 4, 1946), "201563248", null, 0, false,
					party1, 100, false, false);
			Candidate candidate2 = new Candidate("Naftali Benet", new Date(12, 4, 1956), "206547921", null, 0, false,
					party1, 50, false, false);
			Vector<Candidate> candidateList1 = new Vector<Candidate>();
			candidateList1.add(candidate1);
			candidateList1.add(candidate2);
			party1 = new Party("Likud", Identity.RightWing, new Date(11, 1, 1930), candidateList1);
			candidate1.setParty(party1);
			candidate2.setParty(party1);
			Party party2 = null;
			Candidate candidate3 = new Candidate("Avigdor Liberman", new Date(8, 12, 1989), "207421897", null, 11, true,
					party2, 39, false, true);
			Candidate candidate4 = new Candidate("Gidon Saar", new Date(12, 9, 1952), "205963145", null, 0, false,
					party2, 50, false, false);
			Vector<Candidate> candidateList2 = new Vector<Candidate>();
			candidateList2.add(candidate3);
			candidateList2.add(candidate4);
			party2 = new Party("Israel Beyteno", Identity.Center, new Date(11, 1, 1993), candidateList2);
			candidate3.setParty(party2);
			candidate4.setParty(party2);
			Party party3 = null;
			Candidate candidate5 = new Candidate("Yair Lapid", new Date(7, 3, 1978), "202514782", null, 0, false,
					party3, 60, false, false);
			Candidate candidate6 = new Candidate("Beni Gantz", new Date(26, 5, 1959), "209514632", null, 0, false,
					party3, 43, false, false);
			Vector<Candidate> candidateList3 = new Vector<Candidate>();
			candidateList3.add(candidate5);
			candidateList3.add(candidate6);
			party3 = new Party("Ka'hol Lavan", Identity.leftWing, new Date(11, 1, 2018), candidateList3);
			candidate5.setParty(party3);
			candidate6.setParty(party3);
			Vector<Party> partyList = new Vector<Party>();
			partyList.add(party1);
			partyList.add(party2);
			partyList.add(party3);
			MySet<Citizen> allCitizens = new MySet<Citizen>();
			allCitizens.add(citizen1);
			allCitizens.add(citizen2);
			allCitizens.add(citizen3);
			allCitizens.add(citizen4);
			allCitizens.add(soldier);
			allCitizens.add(candidate1);
			allCitizens.add(candidate2);
			allCitizens.add(candidate3);
			allCitizens.add(candidate4);
			allCitizens.add(candidate5);
			allCitizens.add(candidate6);
			GenericBallot<Citizen> ballot1 = new GenericBallot<Citizen>("Tel Aviv Rabin 5", false, false, partyList);
			GenericBallot<Citizen> ballot2 = new GenericBallot<Citizen>("Rishon Letziyon Haalia 21", true, false,
					partyList);
			GenericBallot<Soldier> ballot3 = new GenericBallot<Soldier>("Jeruselm Azza 1", false, true, partyList);
			Vector<GenericBallot<?>> totalBallots = new Vector<GenericBallot<?>>();
			totalBallots.add(ballot1);
			totalBallots.add(ballot2);
			totalBallots.add(ballot3);
			this.goverment = new Elections(allCitizens, partyList, totalBallots);
			this.goverment.sortToBallots();
			// end of hard-coded part
		} catch (DateException | IdException | MisMatchObjectException e) {
			System.out.println(e.getMessage());
			return;
		}
	}

	public static void main(String[] args) {
		Program prog = new Program();
		Scanner input = new Scanner(System.in);
		prog.read();
		int choice = 0;
		while (choice != 10) {

			System.out.println("Welcome to our election system please choose from the menu below for any option:");
			System.out.println("1 - Add ballot");
			System.out.println("2 - Add citizen ");
			System.out.println("3 - Add party ");
			System.out.println("4 - Add citizen as candidate ");
			System.out.println("5 - Show all ballots information ");
			System.out.println("6 - Show all citizens information");
			System.out.println("7 - show all parties information ");
			System.out.println("8 - Start the election process ");
			System.out.println("9 - show election results ");
			System.out.println("10 - Exit system ");
			System.out.print("enter your choice:");
			choice = input.nextInt();
			System.out.println("");

			switch (choice) {

			case 1:
				prog.addBallot();
				break;

			// adding citizen to system
			case 2:
				prog.addCitizen();
				break;

			// adding party to system
			case 3:
				prog.addParty();
				break;

			// adding candidate to system
			case 4:
				prog.addCandidate();
				break;

			// ballots info
			case 5:
				prog.showAllBallots();
				break;

			// citizens info
			case 6:
				prog.showAllCitizens();
				break;

			// parties info
			case 7:
				prog.showAllParties();
				break;

			// activating the election process
			case 8:
				prog.elect();
				break;

			// election results at this moment
			case 9:
				prog.showResultsOfTheElections();
				break;

			// exiting and saving data automatically
			case 10:
				System.out.println("Thank you for using our elections system, see you next time !");
				prog.save();
				break;

			default:
				System.out.println("please enter number between 1 - 9 only\n");

			}

		}
		input.close();
	}

	// reading from file
	@Override
	public void read() {
		Scanner input = new Scanner(System.in);
		int choice = 0;
		boolean flag = false;
		while (flag == false) {
			System.out.print("would you like to upload previous data of lastest elections? 1-yes 2-no: ");
			choice = input.nextInt();
			System.out.println("");
			switch (choice) {
			case 1:
				try {
					goverment.read();
				} catch (ClassNotFoundException | IOException e) {
					System.out.println("you need to save the data at least for the first time!");
					System.out.println("");
				}
				flag = true;
				break;
			case 2:
				flag = true;
				break;
			default:
				System.out.println("please choose 1 or 2");
				choice = input.nextInt();
				break;
			}
		}
	}

	// adding ballot
	@Override
	public void addBallot() {
		Scanner input = new Scanner(System.in);
		boolean flag = false;
		GenericBallot<?> ballotToPut = null;
		System.out.print("please enter address of the ballot:");
		String address = input.nextLine();
		System.out.println("");
		System.out.println("please choose which kind of ballot would you like to add:");
		System.out.println("for regular ballot plaese press 1");
		System.out.println("for corona ballot please press 2");
		System.out.println("for army ballot please press 3");
		System.out.println("for corona-army ballot please press 4");
		System.out.print("your choice:");
		int ballotChoice = input.nextInt();
		System.out.println("");
		while (flag == false) {
			switch (ballotChoice) {
			case 1:
				ballotToPut = new GenericBallot<Citizen>(address, false, false, goverment.getAllParties());
				goverment.addBallot(ballotToPut);
				flag = true;
				break;

			case 2:
				ballotToPut = new GenericBallot<Citizen>(address, true, false, goverment.getAllParties());
				goverment.addBallot(ballotToPut);
				flag = true;
				break;

			case 3:
				ballotToPut = new GenericBallot<Soldier>(address, false, true, goverment.getAllParties());
				goverment.addBallot(ballotToPut);
				flag = true;
				break;

			case 4:
				ballotToPut = new GenericBallot<Soldier>(address, true, true, goverment.getAllParties());
				goverment.addBallot(ballotToPut);
				flag = true;
				break;

			default:
				System.out.print("please enter values between 1-4:");
				ballotChoice = input.nextInt();
				System.out.println("");

			}
			System.out.println("");
		}
	}

	// adding citizen
	@Override
	public void addCitizen() {
		Scanner input = new Scanner(System.in);
		String name, id;
		int days, monthes, years, selector, daysQuarantined = 0;
		boolean isValidInput = false, withProtectiveSuit = false, isInQuarantine = false;
		Date date = null;
		Citizen citizenToPut = null;
		Soldier soldierToPut = null;
		System.out.print("please enter citizen name:");
		name = input.nextLine();
		System.out.println("");
		System.out.println("please enter date of bitrth by the form dd/mm/yyyy");
		System.out.print("enter days:");
		days = input.nextInt();
		System.out.println("");
		System.out.print("enter monthes:");
		monthes = input.nextInt();
		System.out.println("");
		System.out.print("enter years:");
		years = input.nextInt();
		System.out.println("");
		while (!isValidInput) {
			try {
				date = new Date(days, monthes, years);

				isValidInput = true;
			} catch (DateException e) {
				System.out.println(e.getMessage());
				System.out.println("please enter again the date");
				System.out.print("enter days:");
				days = input.nextInt();
				System.out.println("");
				System.out.print("enter monthes:");
				monthes = input.nextInt();
				System.out.println("");
				System.out.print("enter years:");
				years = input.nextInt();
				System.out.println("");
			}
		}
		isValidInput = false;
		System.out.print("please enter id of the citizen:");
		id = input.next();
		System.out.println("");
		System.out.println("please enter if the citizen is quaratined: 1-yes 2-no");
		selector = 0;
		while (selector != 1 && selector != 2) {
			selector = input.nextInt();
			switch (selector) {
			case 1:
				System.out.print("please enter the number of days that the citizen has been quarantined:");
				daysQuarantined = input.nextInt();
				System.out.println("");
				isInQuarantine = true;
				System.out.println("does the citizen has a protective suit?: 1-yes 2-no");
				selector = 0;
				while (selector != 2 && selector != 1) {
					selector = input.nextInt();
					System.out.println("");
					switch (selector) {
					case 1:
						withProtectiveSuit = true;
						break;
					case 2:
						withProtectiveSuit = false;
						break;
					default:
						System.out.print("you must enter 1 or 2:");
						selector = input.nextInt();
						System.out.println("");
					}
				}
				break;
			case 2:
				isInQuarantine = false;
				withProtectiveSuit = false;
				break;
			default:
				System.out.println("you must enter 1 or 2");
				selector = input.nextInt();
				System.out.println("");
			}
		}
		while (!isValidInput) {
			try {
				citizenToPut = new Citizen(name, date, id, null, daysQuarantined, isInQuarantine, false,
						withProtectiveSuit);
				citizenToPut.isUnderEighteen(date);
				isValidInput = true;
				if (citizenToPut.isSoldier()) {
					citizenToPut = null;
					soldierToPut = new Soldier(name, date, id, null, daysQuarantined, isInQuarantine, false,
							withProtectiveSuit);
					goverment.addCitizen(soldierToPut);
					goverment.sortToBallots();
				} else {
					goverment.addCitizen(citizenToPut);
					goverment.sortToBallots();
				}
			} catch (IdException | unAdultException | InputMismatchException | MisMatchObjectException e) {
				if (e instanceof IdException) {
					System.out.print("please enter the id number properly:");
					id = input.next();
					System.out.println("");
				} else {
					System.out.println(e.getMessage());
					break;

				}
			}
		}
	}

	// adding party
	@Override
	public void addParty() {
		Scanner input = new Scanner(System.in);
		int days = 1, monthes = 1, years = 1900;
		boolean isValidInput = false;
		String name;
		Party partyToPut = null;
		Identity essence = null;
		Date date = null;

		System.out.print("please enter the name of the party:");
		name = input.nextLine();
		System.out.println("");
		System.out.println("please choose the identity of the party:");
		boolean flag2 = false;
		System.out.println("for left wing choose -1");
		System.out.println("for center choose -2");
		System.out.println("for right wing choose -3");
		System.out.print("your choice:");
		int identityChoice = input.nextInt();
		System.out.println("");
		while (flag2 == false) {
			switch (identityChoice) {
			case 1:
				essence = Identity.leftWing;
				flag2 = true;
				break;
			case 2:
				essence = Identity.Center;
				flag2 = true;
				break;
			case 3:
				essence = Identity.RightWing;
				flag2 = true;
				break;

			default:
				System.out.print("you must enter values between 1-3:");
				identityChoice = input.nextInt();
				System.out.println("");
				break;
			}

		}
		System.out.println("please enter date of establisment by the form dd/mm/yyyy");
		System.out.print("enter days:");
		days = input.nextInt();
		System.out.println("");
		System.out.print("enter monthes:");
		monthes = input.nextInt();
		System.out.println("");
		System.out.print("enter years:");
		years = input.nextInt();
		System.out.println("");
		while (!isValidInput) {
			try {
				date = new Date(days, monthes, years);
				isValidInput = true;
			} catch (DateException e) {
				System.out.println(e.getMessage());
				System.out.println("please enter again the date");
				System.out.print("enter days:");
				days = input.nextInt();
				System.out.println("");
				System.out.print("enter monthes:");
				monthes = input.nextInt();
				System.out.println("");
				System.out.print("enter years:");
				years = input.nextInt();
				System.out.println("");
			}
		}
		partyToPut = new Party(name, essence, date, null);
		goverment.addParty(partyToPut);
		System.out.println("");
	}

	// adding candidate
	@Override
	public void addCandidate() {
		Scanner input = new Scanner(System.in);
		int selector, days = 1, monthes = 1, years = 1900, daysQuarantined = 0;
		boolean isInQuarantine = false, withProtectiveSuit = false, isValidInput = false;
		String name, id;
		Candidate candidateToPut = null;
		Party partyToPut = null;
		Date date = null;
		System.out.print("please enter candidate name:");
		name = input.nextLine();
		System.out.println("");
		System.out.println("please enter date of bitrth by the form dd/mm/yyyy");
		System.out.print("enter days:");
		days = input.nextInt();
		System.out.println("");
		System.out.print("enter monthes:");
		monthes = input.nextInt();
		System.out.println("");
		System.out.print("enter years:");
		years = input.nextInt();
		System.out.println("");
		while (!isValidInput) {
			try {
				date = new Date(days, monthes, years);
				isValidInput = true;
			} catch (DateException e) {
				System.out.println(e.getMessage());
				System.out.println("please enter again the date");
				System.out.print("enter days:");
				days = input.nextInt();
				System.out.println("");
				System.out.print("enter monthes:");
				monthes = input.nextInt();
				System.out.println("");
				System.out.print("enter years:");
				years = input.nextInt();
				System.out.println("");
			}
		}
		isValidInput = false;
		System.out.print("please enter id of the citizen:");
		id = input.next();
		System.out.println("");
		System.out.println("please enter if the citizen is quaratined: 1-yes 2-no");
		selector = 0;
		while (selector != 1 && selector != 2) {
			selector = input.nextInt();
			switch (selector) {
			case 1:
				System.out.print("please enter the number of days that the citizen has been quarantined:");
				daysQuarantined = input.nextInt();
				System.out.println("");
				isInQuarantine = true;
				System.out.println("does the citizen has a protective suit?: 1-yes 2-no");
				selector = 0;
				while (selector != 2 && selector != 1) {
					selector = input.nextInt();
					System.out.println("");
					switch (selector) {
					case 1:
						withProtectiveSuit = true;
						break;
					case 2:
						withProtectiveSuit = false;
						break;
					default:
						System.out.print("you must enter 1 or 2:");
						selector = input.nextInt();
						System.out.println("");
					}
				}
				break;
			case 2:
				isInQuarantine = false;
				withProtectiveSuit = false;
				break;
			default:
				System.out.println("you must enter 1 or 2");
				selector = input.nextInt();
				System.out.println("");
			}
		}
		System.out.print("please enter number of votes that the candidate got in the primaries:");
		int votes = input.nextInt();
		System.out.println("");
		partyToPut = goverment.candidateChooseParty(input);
		while (!isValidInput) {
			try {
				candidateToPut = new Candidate(name, date, id, null, daysQuarantined, isInQuarantine, partyToPut, votes,
						false, withProtectiveSuit);
				candidateToPut.isUnderEighteen(date);
				isValidInput = true;
				goverment.addCandidate(candidateToPut, partyToPut);
				goverment.sortToBallots();
			} catch (IdException | unAdultException | MisMatchObjectException e) {
				if (e instanceof IdException) {
					System.out.print("please enter the id number properly:");
					id = input.next();
					System.out.println("");
				} else {
					System.out.println(e.getMessage());
					break;
				}
			}
		}
		System.out.println("");
	}

	// showing all ballots information
	@Override
	public void showAllBallots() {
		goverment.showAllBallots();
		System.out.println("");
	}

	// showing all citizens information
	@Override
	public void showAllCitizens() {
		goverment.showAllCitizens();
		System.out.println("");
	}

	//// showing all parties information
	@Override
	public void showAllParties() {
		goverment.showAllParties();
		System.out.println("");
	}

	// Initiating election process
	@Override
	public void elect() {
		Scanner input = new Scanner(System.in);
		goverment.elect(input);

	}

	// showing results of the elections
	@Override
	public void showResultsOfTheElections() {
		goverment.showResultsOfTheElections();
	}

	// saving information of the last round of elections
	@Override
	public void save() {
		try {
			goverment.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
