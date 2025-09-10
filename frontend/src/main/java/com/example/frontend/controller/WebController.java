package com.example.frontend.controller;

import com.example.frontend.client.BookClient;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Validated
public class WebController {
  private final BookClient client;
  public WebController(BookClient client) { this.client = client; }

  /** Fallback: allow binding directly to fields if getters/setters are ever missing */
  @InitBinder
  public void initBinder(WebDataBinder binder) {
    binder.initDirectFieldAccess();
  }

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
  public String create(@ModelAttribute("form") @Valid BookForm form,
                       BindingResult binding,
                       Model model,
                       RedirectAttributes ra) {
    if (binding.hasErrors()) {
      return "add";
    }
    BookClient.BookDto dto = new BookClient.BookDto();
    dto.setTitle(form.getTitle());
    dto.setAuthor(form.getAuthor());
    dto.setIsbn(form.getIsbn());
    dto.setPrice(form.getPrice());
    client.create(dto);
    ra.addFlashAttribute("toast", "Book added successfully.");
    return "redirect:/books";
  }

  @PostMapping("/books/{id}/delete")
  public String delete(@PathVariable String id, RedirectAttributes ra) {
    try {
      boolean removed = client.delete(id);
      ra.addFlashAttribute("toast", removed ? "Book deleted." : "Book was already deleted.");
    } catch (Exception e) {
      ra.addFlashAttribute("error", "Delete failed: " + e.getClass().getSimpleName());
    }
    return "redirect:/books";
  }

  @Data
  public static class BookForm {
    @NotBlank private String title;
    @NotBlank private String author;
    @NotBlank private String isbn;
    @NotNull  private Double price;
  }
}

