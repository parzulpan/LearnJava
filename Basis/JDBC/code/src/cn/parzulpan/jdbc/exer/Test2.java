package cn.parzulpan.jdbc.exer;

import cn.parzulpan.jdbc.bean.ExamStudent;
import cn.parzulpan.jdbc.util.JDBCUtils;

import java.util.Scanner;

/**
 * @Author : parzulpan
 * @Time : 2020-11-30
 * @Desc : 练习2：创建数据库表 examstudent，插入一个新的student 信息，输入身份证号或准考证号可以查询和删除学生的基本信息
 */

public class Test2 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Test2 test2 = new Test2();
        while (true) {
            System.out.print("请选择操作类型（a: 添加、b: 查询、c: 删除、q: 退出）: ");
            String selection = scanner.next();
            if ("a".equalsIgnoreCase(selection)) {
                test2.insert();
            } else if ("b".equalsIgnoreCase(selection)) {
                test2.query();
            } else if ("c".equalsIgnoreCase(selection)) {
                test2.delete();
            } else if ("q".equalsIgnoreCase(selection)) {
                break;
            } else {
                System.out.println("您的输入有误！请重新输入！");
            }
        }
    }

    public void insert() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Type: ");
        int type = scanner.nextInt();
        System.out.print("IDCard: ");
        String idCard = scanner.next();
        System.out.print("ExamCard: ");
        String examCard = scanner.next();
        System.out.print("StudentName: ");
        String studentName = scanner.next();
        System.out.print("Location: ");
        String location = scanner.next();
        System.out.print("Grade: ");
        int grade = scanner.nextInt();

        String sql = "insert into examstudent(Type,IDCard,ExamCard,StudentName,Location,Grade)values(?,?,?,?,?,?)";
        int update = JDBCUtils.update(sql, type, idCard, examCard, studentName, location, grade);
        if (update > 0) {
            System.out.println("信息录入成功！");
        } else {
            System.out.println("信息录入失败！");
        }
    }

    public void query() {
        JDBCUtils jdbcUtils = new JDBCUtils();
        Scanner scanner = new Scanner(System.in);
        System.out.print("请选择您要输入的类型（a: 准考证号、b: 身份证号）: ");
        String selection = scanner.next();
        if ("a".equalsIgnoreCase(selection)) {
            System.out.print("请输入准考证号: ");
            String examCard = scanner.next();
            String sql = "select FlowID flowID, Type type, IDCard idCard, ExamCard examCard," +
                    "StudentName studentName, Location location, Grade grade from examstudent where ExamCard = ?";
            ExamStudent examStudent = jdbcUtils.getQuery(ExamStudent.class, sql, examCard);
            if (examStudent != null) {
                System.out.println(examStudent);
            } else {
                System.out.println("查无此人！请重新输入！");
            }

        } else if ("b".equalsIgnoreCase(selection)) {
            System.out.print("请输入身份证号: ");
            String idCard = scanner.next();
            String sql = "select FlowID flowID, Type type, IDCard idCard, ExamCard examCard," +
                    "StudentName studentName, Location location, Grade grade from examstudent where IDCard = ?";
            ExamStudent examStudent = jdbcUtils.getQuery(ExamStudent.class, sql, idCard);
            if (examStudent != null) {
                System.out.println(examStudent);
            } else {
                System.out.println("查无此人！请重新输入！");
            }
        } else {
            System.out.println("您的输入有误！请重新输入！");
        }
    }

    public void delete() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入的学生的准考证号: ");
        String examCard = scanner.next();
        String sql = "delete from examstudent where ExamCard = ?";
        int update = JDBCUtils.update(sql, examCard);
        if (update > 0) {
            System.out.println("删除成功！");
        } else {
            System.out.println("查无此人！请重新输入！");
        }
    }

}
