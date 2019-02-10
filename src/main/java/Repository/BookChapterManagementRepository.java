package Repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import entity.bookchaptermanagement;

import java.io.File;
import java.lang.String;
import java.util.List;
import entity.book;



public interface BookChapterManagementRepository extends CrudRepository<bookchaptermanagement,String>,BookChapterManagementRepositoryCustom{

List<bookchaptermanagement> findByBookId(book bookid);
}
