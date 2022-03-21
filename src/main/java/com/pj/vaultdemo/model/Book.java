package com.pj.vaultdemo.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * Book entity class that maps to the book table in the database.
 *
 * @author Pavan Kumar Jadda
 * @since 1.0.0
 */
@Entity
@Table(name = "book")
@Data
public class Book
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "title")
  private String title;

  @Column(name = "number_of_pages")
  private Integer numberOfPages;

  @Column(name = "cost")
  private Double cost;


  public Book()
  {
  }

  public Book(String title, Integer numberOfPages, Double cost, String author)
  {
  }
}
