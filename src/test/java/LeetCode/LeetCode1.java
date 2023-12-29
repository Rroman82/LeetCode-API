package LeetCode;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Arrays;


public class LeetCode1 {

//        static public int romanToInt(String s) {
//        int ans = 0;
//
//        int[] roman = new int[435];
//        roman['I'] = 1;
//        roman['V'] = 5;
//        roman['X'] = 10;
//        roman['L'] = 50;
//        roman['C'] = 100;
//        roman['D'] = 500;
//        roman['M'] = 1000;
//
//        for (int i = 0; i + 1 < s.length(); ++i) {
//            if (roman[s.charAt(i)] < roman[s.charAt(i + 1)])
//                ans -= roman[s.charAt(i)];
//            else
//                ans += roman[s.charAt(i)];
//        }
//
//        return ans + roman[s.charAt(s.length() - 1)];
//    }

    static public int romanToInt(String s) {
        int ans = 0, num = 0;
        for (int i = s.length() - 1; i >= 0; i--) {
            switch (s.charAt(i)) {
                case 'I':
                    num = 1;
                    break;
                case 'V':
                    num = 5;
                    break;
                case 'X':
                    num = 10;
                    break;
            }
            if (4 * num < ans) ans -= num;
            else ans += num;
        }
        return ans;
    }

    @Test
    public void testRomanToInt() {
        romanToInt("XIV");
        Assert.assertEquals(romanToInt("XIV"), 11);
        System.out.println("14");
    }

        static public String longuestCommonPrefix(String[] strs) {
            String res = "";
            String firstWord = strs[0];
            boolean isAll = false;
            for (String letter : firstWord.split("")) {
                for (int i = 1; i < strs.length; i++) {
                    if (strs[i].indexOf(res) > -1) {
                        isAll = true;
                    } else {
                        isAll = false;
                    }
                }
                if (isAll) {
                    res = res + letter;
                }
            }
            if(res.equals(firstWord)){
                return res;
            }else{
                return res.substring(0, res.length() - 1);
            }

        }


    public static void main(String[] args) {
        System.out.println(longuestCommonPrefix(new String[]{"flow", "flower", "flowchart"}));
    }
}

