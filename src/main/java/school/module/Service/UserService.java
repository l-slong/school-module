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
    User selectByAccount(String account);

    void updateByPrimaryKeySelective(User user);
}
