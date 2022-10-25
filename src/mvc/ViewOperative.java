package mvc;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Vector;
import javax.swing.JOptionPane;
import ballot.GenericBallot;
import citizen.Candidate;
import citizen.Citizen;
import citizen.Soldier;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import party.Date;
import party.Identity;
import party.Party;

public class ViewOperative implements AbstractElectionsView, Serializable {
	private Vector<ViewListener> allListeners = new Vector<ViewListener>();
	private transient Stage stage;
	private transient GridPane gpRoot = new GridPane();
	private Vector<Integer> days = new Vector<Integer>();
	private Vector<Integer> month = new Vector<Integer>();
	private Vector<Integer> year = new Vector<Integer>();
	private Vector<Integer> daysForCorona = new Vector<Integer>();
	private LocalDateTime timeAndDate = LocalDateTime.now();

	public ViewOperative(Stage theStage) {
		this.stage = theStage;
		theStage.setTitle("Elections System");
		gpRoot.setPadding(new Insets(10));
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		this.openingScreen();
		stage.setScene(new Scene(gpRoot, 650, 400));
		stage.show();
		this.initializeDates();
	}

	void openingScreen() {
		Label welcome = new Label("Welcome to the Elections system!");
		welcome.setTextFill(Color.BLUE);
		welcome.setFont(new Font("Ariel", 40));
		gpRoot.add(welcome, 0, 0);
	}

	void opReadFile() {
		connectToNewScene(1000, 1000);
		this.read();
	}

	void opAddBallot() {
		connectToNewScene(600, 200);
		Label addressLabel = new Label("ballot address:");
		TextField text = new TextField();
		Label noteLabel = new Label("please mark the type of the ballot");
		RadioButton regularBallot = new RadioButton("regular ballot");
		RadioButton coronaBallot = new RadioButton("corona ballot");
		RadioButton armyBallot = new RadioButton("army ballot");
		Button addBtn = new Button("add");
		gpRoot.add(addressLabel, 0, 0);
		gpRoot.add(text, 1, 0);
		gpRoot.add(noteLabel, 0, 1);
		gpRoot.addRow(2, regularBallot, coronaBallot, armyBallot);
		gpRoot.add(addBtn, 1, 3);
		regularBallot.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (regularBallot.isSelected()) {
					coronaBallot.setSelected(false);
					armyBallot.setSelected(false);
				}
			}
		});
		coronaBallot.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (coronaBallot.isSelected()) {
					regularBallot.setSelected(false);
				}
			}
		});
		armyBallot.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (armyBallot.isSelected()) {
					regularBallot.setSelected(false);
				}
			}
		});
		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (coronaBallot.isSelected()) {
					if (armyBallot.isSelected()) {
						GenericBallot<Soldier> ballotToPut = new GenericBallot<Soldier>(text.getText(), true, true,
								allListeners.get(0).viewGetParty());
						addBallot(ballotToPut);
					} else {
						GenericBallot<Citizen> ballotToPut = new GenericBallot<Citizen>(text.getText(), true, false,
								allListeners.get(0).viewGetParty());
						addBallot(ballotToPut);
					}
				} else {
					if (armyBallot.isSelected()) {
						GenericBallot<Soldier> ballotToPut = new GenericBallot<Soldier>(text.getText(), false, true,
								allListeners.get(0).viewGetParty());
						addBallot(ballotToPut);
					} else {
						GenericBallot<Citizen> ballotToPut = new GenericBallot<Citizen>(text.getText(), false, false,
								allListeners.get(0).viewGetParty());
						addBallot(ballotToPut);
					}
				}
			}

		});
	}

	void opAddCitizen() {
		connectToNewScene(750, 300);
		Label nameLbl = new Label("name:");
		TextField textName = new TextField();
		Label idLbl = new Label("id:");
		TextField textId = new TextField();
		Label dateLbl = new Label("date:");
		Label daysLbl = new Label("days:");
		ComboBox<Integer> daysCmb = new ComboBox<Integer>();
		Label monthLbl = new Label("month:");
		ComboBox<Integer> monthCmb = new ComboBox<Integer>();
		Label yearLbl = new Label("year:");
		ComboBox<Integer> yearCmb = new ComboBox<Integer>();
		// put here the options for combo boxes
		daysCmb.getItems().addAll(days);
		monthCmb.getItems().addAll(month);
		yearCmb.getItems().addAll(year);
		Label noteLabel = new Label("person has corona");
		CheckBox hasCorona = new CheckBox("has corona");
		RadioButton hasProtectiveSuitRdo = new RadioButton("has protective suit");
		ComboBox<Integer> daysInQuarantineCmb = new ComboBox<Integer>();
		daysInQuarantineCmb.getItems().addAll(daysForCorona);
		Button addBtn = new Button("add");
		gpRoot.addRow(0, nameLbl, textName);
		gpRoot.addRow(1, idLbl, textId);
		gpRoot.add(dateLbl, 0, 2);
		gpRoot.addRow(3, daysLbl, daysCmb, monthLbl, monthCmb, yearLbl, yearCmb);
		gpRoot.add(noteLabel, 0, 4);
		gpRoot.add(hasCorona, 0, 5);
		gpRoot.addRow(5, daysInQuarantineCmb, hasProtectiveSuitRdo);
		gpRoot.add(addBtn, 1, 6);
		daysInQuarantineCmb.setVisible(false);
		hasProtectiveSuitRdo.setVisible(false);
		hasCorona.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (hasCorona.isSelected()) {
					daysInQuarantineCmb.setVisible(true);
					hasProtectiveSuitRdo.setVisible(true);
				} else {
					daysInQuarantineCmb.setVisible(false);
					hasProtectiveSuitRdo.setVisible(false);
				}
			}
		});
		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Citizen person = new Citizen(textName.getText(),
							new Date(daysCmb.getValue(), monthCmb.getValue(), yearCmb.getValue()), textId.getText(),
							null, 0, false, false, false);
					person.isUnderEighteen(person.getDateOfBirth());
					if (person.isSoldier()) {
						if (hasCorona.isSelected()) {
							Soldier soldier = new Soldier(textName.getText(),
									new Date(daysCmb.getValue(), monthCmb.getValue(), yearCmb.getValue()),
									textId.getText(), null, daysInQuarantineCmb.getValue(), true, false,
									hasProtectiveSuitRdo.isSelected());
							addCitizen(soldier);
						} else {
							Soldier soldier = new Soldier(textName.getText(),
									new Date(daysCmb.getValue(), monthCmb.getValue(), yearCmb.getValue()),
									textId.getText(), null, 0, false, false, false);
							addCitizen(soldier);
						}
					} else {
						if (hasCorona.isSelected()) {
							person = new Citizen(textName.getText(),
									new Date(daysCmb.getValue(), monthCmb.getValue(), yearCmb.getValue()),
									textId.getText(), null, daysInQuarantineCmb.getValue(), true, false,
									hasProtectiveSuitRdo.isSelected());
							addCitizen(person);
						} else {
							person = new Citizen(textName.getText(),
									new Date(daysCmb.getValue(), monthCmb.getValue(), yearCmb.getValue()),
									textId.getText(), null, 0, false, false, false);
							addCitizen(person);
						}
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}

		});
	}

	void opAddParty() {
		connectToNewScene(800, 250);
		Label nameLbl = new Label("party name:");
		TextField text = new TextField();
		Label noteLabel = new Label("please mark the essence of the party:");
		RadioButton rightWingRdo = new RadioButton("right-wing");
		RadioButton centerRdo = new RadioButton("center");
		RadioButton leftWingRdo = new RadioButton("left-wing");
		Label dateLbl = new Label("date of establishment:");
		Label daysLbl = new Label("days:");
		ComboBox<Integer> daysCmb = new ComboBox<Integer>();
		Label monthLbl = new Label("month:");
		ComboBox<Integer> monthCmb = new ComboBox<Integer>();
		Label yearLbl = new Label("year:");
		ComboBox<Integer> yearCmb = new ComboBox<Integer>();
		daysCmb.getItems().addAll(days);
		monthCmb.getItems().addAll(month);
		yearCmb.getItems().addAll(year);
		Button addBtn = new Button("add");
		gpRoot.add(nameLbl, 0, 0);
		gpRoot.add(text, 1, 0);
		gpRoot.add(noteLabel, 0, 1);
		gpRoot.addRow(2, rightWingRdo, centerRdo, leftWingRdo);
		gpRoot.add(dateLbl, 0, 3);
		gpRoot.addRow(4, daysLbl, daysCmb, monthLbl, monthCmb, yearLbl, yearCmb);
		gpRoot.add(addBtn, 1, 5);
		rightWingRdo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				centerRdo.setSelected(false);
				leftWingRdo.setSelected(false);
			}
		});
		centerRdo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				rightWingRdo.setSelected(false);
				leftWingRdo.setSelected(false);
			}
		});
		leftWingRdo.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				rightWingRdo.setSelected(false);
				centerRdo.setSelected(false);
			}
		});
		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				Party party;
				try {
					if (rightWingRdo.isSelected()) {
						party = new Party(text.getText(), Identity.RightWing,
								new Date(daysCmb.getValue(), monthCmb.getValue(), yearCmb.getValue()), null);
						addParty(party);
					}
					if (centerRdo.isSelected()) {
						party = new Party(text.getText(), Identity.Center,
								new Date(daysCmb.getValue(), monthCmb.getValue(), yearCmb.getValue()), null);
						addParty(party);
					}
					if (leftWingRdo.isSelected()) {
						party = new Party(text.getText(), Identity.leftWing,
								new Date(daysCmb.getValue(), monthCmb.getValue(), yearCmb.getValue()), null);
						addParty(party);
					}
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage());
				}
			}
		});

	}

	void opAddCandidate() {
		connectToNewScene(750, 400);
		Label nameLbl = new Label("name:");
		TextField textName = new TextField();
		Label idLbl = new Label("id:");
		TextField textId = new TextField();
		Label dateLbl = new Label("date:");
		Label daysLbl = new Label("days:");
		ComboBox<Integer> daysCmb = new ComboBox<Integer>();
		Label monthLbl = new Label("month:");
		ComboBox<Integer> monthCmb = new ComboBox<Integer>();
		Label yearLbl = new Label("year:");
		ComboBox<Integer> yearCmb = new ComboBox<Integer>();
		daysCmb.getItems().addAll(days);
		monthCmb.getItems().addAll(month);
		yearCmb.getItems().addAll(year);
		Label runForLbl = new Label("running for:");
		Vector<String> partyNames = new Vector<String>();
		for (int i = 0; i < allListeners.get(0).viewGetParty().size(); i++) {
			partyNames.add(allListeners.get(0).viewGetParty().get(i).getName());
		}
		ComboBox<String> partiesCmb = new ComboBox<String>();
		partiesCmb.getItems().addAll(partyNames);
		Label numVotesLbl = new Label("number of votes:");
		TextField votesTxt = new TextField();
		Label noteLabel = new Label("person has corona");
		CheckBox hasCorona = new CheckBox("has corona");
		RadioButton hasProtectiveSuitRdo = new RadioButton("has protective suit");
		ComboBox<Integer> daysInQuarantineCmb = new ComboBox<Integer>();
		daysInQuarantineCmb.getItems().addAll(daysForCorona);
		Button addBtn = new Button("add");
		gpRoot.addRow(0, nameLbl, textName);
		gpRoot.addRow(1, idLbl, textId);
		gpRoot.add(dateLbl, 0, 2);
		gpRoot.addRow(3, daysLbl, daysCmb, monthLbl, monthCmb, yearLbl, yearCmb);
		gpRoot.add(noteLabel, 0, 4);
		gpRoot.addRow(5, runForLbl, partiesCmb);
		gpRoot.addRow(6, numVotesLbl, votesTxt);
		gpRoot.add(hasCorona, 0, 7);
		gpRoot.addRow(7, daysInQuarantineCmb, hasProtectiveSuitRdo);
		gpRoot.add(addBtn, 1, 8);
		daysInQuarantineCmb.setVisible(false);
		hasProtectiveSuitRdo.setVisible(false);
		hasCorona.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (hasCorona.isSelected()) {
					daysInQuarantineCmb.setVisible(true);
					hasProtectiveSuitRdo.setVisible(true);
				} else {
					daysInQuarantineCmb.setVisible(false);
					hasProtectiveSuitRdo.setVisible(false);
				}
			}
		});
		addBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				try {
					Party partyChoice = null;
					for (int i = 0; i < allListeners.get(0).viewGetParty().size(); i++) {
						if (partiesCmb.getValue().equals(allListeners.get(0).viewGetParty().get(i).getName())) {
							partyChoice = allListeners.get(0).viewGetParty().get(i);
						}
					}
					Candidate person = new Candidate(textName.getText(),
							new Date(daysCmb.getValue(), monthCmb.getValue(), yearCmb.getValue()), textId.getText(),
							null, 0, false, partyChoice, Integer.parseInt(votesTxt.getText()), false, false);
					person.isUnderEighteen(person.getDateOfBirth());
					if (person.isSoldier()) {
						JOptionPane.showMessageDialog(null, "Soldier can not run as candidate for party!");
					} else {
						if (hasCorona.isSelected()) {
							person = new Candidate(textName.getText(),
									new Date(daysCmb.getValue(), monthCmb.getValue(), yearCmb.getValue()),
									textId.getText(), null, daysInQuarantineCmb.getValue(), true, partyChoice,
									Integer.parseInt(votesTxt.getText()), false, hasProtectiveSuitRdo.isSelected());
							addCandidate(person);
						} else {
							person = new Candidate(textName.getText(),
									new Date(daysCmb.getValue(), monthCmb.getValue(), yearCmb.getValue()),
									textId.getText(), null, 0, false, partyChoice, Integer.parseInt(votesTxt.getText()),
									false, false);
							addCandidate(person);
						}
					}
				} catch (Exception e) {
					if (e instanceof NullPointerException) {
						JOptionPane.showMessageDialog(null, "please select party!");
					} else {
						if (e instanceof NumberFormatException) {
							JOptionPane.showMessageDialog(null, "please enter number of votes!");
						} else {
							JOptionPane.showMessageDialog(null, e.getMessage());
						}
					}
				}
			}

		});

	}

	void opShowAllBallots() {
		connectToNewScene(1200, 450);
		this.showAllBallots();
	}

	void opShowAllCitizens() {
		connectToNewScene(1200, 450);
		this.showAllCitizens();
	}

	void opShowAllParties() {
		connectToNewScene(1000, 400);
		this.showAllParties();
	}

	void opVote() {
		connectToNewScene(800, 250);
		Label msgLbl = new Label("the citizens who did not vote:");
		Vector<Citizen> tempList = allListeners.get(0).viewGetSetCitizens().getData();
		Vector<String> citizensNames = new Vector<String>();
		Vector<String> citizensId = new Vector<String>();
		Vector<Party> partyList = allListeners.get(0).viewGetParty();
		ComboBox<String> partyNamesCmb = new ComboBox<String>();
		Map<String, String> voters = new LinkedHashMap<String, String>();
		for (int i = 0; i < tempList.size(); i++) {
			if (!tempList.get(i).getHasAlreadyVoted()) {
				if (tempList.get(i).getIsInQuarantine()) {
					if (tempList.get(i).getWithProtectiveSuit()) {
						citizensNames.add(tempList.get(i).getName());
						citizensId.add(tempList.get(i).getIdNumber());
					}
				} else {
					citizensNames.add(tempList.get(i).getName());
					citizensId.add(tempList.get(i).getIdNumber());
				}
			}
		}
		for (int i = 0; i < partyList.size(); i++) {
			if (partyList.get(i).getCandidateList() != null) {
				partyNamesCmb.getItems().add(partyList.get(i).getName());
			}
		}
		ComboBox<String> namesCmb = new ComboBox<String>();
		namesCmb.getItems().addAll(citizensNames);
		Label noteLabel = new Label();
		Button confirmBtn = new Button("confirm");
		Button abortBtn = new Button("abort");
		Button finishBtn = new Button("finish");
		Label indicationlbl = new Label("the votes has been updated!");
		indicationlbl.setTextFill(Color.RED);
		gpRoot.add(msgLbl, 0, 0);
		gpRoot.add(namesCmb, 0, 1);
		gpRoot.add(noteLabel, 0, 2);
		gpRoot.add(partyNamesCmb, 0, 3);
		gpRoot.addRow(4, confirmBtn, abortBtn);
		gpRoot.addRow(5, finishBtn, indicationlbl);
		noteLabel.setVisible(false);
		confirmBtn.setVisible(false);
		partyNamesCmb.setVisible(false);
		abortBtn.setVisible(false);
		indicationlbl.setVisible(false);
		abortBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				namesCmb.setValue(null);
				partyNamesCmb.setValue(null);
				noteLabel.setVisible(false);
				partyNamesCmb.setVisible(false);
				confirmBtn.setVisible(false);
				abortBtn.setVisible(false);
			}
		});
		confirmBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if (namesCmb.getValue() != null && partyNamesCmb.getValue() != null) {
					int index = namesCmb.getItems().indexOf(namesCmb.getValue());
					for (int i = 0; i < citizensId.size(); i++) {
						if (index == i) {
							voters.put(citizensId.get(i), partyNamesCmb.getValue());
							namesCmb.getItems().set(i, "----------");
						}
					}
					namesCmb.setValue(null);
					partyNamesCmb.setValue(null);
					noteLabel.setVisible(false);
					partyNamesCmb.setVisible(false);
					abortBtn.setVisible(false);
					confirmBtn.setVisible(false);
				}
			}
		});
		namesCmb.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				noteLabel.setText("hello " + namesCmb.getValue() + " please select party to vote");
				noteLabel.setVisible(true);
				partyNamesCmb.setVisible(true);
				confirmBtn.setVisible(true);
				abortBtn.setVisible(true);
			}
		});

		finishBtn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				indicationlbl.setVisible(true);
				vote(voters);
			}
		});
	}

	void opShowElectionsResults() {
		connectToNewScene(1000, 700);
		this.showElectionsResults();
	}

	void opSave() {
		save();
	}

	void opExit() {
		opSave();
		stage.close();
	}

	//this method initialize dates
	void initializeDates() {
		for (int i = 1; i <= 31; i++) {
			days.add(i);
		}
		for (int i = 1; i <= 12; i++) {
			month.add(i);
		}
		for (int i = timeAndDate.getYear(); i >= 1900; i--) {
			year.add(i);
		}
		for (int i = 0; i <= 14; i++) {
			daysForCorona.add(i);
		}
	}

	//this method switch scenes
	void connectToNewScene(double width, double height) {
		gpRoot = new GridPane();
		gpRoot.setPadding(new Insets(10));
		gpRoot.setHgap(10);
		gpRoot.setVgap(10);
		stage.setScene(new Scene(gpRoot, width, height));
		stage.show();
	}

	//this nethod connect view to listener
	@Override
	public void registerListener(ViewListener listener) {
		allListeners.add(listener);
	}

	@Override
	public void read() {
		Label lastResultsInfo = new Label(allListeners.get(0).viewShowLastElectionsResults());
		ScrollPane scroller = new ScrollPane();
		scroller.setContent(lastResultsInfo);
		gpRoot.add(lastResultsInfo, 0, 0);
		gpRoot.getChildren().add(scroller);
	}

	@Override
	public void addBallot(GenericBallot<?> ballot) {
		for (ViewListener listener : allListeners) {
			listener.viewAddBallot(ballot);
		}
	}

	@Override
	public void addCitizen(Citizen person) {
		for (ViewListener listener : allListeners) {
			listener.viewAddCitizen(person);
		}
	}

	@Override
	public void addParty(Party party) {
		for (ViewListener listener : allListeners) {
			listener.viewAddParty(party);
		}
	}

	@Override
	public void addCandidate(Candidate person) {
		for (ViewListener listener : allListeners) {
			listener.viewAddCandidate(person, person.getParty());
		}
	}

	@Override
	public void showAllBallots() {
		Label ballotsInfo = new Label(allListeners.get(0).viewShowAllBallotsInformation());
		ScrollPane scroller = new ScrollPane();
		scroller.setContent(ballotsInfo);
		gpRoot.add(ballotsInfo, 0, 0);
		gpRoot.getChildren().add(scroller);
	}

	@Override
	public void showAllCitizens() {
		Label citizensInfo = new Label(allListeners.get(0).viewShowAllCitizensInformation());
		ScrollPane scroller = new ScrollPane();
		scroller.setContent(citizensInfo);
		gpRoot.add(citizensInfo, 0, 0);
		gpRoot.getChildren().add(scroller);
	}

	@Override
	public void showAllParties() {
		Label partiesInfo = new Label(allListeners.get(0).viewShowAllPartiesInformation());
		ScrollPane scroller = new ScrollPane();
		scroller.setContent(partiesInfo);
		gpRoot.add(partiesInfo, 0, 0);
		gpRoot.getChildren().add(scroller);
	}

	@Override
	public void vote(Map<String, String> listOfVoters) {
		for (ViewListener listener : allListeners) {
			listener.viewVote(listOfVoters);
		}
	}

	@Override
	public void showElectionsResults() {
		Label electionResLbl = new Label(allListeners.get(0).viewShowElectionsResults());
		ScrollPane scroller = new ScrollPane();
		scroller.setContent(electionResLbl);
		gpRoot.add(electionResLbl, 0, 0);
		gpRoot.getChildren().add(scroller);

	}

	@Override
	public void save() {
		for (ViewListener listener : allListeners) {
			listener.save();
		}

	}

}
