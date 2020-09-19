package school.module.dao;

import org.springframework.stereotype.Service;
import school.module.entity.User;

@Service
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    User selectByAccount(String account);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

}