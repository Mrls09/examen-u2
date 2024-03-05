package mx.edu.utez.sda.examenu2.controller;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.sda.examenu2.model.book.Book;
import mx.edu.utez.sda.examenu2.model.images.Image;

import java.util.Date;
import java.util.List;

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
    private List<Image> images;

    public Book getBook(){
        return new Book(
                getId(),
                getName(),
                getAuthor(),
                getPublication(),
                getImages()
        );
    }
}
