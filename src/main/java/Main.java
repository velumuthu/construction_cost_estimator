import java.util.Scanner;
import materials.*;
import management.*;
import estimator.ProjectEstimator;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String projectName = "";
        while (projectName.isEmpty()) {
            System.out.print("Enter Project Name: ");
            projectName = sc.nextLine().trim();
            if (projectName.equalsIgnoreCase("default")) {
                projectName = "Default Project";
                break;
            }
            if (projectName.isEmpty()) {
                System.out.println("Project name cannot be empty. Please enter a project name or type 'default'.");
            }
        }
        ProjectEstimator estimator = new ProjectEstimator(projectName);
        int choice;

        do {
            System.out.println("\n===== Construction Cost Estimator =====");
            System.out.println("1. Add Material");
            System.out.println("2. Add Labor");
            System.out.println("3. Add Equipment");
            System.out.println("4. View All Materials (from Database)");
            System.out.println("5. View All Labor (from Database)");
            System.out.println("6. View All Equipment (from Database)");
            System.out.println("7. Generate Cost Report");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");
            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("Choose Material: 1-Cement  2-Steel  3-Sand  4-Bricks");
                    int mType = sc.nextInt();
                    System.out.print("Enter Unit Cost: ");
                    double cost = sc.nextDouble();
                    System.out.print("Enter Quantity: ");
                    double qty = sc.nextDouble();

                    Material m = null;
                    switch (mType) {
                        case 1: m = new Cement(cost, qty); break;
                        case 2: m = new Steel(cost, qty); break;
                        case 3: m = new Sand(cost, qty); break;
                        case 4: m = new Bricks(cost, qty); break;
                        default: System.out.println("Invalid material!"); continue;
                    }
                    estimator.addMaterial(m);
                    System.out.println("Material added successfully!");
                    break;

                case 2:
                    System.out.print("Enter Labor Type: ");
                    String type = sc.next();
                    System.out.print("Enter Rate per Hour: ");
                    double rate = sc.nextDouble();
                    System.out.print("Enter Hours Worked: ");
                    int hours = sc.nextInt();
                    estimator.addLabor(new Labor(type, rate, hours));
                    System.out.println("Labor added successfully!");
                    break;

                case 3:
                    System.out.print("Enter Equipment Name: ");
                    String eqName = sc.next();
                    System.out.print("Enter Rental Rate per Day: ");
                    double ratePerDay = sc.nextDouble();
                    System.out.print("Enter Days Used: ");
                    int days = sc.nextInt();
                    estimator.addEquipment(new Equipment(eqName, ratePerDay, days));
                    System.out.println("Equipment added successfully!");
                    break;

                case 4:
                    estimator.viewAllMaterials();
                    break;

                case 5:
                    estimator.viewAllLabor();
                    break;

                case 6:
                    estimator.viewAllEquipment();
                    break;

                case 7:
                    estimator.generateReport();
                    break;

                case 8:
                    System.out.println("Exiting... Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        } while (choice != 8);

        sc.close();
    }
}