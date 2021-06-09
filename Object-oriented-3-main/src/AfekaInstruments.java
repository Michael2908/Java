
//Name: Michael Sieradzki	ID: 204256093
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class AfekaInstruments extends Application {
	static int counter = 0;
	boolean leftPressed = false, rightPressed = false, toSearch = false;
	static Button btn1, btn, btn2, btn3, left, right, btn4;
	static TextField fd, fd1, fd2, fd3, fd4, fd5, fd6;
	static Label l1, l2, l3, l4, l5, l6, l7;
	HBox botTop, top;
	static VBox bot, newWindow, box1;
	static GridPane mid, gp;
	static Stage window, stage;
	static ComboBox<String> combo, combo1, combo2;
	static CheckBox cb;
	BorderPane root = new BorderPane();
	ArrayList<MusicalInstrument> allInstruments = new ArrayList<MusicalInstrument>(), arr,
			arr2 = new ArrayList<MusicalInstrument>();
	File file;
	MenuUI menu = new MenuUI();

	@Override
	public void start(Stage primaryStage) {
		window = primaryStage;
		file = getInstrumentsFileFromUser();
		loadInstrumentsFromFile(file, allInstruments);
		try {
			arr2 = allInstruments;
			Scene scene = new Scene(setBorderPane(), 900, 400);
			window.setTitle("Afeka Instrument Music Store");
			window.setScene(scene);
			window.show();
			right.setOnAction(e -> {
				rightPressed();
			});
			left.setOnAction(e -> {
				leftPressed();
			});
			menu.getDelete().setOnAction(e -> {
				deletePressed();
			});
			menu.getAdd().setOnAction(e -> {
				AddPressed();
			});
			menu.getClear().setOnAction(e -> {
				clearPressed();
			});
			root.setOnKeyPressed(e -> {
				keyBoardInput(e);
			});
			menu.btn.setOnAction(e -> {
				arr2 = searchInstrument();
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
		PrintToConsole();
	}

	public void PrintToConsole() {
		printInstruments(allInstruments);
		int different = getNumOfDifferentElements(allInstruments);
		System.out.println("\n\nDifferent Instruments: " + different);
		MusicalInstrument mostExpensive = getMostExpensiveInstrument(allInstruments);
		System.out.println("\n\nMost Expensive Instrument:\n" + mostExpensive + "\n");
	}

	public Parent setBorderPane() {
		root.setPadding(new Insets(10, 10, 10, 10));
		root.setBottom(menu.setBot());
		root.setCenter(menu.setMid());
		root.setLeft(left = new Button("<"));
		root.setRight(right = new Button(">"));
		BorderPane.setAlignment(right, Pos.CENTER_LEFT);
		BorderPane.setAlignment(left, Pos.CENTER_RIGHT);
		root.setTop(menu.setTop());
		return root;
	}

	public static void nextInstruments(ArrayList<MusicalInstrument> instruments, TextField field1, TextField field2,
			TextField field3) {
		String str2 = instruments.get(counter).toString();
		str2 = str2.replaceAll(",", " ");
		String[] token = str2.split("\\s+");
		field1.setText(token[1]);
		field2.setText(token[0]);
		field3.setText(token[4]);
	}

	public void keyBoardInput(KeyEvent e) {
		switch (e.getCode()) {
		case ENTER:
			arr2 = searchInstrument();
			break;
		case A:
			AddPressed();
			break;
		case DELETE:
			deletePressed();
			break;
		case LEFT:
			leftPressed();
			break;
		case RIGHT:
			rightPressed();
			break;
		default:
			break;
		}
	}

	public ArrayList<MusicalInstrument> searchInstrument() {
		arr = new ArrayList<MusicalInstrument>();
		if (menu.fd.getText().isEmpty()) {
			toSearch = false;
			return allInstruments;
		} else {
			String str = menu.fd.getText().toLowerCase();
			menu.fd1.clear();
			menu.fd2.clear();
			menu.fd3.clear();
			arr.removeAll(allInstruments);
			for (int i = 0; i < allInstruments.size(); i++) {
				if (getAllInstruments().get(i).toString().toLowerCase().contains(str)) {
					arr.add(allInstruments.get(i));
				}
			}
			toSearch = true;
			return arr;
		}

	}

	public static File getInstrumentsFileFromUser() {
		boolean stopLoop = true;
		File file;
		String filepath = null;
		do {
			TextInputDialog dialog = new TextInputDialog();
			dialog.setTitle("Confirmation");
			dialog.setHeaderText("Load Intruments From File");
			dialog.setContentText("Please Enter File Name:");
			Optional<String> result = dialog.showAndWait();
			if (result.isPresent())
				filepath = result.get();
			else
				System.exit(1);
			file = new File(filepath);
			stopLoop = file.exists() && file.canRead();
			if (!stopLoop) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Error");
				alert.setHeaderText("File Error");
				alert.setContentText("Cannot read from file, please try again");
				alert.showAndWait();
			}
		} while (!stopLoop);

		return file;
	}

	public static void loadInstrumentsFromFile(File file, ArrayList<MusicalInstrument> allInstruments) {
		Scanner scanner = null;
		try {
			scanner = new Scanner(file);
			addAllInstruments(allInstruments, loadGuitars(scanner));
			addAllInstruments(allInstruments, loadBassGuitars(scanner));
			addAllInstruments(allInstruments, loadFlutes(scanner));
			addAllInstruments(allInstruments, loadSaxophones(scanner));
		} catch (FileNotFoundException ex) {
			System.err.println("\nFile Error! File was not found");
			System.exit(2);
		} finally {
			scanner.close();
		}
	}

	public static ArrayList<Guitar> loadGuitars(Scanner scanner) {
		int numOfInstruments = scanner.nextInt();
		ArrayList<Guitar> guitars = new ArrayList<Guitar>(numOfInstruments);
		for (int i = 0; i < numOfInstruments; i++)
			guitars.add(new Guitar(scanner));
		return guitars;
	}

	public static ArrayList<Bass> loadBassGuitars(Scanner scanner) {
		int numOfInstruments = scanner.nextInt();
		ArrayList<Bass> bassGuitars = new ArrayList<Bass>(numOfInstruments);
		for (int i = 0; i < numOfInstruments; i++)
			bassGuitars.add(new Bass(scanner));
		return bassGuitars;
	}

	public static ArrayList<Flute> loadFlutes(Scanner scanner) {
		int numOfInstruments = scanner.nextInt();
		ArrayList<Flute> flutes = new ArrayList<Flute>(numOfInstruments);
		for (int i = 0; i < numOfInstruments; i++)
			flutes.add(new Flute(scanner));
		return flutes;
	}

	public static ArrayList<Saxophone> loadSaxophones(Scanner scanner) {
		int numOfInstruments = scanner.nextInt();
		ArrayList<Saxophone> saxophones = new ArrayList<Saxophone>(numOfInstruments);
		for (int i = 0; i < numOfInstruments; i++)
			saxophones.add(new Saxophone(scanner));
		return saxophones;
	}

	public static <E extends MusicalInstrument> void addAllInstruments(ArrayList<MusicalInstrument> instruments,
			ArrayList<E> moreInstruments) {
		for (int i = 0; i < moreInstruments.size(); i++) {
			instruments.add(moreInstruments.get(i));
		}
	}

	public static <E extends MusicalInstrument> void printInstruments(ArrayList<E> instruments) {
		for (int i = 0; i < instruments.size(); i++)
			System.out.println(instruments.get(i));
	}

	public static int getNumOfDifferentElements(ArrayList<MusicalInstrument> instruments) {
		int numOfDifferentInstruments;
		ArrayList<MusicalInstrument> differentInstruments = new ArrayList<MusicalInstrument>();
		System.out.println();
		for (int i = 0; i < instruments.size(); i++) {
			if (!differentInstruments.contains((instruments.get(i)))) {
				differentInstruments.add(instruments.get(i));
			}
		}
		if (differentInstruments.size() == 1)
			numOfDifferentInstruments = 0;
		else
			numOfDifferentInstruments = differentInstruments.size();
		return numOfDifferentInstruments;
	}

	public static MusicalInstrument getMostExpensiveInstrument(ArrayList<MusicalInstrument> instruments) {
		double maxPrice = 0;
		MusicalInstrument mostExpensive = (MusicalInstrument) instruments.get(0);
		for (int i = 0; i < instruments.size(); i++) {
			MusicalInstrument temp = (MusicalInstrument) instruments.get(i);
			if (temp.getPrice() > maxPrice) {
				maxPrice = temp.getPrice();
				mostExpensive = temp;
			}
		}
		return mostExpensive;
	}

	public ArrayList<MusicalInstrument> getAllInstruments() {
		return allInstruments;
	}

	public void rightPressed() {
		try {
			if (toSearch) {
				counter = 0;
				toSearch = false;
			}

			if (counter == arr2.size())
				counter = 0;
			if (leftPressed) {
				counter = counter + 2;
				leftPressed = false;
			}
			nextInstruments(arr2, menu.fd1, menu.fd2, menu.fd3);
			counter++;
			rightPressed = true;
		} catch (Exception e) {
			return;
		}
	}

	public void leftPressed() {
		try {
			if (toSearch) {
				counter = 0;
				toSearch = false;
			}
			if (counter == -1)
				counter = arr2.size() - 1;
			if (rightPressed) {
				counter = counter - 2;
				rightPressed = false;
			}
			nextInstruments(arr2, menu.fd1, menu.fd2, menu.fd3);
			counter--;
			leftPressed = true;
		} catch (Exception e) {
			return;
		}
	}

	public void deletePressed() {
		try {
			String str1 = "No Items";
			if (str1.equals(menu.fd1.getText()))
				System.out.println("No Items Selected");
			else {
				if (counter == 0) {
					arr2.remove(counter);
				} else {
					if (leftPressed) {
						arr2.remove(counter + 1);
						leftPressed = false;
					} else
						arr2.remove(counter - 1);
				}
				if (counter == arr2.size())
					counter = 0;
				nextInstruments(arr2, menu.fd1, menu.fd2, menu.fd3);
				printInstruments(arr2);
				System.out.println("\n\n");
			}
		} catch (Exception e) {
			menu.fd1.clear();
			menu.fd2.clear();
			menu.fd3.clear();
		}
	}

	public void clearPressed() {
		arr2.removeAll(allInstruments);
		menu.fd1.clear();
		menu.fd2.clear();
		menu.fd3.clear();
		menu.fd.clear();
	}

	public void AddPressed() {
		addItemPane();
		combo.setOnAction(t -> {
			switch (combo.getValue()) {
			case "Guitar":
				guitarPane();
				btn4.setOnAction(r -> {
					addGuitar();
				});
				break;
			case "Bass":
				bassPane();
				btn4.setOnAction(r -> {
					addBass();
				});
				break;
			case "Flute":
			flutePane();
				btn4.setOnAction(r -> {
					addFlute();
				});
				break;
			case "Saxophone":
				saxophonePane();
				btn4.setOnAction(r -> {
					addSaxophone();
				});
				break;
			}
		});
	}

	public void addGuitar() {
		try {
		
			getAllInstruments().add(new Guitar(fd4.getText(), Double.parseDouble(fd5.getText()),
					Integer.parseInt(fd6.getText()), combo1.getValue()));
			stage.close();
			arr2 = getAllInstruments();
			counter = 0;
			printInstruments(arr2);
			System.out.println("\n\n");
		} catch (Exception e1) {
			alertPane(e1);
		}
	}

	public void addBass() {
		try {
			getAllInstruments().add(new Bass(fd4.getText(), Double.parseDouble(fd5.getText()),
					Integer.parseInt(fd6.getText()), cb.isSelected()));
			stage.close();
			arr2 = getAllInstruments();
			counter = 0;
			printInstruments(arr2);
			System.out.println("\n\n");
		} catch (Exception e1) {
			alertPane(e1);
		}
	}

	public void addFlute() {
		try {
           getAllInstruments().add(
					new Flute(fd4.getText(), Double.parseDouble(fd5.getText()), combo1.getValue(), combo2.getValue()));
			stage.close();
			arr2 = getAllInstruments();
			counter = 0;
			printInstruments(arr2);
			System.out.println("\n\n");
		} catch (Exception e1) {
			alertPane(e1);
		}
	}

	public void addSaxophone() {
		try {
			getAllInstruments().add(new Saxophone(fd4.getText(), Double.parseDouble(fd5.getText())));
			arr2 = getAllInstruments();
			counter = 0;
			printInstruments(arr2);
			System.out.println("\n\n");
			stage.close();
		} catch (Exception e1) {
			alertPane(e1);
		}
	}

	public static void alertPane(Exception e1) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("Error");
		alert.setContentText(e1.getMessage());
		alert.showAndWait();
	}

	public static void addItemPane() {
		newWindow = new VBox();
		newWindow.setPadding(new Insets(10, 10, 10, 10));
		combo = new ComboBox<String>();
		combo.setPrefWidth(300);
		combo.setPromptText("Choose Instrument Type Here");
		combo.getItems().addAll("Guitar", "Bass", "Flute", "Saxophone");
		newWindow.setAlignment(Pos.CENTER);
		newWindow.getChildren().add(combo);
		stage = new Stage();
		stage.setTitle("Afeka Instruments - Add New Instrument");
		stage.setScene(new Scene(newWindow, 450, 400));
		stage.show();
	}

	public static void guitarPane() {
		box1 = new VBox();
		box1.setSpacing(20);
		box1.setPadding(new Insets(40, 40, 40, 40));
		btn4 = new Button("Add");
		gp = new GridPane();
		gp.setPadding(new Insets(20, 20, 20, 20));
		gp.setAlignment(Pos.CENTER);
		gp.setHgap(10);
		gp.setVgap(10);
		l4 = new Label("Brand: ");
		l5 = new Label("Price: ");
		l6 = new Label("Number Of Strings: ");
		l7 = new Label(combo.getValue() + " Type:");
		fd4 = new TextField();
		fd5 = new TextField();
		fd6 = new TextField();
		combo1 = new ComboBox<String>();
		combo1.setPromptText("Type");
		combo1.getItems().addAll("Classic", "Acoustic", "Electric");
		combo.setPromptText("Guitar");
		fd4.setPromptText("Ex: Gibson");
		fd5.setPromptText("Ex: 7500");
		fd6.setPromptText("Ex: 6");
		GridPane.setConstraints(fd4, 1, 0);
		GridPane.setConstraints(fd5, 1, 1);
		GridPane.setConstraints(fd6, 1, 2);
		GridPane.setConstraints(combo1, 1, 3);
		GridPane.setConstraints(l4, 0, 0);
		GridPane.setConstraints(l5, 0, 1);
		GridPane.setConstraints(l6, 0, 2);
		GridPane.setConstraints(l7, 0, 3);
		gp.getChildren().addAll(fd4, fd5, fd6, l4, l5, l6, l7, combo1);
		box1.setAlignment(Pos.CENTER);
		box1.getChildren().addAll(combo, gp, btn4);
		stage.setScene(new Scene(box1, 450, 400));
		stage.show();
	}

	public static void bassPane() {
		box1 = new VBox();
		box1.setSpacing(20);
		box1.setPadding(new Insets(40, 40, 40, 40));
		btn4 = new Button("Add");
		gp = new GridPane();
		gp.setPadding(new Insets(20, 20, 20, 20));
		gp.setAlignment(Pos.CENTER);
		gp.setHgap(10);
		gp.setVgap(10);
		l4 = new Label("Brand: ");
		l5 = new Label("Price: ");
		l6 = new Label("Number Of Strings: ");
		fd4 = new TextField();
		fd5 = new TextField();
		fd6 = new TextField();
		fd4.setPromptText("Ex: Fender Jazz");
		fd5.setPromptText("Ex: 7500");
		fd6.setPromptText("Ex: 6");
		GridPane.setConstraints(fd4, 1, 0);
		GridPane.setConstraints(fd5, 1, 1);
		GridPane.setConstraints(fd6, 1, 2);
		GridPane.setConstraints(l4, 0, 0);
		GridPane.setConstraints(l5, 0, 1);
		GridPane.setConstraints(l6, 0, 2);
		cb = new CheckBox("Fretless");
		gp.getChildren().addAll(fd4, fd5, fd6, l4, l5, l6);
		box1.setAlignment(Pos.CENTER);
		box1.getChildren().addAll(combo, gp, cb, btn4);
		stage.setScene(new Scene(box1, 450, 400));
		stage.show();
	}

	public static void flutePane() {
		box1 = new VBox();
		box1.setSpacing(20);
		box1.setPadding(new Insets(40, 40, 40, 40));
		btn4 = new Button("Add");
		gp = new GridPane();
		gp.setPadding(new Insets(20, 20, 20, 20));
		gp.setAlignment(Pos.CENTER);
		gp.setHgap(10);
		gp.setVgap(10);
		l4 = new Label("Brand: ");
		l5 = new Label("Price: ");
		l6 = new Label("Material: ");
		l7 = new Label("Flute Type: ");
		fd4 = new TextField();
		fd5 = new TextField();
		fd4.setPromptText("Ex: Levit");
		fd5.setPromptText("Ex: 300");
		combo1 = new ComboBox<String>();
		combo2 = new ComboBox<String>();
		combo1.setPromptText("Material");
		combo1.getItems().addAll("Wood", "Metal", "Plastic");
		combo.setPromptText("Guitar");
		combo2.setPromptText("Type");
		combo2.getItems().addAll("Flute", "Recorder", "Bass");
		combo.setPromptText("Guitar");
		GridPane.setConstraints(fd4, 1, 0);
		GridPane.setConstraints(fd5, 1, 1);
		GridPane.setConstraints(combo1, 1, 2);
		GridPane.setConstraints(combo2, 1, 3);
		GridPane.setConstraints(l4, 0, 0);
		GridPane.setConstraints(l5, 0, 1);
		GridPane.setConstraints(l6, 0, 2);
		GridPane.setConstraints(l7, 0, 3);
		gp.getChildren().addAll(fd4, fd5, combo1, combo2, l4, l5, l6, l7);
		box1.setAlignment(Pos.CENTER);
		box1.getChildren().addAll(combo, gp, btn4);
		stage.setScene(new Scene(box1, 450, 400));
		stage.show();
	}

	public static void saxophonePane() {
		box1 = new VBox();
		box1.setSpacing(20);
		box1.setPadding(new Insets(40, 40, 40, 40));
		btn4 = new Button("Add");
		gp = new GridPane();
		gp.setPadding(new Insets(20, 20, 20, 20));
		gp.setAlignment(Pos.CENTER);
		gp.setHgap(10);
		gp.setVgap(10);
		l4 = new Label("Brand: ");
		l5 = new Label("Price: ");
		fd4 = new TextField();
		fd5 = new TextField();
		GridPane.setConstraints(fd4, 1, 0);
		GridPane.setConstraints(fd5, 1, 1);
		GridPane.setConstraints(l4, 0, 0);
		GridPane.setConstraints(l5, 0, 1);
		gp.getChildren().addAll(fd4, fd5, l4, l5);
		box1.setAlignment(Pos.CENTER);
		box1.getChildren().addAll(combo, gp, btn4);
		stage.setScene(new Scene(box1, 450, 400));
		stage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}