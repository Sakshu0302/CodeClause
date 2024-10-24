import java.util.ArrayList;
import java.util.Scanner;

// Employee class
class Employee {
    private int id;
    private String name;
    private String position;
    private double salary;

    // Constructor
    public Employee(int id, String name, String position, double salary) {
        this.id = id;
        this.name = name;
        this.position = position;
        this.salary = salary;
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    // To display employee details
    @Override
    public String toString() {
        return "Employee ID: " + id + ", Name: " + name + ", Position: " + position + ", Salary: " + salary;
    }
}

// Main Employee Management System class
public class EmployeeManagementSystem {

    private ArrayList<Employee> employees;

    public EmployeeManagementSystem() {
        employees = new ArrayList<>();
    }

    // Method to add a new employee
    public void addEmployee(int id, String name, String position, double salary) {
        Employee newEmployee = new Employee(id, name, position, salary);
        employees.add(newEmployee);
        System.out.println("Employee added successfully.");
    }

    // Method to display all employees
    public void displayEmployees() {
        if (employees.isEmpty()) {
            System.out.println("No employees to display.");
        } else {
            for (Employee employee : employees) {
                System.out.println(employee);
            }
        }
    }

    // Method to update an employee
    public void updateEmployee(int id) {
        boolean found = false;
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                Scanner scanner = new Scanner(System.in);
                System.out.print("Enter new name: ");
                employee.setName(scanner.nextLine());
                System.out.print("Enter new position: ");
                employee.setPosition(scanner.nextLine());
                System.out.print("Enter new salary: ");
                employee.setSalary(scanner.nextDouble());
                System.out.println("Employee details updated successfully.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Employee not found.");
        }
    }

    // Method to remove an employee
    public void removeEmployee(int id) {
        boolean found = false;
        for (Employee employee : employees) {
            if (employee.getId() == id) {
                employees.remove(employee);
                System.out.println("Employee removed successfully.");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println("Employee not found.");
        }
    }

    // Main method to run the system
    public static void main(String[] args) {
        EmployeeManagementSystem system = new EmployeeManagementSystem();
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nEmployee Management System Menu:");
            System.out.println("1. Add Employee");
            System.out.println("2. Display Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Remove Employee");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter Employee ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();  // Consume newline
                    System.out.print("Enter Employee Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Employee Position: ");
                    String position = scanner.nextLine();
                    System.out.print("Enter Employee Salary: ");
                    double salary = scanner.nextDouble();
                    system.addEmployee(id, name, position, salary);
                    break;
                case 2:
                    system.displayEmployees();
                    break;
                case 3:
                    System.out.print("Enter Employee ID to update: ");
                    int updateId = scanner.nextInt();
                    system.updateEmployee(updateId);
                    break;
                case 4:
                    System.out.print("Enter Employee ID to remove: ");
                    int removeId = scanner.nextInt();
                    system.removeEmployee(removeId);
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 5);

        scanner.close();
    }
}
