package mvc;

import java.util.Map;
import ballot.GenericBallot;
import citizen.Candidate;
import citizen.Citizen;
import party.Party;

public interface AbstractElectionsView {
	void registerListener(ViewListener listener);
	void read();
	void addBallot(GenericBallot<?> ballot);
	void addCitizen(Citizen person);
	void addParty(Party party);
	void addCandidate(Candidate person);
	void showAllBallots();
	void showAllCitizens();
	void  showAllParties();
	void vote(Map<String, String> listOfVoters);
	void showElectionsResults();
	void save();
}


