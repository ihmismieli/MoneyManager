package fi.ahlgren.moneymanager.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

//tells spring that this class is a part of components in your application, which can be use elsewhere in the application
@Component
public class CategoryKeywords {
    private Map<String, List<String>> categoryMap;

    //class contructor, first an empty hashMap object and inizializeCategories fills categoryMap with categories and keywords
    //ensures that categoryMap is ready to use
    public CategoryKeywords() {
        categoryMap = new HashMap<>();
        inizializeCategories();
    }

    //inizializes categories by assigning a category as the key and a list of recipients as the value
    private void inizializeCategories() {

        categoryMap.put("Personal transfer", Arrays.asList("omatilisiirto", "NORDNET"));

        // income
        categoryMap.put("Income", Arrays.asList("tyollisyysrahasto", "HOK-ELANTO", "Kela", "Kansanelakelaitos", "Verohallinto"));

        // Food
        categoryMap.put("Food", Arrays.asList("K-supermarket", "LIDL", "K-Market", "S-market",
                "Alepa", "PRISMA", "K-Citymarket", "R-kioski", "Sale", "Extra", "Maxi ica", "7-Eleven"));

        // Restaurants and cafes
        categoryMap.put("Restaurant", Arrays.asList("Compass Group Finland Oy", "Restaurant", "ravintola", "ravintol", "ravinto", "McDonald's", "MCD", "Momotoko", "Hesburger","HESB", "Pizza Hut", "Wolt", "Foodora", "Subway", "KFC", "pizza", "pizzeria", "pizza", "cafe", "Caffe", "Trattoria", "YLVA", "JAATELOKIOSKI", "GELATTERIA", "Grill it", "Jungle juice", "sushi", "Amarillo", "Belge", "Catering", "Kappeli", "Wok" ));

        // Bar and Alko
        categoryMap.put("Bar", Arrays.asList("bar", "baari","Pub", "Club", "Alko", "ROCK AND ROLL", "Satama Ba", "Apollo", "Harjun huone", "on the rocks", "lepis", "BK","Vartiointipalve", "Breweries", "Grotesk", "Kaarle", "Rooftop"));

        // Streaming
        categoryMap.put("Streaming", Arrays.asList("YouTube", "Netflix", "Spotify", "HBO Max",
                "Disney+", "Viaplay", "Amazon Prime Video", "Apple TV+", "C More", "Ruutu", "Microsoft", "DPLAY"));

        // Phone
        categoryMap.put("Phone", Arrays.asList("Elisa", "Telia", "DNA", "Moi Mobiili", "Saunalahti"));

        // Public Transport
        categoryMap.put("Public Transport", Arrays.asList("HSL", "VR", "Matkahuolto", "Onnibus",
                "Nysse", "liikenne", "FFS", "train", "Manor AG", "STF", "Vy Tag", "CITYBIKE", "metro"));

        // Gas station
        categoryMap.put("Gas station", Arrays.asList("Neste", "Shell", "Teboil", "St1", "Bensiini", "ABC", "Juustoportti", "Juustoportti catering"));

        // Taxi
        categoryMap.put("Taxi", Arrays.asList("Taksi", "Taxi", "Meneva", "Kovanen", "Uber", "Bolt"));

        // Beauty
        categoryMap.put("Beauty", Arrays.asList("beauty", "kauneus", "klinikka", "HejBuddy", "Hair", "Tmi Susanna Ma", "TMI SESILIA RA", "FLOW21", "LOOKGOOD", "koko", "orchidee"));

        // Health
        categoryMap.put("Health", Arrays.asList("Oloni", "Hieronta", "Osteopaatti"));

        // Hobbies
        categoryMap.put("Hobbies", Arrays.asList("fitness", "Elixia", "Fressi", "Gym", "Fit", "sali", "Paybyway"));

        // Events
        categoryMap.put("Events", Arrays.asList("teatteri", "kulttuuri", "museo", "Festival", "messu", "sarkanniemi", "festival"));

        // Shopping
        categoryMap.put("Shopping", Arrays.asList("Zalando", "H&M", "HM", "KappAhl", "Lindex",
                "Stockmann", "Seppälä", "Gina Tricot", "Vero Moda", "Rock Shop", "Svea Payments Oy", "VFI*NIMBUS GRUPPEN OY", "Tokmanni", "Clas Ohlson", "INTERSPORT", "Normal", "Ninepine", "Scandinavian outdoor", "sokos", "stadium", "suomalainen kirjakauppa", "temu", "Ruohonjuuri", "Granit", "New yorker", "zara", "partioaitta"));

        // Renovation
        categoryMap.put("Renovation", Arrays.asList("K-Rauta", "Bauhaus", "Stark", "Hong Kong", "Puuilo", "Biltema", "Byggmax", "Rautia"));

        //Travelling
        categoryMap.put("Travelling", Arrays.asList("Hotel", "Hote", "Break So","solo sok" ));

        //pharmacy
        categoryMap.put("Pharmacy", Arrays.asList("APTEEKKI", "apteekk", "YA" ));

        // Living (rent, loan, electricity)
        // categoryMap.put("Living", Arrays.asList("Fortum", "Vattenfall", "Helen", "VVO", "SATO",
        //         "HEKA", "KOJAMO", "Nordea", "Osuuspankki", "OP", "Danske Bank", "S-Pankki", "Handelsbanken"));
    }

    //Returns keywords that are joined to the category, if category is not found from categoryMap variable, it returns an empty list
    public List<String> getKeywordsByCategory(String category) {
        if (categoryMap.containsKey(category)) {
            return categoryMap.get(category);
        } else {
            return new ArrayList<>();
        }
    }

    public Map<String, List<String>> getAllCategories() {
        return categoryMap;
    }

    public Map<String, List<String>> getCategoryMap() {
        return categoryMap;
    }
}