package SwordToOfferSolution._46_TranslateNumbersToStrings;

import java.util.HashMap;

/*
 * 把数字翻译成字符串
 *
 * 题目描述：
 * 给定一个数字，我们按照如下规则把它翻译为字符串：0 翻译成 a，1 翻译成 b，……，11 翻译成 l，……，25 翻译成 z。
 * 一个数字可能有多个翻译。例如 12258 有 5 种不同的翻译，它们分别是 bccfi、bwfi、bczi、mcfi 和 mzi。
 * 请编程实现一个函数用来计算一个数字有多少种不同的翻译方法。
 *
 * 思路1：递归
 * 对于一组数字可以将大问题划分成小问题，最后统计问题的数量即可。
 *
 * 思路2：带有备忘录的递归
 * 将结果保存下来，如果有需要的话直接拿来用就可以了。
 *
 * 思路3: DP
 * 采用自底向上的方式，dp[i] 表示字符串 s[i, s.len-1] 从 i 开始到结尾的字符串的所有的解码方式。
 * 如果 s[i] 和 s[i+1] 组成的数字小于 26，则 dp[i] = dp[i+1] + dp[i+2]。
 */
public class Solution {
    // 方法一
    public static int getTranslationCount1(String s) {
        return getResult1(s, 0);
    }

    private static int getResult1(String s, int start) {
        // 如果到了最后一个字符，则只有一种翻译方式
        if (start == s.length()) {
            return 1;
        }
        // 如果遇到了字符 0，则没法翻译
        if (s.charAt(start) == '0') {
            return 0;
        }

        // 第一种翻译方式
        int ans1 = getResult1(s, start + 1);
        int ans2 = 0;
        // 前两个数字小于 26 才可以继续递归（才能满足条件）
        if (start < s.length() - 1) {
            int ten = (s.charAt(start) - '0') * 10;
            int one = s.charAt(start + 1) - '0';
            if (ten + one <= 26) {
                ans2 = getResult1(s, start + 2);
            }
        }
        return ans1 + ans2;
    }


    // 方法二
    public static int getTranslationCount2(String s) {
        HashMap<Integer, Integer> map = new HashMap<>();
        return getResult2(s, 0, map);
    }

    private static int getResult2(String s, int start, HashMap<Integer, Integer> map) {
        if (start == s.length()) {
            return 1;
        }
        if (s.charAt(start) == '0') {
            return 0;
        }

        // 判断一下之前是否计算过，如果计算过的话就不用再重复计算了
        // getOrDefault(Object key, V defaultValue) 如果有这个 key，则使用其对应的 value，否则就使用默认值 defaultValue
        int m = map.getOrDefault(start, -1);
        if (m != -1) {
            return m;
        }
        int ans1 = getResult2(s, start + 1, map);
        int ans2 = 0;
        if (start < s.length() - 1) {
            int ten = (s.charAt(start) - '0') * 10;
            int one = s.charAt(start + 1) - '0';
            if (ten + one <= 26) {
                ans2 = getResult2(s, start + 2, map);
            }
        }
        // 保存结果
        map.put(start, ans1 + ans2);
        return ans1 + ans2;
    }

    // 方法三
    public static int getTranslationCount3(String s) {
        int length = s.length();
        int[] dp = new int[length + 1];
        // 从后往前开始
        dp[length] = 1;
        // 最后一个数字不等于 0 就初始化为 1
        if (s.charAt(length - 1) != '0') {
            dp[length - 1] = 1;
        }
        for (int i = length - 2; i >= 0; i--) {
            // 如果遇到 0 的话，就直接跳过
            if (s.charAt(i) == '0') {
                continue;
            }
            int ans1 = dp[i + 1];
            int ans2 = 0;
            int ten = (s.charAt(i) - '0') * 10;
            int one = s.charAt(i + 1) - '0';
            if (ten + one <= 26) {
                ans2 = dp[i + 2];
            }
            dp[i] = ans1 + ans2;
        }
        return dp[0];
    }

    public static int getTranslationCount4(int num) {
        String str = String.valueOf(num);
        int len = str.length();
        int[] dp = new int[len + 1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 1; i < len; i++) {
            if (str.charAt(i - 1) == '0' || str.substring(i - 1, i + 1).compareTo("25") > 0) {
                dp[i + 1] = dp[i];
            } else {
                dp[i + 1] = dp[i] + dp[i - 1];
            }
        }
        return dp[len];
    }

    public static void main(String[] args) {
        String str = "12258";
        System.out.println(getTranslationCount1(str));
        System.out.println(getTranslationCount2(str));
        System.out.println(getTranslationCount3(str));
        System.out.println(getTranslationCount4(12258));
    }
}
