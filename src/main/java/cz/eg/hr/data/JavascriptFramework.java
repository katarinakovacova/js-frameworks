package cz.eg.hr.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Map;

/**
 * Class that represents a JavaScript framework
 */
@Entity
public class JavascriptFramework {

    /**
     * Primary key for persistent storage
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Name for framework
     */
    @NotBlank
    @Size(max = 30)
    @Column(nullable = false, length = 30, unique = true)
    private String name;

    /**
     * Framework rating
     */
    @Min(1)
    @Max(5)
    @Column
    private float rating;

    /**
     * Map of framework version by its end of support date (YYYY-MM-DD)
     */
    @ElementCollection
    private Map<@NotBlank String, @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$") String> endOfSupportByVersion;

    public JavascriptFramework() {
    }

    public JavascriptFramework(String name, float rating, Map<String, String> endOfSupportByVersion) {
        this.name = name;
        this.rating = rating;
        this.endOfSupportByVersion = endOfSupportByVersion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public Map<String, String> getEndOfSupportByVersion() {
        return endOfSupportByVersion;
    }

    public void setEndOfSupportByVersion(Map<String, String> endOfSupportByVersion) {
        this.endOfSupportByVersion = endOfSupportByVersion;
    }

    @Override
    public String toString() {
        return "JavaScriptFramework [id=" + id +
            ", name=" + name +
            ", rating=" + rating +
            ", endOfSupportByVersion=" + endOfSupportByVersion +
            "]";
    }

}
