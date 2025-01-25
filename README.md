# Bingo Ticket Generator

## Problem Statement
A small challenge that involves building a Bingo 90 ticket generator.

Requirements:

Generate a strip of 6 tickets
Tickets are created as strips of 6, because this allows every number from 1 to 90 to appear across all 6 tickets. If 
they buy a full strip of six it means that players are guaranteed to mark off a number every time a number is called.
A bingo ticket consists of 9 columns and 3 rows.
Each ticket row contains five numbers and four blank spaces
Each ticket column consists of one, two or three numbers and never three blanks.
The first column contains numbers from 1 to 9 (only nine),
The second column numbers from 10 to 19 (ten), the third, 20 to 29 and so on up until
The last column, which contains numbers from 80 to 90 (eleven).
Numbers in the ticket columns are ordered from top to bottom (ASC).
There can be no duplicate numbers between 1 and 90 in the strip (since you generate 6 tickets with 15 numbers each)
Please make sure you add unit tests to verify the above conditions and an output to view the strips generated
(command line is ok).

Try to also think about the performance aspects of your solution. How long does it take to generate 10k strips? 
The recommended time is less than 1s (with a lightweight random implementation)

## Solution

The solution has been implemented in Java 21, but it is compatible with Java 1.8+. It runs with lower versions,
because of the simplicity of the code trying to use as many primitives as possible.

The key of this solution is to generate a strip of 6 tickets with all 90 numbers which bingo game consists of.
The solution is based on the following steps:

1. We create a strip of 6 tickets, each ticket has 3 rows and 9 columns. In the initialization of each ticket, we
dynamically define the gaps of the columns, with the main purpose of having enough spaces to fill the 90 numbers of the
bingo game for each strip.
2. We generate the numbers of the bingo game, from 1 to 90, and we shuffle them to have a random order of the numbers.
3. We fill each ticket per column, so first column only contains numbers between 1-9, second column from 10-19, and so
on until the last column which contains numbers from 80-90.
4. Finally, we order numbers per column and ticket ascendingly.

One thing to be considered is the performance of the solution, so we have to take into account:
1. **Memory consumption.** Because in this example we are generating just 6 tickets but if we want to put this service in
production, we must consider the memory consumption taking into account the number of the players. That's why we decided to use
short (2 bytes) and byte(1 byte) primitives as much as possible instead of int (4 bytes) or any other complex object provided
by Java (Integer, Byte, Short, etc) (16 bytes).
2. **Time consumption.** Just to get the 10k strip in less than one second, we need to define an algorithm as less complex as possible 
to reach this value. In this case, we checked the most complexity part is to generate random gaps per ticket and strip. So we analyzed
the problem and we found a pattern to generate the gaps per strip based on this:
    - Total gaps of first column per strip are 9
    - Second column, third column and so on until eighth column, there are 8 gaps per strip.
    - Last column, we have 7 gaps per strip.
    - Each ticket has 12 gaps and each row of the ticket has 4 gaps and 5 numbers. Because of we haven't 3 gaps in the
   same column, that means we have at least 1 gap per column (9 gaps counted for now) and so we need to fill 3 more gaps
   which means there are 3 columns with 2 gaps. If we represent gaps in an array, one example could be (1,2,2,2,1,1,1,1,1).
   
   Now we need to analyze how many gaps we have per column and strip:
    - First column has 9 numbers per strip [0-9]. So we have 9 gaps.
    - Second column, third column and so on until eighth column, there are 10 numbers per strip ([10-19], [20-29], ..., [70-79]). So we have 8 gaps.
    - Last column, we have 11 numbers per strip [80-90]. So we have 7 gaps.
  So, having 9+(8*7)+7 = 72 gaps in total per strip, we can generate matrix of gaps per strip following previous rules. One example could be: 
```
            | [1-9] |[10-19]|[20-29]|[30-39]|[40-49]|[50-59]|[60-69]|[70-79]|[80-90]|| SUM ROWS  |
            |-------|-------|-------|-------|-------|-------|-------|-------|-------||-----------|
            |   1   |   2   |   2   |   2   |   1   |   1   |   1   |   1   |   1   ||         12|
            |   1   |   2   |   2   |   2   |   1   |   1   |   1   |   1   |   1   ||         12|
            |   2   |   1   |   1   |   1   |   2   |   2   |   1   |   1   |   1   ||         12|
            |   2   |   1   |   1   |   1   |   1   |   1   |   2   |   2   |   1   ||         12|
            |   2   |   1   |   1   |   1   |   2   |   2   |   1   |   1   |   1   ||         12|
            |   1   |   1   |   1   |   1   |   1   |   1   |   2   |   2   |   2   ||         12|
            |-------|-------|-------|-------|-------|-------|-------|-------|-------||-----------|
            |   9   |   8   |   8   |   8   |   8   |   8   |   8   |   8   |   7   ||SUM COLUMNS|
```
   With that example, we know how many '2' values are available per column and strip and can be mapped to this array:
   (3,2,2,2,2,2,2,2,1)

   So, how can we dynamically crete valid matrix gaps per strip?. For that, we follow backtracking strategy so we check
   every value added to matrix doesn't violate the rules described before. Just to avoid repeated sums per row and column,
   we have temporary variables which store the sum of rows and columns updated. It's part of the improvement of the backtracking solution.

   Once gaps problem is solved, we need to generate (non-repeated) numbers per strip. We could use randoms to generate values
   between 1-90 and check if the number is not repeated in the strip. But this is not so efficient, so we decided to instantiate statically
   numbers, shuffle them and move the structure to a Deque (FIFO) structure solution. So, if we generated non repeated 1-90 numbers,
   we shuffle them and move the structure to a deque, we don't need to check repeated numbers.

   Finally, we just need to order ascendingly the numbers per column and strip and its done!
   
## Other considerations
   As said, we considered to use as many primitives as possible to reduce memory consumption. That's why we implemented
   custom Deque (ShortDeque) and some Custom Collections methods to avoid using Java Collections.
   
## Testing
   We have implemented some unit tests to check the correctness of the solution. Each test checks a different part of the
    solution:
    - We check the generation of the strip of 6 tickets, checking the number of tickets, rows, columns, and numbers per ticket.
    - We check the generation of the gaps per strip, checking the number of gaps per column and strip.
    - We check the generation of the numbers per strip, checking the number of numbers per strip and the non-repetition of the numbers.
    - We check the ordering of the numbers per column and strip.

   Furthermore, just to be sure we run our solution in time below 1 second, we created a performance test, which generates
   50 times 10k Strip. So we check in each iteration we got a less execution of 1 second and we also check the average
   of those 50 iterations.
   
   It can be checked running:
   ```sh
   .\gradlew test
   ```
   After being run, there will be a report in the following path: ```.\build\reports\tests\test\index.html``` where you can check
    the results of the tests and even the performance test results. Check in the Standard Output section to see the performance test results.
## Running the application
   The application can be run executing the following command:
   ```sh
   .\gradlew run
   ```
   After being run, you will see the output of the 6 tickets generated.