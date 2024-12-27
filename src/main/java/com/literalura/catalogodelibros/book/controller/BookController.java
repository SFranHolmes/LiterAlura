package com.literalura.catalogodelibros.book.controller;

import com.literalura.catalogodelibros.book.dto.BookDTO;
import com.literalura.catalogodelibros.book.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/search")
    public ResponseEntity<BookDTO> buscarLibros(@RequestParam String query){
        List<BookDTO> libros = bookService.buscarLibros(query);
        return ResponseEntity.ok(libros);
    }

}
