package ArrayAndString;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LearnString {
    public String reverse(String str){
        String result = "";
        for(char n:str.toCharArray()){
            result = n+result;
        }
        return result;
    }

    public boolean isPalindrome(String str){
        int len = str.length();

        for(int i=0;i<len/2;i++){
            if(str.charAt(i)!=str.charAt(len-i-1)) return false;
        }
        return true;
    }

    public String onlyUniqueChar(String str){
        String result = "";
        for(char s:str.toCharArray()){
            if(result.indexOf(s)==-1) result = result+s;
        }
        return result;
        
    }

    //Only for English Aphabets
    public boolean isAnagram(String str1,String str2){
        if(str1.length() != str2.length()) return false;
        int[] freq = new int[26];
        str1 = str1.toLowerCase();
        str2 = str2.toLowerCase();
        for(int i=0;i<str1.length();i++){
            freq[str1.charAt(i)-'a']++;
            freq[str2.charAt(i)-'a']--;
        }
        for(int f:freq){
            if(f!=0) return false;
        }
        return true;
    }
    
    public int longestNonRepeatingSubstring(String str){
        int len = str.length();
        int[] freq = new int[26];
        int result = 0;
        int startPoint=-1;
        for(int endPoint=0;endPoint<len;endPoint++){
            freq[str.charAt(endPoint)-'a']++;
            while( freq[str.charAt(endPoint)-'a']>1){
                freq[str.charAt(++startPoint)-'a']--;
            }
            result = Math.max(result,endPoint-startPoint);
        }
        return result;
    }

    public String sortString(String str){
        int len = str.length();
        char[] c = str.toCharArray();
        for (int i = 0;i<len;i++){
            for(int j=0;j<len;j++){
                if (c[j] > c[j + 1]) {
                    char temp = c[j];
                    c[j] = c[j + 1];
                    c[j + 1] = temp;
                }
            }
        }
        return new String(c);
    }

    public List<List<String>> groupAnangram(String[] strArr){
        int len= strArr.length;
        List<String> keys = new ArrayList<>();
        List<List<String>> group = new ArrayList<>();

        for(int i =0 ;i<len;i++) {
            String sortedStr = sortString(strArr[i]);
            int idx = keys.indexOf(sortedStr);

            if(idx == -1){
                keys.add(sortedStr);
                List<String> newGroup = new ArrayList<>();
                newGroup.add(sortedStr);
                group.add(newGroup);
            }
            else{
                group.get(idx).add(strArr[i]);
            }
        }
        return group;
    }

    public boolean compareCircular(String str1,String str2){
        int len1 = str1.length();
        int len2 = str2.length();

        if(len1!=len2) return false;
        return (str1 + str1).contains(str2);//suggested by ChatGPT

        // for(int i=0;i<len1;i++){
        //     String s1sub1 = str1.substring(0,i);
        //     String s1sub2 = str1.substring(i,len1);
        //     String s2sub1 = str2.substring(0,len1-i);
        //     String s2sub2 = str2.substring(len1-i,len1);
        //     System.out.println(s1sub1+":"+s1sub2+"::"+s2sub1+":"+s2sub2);
        //     if(s1sub1.equals(s2sub2) && s1sub2.equals(s2sub1)) return true;
        // }
        
        // return false;
    }

    public boolean validParentheses(String str){
        Map<Character, Character> parentheses = new HashMap<>();
        List<Character> charArr = new ArrayList<>();
        parentheses.put(')', '(');
        parentheses.put('}', '{');
        parentheses.put(']', '[');

        for(char c:str.toCharArray()){
            if (c == '(' || c == '[' || c == '{') charArr.add(c);
            else if(c == ')' || c == ']' || c == '}'){
                if(charArr.isEmpty()) return false;
                if(!(charArr.remove(charArr.size()-1)==parentheses.get(c))) return false;
            }
            else return false;
        }
        return charArr.isEmpty();
    }

    public int decodeWays(String str){
        int len = str.length();
        if (str == null || len == 0 || str.charAt(0) == '0')return 0;

        int prev = 1,curr=1;

        // int[] dp = new int[n + 1];
        // dp[0] = 1;dp[1] = 1; 

        for(int i=1;i<len;i++){
            int temp =str.charAt(i)=='0'?0:curr;
            // dp[i] = str.charAt(i - 1) != '0'?dp[i]+dp[i - 1]:dp[i];

            int twoDigit = Integer.parseInt(str.substring(i - 1, i + 1));

            if (twoDigit >= 10 && twoDigit <= 26) {
                temp += prev;
                // dp[i] += dp[i - 2];
            }         
            
            // Shift values
            prev = curr;
            curr = temp;
        }
        return curr;
        // return dp[n];
    }

    public int editWays(String str1,String str2){
        int len1 = str1.length();
        int len2 = str2.length();
        int[][] dp = new int[len1+1][len2+1];

        for(int i=0;i<=len2;i++)dp[0][i]=i;
        for(int i=0;i<=len1;i++)dp[i][0]=i;

        for(int i=1;i<=len1;i++){
            char curr = str1.charAt(i-1);
            for(int j=1;j<=len2;j++){
                dp[i][j] = Math.min(dp[i-1][j-1],Math.min(dp[i-1][j],dp[i][j-1]));
                if(curr != str2.charAt(j-1)) dp[i][j]++;
            }
        }

        // char[] str1Arr = str1.toCharArray();
        // char[] str2Arr = str2.toCharArray();
        // int i = len1, j = len2;
        // while (i > 0 || j > 0) {
        //     if (i > 0 && j > 0 && str1.charAt(i - 1) == str2.charAt(j - 1)) {
        //         i--; j--; // No operation needed
        //     } else if (i > 0 && j > 0 && dp[i][j] == dp[i-1][j-1] + 1) {
        //         str1Arr[i - 1] = '*';
        //         str2Arr[j - 1] = '*';
        //         i--; j--; // Substitution
        //     } else if (i > 0 && dp[i][j] == dp[i-1][j] + 1) {
        //         str1Arr[i - 1] = '*';
        //         i--; // Deletion in str1
        //     } else if (j > 0 && dp[i][j] == dp[i][j-1] + 1) {
        //         str2Arr[j - 1] = '*';
        //         j--; // Insertion in str1 (means str2 has extra char)
        //     }
        // }
        // System.out.println(str1Arr);
        // System.out.println(str2Arr);

        return dp[len1][len2];
    }
    public int bruteKMP(String str,String pattern){
        List<Integer> result = new ArrayList<>();
        int count=0;
        int len = str.length();
        int plen = pattern.length();
        if(len<plen) return 0;
        if(len==plen) return str.equals(pattern)?1:0;

        for(int i=0;i<len-plen+1;i++){
            boolean flag = true;
            // int iCount = 0;//for unique characters pattern

            for(int j=0;j<plen;j++){
                if(str.charAt(i+j)!=pattern.charAt(j)){
                    flag=false;
                    break;
                }
                // iCount++;/for unique characters pattern
            }
            if(flag){
                count++;
                result.add(i);
                System.out.println(str.substring(i,i+plen));
            }
            // i+=iCount;/for unique characters pattern
        }
        System.out.print(result);
        return count;
    }

    // Compute the Longest Prefix Suffix (LPS) array for the pattern
    public int[] computeLPSArray(String pattern) {
        int len = pattern.length();
        int[] lps = new int[len];
        int lenp = 0; // length of previous longest prefix suffix
        int i = 1;

        LearnArray l = new LearnArray();
        
        lps[0] = 0; // lps[0] is always 0
        
        while (i < len) {
            // l.print(lps);
            if (pattern.charAt(i) == pattern.charAt(lenp)) {
                lenp++;
                lps[i] = lenp;
                i++;
            } else {
                if (lenp != 0) lenp = lps[lenp - 1]; // fallback in the pattern
                else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
        // l.print(lps);
        return lps;
    }

    public int KMP(String str, String pattern) {
        int len = str.length();
        int lenp = pattern.length();

        if (lenp== 0 ||len <lenp) return 0;

        int[] lps = computeLPSArray(pattern);
        List<Integer> result = new ArrayList<>();

        int i = 0; // index for str
        int j = 0; // index for pattern
        while (i < len) {
            // System.out.println(i+":"+j);
            if (str.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
                if (j ==lenp) {
                    // Pattern found at index i-j
                    result.add(i - j);
                    j = lps[j - 1]; // continue searching for next possible match
                }
            } else {
                if (j != 0) {
                    j = lps[j - 1]; // fallback in pattern using lps array
                } else {
                    i++; // no prefix matched, move str index forward
                }
            }
        }

        // Print all match positions and matched substrings
        for (int index : result) {
            System.out.println("Match at index " + index + ": " + str.substring(index, index +lenp));
        }

        System.out.println("Total matches found: " + result.size());
        return result.size();
    }
   
    
    public String countAndSay(int n){
        String result = "1";
        while(n-->1){
            String str = result;
            result = "";
            char curr = str.charAt(0);
            int currCount = 1;
            
            for(int i=1;i<str.length();i++){
                if(str.charAt(i)==curr){
                    currCount++;
                }else{
                    result = result+String.valueOf(currCount) + curr;//use String builder for better result
                    curr = str.charAt(i);
                    currCount=1;
                }
            }
            result = result+String.valueOf(currCount) + curr;
        }
        return result;
    }


    

    public static void main(String[] args) {
        LearnString ls = new LearnString();
        String str = "Goodolddays";
        // System.out.println(ls.reverse(str));
        // System.out.println(ls.isPlindrome(str));
        // System.out.println(ls.onlyUniqueChar(str));
        // System.out.println(ls.isAnagram(str,str+"g"));
        // System.out.println(ls.longestNonRepeatingSubstring("abcabcbd"));
        // boolean t = ls.compareCircular("abcdef", "efabcd");
        // boolean t = ls.validParentheses("{([])}(]");
        // System.out.println(ls.decodeWays("1424"));
        // System.out.println(ls.editWays("intention","execution"));
        // System.out.println(ls.KMP("ababbaabab", "abab"));
        int n=30;
        while(n-->1) System.out.println(ls.countAndSay(n));
        
    }
}

