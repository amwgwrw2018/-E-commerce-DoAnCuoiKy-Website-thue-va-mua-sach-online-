package Repository;







import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface BookChapterManagementRepositoryCustom {
	public long getBookChapterCount(String bookId);

}
