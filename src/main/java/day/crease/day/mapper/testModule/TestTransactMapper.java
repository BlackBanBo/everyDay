package day.crease.day.mapper.testModule;



import day.crease.day.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface TestTransactMapper {

    /**
     * 删除一条测试数据
     * @param jobNum
     * @return
     */
    int removeOne(@Param("jobNum") String jobNum);

    /**
     * 先查user
     */
    User oneUser();

    /**
     * 新增age
     * @return
     */
    int updateAge(User user);



}
