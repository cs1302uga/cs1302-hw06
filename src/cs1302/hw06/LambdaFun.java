package cs1302.hw06;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.function.IntFunction;
import java.util.function.Predicate;
import java.util.function.Function;

/**
 * This class contains methods related to {@code cs1302-hw06}.
 */
public class LambdaFun {

    /** Standard Input scanner. */
    private static Scanner input = new Scanner(System.in);

    /**
     * Main entry-point into the application.
     * @param args the command-line arguments.
     */
    public static void main(String[] args) {

        String[] myStrings = new String[] {
            "CSCI",        "1302",    "is", "an", "awesome", "course!",
            "Lambda", "expressions", "scare", "me",     "but",       "I",
            "will",   "persevere"
        };

        Email[] inbox = new Email[] {
            //  Email(recipient, sender, sendDate, contents)
            new Email("bjb211@uga.edu", "yellowjacket@gatech.edu",
                      LocalDate.of(2019, 2, 4), "Go GA Tech!"),
            new Email("bjb211@uga.edu", "mom@aol.com",
                      LocalDate.of(2019, 2, 5), "Have a good day!"),
            new Email("bjb211@uga.edu", "steve@anyotherschool.edu",
                      LocalDate.of(2019, 2, 6), "I wish I would've chosen UGA"),
            new Email("bjb211@uga.edu", "student1@uga.edu",
                      LocalDate.of(2019, 2, 7), "Thanks for teaching us!"),
            new Email("bjb211@uga.edu", "yellowjacket@gatech.edu",
                      LocalDate.of(2019, 2, 8), "Go GA Tech!")
        };

        // This line provides an implementation for the single, abstract
        // method of Predicate<String> using a lambda expression.
        Predicate<String> containsA = (String str) -> {
            return str.contains("a");
        };

        // The lambda expression above creates an object with an override for
        // the Predicate<String>'s test method. It's conceptually similar to 
        // the following, which will not work as written but can be adjusted
        // into a valid lambda expression with some minor adjustments:
        //
        // Predicate<String> containsA = public boolean test(String str) {
        //     return str.contains("a");
        // };

        // This line uses the object created in the previous statement to
        // make printlnMatches print only the strings that contain the letter "a".
        LambdaFun.<String>printlnMatches(myStrings, containsA);

    } // main

    /**
     * Prints the elements of the array that pass the test specified by the given predicate.
     * More formally, this method prints all elements {@code element} in the array referred to by
     * {@code array} such that {@code condition.test(element)}. Each element will be printed on
     * its own line.
     *
     * @param <T> the type of the array elements
     * @param array the specified array
     * @param condition the specified predicate
     * @throws NullPointerException if the specified predicate is {@code null}
     */
    private static <T> void printlnMatches(T[] array, Predicate<T> condition) {
        throw new UnsupportedOperationException("not yet implemented");
    } // printlnMatches

    /**
     * Prints the elements of the array that pass the test specified by the given predicate
     * using a string mapper. More formally, this method prints the string-mapped elements
     * {@code mapper.apply(element)} in the array referred to by {@code array} for each
     * {@code element} such that {@code condition.test(element)}. Each string-mapped element
     * will be printed on its own line.
     *
     * @param <T> the type of the array elements
     * @param array the specified array
     * @param condition the specified predicate
     * @param mapper the specified string mapper
     * @throws NullPointerException if the specified predicate or string mapper is {@code null}
     */
    private static <T> void printlnMappedMatches(T[] array, Predicate<T> condition,
                                                 Function<T, String> mapper) {
        throw new UnsupportedOperationException("not yet implemented");
    } // printlnMappedMatches

} // LambdaFun
