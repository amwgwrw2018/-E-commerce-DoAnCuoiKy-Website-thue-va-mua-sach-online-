package Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import entity.book;
import entity.bookrentandboughtexpiredtimemanagement;
import entity.users;

public class BookRentAndBoughtExpiredTimeManagementRepositoryImpl implements BookRentAndBoughtExpiredTimeManagementRepositoryCustom{
	
	@PersistenceContext
	EntityManager entityManager;
@Override
public boolean isRentedBefore(String userId, String bookId) {
	 String sql = "FROM bookrentandboughtexpiredtimemanagement where userID=:userid and bookID=:bookid";
		TypedQuery<bookrentandboughtexpiredtimemanagement> query=entityManager.createQuery(sql,bookrentandboughtexpiredtimemanagement.class);
	
		query.setParameter("userid",new users(userId));
		query.setParameter("bookid",new book(bookId));
		try {
			query.getSingleResult();
			return true;
		} catch (NoResultException e) {
			return false;
		}


}
@Override
public String getCurrentExpiredDateOfBook(String userId, String bookId) {
	 String sql = "SELECT expiredTime FROM bookrentandboughtexpiredtimemanagement where userID=:userid and bookID=:bookid";
		TypedQuery<String> query=entityManager.createQuery(sql,String.class);
	
		query.setParameter("userid",new users(userId));
		query.setParameter("bookid",new book(bookId));
	return query.getSingleResult();
}
}
