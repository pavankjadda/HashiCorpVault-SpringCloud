package com.pj.vaultdemo.repo;

import com.pj.vaultdemo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * A repository interface to get, save and delete books
 *
 * @author Pavan Kumar Jadda
 * @since 1.0.0
 */
public interface BookRepository extends JpaRepository<Book, Long>
{
}
