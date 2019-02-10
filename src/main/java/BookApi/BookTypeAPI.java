package BookApi;


import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ntn.API.HibernateUtils;

import entity.book;
import entity.booktype;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class BookTypeAPI {
	@Autowired
	  private HibernateUtils HibernateSessionfactory;
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/getBookTypeList",produces = "application/json",method=RequestMethod.POST)
	public List<booktype> getBookTypeList() {
		Session session =HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		TypedQuery<booktype> query = session.createQuery("FROM booktype b");
		  
		List<booktype> list=query.getResultList();
				tx.commit();
		session.close();
		    
		 
		return list;
		
	}
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/getBookListByBookType/{bookTypeId}",produces = "application/json",method=RequestMethod.POST)
	public List<book> getBookListByBookType(@PathVariable("bookTypeId") String bookTypeId) {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		TypedQuery<book> query = session.createQuery("FROM book b where b.bookType=:bookTypeInput");
		  query.setParameter("bookTypeInput", new booktype(bookTypeId));
		List<book> list=query.getResultList();
				tx.commit();
		session.close();
		    
		 
		return list;
		
	}
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/getNewestBookListByBookType/{bookTypeId}/{numberOfBook}",produces = "application/json",method=RequestMethod.POST)
	public List<book> getNewestBookListByBookType(@PathVariable("bookTypeId") String bookTypeId,
			@PathVariable("numberOfBook") String numberOfBook
			) {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		TypedQuery<book> query = session.createQuery("FROM book b where b.bookType=:bookTypeInput order by b.releaseDate desc");
		  query.setParameter("bookTypeInput", new booktype(bookTypeId));
		  query.setMaxResults(Integer.parseInt(numberOfBook));
		List<book> list=query.getResultList();
				tx.commit();
		session.close();
		    
		 
		return list;
		
	}
}
