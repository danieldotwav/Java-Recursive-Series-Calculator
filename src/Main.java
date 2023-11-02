package src;
enum MENU { DUMMY, DIVISION, MULTIPLICATION, QUIT, ERROR }

public class Main implements Constants{
    public static void main(String[] args) {
        MENU menu;
        
        System.out.println("Welcome User");

        do {
            menu = getMenuSelection();
            
            switch (menu) {
                case DIVISION:
                    executeDivisionCalculation();
                    break;
                case MULTIPLICATION:
                    executeMultiplicationCalculation();
                    break;
                case QUIT:
                    System.out.println("\nTerminating Program");
                    break;
                default:
                    System.out.println("ERROR: Invalid Menu Option");
            }
        } while(menu != MENU.QUIT);
    }

    static MENU getMenuSelection() {
        int choice;
        StringBuilder menuOptions = new StringBuilder() 
            .append("\nMain Menu\n")
            .append("Please selection one of the following options:\n")
            .append("1. DIVISION        ∑ (1 / (i + (i - 1))) from i=2 to n\n")
            .append("2. MULTIPLICATION  ∑ (i²) from i=1 to n\n")
            .append("3. QUIT\nSelection: ");

        MENU menu = MENU.ERROR;
        
        try {
            System.out.print(menuOptions.toString());
            choice = Integer.parseInt(System.console().readLine());
            
            if (choice > 0 && choice < MENU.values().length) {
                menu = MENU.values()[choice];
            }
        }
        catch (NumberFormatException ex) {
            choice = MENU.values().length;
        }
        return menu;
    }

    static void executeDivisionCalculation() {
        int n = 0;
        double sum = 0.0;

        printCenteredTitle("Division", TITLE_WIDTH, TITLE_CHAR);
        n = getNumTerms(MIN_N, DIV_MAX_N);
        recursiveDivision(MIN_N, n, sum);
    }

    static void executeMultiplicationCalculation() {
        int n = 0;
        int sum = 0;

        printCenteredTitle("Multiplication", TITLE_WIDTH, TITLE_CHAR);
        n = getNumTerms(MIN_N, MULT_MAX_N);
        recursiveMultiplication(n, sum);
    }

    static int getNumTerms(int minVal, int maxVal) {
        int numTerms;

        do {
            try {
                numTerms = Integer.parseInt(System.console().readLine("Enter the number of terms n: "));

                if (numTerms < 0) {
                    System.out.println("ERROR: Negative Values Are Invalid\n");
                }
                else if (numTerms < minVal) {
                    System.out.println("ERROR: Minimum Value For 'n' In This Series Is: " + minVal + "\n");
                }
                else if (numTerms > maxVal) {
                    System.out.println("ERROR: Maximum Value for 'n' In This Series is: " + maxVal + "\n");
                }
            }
            catch (NumberFormatException ex) {
                numTerms = 0; // reset the value of n
                System.out.println("ERROR: Non-Numeric Input Is Invalid\n");
            }
        } while(numTerms < minVal || numTerms > maxVal);
        
        return numTerms;
    }
    
    // Takes in the starting position (current), target (user defined n), and current total sum to recursively calculate series
    static void recursiveDivision(int current, int target, double sum) {
        sum += (1 / ((double) ((current - 1) + (current)) ));

        if (current == 1) {
            System.out.print("\nn = " + target + ", Result: 1");
        }
        else {
            
            System.out.print(1 + "/(" + (current - 1) + " + " + current + ")");
        }

        if (current == target) {
            System.out.println(" = " + String.format("%.4f", sum));
        }
        else {
            System.out.print(" + ");
            recursiveDivision(current + 1, target, sum);
        }
    }

    // Takes in user defined n, and current total sum to recursively calculate series
    static void recursiveMultiplication(int n, int sum) {
        if (sum == 0) {
            System.out.print("\nn = " + n + ", Result: ");
        }

        if (n == 1) { // base case
            sum += 1;
            System.out.println("1 = " + sum);
        }
        else {
            System.out.print("(" + n + "*" + n + ") + ");
            sum += n * n;
            recursiveMultiplication(n - 1, sum);
        }
    }

    public static void printCenteredTitle(String title, int total_width, char padding_char) {
        int title_length = title.length();

        if (title_length > total_width) {
            title = title.substring(0, total_width);
            title_length = title.length();
        }

        // Determine Padding
        int num_padding_chars = (total_width - title_length) / TWO;
        String padding = String.valueOf(padding_char).repeat(num_padding_chars);

        // Ensure symmetry by checking for odd total width and even title length
        boolean needs_extra_padding = (total_width % TWO != 0) && (title_length % TWO == 0);

        // StringBuilder for efficient string concatenation
        StringBuilder output = new StringBuilder("\n");

        // Append padding and title to output
        output.append(padding);
        if (needs_extra_padding) {
            output.append(padding_char);
        }

        // Check for non-empty title
        if (title_length > 0) {
            output.append(" ").append(title).append(" ");
            output.append(padding);
        }
        else {
            output.append(padding_char).append(padding_char).append(padding);
        }

        System.out.println(output.toString());
    }
}