package com.imooc.sell.util;

import java.util.Random;

/**
 * Description:
 * User: wen-sr
 * Date: 2017-09-08  15:54
 */
public class KeyUtil {

    /**
     * 生成唯一的格式：
     * @return 当前毫秒数 + 随机数
     */
    public static synchronized String getUniqueKey(){
        Random random = new Random();

        System.currentTimeMillis();

        String a = (random.nextInt(900000) + 100000) + "";

        return System.currentTimeMillis() + a + "";
    }

}
