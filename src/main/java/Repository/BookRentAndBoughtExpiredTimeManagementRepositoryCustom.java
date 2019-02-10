package Repository;

import entity.book;
import entity.users;

public interface BookRentAndBoughtExpiredTimeManagementRepositoryCustom {
public boolean isRentedBefore(String userId, String bookId);
public String getCurrentExpiredDateOfBook(String userId, String bookId);
}
