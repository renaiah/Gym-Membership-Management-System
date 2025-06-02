import java.util.*;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Scanner sc = new Scanner(System.in);
		Gym gym = new Gym();
        int choice;

        System.out.println("    Welcome to Gym Membership Management System App   ");
        do {
            System.out.println("1. Add New Member");
            System.out.println("2. Assign Membership Plan to Member");
            System.out.println("3. View All Members");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            
            try {
                choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {
                    case 1 -> {
                        System.out.print("Enter Member ID: ");
                        String id = sc.nextLine();
                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();
                        System.out.print("Enter Age: ");
                        int age = sc.nextInt();
                        sc.nextLine();
                        gym.addMember(id, name, age);
                    }

                    case 2 -> {
                        System.out.print("Enter Member ID: ");
                        String memberId = sc.nextLine();
                        gym.showAllPlans();
                        System.out.print("Choose plan index: ");
                        int planIndex = sc.nextInt();
                        sc.nextLine();
                        gym.assignPlanToMember(memberId, planIndex);
                        }

                    case 3 -> {
                    	System.out.println("     Registered Gym Mermbers List    ");
                        gym.viewAllMembers();
                       	}

                    case 4 -> {
                        System.out.println("	Exiting system....");
                    }

                    default -> {
                        System.out.println("	Invalid choice input. Please enter numbers only and in the range of (1-4).");
                    }
                }

            } catch (InputMismatchException exe) {
                System.out.println("	Invalid input. Please enter correct input  for smooth preocess...");
                sc.nextLine(); 
                choice = 0;
            }
        } while (choice != 4);

        sc.close();
	}

}
