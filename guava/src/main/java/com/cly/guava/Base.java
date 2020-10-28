package com.cly.guava;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.base.Stopwatch;
import com.google.common.base.Strings;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * @Author c
 * @create 2019/12/2 17:33
 */
public class Base {

    @Test
    public void testStrings() {
        final String a = Strings.repeat("a", 10);
        checkNotNull(a);
        System.out.println(a);
    }


    @Test
    public void testJoiner() {
        Joiner joiner = Joiner.on(",").useForNull("ddd");
        final String join = joiner.join("I", "am", null, "A");
        System.out.println(join);
    }

    @Test
    public void testSplit() {
        String str = ",a,,,b,";
        final String[] split1 = str.split(",");
        Splitter splitter = Splitter.on(",").trimResults().omitEmptyStrings();
        final Iterable<String> split = splitter.split(str);
        final List<String> strings = splitter.splitToList(str);
        final Iterator<String> iterator = split.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println("====");
    }


    @Test
    public void testStopWatch() throws InterruptedException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Thread.sleep(10000);
        stopwatch.stop();
        System.out.println(stopwatch.elapsed(TimeUnit.SECONDS));
        stopwatch.reset();
        System.out.println(stopwatch.toString());
    }
}
