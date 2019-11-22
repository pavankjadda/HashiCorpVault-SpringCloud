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

		@GetMapping("/book/list")
		public List<Book> getBooks()
		{
				return bookRepository.findAll();
		}

		@GetMapping("/book/{id}")
		public Optional<Book> getBookById(@PathVariable Long id)
		{
				return bookRepository.findById(id);
		}

		@PostMapping("/book/save_book")
		public Book createBook(@RequestBody Book book)
		{
				return bookRepository.saveAndFlush(book);
		}

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
		return appConfig.getEnvironment().getProperty("username")+", "+appConfig.getEnvironment().getProperty("password")+", "+appConfig.getEnvironment().getProperty("url");
	}
}
