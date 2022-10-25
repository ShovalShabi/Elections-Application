package mvc;

import java.util.Vector;

import ballot.GenericBallot;
import citizen.Candidate;
import citizen.Citizen;
import citizen.IdException;
import citizen.MisMatchObjectException;
import citizen.MySet;
import citizen.Soldier;
import elections.Elections;
import javafx.application.Application;
import javafx.stage.Stage;
import party.Date;
import party.DateException;
import party.Identity;
import party.Party;;

public class MainJavaFx extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
/////////////////////////////////////// start of hard-coded part ///////////////////////////////////////////////////////
		Elections goverment;
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
			goverment = new Elections(allCitizens, partyList, totalBallots);
			goverment.sortToBallots();
////////////////////////////////end of hard-coded part//////////////////////////////////////////////////////////////////
		} catch (DateException | IdException | MisMatchObjectException e) {
			System.out.println(e.getMessage());
			return;
		}
		ViewMenu menu = new ViewMenu(primaryStage);
		Controller controller = new Controller(goverment, menu.getOperative());
	}
}
