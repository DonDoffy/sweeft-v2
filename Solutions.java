package debili;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Solutions {

    //task 1

    /**
     * algorithm:
     * Input is an array in which the members are repeated twice except for one number,
     * we need to find that one number, for this we can use bitwise operations such as the XOR operator,
     * 000 xor 010 = 010, 010 xor 010 = 000, i.e. all twice repeating numbers will cancel each other and 000 will remain.
     * 00000...0 + search number = search number
     *
     * time complexity O(n)
     *
     * @param arr
     * @return single number
     */
    public static int singleNumber(int[]arr){
        int answer = 0;
        for (int i:arr) {
            answer^=i;
        }
        return answer;
    }

    //task 2

    public static int minSplit(int amount){
        return f(new int[]{1,5,10,20,50},amount);
    }

    public static int f(int[] coins, int amount) {
        int max = amount + 1;
        int[] dp = new int[amount + 1];
        Arrays.fill(dp, max);
        dp[0] = 0;
        for (int i = 1; i <= amount; i++) {
            for (int j = 0; j < coins.length; j++) {
                if (coins[j] <= i) {
                    dp[i] = Math.min(dp[i], dp[i - coins[j]] + 1);
                }
            }
        }
        return dp[amount] > amount ? -1 : dp[amount];
    }


    /**
     * algorithm:
     * idea: hash table
     * გადავყვეთ მასივს შევინახოთ hashetში ჩვენი ყველა წევრი, მას შემდეგ დავიწყოთ შემოწმება ნულიდან რომელი წევრი
     * არაა შეყვენილი,წევრი არსებობს თუ არა მაგ ოპერააციას O(1) ჭირდება.
     * time complexity O(n)
     * @param arr
     * @return
     */
    // task 3
    public static int notContain(int[]arr){
        Set<Integer> set = new HashSet<>();
        for(int i:arr){
            if(i>=0)
                set.add(i);
        }
        int i = 0;
        while (set.contains(i)){
            i++;
        }
        return i;
    }
    // task 4

    /**
     * algorithm:
     * idea: two pointer
     *
     * we can solve this task also by using BIGINTEGER, but it's much better to solve by this way
     * @param a
     * @param b
     * @return
     */
    public static String addBinary(String a, String b) {
        StringBuilder sb=new StringBuilder();
        int i=a.length()-1;
        int j=b.length()-1;
        int c=0;
        while(i>=0 || j>=0){
            int sum=((i>=0)?a.charAt(i--)-'0':0)+((j>=0)?b.charAt(j--)-'0':0);
            sb.append((sum+c)%2);
            c=(sum+c<2)?0:1;
        }
        if(c!=0)   sb.append(1);
        return sb.reverse().toString();
    }
    //task 5
    /**
     * algorithm:
     * თუ დავაკვირდებოდით ამოცანას, ეს იყო ფიბონაჩის რიცხვები
     * fib(1) = 1
     * fib(2) = 1
     * fib(n) = fib(n-1)+fib(n-2)
     * ამის დაწერა შეგვეძლო რეკურსიულად, დროის მხრივ ცუდი იქნებოდა,
     * მაგალითად fib(6) = fib(5)   +    fib(4)
     *                   /   \          /    \
     *              fib(4)+fib(3)     fib(3) fib(2)
     *fib(3) ის გამოთვლა ორივეგან ცალცალკე მოხდა,რაც ცუდია, შეგვიძლია ეს გამოთვლილი ინფორმაცია
     * შევინახოთ და მომავალში გამოვიყენოთ, ამიტომაც ამოვხსენი ეს ამოცანა დინამიურად
     *
     * @param n
     * @return
     */
    public static int CountVariants(int n){
        if (n<=2)
            return 1;
        int[]dp = new int[n+1];
        dp[1] = 1;
        dp[2] = 1;
        for(int i = 3;i<n+1;i++){
            dp[i] = dp[i-1]+dp[i-2];
        }
        return dp[n];
    }



    // task 6 build own data structure where delete operation time complwxity is O(1)


    /**
     * build simple linked List
     *
     */
    static class List<T>{
        private T info;
        private List<T> next;
        public List(T info){
            this.info = info;
            this.next = null;
        }
        public List(T info, List<T> next){
            this.info = info;
            this.next = next;
        }
    }

    /**
     * build stack using linked list
     * pop() - time complexity O(1)
     * push(T x) - time complexity O(1)
     */
    static class Stack<T>{
        List<T> head;

        public Stack(){
            head = null;
        }
        public void push(T info){
            head = new List<>(info,head);
        }
        public T pop(){
            if(head!=null){
                T temp = head.info;
                head = head.next;
                return temp;
            }
            return null;
        }
    }

    /**
     * other beautiful data structure using interfaces
     * delete operation cost is O(1)
     *
     */


     interface Work<T> extends Iterable<T> {
        boolean noWork();
        Work<T> addWork(T x);
        T viewWork();
        Work<T> removeWork();
        Work<T> reverse();
        class WorkIterator<T> implements Iterator<T> {
            private Work<T> work;
            public WorkIterator(Work<T> work){
                this.work = work;
            }
            @Override
            public boolean hasNext() {
                return !work.noWork();
            }
            @Override
            public T next() {
                T temp = work.viewWork();
                work = work.removeWork();
                return temp;
            }
        }
        default Iterator<T> iterator() {
            return new WorkIterator<>(this);
        }
    }


    static class NoWork<T> implements Work<T> {
        @Override
        public boolean noWork() {
            return true;
        }
        @Override
        public Work<T> addWork(T x) {
            return null;
        }
        @Override
        public T viewWork() {
            throw new RuntimeException("No Work!");
        }
        @Override
        public Work<T> removeWork() {
            throw new RuntimeException("No Work!");
        }
        @Override
        public Work<T> reverse() {
            return this;
        }
    }

    static class SomeWork<T>implements Work<T> {
        private T data;
        private Work<T> work;

        public SomeWork(T x, Work<T> work) {
            data = x;
            this.work = work;
        }

        @Override
        public boolean noWork() {
            return false;
        }
        @Override
        public Work<T> addWork(T x) {
            return new SomeWork<>(x, this);
        }
        @Override
        public T viewWork() {
            return data;
        }
        @Override
        public Work<T> removeWork() {
            return work;
        }
        @Override
        public Work<T> reverse() {
            return helper(this, new NoWork<>());

        }
        private static <T> Work<T> helper(Work<T> w, Work<T> acc) {
            if (w.noWork()) {
                return acc;
            } else {
                acc = new SomeWork<>(((SomeWork<T>) w).data, acc);
                return helper(((SomeWork<T>) w).work, acc);
            }
        }


    }

        public static void main(String[] args) {
         // testers
            System.out.println("task 1");
            System.out.println(singleNumber(new int[]{1,2,3,4,4,3,2,9,1}));  // 9

            System.out.println("task 2");
            System.out.println(minSplit(57)); // 50 5 1 1 -> 4

            System.out.println("task 3");
            System.out.println(notContain(new int[]{-4,1,2,6,7,0})); // 3

            System.out.println("task 4");
            System.out.println(addBinary("10001","1100100")); //1110101

            System.out.println("task 5");
            System.out.println(CountVariants(6));  // 8


            System.out.println("task 6, variation 1");
            Stack<Integer> stack = new Stack<>();
            stack.push(9);
            stack.push(12);
            System.out.println(stack.pop());  // 12

            System.out.println("task 6, variation 2");
            Work<Integer> work = new SomeWork<>(5,new SomeWork<>(1,new SomeWork<>(2,new NoWork<>())));
            work = work.addWork(12);
            work = work.reverse();
            work = work.removeWork();
            for (Integer i:work) {
                System.out.print(i+" ");  // 1 5 12
            }



        }



}
