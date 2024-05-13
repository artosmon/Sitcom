package org.aton;


import lombok.Getter;



public class App {

    private static Member[] members;

    public static void main(String[] args) throws InterruptedException {

        Member chandler = new Member();
        chandler.setName("chandler");

        Member joey = new Member();
        joey.setName("joey");

        Member monica = new Member();
        monica.setName("monica");

        Member phoebe = new Member();
        phoebe.setName("phoebe");

        Member rachel = new Member();
        rachel.setName("rachel");

        Member ross = new Member();
        ross.setName("ross");

        startThreads(joey, chandler, monica, rachel, ross, phoebe);
    }

    private static void startThreads(Member... threads) {

        members = threads;
        for (Thread thread : threads) {
            thread.start();
        }

    }

    private static void changeFlagAllThreadsOnTrue() {

        for (Member thread : members) {
            thread.notified = true;
        }
    }



    static class Printer {

        private int i;

        public  void printChandlerPhrase(int i) {

            if (i == 0) {
                System.out.println("Chandler: Hey.");
            } else if (i == 1) {
                System.out.println("Chandler: And this from the cry-for-help department. Are you wearing make up?");
            } else if (i == 2) {
                System.out.println(
                        "Chandler: That's so funny, " +
                                "'cause I was thinking you look more like Joey Tribbiani, man splash woman."
                );
            } else if (i == 3) {
                System.out.println("Chandler: Do you know which one you're gonna be?");
            } else if (i == 4) {
                System.out.println("Chandler: Good luck, man. I hope you get it.");
            }



        }

        public  void printJoeyPhrase(int i) {

            if (i == 0) {
                System.out.println("Joey: Hey, hey.");
            } else if (i == 1) {
                System.out.println("Joey: Yes, I am. As of today, I am officially Joey Tribbiani, actor slash model.");
            } else if (i == 2) {
                System.out.println("Joey: You know those posters for the City Free Clinic?");
            } else if (i == 3) {
                System.out.println("Joey: No, but I hear lyme disease is open, so… (crossed fingers)");
            } else if (i == 4) {
                System.out.println("Joey: Thanks.");
            }


        }

        public  void printMonicaPhrase(int i) {

            if (i == 0) {
                System.out.println(
                        "Monica: Oh, wow, so you're gonna be one of those \"healthy, wealthy, healthy guys\"?"
                );
            }

        }

        public  void printPhoebePhrase(int i) {

            if (i == 0) {
                System.out.println("Phoebe: Hey.");
            } else if (i == 1) {
                System.out.println("Phoebe: What were you modeling for?");
            } else if (i == 2) {
                System.out.println("Phoebe: You know, the asthma guy was really cute.");
            }

        }

        public  void printRachelPhrase(int i) {

            if (i == 0) {
                System.out.println("Rachel: Hello everyone!");
            }

        }

        public  void printRossPhrase(int i) {

            if (i == 0) {
                System.out.println("Ross: Yo!");
            }

        }





        public synchronized void print(Member member) {
            boolean wasPrinted;
            for (; i < 20; i++) {

                wasPrinted = false;
                    switch (i) {
                        case 1, 5, 7, 12, 14 -> {

                            if(!Thread.currentThread().getName().equals("chandler")) {

                                putInWaiting(member);
                            } else {
                                wasPrinted = true;
                                printChandlerPhrase(member.getCounter());
                                member.incrementCounter();
                            }
                        }
                        case 0, 6, 9, 13, 15 -> {

                            if(!Thread.currentThread().getName().equals("joey")) {

                                putInWaiting(member);
                            } else {
                                wasPrinted = true;
                                printJoeyPhrase(member.getCounter());
                                member.incrementCounter();;
                            }
                        }
                        case 10 -> {

                            if(!Thread.currentThread().getName().equals("monica")) {

                                putInWaiting(member);
                            } else {
                                wasPrinted = true;
                                printMonicaPhrase(member.getCounter());
                                member.incrementCounter();
                            }
                        }

                        case 2, 8, 11 -> {

                            if(!Thread.currentThread().getName().equals("phoebe")) {

                                putInWaiting(member);
                            } else {
                                wasPrinted = true;
                                printPhoebePhrase(member.getCounter());
                                member.incrementCounter();
                            }
                        }
                        case 3 -> {

                            if(!Thread.currentThread().getName().equals("rachel")) {

                                putInWaiting(member);
                            } else {
                                wasPrinted = true;
                                printRachelPhrase(member.getCounter());
                                member.incrementCounter();;
                            }
                        }
                        case 4 -> {

                            if(!Thread.currentThread().getName().equals("ross")) {

                                putInWaiting(member);
                            } else {
                                wasPrinted = true;
                                printRossPhrase(member.getCounter());
                                member.incrementCounter();
                            }
                        }


                    }

                    if (wasPrinted) {
                        changeFlagAllThreadsOnTrue();
                        this.notifyAll();
                    }
            }
        }

        private synchronized void putInWaiting(Member member) {

            member.notified = false;

            while (!member.notified) {
                try {
                    this.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
            i--;

        }



    }

}


class Member extends Thread {

     public boolean notified;

     @Getter
     private int counter;
     private final static App.Printer printer = new App.Printer();

     public void incrementCounter() {
         counter++;
     }

     @Override
     public void run() {

         printer.print(this);

     }


}

