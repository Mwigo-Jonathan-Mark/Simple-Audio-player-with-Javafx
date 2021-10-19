/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package musicplayer;

import java.io.File;
import javafx.application.Application;
import static javafx.application.Application.STYLESHEET_CASPIAN;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.SliderBuilder;
import javafx.scene.effect.Reflection;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
/**
 *
 * @author MWIGO-JON-MARK
 * @version 1.0.1
 */
public class MusicPlayer extends Application
{

    MediaPlayer mediaPlayer;
    Media media;
    Status playerStatus;
    Slider mediaSlider;
    Slider volSlider;
    Button playPauseBtn;
    Button stop;
    Button addFile;
    Button mute;
    Label defaultMediaTime;
    Label currentMediaTime;
    Label songName;
    Duration defaultTime;
    Duration currentTime;
    VBox songTitle;
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        // TODO code application logic here
        Application.launch(args);
    }

    @Override
    public void init()
    {
        mediaSlider = SliderBuilder
                .create()
                .prefWidth(200)
                .minWidth(150)
                .maxWidth(Region.USE_PREF_SIZE)
                .build();
        mediaSlider.valueChangingProperty().addListener(e->updateValues());
                
        volSlider = SliderBuilder
                .create()
                .prefWidth(100)
                .minWidth(40)
                .maxWidth(Region.USE_PREF_SIZE)
                .build();
//        volSlider.onMouseExited(volSlider.);
        volSlider.valueChangingProperty().addListener(e->updateValues());
        
        playPauseBtn = new Button("Play");
        playPauseBtn.setStyle("-fx-background-color: grey;"
                + "-fx-border-width: 2;"
                + "-fx-padding: 5;"
                );
        stop = new Button("Stop");
        stop.setStyle("-fx-background-color: grey;"
                + "-fx-border-width: 2;"
                + "-fx-padding: 5;"
                );
        mute = new Button("Mute");
        mute.setStyle("-fx-background-color: grey;"
                + "-fx-border-width: 2;"
                + "-fx-padding: 5;"
                );
        addFile = new Button("Add File");
        addFile.setStyle("-fx-background-color: grey;"
                + "-fx-border-width: 2;"
                + "-fx-padding: 5;"
                );
        
        playPauseBtn.setOnMouseEntered(e->{playPauseBtn.setStyle("-fx-background-color: rgb(102, 102, 102);"
                + "-fx-padding: 5;"
                + "-fx-border-radius: 2;"
                + "-fx-border-insets: 0,0,0,0;"
                + "-fx-border-color: white;"
                + "-fx-border-width: 1;");});
        mute.setOnMouseEntered(e->{mute.setStyle("-fx-background-color: rgb(102, 102, 102);"
                + "-fx-padding: 5;"
                + "-fx-border-radius: 2;"
                + "-fx-border-insets: 0,0,0,0;"
                + "-fx-border-color: white;"
                + "-fx-border-width: 1;");});
        stop.setOnMouseEntered(e->{stop.setStyle("-fx-background-color: rgb(102, 102, 102);"
                + "-fx-padding: 5;"
                + "-fx-border-radius: 2;"
                + "-fx-border-insets: 0,0,0,0;"
                + "-fx-border-color: white;"
                + "-fx-border-width: 1;");});
        addFile.setOnMouseEntered(e->{addFile.setStyle("-fx-background-color: rgb(102, 102, 102);"
                + "-fx-padding: 5;"
                + "-fx-border-radius: 2;"
                + "-fx-border-insets: 0,0,0,0;"
                + "-fx-border-color: white;"
                + "-fx-border-width: 1;");});
        
        playPauseBtn.setOnMouseExited(e->{playPauseBtn.setStyle("-fx-background-color: gray;"
                + "-fx-padding: 5;"
                + "-fx-border-radius: 2;"
                + "-fx-border-color: gray;"
                + "-fx-border-width: 1;");});
        mute.setOnMouseExited(e->{mute.setStyle("-fx-background-color: gray;"
                + "-fx-padding: 5;"
                + "-fx-border-radius: 2;"
                + "-fx-border-color: gray;"
                + "-fx-border-width: 1;");});
        stop.setOnMouseExited(e->{stop.setStyle("-fx-background-color: gray;"
                + "-fx-padding: 5;"
                + "-fx-border-radius: 2;"
                + "-fx-border-color: gray;"
                + "-fx-border-width: 1;");});
        addFile.setOnMouseExited(e->{addFile.setStyle("-fx-background-color: gray;"
                + "-fx-padding: 5;"
                + "-fx-border-radius: 2;"
                + "-fx-border-color: gray;"
                + "-fx-border-width: 1;");});
        defaultMediaTime = new Label();
        currentMediaTime = new Label();
        songName = new Label();
        songName.setFont(Font.font(STYLESHEET_CASPIAN, FontWeight.BOLD, 18));
        songName.setWrapText(true);
        songTitle = new VBox(songName);
        songTitle.setStyle("-fx-background-color: black;"
                );
        songTitle.setAlignment(Pos.CENTER);
        songName.setStyle("-fx-padding: 10;"
                + "-fx-background-color: black;"
                + "-fx-foreground-color: white;"
                );
        songName.setEffect(new Reflection(0.01, 0.9, 0.6, 0.1));
        
        playPauseBtn.setOnAction(e->{
            playerStatus = mediaPlayer.getStatus();
            
            if(playerStatus == Status.UNKNOWN || playerStatus == Status.DISPOSED || playerStatus == Status.HALTED || playerStatus == Status.STALLED){}
            
            if(playerStatus == Status.PAUSED || playerStatus == Status.READY || playerStatus == Status.STOPPED)
            {
                mediaPlayer.play();
                playPauseBtn.setText("Pause");
            }
            else
            {
                mediaPlayer.pause();
                playPauseBtn.setText("Play");
            }
        });
        
        stop.setOnAction(e->{
            if(playerStatus == Status.PLAYING)
            {
                mediaPlayer.stop();
            }
        });
        
        mute.setOnAction(e->{
            if(mediaPlayer.getStatus() == Status.PLAYING || mediaPlayer.getStatus() == Status.PAUSED || mediaPlayer.getStatus() == Status.READY || mediaPlayer.getStatus() == Status.STOPPED)
            {
                if(mediaPlayer.isMute() == false)
                {
                    mediaPlayer.setMute(true);
                    mute.setText("Un-Mute");
                }
                
                else{mediaPlayer.setMute(false);}
            }
        });
        
        volSlider.valueProperty().addListener(e->{
            if(volSlider.isValueChanging())
            {
                mediaPlayer.setVolume(volSlider.getValue() / 100);
            }
            updateValues();
        });
        
        mediaSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable)
            {}
        });
        mediaSlider.valueProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue)
            {
                if(mediaSlider.isValueChanging())
                {
                    if(defaultTime != null)
                    {
                        mediaPlayer.seek(defaultTime.multiply(mediaSlider.getValue() / 100));
                    }
                    updateValues();
                }
            }
        });
        
    }

    public void updateValues()
    {
        defaultTime = mediaPlayer.getMedia().getDuration();
        currentTime = mediaPlayer.getCurrentTime();
        
        int currentHour = (int) Math.floor(currentTime.toHours());
        int currentMin = (int) Math.floor(currentTime.toMinutes());
        int currentSec = (int) Math.floor(currentTime.toSeconds());
        
        int defaultHour = (int) Math.floor(defaultTime.toHours());
        int defaultMin = (int) Math.floor(defaultTime.toMinutes());
        int defaultSec = (int) Math.floor(defaultTime.toSeconds());
        
        String currentTimeFormat1 = String.format("%d:%02d:%02d", currentHour, currentMin, currentSec);
        String currentTimeFormat2 = String.format("%02d:%02d", currentMin, currentSec);
        String currentTimeFormat3 = String.format("%02d", currentSec);
        
        String defaultTimeFormat1 = String.format("%d:%02d:%02d", defaultHour, defaultMin, defaultSec);
        String defaultTimeFormat2 = String.format("%02d:%02d", defaultMin, defaultSec);
        String defaultTimeFormat3 = String.format("%02d", defaultSec);
        
        if(defaultTime != null && !mediaSlider.isValueChanging() && !volSlider.isValueChanging())
        {
            if(defaultTime != null && !mediaSlider.isValueChanging())
            {
                mediaSlider.setValue(currentTime.divide(defaultTime).toMillis()*100.0);
                
                if(defaultMin >= 60)
                {
                    defaultMediaTime.setText(defaultTimeFormat1);
                    currentMediaTime.setText(currentTimeFormat1);
                }
                
                else if(defaultSec >= 60)
                {
                    defaultMediaTime.setText(defaultTimeFormat2);
                    currentMediaTime.setText(currentTimeFormat2);
                }
                
                else
                {
                    defaultMediaTime.setText(defaultTimeFormat3);
                    currentMediaTime.setText(currentTimeFormat3);
                }
            }
            
            if(!volSlider.isValueChanging())
            {
                volSlider.setValue(mediaPlayer.getVolume()*100.0);
            }
        }
    }
    
    @Override
    public void start(Stage stage) throws Exception
    {
        addFile.setOnAction(e->{
            FileChooser fileSelecter = new FileChooser();
            
            fileSelecter.getExtensionFilters().addAll(new ExtensionFilter("Audio Files", "*.mp3", "*.ogg", "*.mp4", "*.wav"));
            
            File selected = fileSelecter.showOpenDialog(stage);
            if(selected != null)
            {
                media = new Media(selected.toURI().toString());
                mediaPlayer = new MediaPlayer(media);
                volSlider.setValue(mediaPlayer.getVolume() * 100);
                mediaPlayer.setAutoPlay(true);
                playPauseBtn.setText("Pause");
                songName.setText(selected.getName());
                mediaPlayer.currentTimeProperty().addListener(ev->updateValues());
            }
            
        });
        
        HBox mediaBar1 = new HBox(currentMediaTime, mediaSlider, defaultMediaTime);
        mediaBar1.setSpacing(20);
        mediaBar1.setStyle("-fx-background-color: grey;"
                + "-fx-padding: 10;");
        
        mediaBar1.setAlignment(Pos.CENTER);
        
        HBox mediaBar2 = new HBox(addFile, playPauseBtn, stop, mute, volSlider);
        mediaBar2.setSpacing(20);
        mediaBar2.setStyle("-fx-background-color: darkgray;"
                + "-fx-padding: 10;");
        
        VBox mediaBox = new VBox(mediaBar1, mediaBar2);
        
        BorderPane root = new BorderPane();
        root.setCenter(songTitle);
        root.setBottom(mediaBox);
        
        Scene scene = new Scene(root, 400, 300);
        
        stage.setX(0);
        stage.setY(0);
        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.initStyle(StageStyle.UNIFIED);
        stage.setTitle("DyOd Audio Player");
        stage.setScene(scene);
        stage.show();
    }
    
    @Override
    public void stop()
    {
        mediaPlayer.dispose();
    }
    
    public static void Main(String[] args)
    {
        Application.launch(args);
    }
}
