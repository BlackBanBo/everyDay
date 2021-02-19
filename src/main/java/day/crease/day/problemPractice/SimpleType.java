package day.crease.day.problemPractice;

/**
 * @ClassName: ProblemPractice_01
 * @Description: 算法练习题_简单类型
 * @Author yzp
 * @Date 2021/2/19
 * @Version 1.0
 */
public class SimpleType {

    /**
     * // 123=>321
     *
     * @param x
     */
    public static void reverse(int x) {
        int rev = 0;
        while (x != 0) {
            int pop = x % 10;
            x /= 10;
            if (rev > Integer.MAX_VALUE / 10 || (rev == Integer.MAX_VALUE / 10 && pop > 7)) System.out.println(0);
            ;
            if (rev < Integer.MIN_VALUE / 10 || (rev == Integer.MIN_VALUE / 10 && pop < -8)) System.out.println(0);
            ;
            rev = rev * 10 + pop;
        }
        System.out.println(rev);
    }

    /**
     * 打印等腰三角形
     */
    public static void test2() {
        // 打印10行
        int n = 5;
        for (int j = 1; j <= n; j++) {
            for (int k = 1; k <= n - j; k++) {
                System.out.print(" ");
            }
            for (int i = 1; i <= 2 * j - 1; i++) {
                System.out.print("*");
            }
            System.out.println();
        }
    }

}
