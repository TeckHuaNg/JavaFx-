package main;
import java.util.stream.Collectors;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GradeCenter extends Application {

    Stage window;
    TableView<Info> table;
    TextField subjectInput, markInput, gradeInput, insInput, codeInput;
    
    
    
    
    
    

    public static void main(String[] args) {
        launch(args);
    
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Grade Center");

        //Name column
        TableColumn<Info, String> subjectColumn = new TableColumn<>("Subject");
        subjectColumn.setMinWidth(200);
        subjectColumn.setCellValueFactory(new PropertyValueFactory<>("subject"));

        //Price column
        TableColumn<Info, Double> markColumn = new TableColumn<>("Mark");
        markColumn.setMinWidth(100);
        markColumn.setCellValueFactory(new PropertyValueFactory<>("mark"));

        //Quantity column
        TableColumn<Info, String> quantityColumn = new TableColumn<>("Grade");
        quantityColumn.setMinWidth(100);
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("grade"));
        
        //Instructor column
        TableColumn<Info, String> insColumn = new TableColumn<>("Instructor");
        insColumn.setMinWidth(100);
        insColumn.setCellValueFactory(new PropertyValueFactory<>("ins"));
        
        //Course code column
        TableColumn<Info, String> codeColumn = new TableColumn<>("Course Code");
        codeColumn.setMinWidth(100);
        codeColumn.setCellValueFactory(new PropertyValueFactory<>("code"));
        
        //Subject Input
        subjectInput = new TextField();
        subjectInput.setPromptText("Subject");
        subjectInput.setMinWidth(200);
        
        //Mark Input
        markInput = new TextField();
        markInput.setPromptText("Mark");
        markInput.setMaxWidth(90);
        
       //Instructor's name Input
        insInput = new TextField();
        insInput.setPromptText("Instructor's name");
        insInput.setMinWidth(100);
        
        //course code Input
        codeInput = new TextField();
        codeInput.setPromptText("Course code");
        codeInput.setMaxWidth(80);
        
        Label label = new Label("Your average grade is 95.60%,which is a letter grade of A");
      
        //Button
        Button addButton = new Button("Add");
        addButton.setOnAction(e -> addButtonClicked(label));
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> deleteButtonClicked(label));
       
        
        
        
       
        HBox hBox = new HBox();
        
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(subjectInput, markInput, insInput, codeInput,label, addButton, deleteButton);

        table = new TableView<>();
        table.setItems(getProduct());
        table.getColumns().addAll(subjectColumn, markColumn, quantityColumn, insColumn, codeColumn);
        
         
        VBox vBox = new VBox();
        vBox.getChildren().addAll(table, hBox);

        Scene scene = new Scene(vBox);
        window.setScene(scene);
        window.show();
        
    }
    //Add buttton clicked
    public void addButtonClicked(Label label){
        
       
        Info infos = new Info();
        infos.setSubject(subjectInput.getText());
        infos.setMark(Double.parseDouble(markInput.getText()));
        infos.setIns(insInput.getText());
        infos.setCode(codeInput.getText());
        
      
        
        table.getItems().add(infos);
        subjectInput.clear();
        markInput.clear();
        insInput.clear();
        codeInput.clear();
        
         double all = table.getItems().stream().collect(Collectors.averagingDouble((Info) -> Info.getMark()));
        String output = String.format("Your average grade is %.2f%%,which is a letter grade of %s",all, getGrade(all) );
        
        label.setText(output);
        
     
    }
    //Delete button clicked
    public void deleteButtonClicked(Label label){
       
        ObservableList<Info> infosSelected, allInfos;
        allInfos = table.getItems();
        infosSelected = table.getSelectionModel().getSelectedItems();
        // remove from main table
        infosSelected.forEach(allInfos::remove);
        
          double all = table.getItems().stream().collect(Collectors.averagingDouble((Info) -> Info.getMark()));
        String output = String.format("Your average grade is %.2f%%,which is a letter grade of %s",all, getGrade(all) );
        
        label.setText(output);
    }
        
    
    //Get all of the products
    public ObservableList<Info> getProduct(){
        ObservableList<Info> infos = FXCollections.observableArrayList();
        infos.add(new Info("Programming Fundamentals", 89.00, "", "Jaret","COMP1030" ));
        infos.add(new Info("Web Development", 95, "","Wayne","COMP"));
        infos.add(new Info("Math", 99.00, "","Anju","COMP"));
        infos.add(new Info("Internet of Things", 97, "","Ross", "COMP"));
        infos.add(new Info("Comm Ess", 98, "", "Melissa", "Math"));
        return infos;
    }
   
   public static String getGrade(double all) {
         String grade = "";
        if (all >= 80.0)
            grade = "A";
        else if (all >= 70.0)
            grade = "B";
        else if (all >= 60.0)
            grade = "C";
        else if (all >= 50.0)
            grade = "D";
        else
            grade = "F";
        return grade;
       
    }
    
    
    
   
    
}