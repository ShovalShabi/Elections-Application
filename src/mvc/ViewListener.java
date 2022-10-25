package mvc;

import java.util.Map;
import java.util.Vector;
import ballot.GenericBallot;
import citizen.Candidate;
import citizen.Citizen;
import citizen.MySet;
import party.Party;

public interface ViewListener {
	String viewShowLastElectionsResults();
	String viewShowAllBallotsInformation();
	String viewShowAllCitizensInformation();
	String viewShowAllPartiesInformation();
	void viewAddBallot(GenericBallot<?> ballot);
	void viewAddCitizen(Citizen person);
	void viewAddCandidate(Candidate person, Party party);
	void viewAddParty(Party party);
	void viewVote(Map<String,String> voters);
	String viewShowElectionsResults();
	void save();
	Vector<Party> viewGetParty();
	MySet<Citizen> viewGetSetCitizens();
}
