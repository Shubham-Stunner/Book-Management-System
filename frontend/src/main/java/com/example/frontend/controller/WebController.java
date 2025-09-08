package com.example.frontend.controller;

import com.example.frontend.client.BookClient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Controller
@Validated
public class WebController {
    private final BookClient client;
    public WebController(BookClient client) { this.client = client; }

    @GetMapping("/")
    public String index() { return "index"; }

    @GetMapping("/books")
    public String list(Model model) {
        model.addAttribute("books", client.list());
        return "list";
    }

    @GetMapping("/books/add")
    public String add(Model model) {
        model.addAttribute("form", new BookForm());
        return "add";
    }

    @PostMapping("/books")
    public String create(@ModelAttribute("form") @Validated BookForm form) {
        BookClient.BookDto dto = new BookClient.BookDto();
        dto.setTitle(form.title);
        dto.setAuthor(form.author);
        dto.setIsbn(form.isbn);
        dto.setPrice(form.price);
        client.create(dto);
        return "redirect:/books";
    }

    @PostMapping("/books/{id}/delete")
    public String delete(@PathVariable String id) {
        client.delete(id);
        return "redirect:/books";
    }

    public static class BookForm {
        @NotBlank public String title;
        @NotBlank public String author;
        @NotBlank public String isbn;
        @NotNull  public Double price;
    }
}
