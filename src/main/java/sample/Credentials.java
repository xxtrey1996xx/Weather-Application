package sample;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Credentials {
    private static String user = "u";
    private static String password = "p";
    private static int port = 0;
    private static String url = "r";
    private static String table = "t";
    private static String apiKey = "k";

    private static boolean populate() {
        boolean success = false;
        if (url.length() < 2) {
            File inputFile = new File("credentials.xml");
            String xml = "";

            //read the file into a single string
            try {
                Scanner input = new Scanner(inputFile);
                while (input.hasNextLine()) {
                    xml += input.nextLine();
                }

            } catch (IOException e) {
                System.err.println(e.getMessage());
                e.printStackTrace();
                System.exit(1);
            }

            // PROCESS THE URL
            //This uses the XMLParser which does not assume html tags.
            Document doc = Jsoup.parse(xml, "", Parser.xmlParser());
            //System.out.println(doc.select("credentials"));

            //get The URL
            Elements urlNodes = doc.select("url");
            //There should be only one url node
            Element urlNode = urlNodes.first();
            url = urlNode.text();
            port = Integer.valueOf(urlNode.attr("port"));


            //Get the table name, this time demonstrating one line instead of 3
            table = doc.select("table").first().text();


            Elements apiKeys = doc.select("weatherUnderground");
            Element key = apiKeys.first();
            apiKey = key.attr("key");

            Elements userNames = doc.select("user");
            Element userName = userNames.first();
            user = userName.attr("UserId");
            password = userName.attr("password");

            if (user == "u" || password == "p" || port == 0 || url == "r" || table == "t" || apiKey == "k") {
                System.err.println("MALFORMED XML FILE! PLEASE TRY AGAIN");
                System.exit(653);
            } else {
                success = true;
            }
        }
        return success;
    }

    public static String getUser() {
        populate();
        return user;
    }

    public static String getPassword() {
        populate();
        return password;
    }

    public static int getPort() {
        populate();
        return port;
    }

    public static String getUrl() {
        populate();
        return url;
    }

    public static String getTable() {
        populate();
        return table;
    }

    public static String getApiKey() {
        populate();
        return apiKey;
    }
}
