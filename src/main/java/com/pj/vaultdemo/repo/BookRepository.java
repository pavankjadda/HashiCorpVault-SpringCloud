package com.pj.vaultdemo.repo;

import com.pj.vaultdemo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book,Long> {
}
