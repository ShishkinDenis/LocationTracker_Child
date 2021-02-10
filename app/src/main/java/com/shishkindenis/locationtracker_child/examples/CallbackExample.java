package com.shishkindenis.locationtracker_child.examples;

public class CallbackExample {



    public static void main(String[] args) {
        CallbackExampleInterface callbackExampleInterface = new CallbackExampleInterface() {
            @Override
            public void doSomething() {
                System.out.println("Пошел коллбэк");
                Thread threadTwoSeconds = new Thread(() -> threadTwoSeconds());
                threadTwoSeconds.start();
            }
        };

        Thread threadFourSeconds = new Thread(() -> {
            threadFourSeconds();
//            callbackExampleInterface.doSomething();
            Thread threadTwoSeconds = new Thread(() -> threadTwoSeconds());
            threadTwoSeconds.start();

        });
        threadFourSeconds.start();


    }

    public static void threadTwoSeconds(){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Две секунды прошло");
    }

    public static void threadFourSeconds(){
        try {
            Thread.sleep(4000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Четыре секунды прошло");
    }
}
