package Repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import entity.users;



public interface UsersRepository  extends CrudRepository<users,String>,UsersRepositoryCustom {


}
