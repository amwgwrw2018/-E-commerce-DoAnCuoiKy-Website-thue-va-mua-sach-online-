package ChapterUploadAPI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ntn.API.HibernateUtils;

import Repository.BookChapterManagementRepository;
import entity.author;
import entity.book;
import entity.bookchaptermanagement;
import entity.booktype;
import entity.language;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class ChapterUploadAPI {
	
	@Autowired
	private BookChapterManagementRepository bookChapterManagementRepo;
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/uploadChapter/{bookID}/{chapterID}",method=RequestMethod.POST)
	public String SignUp(@RequestParam MultipartFile uploadFile,@PathVariable("bookID")String bookID,@PathVariable("chapterID")String chapterID) {

		System.out.println(uploadFile.getName());
		System.out.println(uploadFile.getOriginalFilename());
	System.out.println(uploadFile.getSize());
	
	Session session = HibernateUtils.getSessionFactory().getCurrentSession();
	Transaction tx = session.beginTransaction();
bookchaptermanagement bcm=new bookchaptermanagement();
bcm.setBookId(new book(bookID));
bcm.setNumberOfChapter(chapterID);
Calendar s=Calendar.getInstance();
bcm.setChapterContentFile("Book"+bookID+"Chapter"+chapterID+".txt");
bcm.setUploadDate(s.get(Calendar.YEAR)+"-"+(s.get(Calendar.MONTH)+1)+"-"+s.get(Calendar.DAY_OF_MONTH));
	session.save(bcm);
	tx.commit();
	session.close();

	try {
		InputStream isr=uploadFile.getInputStream();
		FileOutputStream fos=new FileOutputStream(new File("E:\\aDoAnHocKy77777\\TMDTcuoiky\\BookContent\\Book"+bookID+"Chapter"+chapterID+".txt"));
		int data;
		byte[]b=new byte[1024];
		while((data=isr.read(b))>0) {
			fos.write(b,0,data);
		
		}
		isr.close();
		fos.close();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/getBookListByAuthor/{authorUsername}",produces = "application/json; charset=UTF-8",method=RequestMethod.POST)
	public List<book> getBookListByAuthor(@PathVariable("authorUsername") String authorUsername) {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		String query="select b from book b inner join bookandauthor bau on bau.bookID=b.id inner join author a on bau.authorID=a.id where a.username=:authorUsernamequery";
		
		TypedQuery<book> listBook=session.createQuery(query, book.class);
		listBook.setParameter("authorUsernamequery", authorUsername);
List<book> resultList=listBook.getResultList();
		tx.commit();
		session.close();
		return resultList;
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/getBookChapterNumberByBook/{bookID}",produces = "application/json; charset=UTF-8",method=RequestMethod.POST)
	public List<String> getBookChapterNumberByBook(@PathVariable("bookID") String bookID) {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		String query="SELECT bc.NumberOfChapter FROM bookchaptermanagement bc WHERE bookid=:bookidAdd";
		
		TypedQuery<String> listBook=session.createQuery(query, String.class);
		listBook.setParameter("bookidAdd", new book(bookID));
List<String> resultList=listBook.getResultList();
		tx.commit();
		session.close();
		return resultList;
	}
	
//	@CrossOrigin(origins = "http://localhost:4200")
//	@RequestMapping(value="/suand",produces = "application/json; charset=UTF-8",method=RequestMethod.POST)
//	public void SuaNoiDungChapter(@RequestBody String text) {
//
//	System.out.println(text);
//	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/getAllChapterUploadHistory/{bookID}",produces = "application/json; charset=UTF-8",method=RequestMethod.POST)
	public List<bookchaptermanagement> GetAllChapterUploadHistoryByBookID(@PathVariable("bookID")String bookID) {
		return bookChapterManagementRepo.findByBookId(new book(bookID));
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/deleteChapter/{chapterID}/{bookID}/{numberOfChapter}",produces = "application/json; charset=UTF-8",method=RequestMethod.POST)
	public void DeleteChapter(@PathVariable("chapterID")String chapterID,
			@PathVariable("bookID")String bookID,
			@PathVariable("numberOfChapter")String numberOfChapter
			
			) {
		Session session = HibernateUtils.getSessionFactory().getCurrentSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("Delete FROM bookchaptermanagement b where b.ChapterID=:chapterID");
		    query.setParameter("chapterID", chapterID);
		query.executeUpdate();
				tx.commit();
		session.close();
		File file=new File("E:\\aDoAnHocKy77777\\TMDTcuoiky\\BookContent\\Book"+bookID+"Chapter"+numberOfChapter+".txt");
		if(file.exists()) {
			file.delete();
		}
	}
	
}
