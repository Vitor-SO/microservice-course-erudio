package vitorcode.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vitorcode.model.Book;

@Repository
public interface BookRepository extends JpaRepository<Book,Long> {
}
