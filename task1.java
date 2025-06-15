import java.util.Scanner;
public class StudentGradeTracker {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);

        System.out.println("         STUDENT GRADE TRACKER");
        System.out.println("=======================================");

        System.out.print("Enter number of students: ");
        int numStudents=sc.nextInt();

        System.out.print("Enter number of subjects per student: ");
        int numSubjects=sc.nextInt();

        String[] studentNames=new String[numStudents];
        sc.nextLine();  

        for (int i=0; i<numStudents; i++) {
            System.out.print("Enter name for Student " + (i+1) + ": ");
            studentNames[i]=sc.nextLine();
        }

        int[][] marks=new int[numStudents][numSubjects];
        int[] totalPerStudent=new int[numStudents];
        double[] averagePerStudent=new double[numStudents];
        int classTotal=0;
        int highestScore= Integer.MIN_VALUE;
        int lowestScore= Integer.MAX_VALUE;
        String highestStudent= "";
        String lowestStudent= "";
        int highestSubIndex= -1;
        int lowestSubIndex= -1;

        for (int i=0; i<numStudents; i++) {
            System.out.println("\nEntering marks for " + studentNames[i] + ":");
            for (int j=0; j<numSubjects; j++) {
                while (true) {
                    System.out.print("Enter marks for Subject " + (j+1) + ": ");
                    int mark = sc.nextInt();
                    if (mark>=0 && mark<=100) {
                        marks[i][j]=mark;
                        totalPerStudent[i]+=mark;
                        classTotal+=mark;

                        if (mark>highestScore) {
                            highestScore=mark;
                            highestStudent=studentNames[i];
                            highestSubIndex=j;
                        }
                        if (mark < lowestScore) {
                            lowestScore=mark;
                            lowestStudent=studentNames[i];
                            lowestSubIndex=j;
                        }
                        break;
                    } else {
                        System.out.println("Invalid input! Please enter a number between 0 and 100.");
                    }
                }
            }
            averagePerStudent[i]=(double)totalPerStudent[i]/numSubjects;
        }

        System.out.println("\n           STUDENTS' REPORTS");
        System.out.println("=======================================");
        for (int i=0; i<numStudents; i++) {
            System.out.println("Student: " + studentNames[i]);
            System.out.println("  Total Marks: " + totalPerStudent[i]);
            System.out.printf("  Average: %.2f\n\n", averagePerStudent[i]);
        }
        double classAverage=(double) classTotal/(numStudents * numSubjects);

        System.out.println("         CLASS STATS");
        System.out.println("=======================================");
        System.out.printf("Class Average Score: %.2f\n", classAverage);
        System.out.println("Highest Score in Class: " + highestScore + 
                           " by " + highestStudent + " in Subject " + (highestSubIndex+1));
        System.out.println("Lowest Score in Class: " + lowestScore + 
                           " by " + lowestStudent + " in Subject " + (lowestSubIndex+1));
        sc.close();
    }
}
