package sample;

import javafx.animation.RotateTransition;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Random;
import java.util.Scanner;

public class Main extends Application {

    // Konstruktory
    Random random = new Random();
    ScenaMenu scena_menu = new ScenaMenu();

    // Animace
    private RotateTransition animace = new RotateTransition();

    // Kontrola jestli už bylo přičteno + 1 k počtu nalezenych among us postaviček
    private boolean pricteno = false;

    @Override
    public void start(Stage hlavni_okno_aplikace){

        Group vzhled_okna = new Group();
        Scene scena = new Scene(vzhled_okna);

        // Vlastnosti okna
        hlavni_okno_aplikace.setTitle("Najdi Among Us Postavičku");
        hlavni_okno_aplikace.setWidth(512);
        hlavni_okno_aplikace.setHeight(412);
        hlavni_okno_aplikace.setResizable(false);

        // Nastavení menu sceny
        scena_menu.scena_menu();

        // Načtení skore z textovyho souboru
        try {
            File soubor_skore = new File("skore.txt");
            Scanner cteni_souboru = new Scanner(soubor_skore);

            while (cteni_souboru.hasNextInt()){
                int data = cteni_souboru.nextInt();
                scena_menu.pocet_nalezenych_among_us_postavicek = data;
            }
            cteni_souboru.close();

        }
        catch (Exception e) { System.out.println(e); }

        // Among us postavička
        Image obrazek_among_us_postavicky = new Image("among_us_postavicka.png");
        Image obrazek_prekryti_among_us_postavicky = new Image("prekryti_among_us_postavicky.png");
        ImageView among_us_postavicka = new ImageView(obrazek_prekryti_among_us_postavicky);
        among_us_postavicka.setX(random.nextInt(512 - 64));
        among_us_postavicka.setY(random.nextInt(412 - 64));
        vzhled_okna.getChildren().add(among_us_postavicka);

        // Prepnuti zpatky na scenu pro hledani among us postavicky
        scena_menu.tlacitko_pokracovat.setOnAction(event -> {
            hlavni_okno_aplikace.setScene(scena);

            // Nastaveni nahodnych X a Y hodnot pro among us postavicku
            among_us_postavicka.setX(random.nextInt(512 - 64));
            among_us_postavicka.setY(random.nextInt(412 - 64));
            among_us_postavicka.setImage(obrazek_prekryti_among_us_postavicky);

            // Můžeme znovu přičítat skore
            pricteno = false;
        });

        // Zviditelnění among us postavičky když na ni kliknu
        among_us_postavicka.setOnMouseClicked(mouseEvent -> {

            // Nastavení obrazku among us postavičky
            among_us_postavicka.setImage(obrazek_among_us_postavicky);

            // Animace among us postavicky
            animace.setNode(among_us_postavicka);
            animace.setDuration(Duration.millis(2000));
            animace.setByAngle(360);
            animace.play();

            // Prepnuti sceny po dokončení animace
            animace.setOnFinished(event -> {

                // Přičtení k počtu nalezených among us postavicek jestli ještě nebylo přičteno + 1
                if (pricteno == false) {
                    scena_menu.pocet_nalezenych_among_us_postavicek++;
                    scena_menu.text_nalezene_among_us_postavicky.setText("Nalezeno Among Us Postaviček: " + scena_menu.pocet_nalezenych_among_us_postavicek);
                    pricteno = true;
                }

                // Uložení skore do souboru
                try {
                    FileWriter zapis_do_souboru = new FileWriter("skore.txt");
                    BufferedWriter zapis_do_souboru_pro_int = new BufferedWriter(zapis_do_souboru);
                    zapis_do_souboru_pro_int.write(Integer.toString(scena_menu.pocet_nalezenych_among_us_postavicek));
                    zapis_do_souboru_pro_int.close();
                }
                catch (Exception e) { System.out.println(e); }

                // Přepnutí na menu scenu
                hlavni_okno_aplikace.setScene(scena_menu.scena_menu);
            });
        });

        // 4 stupně vzdalenosti
        File zvuk_ctvrty_stupen = new File("sus_ctvrty_stupen.wav");
        File zvuk_treti_stupen = new File("sus_treti_stupen.wav");
        File zvuk_druhy_stupen = new File("sus_druhy_stupen.wav");
        File zvuk_prvni_stupen = new File("sus_prvni_stupen.wav");

        // Zvuk při pohybu myší po okně
        scena.setOnMouseMoved(mouseEvent -> {

            double mys_x = mouseEvent.getX();
            double mys_y = mouseEvent.getY();
            double postavicka_x = among_us_postavicka.getX();
            double postavicka_y = among_us_postavicka.getY();

            // Ctvrty stupen zvuku
            if (mys_x <= postavicka_x - 150 || mys_x >= postavicka_x + 150 || mys_y <= postavicka_y - 150 || mys_y >= postavicka_y + 150){
                try {
                    AudioInputStream hudba = AudioSystem.getAudioInputStream(zvuk_ctvrty_stupen);
                    Clip klip = AudioSystem.getClip();
                    klip.open(hudba);
                    klip.start();
                    Thread.sleep(300);
                }
                catch (Exception e) { System.out.println(e); }
            }

            // Treti stupen zvuku
            else if (mys_x <= postavicka_x - 100 || mys_x >= postavicka_x + 100 || mys_y <= postavicka_y - 100 || mys_y >= postavicka_y + 100){
                try {
                    AudioInputStream hudba = AudioSystem.getAudioInputStream(zvuk_treti_stupen);
                    Clip klip = AudioSystem.getClip();
                    klip.open(hudba);
                    klip.start();
                    Thread.sleep(300);
                }
                catch (Exception e) { System.out.println(e); }
            }

            // Druhy stupen zvuku
            else if (mys_x <= postavicka_x - 50 || mys_x >= postavicka_x + 50 || mys_y <= postavicka_y - 50 || mys_y >= postavicka_y + 50){
                try {
                    AudioInputStream hudba = AudioSystem.getAudioInputStream(zvuk_druhy_stupen);
                    Clip klip = AudioSystem.getClip();
                    klip.open(hudba);
                    klip.start();
                    Thread.sleep(300);
                }
                catch (Exception e) { System.out.println(e); }
            }

            // Prvni stupen zvuku
            else if (mys_x <= postavicka_x - 10 || mys_x >= postavicka_x + 10 || mys_y <= postavicka_y - 10 || mys_y >= postavicka_y + 10){
                try {
                    AudioInputStream hudba = AudioSystem.getAudioInputStream(zvuk_prvni_stupen);
                    Clip klip = AudioSystem.getClip();
                    klip.open(hudba);
                    klip.start();
                    Thread.sleep(300);
                }
                catch (Exception e) { System.out.println(e); }
            }
        });

        hlavni_okno_aplikace.setScene(scena);
        hlavni_okno_aplikace.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
