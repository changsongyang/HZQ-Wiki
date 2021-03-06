package com.hzqing.admin.sercurity.service;


import com.hzqing.admin.model.dto.system.UserInfoDetails;
import com.hzqing.admin.model.entity.system.User;
import com.hzqing.admin.service.system.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author hzqing
 * @date 2019-05-21 09:51
 */
@Slf4j
@Service
public class UserInfoDetailsService implements UserDetailsService {


    @Autowired
    private IUserService userService;


    /**
     * 如果登陆成功之后，返回用户基础信息
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.getByUserName(username);
        log.info("UserInfoDetailsService.loadUserByUsername " + user);
        UserInfoDetails userDetails = new UserInfoDetails(user.getId(),user.getUsername(),user.getPassword());
        return userDetails;
    }

}
