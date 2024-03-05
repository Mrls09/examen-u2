package mx.edu.utez.sda.examenu2.service;

import mx.edu.utez.sda.examenu2.model.Book;
import mx.edu.utez.sda.examenu2.model.BookRepository;
import mx.edu.utez.sda.examenu2.utils.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class BookService {
    @Autowired
    private BookRepository repository;

    @Transactional(readOnly = true)
    public Response<List<Book>> getAll(){
        return new Response<>(
                this.repository.findAll(),
                false,
                200,
                "OK"
        );
    }
    @Transactional(readOnly = true)
    public Response<List<Book>> getAllByAuthor(String author){
        return new Response<>(
                this.repository.findAllByAuthor(author),
                false,
                200,
                "OK"
        );
    }
    @Transactional(readOnly = true)
    public Response<List<Book>> getAllByDate(Date date){
        return new Response<>(
                this.repository.findAllByPublication(date),
                false,
                200,
                "OK"
        );
    }
    @Transactional(readOnly = true)
    public Response<List<Book>> getAllByName(String name_book){
        return new Response<>(
                this.repository.findAllByName(name_book),
                false,
                200,
                "OK"
        );
    }
    @Transactional(rollbackFor = {SQLException.class})
    public Response<Book> insert(Book book){
        return new Response<>(
                this.repository.save(book),
                false,
                200,
                "OK"
        );
    }
    @Transactional(rollbackFor = {SQLException.class})
    public Response<Book> update(Book book){
        Optional<Book> bookUpdate = this.repository.findById(book.getId());
        if(bookUpdate.isPresent()){
            return new Response<>(
                    this.repository.saveAndFlush(book),
                    false,
                    200,
                    "OK"
            );
        }
        return new Response<>(
                null,
                true,
                400,
                "Not found"
        );
    }
    @Transactional(rollbackFor = {SQLException.class})
    public Response<String> changeStatus(Long id){
        Optional<Book> bookUpdate = this.repository.findById(id);
        if(bookUpdate.isPresent()){
            this.repository.delete(bookUpdate.get());
            return new Response<>(
                    "Done Delete",
                    false,
                    200,
                    "OK"
            );
        }
        return new Response<>(
                "Error",
                true,
                400,
                "OK"
        );
    }
}
