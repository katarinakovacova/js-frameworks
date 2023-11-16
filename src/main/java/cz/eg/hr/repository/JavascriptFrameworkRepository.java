package cz.eg.hr.repository;

import cz.eg.hr.data.JavascriptFramework;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface JavascriptFrameworkRepository extends CrudRepository<JavascriptFramework, Long> {
    @Query("SELECT j FROM JavascriptFramework j WHERE LOWER(j.name) LIKE LOWER(CONCAT('%', ?1, '%'))")
    List<JavascriptFramework> findAllByFullTextSearch(String searchTerm);
}
