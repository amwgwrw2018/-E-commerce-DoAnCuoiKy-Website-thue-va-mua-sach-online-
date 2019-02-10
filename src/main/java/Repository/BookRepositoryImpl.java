package Repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entity.book;

public class BookRepositoryImpl implements BookRepositoryCustom{
	
	@PersistenceContext
	EntityManager entityManager;
	
@Override
public List<book> getAllBook() {
	 String sql = "FROM book b";
	TypedQuery<book> query=entityManager.createQuery(sql,book.class);
 return query.getResultList();
}


}
