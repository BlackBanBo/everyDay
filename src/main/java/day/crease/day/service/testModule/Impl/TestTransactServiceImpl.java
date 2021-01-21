package day.crease.day.service.testModule.Impl;

import day.crease.day.bean.User;
import day.crease.day.mapper.testModule.TestTransactMapper;
import day.crease.day.service.testModule.TestTransactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 测试事务
 * service方法有异常 抛出
 */
@Service
public class TestTransactServiceImpl implements TestTransactService {


    @Autowired
    private TestTransactMapper transactMapper;

    @Autowired
    private BTransactServiceImpl bTransactService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void removeOne(String jobNum) {
        transactMapper.removeOne(jobNum);
        System.out.println(2222);
        int i = 1 / 0; // 假设这里有异常
    }

    /**
     * 测试多线程新增用户age,看结果多少
     */
    @Transactional
    public  void oneUser() {
        User user = transactMapper.oneUser();
        System.out.println(user.toString());
        long age = user.getAge();
        user.setAge(age + 1);
        transactMapper.updateAge(user);
    }

}
