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
    public int deleteByPrimaryKey(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(User record) {
        return userMapper.insert(record);
    }

    @Override
    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }

    @Override
    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    @Cacheable(value = "user", key = "#account")
    public User selectByAccount(String account) {

        return userMapper.selectByAccount(account);
    }

    @Override
    @CacheEvict(value = "user", key = "#user.account")
    public int updateByPrimaryKeySelective(User user) {
        return userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return 0;
    }
}
