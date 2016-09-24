package pac;

/**
 * Created by macbookair on 23.09.16.
 */
public class Main {
    public static void main(String[] args) {
        int N = 10000;
//        int count=0;
        double Tstop = 3396.0;

        Functions f = new Functions(15.0, 16.0, 0.000001, 0.99999999);

        final boolean[] stopFlug = new boolean[1];
        Thread t1;
        Thread t2;
        t1 = new Thread(()->{
            while (f.getSystemTime() <= Tstop && !stopFlug[0]){
                try {
                    if (f.getSystemTime() == Tstop){
                        stopFlug[0] = true;
                    }
                    f.nextTask();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            stopFlug[0] = true;
            System.out.println("--------------Время затраченное для решения задач : "+f.getSystemTime()+" ----------------");

        });

        t2 = new Thread(()->{
            while (f.getSolveCount() <= N && !stopFlug[0]){
                try {
                    if (f.getSolveCount() == N){
                        stopFlug[0]= true;
                        t1.interrupt();
                    }
                    f.nextSolution();

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            stopFlug[0] = true;
            System.out.println("--------------количество решений : "+f.getSolveCount()+" ------------------");
        });

        t1.start();
        t2.start();

        if (stopFlug[0]){
            t1.interrupt();
            t2.interrupt();
        }
//        while (true){
//
//        }
        try {
            t1.join();
//            if (!t1.isAlive() && t2.isAlive()){
//                t2.interrupt();
//            }
            t2.join();

            if (!t1.isAlive() && t2.isAlive()){
                t2.interrupt();
            }else if (t1.isAlive() && !t2.isAlive()){
                t1.interrupt();
            }else {
                t1.join(10);
                t2.join(10);
            }
//            if (!t2.isAlive() && t1.isAlive()){
//                t1.interrupt();
//            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("----------------------------Потоки завершили свою работу------------------------------");
//        for (int i = 0; i < N; i++) {
//            if (f.getSystemTime() <=Tstop) {
////                f.solution();
//                double timeNew = 0;
//                timeNew = newTask();
//                System.out.println("Task " + timeNew);
//                if (tasks.size() == 0){
//                    if (!flug){
//                        flug = true;
//
//                        double sol = newSolution();
////                try {
////                    Thread.sleep((long) (sol*1000));
////                } catch (InterruptedException e) {
////                    e.printStackTrace();
////                }
//                        if (sol != 0){
//                            solutions.add(sol);
//                            System.out.println("Solution "+sol);
//                            flug = false;
//
//                        }else {
//                            tasks.add(timeNew);
//                            flug = false;
//                        }
//                    }else {
//                        tasks.add(timeNew);
//                    }
//                }else {
//                    tasks.add(timeNew);
//                }
//            }else {
//                System.out.println("Count of N = "+ i+"; count of n = "+f.getSolutions().size());
//                return;
//            }
//        }
        System.out.println("End!!!!! Count of N = "+ N+"; count of n = "+f.getSolutions().size());

    }
}
