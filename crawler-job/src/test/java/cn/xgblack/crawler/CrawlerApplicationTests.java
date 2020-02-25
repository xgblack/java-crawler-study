package cn.xgblack.crawler;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@SpringBootTest
class CrawlerApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void splitTest(){
        String s = "北京-海淀区  |  3-4年经验  |  本科  |  招1人  |  02-24发布";
        String[] ss = s.split("\\|");
        System.out.println(Arrays.toString(ss));
    }

}
