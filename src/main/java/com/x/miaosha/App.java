package com.x.miaosha;

import com.x.miaosha.dao.UserInfoDOMapper;
import com.x.miaosha.dataobject.UserInfoDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
@SpringBootApplication(scanBasePackages = {"com.x"})
@RestController
@MapperScan("com.x.miaosha.dao")
public class App 
{
//    @Autowired
//    UserInfoDOMapper userInfoDOMapper;
//
//    @RequestMapping("/")
//    public String hello(){
//       UserInfoDO userInfoDO = userInfoDOMapper.selectByPrimaryKey(1);
//       if(userInfoDO == null){
//           return "不存在";
//       }
//        return userInfoDO.getName();
//    }

    public static void main( String[] args )
    {
        SpringApplication.run(App.class);   //启动容器
    }
}
