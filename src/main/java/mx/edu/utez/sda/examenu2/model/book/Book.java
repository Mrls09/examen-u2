package mx.edu.utez.sda.examenu2.model.book;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import mx.edu.utez.sda.examenu2.model.images.Image;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private String name;
    @Column
    private String author;
    @Column
    @JsonFormat(pattern = "dd/MM/yyyy")
    private Date publication;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Image> images = new ArrayList<>();
}
