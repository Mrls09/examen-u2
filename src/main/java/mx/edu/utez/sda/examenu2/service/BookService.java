package mx.edu.utez.sda.examenu2.service;

import mx.edu.utez.sda.examenu2.model.book.Book;
import mx.edu.utez.sda.examenu2.model.book.BookRepository;
import mx.edu.utez.sda.examenu2.model.images.Image;
import mx.edu.utez.sda.examenu2.model.images.ImageRepository;
import mx.edu.utez.sda.examenu2.utils.Response;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.nio.file.FileSystems;
import java.sql.SQLException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class BookService {
    @Value("${data.storage}")
    private String rootPath;
    @Autowired
    private BookRepository repository;
    @Autowired
    private ImageRepository imageRepository;
    private String separator = FileSystems.getDefault().getSeparator();;


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
    public Response<List<Book>> getAllByAuthor(){
        return new Response<>(
                this.repository.findAllByOrderByAuthorAsc(),
                false,
                200,
                "OK"
        );
    }
    @Transactional(readOnly = true)
    public Response<List<Book>> getAllByDate(){
        return new Response<>(
                this.repository.findAllByOrderByPublicationAsc(),
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
    public Response<Book> insert(Book book) {
        book.getImages().forEach(image -> {
            image.setBook(book);
            try {
                String fileBase64 = image.getFileBase64();
                if (fileBase64 != null) {
                    byte[] arr = Base64.getDecoder().decode(fileBase64);
                    byte[] compressedImageBytes = compressImage(arr);
                    String uid = UUID.randomUUID().toString();
                    String mineType = determineImageType(image.getMimeType());
                    try (OutputStream stream = new FileOutputStream(rootPath + separator + uid + mineType)) {
                        stream.write(compressedImageBytes);
                        image.setFileBase64(null);
                        image.setUrl("http://localhost:8080/api-book/loadfile/" + uid + mineType);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    // Manejo para el caso cuando fileBase64 es nulo
                    throw new RuntimeException("El campo fileBase64 no puede ser nulo");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        Book savedBook = this.repository.saveAndFlush(book);
        this.imageRepository.saveAllAndFlush(savedBook.getImages());
        return new Response<>(
                savedBook, // Cambiado de this.repository.save(book) a savedBook
                false,
                200,
                "OK"
        );
    }

    private String determineImageType(String mimeType){
        if ("image/png".equals(mimeType)) {
            return ".png";
        } else if ("image/jpeg".equals(mimeType)) {
            return ".jpeg";
        } else {
            return ".jpg";
        }
    }
    private byte[] compressImage(byte[] imageBytes) throws IOException{
        ByteArrayInputStream inputStream = new ByteArrayInputStream(imageBytes);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        Thumbnails.of(inputStream)
                .size(800,800)
                .outputFormat("jpg")
                .toOutputStream(outputStream);
        return outputStream.toByteArray();
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
