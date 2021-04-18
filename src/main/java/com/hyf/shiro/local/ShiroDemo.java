package com.hyf.shiro.local;

import com.hyf.shiro.local.config.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.authz.Authorizer;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.event.EventBus;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.*;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.Locale;

/**
 * @author baB_hyf
 * @date 2021/04/08
 */
public class ShiroDemo {

    private static final Logger log = LoggerFactory.getLogger(ShiroDemo.class);

    public static void main(String[] args) throws Exception {

        //1.创建SecurityManagerFactory
        IniSecurityManagerFactory factory =
                new IniSecurityManagerFactory("classpath:shiro-basic.ini");
        //2.获取SecurityManager,绑定到SecurityUtils中
        org.apache.shiro.mgt.SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);

        ext(securityManager);

        //3.获取一个用户识别信息
        Subject currentUser = SecurityUtils.getSubject();

        Session session = currentUser.getSession();
        session.setAttribute("k", "kk");
        session.touch();
        log.info("Get session attribute: " + session.getAttribute("k"));

        //4.判断是否已经身份验证
        if (!currentUser.isAuthenticated()) {
            // 4.1把用户名和密码封装为 UsernamePasswordToken 对象
            UsernamePasswordToken token = new UsernamePasswordToken("guest", "guest");
            // 4.2设置rememberme
            token.setRememberMe(true);
            try {
                // 4.3登录.
                currentUser.login(token);
                // 用户名称
                log.info("User [" + currentUser.getPrincipal() + "] logged in successfully.");
            } catch (UnknownAccountException uae) { //用户不存在异常
                log.info("****---->用户名不存在： " + token.getPrincipal());
                return;
            } catch (IncorrectCredentialsException ice) {// 密码不匹配异常
                log.info("****---->" + token.getPrincipal() + " 的密码错误!");
                return;
            } catch (LockedAccountException lae) {// 用户被锁定
                log.info("****---->用户 " + token.getPrincipal() + " 已被锁定");
            } catch (AuthenticationException ae) { // 其他异常，认证异常的父类
                log.info("****---->用户" + token.getPrincipal() + " 验证发生异常");
            }
        }

        // 5.权限测试：
        //5.1判断用户是否有某个角色
        if (currentUser.hasRole("guest")) {
            log.info("****---->用户拥有角色guest!");
        }
        else {
            log.info("****---->用户没有拥有角色guest");
            return;
        }
        //5.2判断用户是否执行某个操作的权限
        if (currentUser.isPermitted("see")) {
            log.info("****----> 用户拥有执行此功能的权限");
        }
        else {
            log.info("****---->用户没有拥有执行此功能的权限");
        }

        System.out.println(currentUser.isPermitted("see,b:read,aaa:bbb"));

        //6.退出
        System.out.println("****---->" + currentUser.isAuthenticated());
        currentUser.logout();
        System.out.println("****---->" + currentUser.isAuthenticated());

    }

    // Subject是安全领域的术语，代表用户，Principal代表用户名，Credential代表密码
    // 从Subject可获取Session，Session和HttpSession具有一样的功能，但不依赖与HTTP
    // Realm进行实际的用户信息获取，验证角色、权限

    public static void ext(SecurityManager securityManager) throws Exception {
        DefaultSecurityManager manager = (DefaultSecurityManager) securityManager;
        DefaultSessionManager sessionManager = (DefaultSessionManager) manager.getSessionManager();

        // 存在于 SecurityManager/SessionManager/CacheManager等中，默认没有使用过，EventBusAware可获取
        EventBus eventBus = manager.getEventBus();
        eventBus.register(new CustomEventBusRegister());

        // SessionListener 监听session的事件
        Collection<SessionListener> sessionListeners = sessionManager.getSessionListeners();
        sessionListeners.add(new CustomSessionListener());

        // Authenticator 账号等的身份认证
        ModularRealmAuthenticator authenticator = (ModularRealmAuthenticator) manager.getAuthenticator();
        manager.setAuthenticator(authenticator);

        // Authorizer 验证角色、权限等
        Authorizer authorizer = manager.getAuthorizer();
        manager.setAuthorizer(authorizer);

        // RememberManager 记住我功能
        // manager.setRememberMeManager(new CustomRememberMeManager());

        // Realm 包含实际的身份认证、权限鉴定
        Collection<Realm> realms = manager.getRealms();
        realms.add(new CustomRealm());
        realms.add(new CustomEventBusRealmHelper());
        authenticator.setRealms(realms);

        // AuthenticationStrategy 身份认证策略，用于多个Realm的情况
        authenticator.setAuthenticationStrategy(new CustomAtLeastOneSuccessfulStrategy());

        // AuthenticationListener 监听认证相关事件
        authenticator.setAuthenticationListeners(Collections.singletonList(new CustomAuthenticationListener()));

        // 初始化相关信息
        afterExeInit(manager);
    }

    public static void afterExeInit(DefaultSecurityManager manager) throws Exception {

        Method afterRealmsSet = RealmSecurityManager.class.getDeclaredMethod("afterRealmsSet");
        Method afterEventBusSet = CachingSecurityManager.class.getDeclaredMethod("afterEventBusSet");
        Method afterCacheManagerSet = CachingSecurityManager.class.getDeclaredMethod("afterCacheManagerSet");
        Method afterSessionManagerSet = SessionsSecurityManager.class.getDeclaredMethod("afterSessionManagerSet");

        afterRealmsSet.setAccessible(true);
        afterEventBusSet.setAccessible(true);
        afterCacheManagerSet.setAccessible(true);
        afterSessionManagerSet.setAccessible(true);

        afterRealmsSet.invoke(manager);
        afterEventBusSet.invoke(manager);
        afterCacheManagerSet.invoke(manager);
        afterSessionManagerSet.invoke(manager);
    }
}