package mvc;

import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.Vector;
import javax.swing.JOptionPane;
import ballot.GenericBallot;
import citizen.Candidate;
import citizen.Citizen;
import citizen.MySet;
import elections.Elections;
import party.Party;

public class Controller implements ElectionsListener, ViewListener, Serializable {
	private Elections goverment;
	private AbstractElectionsView view;

	public Controller(Elections goverment, AbstractElectionsView view) {
		this.goverment = goverment;
		this.view = view;
		this.goverment.registerListener(this);
		this.view.registerListener(this);
	}

	@Override
	public String viewShowLastElectionsResults() {
		return modelShowLastElectionsResults();
	}

	@Override
	public void viewAddBallot(GenericBallot<?> ballot) {
		goverment.addBallot(ballot);

	}

	@Override
	public void viewAddCitizen(Citizen person) {
		try {
			goverment.addCitizen(person);
			goverment.sortToBallots();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	@Override
	public void viewAddParty(Party party) {
		goverment.addParty(party);
	}

	@Override
	public void viewAddCandidate(Candidate person, Party party) {
		try {
			goverment.addCandidate(person, party);
			goverment.sortToBallots();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}
	}

	@Override
	public String viewShowAllBallotsInformation() {
		return modelShowAllBallotsInformation();

	}

	@Override
	public String viewShowAllCitizensInformation() {
		return modelShowAllCitizensInformation();

	}

	@Override
	public String viewShowAllPartiesInformation() {
		return modelShowAllPartiesInformation();
	}

	@Override
	public void viewVote(Map<String, String> voters) {
		modelElect(voters);
	}

	@Override
	public String viewShowElectionsResults() {
		return modelShowElectionsResults();
	}

	@Override
	public Vector<Party> viewGetParty() {
		return goverment.getAllParties();
	}

	@Override
	public MySet<Citizen> viewGetSetCitizens() {
		return goverment.getAllCitizens();
	}

///////////////////////////////////////////////////////////////// MODEL////////////////////////////////////////////////
	@Override
	public String modelShowLastElectionsResults() {
		try {
			return (goverment.readLastResults());
		} catch (ClassNotFoundException | IOException e) {
			JOptionPane.showMessageDialog(null, "you need to save the data at least for the first time!");
			return ("you need to save the data at least for the first time!");
		}
	}

	@Override
	public String modelShowAllBallotsInformation() {
		return goverment.allBallotsInfo();
	}

	@Override
	public String modelShowAllCitizensInformation() {
		return goverment.allCitizensInfo();
	}

	@Override
	public String modelShowAllPartiesInformation() {
		return goverment.allPartiesInfo();
	}

	@Override
	public void modelElect(Map<String, String> voters) {
		goverment.voteFromView(voters);
	}

	@Override
	public String modelShowElectionsResults() {
		return goverment.showResultsOfTheElectionsForView();
	}

	@Override
	public void save() {
		try {
			goverment.save();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
