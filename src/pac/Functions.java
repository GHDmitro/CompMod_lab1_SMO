package pac;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * Created by macbookair on 22.09.16.
 */
public class Functions extends Thread {
    private List<Double> tasks;
    private List<Double> solutions;
    private boolean flug = false;
    private double systemTime = 0;
//    private final int Nn= 10000;
    private int solveCount=0;
//    private final double Tstop = 332234.0;
    private final double L;
    private final double U;
    private final double MIN_Ri_VALUE;
    private final double MAX_Ri_VALUE;


    public Functions(double L, double U, double MIN_Ri_VALUE, double MAX_Ri_VALUE) {

        this.L = L;
        this.U = U;
        this.MIN_Ri_VALUE = MIN_Ri_VALUE;
        this.MAX_Ri_VALUE = MAX_Ri_VALUE;
        tasks = new LinkedList<>();
        solutions = new LinkedList<>();

        System.out.println("-------------------------Данные введены-----------------------------");
    }



//    public void solution(){
//        double timeNew = 0;
//        timeNew = newTask();
//        System.out.println("Task "+timeNew);
//        if (tasks.size() == 0){
//            if (!flug){
//                flug = true;
//
//                double sol = newSolution();
////                try {
////                    Thread.sleep((long) (sol*1000));
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
//                if (sol != 0){
//                    solutions.add(sol);
//                    System.out.println("Solution "+sol);
//                    flug = false;
//
//                }else {
//                    tasks.add(timeNew);
//                    flug = false;
//                }
//            }else {
//                tasks.add(timeNew);
//            }
//        }else {
//            tasks.add(timeNew);
//        }
//    }

    public synchronized void nextTask() throws InterruptedException {

        while (tasks.size() >= L){
            wait();
        }

        double t = newTask();
//        Thread.sleep((long) (systemTime - t)*10);
        if (tasks.size() > 0){
            System.out.println("------------------------------");
            System.out.println("Очередь не пуста");
            System.out.println("Добавляем в очередь задачу: "+t);
            tasks.add(t);
        }else {
            if (flug){
                System.out.println("------------------------------");
                System.out.println("Процессор загружен задачей");
                System.out.println("Добавляем в очередь задачу: "+t);
                tasks.add(t);
            }else {
                System.out.println("------------------------------");
                System.out.println("Добавляем в очередь задачу: "+t);
                tasks.add(t);
//                flug = true;
//                wait();
            }
        }
        notify();
    }


    public synchronized void nextSolution() throws InterruptedException {
        notify();
        flug = true;
        if (tasks.size() > 0){
            System.out.println("Есть очередь");
            double s = newSolution();
//            Thread.sleep((long) (s*10));
            if (s != 0 || !Double.isInfinite(s)){
                System.out.println("Получено решение : " + s);
                solutions.add(s);
                solveCount++;
                tasks.remove(0);
            }else {
                System.out.println("Не получено решение S, ставим в очередь");
                Double d = tasks.remove(0);
                tasks.add(d);
            }
        }
        while (tasks.size() == 0){
            wait();
        }
    }

    /**
     * new t = T + (-Math.log(randomValue))/L
     * @return time for new task
     */
    private double newTask(){
        Random r = new Random();
        double randomValue = MIN_Ri_VALUE + (MAX_Ri_VALUE - MIN_Ri_VALUE) * r.nextDouble();
        randomValue = (-Math.log(randomValue))/L;
        return systemTime + randomValue;
    }

    private double newSolution(){
        double function = MIN_Ri_VALUE + (MAX_Ri_VALUE - MIN_Ri_VALUE) * Math.random();
        systemTime += function;
        return function;
    }

    public double getSystemTime() {
        return systemTime;
    }

    public List<Double> getSolutions() {
        return solutions;
    }

    public int getSolveCount() {
        return solveCount;
    }
}
