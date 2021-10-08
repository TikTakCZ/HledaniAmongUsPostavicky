package sample;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class ScenaMenu {

    // Počet nalezených among us postaviček
    public int pocet_nalezenych_among_us_postavicek = 0;

    // Text počet nalezenych among us postavicek
    public Text text_nalezene_among_us_postavicky = new Text();

    // Tlacitko pokracovat
    public Button tlacitko_pokracovat = new Button();

    Group vzhled_okna_menu = new Group();
    Scene scena_menu = new Scene(vzhled_okna_menu);

    public Scene scena_menu(){

        // Počet nalezených among us postaviček
        text_nalezene_among_us_postavicky.setText("Nalezeno Among Us Postaviček: " + pocet_nalezenych_among_us_postavicek);
        text_nalezene_among_us_postavicky.setFont(Font.font("Times New Roman", 20));
        text_nalezene_among_us_postavicky.setX(15);
        text_nalezene_among_us_postavicky.setY(25);
        vzhled_okna_menu.getChildren().add(text_nalezene_among_us_postavicky);

        // Tlačítko pokračovat ve hře
        tlacitko_pokracovat.setText("Pokračovat");
        tlacitko_pokracovat.setFont(Font.font("Times New Roman", 16));
        tlacitko_pokracovat.setLayoutX(190);
        tlacitko_pokracovat.setLayoutY(190);
        vzhled_okna_menu.getChildren().add(tlacitko_pokracovat);

        return scena_menu;
    }
}
