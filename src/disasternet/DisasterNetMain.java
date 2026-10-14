
package disasternet;

import java.util.Scanner;

public class DisasterNetMain {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int choice = -1;

        while (choice != 0) {

            System.out.println("\n==================================");
            System.out.println(" DISASTERNET - EMERGENCY SYSTEM");
            System.out.println("==================================");
            System.out.println("1. M1 - String Algorithms");
            System.out.println("2. M2 - Suffix Structures");
            System.out.println("3. M3 - Dynamic Programming");
            System.out.println("4. M4 - Network Flow");
            System.out.println("5. M5 - Constraint Algorithms");
            System.out.println("6. M6 - Advanced Algorithms");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("Please enter a valid number.");
                sc.nextLine();
                continue;
            }

            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    StringAlgorithms.main(new String[0]);
                    break;

                case 2:
                    SuffixAlgorithms.main(new String[0]);
                    break;

                case 3:
                    DynamicProgramming.main(new String[0]);
                    break;

                case 4:
                    NetworkFlow.main(new String[0]);
                    break;

                case 5:
                    ConstraintAlgorithms.main(new String[0]);
                    break;

                case 6:
                    AdvancedAlgorithms.main(new String[0]);
                    break;

                case 0:
                    System.out.println(
                        "DisasterNet closed successfully.");
                    break;

                default:
                    System.out.println(
                        "Invalid choice. Select 0 to 6.");
            }
        }

        sc.close();
    }
}
