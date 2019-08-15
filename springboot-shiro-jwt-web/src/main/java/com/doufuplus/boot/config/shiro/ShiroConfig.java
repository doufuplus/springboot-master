package com.doufuplus.boot.config.shiro;

import com.doufuplus.boot.config.shiro.cache.CustomCacheManager;
import com.doufuplus.boot.filter.JwtFilter;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.data.redis.core.RedisTemplate;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.Map;

/**
 * Shiro配置
 * 转载请注明出处，更多技术文章欢迎大家访问我的个人博客站点：https://www.doufuplus.com
 *
 * @author 丶doufu
 * @date 2019/08/03
 */
@Configuration
public class ShiroConfig {

    /**
     * 配置使用自定义Realm，关闭Shiro自带的session 详情见文档
     * http://shiro.apache.org/session-management.html#SessionManagement-StatelessApplications%28Sessionless%29
     */
    @Bean("securityManager")
    public DefaultWebSecurityManager getManager(UserRealm userRealm, RedisTemplate<String, Object> template) {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        // 使用自定义Realm
        manager.setRealm(userRealm);
        // 关闭Shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        manager.setSubjectDAO(subjectDAO);
        // 设置自定义Cache缓存
        manager.setCacheManager(new CustomCacheManager(template));
        return manager;
    }

    /**
     * 生成一个ShiroRedisCacheManager
     **/
    private CustomCacheManager cacheManager(RedisTemplate template) {
        return new CustomCacheManager(template);
    }

    /**
     * 添加自己的过滤器，自定义url规则 详情见文档 http://shiro.apache.org/web.html#urls-
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean factory(DefaultWebSecurityManager securityManager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        // 添加自己的过滤器取名为jwt
        Map<String, Filter> filterMap = new HashMap<>(16);
        filterMap.put("jwtFilter", jwtFilterBean());
        factoryBean.setFilters(filterMap);
        factoryBean.setSecurityManager(securityManager);
        // 自定义url规则
        Map<String, String> filterRuleMap = new HashMap<>(16);
        // 所有请求通过我们自己的JWTFilter
        filterRuleMap.put("/**", "jwtFilter");
        factoryBean.setFilterChainDefinitionMap(filterRuleMap);
        return factoryBean;
    }

    /**
     * <pre>
     * 注入bean，此处应注意：
     *
     * (1)代码顺序，应放置于shiroFilter后面，否则报错：
     * 	No SecurityManager accessible to the calling code, either bound to the org.apache.shiro.util.
     * 	ThreadContext or as a vm static singleton. This is an invalid application configuration.
     *
     * (2)如不在此注册，在filter中将无法正常注入bean
     * </pre>
     */
    @Bean("jwtFilter")
    public JwtFilter jwtFilterBean() {
        return new JwtFilter();
    }

    /**
     * 下面的代码是添加注解支持
     */
    @Bean
    @DependsOn("lifecycleBeanPostProcessor")
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib，防止重复代理和可能引起代理出错的问题，https://zhuanlan.zhihu.com/p/29161098
        defaultAdvisorAutoProxyCreator.setProxyTargetClass(true);
        return defaultAdvisorAutoProxyCreator;
    }

    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(
            DefaultWebSecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor advisor = new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }
}
