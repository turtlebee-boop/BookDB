package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {

    Stage stage = new Stage();

    @Override
    public void start(Stage primaryStage) throws Exception{

        GridPane gridPane = new GridPane();
        Scene scene = new Scene(gridPane, 800, 600);
        stage.setScene(scene);
        stage.setTitle("Book DataBase Control");
        stage.show();

        addingLayout();
    }


    public static void main(String[] args) {
        launch(args);
    }

    public void addingLayout(){

        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setVgap(30);
        root.setHgap(30);

        Text title = new Text("Title");
        TextField enterTitle = new TextField();
        enterTitle.setTooltip(new Tooltip("enter the title"));

        Text pages = new Text("Pages");
        TextField enterPages = new TextField();
        enterPages.setTooltip(new Tooltip("if left empty the value assigned will be 0"));




        Button add = new Button("Add to database");
        add.setOnAction(event -> {
            if (enterTitle.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR, "No title entered", ButtonType.CLOSE);
                alert.show();
            }
            else if (enterPages.getText().isEmpty() || enterPages.getText().toLowerCase().matches("[a-z]")){
                Book book = new Book(enterTitle.getText(), 0);
                DbController.addToDB(book);
                enterTitle.clear();
                enterPages.clear();
            }
            else{
                Book book = new Book(enterTitle.getText(), Integer.parseInt(enterPages.getText()));
                DbController.addToDB(book);
                enterTitle.clear();
                enterPages.clear();
            }
        });
        Button goToTableLayout = new Button("view table");
        goToTableLayout.setOnAction(event -> {
            tableLayout();
        });

        root.add(title, 0, 0);
        root.add(enterTitle, 1, 0);
        root.add(pages, 0, 1);
        root.add(enterPages, 1, 1);
        root.add(add, 1, 3);
        root.add(goToTableLayout, 1, 4);

        stage.getScene().setRoot(root);

    }

    public void tableLayout(){
        GridPane root = new GridPane();
        stage.setTitle("Table");
        root.setAlignment(Pos.CENTER);
        root.setVgap(50);
        root.setHgap(50);

        TableView<Book> tableViewBooks = new TableView<>();

        TableColumn<Book, String> columnTitle = new TableColumn<>("Title");
        columnTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        columnTitle.setMinWidth(200);

        TableColumn<Book, Integer> columnPages = new TableColumn<>("Pages");
        columnPages.setCellValueFactory(new PropertyValueFactory<>("pages"));
        columnPages.setMinWidth(100);

        tableViewBooks.getColumns().addAll(columnTitle, columnPages);
        root.add(tableViewBooks, 0,0);

        List<Book> books = DbController.getFromDB();
        tableViewBooks.getItems().addAll(books);

        Button back = new Button("Back");
        back.setOnAction(event -> addingLayout());
        root.add(back,0,2);

        stage.getScene().setRoot(root);

    }
}
