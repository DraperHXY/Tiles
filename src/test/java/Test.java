import com.danga.MemCached.MemCachedClient;
import com.mutesaid.mapper.ExpertMapper;
import com.mutesaid.mapper.ProfessionMapper;
import com.mutesaid.mapper.StudentMapper;
import com.mutesaid.pojo.Usr;
import com.mutesaid.service.ExpertService;
import com.mutesaid.service.ProfessionService;
import com.mutesaid.service.UsrService;
import com.mutesaid.utils.JJWTUtil;
import org.junit.*;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class Test {
    @Autowired
    ProfessionMapper professionMapper;

    @Autowired
    ProfessionService professionService;

    @Autowired
    UsrService usrService;

    @Autowired
    ExpertService expertService;

    @Autowired
    ExpertMapper expertMapper;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private StudentMapper studentMapper;

    @org.junit.Test
    public void name() {
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add("zset1",1,1);
        zset.add("zset1",2,3);
        zset.add("zset1",2,4);
        zset.add("zset1",3,2);

        System.out.println(zset.range("zset1",0, -1));
    }

    @org.junit.Test
    public void cacheTest() {
        studentMapper.update(8L, "fellow", "黄旭东", 12345646L);
    }
}
