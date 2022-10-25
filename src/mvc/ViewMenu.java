package mvc;

import java.io.Serializable;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ViewMenu implements Serializable {
	private CheckBox readingFile = new CheckBox("To show last elections results");
	private CheckBox checkBallot = new CheckBox("Add ballot");
	private CheckBox checkCitizen = new CheckBox("Add citizen");
	private CheckBox checkParty = new CheckBox("Add party");
	private CheckBox checkCandidate = new CheckBox("Add candidate");
	private CheckBox checkShowBallots = new CheckBox("Show ballots information");
	private CheckBox checkShowCitizens = new CheckBox("Show citizens information");
	private CheckBox checkShowParties = new CheckBox("Show parties information");
	private CheckBox checkVote = new CheckBox("Vote");
	private CheckBox checkExit = new CheckBox("Exit");
	private CheckBox checkShowElectionsResults = new CheckBox("Show elections results");
	private ViewOperative operative;
	private CheckBox lastMarked = checkExit;
	
//this view is meant to be menu and operates the operative view 
	public ViewMenu(Stage theStage) {
		theStage.setTitle("Elections System");
		GridPane gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		this.operative = new ViewOperative(new Stage());
		readingFile.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				unMark(lastMarked);
				operative.opReadFile();
				lastMarked = readingFile;
			}
		});
		checkBallot.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				unMark(lastMarked);
				operative.opAddBallot();
				lastMarked = checkBallot;
			}
		});
		checkCitizen.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				unMark(lastMarked);
				operative.opAddCitizen();
				lastMarked = checkCitizen;
			}
		});
		checkParty.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				unMark(lastMarked);
				operative.opAddParty();
				lastMarked = checkParty;
			}
		});
		checkCandidate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				unMark(lastMarked);
				operative.opAddCandidate();
				lastMarked = checkCandidate;
			}
		});
		checkShowBallots.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				unMark(lastMarked);
				operative.opShowAllBallots();
				lastMarked = checkShowBallots;
			}
		});
		checkShowCitizens.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				unMark(lastMarked);
				operative.opShowAllCitizens();
				lastMarked = checkShowCitizens;
			}
		});
		checkShowParties.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				unMark(lastMarked);
				operative.opShowAllParties();
				lastMarked = checkShowParties;
			}
		});
		checkVote.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				unMark(lastMarked);
				operative.opVote();
				lastMarked = checkVote;
			}
		});
		checkShowElectionsResults.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				unMark(lastMarked);
				operative.opShowElectionsResults();
				lastMarked = checkShowElectionsResults;
			}
		});
		checkExit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent action) {
				operative.opExit();
				theStage.close();
			}
		});
		gpRoot.add(readingFile, 0, 1);
		gpRoot.add(checkBallot, 0, 2);
		gpRoot.add(checkCitizen, 0, 3);
		gpRoot.add(checkParty, 0, 4);
		gpRoot.add(checkCandidate, 0, 5);
		gpRoot.add(checkShowBallots, 0, 6);
		gpRoot.add(checkShowCitizens, 0, 7);
		gpRoot.add(checkShowParties, 0, 8);
		gpRoot.add(checkVote, 0, 9);
		gpRoot.add(checkShowElectionsResults, 0, 10);
		gpRoot.add(checkExit, 0, 11);
		theStage.setScene(new Scene(gpRoot, 350, 400));
		theStage.show();
	}

	public void unMark(CheckBox check) {
		check.setSelected(false);
	}

	public ViewOperative getOperative() {
		return operative;
	}

}
