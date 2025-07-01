package ArrayAndString;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LearnArray {

    public void print(int[] arr){
        System.out.println(Arrays.toString(arr));
    }

    public void print(int[][] arr){
        for(int[] ar:arr) print(ar);
    }

    public int[] maxAndMin(int[] arr){
        // int len = arr.length;
        int[] returnValue ={ Integer.MIN_VALUE,Integer.MAX_VALUE};

        for(int a:arr){
            if (a>returnValue[0]) returnValue[0]=a;
            if (a<returnValue[1]) returnValue[1]=a;
        }

        return returnValue;
    }

    public int[] reverse(int[] arr){
        int len = arr.length;
        int[] returnValue = new int[len];
        
        for(int i=0;i<len;i++){
            returnValue[i] = arr[len-i-1];
        }

        return returnValue;
    }

    public int[] kthMaxAndMin(int[] arr,int k){
        int len = arr.length;
        int[] returnValue ={ Integer.MIN_VALUE,Integer.MAX_VALUE};
        for(int i=0;i<k;i++){
            int minIndex = i;
            int maxIndex = i;
            for(int j=i+1;j<len-i;j++){
                if(arr[j]>arr[maxIndex]) maxIndex = j;
                if(arr[j]<arr[minIndex]) minIndex = j;
            }
            //swap
            int temp = arr[minIndex];
            arr[minIndex]= arr[i];
            arr[i]=temp;
            
            temp = arr[maxIndex];
            arr[maxIndex]= arr[len-i-1];
            arr[len-i-1]=temp;
        }

        returnValue[0]=arr[len-k];
        returnValue[1]=arr[k-1];
        return returnValue;

    }

    public int[] sort0s1s2sarray(int[] arr){
        int len = arr.length;
        int[] returnValue = new int[len];
        Arrays.fill(returnValue,1);
        int zeroIndex=0,twoIndex=len-1;
        for(int n:arr){
            if(n==0){
                returnValue[zeroIndex]=0;
                zeroIndex++;
            } 
            else if(n==2){
                returnValue[twoIndex]=2;
                twoIndex--;
            }
        }
        return returnValue;
    }

    public boolean checkSort(int[ ] arr){
        int len = arr.length;
        if (len < 2) return true;

        int assumedOrder = arr[0]>arr[1]?1:-1;
        
        for(int i=1;i<len-1;i++)
            if(!(arr[i]*assumedOrder>=arr[i+1]*assumedOrder)) return false;

        return true;
    }
    public int[] uniqueArray(int[] arr){
        
        Set<Integer> seen = new HashSet<>();
        List<Integer> uniqueList = new ArrayList<>();

        for (int n : arr) {
            if (!seen.contains(n)) {
                seen.add(n);
                uniqueList.add(n);
            }
        }

        // Convert List<Integer> to int[]
        int[] result = new int[uniqueList.size()];
        for (int i = 0; i < uniqueList.size(); i++) {
            result[i] = uniqueList.get(i);
        }

        return result;
    }

    public int[] arrayUnion(int[] arr1,int[] arr2){
        int len1 = arr1.length;
        int len2 = arr2.length;
        int[] returnValue = new int[len1+len2];
        int rVsize = 0;
        int[] joined = new int[arr1.length + arr2.length];
        System.arraycopy(arr1, 0, joined, 0, arr1.length);
        System.arraycopy(arr2, 0, joined, arr1.length, arr2.length);
        for(int n:joined){
            boolean flag = false;
            for(int i=0;i<rVsize;i++){
                if(n == returnValue[i]) flag=true;
            }
            if(flag)continue;
            returnValue[rVsize]=n;
            rVsize++;
        }
        return Arrays.copyOfRange(returnValue,0, rVsize);
    }

    public int[] arrayIntersection(int[] arr1,int[] arr2){
        int len1 = arr1.length;
        int len2 = arr2.length;
        int[] returnValue = new int[len1<len2?len1:len2];

        int[] unique = new int[len1];
        int counter=0;

        for(int i=0;i<len1;i++){
            boolean flag = false;
            for(int j=i+1;j<len1;j++){
                if(arr1[i]==arr1[j]){
                    flag = true;break;
                }
            }
            if(flag) continue;
            unique[counter]=arr1[i];
            counter++;
        }
        counter=0;
        for(int n:arr2){
            boolean flag = false;
            for(int i=0;i<unique.length;i++){
                if(unique[i]==n){
                    flag = true;break;
                }
            }
            if(flag) continue;
            returnValue[counter]=n;
            counter++;
        }

        return returnValue;

    }

    public int maxSubArraySum(int[] arr) {
        int maxSum = arr[0]; 
        int currentSum = arr[0];

        for (int i = 1; i < arr.length; i++) {
            currentSum = Math.max(arr[i], currentSum + arr[i]);
            maxSum = Math.max(maxSum, currentSum);
        }

        return maxSum;
    }

    public int[][] pairsOfGivenSum(int[] arr,int k){
        int[][] returnValue = new int[arr.length][2];
        int[] uniqueArr = uniqueArray(arr);
        Arrays.sort(uniqueArr);

        int count=0,left=0 ,right=uniqueArr.length-1;
        while(left<right){

            while(uniqueArr[left] + uniqueArr[right]>k && left<right)right--;
            if(uniqueArr[left] + uniqueArr[right]==k) {
                returnValue[count][0] = uniqueArr[left];
                returnValue[count][1] = uniqueArr[right];
                count++;
                right--;
                left++;
            }
            else left++;
        }
        return returnValue;
    }

    public int maxConsecutiveSequence(int[] arr){
        arr = uniqueArray(arr);
        Arrays.sort(arr);

        int result= 1,counter=1;
        for (int i = 1; i < arr.length; i++) {
            if (arr[i] - arr[i - 1] == 1) {
                counter++;
            } else {
                counter = 1;
            }
            if (counter > result) {
                result = counter;
            }
        }
        return result;
    }

    public int[] longestSubArrayWithGivenSum(int[] arr,int k){
        int len = arr.length;

        int left=0;
        int sum=0;
        int maxLen = 0;
        int resLeft = -1, resRight = -1;


        for(int right = 0;right<len;right++){
            sum+=arr[right];
            while(sum>k && left<right){
                sum-=arr[left];
                left++;
            }
            if(sum==k){
                if(right-left+1>maxLen){
                    maxLen = right-left+1;
                    resLeft = left;
                    resRight = right;
                }
            }
        }
        if (resLeft == -1) {
        // No subarray found
        return new int[0];
    }
    
        int[] result = {resLeft, resRight};
    
    return result;
    }

    public int[] mergeSortedArraysTwo(int[] arr1,int[] arr2){
        int len1 = arr1.length;
        int len2 = arr2.length;
        int[] result = new int[len1+len2];
        int arr1Order =  len1<2?1:(arr1[0]<arr1[len1-1]?1:-1);
        int arr2Order =  len2<2?1:(arr2[0]<arr2[len2-1]?1:-1);

        if(arr1Order==-1) arr1 = reverse(arr1);
        if(arr2Order==-1) arr2 = reverse(arr2);

        int j=0,i=0;
        while(i<len1 && j<len2)
            result[i+j] = arr1[i]<arr2[j]?arr1[i++]:arr2[j++];

        while(i<len1) result[i+j] = arr2[i++];
        while(j<len2) result[i+j] = arr2[j++];

        return result;

    }


    public int maxProductSubArray(int[] arr){
        int len = arr.length;

        int result =1;
        int prod = 1;
        int[] maxProd = new int[len];
        for(int i=0;i<len;i++){
            prod = arr[i];
            maxProd[i] = arr[i];
            for(int j=i+1;j<len;j++){
                if( arr[j]==0) break;
                prod = prod*arr[j];
                maxProd[i]=Math.max(maxProd[i],prod);
            }
        }
        result = maxProd[0];
        for(int n:maxProd){
            result = Math.max(n,result);
        }

        return result;
    }

    public int maximumSumSubarrayofSizeK(int[] arr,int k){
        int len = arr.length;
        int sum=0;
        for(int i=0;i<k;i++) sum+=arr[i];
        int result = sum;
        for(int i=1;i<len-k+1;i++) {
            sum += arr[i+k-1]-arr[i-1];          
            result = Math.max(result,sum);
        }
        return result;
    }

    public int maximumSumNonAdjacent(int[] arr){  
        int include=0,exclude=0;

        for(int n:arr){
           int new_include = exclude+n;
           int new_exclude = Math.max(include,exclude);

           include = new_include;
           exclude  = new_exclude;

           System.out.print(n);
           System.out.print(",");
           System.out.print(new_include);
           System.out.print(",");
           System.out.print(new_exclude);
           System.out.print(",");
           System.out.print(include);
           System.out.print(",");
           System.out.print(exclude);
           System.out.println();

        }

        return Math.max(include, exclude);
    }



    public static void main(String[] args) {
        // LearnArray l = new LearnArray();
        // int[] arr = {8, 3, 5, 11, 5};//,45,876,2,3,11,7,8};
        // int[] arr1 = {11,10,9,7,5,3,1};
        // int[] result = l.maxAndMin(arr);
        // System.out.println("Max: " + result[0] + ", Min: " + result[1]);
        // l.print(l.mergeSortedArraysTwo(arr,arr1));
        // System.out.println(l.maxProductSubArray(arr));
        // System.out.println(l.maximumSumNonAdjacent(arr));
    }
}
