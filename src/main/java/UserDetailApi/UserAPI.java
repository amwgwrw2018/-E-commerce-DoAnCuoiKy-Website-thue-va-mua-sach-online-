package UserDetailApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.persistence.TypedQuery;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.json.JSONObject;
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

import Repository.UsersRepository;
import SendMail.SendMailService;
import entity.book;
import entity.language;
import entity.users;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class UserAPI {
	@Autowired
	private UsersRepository usersrepo;
	@Autowired
	private SendMailService mailservice;
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/getUser/{userId}",produces = "application/json",method=RequestMethod.POST)
	public users getUserInfoByID(@PathVariable("userId")String userId) {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		TypedQuery<users> query = session.createQuery("FROM users u where u.id=:uid");
		  query.setParameter("uid", userId);
		  users user=query.getSingleResult();
				tx.commit();
		session.close();
		    
		  
		return user;
		
	}
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/getUserList",produces = "application/json",method=RequestMethod.POST)
	public List<users> getUserInfoList() {
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		TypedQuery<users> query = session.createQuery("FROM users");
		 
		  List<users> userList=query.getResultList();
				tx.commit();
		session.close();
		    
		  
		return userList;
		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/changeUserProfile",produces = "application/json",method=RequestMethod.POST)
	public void changeUserProfile(@RequestBody String body) {

		JSONObject data=new JSONObject(body);
//		System.out.println(data.get("email"));
//		System.out.println(data.get("fullName"));
//		System.out.println(data.get("phoneNumber"));
//		System.out.println(data.get("address"));
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		Query query = session.createQuery("update users set email=:emailchange,FullName=:FullNamechange,phoneNumber=:phoneNumberchange,address=:addresschange where id=:userIDchange");
		 query.setParameter("emailchange", (String)data.get("email"));
		 query.setParameter("FullNamechange", (String)data.get("fullName"));
		 query.setParameter("phoneNumberchange", (String)data.get("phoneNumber"));
		 query.setParameter("addresschange", (String)data.get("address"));
		 query.setParameter("userIDchange", (String)data.get("userID"));
		query.executeUpdate();
				tx.commit();
		session.close();
		    
		  
	
		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/changePassword",produces = "application/json",method=RequestMethod.POST)
	public void changePassword(@RequestBody String body) {

JSONObject data=new JSONObject(body); 
System.out.println(data.get("userID"));
System.out.println(data.get("currentPassword"));
System.out.println(data.get("newPassword"));
		Session session0 = HibernateUtils.getSessionFactory().openSession();
		Transaction tx0 = session0.beginTransaction();
		TypedQuery<String> getPass=session0.createQuery("select u.password from users u where u.id=:userIDGet",String.class);
		getPass.setParameter("userIDGet", (String)data.get("userID"));
		String currentPassInDatabase=getPass.getSingleResult();
		tx0.commit();
		session0.close();
		System.out.println("databbase "+currentPassInDatabase);
		if(currentPassInDatabase.equals((String)data.get("currentPassword"))) {
			System.out.println("mat khau khop");
			Session session1 = HibernateUtils.getSessionFactory().openSession();
			Transaction tx1 = session1.beginTransaction();
			Query changePass=session1.createQuery("Update users set password=:newPassChange where id=:userIDGet");
			
			changePass.setParameter("newPassChange", (String)data.get("newPassword"));
			changePass.setParameter("userIDGet", (String)data.get("userID"));
changePass.executeUpdate();
			tx1.commit();
			session1.close();
		}
//		
//		
//		Session session = HibernateUtils.getSessionFactory().openSession();
//		Transaction tx = session.beginTransaction();
////		Query query = session.createQuery("update users set email=:emailchange,FullName=:FullNamechange,phoneNumber=:phoneNumberchange,address=:addresschange where id=:userIDchange");
////		 query.setParameter("emailchange", (String)data.get("email"));
////		 query.setParameter("FullNamechange", (String)data.get("fullName"));
////		 query.setParameter("phoneNumberchange", (String)data.get("phoneNumber"));
////		 query.setParameter("addresschange", (String)data.get("address"));
////		 query.setParameter("userIDchange", (String)data.get("userID"));
////		query.executeUpdate();
//				tx.commit();
//		session.close();
		    
		  
	
		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/changeUserAvatar/{userid}/{username}",method=RequestMethod.POST)
	public void changeUserAvatar(@RequestParam MultipartFile uploadFile,@PathVariable("userid")String userid, @PathVariable("username")String username) {

		System.out.println(uploadFile.getName());
		System.out.println(uploadFile.getOriginalFilename());
	System.out.println(uploadFile.getSize());
System.out.println(userid);
System.out.println(username);
	try {
		File currentImage=new File("E:\\aDoAnHocKy77777\\TMDTcuoiky\\frondend\\bookRent\\src\\assets\\images\\"+username+"Avatar.png");
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
	Session session = HibernateUtils.getSessionFactory().openSession();
	Transaction tx = session.beginTransaction();
	
	Query query=session.createQuery("update users set avatar=:avatarSET where id=:userIDSET");
	query.setParameter("avatarSET",username+"Avatar.png");
	query.setParameter("userIDSET",userid);
	query.executeUpdate();
	tx.commit();
	session.close();
	
	}

}
