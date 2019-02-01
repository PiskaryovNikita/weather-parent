package main.tasks.n2;

public final class Demo {

    public static void main(String[] args) {
        FractionNumberOperationImpl operations = new FractionNumberOperationImpl();
        FractionNumberImpl num1 = new FractionNumberImpl(3, 4);
        FractionNumberImpl num2 = new FractionNumberImpl(2, 3);
        System.out.println(operations.sub(num1, num2));
        System.out.println(operations.add(num1, num2));
        System.out.println(operations.div(num1, num2));
        System.out.println(operations.mul(num1, num2));
    }

}
