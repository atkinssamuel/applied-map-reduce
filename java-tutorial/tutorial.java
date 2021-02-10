https://www.youtube.com/watch?v=eIrMbAQSU34

// primitive types
byte // 1 byte
short // 2 bytes
int // 4 bytes
long // 8 bytes
float // 4 bytes
double // 8 bytes
char // 2 bytes
boolean // 1 byte

// primitive variable declaration
int age = 30;
long bigNumber = 3123456789L;
float doubleExample = 10.99F;
char letter = 'A'; // single characters with single quotes, multiple characters with double quotes
boolean boolEx = true;

// reference types
String message = "Hello World"; // shorthand
String message = new String("Hello World"); // long-version

// string operations (reference type):
String messageOne = "M1";
String messageTwo = "M2";
String messageAddition = messageOne + messageTwo;

// arrays (reference type):
int[] numbers = new int[5];
numbers[0] = 5; // indexing starts at 0
int[] numbers = {1, 2, 3};
int[][] numberMatrix = {{1, 4, 1}, {4, 2, 1}};
numberMatrix[row][column] = val;


// casting
// byte < short < int < long
short x = 1;
int y = x + 2; // x is cast as an integer

// constants:
final float PI = 3.14F; // final modifier marks variable as unchangeable

// reading input:
Scanner scanner = new Scanner(System.in);
byte age = scanner.nextByte(); // reads a byte
int bigAge = scanner.nextInt(); // reads an integer
String name = scanner.next(); // reads a string

// functions:
returnType functionName(arg1_type arg1, ...){
    return returnType
}

// classes:
visibility_modifier class ClassName {
    // the first method is the main class method - has the same name as the class but camel cased
    visibility_modifier return_type className() {

    }
}

// Main class:
package com.basepackage;

public class Main {
    public static main(String[] args){
        // entry point of standard java console application
    }
}


// visibility modifier:
public // accessible from other parts of the program
private // not accessible from other parts of the program
public static // main method should always be static

// package: specifies which package this class belongs to (think namespace)
package com.java-tutorial

// if statements:
if (temp > 30) {
    System.out.println("Temp is greater than 30");
} else if (temp > 20 && temp <= 30) {
    System.out.println("It is a beautiful day");
}

// ternary operator
String className = income > 100_000 ? "First" : "Economy";
String className = (condition) ? value_if_true : value_if_false;

// switch statement
switch (condition_variable){
    case value_1:
        break;
    case value_2:
        break;
    case value_n:
        break;
    default:
        break;
}

// for loops:
for (int i = 0; i < 10; i++){
    System.out.println("Loop iteration");
}

// while loops:
while (i > 0){
    // do something
}

// for/while loop interrupt commands:
continue;
break;

// for each loop:
String[] fruits = {"Apple", "Mango", "Orange"};
for (String fruit: fruits){
    // will iterate over each fruit in fruits
}


// running java file from command line:
javac path-to-file.java
java path-to-file