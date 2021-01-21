package day.crease.day.service.testModule.Impl;

import day.crease.day.mapper.testModule.TestTransactMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BTransactServiceImpl {

    @Autowired
    private TestTransactMapper transactMapper;

    @Transactional
    public void removeOne(String jobNum) {
        transactMapper.removeOne(jobNum);
        System.out.println(333);
        int i=1/0; // 假设这里有异常
    }
}
