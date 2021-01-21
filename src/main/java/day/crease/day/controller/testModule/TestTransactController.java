package day.crease.day.controller.testModule;

import day.crease.day.service.testModule.Impl.TestTransactServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/testTransact")
public class TestTransactController {

    @Autowired
    private TestTransactServiceImpl testTransactService;

    @RequestMapping(value = "/removeOne", method = RequestMethod.GET)
    public void removeOne(@RequestParam("jobNum") String jobNum) {
        System.out.println(jobNum);
        try {
            testTransactService.removeOne(jobNum);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // testTransactService.parent(jobNum);
    }

    @RequestMapping("/updateUser")
    public void updateUser() {
        for (int i = 0; i < 100; i++) {
            new Thread(()->testTransactService.oneUser()).start();
        }
    }
}
