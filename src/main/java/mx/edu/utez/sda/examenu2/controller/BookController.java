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
    @GetMapping("/author/")
    public ResponseEntity<Response<List<Book>>> getAllByAuthor(){
        return new ResponseEntity<>(
                this.service.getAllByAuthor(),
                HttpStatus.OK
        );
    }
    @GetMapping("/publication/")
    public ResponseEntity<Response<List<Book>>> getAllByPublication(){
        return new ResponseEntity<>(
                this.service.getAllByDate(),
                HttpStatus.OK
        );
    }
    @GetMapping("/name/{name_book}")
    public ResponseEntity<Response<List<Book>>> getAllByName(@PathVariable("name_book")String name_book){
        return new ResponseEntity<>(
                this.service.getAllByName(name_book),
                HttpStatus.OK
        );
    }
    @PostMapping("/")
    public ResponseEntity<Response<Book>> insert(@RequestBody  BookDto dto){
        return new ResponseEntity<>(
                this.service.insert(dto.getBook()),
                HttpStatus.OK
        );
    }
    @PutMapping("/")
    public ResponseEntity<Response<Book>> update(@RequestBody BookDto dto){
        return new ResponseEntity<>(
                this.service.update(dto.getBook()),
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
