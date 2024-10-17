package fi.ahlgren.moneymanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import fi.ahlgren.moneymanager.domain.AppUser;
import fi.ahlgren.moneymanager.domain.AppUserRepository;
import fi.ahlgren.moneymanager.domain.CategoryRepository;
import fi.ahlgren.moneymanager.domain.Transaction;
import fi.ahlgren.moneymanager.domain.TransactionRepository;



@SpringBootApplication
public class MoneymanagerApplication {

	private static final Logger log = LoggerFactory.getLogger(MoneymanagerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(MoneymanagerApplication.class, args);
	}

	@Bean
	public CommandLineRunner transactionDemo(CategoryRepository categoryRepository, TransactionRepository transactionRepository, AppUserRepository appUserRepository){
		return (args) -> {

			createUserIfNotFound(appUserRepository, "user", "$2a$10$ZgZhgrczZubse6IzjJe9vO3MayZtLeOaW5yjD15DSjTRsDh860y2G", "USER");

			createUserIfNotFound(appUserRepository, "admin", "$2a$10$ppC47QIlMYsg7LU8YdR2Wu3Xp8IA9x3pO.X4ePWW0TmVUU3XHza7.", "ADMIN");
			
			log.info("fetch all transactions");
			for (Transaction transaction : transactionRepository.findAll()) {
				log.info(transaction.toString());
			}
		};
	}

	//creates new users
	private void createUserIfNotFound(AppUserRepository appUserRepository, String username, String password, String role){
		if (appUserRepository.findByUsername(username) == null) {
			AppUser appUser = new AppUser(username, password, role);
			appUserRepository.save(appUser);
			log.info("New user: ", username);
		} else {
			log.info("User already exists: ", username);
		}
	}

}
