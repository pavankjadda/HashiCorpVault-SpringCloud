package com.pj.vaultdemo.web;

import com.pj.vaultdemo.config.AppConfig;
import com.pj.vaultdemo.model.Book;
import com.pj.vaultdemo.repo.BookRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * A controller class for the books API
 *
 * @author Pavan Kumar Jadda
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/v1")
public class BookController
{
  private final AppConfig appConfig;
  private final BookRepository bookRepository;

  public BookController(AppConfig appConfig, BookRepository bookRepository)
  {
    this.appConfig = appConfig;
    this.bookRepository = bookRepository;
  }

  /**
   * Get all the books
   *
   * @author Pavan Kumar Jadda
   * @since 1.0.0
   */
  @GetMapping("/book/list")
  public List<Book> getBooks()
  {
    return bookRepository.findAll();
  }

  /**
   * Find the book by id and return it. If not found, return null
   *
   * @param id the id of the book
   *
   * @return the book or null
   *
   * @author Pavan Kumar Jadda
   * @since 1.0.0
   */
  @GetMapping("/book/{id}")
  public Optional<Book> getBookById(@PathVariable Long id)
  {
    return bookRepository.findById(id);
  }

  /**
   * Save the book and return updated book
   *
   * @param book the book to save
   *
   * @return the saved book
   *
   * @author Pavan Kumar Jadda
   * @since 1.0.0
   */
  @PostMapping("/book/save_book")
  public Book createBook(@RequestBody Book book)
  {
    return bookRepository.saveAndFlush(book);
  }

  /**
   * Delete the book by id and return the deleted status
   *
   * @param id the id of the book to delete
   *
   * @return the deleted status
   *
   * @author Pavan Kumar Jadda
   * @since 1.0.0
   */
  @DeleteMapping("/book/delete/{id}")
  public ResponseEntity<String> deleteBook(@PathVariable Long id)
  {
    try
    {
      bookRepository.deleteById(id);
      return ResponseEntity.ok("Book Deleted");
    }
    catch (Exception e)
    {
      return ResponseEntity.ok("Book Not found");
    }
  }

  @GetMapping("/test")
  public String test()
  {
    return appConfig.getEnvironment().getProperty("username") + ", " + appConfig.getEnvironment().getProperty("password") + ", " + appConfig.getEnvironment().getProperty("url");
  }
}
