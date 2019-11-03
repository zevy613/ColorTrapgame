import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.layout.*;
import javafx.util.Duration;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ColorTrap extends Application
{   
    private Scene scene;
    private BorderPane borderPane;
   // Timer 
    private final int TIMER = 15;
    private Text txtCountDown = new Text(Integer.toString(TIMER));
    private Timeline timeline;    
    private int count = TIMER;
   //Score 
    public String score = "0";
    public Text scr = new Text("Score: " + score);    
   //Colors
    private final ColorsEnum []colors = ColorsEnum.values();
   //Images 
    public Image crtIm = new Image("correct.png"); 
    public Image wrgIm = new Image("wrong.png"); 
    
    public ImageView imageView = new ImageView();    
   //Words 
    public Text [] t = new Text [7];
    public Text trapWord = new Text();    
    
    //panes
           FlowPane flowp;
           FlowPane flop;
           FlowPane flp;
           
     Timeline tml;           
    
//    public Button st = new Button("Start!");
    @Override
    public void start(Stage primaryStage)
    {
        borderPane = new BorderPane();
        borderPane.setStyle("-fx-background-color: lightgrey");
        scene = new Scene(borderPane, 600, 300);
        primaryStage.setMinHeight(300);
        primaryStage.setMinWidth(600);
        
        /*instructions();
       st.setOnAction( e ->
        { */         
         initializeGame();
         startPlay();
        /*}
       
       ); */
       
        primaryStage.setTitle("Color Trap");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public void startPlay()
    {
        chooseTrapWordAndColor();
        colorNameOptions();

        count = TIMER;
        txtCountDown.setText("Timer: " + TIMER + "");
        timeline = new Timeline(new KeyFrame(
                Duration.millis(1000), e -> {

                    if(count >= 0)
                    {
                        txtCountDown.setText("Timer: " +  count + "");
                        count--;
                    }
                    else
                    {
                        endOfGame();
                    }
                }));
        timeline.setCycleCount(TIMER + 2);
        timeline.play();
    }
    
    public void endOfGame()
    {
        tml.stop();
        //clears off the top and bottom part of the borderPane.
        borderPane.setTop(null);
        borderPane.setBottom(null);
        
        //displays the total score of the player
        Text end = new Text("Your " + scr.getText());
        end.setFont(Font.font ("Marker Felt", 50));
        //promotes another game  
        Button playAgian = new Button("CLICK HERE TO PLAY AGIAN?");
        
        //put everything into place
        VBox vb = new VBox();
        vb.setSpacing(85);
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(end, playAgian); 
        borderPane.setCenter(vb);

        playAgian.setOnAction(e ->
         {
          //resets the score and the timer and starts the next game
          score = "0";
          scr.setText("Score: " + score);
          count = TIMER;
          imageView.setImage(null);
         
          
          borderPane.setCenter(flowp);
          borderPane.setTop(flop);
          borderPane.setBottom(flp);  
          tml.play();                    
          startPlay(); 
         }
        );
    }

    public void checkChoice(Text choice)
    {     
        Color temp2 = Color.valueOf(choice.getText());
        Color temp1 = (Color)trapWord.getFill();        
         if (temp2.equals(temp1))
          {           
           int temp = (Integer.parseInt(score)+1);
           score = Integer.toString(temp);
           scr.setText("Score: " + score);
           imageView.setImage(crtIm);
          }
         else
          {
           imageView.setImage(wrgIm);
          } 
          
        //Do NOT add any code after this comment
        //Choose a new trap word and options list
        chooseTrapWordAndColor();
        colorNameOptions();
    }

    public void chooseTrapWordAndColor()
    {
        
        int x = (int)(Math.random()*7); 
        trapWord.setText(colors[x] + "");
        x = (int)(Math.random()*7); 
        trapWord.setFill(Paint.valueOf(colors[x] + ""));    
    }
    
    public void colorNameOptions()
    {        
        ColorsEnum temp;
         
         for (int i = 0; i < colors.length; i++)
          {
            int x = (int)(Math.random()*7); 
            temp = colors[i];
            colors[i] = colors[x];
            colors[x] = temp;  
          }
         
         int tempint [] = new int [colors.length];
         int x = 0;
         for (int i = 0; i < colors.length; i++)
          {
           do
            {
             x = (int)(Math.random()*7); 
            }
           while(tempint[x] == 1);   
                     
           t[i].setText(colors[i] + "");
           t[i].setFill(Paint.valueOf(colors[x] + ""));
           tempint[x] = 1;          
          } 
 
       for (int i = 0; i < t.length; i++)
        { 
        Text temporary = t[i];
         t[i].setOnMouseClicked( e ->
          {
           checkChoice(temporary);
          });        
        }        
    }

    public void initializeGame()
    {                
     // hard way
     String [] back = {"PINK", "BEIGE", "BURLYWOOD", "CYAN", "GOLD", "LAVENDER"};
     
     tml = new Timeline(new KeyFrame(
      Duration.millis(250), e -> {
      String tempm = "-fx-background-color:" + back[(int)(Math.random()*6)];
      borderPane.setStyle(tempm);
             
      }));
      tml.setCycleCount(Timeline.INDEFINITE);
      tml.play();  
                
     //sizs the images
      imageView.setFitWidth(30);
      imageView.setFitHeight(30);
      
       flowp = new FlowPane();
       flowp.setHgap(30);
       flowp.setAlignment(Pos.CENTER);
       borderPane.setCenter(flowp);

      trapWord.setFont(Font.font ("Marker Felt", 60));  
      for (int i = 0; i < colors.length; i++)
       {       
        t[i] = new Text(colors[i] + "");
        t[i].setFont(Font.font ("Marker Felt",40));
        flowp.getChildren().add(t[i]);
       }
      
      flop = new FlowPane();
      borderPane.setTop(flop);
      flop.getChildren().add(trapWord);            
      flop.setAlignment(Pos.CENTER);
      
      flp = new FlowPane();
      flp.setHgap(235);
      flp.setAlignment(Pos.CENTER);
      borderPane.setBottom(flp);  
      flp.getChildren().addAll(scr, imageView, txtCountDown);
      
            
 //       property binding
        flop.minHeightProperty().bind(borderPane.heightProperty().multiply(.35));
        flowp.minHeightProperty().bind(borderPane.heightProperty().multiply(.55));
        flp.minHeightProperty().bind(borderPane.heightProperty().multiply(.10));
    }      
    public static void main(String[] args)
    {
        launch(args);
    }
}
/*
    public void instructions()
     {
      FlowPane fl1 = new FlowPane();
      fl1.setAlignment(Pos.CENTER);
      borderPane.setTop(fl1);
      
      Text instr = new Text();
      instr.setText("the object of the game is to click on the list of colors diplyed in the middle of the screen that states the color of the word at the top of the screen. an example is shown below.");
      instr.setFont(Font.font ("Times of new Roman", 15));
      Image ex = new Image("Example.png"); 
      imageView.setFitWidth(30);
      imageView.setFitHeight(30);      
      imageView.setImage(ex);
      
      fl1.getChildren().add(instr);
      
      FlowPane fl2 = new FlowPane();      
      fl2.setAlignment(Pos.CENTER);
      borderPane.setCenter(fl2);
      
      fl2.getChildren().add(imageView);           
     }*/
   