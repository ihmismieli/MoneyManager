package fi.ahlgren.moneymanager.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

    Category findByName(String name);
}
