package BookApi;
import static java.lang.Math.toIntExact;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.StringTokenizer;

import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ntn.API.HibernateUtils;

import Repository.BookChapterManagementRepository;
import Repository.BookRentAndBoughtExpiredTimeManagementRepository;
import Repository.BookRepository;
import Repository.UsersRepository;
import SendMail.SendMailService;
import config.JwtService;
import entity.author;
import entity.book;
import entity.bookandauthor;
import entity.bookchaptermanagement;
import entity.bookrentandboughtexpiredtimemanagement;
import entity.booktype;
import entity.commentmanagement;
import entity.language;
import entity.rentpackage;
import entity.transactiondetail;
import entity.users;
import entity.usertransaction;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class BookAPI {
	@Autowired
	private BookRepository bookrepo;
	  @Autowired
	  private JwtService jwtService;
	  @Autowired
		private UsersRepository usersrepo;
	  @Autowired
		private SendMailService mailservice;
	  @Autowired
	  private HibernateUtils HibernateSessionfactory;
	  @Autowired
	  private BookRentAndBoughtExpiredTimeManagementRepository brentrepo;
	@Autowired
	private BookChapterManagementRepository bookChapterManagementRepo;

	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/getBook",produces = "application/json",method=RequestMethod.POST)
	public List<book> getBook() {
		return bookrepo.getAllBook();
	}
	

	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/getBook/{id}",produces = "application/json",method=RequestMethod.POST)
	public book getBook(@PathVariable("id")String id) {

		
	//System.out.println(	jwtService.getAttrFromToken(token, "username"));
//	System.out.println(	jwtService.getAttrFromToken(token, "password"));
		Optional<book> b=bookrepo.findById(id);
		
		return b.get();
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/getBookAuthor/{id}",produces = "application/json",method=RequestMethod.POST)
	public List<author> getBookAuthor(@PathVariable("id")String id) {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		TypedQuery<author> query = session.createQuery("SELECT b.authorID FROM bookandauthor b WHERE "
		  		+ "b.bookID=:bookID");
		    query.setParameter("bookID",    new book(id));
		List<author> s=query.getResultList();
				tx.commit();
			
		session.close();
		    
		  
		return s;
		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/getBookChapterCount/{id}",produces = "application/json",method=RequestMethod.POST)
	public long getBookChapterCount(@PathVariable("id")String id) {
		return bookChapterManagementRepo.getBookChapterCount(id);
	}
	
	
	@RequestMapping(value="/addBook",method=RequestMethod.POST)
	public void addBook(@RequestBody String body
			) {
		LocalDate lc=LocalDate.now();
		System.out.println(lc.getYear()+"-"+lc.getMonthValue()+"-"+lc.getDayOfMonth());
		System.out.println(body);
		JSONObject data=new JSONObject(body);
		;
	
		
		
	book newBook=new book();
String bookidForAuthor;
String authorid;
	Session session = HibernateSessionfactory.getSessionFactory().openSession();
	Transaction tx = session.beginTransaction();
	
	newBook.setBookName((String)data.get("bookName"));
	newBook.setBookType(new booktype((String)data.get("bookType")));
	newBook.setLanguageId(new entity.language((String)data.get("languageId")));
	newBook.setPrice(data.get("price")+"");

	newBook.setBookImage("bookDefaultImage.png");
	newBook.setReleaseDate(lc.getYear()+"-"+lc.getMonthValue()+"-"+lc.getDayOfMonth());
	session.save(newBook);
tx.commit();
session.close();
Session session2 = HibernateSessionfactory.getSessionFactory().openSession();
Transaction tx2 = session2.beginTransaction();
TypedQuery<String> bookid =session2.createQuery("select max(id) from book where bookName=:bookNameInfo", String.class);
bookid.setParameter("bookNameInfo", (String)data.get("bookName"));
bookidForAuthor=bookid.getSingleResult();
tx2.commit();
session2.close();
System.out.println("bookid author : "+bookidForAuthor);
Session session4 = HibernateSessionfactory.getSessionFactory().openSession();
Transaction tx4 = session4.beginTransaction();
TypedQuery<String> queryauthorid=session4.createQuery("select a.id from author a inner join users u on a.username=u.username where u.id=:userid",String.class);
queryauthorid.setParameter("userid", (String)data.get("userID"));
authorid=queryauthorid.getSingleResult();
tx4.commit();
session4.close();
System.out.println("authorid : "+authorid);
Session session3 = HibernateSessionfactory.getSessionFactory().openSession();
Transaction tx3 = session3.beginTransaction();
bookandauthor bna=new bookandauthor();
bna.setBookID(new book(bookidForAuthor));
bna.setAuthorID(new author(authorid));
session3.save(bna);
tx3.commit();
session3.close();
	}
	
	@RequestMapping(value="/deleteBook/{id}/{bookName}/{bookType}/{authorId}/{price}/{language}/{bookImage}/{releaseDate}",method=RequestMethod.POST)
	public void deleteBook(
			@PathVariable("id")String id,
			@PathVariable("bookName")String bookName,
			@PathVariable("bookType")String bookType,
			@PathVariable("authorId")String authorId,
			@PathVariable("price")String price,
			@PathVariable("language")String language,
			@PathVariable("bookImage")String bookImage,
			@PathVariable("releaseDate")String releaseDate
			
			
			) {
	//book deleteBook=new book(id, bookName, bookType, new author(authorId), language, bookImage, price, releaseDate);

	Session session = HibernateSessionfactory.getSessionFactory().openSession();
	Transaction tx = session.beginTransaction();
	
	//session.delete(deleteBook);
tx.commit();
session.close();
	
	}
	
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/changeBookInfo/{bookID}/{bookName}/{bookTypeID}/{languageID}/{price}",produces = "text/plain;charset=UTF-8",method=RequestMethod.POST)
	public void changeBookInfo(@PathVariable("bookID")String bookID,
			@PathVariable("bookName")String bookName,
			@PathVariable("bookTypeID")String bookTypeID,
			@PathVariable("languageID")String languageID,
			@PathVariable("price")String price
			) throws FileNotFoundException, UnsupportedEncodingException {
		
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		book b=new book(bookID);
		
		b.setBookName(new String(bookName.getBytes(),"UTF-8"));
		b.setBookType(new booktype(bookTypeID));
		b.setLanguageId(new language(languageID));
		b.setPrice(price);
	
		session.saveOrUpdate(b);
		tx.commit();
		session.close();
	
	}
	
	
	
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/getBookChapter/{id}/{chapter}",produces = "text/plain;charset=UTF-8",method=RequestMethod.GET)
	public String getBookChapterByBookIdAndNumber(@PathVariable("id")String id,
			@PathVariable("chapter")String chapter) throws FileNotFoundException {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		  TypedQuery<String> query = session.createQuery("SELECT b.ChapterContentFile FROM bookchaptermanagement b WHERE "
		  		+ "b.bookId=:bookId and b.NumberOfChapter=:number");
		    query.setParameter("bookId",    new book(id));
		
		    query.setParameter("number", chapter);
		    String fileName= query.getSingleResult();
		    tx.commit();
		    session.close();
		    File file=new File("E:\\aDoAnHocKy77777\\TMDTcuoiky\\BookContent\\"+fileName);
		    String content="";
		
		   try {
			FileInputStream fis=new FileInputStream(file);
			BufferedReader br=new BufferedReader(new InputStreamReader(fis,"UTF-8"));
			String data="";
			while((data=br.readLine())!=null) {
				content+=data+"\n";
			}
		br.close();
		fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return content;
	}
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/changeBookImage/{bookid}",method=RequestMethod.POST)
	public String changeBookImage(@RequestParam MultipartFile uploadFile,@PathVariable("bookid")String bookid) {

		System.out.println(uploadFile.getName());
		System.out.println(uploadFile.getOriginalFilename());
	System.out.println(uploadFile.getSize());

	try {
		File currentImage=new File("E:\\aDoAnHocKy77777\\TMDTcuoiky\\frondend\\bookRent\\src\\assets\\images\\Book"+bookid+"Image.png");
if(currentImage.exists()) {
	currentImage.delete();
}
	InputStream isr=uploadFile.getInputStream();
	FileOutputStream fos=new FileOutputStream(currentImage);
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
	Session session = HibernateSessionfactory.getSessionFactory().openSession();
	Transaction tx = session.beginTransaction();
	book thisbook=new book(bookid);
	Query query=session.createQuery("update book set bookImage=:bookImageSet where id=:bookid");
	query.setParameter("bookImageSet", "Book"+bookid+"Image.png");
	query.setParameter("bookid", bookid);
	query.executeUpdate();
	tx.commit();
	session.close();
	return null;
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/countBookRent/{userID}",method=RequestMethod.GET)
	public int countBookRent(@PathVariable("userID") String userID) {
String sql="select count(*) from bookrentandboughtexpiredtimemanagement where userID=:userID and expiredTime>CURRENT_DATE";
Session session = HibernateSessionfactory.getSessionFactory().openSession();
Transaction tx = session.beginTransaction();
TypedQuery<Long> query = session.createQuery(sql);
    query.setParameter("userID",new users(userID));
   int result=toIntExact(query.getSingleResult());

		tx.commit();
session.close();
return result;
	
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/countBookBought/{userID}",method=RequestMethod.POST)
	public int countBookBought(@PathVariable("userID") String userID) {
String sql="SELECT count(distinct t.bookID) from usertransaction u inner join transactiondetail t on u.transactionID=t.transactionID where u.userID=:userID and t.rentPackageID=4";
Session session = HibernateSessionfactory.getSessionFactory().openSession();
Transaction tx = session.beginTransaction();
TypedQuery<Long> query = session.createQuery(sql);

    query.setParameter("userID",new users(userID));
   int result=toIntExact(query.getSingleResult());

		tx.commit();
session.close();
return result;
	
	}
	
//	@CrossOrigin(origins = "http://localhost:4200")
//	@RequestMapping(value="/getRecomendedBook/{bookType}",method=RequestMethod.GET)
//	public int countBookBought(@PathVariable("bookType") String bookType) {
//String sql="";
//Session session = HibernateUtils.getSessionFactory().openSession();
//Transaction tx = session.beginTransaction();
//TypedQuery<Long> query = session.createQuery(sql);
//
//    query.setParameter("userID",new users(userID));
//   int result=toIntExact(query.getSingleResult());
//
//		tx.commit();
//session.close();
//return result;
//	
//	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/getCommendList/{bookID}",method=RequestMethod.POST)
	public List<commentmanagement> getCommendList(@PathVariable("bookID") String bookID) {
String sql="From commentmanagement where bookId=:bookID";
Session session = HibernateUtils.getSessionFactory().openSession();
Transaction tx = session.beginTransaction();
TypedQuery<commentmanagement> query = session.createQuery(sql,commentmanagement.class);

    query.setParameter("bookID",new book(bookID));
    List<commentmanagement> list= query.getResultList();

		tx.commit();
session.close();
return list;
	
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/getRentpackageList",method=RequestMethod.POST)
	public List<rentpackage> getRentpackageList() {
String sql="FROM rentpackage WHERE RentPackageID <>4";
Session session = HibernateUtils.getSessionFactory().openSession();
Transaction tx = session.beginTransaction();
TypedQuery<rentpackage> query = session.createQuery(sql,rentpackage.class);

    
    List<rentpackage> list= query.getResultList();

		tx.commit();
session.close();
return list;
	
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/getRentpackageById/{rentId}",method=RequestMethod.POST)
	public rentpackage getRentpackageById(@PathVariable("rentId") String rendId) {
String sql="FROM rentpackage WHERE RentPackageID=:rendID";
Session session =HibernateUtils.getSessionFactory().openSession();

Transaction tx = session.beginTransaction();
TypedQuery<rentpackage> query = session.createQuery(sql,rentpackage.class);
query.setParameter("rendID", rendId);
    
rentpackage list= query.getSingleResult();

		tx.commit();
session.close();
return list;
	
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/test",method=RequestMethod.GET)
	public void test() {
		System.out.println(brentrepo.getCurrentExpiredDateOfBook("1", "1"));
	}
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/addNewTransation",method=RequestMethod.POST)
	public void getRentpackageList(@RequestBody String body) throws JSONException, ParseException {
	
		JSONObject data=new JSONObject(body);
		Calendar c=Calendar.getInstance();
		String currentDate=c.get(Calendar.YEAR)+"-"+c.get(Calendar.MONTH)+"-"+c.get(Calendar.DAY_OF_MONTH);
		String userid=usersrepo.getUserIdByUsername((String)data.get("username"));
		usertransaction transaction=new usertransaction();
		transaction.setUserID(new users(userid));
		transaction.setTransactionDate(currentDate);
		transactiondetail detail=new transactiondetail();

				
System.out.println(data.get("username"));
System.out.println("book :"+data.get("bookID"));
System.out.println("rent :"+data.get("rentPackagedID"));


Session session = HibernateSessionfactory.getSessionFactory().openSession();
//add transaction 
Transaction tx = session.beginTransaction();
System.out.println("xong session1");
session.save(transaction);
tx.commit();
session.close();
//add transaction detail
Session session2 = HibernateSessionfactory.getSessionFactory().openSession();
Transaction tx2 = session2.beginTransaction();
String thisTransactionID=usersrepo.getRecentTransactionIdByUserID(userid);
detail.setTransactionID(new usertransaction(thisTransactionID));
detail.setBookID(new book((String)data.get("bookID")));
detail.setRentPackageID(new rentpackage((String)data.get("rentPackagedID")));

session2.save(detail);
System.out.println("xong session2");
tx2.commit();
session2.close();
if(!((String)data.get("rentPackagedID")).equals("4")) {
	//get number of day to be added
	int numberOfDay;
	String sql="select r.numberOfDay from rentpackage r WHERE r.RentPackageID=:rentID";
	Session session3 = HibernateSessionfactory.getSessionFactory().openSession();
	Transaction tx3 = session3.beginTransaction();
	TypedQuery<Integer> query = session3.createQuery(sql);

	    query.setParameter("rentID",(String)data.get("rentPackagedID"));
	   numberOfDay=query.getSingleResult();
	   System.out.println("xong session3");
			tx3.commit();
	session3.close();
	//add day to expiredtime
	System.out.println("so ngay :"+numberOfDay);

	Session session4 = HibernateSessionfactory.getSessionFactory().openSession();
	Transaction tx4 = session4.beginTransaction();
	LocalDate lcNow=LocalDate.now();
	if(brentrepo.isRentedBefore(userid, (String)data.get("bookID")) == false) {
		bookrentandboughtexpiredtimemanagement booktrack=new bookrentandboughtexpiredtimemanagement();
		booktrack.setUserID(new users(userid));
		booktrack.setBookID(new book((String)data.get("bookID")));
		 booktrack.setIsBought("0");
	booktrack.setExpiredTime(lcNow.plusDays(numberOfDay).getYear()+"-"+lcNow.plusDays(numberOfDay).getMonthValue()+"-"+lcNow.plusDays(numberOfDay).getDayOfMonth());
	session4.save(booktrack);
	}else {

		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
		StringTokenizer str=new StringTokenizer(brentrepo.getCurrentExpiredDateOfBook(userid, (String)data.get("bookID")),"-");
		LocalDate lcExpiredDate=LocalDate.of(Integer.parseInt(str.nextToken()),Integer.parseInt(str.nextToken()),Integer.parseInt(str.nextToken()));


	//neu ngay het han<ngay hien tai lay' ngayhientai+them
	if(lcExpiredDate.isBefore(lcNow)) {
		System.out.println("trigger lcExpiredDate");
		
		String addDate="UPDATE bookrentandboughtexpiredtimemanagement SET expiredTime=:newTime where userID=:userIDupdate and bookID=:bookIDupdate";
		
		Query queryAddDate=session4.createQuery(addDate);
	
		queryAddDate.setParameter("newTime",lcNow.plusDays(numberOfDay).getYear()+"-"+lcNow.plusDays(numberOfDay).getMonthValue()+"-"+lcNow.plusDays(numberOfDay).getDayOfMonth());
		queryAddDate.setParameter("userIDupdate", new users(userid));
		queryAddDate.setParameter("bookIDupdate",new book( (String)data.get("bookID")));
		queryAddDate.executeUpdate();
	}else {
		//neu ngay het han>=ngay hien tai lay' ngayhethan+them
		if(lcExpiredDate.isBefore(lcNow)==false) {
			System.out.println("trigger lcNow");
			String addDate="UPDATE bookrentandboughtexpiredtimemanagement SET expiredTime=:newTime where userID=:userIDupdate and bookID=:bookIDupdate";
			Query queryAddDate=session4.createQuery(addDate);
			
			queryAddDate.setParameter("newTime",lcExpiredDate.plusDays(numberOfDay).getYear()+"-"+lcExpiredDate.plusDays(numberOfDay).getMonthValue()+"-"+lcExpiredDate.plusDays(numberOfDay).getDayOfMonth());
			queryAddDate.setParameter("userIDupdate",  new users(userid));
			queryAddDate.setParameter("bookIDupdate",new book( (String)data.get("bookID")));
			queryAddDate.executeUpdate();
		}
	}
	}


	System.out.println("xong session");
	tx4.commit();
	session4.close();
}else {
	if(brentrepo.isRentedBefore(userid, (String)data.get("bookID")) == true) {
		System.out.println("mua sach");
		String sql="update bookrentandboughtexpiredtimemanagement set isBought=1 where userID=:userIDupdate and bookID=:bookIDupdate ";
		Session session3 = HibernateSessionfactory.getSessionFactory().openSession();
		Transaction tx3 = session3.beginTransaction();
		Query query = session3.createQuery(sql);
		query.setParameter("userIDupdate",  new users(userid));
		query.setParameter("bookIDupdate",new book( (String)data.get("bookID")));
		query.executeUpdate();
		  
		   System.out.println("xong session3");
				tx3.commit();
		session3.close();
	}else {
		Session session3 = HibernateSessionfactory.getSessionFactory().openSession();
		Transaction tx3 = session3.beginTransaction();
		bookrentandboughtexpiredtimemanagement newBuy=new bookrentandboughtexpiredtimemanagement();
		newBuy.setUserID(new users(userid));
		newBuy.setBookID(new book( (String)data.get("bookID")));
		newBuy.setIsBought("1");
		newBuy.setExpiredTime("2018-1-1");
		session3.save(newBuy);
		tx3.commit();
		session3.close();
	}
	Session session5 = HibernateSessionfactory.getSessionFactory().openSession();
	Transaction tx5 = session5.beginTransaction();
	
	TypedQuery<String> queryemail=session5.createQuery("select u.email from users u where u.id=:userIDAdd");
	queryemail.setParameter("userIDAdd", new users(userid));
	String userEmail=queryemail.getSingleResult();
	tx5.commit();
	session5.close();
	System.out.println("MAIL NHAN DC LA : "+userEmail);
	//String mailContent="";
	//mailservice.addNewMail(mailContent, to);
}




System.out.println(jwtService.getAttrFromToken(data.getString("token"),"username"));
System.out.println(jwtService.getAttrFromToken(data.getString("token"),"password"));

	}
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/isReadableBookForUser/{userID}/{bookID}",produces = "application/json; charset=UTF-8",method=RequestMethod.POST)
	public bookrentandboughtexpiredtimemanagement isReadableBookForUser(@PathVariable("userID") String userID,@PathVariable("bookID") String bookID) {
	try {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		String query="from bookrentandboughtexpiredtimemanagement where userID=:useridcheck and bookID=:bookidcheck";
		
		TypedQuery<bookrentandboughtexpiredtimemanagement> listBook=session.createQuery(query, bookrentandboughtexpiredtimemanagement.class);
		listBook.setParameter("useridcheck", new users(userID));
		listBook.setParameter("bookidcheck", new book(bookID));
		bookrentandboughtexpiredtimemanagement result=listBook.getSingleResult();
		tx.commit();
		session.close();
		return result;
	} catch (Exception e) {
		return null;
	}
		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/isAuthorOfThisBook/{userID}",produces = "application/json; charset=UTF-8",method=RequestMethod.GET)
	public List<Object> isAuthorOfThisBook(@PathVariable("userID") String userID) {
	try {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		String query="select bau.bookID from bookandauthor bau inner join author a where bau.authorID=a.id inner join users u on u.username=a.username where u.id=:userIDadd";
		
		TypedQuery<Object> listBook=session.createQuery(query);
		listBook.setParameter("userIDadd", new author(userID));
	
		List<Object> result=listBook.getResultList();
		tx.commit();
		session.close();
		return result;
	} catch (Exception e) {
		return null;
	}
		
	}
	
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/isReadableBookListForUser/{userID}",produces = "application/json; charset=UTF-8",method=RequestMethod.POST)
	public List<Object> isReadableBookListForUser(@PathVariable("userID") String userID) {

		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		String query="Select b.bookName,b.bookType,b.languageId,b.releaseDate,b.id,b.bookImage,br.expiredTime,br.isBought from book b inner join bookrentandboughtexpiredtimemanagement br on b.id=br.bookID where br.userID=:userIDInfo and (br.isBought=1 or (br.isBought=0 and br.expiredTime>=CURRENT_DATE))";
		
		Query listBook=session.createQuery(query);
		listBook.setParameter("userIDInfo", new users(userID));
	
		List<Object> result=listBook.getResultList();
		tx.commit();
		session.close();
		return result;
	
		
	}
	
	
	
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/addComment/{userID}/{bookID}",produces = "text/plain; charset=UTF-8",consumes = {"text/plain;charset=UTF-8"},method=RequestMethod.POST)
	public void addComment(
			@PathVariable("userID") String userID,
			@PathVariable("bookID") String bookID,
			@RequestBody String commentContent) throws UnsupportedEncodingException 
	{
		
		LocalDate lc=LocalDate.now();


		System.out.println(userID);
		System.out.println(bookID);
System.out.println(commentContent);
Session session = HibernateUtils.getSessionFactory().openSession();
Transaction tx = session.beginTransaction();
commentmanagement cm=new commentmanagement();
cm.setBookId(new book(bookID));
cm.setUserId(new users(userID));
cm.setContent(commentContent);
cm.setCommentDate(lc.getYear()+"-"+lc.getMonthValue()+"-"+lc.getDayOfMonth());
session.save(cm);
tx.commit();
session.close();

	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/ListBookByNameLike/{bookName}",produces = "application/json; charset=UTF-8",method=RequestMethod.POST)
	public List<book> ListBookByNameLike(@PathVariable("bookName") String bookName) {
System.out.println(bookName);
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		String query=" from book b where b.bookName like :bookNameInput";
		
		TypedQuery<book> listBook=session.createQuery(query,book.class);
		listBook.setParameter("bookNameInput","%"+bookName+"%");
	
		List<book> result=listBook.getResultList();
		tx.commit();
		session.close();
		return result;
	
		
	}
	
}
	
