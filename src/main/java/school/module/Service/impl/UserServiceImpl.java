package school.module.Service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import school.module.Service.UserService;
import school.module.dao.UserMapper;
import school.module.entity.User;

/**
 * @author Limgmk
 * @time 20-9-20 上午10:18
 * @description
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Cacheable(value = "user", key = "#account")
    public User selectByAccount(String account) {
        return userMapper.selectByAccount(account);
    }

    @Override
    @CacheEvict(value = "user", key = "#user.account")
    public void updateByPrimaryKeySelective(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }
}
