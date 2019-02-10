package Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import entity.users;


public class UsersRepositoryImpl implements UsersRepositoryCustom{
	@PersistenceContext
	EntityManager entityManager;
	
@Override
public boolean isUserExist(String username, String password) {
	 String sql = "FROM users u where username=:usernames and password=:passwords";
	TypedQuery<users> query=entityManager.createQuery(sql,users.class);
	query.setParameter("usernames", username);
	query.setParameter("passwords", password);
if(query.getSingleResult()!=null) {
	return true;
}else {
	return false;
}
}
@Override
public String getUserIdByUsername(String username) {
	String sql = "SELECT u.id FROM users u where username=:usernames";
	Query query=entityManager.createQuery(sql);
	query.setParameter("usernames", username);

	return (String)query.getSingleResult();
}
@Override
public String getRecentTransactionIdByUserID(String userID) {
	String sql = "select max(transactionID) from usertransaction u WHERE u.userID=:userID";
	Query query=entityManager.createQuery(sql);
	query.setParameter("userID", new users(userID));

	return (String)query.getSingleResult();
	
	
}
}
