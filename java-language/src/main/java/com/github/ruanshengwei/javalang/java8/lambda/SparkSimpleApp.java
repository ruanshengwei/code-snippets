package com.github.ruanshengwei.javalang.java8.lambda;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.SparkSession;

import java.io.PrintStream;
import java.util.Arrays;

/**
 * https://www.zhihu.com/question/51491241/answer/126232275
 * 复现知乎中的问题
 */
public class SparkSimpleApp {
    public static void main(String[] args) {
        SparkSession spark = SparkSession
                .builder()
                .master("local")
                .appName("---")
                .getOrCreate();

        JavaRDD rdd =JavaSparkContext.fromSparkContext(spark.sparkContext())
                .parallelize(Arrays.asList(1,2,3,4,5,6))
                .filter((item)->item<5);

        PrintStream out = System.out;
        rdd.foreach(item->out.println(item));
    }
}
