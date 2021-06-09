
import java.time.LocalDate;
import java.time.LocalTime;

import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MenuUI extends Application {
	public Button btn, left, right, btn1 = new Button("Add"), btn2 = new Button("Delete"), btn3 = new Button("Clear");
	public TextField fd, fd1, fd2, fd3;
	Label l1, l2, l3;
	HBox botTop, top;
	VBox bot;
	Text sale;
	GridPane mid;
	PathTransition transition;

	public MenuUI() {
		setTop();
		setBot();
		setMid();
	}

	public Node setTop() {
		top = new HBox(25);
		fd = new TextField();
		fd.setFocusTraversable(false);
		fd.setPromptText("Search...");
		fd.setPrefWidth(700);
		btn = new Button("Go!");
		top.getChildren().addAll(fd, btn);
		top.setAlignment(Pos.TOP_CENTER);
		return top;
	}

	public Button getAdd() {
		return btn1;
	}

	public Button getDelete() {
		return btn2;
	}

	public Button getClear() {
		return btn3;
	}

	public PathTransition getPathTransition() {

		PathTransition transition = new PathTransition();
		transition.setDuration(Duration.seconds(10));
		transition.setPath(new Rectangle(800, 0));
		transition.setNode(sale);
		transition.setAutoReverse(true);
		transition.setCycleCount(Timeline.INDEFINITE);

		return transition;
	}

	public String setStr() {
		LocalDate date = LocalDate.now();
		LocalTime time = LocalTime.now().minusNanos(LocalTime.now().getNano());
		String str = date.toString() + " " + time.toString()
				+ " Afeka Instruments Music Store $$$ ON SALE!!! $$$ Guitars , Basses , Flutes , Saxophones and more! ";
		return str;
	}

	public Node setBot() {
		bot = new VBox(20);
		botTop = new HBox(50);
		botTop.getChildren().addAll(btn1, btn2, btn3);
		botTop.setAlignment(Pos.CENTER);
		sale = new Text();
		sale.setFill(Color.RED);
		sale.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		transition = getPathTransition();
		EventHandler<ActionEvent> eventHandler = e -> {
			sale.setText(setStr());
			transition.setNode(sale);
		};
		bot.getChildren().addAll(botTop, sale);
		bot.setAlignment(Pos.CENTER);
		Timeline animation = new Timeline(new KeyFrame(Duration.millis(1000), eventHandler));
		animation.setCycleCount(Timeline.INDEFINITE);
		animation.play();
		transition.play();
		bot.setOnMouseMoved(e -> transition.pause());
		bot.setOnMouseExited(e -> transition.play());
		return bot;
	}

	public Node setMid() {
		mid = new GridPane();
		mid.setPadding(new Insets(10, 10, 10, 10));
		mid.setVgap(10);
		mid.setHgap(8);
		fd1 = new TextField();
		fd1.setPromptText("No Items");
		GridPane.setConstraints(fd1, 1, 0);
		fd2 = new TextField();
		fd2.setPromptText("No Items");
		GridPane.setConstraints(fd2, 1, 1);
		fd3 = new TextField();
		fd3.setPromptText("No Items");
		GridPane.setConstraints(fd3, 1, 2);
		l1 = new Label("Type:");
		GridPane.setConstraints(l1, 0, 0);
		l2 = new Label("Brand:");
		GridPane.setConstraints(l2, 0, 1);
		l3 = new Label("Price:");
		fd1.setFocusTraversable(false);
		fd2.setFocusTraversable(false);
		fd3.setFocusTraversable(false);
		GridPane.setConstraints(l3, 0, 2);
		mid.getChildren().addAll(fd1, fd2, fd3, l1, l2, l3);
		mid.setAlignment(Pos.CENTER);
		return mid;
	}

	public static void setScene() {

	}

	@Override
	public void start(Stage arg) throws Exception {

	}

}