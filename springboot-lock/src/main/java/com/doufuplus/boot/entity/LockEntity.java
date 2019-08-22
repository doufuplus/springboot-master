package com.doufuplus.boot.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.data.annotation.Id;

/**
 * 锁实体
 * 转载请注明出处，更多技术文章欢迎大家访问我的个人博客站点：https://www.doufuplus.com
 *
 * @author 丶doufu
 * @date 2019/08/22
 */
@TableName("t_lock")
public class LockEntity {

    /**
     * ID
     */
    @Id
    private int id;


    public LockEntity() {
    }

    public LockEntity(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
