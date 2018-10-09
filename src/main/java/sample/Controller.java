package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;


public class Controller {
    @FXML
    private TextField cityField;

    @FXML
    private TextField stateField;

    @FXML
    private Pane conditionsPane;
    @FXML
    private Label currentWeather;
    @FXML
    private Label temperature;
    @FXML
    private Label wind;
    @FXML
    private Label feelsLike;
    @FXML
    Label lastUpdated;
    @FXML
    Label station;
    @FXML
    Label pressure;
    @FXML
    AnchorPane ap;


    public String city;
    public String state;


    public void handleSubmitButtonAction(ActionEvent actionEvent) {
        String url = Credentials.getUrl();
        System.out.println("URL: " + url);
        System.out.println("Table: " + Credentials.getTable());
        System.out.println("Done.");


        city = cityField.getText().replace(" ", "_").replace(".", "");
        state = stateField.getText();

        String website = "http://api.wunderground.com/api/" + Credentials.getApiKey() + "/conditions/q/" + state + "/" + city + ".xml";
        String xml = readFromURL(website);
        Document doc = Jsoup.parse(xml, "", Parser.xmlParser());
        String conditions = doc.select("weather").text();
        System.out.println(doc.select("link").text());
        Elements cities = doc.select("city");
        if (cities.first() != null) {
            try {
                currentWeather.setText(doc.select("weather").text());
                wind.setText(doc.select("wind_string").text());
                temperature.setText(doc.select("temperature_string").text());
                feelsLike.setText(doc.select("feelslike_string").text());
                lastUpdated.setText(doc.select("observation_time").text());
                station.setText(doc.select("station_id").text());
                pressure.setText(doc.select("pressure_in").text());
                conditionsPane.setVisible(true);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String readFromURL(String urlString) {
        String data = "";
        try {
            URL url = new URL(urlString);
            BufferedReader input = new BufferedReader(
                    new InputStreamReader(url.openStream())
            );
            String s;
            while ((s = input.readLine()) != null) {
                data += s;
            }


            input.close();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            System.exit(2);
        }
        return data;
    }

}
