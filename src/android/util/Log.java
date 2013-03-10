/*
 * This file is in PUBLIC DOMAIN. You can use it freely. No guarantee.
 */
package android.util;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * @author Fan Hongtao &ltfanhongtao@gmail.com&gt
 */
public class Log {
    public static void i(String tag, String log) {
        String time = DateFormatUtils.format(System.currentTimeMillis(), "yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println(time + " " + tag + " " + log);
    }
}
