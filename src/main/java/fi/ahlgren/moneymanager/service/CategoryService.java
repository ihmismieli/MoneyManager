package fi.ahlgren.moneymanager.service;

//This class categorizes transactions by recipient

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import fi.ahlgren.moneymanager.domain.Category;
import fi.ahlgren.moneymanager.domain.CategoryRepository;

@Service
@Transactional
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    

    // public void addCategory(Category category) {
    //     categoryRepository.save(category);
    // }

    private Map<String, String> categoryMap;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryMap = new HashMap<>();
        createCategoryMap();
        saveCategories();
    }

    private void createCategoryMap() {
        categoryMap.put("omatilisiirto", "Personal transfer");

        //Income
        categoryMap.put("tyollisyysrahasto", "Income");
        categoryMap.put("HOK", "Income");
        categoryMap.put("Kela", "Income");
        categoryMap.put("Kansanelakelaitos", "Income");

        // Living (rent, loan, electricity)
        categoryMap.put("Fortum", "Living");
        categoryMap.put("Vattenfall", "Living");
        categoryMap.put("Helen", "Living");
        categoryMap.put("VVO", "Living");
        categoryMap.put("SATO", "Living");
        categoryMap.put("HEKA", "Living");
        categoryMap.put("KOJAMO", "Living");
        categoryMap.put("Nordea", "Living");
        categoryMap.put("Osuuspankki", "Living");
        categoryMap.put("OP", "Living");
        categoryMap.put("Danske Bank", "Living");
        categoryMap.put("S-Pankki", "Living");
        categoryMap.put("Handelsbanken", "Living");

        // Food
        categoryMap.put("K-supermarket", "Food");
        categoryMap.put("LIDL", "Food");
        categoryMap.put("K-Market", "Food");
        categoryMap.put("S-market", "Food");
        categoryMap.put("Alepa", "Food");
        categoryMap.put("PRISMA", "Food");
        categoryMap.put("K-Citymarket", "Food");
        categoryMap.put("R-kioski", "Food");
        categoryMap.put("Sale", "Food");

        // Restaurants
        categoryMap.put("Compass Group Finland Oy", "Restaurant");
        categoryMap.put("Restaurant", "Restaurant");
        categoryMap.put("ravintola", "Restaurant");
        categoryMap.put("McDonald's", "Restaurant");
        categoryMap.put("Hesburger", "Restaurant");
        categoryMap.put("Pizza Hut", "Restaurant");
        categoryMap.put("Wolt", "Restaurant");
        categoryMap.put("Foodora", "Restaurant");
        categoryMap.put("Subway", "Restaurant");
        categoryMap.put("KFC", "Restaurant");
        categoryMap.put("pizza", "Restaurant");
        categoryMap.put("pizzeria", "Restaurant");

        // Bar
        categoryMap.put("bar", "Bar");
        categoryMap.put("Club", "Bar");

        // Streaming
        categoryMap.put("YouTube", "Streaming");
        categoryMap.put("Netflix", "Streaming");
        categoryMap.put("Spotify", "Streaming");
        categoryMap.put("HBO Max", "Streaming");
        categoryMap.put("Disney+", "Streaming");
        categoryMap.put("Viaplay", "Streaming");
        categoryMap.put("Amazon Prime Video", "Streaming");
        categoryMap.put("Apple TV+", "Streaming");
        categoryMap.put("C More", "Streaming");
        categoryMap.put("Ruutu", "Streaming");

        // Insurance
        // categoryMap.put("If", "Insurance");
        // categoryMap.put("Lähitapiola", "Insurance");
        // categoryMap.put("OP Vakuutus", "Insurance");
        // categoryMap.put("Fennia", "Insurance");
        // categoryMap.put("Turva", "Insurance");
        // categoryMap.put("Pohjola Vakuutus", "Insurance");
        // categoryMap.put("Alandia", "Insurance");
        // categoryMap.put("Aktia", "Insurance");
        // categoryMap.put("Säästöpankki Vakuutus", "Insurance");
        // categoryMap.put("Veritas", "Insurance");
        // categoryMap.put("Mandatum", "Insurance");
        // categoryMap.put("vakuutus", "Insurance");

        // Phone and web
        categoryMap.put("Elisa", "Phone");
        categoryMap.put("Telia", "Phone");
        categoryMap.put("DNA", "Phone");
        categoryMap.put("Moi Mobiili", "Phone");
        categoryMap.put("Saunalahti", "Phone");

        // Public Transport
        categoryMap.put("HSL", "Public Transport");
        categoryMap.put("VR", "Public Transport");
        categoryMap.put("Matkahuolto", "Public Transport");
        categoryMap.put("Onnibus", "Public Transport");
        categoryMap.put("Nysse", "Public Transport");
        categoryMap.put("liikenne", "Public Transport");

        // Gas
        categoryMap.put("Neste", "Gas");
        categoryMap.put("Shell", "Gas");
        categoryMap.put("Teboil", "Gas");
        categoryMap.put("St1", "Gas");
        categoryMap.put("Bensiini", "Gas");
        categoryMap.put("ABC", "Gas");

        // TAXI
        categoryMap.put("Taksi", "Taxi");
        categoryMap.put("Taxi", "Taxi");
        categoryMap.put("Meneva", "Taxi");
        categoryMap.put("Kovanen", "Taxi");
        categoryMap.put("Uber", "Taxi");
        categoryMap.put("Bolt", "Taxi");

        // Beauty
        categoryMap.put("beauty", "Beauty");
        categoryMap.put("kauneus", "Beauty");
        categoryMap.put("klinikka", "Beauty");

        // Health
        categoryMap.put("Oloni", "Health");
        categoryMap.put("Hieronta", "Health");
        categoryMap.put("Osteopaatti", "Health");

        // Hobbies
        categoryMap.put("fitness", "Hobbies");
        categoryMap.put("Elixia", "Hobbies");
        categoryMap.put("Fressi", "Hobbies");
        categoryMap.put("Gym", "Hobbies");
        categoryMap.put("Fit", "Hobbies");
        categoryMap.put("sali", "Hobbies");

        // Events
        categoryMap.put("teatteri", "Events");
        categoryMap.put("kulttuuri", "Events");
        categoryMap.put("museo", "Events");
        categoryMap.put("Festival", "Events");
        categoryMap.put("messu", "Events");

        // Clothing
        categoryMap.put("Zalando", "Clothing");
        categoryMap.put("H&M", "Clothing");
        categoryMap.put("KappAhl", "Clothing");
        categoryMap.put("Lindex", "Clothing");
        categoryMap.put("Stockmann", "Clothing");
        categoryMap.put("Seppälä", "Clothing");
        categoryMap.put("Gina Tricot", "Clothing");
        categoryMap.put("Vero Moda", "Clothing");

        // Renovation
        categoryMap.put("K-Rauta", "Renovation");
        categoryMap.put("Bauhaus", "Renovation");
        categoryMap.put("Stark", "Renovation");
        categoryMap.put("Hong Kong", "Renovation");
        categoryMap.put("Tokmanni", "Renovation");
        categoryMap.put("Puuilo", "Renovation");
        categoryMap.put("Biltema", "Renovation");
        categoryMap.put("Byggmax", "Renovation");
        categoryMap.put("Rautia", "Renovation");

    }


    public void saveCategories(){
        for (String categoryName : categoryMap.values()) {
            if (categoryRepository.findByName(categoryName) == null) {
                Category category = new Category();
                category.setName(categoryName);
                categoryRepository.save(category);
            }
        }
    }

    public String getCategory(String recipient, String payer, String transactionType) {
        if (payer.equalsIgnoreCase("Ahlgren Heidi") || payer.equalsIgnoreCase("Heidi Ahlgren") && recipient.equalsIgnoreCase("Heidi Ahlgren")) {
            return "Personal transfer";
        }

        if (payer.equalsIgnoreCase("tyollisyysrahasto")) {
            return "Income";
        }
        //checks if payer is not heidi and recipient is heidi
        if (!payer.equalsIgnoreCase("Heidi Ahlgren") && recipient.equalsIgnoreCase("Heidi Ahlgren")) {
            return "Income";
        }

        for (Map.Entry<String, String> entry : categoryMap.entrySet()) {
            if (recipient.toLowerCase().contains(entry.getKey().toLowerCase())) {
                return entry.getValue();
            }
        }

        return "Other";
    }

    public Category getCategoryByRecipientObject(String recipient, String payer, String transactionType){
        String categoryName = getCategory(recipient, payer, transactionType);
        Category category = categoryRepository.findByName(categoryName);
    
        if(category == null){
            category = categoryRepository.findByName("Other");
            if (category == null) {
                category = new Category();
                category.setName("Other");
                categoryRepository.save(category);
            }
        }
        return category;
    }
}
