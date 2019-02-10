package AuthApi;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.axis.encoding.Base64;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ntn.API.HibernateUtils;

import Repository.BookRepository;
import Repository.UsersRepository;
import config.JwtService;
import entity.author;
import entity.book;
import entity.user_roles;
import entity.users;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AuthAPI {

	@Autowired
	private JwtService jwtService;
	@Autowired
	private UsersRepository usersrepo;
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/getToken",produces = "application/json",method=RequestMethod.POST)
	public String login() {
	
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String token="";
		
			String username = ((UserDetails)principal).getUsername();
			String password = ((UserDetails)principal).getPassword();
			System.out.println("nhan dc password la : "+password);
			if(usersrepo.isUserExist(username, password)) {
				token=jwtService.generateTokenLogin(username,password);
				System.out.println(username);
				System.out.println(password);
		String userID=usersrepo.getUserIdByUsername(username);
				Iterator<? extends GrantedAuthority> it=((UserDetails)principal).getAuthorities().iterator();
				JSONObject string=new JSONObject().put("token", token);
				int i=0;
				while(it.hasNext()) {
					i++;
					string.put("role"+i,it.next().getAuthority());
				}
				string.put("userid", userID);
			System.out.println(string.toString());
				return string.toString();
			
			}else {
				return "fail";
			}
			
			
			

		
		
		
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/signUp",produces = "application/json",method=RequestMethod.POST)
	public void signUp(@RequestBody String body) {
		JSONObject js=new JSONObject(body);
	
		System.out.println(js.get("role"));
		users newUser=new users();
user_roles user_role=new user_roles();
author newAuthor=new author();
		Session session = HibernateUtils.getSessionFactory().openSession();
		Transaction tx = session.beginTransaction();
		
		newUser.setUsername((String)js.get("username"));
		newUser.setPassword(Base64.encode(((String)js.get("password")).getBytes()));
		newUser.setEmail((String)js.get("email"));
		newUser.setEnabled("1");
		newUser.setAvatar("defaultAvatar.png");
	
		newUser.setFullName((String)js.get("fullname"));
		newUser.setAddress((String)js.get("address"));
		newUser.setPhoneNumber((String)js.get("phoneNumber"));
		session.save(newUser);
		user_role.setUsername((String)js.get("username"));
		user_role.setRole((String)js.get("role"));
		session.save(user_role);
		if((boolean)js.get("isAuthor")) {
			newAuthor.setAvatar("defaultAvatar.png");
			newAuthor.setAuthorName((String)js.get("authorName"));
			newAuthor.setAuthorInfo((String)js.get("authorInfo"));
			newAuthor.setAuthorDescription((String)js.get("authorDescription"));
			newAuthor.setUsername((String)js.get("username"));
			session.save(newAuthor);
		}
	
	tx.commit();
	session.close();
			

		
		
		
	}
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/addUserAvatar/{username}",method=RequestMethod.POST)
	public String SignUp(@RequestParam MultipartFile uploadFile,@PathVariable("username") String username) {

		System.out.println(uploadFile.getName());
		System.out.println(uploadFile.getOriginalFilename());
	System.out.println(uploadFile.getSize());
	Session session = HibernateUtils.getSessionFactory().openSession();
	Transaction tx = session.beginTransaction();
	try {
		File currentImage=new File("E:\\aDoAnHocKy77777\\TMDTcuoiky\\frondend\\bookRent\\src\\assets\\userAvatar\\"+username+"Avatar.png");
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
	return null;
	}
	
	@CrossOrigin(origins = "http://localhost:4200")
	@RequestMapping(value="/isAcceptableAuthor/{userid}/{bookid}",produces = "application/json",method=RequestMethod.POST)
	public String isAuthorAndAccessToUploadBook() {
	return "";
			

		
		
		
	}
}
	