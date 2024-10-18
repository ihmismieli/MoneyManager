package fi.ahlgren.moneymanager.service;

//This class categorizes transactions by recipient

import java.util.List;
import java.util.Map;

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

    @Autowired
    private CategoryKeywords categoryKeywords;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryKeywords = new CategoryKeywords();
        saveCategories();
    }

    // saves categories to categoryRepository
    public void saveCategories() {
        for (Map.Entry<String, List<String>> entry : categoryKeywords.getCategoryMap().entrySet()) {
            String categoryName = entry.getKey();
            if (categoryRepository.findByName(categoryName) == null) {
                Category category = new Category();
                category.setName(categoryName);
                categoryRepository.save(category);
            }
        }
    }

    public String getCategory(String recipient, String payer, String transactionType) {
        if (payer.equalsIgnoreCase("Ahlgren Heidi")
                || payer.equalsIgnoreCase("Heidi Ahlgren") && recipient.equalsIgnoreCase("Heidi Ahlgren")) {
            return "Personal transfer";
        }

        if (payer.equalsIgnoreCase("Heidi Elina Ahlgren")
                || payer.equalsIgnoreCase("Ahlgren Heidi Elina") && recipient.equalsIgnoreCase("Heidi Elina Ahlgren")) {
            return "Personal transfer";
        }

        if (payer.equalsIgnoreCase("HOK-ELANTO LIIKETOIMINTA OY") && recipient.equalsIgnoreCase("Ahlgren Heidi")) {
            return "Income";
        }

        if (payer.equalsIgnoreCase("tyollisyysrahasto") || payer.equalsIgnoreCase("VIPPS MOBILEPAY AS, SUOMEN SIVULII")
                || payer.equalsIgnoreCase("verohallinto") || payer.contains(("MobilePay"))) {
            return "Income";
        }
        // checks if payer is not heidi and recipient is heidi
        if (!payer.equalsIgnoreCase("Heidi Ahlgren") && recipient.equalsIgnoreCase("Heidi Ahlgren")) {
            return "Income";
        }

        // checks keywords from CategoryKeywords and returns a category
        for (Map.Entry<String, List<String>> entry : categoryKeywords.getCategoryMap().entrySet()) {
            for (String keyword : entry.getValue()) {
                if (recipient.toLowerCase().contains(keyword.toLowerCase())) {
                    return entry.getKey();
                }
            }
        }
        return "Other";
    }

    public Category getCategoryByRecipientObject(String recipient, String payer, String transactionType) {
        String categoryName = getCategory(recipient, payer, transactionType);
        Category category = categoryRepository.findByName(categoryName);

        if (category == null) {
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
