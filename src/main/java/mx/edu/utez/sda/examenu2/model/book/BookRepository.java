package mx.edu.utez.sda.examenu2.model.book;

import mx.edu.utez.sda.examenu2.model.book.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findAllByAuthor(String author);

    List<Book> findAllByPublication(Date date);

    List<Book> findAllByName(String name_book);

    List<Book>  findAllByOrderByAuthorAsc();

    List<Book> findAllByOrderByPublicationAsc();
}
