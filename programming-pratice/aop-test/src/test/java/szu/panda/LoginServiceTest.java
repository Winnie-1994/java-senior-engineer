package szu.panda;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @Author: Winnie
 * @Date: 2019-07-26
 * @Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginServiceTest {

    @Autowired
    LoginService loginService;

    @Test
    public void LoginServiceTest(){
        loginService.login("Panda");
    }
}