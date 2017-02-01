/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wordsearch;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Michael
 */
public class Gui extends Application implements EventHandler<ActionEvent>{
    Stage window;
    Scene scene1, scene2, scene3; //create 3 scenes to change
    TableView<Partner> table;
    TextField wordInput;
    ObservableList<Partner> words;
    int num;
    char[][] crossWord;
    ArrayList<String> wordList;
    VBox layout3 = new VBox();
    Label label2;
   
    
    @Override
    public void start(Stage primaryStage) {
        label2 = new Label();
        label2.setPadding(new Insets(5,5,5,5));
        Spinner<Integer> spinner = new Spinner(5,20,5); //create spinner object 
        words = FXCollections.observableArrayList();
        wordList = new ArrayList<>();
        
        Button button = new Button("Create Puzzle");//create a button that has action
        button.setOnAction(e -> {
            Partner gui = new Partner();//access methods from another class
            num = spinner.getValue();//get value from spinner
            crossWord = new char [num][num];//create 2d array 
            primaryStage.setScene(scene2);
            primaryStage.setTitle("Add words to puzzle");
        });//set scene2 and get number of words
        
        GridPane grid = new GridPane();//create grid layout
        grid.setHgap(10);
        grid.setVgap(10);
        
        
        //Place spinner and label together
        grid.add(new Label("Size of puzzle"), 0, 0);
        grid.add(spinner, 1, 0);
        grid.add(button, 0, 2);
        
        
        //Word column
        TableColumn<Partner, String> wordColumn = new TableColumn<>("Word List");
        wordColumn.setMinWidth(450);
        wordColumn.setCellValueFactory(new PropertyValueFactory<>("word"));//must be the same name as instance of another class
        
        //Word Input
        wordInput = new TextField();
        wordInput.setPromptText("Word");
        wordInput.setMinWidth(200);
        
        //Create label to show exception thrown
        Label label = new Label();
        label.setPadding(new Insets(5,5,5,5));
        //Create label to show puzzle
        
       
        
        //Add Button
        Button addButton = new Button("Add word to puzzle");
        addButton.setOnAction(e -> {
            addButtonClicked(label);
            if(words.size() == num){
               primaryStage.setScene(scene3);
               primaryStage.setTitle("CrossWord Puzzle complete!!!");
               label2.setText(verticalString());                     }     
            });
        
         //verical words button
        Button vButton = new Button("Vertical Words");
        vButton.setOnAction(e -> {
            label2.setText(verticalString());
                });
        
         //horizontal words button
        Button hButton = new Button("Horizontal Words");
        hButton.setOnAction(e -> {
            label2.setText(horizontalString());
                });
        
        //Save to file button
        Button saveButton = new Button("Save to File");
        saveButton.setOnAction(e -> saveFile());
        
        //Horizontal Box
        HBox hBox = new HBox();
        hBox.setPadding(new Insets(10,10,10,10));
        hBox.setSpacing(10);
        hBox.getChildren().addAll(wordInput, addButton);
        
        //Horizontal Box for layout3
        HBox hBox2 = new HBox();
        hBox2.setPadding(new Insets(10,10,10,10));
        hBox2.setSpacing(10);
        hBox2.getChildren().addAll(saveButton,hButton, vButton);
        
        //create tableview
        table = new TableView<>();
        table.setItems(getWords());
        table.getColumns().addAll(wordColumn);
        
        //layout2
        VBox layout2 = new VBox();
        layout2.getChildren().addAll(hBox, table, label);
        scene2 = new Scene(layout2,450,400);
        
        //layout3
        layout3.getChildren().addAll(label2 , hBox2);
        scene3 = new Scene(layout3, 500, 500);
         
        //scene 1
        Scene scene = new Scene(grid, 350,150);
        primaryStage.setTitle("Assignment 2 Gui");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void handle(ActionEvent event) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    // get words and return/show them to screen
    public ObservableList<Partner> getWords(){
        return words;
    }
    
    //Add Button clicked
    public void addButtonClicked(Label label){
        try{
        Partner word = new Partner();
        String newWord = wordInput.getText();
        word.setWord(validateWord(wordInput.getText().trim().toUpperCase()));

        wordList.add(newWord.toUpperCase());
        table.getItems().add(word);
        wordInput.setStyle("-fx-text-box-border: transparent");
        wordInput.clear();
        } catch (IllegalArgumentException e){
            String warning = String.format("%s", e.getMessage());
            label.setText(warning);
            wordInput.setStyle("-fx-text-box-border: red");
            wordInput.clear();
        }
    
    }
    
    public String validateWord(String word){//This method will validate words entered
            
            if(word.equals(""))
                throw new IllegalArgumentException("Please enter a word");
            if(word.length()>num)
                throw new IllegalArgumentException(word + " is too long, it must be "+ num + " characters long.");
            if(word.length() == 1)
                throw new IllegalArgumentException(word +" is too short, it must be at least 2 characters long");
            if(word.contains(" "))
                throw new IllegalArgumentException("The word has spacing");
            if(words.size()>0){
                for (int i=0; i<words.size(); i++) {
                    if(word.equalsIgnoreCase(words.get(i).getWord()))
                        throw new IllegalArgumentException("The word is a duplicate"); 
                }
        }
            return word;
    }
    
    public void verticalWords(){ //Insert words vertically
        SecureRandom rng = new SecureRandom();
        char[] letters = {'A','B','C','D','E','F','G','H','I','J','K','L','M',
                   'N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        
        //insert words from list to puzzle grid
        for(int i=0; i<words.size();i++){
            String word = wordList.get(i);
            int random;
            if(num - word.length() == 0)
                random = 0;
            else
                random = rng.nextInt(num - word.length()+1);
            for(int j=0; j<word.length(); j++){
                crossWord[random][i] = word.charAt(j);
                random++;
            }
        }
        
        //insert alphabet into grid
        for (int row=0; row<crossWord.length;row++){
            for(int col=0;col<crossWord.length;col++){
                 int alpha = rng.nextInt(26);
                if (crossWord[row][col] == 0)
                    crossWord[row][col] = letters[alpha];
                  }
        }
    }   
    
    public void horizontalWords(){ //Insert words horizontally
        SecureRandom rng = new SecureRandom();
        char[] letters = {'A','B','C','D','E','F','G','H','I','J','K','L','M',
                   'N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        
        //insert words from list to puzzle grid
        for(int i=0; i<words.size();i++){
            String word = wordList.get(i);
            int random;
            if(wordList.size() - word.length() == 0)
                random = 0;
            else
                random = rng.nextInt(wordList.size() - word.length()+1);
            for(int j=0; j<word.length(); j++){
                crossWord[i][random] = word.charAt(j);
                random++;
            }
        }
        
        //insert alphabet into grid
        for (int row=0; row<crossWord.length;row++){
            for(int col=0;col<crossWord.length;col++){
                 int alpha = rng.nextInt(26);
                if (crossWord[row][col] == 0)
                    crossWord[row][col] = letters[alpha];
                  }
        }
    }  
    
    public String verticalString(){ //create vertical crossWord puzzle
         Collections.sort(wordList);
         String str = "";
         for(String element : wordList){
             str += String.format("%s%n", element);
         }
        
        verticalWords();
        String grid = "";
        for (char[] crossWord1 : crossWord) {
            for (int col = 0; col<crossWord.length; col++) {
                grid += String.format("%6s", crossWord1[col]);
            }
            grid += String.format("%n");
        }
         return String.format("%s%n%nHere are the words to find:%n%s",grid, str);
     }   
    
    public String horizontalString(){ //create horizontal crossWord puzzle
         Collections.sort(wordList);
         String str = "";
         for(String elements : wordList){
             str += String.format("%s%n", elements);
         }
         
        horizontalWords();
        String grid = "";
        for (char[] crossWord1 : crossWord) {
            for (int col = 0; col<crossWord.length; col++) {
                grid += String.format("%6s", crossWord1[col]);
            }
            grid += String.format("%n");
        }
         return String.format("%s%n%nHere are the words to find:%n%s",grid,str);
     }
    
    public void saveFile(){// save file method
       FileChooser fileChooser = new FileChooser();
  
              //Set extension filter
              FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
              fileChooser.getExtensionFilters().add(extFilter);
              
              //Show save file dialog
              File file = fileChooser.showSaveDialog(window);
              
              String content = String.format("%s", label2.getText());
             if(file != null){
                  sFile(content, file);
            }
    }
     
    public void sFile(String content, File file){//write to text file
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(content);
            fileWriter.close();
        } catch (IOException ex) {
            System.err.printf("%s", ex);
        }
         
    }
    
}//end of class
