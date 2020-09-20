package school.module.Service;

import org.springframework.stereotype.Service;
import school.module.entity.User;

/**
 * @author Limgmk
 * @time 20-9-20 上午10:17
 * @description
 */

@Service
public interface UserService {

    int deleteByPrimaryKey(User user);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    User selectByAccount(String account);

    int updateByPrimaryKeySelective(User user);

    int updateByPrimaryKey(User user);
}
