package Repository;

import java.io.File;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import entity.book;

public class BookChapterManagementRepositoryImpl implements BookChapterManagementRepositoryCustom{
	

	@PersistenceContext
	EntityManager entityManager;
	@Override
	public long getBookChapterCount(String bookId) {
		 String sql = "select count(*) FROM bookchaptermanagement group by bookId having bookId=:id";
		long result;
			TypedQuery<Long> query=entityManager.createQuery(sql,Long.class);
			book b=new book(bookId); 
			query.setParameter("id",b);
			result= query.getSingleResult();
			entityManager.close();
			return result;
	}



}
