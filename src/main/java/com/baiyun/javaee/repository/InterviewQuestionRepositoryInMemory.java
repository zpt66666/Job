package com.baiyun.javaee.repository;

import com.baiyun.javaee.model.InterviewQuestion;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class InterviewQuestionRepositoryInMemory {

    private final List<InterviewQuestion> questions = new ArrayList<>();
    private final AtomicLong idCounter = new AtomicLong();

    // 应用启动时加载一些示例题目
    public InterviewQuestionRepositoryInMemory() {
        addSampleQuestions();
    }

    public void save(InterviewQuestion question) {
        if (question.getId() == null) {
            question.setId(idCounter.incrementAndGet());
        }
        questions.add(question);
    }

    public List<InterviewQuestion> findAll() {
        return new ArrayList<>(questions);
    }

    public List<InterviewQuestion> findByCategory(String category) {
        if (category == null || category.isEmpty() || category.equalsIgnoreCase("all")) {
            return findAll();
        }
        return questions.stream()
                .filter(q -> q.getCategory() != null && q.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
    }

    // 添加示例题目
    private void addSampleQuestions() {
        InterviewQuestion q1 = new InterviewQuestion();
        q1.setId(idCounter.incrementAndGet());
        q1.setCategory("Java");
        q1.setQuestion("什么是 JVM、JDK 和 JRE？");
        q1.setAnswer("JVM 是 Java 虚拟机，JRE 是 Java 运行时环境，JDK 是 Java 开发工具包。");
        save(q1);

        InterviewQuestion q2 = new InterviewQuestion();
        q2.setId(idCounter.incrementAndGet());
        q2.setCategory("Java");
        q2.setQuestion("final、finally 和 finalize 有什么区别？");
        q2.setAnswer("final 用于修饰类、方法、变量；finally 用于异常处理；finalize 用于垃圾回收。");
        save(q2);

        InterviewQuestion q3 = new InterviewQuestion();
        q3.setId(idCounter.incrementAndGet());
        q3.setCategory("Python");
        q3.setQuestion("解释一下 Python 中的 GIL。");
        q3.setAnswer("GIL 是全局解释器锁，它限制了 Python 在多线程环境下同时执行原生代码的能力。");
        save(q3);

        InterviewQuestion q4 = new InterviewQuestion();
        q4.setId(idCounter.incrementAndGet());
        q4.setCategory("Python");
        q4.setQuestion("Python 中的列表和元组有什么区别？");
        q4.setAnswer("列表是可变的，元组是不可变的。");
        save(q4);

        InterviewQuestion q5 = new InterviewQuestion();
        q5.setId(idCounter.incrementAndGet());
        q5.setCategory("前端");
        q5.setQuestion("解释一下事件委托。");
        q5.setAnswer("事件委托是一种技术，利用事件冒泡，只指定一个事件处理程序，就可以管理同类或相似事件。");
        save(q5);

        InterviewQuestion q6 = new InterviewQuestion();
        q6.setId(idCounter.incrementAndGet());
        q6.setCategory("前端");
        q6.setQuestion("什么是闭包？");
        q6.setAnswer("闭包是指函数及其周围的状态（词法环境）的引用组合。它允许你在函数外部访问函数内部的作用域。");
        save(q6);

        InterviewQuestion q7 = new InterviewQuestion();
        q7.setId(idCounter.incrementAndGet());
        q7.setCategory("数据库");
        q7.setQuestion("ACID 是什么？");
        q7.setAnswer("ACID 是数据库事务的四个特性：原子性、一致性、隔离性、持久性。");
        save(q7);

        InterviewQuestion q8 = new InterviewQuestion();
        q8.setId(idCounter.incrementAndGet());
        q8.setCategory("数据库");
        q8.setQuestion("解释一下 SQL 中的 JOIN。");
        q8.setAnswer("JOIN 用于根据两个或多个表中的相关列，组合来自这些表的行。");
        save(q8);
    }
} 