package Repository;



import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

public interface UsersRepositoryCustom {
public boolean isUserExist(String username,String password);
public String getUserIdByUsername(String username);
public String getRecentTransactionIdByUserID(String userID);
}
