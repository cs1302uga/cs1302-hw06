
# cs1302-hw06 Generic Method Implementation

![Approved for: Spring 2023](https://img.shields.io/badge/Approved%20for-Spring%202023-magenta)

> ```java
> String notSecret = "VUdBIGlzIGJldHRlciB0aGFuIEdBIFRlY2g=";
> String decoded = new String(java.util.Base64.getDecoder().decode(notSecret));
> System.out.println(decoded);
> ```
> **-- Proven Fact**

This homework assignment explores functional interfaces and lambda expressions in
conjunction with generic methods and interfaces. In this exercise, only the generic
method signatures will be provided. Implementation details are left to the student.

## Course-Specific Learning Outcomes

* **LO2.d:** (Partial) Implement new generic methods, interfaces, and classes in a software solution.
* **LO2.e:** (Partial) Utilize existing generic methods, interfaces, and classes in a software solution.
* **LO4.a:** (Partial) Design, create and use interfaces in a software solution.
* **LO4.b:** (Partial) Utilize interface-based polymorphism in a software solution.

## References and Prerequisites

* [1302 Generic Methods Reading](https://github.com/cs1302uga/cs1302-tutorials/blob/alsi/generics/generic-methods/generic-methods.md)
* [1302 Lambda Expressions Reading](https://github.com/cs1302uga/cs1302-tutorials/blob/alsi/lambda/lambda.rst)
* [`java.util.function.Predicate` Interface Documentation](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/function/Predicate.html)
* [`java.util.function.Function` Interface Documentation](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/function/Function.html)

## Questions

In your notes, clearly answer the following questions. These instructions assume that you are 
logged into the Odin server. 

**NOTE:** For each step, please provide in your notes the full command that you typed to make the related 
action happen along with an explanation of why that command worked. Some commands require multiple options. 
It is important to not only recall what you typed but also why you typed each of them. If context is necessary 
(e.g., the command depends on your present working directory), then please note that context as well.
You won't need to submit your notes in your final submission. However, if done properly, your exercise notes 
will serve as a helpful study guide for the exam.

### Introduction

1. In this assignment, you will be referring to multiple generic interfaces, some of which
   have multiple generic type parameters. It's very important that you keep parameter **composition** 
   in mind when referring to the API documentation, especially in cases where inheritance and interfaces
   are involved. For example, consider the code snippets below. We've omitted most of the details so
   that you can focus on just what is presented. 
   
   ```java
   public interface Set<E> {
       public boolean add(E item);
       public boolean contains(E o);
       ...
   } // Set
   ```
   
   ```java
   public class TreeSet<T> implements Set<T> { // <---------- LINE1 
       // The method bodies are omitted but would be needed in a real implementation
       public T first() { ... }
       public boolean add(T item) { ... }
       ...
   } // TreeSet
   ```
   
   The way it's written, the `T` in `implements Set<T>` will always be the same as the 
   `T` in `TreeSet<T>`. This means all of the following are true:
   
   * `TreeSet<String> implements Set<String>`
   * `TreeSet<Scanner> implements Set<Scanner>`
   * `TreeSet<ConnectFour> implements Set<ConnectFour>`
   
   Here is a snippet that utilizes what has been shown so far:
   
   ```java       
   public static void main(String[] args) {
       Set<String> set = new TreeSet<String>(); // <--------- LINE2
       set.add("hello"); // <-------------------------------- LINE3
       System.out.println(set.first()); // <----------------- LINE4
   } // main
   ```
   
   The table below shows the impact of type composition for the indicated
   lines in the snippets. Anytime you see "⇨" in the table, you should read it
   as "is replaced by" or "becomes":
   
   | LINE# | Result of Composition            | Replacements              |
   |-------|----------------------------------|---------------------------|
   | LINE1 | `Set<E>` ⇨ `Set<T>`              | `E` ⇨ `T`                 |  
   | LINE2 | `Set<E>` ⇨ `Set<String>`         | `E` ⇨ `String`            |
   | LINE2 | `TreeSet<T>` ⇨ `TreeSet<String>` | `T` ⇨ `String`            |
   | LINE3 | `add(E)` ⇨ `add(String)`         | `E` ⇨ `T`, `T` ⇨ `String` |
   | LINE4 | `T first()` ⇨ `String first()`   | `T` ⇨ `String`            |
   
### Getting Started

1. Use Git to clone the repository for this exercise onto Odin into a subdirectory called `cs1302-hw06`:

   ```
   $ git clone --depth 1 https://github.com/cs1302uga/cs1302-hw06.git
   ```

1. Change into the `cs1302-hw06` directory that was just created and look around. There should be
   multiple Java files contained within the directory structure. To see a listing of all of the 
   files under the `src` subdirectory, use the `find` command as follows:
   
   ```
   $ find src
   ```

## Exercise Steps

### Checkpoint 1 Steps

1. If you have not read and understood the [prerequisite readings](#references-and-prerequisites), you should 
   do that **before** attempting to implement the methods in this homework assignment.
   
1. `LambdaFun.java` contains method signatures and documentation for three generic methods. We will implement
    and test these methods in the order that they appear in the Java program. Before getting to work on method
    implementation, create and checkout a branch called `method1` to perform the work related to this 
    checkpoint. You can do this using the following command:
   
    ```
    $ git checkout -b method1
    ```
   
    **EXPLANATION:** When you create a branch, it is as if Git makes a copy of the current
    branch without the `cp` command! If you checkout the branch (like we just did), then stage
    and commit changes, then those commits do not affect the `main` branch. In this way, 
    you can work on adding new features or fixing bugs until you are confident that they work. 
    Towards the end of this checkpoint, you will checkout the `main` branch, and it will appear 
    as it did when you started the homework assignment. Then, you will merge changes from the
    `method1` branch into the currently checked out branch. You can do the same kind of thing 
    in your projects: 

    1. branch to work on a new feature;
    1. stage and commit as you test that feature; then 
    1. once confident, checkout `main` and merge your branch commits into `main`. This way,
       your `main` branch is always in a good state. 

1. Confirm that you are on the desired branch using `git status` and/or `git branch`. You should 
   be able to tell by the output that you are currently on the `method1` branch. How can you tell this
   is the case?
   
1. You will start by implementing the `printlnMatches` method in `LambdaFun.java`. The exact signature for 
   this method is:
    
   ```java
   private static <T> void printlnMatches(T[] array, Predicate<T> condition)
   ```
    
   Answer the following questions about this method in your notes:
   1. What is the generic type parameter?
   1. Specifically, what reference types can replace `T`?
   1. In order to call this method, we need a reference to an object of a class that implements 
      [`Predicate<T>`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/function/Predicate.html). 
      `Predicate<T>` is a functional interface. Once you identify the single, abstract method in the `Predicate` API documentation, 
      **write the full method signature in your notes for reference.** Pay careful attention to the return type and the 
      type of the formal parameter.
      
1. Implement the `printlnMatches` method in `LambdaFun.java`. **You do not need to use a lambda for this step.**
   You will only need to use the object of type `Predicate<T>` referred to by `condition` to call the appropriate method.
   If you did the previous step, then you know what method(s) can be called with `condition`.
   
1. Take a few minutes to read the in-line comments above the last two statements in the `main` method of `LambdaFun.java`.
   1. The `containsA` variable uses a lambda expression to provide an implementation for the single, abstract
      method of the `Predicate<String>` interface that you wrote down in your notes in a previous step. Please note the 
      parameterization (`String`) that replaces `T`. See the [Oracle tutorial](https://docs.oracle.com/javase/tutorial/java/javaOO/lambdaexpressions.html#syntax)
      if you're still unsure about the syntax. Use your answer for **3.iii.** to help you write this lambda.
   1. This lambda returns `true` if the string argument contains the letter `"a"` (case sensitive).
      You may wish to refer to the documentation for [`java.lang.String`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/lang/String.html).
   
1. Variable `containsA` references an object of type `Predicate<String>`. **HOLD THE FRONT DOOR!** That one line of code
   created a class that implemented an interface and created an object of that class! It must be the case since
   `containsA` is a reference variable of an interface type and the code compiles. This object contains a specific implementation
   of the single abstract method in the interface.
   
1. Compile and run your code. You will know if everything is working correctly if the method only prints strings 
   containing the letter `"a"`. There should be 4 total lines of output at this point.

1. Stage and commit all changes.
   
1. Using the last two statements in the `main` method as an example, make three additional calls to `printlnMatches` that 
   output the information below. After implementing each call, we recommend compiling and running before moving on to the
   next output. Make sure to include a print statement in between each method call so you can distinguish between the output.
   
   1. **Call 1**: Outputs all strings in the array.
   1. **Call 2**: Outputs all strings that are less than 6 characters.
   1. **Call 3**: **Tricky:** Outputs all strings that contain two or more a's.

1. Once you are confident that your method calls are correct, check your output against the [expected output](expected.md).
   If your output does not match the expected output, revise your method calls or your lambda expressions.
   
1. Compile and make sure your code passes the `check1302` style audit.

1. Now that everything on this branch compiles, ensure that all changes 
   in the current branch have been staged and committed. 
   **Do not execute the next command without making sure all changes are staged and committed**
   After that, checkout the `main` branch using the following command:
   
   ```
   $ git checkout main
   ```

1. Take a look at `LambdaFun.java`. Notice that you have reverted back to the
   original code (before you made any changes!). The new, updated, code is 
   on the other branch. To incorporate the changes (merge the changes) from the 
   `method1` branch into the current (`main`) branch, use the following command:
   
   ```
   $ git merge method1
   ```

   Did the merge work? How do you know?
   
1. View the condensed, graphical version of your Git log using `git adog`.
   You should see that the current (`HEAD`) branch is `main` and that `main`
   and `method1` are in the same place! You've now merged the changes into
   the `main` branch. Now, we don't need the `method1` branch anymore. You can safely
   delete it with:
   
   ```
   git branch -d method1
   ```
   
   Check the output of `git adog` to see that it is removed.
   
<hr/>

![CP](https://img.shields.io/badge/Just%20Finished%20Checkpoint-1-success?style=for-the-badge)

<hr/>

### Checkpoint 2 Steps

1. For the next method, create and checkout a branch called `method2` using the same steps given in the 
   previous checkpoint.

1. Take a close look at the `printlnMappedMatches` method and its associated Javadoc in `LambdaFun.java`. 
   The exact signature for this method is:

   ```java
   private static <T> void printlnMappedMatches(T[] array, Predicate<T> condition, Function<T, String> mapper)
   ```

   Answer the following questions about this method in your notes:
   * What is the generic type parameter?
   * Specifically, what reference types can replace `T`?
   * In order to call this method, we need a reference to an object of a class that implements 
     [`Function<T, String>`](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/function/Function.html)
     and a reference to an object of a class that implements `Predicate<T>`. Similar to `Predicate<T>`,
     `Function<T, R>` is a functional interface. Write the full method signature of the single, abstract method
     of `Function<T, R>` in your notes. Pay careful attention to the return type and the type of the formal parameter.
     **Note**: The method *can* (but doesn't have to) return a datatype that is different from the datatype of the 
     parameter.
     
1. Implement the `printlnMappedMatches` method in `LambdaFun.java`. You do not need to use a lambda for this step.
   You will only need to use the `Function<T, R>` and `Predicate<T>` references to call the appropriate methods.
   
1. At the end of the `main` method of `LambdaFun` class:
   * Declare a variable of type `Predicate<Email>` and assign to it, using a lambda expression, a reference to
     an object that tests if the sender of the email does not have a `"gatech.edu"` email address 
     (we'll "pretend" that they go to the spam filter). Remember, you can always refer to the API documentation
     for the associated interface when trying to determine the layout of your lambda.
   * Declare a variable of type `Function<Email, String>` and assign to it, using a lambda expression, a reference
     to an object that takes an `Email` object as a parameter and return only the contents of the email (don't include
     `sender`, `recipient`, `date`, etc.) as some nicely formatted `String`. **Note:** `contents` is an instance
     variable of the `Email` class. Also, remember that you can  refer to the API 
     documentation for the associated interface when trying to determine the layout of your lambda.
   * **Note:** If the code you add causes your `main` method to exceed the `check1302` line limit, you can move
     some of your code into test methods and have `main` call the test methods. Just remember to fully comment
     your test methods.
   
1. Call the `printlnMappedMatches` using your newly created variables to print out emails in the array referred
   to by `inbox` that do not come from GA Tech. In other words, we don't want to receive any emails from a 
   "gatech.edu" address. :)
   
   Make sure to provide sufficient output so that an instructor/TA will be
   able to easily tell that everything is working properly. See the previous checkpoint for an example
   of appropriate output.
   
1. Stage and commit all changes.
   
1. Make sure your code passes the `check1302` style audit then stage and commit all changes.

1. When you are convinced that everything is working properly, merge your `method2` branch into `main` as you did
   in the previous checkpoint. Do not delete the `method2` branch so we can see that you created a branch.

<hr/>

![CP](https://img.shields.io/badge/Just%20Finished%20Checkpoint-2-success?style=for-the-badge)

<hr/>

### Submission Steps

**Each student needs to individually submit their own work.**

1. Create a plain text file called `SUBMISSION.md` directly inside the `cs1302-hw06`
   directory with the following information:

   1. Your name and UGA ID number
  
   Here is an example of the contents of `SUBMISSION.md`.
   
   ```
   Sally Smith (811-000-999)
   ```

1. Change directories to the parent of `cs1302-hw06` (e.g., `cd ..` from `cs1302-hw06`). If you would like
   to make a backup tar file, the instructions are in the submissions steps for [hw01](https://github.com/cs1302uga/cs1302-hw01).
   We won't repeat those steps here and you can view them as optional.
   
1. Use the `submit` command to submit this exercise to `csci-1302`:
   
   ```
   $ submit cs1302-hw06 csci-1302
   ```
   
   Read the output of the submit command very carefully. If there is an error while submitting, then it will displayed 
   in that output. Additionally, if successful, the `submit` command creates a new receipt file in the directory you 
   submitted. The receipt file begins with rec and contains a detailed list of all files that were successfully submitted. 
   Look through the contents of the rec file and always remember to keep that file in case there is an issue with your submission.

   **Note:** You must be on Odin to submit.

<hr/>

![CP](https://img.shields.io/badge/Just%20Finished-Submission-success?style=for-the-badge)

<hr/>

[![License: CC BY-NC-ND 4.0](https://img.shields.io/badge/License-CC%20BY--NC--ND%204.0-lightgrey.svg)](http://creativecommons.org/licenses/by-nc-nd/4.0/) [![License: CC BY-NC 4.0](https://img.shields.io/badge/Instructor%20License-CC%20BY--NC%204.0-lightgrey.svg)](http://creativecommons.org/licenses/by-nc/4.0/)

<small>
Copyright &copy; Michael E. Cotterell, Bradley J. Barnes, and the University of Georgia.
This work is licensed under 
a <a rel="license" href="http://creativecommons.org/licenses/by-nc-nd/4.0/">Creative Commons Attribution-NonCommercial-NoDerivatives 4.0 International License</a> to students and the public and licensed under
a <a rel="license" href="http://creativecommons.org/licenses/by-nc/4.0/">Creative Commons Attribution-NonCommercial 4.0 International License</a> to instructors at institutions of higher education.
The content and opinions expressed on this Web page do not necessarily reflect the views of nor are they endorsed by the University of Georgia or the University System of Georgia.
</small>
