package BookApi;

import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ntn.API.HibernateUtils;

import entity.author;
import entity.book;
import entity.language;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class LanguageAPI {
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/getLanguageList",produces = "application/json",method=RequestMethod.POST)
	public List<language> getLanguageList() {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		TypedQuery<language> query = session.createQuery("FROM language l");
		  
		List<language> list=query.getResultList();
				tx.commit();
		session.close();
		    
		  
		return list;
		
	}
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/getLanguageById/{id}",produces = "application/json",method=RequestMethod.POST)
	public language getLanguageById(@PathVariable("id") String id) {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		TypedQuery<language> query = session.createQuery("FROM language l where languageId=:langid");
		  query.setParameter("langid", id);
		language lang=query.getSingleResult();
				tx.commit();
		session.close();
		    
		  
		return lang;
		
	}
}
