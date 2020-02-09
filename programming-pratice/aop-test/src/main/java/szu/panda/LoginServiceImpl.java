package szu.panda;

import org.springframework.stereotype.Service;

/**
 * @Author: Winnie
 * @Date: 2019-07-26
 * @Description:
 */
@Service
public class LoginServiceImpl implements LoginService{

    public String login(String username){
        System.out.println(username + ": 正在登陆");
        return "success";
    }
}