package fi.ahlgren.moneymanager.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

//tells spring that this class is a part of components in your application, which can be use elsewhere in the application
@Component
public class CategoryKeywords {
    private Map<String, List<String>> categoryMap;

    //class contructor, first an empty hashMap object and inizializeCategories fills categoryMap with categories and keywords
    public CategoryKeywords() {
        categoryMap = new HashMap<>();
        inizializeCategories();
    }

    //inizializes categories by assigning a category as the key and a list of recipients as the value
    private void inizializeCategories() {

        categoryMap.put("Personal transfer", Arrays.asList("omatilisiirto"));

        // income
        categoryMap.put("Income", Arrays.asList("tyollisyysrahasto", "HOK", "Kela", "Kansanelakelaitos"));

        // Living (rent, loan, electricity)
        categoryMap.put("Living", Arrays.asList("Fortum", "Vattenfall", "Helen", "VVO", "SATO",
                "HEKA", "KOJAMO", "Nordea", "Osuuspankki", "OP", "Danske Bank", "S-Pankki", "Handelsbanken"));

        // Food
        categoryMap.put("Food", Arrays.asList("K-supermarket", "LIDL", "K-Market", "S-market",
                "Alepa", "PRISMA", "K-Citymarket", "R-kioski", "Sale"));

        // Restaurants and cafes
        categoryMap.put("Restaurant", Arrays.asList("Compass Group Finland Oy", "Restaurant", "ravintola",
                "McDonald's", "Hesburger", "Pizza Hut", "Wolt", "Foodora", "Subway", "KFC", "pizza", "pizzeria", "Caffe", "Trattoria", "YLVA", "JAATELOKIOSKI"));

        // Bar and Alko
        categoryMap.put("Bar", Arrays.asList("bar", "Club", "Alko", "ROCK AND ROLL", "Satama Ba"));

        // Streaming
        categoryMap.put("Streaming", Arrays.asList("YouTube", "Netflix", "Spotify", "HBO Max",
                "Disney+", "Viaplay", "Amazon Prime Video", "Apple TV+", "C More", "Ruutu"));

        // Phone
        categoryMap.put("Phone", Arrays.asList("Elisa", "Telia", "DNA", "Moi Mobiili", "Saunalahti"));

        // Public Transport
        categoryMap.put("Public Transport", Arrays.asList("HSL", "VR", "Matkahuolto", "Onnibus",
                "Nysse", "liikenne", "FFS", "train", "Manor AG"));

        // Gas
        categoryMap.put("Gas station", Arrays.asList("Neste", "Shell", "Teboil", "St1", "Bensiini", "ABC", "Juustoportti"));

        // Taxi
        categoryMap.put("Taxi", Arrays.asList("Taksi", "Taxi", "Meneva", "Kovanen", "Uber", "Bolt"));

        // Beauty
        categoryMap.put("Beauty", Arrays.asList("beauty", "kauneus", "klinikka", "HejBuddy", "Hair", "Tmi Susanna Majur"));

        // Health
        categoryMap.put("Health", Arrays.asList("Oloni", "Hieronta", "Osteopaatti"));

        // Hobbies
        categoryMap.put("Hobbies", Arrays.asList("fitness", "Elixia", "Fressi", "Gym", "Fit", "sali"));

        // Events
        categoryMap.put("Events", Arrays.asList("teatteri", "kulttuuri", "museo", "Festival", "messu", "sarkanniemi", "festival"));

        // Clothing
        categoryMap.put("Clothing", Arrays.asList("Zalando", "H&M", "KappAhl", "Lindex",
                "Stockmann", "Seppälä", "Gina Tricot", "Vero Moda", "Rock Shop", "Svea Payments Oy", "VFI*NIMBUS GRUPPEN OY"));

        // Renovation
        categoryMap.put("Renovation", Arrays.asList("K-Rauta", "Bauhaus", "Stark", "Hong Kong",
                "Tokmanni", "Puuilo", "Biltema", "Byggmax", "Rautia"));

        //Travelling
        categoryMap.put("Travelling", Arrays.asList("Hotel", "Hote" ));

        //pharmacy
        categoryMap.put("Pharmacy", Arrays.asList("apteekki", "apteekk" ));
    }

    //Returns keywords that are joined to the category, if category is not found from categoryMap variable, it returns an empty list
    public List<String> getKeywordsByCategory(String category) {
        return categoryMap.getOrDefault(category, Collections.emptyList());
    }

    public Map<String, List<String>> getAllCategories() {
        return categoryMap;
    }

    public Map<String, List<String>> getCategoryMap() {
        return categoryMap;
    }
}