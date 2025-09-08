package com.example.backend.controller;

import com.example.backend.model.Book;
import com.example.backend.repo.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@CrossOrigin
public class BookController {
    private final BookRepository repo;
    public BookController(BookRepository repo) { this.repo = repo; }

    @GetMapping
    public List<Book> all() { return repo.findAll(); }

    @GetMapping("/{id}")
    public ResponseEntity<Book> one(@PathVariable String id) {
        return repo.findById(id).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Book create(@RequestBody Book b) { return repo.save(b); }

    @PutMapping("/{id}")
    public ResponseEntity<Book> update(@PathVariable String id, @RequestBody Book b) {
        return repo.findById(id).map(existing -> {
            existing.setTitle(b.getTitle());
            existing.setAuthor(b.getAuthor());
            existing.setIsbn(b.getIsbn());
            existing.setPrice(b.getPrice());
            return ResponseEntity.ok(repo.save(existing));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        if (repo.existsById(id)) {
            repo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
