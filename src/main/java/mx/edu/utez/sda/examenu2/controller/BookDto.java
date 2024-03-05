package mx.edu.utez.sda.examenu2.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.sda.examenu2.model.Book;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class BookDto {
    private Long id;
    private String name;
    private String author;
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date publication;

    public Book getBook(){
        return new Book(
                getId(),
                getName(),
                getAuthor(),
                getPublication()
        );
    }
}
