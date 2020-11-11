package Threads;

public class Count {
    private volatile Integer value = null;

    public void sum(int num) {
        value = fibonacci(num);
    }

    public int fibonacci(int num) {
        if(num<=2) return num;
        int a = 1;
        int b = 1;
        int result = num;
        for (int i=3;i<=num;i++) {
            result = a + b;
            a = b;
            b = result;
        }
        return result;
    }

    public int getResult(){
        return value;
    }

    public int getResultWhile(){
        while (value==null) {
        }
        return value;
    }
}
