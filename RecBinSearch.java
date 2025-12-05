//imports
import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/**
 * This program reads input.txt of unsorted integers and a search number,
 * sorts the integers, and performs a recursive binary search to locate
 * the target number.
 *
 * It outputs the sorted array, search number, and result.
 *
 * @author Dylan
 * @version 1.0
 * @since 2025-December
 */
final class RecBinSearch {

    /** Prevent instantiation. */
    private RecBinSearch() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Recursive Binary Search (first occurrence).
     *
     * @param arr   the array to search
     * @param target the number to find
     * @param low   the lower index
     * @param high  the upper index
     * @return the index of the target if found, otherwise -1
     */
    public static int recBinSearch(final int[] arr, final int target,
            final int low, final int high) {

        // Base case: if low > high the number is not in the array
        if (low > high) {
            return -1;
        }

        // calculate mid index
        int mid = low + ((high - low) / 2);

        // if target is found at mid
        if (arr[mid] == target) {
            return mid;
        }

        // if target is smaller than mid, search left half
        if (target < arr[mid]) {
            return recBinSearch(arr, target, low, mid - 1);
        }

        // else search right half
        return recBinSearch(arr, target, mid + 1, high);
    }

    /**
     * Program entry point.
     *
     * @param args command-line arguments
     */
    public static void main(final String[] args) {

        // Result message
        String resultMessage;

        // Input and output files
        File inputFile = new File("input.txt");
        File outputFile = new File("output.txt");

        try {
            Scanner scanner = new Scanner(inputFile);
            FileWriter out = new FileWriter(outputFile);

            // while scanner has next line
            while (scanner.hasNextLine()) {

                String numsLine = scanner.nextLine();

                if (!scanner.hasNextLine()) {
                    break;
                }

                String searchLine = scanner.nextLine().trim();

                // Skip empty lines
                if (numsLine.isEmpty() || searchLine.isEmpty()) {
                    out.write(" \n\n");
                    continue;
                }

                try {

                    // splits and parses numbers
                    String[] parts = numsLine.split("\\s+");
                    int[] nums = new int[parts.length];

                    for (int i = 0; i < parts.length; i++) {
                        nums[i] = Integer.parseInt(parts[i]);
                    }

                    // Parse the search number
                    int searchNum = Integer.parseInt(searchLine);

                    // sort array
                    java.util.Arrays.sort(nums);

                    // call binary search
                    int resultIndex = recBinSearch(nums, searchNum,
                            0, nums.length - 1);

                    // Write results
                    out.write("Sorted Array: "
                            + java.util.Arrays.toString(nums) + "\n");
                    out.write("Search Number: " + searchNum + "\n");

                    if (resultIndex >= 0) {
                        resultMessage = "FOUND at index " + resultIndex;
                    } else {
                        resultMessage = "NOT FOUND";
                    }
                    out.write(resultMessage + "\n\n");

                } catch (NumberFormatException e) {
                    out.write("Error splitting and parsing\n\n");
                }
            }

            // Close resources
            scanner.close();
            out.close();

        } catch (FileNotFoundException e) {
            System.err.println("Input file not found");
        } catch (IOException e) {
            System.err.println("Error with output file");
        }
    }
}
