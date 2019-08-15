package com.doufuplus.boot.config.shiro;

import com.doufuplus.boot.mapper.UserMapper;
import com.doufuplus.boot.entity.po.JwtToken;
import com.doufuplus.boot.entity.User;
import com.doufuplus.boot.constant.JwtConstant;
import com.doufuplus.boot.constant.RedisConstant;
import com.doufuplus.boot.redis.RedisClient;
import com.doufuplus.boot.util.JwtUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 自定义Realm
 * 转载请注明出处，更多技术文章欢迎大家访问我的个人博客站点：https://www.doufuplus.com
 *
 * @author 丶doufu
 * @date 2019/08/03
 */
@Service
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private RedisClient redis;

    @Autowired
    private UserMapper userMapper;

    /**
     * 大坑，必须重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        /*
        // 返回当前用户所拥有的角色、权限等信息，根据自身项目编码即可
        String account = JwtUtil.getClaim(principals.toString(), JwtConstant.ACCOUNT);
        // 查询用户角色
        List<Role> roles = roleMapper.findByAccount(account);
        for (int i = 0, roleLen = roles.size(); i < roleLen; i++) {
            Role role = roles.get(i);
            // 添加角色
            simpleAuthorizationInfo.addRole(role.getName());
            // 根据用户角色查询权限
            List<Permission> permissions = permissionMapper.findByRoleId(role.getId());
            for (int j = 0, perLen = permissions.size(); j < perLen; j++) {
                Permission permission = permissions.get(j);
                // 添加权限
                simpleAuthorizationInfo.addStringPermission(permission.getSn());
            }
        }
        */
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws AuthenticationException {
        String token = (String) auth.getCredentials();
        if (StringUtils.isBlank(token)) {
            throw new AuthenticationException("token cannot be empty.");
        }

        // 解密获得account，用于和数据库进行对比
        String account = JwtUtil.getClaim(token, JwtConstant.ACCOUNT);
        // 帐号为空
        if (StringUtils.isBlank(account)) {
            throw new AuthenticationException("token中帐号为空(The account in Token is empty.)");
        }
        // 查询用户是否存在
        User user = userMapper.findByAccount(account);
        if (user == null) {
            throw new AuthenticationException("该帐号不存在(The account does not exist.)");
        }
        // 开始认证，要AccessToken认证通过，且Redis中存在RefreshToken，且两个Token时间戳一致
        if (JwtUtil.verify(token) && redis.hasKey(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN + account)) {
            // 获取RefreshToken的时间戳
            String currentTimeMillisRedis = redis.get(RedisConstant.PREFIX_SHIRO_REFRESH_TOKEN + account).toString();
            // 获取AccessToken时间戳，与RefreshToken的时间戳对比
            if (JwtUtil.getClaim(token, JwtConstant.CURRENT_TIME_MILLIS).equals(currentTimeMillisRedis)) {
                return new SimpleAuthenticationInfo(token, token, "userRealm");
            }
        }
        throw new AuthenticationException("token expired or incorrect.");
    }
}
