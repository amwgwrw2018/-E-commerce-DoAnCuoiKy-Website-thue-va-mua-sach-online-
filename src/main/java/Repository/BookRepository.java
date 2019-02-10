package Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import entity.book;

public interface BookRepository extends CrudRepository<book,String>,BookRepositoryCustom{
@Override
Optional<book> findById(String id);
}
