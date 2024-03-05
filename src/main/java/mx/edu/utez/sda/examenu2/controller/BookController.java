package mx.edu.utez.sda.examenu2.controller;

import mx.edu.utez.sda.examenu2.model.Book;
import mx.edu.utez.sda.examenu2.service.BookService;
import mx.edu.utez.sda.examenu2.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api-book")
@CrossOrigin(value = {"*"})
public class BookController {
    @Autowired
    private BookService service;

    @GetMapping("/")
    public ResponseEntity<Response<List<Book>>> getAll(){
        return new ResponseEntity<>(
                this.service.getAll(),
                HttpStatus.OK
        );
    }
    @GetMapping("/{author}")
    public ResponseEntity<Response<List<Book>>> getAllByAuthor(@PathVariable("author") String author){
        return new ResponseEntity<>(
                this.service.getAllByAuthor(author),
                HttpStatus.OK
        );
    }
    @GetMapping("/{name_book}")
    public ResponseEntity<Response<List<Book>>> getAllByName(@PathVariable("name_book")String name_book){
        return new ResponseEntity<>(
                this.service.getAllByName(name_book),
                HttpStatus.OK
        );
    }
    @PutMapping("/")
    public ResponseEntity<Response<Book>> update(BookDto dto){
        return new ResponseEntity<>(
                this.service.insert(dto.getBook()),
                HttpStatus.OK
        );
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> changeStatus(@PathVariable("id") Long id){
        return new ResponseEntity<>(
                this.service.changeStatus(id),
                HttpStatus.OK
        );
    }
}
