package com.seek.friend.util.OPT;

import com.seek.friend.configobject.RedisData.RedisKeyData;
import com.seek.friend.util.Exception.BizException;
import com.seek.friend.util.Exception.ErrorCodeEnum;
import com.seek.friend.util.Redis.RedisUtil;
import com.seek.friend.util.TimeUtil.DurationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

@Component
@Lazy
public class OPTUtil {

    private final RedisUtil redisUtil;
    @Autowired
    public OPTUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    public String generateOPT(int n){
        StringBuilder opt = new StringBuilder();
        for (int i = 0; i < n; i++) {
            //添加一位随机数字
            opt.append(ThreadLocalRandom.current().nextInt(10));
        }
        return opt.toString();
    }

    //生成验证码并且写入redis中
    public String generateAndRecordRedis(RedisKeyData key,Object id,int n){
        String opt=generateOPT(n);
        //写入redis并且判断是否已有验证码
        if (!redisUtil.trySetStringWithExpire(key,id, opt))throw new BizException(ErrorCodeEnum.OPT_SURVIVE);
        return opt;
    }

    public void checkFromRedis( RedisKeyData key, String opt){
        //获取redis的验证码
        String originOpt=redisUtil.getString(key,null);
        //检验验证码
        if ( originOpt== null) throw new BizException(ErrorCodeEnum.OPT_NOT_SURVIVE);
        if (!originOpt.equals(opt)) throw new BizException(ErrorCodeEnum.OPT_NOT_SAME);
        //如果删除验证码失败，则拒绝
        if (!redisUtil.delete(key,null)) throw new BizException(ErrorCodeEnum.DATA_NOT_FOUND);
    }
}
