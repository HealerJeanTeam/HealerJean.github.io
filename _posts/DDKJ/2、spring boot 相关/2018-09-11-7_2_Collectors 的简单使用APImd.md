---
title: Collectors 的简单使用API
date: 2018-09-11 03:33:00
tags: 
- Java
category: 
- Java
description: Collectors 的简单使用API
---
<!-- image url 
https://raw.githubusercontent.com/HealerJean/HealerJean.github.io/master/blogImages
　　首行缩进
<font color="red">  </font>
-->

## 前言






### 4、forEach

```java
/**
 * 3、Java8为集合类引入了另一个重要概念：流（stream）。一个流通常以一个集合类实例为其数据源，
 * 然后在其上定义各种操作。流的API设计使用了管道（pipelines）模式。
 * 对流的一次操作会返回另一个流。如同IO的API或者StringBuffer的append方法那样，
 * 从而多个不同的操作可以在一个语句里串起来。看下面的例子：
 */

@Test
public  void stream(){

    distinctPrimary("1","2","3");

}
//给出一个String类型的数组，找出其中所有不重复的素数
public void distinctPrimary(String... numbers) {
    List<String> l = Arrays.asList(numbers);
    List<Integer> r = l.stream()
            .map(e -> new Integer(e)) //调用流的map方法把每个元素由String转成Integer，得到一个新的流。map方法接受一个Function类型的参数，上面介绍了，Function是个函数接口，所以这里用λ表达式。
            .distinct() //调用流的distinct方法，去掉重复，并得到一个新流。这本质上是另一个filter操作。

            // 用collect方法将最终结果收集到一个List里面去。collect方法接受一个Collector类型的参数，这个参数指明如何收集最终结果
            .collect(Collectors.toList());
    System.out.println("distinctPrimary result is: " + r);
}





 //1.分组计数
        List<Student> list1= Arrays.asList(
                new Student(1,"one","zhao"),new Student(2,"one","qian"),new Student(3,"two","sun"));
        //1.1根据某个属性分组计数
        Map<String,Long> result1=list1.stream().collect(Collectors.groupingBy(Student::getGroupId,Collectors.counting()));
        System.out.println(result1);
        //1.2根据整个实体对象分组计数,当其为String时常使用
        Map<Student,Long> result2=list1.stream().collect(Collectors.groupingBy(Function.identity(),Collectors.counting()));
        System.out.println(result2);

```




```java
package com.hlj.Lambda;

import com.hlj.Lambda.bean.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;


/**
 * 
 * @author yuanbing
 *
 */
public class IntroduceCollectors {


	static List<Student> students = null;
	static IntroduceCollectors collectors = null;


	@BeforeAll
	public static void init() {
		students = Arrays.asList(
				new Student("Ice", "1234", new Date(), null, "Shannxi", "123456", "", "M", null, 170.5, 65.0),
				new Student("Leo", "2344", new Date(), null, "Liaoning", "123456", "", "M", null, 175.5, 50.0),
				new Student("Mark", "4345", new Date(), null, "Shannxi", "123456", "", "M", null, 169.5, 70.0),
				new Student("Will", "4552", new Date(), null, "Fujian", "123456", "", "M", null, 176.5, 45.0),
				new Student("Yuan", "4554", new Date(), null, "Fujian", "123456", "", "M", null, 180.5, 56.0),
				new Student("Bing", "5677", new Date(), null, "Shannxi", "123456", "", "M", null, 166.5, 67.0),
				new Student("Amy", "5675", new Date(), null, "Shannxi", "123456", "", "F", null, 156.5, 78.0),
				new Student("Lily", "7567", new Date(), null, "Shannxi", "123456", "", "F", null, 167.5, 66.0),
				new Student("Timiy", "4677", new Date(), null, "Shannxi", "123456", "", "F", null, 182.5, 68.0),
				new Student("Eline", "4697", new Date(), null, "Liaoning", "123456", "", "F", null, 188.5, 54.0),
				new Student("Chrich", "8799", new Date(), null, "Liaoning", "123456", "", "F", null, 155.5, 75.0));

		collectors = new IntroduceCollectors();

	}



	/**
	 * 1、 averagingDouble/Int/Long
	 *     汇总求出平均值
	 */
	public double averagingDoubleTest(List<Student> students) {
		Objects.requireNonNull(students, "The parameter cannot be empty");
		return students.stream()
				.collect(Collectors.averagingDouble(student -> student.getHeight()));
	}
	@Test //171.77272727272728
	public void averagingDoubleTestCase() {
		System.out.println(collectors.averagingDoubleTest(students));
	}


	/**
	 * 2.1、collectingAndThen 将收集来的结果进行而外的转换
	 * 本例中将收集的结果放入不可变List中。
	 */
	public List<String> collectingAndThenTest(List<Student> students){
		Objects.requireNonNull(students, "The parameter cannot be empty");
		return students.stream()
				.map(student -> student.getName())
				.collect(Collectors.collectingAndThen(toList(), Collections::unmodifiableList));
	}
	@Test
	public void collectingAndThenTestCase() {
		System.out.println(collectors.collectingAndThenTest(students));
	}

	/**
	 *
	 * 2.2、将元素放入制定的集合中
	 */
	public List<String> toCollectionToList(List<Student> students){
		return students.stream()
				.map(Student::getName)
				.collect(Collectors.toCollection(ArrayList::new));
	}


	/**
	 *	3、Collectors.counting()
	 *  简单的计数器，简单的计算流元素。
	 */
	public long countingTest(List<Student> students) {
		Objects.requireNonNull(students, "The parameter cannot be empty");
		return students.stream()
				.collect(Collectors.counting());
	}
	@Test//11
	public void countingTestCase() {
		System.out.println(collectors.countingTest(students));
	}

	
	/**
	 * 4.1、 通过省份将学生分组到List中。
	 */
	public Map<String, List<Student>> groupingByToList(List<Student> students){
		Objects.requireNonNull(students, "The parameter cannot be empty");
		return students.stream()
//				.collect(Collectors.groupingBy(student -> student.getFrom(),toList()));
				.collect(Collectors.groupingBy(student -> student.getFrom())); //默认是toList
	}
	@Test
	public void groupingByToListCase() {
		System.out.println(collectors.groupingByToList(students));//{Liaoning=[Student [name=Leo, id=2344, birthday=Mon Sep 10 19:38:10 CST 2018, scores=null, from=Liaoning, phoneNumber=123456, email=, gender=M, course=null, height=175.5, weight=50.0], Student [name=Eline, id=4697, birthday=Mon Sep 10 19:38:10 CST 2018, scores=null, from=Liaoning, phoneNumber=123456, email=, gender=F, course=null, height=188.5, weight=54.0], Student [name=Chrich, id=8799, birthday=Mon Sep 10 19:38:10 CST 2018, scores=null, from=Liaoning, phoneNumber=123456, email=, gender=F, course=null, height=155.5, weight=75.0]], Shannxi=[Student [name=Ice, id=1234, birthday=Mon Sep 10 19:38:10 CST 2018, scores=null, from=Shannxi, phoneNumber=123456, email=, gender=M, course=null, height=170.5, weight=65.0], Student [name=Mark, id=4345, birthday=Mon Sep 10 19:38:10 CST 2018, scores=null, from=Shannxi, phoneNumber=123456, email=, gender=M, course=null, height=169.5, weight=70.0], Student [name=Bing, id=5677, birthday=Mon Sep 10 19:38:10 CST 2018, scores=null, from=Shannxi, phoneNumber=123456, email=, gender=M, course=null, height=166.5, weight=67.0], Student [name=Amy, id=5675, birthday=Mon Sep 10 19:38:10 CST 2018, scores=null, from=Shannxi, phoneNumber=123456, email=, gender=F, course=null, height=156.5, weight=78.0], Student [name=Lily, id=7567, birthday=Mon Sep 10 19:38:10 CST 2018, scores=null, from=Shannxi, phoneNumber=123456, email=, gender=F, course=null, height=167.5, weight=66.0], Student [name=Timiy, id=4677, birthday=Mon Sep 10 19:38:10 CST 2018, scores=null, from=Shannxi, phoneNumber=123456, email=, gender=F, course=null, height=182.5, weight=68.0]], Fujian=[Student [name=Will, id=4552, birthday=Mon Sep 10 19:38:10 CST 2018, scores=null, from=Fujian, phoneNumber=123456, email=, gender=M, course=null, height=176.5, weight=45.0], Student [name=Yuan, id=4554, birthday=Mon Sep 10 19:38:10 CST 2018, scores=null, from=Fujian, phoneNumber=123456, email=, gender=M, course=null, height=180.5, weight=56.0]]}
	}


	/**
	 * 4.2、通过省份将学生分组到Set中。
	 */
	public Map<String, Set<Student>> groupingByToSet(List<Student> students){
		Objects.requireNonNull(students, "The parameter cannot be empty");
		return students.stream()
				.collect(groupingBy(Student::getFrom, toSet()));
	}
	@Test//{Liaoning=[Student [name=Eline, id=4697, birthday=Mon Sep 10 19:39:38 CST 2018, scores=null, from=Liaoning, phoneNumber=123456, email=, gender=F, course=null, height=188.5, weight=54.0], Student [name=Leo, id=2344, birthday=Mon Sep 10 19:39:38 CST 2018, scores=null, from=Liaoning, phoneNumber=123456, email=, gender=M, course=null, height=175.5, weight=50.0], Student [name=Chrich, id=8799, birthday=Mon Sep 10 19:39:38 CST 2018, scores=null, from=Liaoning, phoneNumber=123456, email=, gender=F, course=null, height=155.5, weight=75.0]], Shannxi=[Student [name=Mark, id=4345, birthday=Mon Sep 10 19:39:38 CST 2018, scores=null, from=Shannxi, phoneNumber=123456, email=, gender=M, course=null, height=169.5, weight=70.0], Student [name=Ice, id=1234, birthday=Mon Sep 10 19:39:38 CST 2018, scores=null, from=Shannxi, phoneNumber=123456, email=, gender=M, course=null, height=170.5, weight=65.0], Student [name=Bing, id=5677, birthday=Mon Sep 10 19:39:38 CST 2018, scores=null, from=Shannxi, phoneNumber=123456, email=, gender=M, course=null, height=166.5, weight=67.0], Student [name=Amy, id=5675, birthday=Mon Sep 10 19:39:38 CST 2018, scores=null, from=Shannxi, phoneNumber=123456, email=, gender=F, course=null, height=156.5, weight=78.0], Student [name=Lily, id=7567, birthday=Mon Sep 10 19:39:38 CST 2018, scores=null, from=Shannxi, phoneNumber=123456, email=, gender=F, course=null, height=167.5, weight=66.0], Student [name=Timiy, id=4677, birthday=Mon Sep 10 19:39:38 CST 2018, scores=null, from=Shannxi, phoneNumber=123456, email=, gender=F, course=null, height=182.5, weight=68.0]], Fujian=[Student [name=Yuan, id=4554, birthday=Mon Sep 10 19:39:38 CST 2018, scores=null, from=Fujian, phoneNumber=123456, email=, gender=M, course=null, height=180.5, weight=56.0], Student [name=Will, id=4552, birthday=Mon Sep 10 19:39:38 CST 2018, scores=null, from=Fujian, phoneNumber=123456, email=, gender=M, course=null, height=176.5, weight=45.0]]}
	public void groupingByToSetCase() {
		System.out.println(collectors.groupingByToSet(students));
	}




	/**
	 * 4.3、通过省份将学生分组到Set中。并返回一个TreeMap.
	 */
	public Map<String, Set<Student>> groupingByToTreeMap(List<Student> students){
		Objects.requireNonNull(students, "The parameter cannot be empty");
		return students.stream()
				.collect(groupingBy(Student::getFrom, TreeMap::new, toSet()));
	}
	@Test
	public void groupingByToTreeMapCase() {
		System.out.println(collectors.groupingByToTreeMap(students));
	}

	
	/**
	 * 5.1、返回所有的学生的姓名
	 */
	public String joiningTest(List<Student> students) {
		Objects.requireNonNull(students, "The parameter cannot be empty");
		return students.stream()
				.map(Student::getName)
				.collect(Collectors.joining());
	}



	/**
	 * 5.2、返回所有的学生的姓名,姓名之间用逗号分割。
	 */
	public String joiningDelimiter(List<Student> students) {
		Objects.requireNonNull(students, "The parameter cannot be empty");
		return students.stream()
				.map(Student::getName)
				.collect(Collectors.joining(", "));
	}


	/**
	 * 5.3、返回所有的学生的姓名,姓名之间用逗号分割，并放在中括号里边。
	 */
	public String joiningDelimiterPreAndSuf(List<Student> students) {
		Objects.requireNonNull(students, "The parameter cannot be empty");
		return students.stream()
				.map(Student::getName)
				.collect(joining(", ", "[", "]"));
	}
	@Test
	public void joiningTestCase() {
		System.out.println(collectors.joiningTest(students)); //IceLeoMarkWillYuanBingAmyLilyTimiyElineChrich
		System.out.println(collectors.joiningDelimiter(students));//Ice, Leo, Mark, Will, Yuan, Bing, Amy, Lily, Timiy, Eline, Chrich
		System.out.println(collectors.joiningDelimiterPreAndSuf(students));//[Ice, Leo, Mark, Will, Yuan, Bing, Amy, Lily, Timiy, Eline, Chrich]

	}


	/**
	 * 6、以省份分组，并返回学生姓名的集合。
	 */
	public Map<String, List<String>> mappingTest(List<Student> students){
		Objects.requireNonNull(students, "The parameter cannot be empty");
		return students.stream()
				.collect(groupingBy(Student::getFrom, Collectors.mapping(Student::getName, toList())));
	}
	
	/**
	 *
	 * 7.1、 返回身高最高的学生
	 */
	public Student maxByTest(List<Student> students) {
		Objects.requireNonNull(students, "The parameter cannot be empty");
		return students.stream()
				.collect(maxBy(Comparator.comparingDouble(Student::getHeight)))
				.get();
	}
	/**
	 * 
	 * 7.2、返回体重最轻的学生。
	 */
	public Student minByTest(List<Student> students) {
		Objects.requireNonNull(students, "The parameter cannot be empty");
		return students.stream()
				.collect(minBy(Comparator.comparingDouble(Student::getWeight)))
				.get();
	}





	/**
	 * 
	 * 7.3、获取学生的成绩 getScores Map<String, Double>
	 */
	public Map<String, Map<String, Double>> toConcurrentMapTest(List<Student> students){
		return students.stream()
				.collect(Collectors.toConcurrentMap(Student::getName, Student::getScores));
	}
	@Test
	public void toConcurrentMapTestCase() {
		System.out.println(collectors.toConcurrentMapTest(students));

	}

	/**
	 * 
	 * @param students
	 * @return
	 */
	public Map<String, Date> toMapTest(List<Student> students){
		return students.stream()
				.collect(toMap(Student::getName, Student::getBirthday));
	}


	
}


```


<br/><br/><br/>
如果满意，请打赏博主任意金额，感兴趣的在微信转账的时候，添加博主微信哦， 请下方留言吧。可与博主自由讨论哦

|支付包 | 微信|微信公众号|
|:-------:|:-------:|:------:|
|![支付宝](https://raw.githubusercontent.com/HealerJean/HealerJean.github.io/master/assets/img/tctip/alpay.jpg) | ![微信](https://raw.githubusercontent.com/HealerJean/HealerJean.github.io/master/assets/img/tctip/weixin.jpg)|![微信公众号](https://raw.githubusercontent.com/HealerJean/HealerJean.github.io/master/assets/img/my/qrcode_for_gh_a23c07a2da9e_258.jpg)|




<!-- Gitalk 评论 start  -->

<link rel="stylesheet" href="https://unpkg.com/gitalk/dist/gitalk.css">
<script src="https://unpkg.com/gitalk@latest/dist/gitalk.min.js"></script> 
<div id="gitalk-container"></div>    
 <script type="text/javascript">
    var gitalk = new Gitalk({
		clientID: `1d164cd85549874d0e3a`,
		clientSecret: `527c3d223d1e6608953e835b547061037d140355`,
		repo: `HealerJean.github.io`,
		owner: 'HealerJean',
		admin: ['HealerJean'],
		id: 'JmKkPYajEHBFOqMf',
    });
    gitalk.render('gitalk-container');
</script> 

<!-- Gitalk end -->

