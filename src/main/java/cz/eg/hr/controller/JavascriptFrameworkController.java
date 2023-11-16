package cz.eg.hr.controller;

import cz.eg.hr.data.JavascriptFramework;
import cz.eg.hr.repository.JavascriptFrameworkRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * REST controller for /api/v1/frameworks resource
 */
@RestController
@RequestMapping("/api/v1")
public class JavascriptFrameworkController {

    private final JavascriptFrameworkRepository repository;

    @Autowired
    public JavascriptFrameworkController(JavascriptFrameworkRepository repository) {
        this.repository = repository;
    }

    @GetMapping(path = "/frameworks",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public Iterable<JavascriptFramework> getFrameworks(@RequestParam(required = false) String search) {
        if (search == null) {
            return repository.findAll();
        } else {
            return repository.findAllByFullTextSearch(search);
        }
    }

    @GetMapping(path = "/frameworks/{id}",
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JavascriptFramework> getFrameworkById(@PathVariable("id") Long id) {
        return repository.findById(id)
            .map(ResponseEntity::ok)
            .orElse(new ResponseEntity<JavascriptFramework>(NOT_FOUND));
    }

    @DeleteMapping("/frameworks/{id}")
    public void deleteFrameworkById(@PathVariable("id") Long id) {
        repository.deleteById(id);
    }

    @PostMapping(path = "/frameworks",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JavascriptFramework> createFramework(@Valid @RequestBody JavascriptFramework javascriptFramework) {
        JavascriptFramework newJavascriptFramework = repository.save(javascriptFramework);

        return new ResponseEntity<>(newJavascriptFramework, HttpStatus.CREATED);
    }

    @PutMapping(path = "/frameworks/{id}",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<JavascriptFramework> updateFramework(@Valid @RequestBody JavascriptFramework javascriptFramework,
                                                               @PathVariable("id") Long id) {
        JavascriptFramework updateJavascriptFramework = repository.findById(id).orElse(null);

        if (updateJavascriptFramework == null) {
           return new ResponseEntity<JavascriptFramework>(NOT_FOUND);
        }

        updateJavascriptFramework.setName(javascriptFramework.getName());
        updateJavascriptFramework.setRating(javascriptFramework.getRating());
        updateJavascriptFramework.setEndOfSupportByVersion(javascriptFramework.getEndOfSupportByVersion());

        repository.save(updateJavascriptFramework);

        return new ResponseEntity<>(updateJavascriptFramework, HttpStatus.CREATED);
    }
}
