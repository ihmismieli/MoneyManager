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

        categoryMap.put("Personal transfer", Arrays.asList("omatilisiirto", "NORDNET", "JUSLIN JUKKA TAPANI", "JUKKA TAPANI JUSLIN"));

        // income
        categoryMap.put("Income", Arrays.asList("tyollisyysrahasto", "HOK-ELANTO", "Kela", "Kansanelakelaitos", "Verohallinto", "Valiolentäjät ry", "Valiolentajat ry", "HAAGA-HELIA AMMATTIKORKEAKOULU", "Matias", "MATIAS PETTERI MALILA", "MTY PESULA ANSELMI JA ELMERI", "AS OY TAITEENTEKIJÄNTIE 8"));

        // Food
        categoryMap.put("Food", Arrays.asList("K-supermarket", "LIDL", "K-Market", "S-market",
                "Alepa", "PRISMA", "K-Citymarket",  "Sale", "Extra", "Maxi ica", "7-Eleven",  "7034 Munkkivuori", "MUNKKIVUOREN"));

        //R-kioski
        categoryMap.put("R-kioski", Arrays.asList("R-kioski","R-HELSINKI-Munkkivuori", "Rkioskioy", "R-HELSINKI-Munkkiniemi" ));

        // Restaurants and cafes
        categoryMap.put("Restaurant", Arrays.asList("Compass Group Finland","Compass Group Finland Oy",  "Restaurant", "ravintola", "ravintol", "ravinto", "McDonald's", "MCD", "Momotoko", "Hesburger","HESB", "Pizza Hut", "Wolt", "Foodora", "Subway", "KFC", "pizza", "pizzeria", "pizza", "cafe", "Caffe", "Trattoria", "YLVA", "JAATELOKIOSKI", "GELATTERIA", "Grill it", "Jungle juice", "sushi", "Amarillo", "Belge", "Catering", "Kappeli", "Wok" , "HELSINKI BUSINE", "SINBRYCHOFF", "Burger King Hyvink??", "KANRESTA OY", "Hyvink?? Sveitsinhovi", "R-HELSINKI-Tripla-Asem", "Kahvilat", "Titavuu Oy", "3366 Neptun Compass", "R_HELSINKI_Munkkiniemi", "kahvila", "Ingman"));

        // Bar and Alko
        categoryMap.put("Bar", Arrays.asList("bar", "baari","Pub", "Club", "Alko", "ROCK AND ROLL", "Satama Ba", "Apollo", "Harjun huone", "on the rocks", "lepis", "BK","Vartiointipalve", "Breweries", "Grotesk", "Kaarle", "Rooftop"));

        // Streaming
        categoryMap.put("Streaming", Arrays.asList("YouTube", "Netflix", "Spotify", "HBO Max",
                "Disney+", "Viaplay", "Amazon Prime Video", "Apple TV+", "C More", "Ruutu", "Microsoft", "DPLAY", "SANOMA MEDIA FINLAND OY", "sanoma"));

        // Phone
        categoryMap.put("Phone", Arrays.asList("Elisa", "Telia", "DNA Oyj", "Moi Mobiili", "Saunalahti"));

        // Public Transport
        categoryMap.put("Public Transport", Arrays.asList("HSL", "VR", "Matkahuolto", "Onnibus",
                "Nysse", "liikenne", "FFS", "train", "Manor AG", "STF", "Vy Tag", "CITYBIKE", "metro", "VR-YHTYMÄ OYJ", "VR-YHTYMA OYJ"));

        // Gas station
        categoryMap.put("Gas station", Arrays.asList("Neste", "Shell", "Teboil", "St1", "Bensiini", "ABC", "Juustoportti", "Juustoportti catering", "SEO", "NESTE EXPRESS HYVINK??"));

        // Taxi
        categoryMap.put("Taxi", Arrays.asList("Taksi", "Taxi", "Meneva", "Kovanen", "Uber", "Bolt"));

        // Beauty
        categoryMap.put("Beauty", Arrays.asList("beauty", "kauneus", "klinikka", "HejBuddy", "Hair", "Tmi Susanna Ma", "TMI SESILIA RA", "FLOW21", "LOOKGOOD", "koko", "orchidee", "Parturi"));

        // Health
        categoryMap.put("Health", Arrays.asList("Oloni", "Hieronta", "Osteopaatti"));

        // Hobbies
        categoryMap.put("Hobbies", Arrays.asList("fitness", "Elixia", "Fressi", "Gym", "Fit", "sali", "Paybyway", "Talihalli", "POLYTEKNIKKOJEN ILMAILUKERHO R", "HELSINKI HACKLAB RY", "Juha Heikkinen kerhomaksu", "Keravan lennokkikerho", "SUOMEN ILMAILULIITTO R.Y.", "Zettle-*Nordic Padel O", "SuperPark", "Helsingin polkupyöräilijät", "Seikkailupuisto Zippy"));

        // Events
        categoryMap.put("Events", Arrays.asList("teatteri", "kulttuuri", "museo", "Festival", "messu", "sarkanniemi", "festival", "ILMAILUMUSEO"));

        // Shopping
        categoryMap.put("Shopping", Arrays.asList("Zalando", "H&M", "HM", "KappAhl", "Lindex",
                "Stockmann", "Seppälä", "Gina Tricot", "Vero Moda", "Rock Shop", "Svea Payments Oy", "VFI*NIMBUS GRUPPEN OY", "Tokmanni", "Clas Ohlson", "INTERSPORT", "Normal", "Ninepine", "Scandinavian outdoor", "sokos", "stadium", "Suomalainen Kirjakaupp", "suomalainen kirjakauppa", "temu", "Ruohonjuuri", "Granit", "New yorker", "zara", "partioaitta", "VERKKOKAUPPA.COM MYYMA", "HOBBY POINT", "Brahen Antikvariaatti", "PICNIC", "Verifone Fin*Antikvari", "Dream Hobb", "Etola", "motonet", "jysk"));

        // Renovation
        categoryMap.put("Renovation", Arrays.asList("K-Rauta", "Bauhaus", "Stark", "Hong Kong", "Puuilo", "Biltema", "Byggmax", "Rautia", "K-Rauta", "Hyvinkaan Puuta", "HYVINKÄÄN RAKENNUS JA PINNOITU", "rakennus"));

        //Travelling
        categoryMap.put("Travelling", Arrays.asList("Hotel", "Hote", "Break So","solo sok", "AAWA", "Caravan" ));

        //pharmacy
        categoryMap.put("Pharmacy", Arrays.asList("APTEEKKI", "apteekk", "YA", "Hyvink??n Willan Kehr?", "APTEEK" ));

        //cash
        categoryMap.put("Cash", Arrays.asList("ATM", "ATM otto/Otto"));

        //saving
        categoryMap.put("Saving", Arrays.asList("Nordea Maailma Passiivine"));

        //Loan
        categoryMap.put("Loan", Arrays.asList("Nordea Bank Oyj"));

        //Living (electricity, insurance)
        categoryMap.put("Living", Arrays.asList("If Vahinkovakuutus Oyj, Suomen sivu", "if", "Keskinäinen Vakuutusyhtiö Turva", "Keskinäinen Vakuutusyhtiö ", "Mandatum Henkivakuutusosakeyhtiö", "Kiertokapula Oy", "Mandatum"));

        //Apartment
        categoryMap.put("Taiteentekijantie", Arrays.asList("Asunto-Oy-Taiteentekijäntie-8"));
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