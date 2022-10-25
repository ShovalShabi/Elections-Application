package elections;

public interface ElectionsView {
	void read();
	void addBallot();
	void addCitizen();
	void addParty();
	void addCandidate();
	void showAllBallots();
	void showAllCitizens();
	void showAllParties();
	void elect();
	void showResultsOfTheElections();
	void save();

}
