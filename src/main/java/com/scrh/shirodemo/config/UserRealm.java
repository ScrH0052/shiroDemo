package com.scrh.shirodemo.config;

import com.scrh.shirodemo.pojo.User;
import com.scrh.shirodemo.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;

import javax.annotation.Resource;

public class UserRealm extends AuthorizingRealm {
    //声明用户服务层
    @Resource
    private UserService userService;


    //执行授权逻辑
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("执行了=>授权逻辑PrincipalCollection");
        //对用户进行资源访问授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //获取当前登录的用户
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        //添加授权字段
        info.addStringPermission(user.getPerms());
        return info;
    }

    //执行认证逻辑
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了=>认证逻辑AuthenticationToken");
        /*无数据库版
        //假设数据库的用户名和密码
        String name = "root";
        String password = "123456";
        //1.判断用户名
        UsernamePasswordToken userToken = (UsernamePasswordToken)token;
        if (!userToken.getUsername().equals(name)){
            //用户名不存在
            return null; //shiro底层就会抛出 UnknownAccountException
        }
        //2. 验证密码,我们可以使用一个AuthenticationInfo实现类SimpleAuthenticationInfo
        // shiro会自动帮我们验证！重点是第二个参数就是要验证的密码！
        return new SimpleAuthenticationInfo("", password, "");*/

        //连接数据库
        //查询
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        User user = userService.getUserByName(userToken.getUsername());
        if (user == null) {
            return null;
        }

        //加密盐
        ByteSource credentialsSalt = ByteSource.Util.bytes("salt01");
        return new SimpleAuthenticationInfo(user, user.getPassword(),credentialsSalt,"UserRealm");
    }


}
