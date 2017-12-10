# Embedded-SQL

There are two programs, one program to generate each report.

1. For each customer compute the minimum and maximum sales quantities along with the corresponding products (purchased), dates (i.e., dates of those minimum and maximum sales quantities) and the state in which the sale transaction took place. If there are >1 occurrences of the min or max, choose one – do not display all.
For the same customer, compute the average sales quantity.

---> Query1.java

2. For each combination of customer and product, output the maximum sales quantities for NJ and minimum sales quantities for NY and CT in 3 separate columns. Like the first report, display the corresponding dates (i.e., dates of those maximum and minimum sales quantities). Furthermore, for NY and NJ, include only the sales that occurred earlier than 2009; for CT, include all sales.


---> Query2.java

The queries for the same are in a single text file named "Query1 and Query2”.

Important Notes while code execution:
Assuming that the postgresql and jdbc connections are made, change the username and password in the code. 
"Steps to run this program" 

Command Line Instructions:set path=”C:\Program Files\Java\jdk1.8.0_102\bin”

FOR QUERY 1 : javac -classpath ".;./postgresql.jar" Query1.java java -classpath ".;./postgresql.jar" Query1
FOR QUERY 2 : javac -classpath ".;./postgresql.jar" Query2.java java -classpath ".;./postgresql.jar" Query2



For Query 1

Important steps in the code:
1.  Retrieving a tuple at a time from the database
2. Initializing the data structure with the first tuple
3.  In the while loop, creating a new entry in the data structure if an entry with the same customer doesn’t exist
4. If such an entry exists, updating the minimum and maximum quantity based on the quantity retrieved in the
current tuple from the database
Pseudocode:
1. Define a data structure for storing the data
2. Initialize the data structure with the data from the first tuple
3. WHILE there is a tuple in the database
a. Set flag as 0
b. FOR loop, set counter = 0 to (size of the data structure i.e. c – 1)
a. Search the data structure if there is an entry for the customer from the retrieved tuple
b. If the customer is found, update the data structure accordingly
c. Set flag as 1
c. IF flag is 0, i.e. if the customer is not found, initialize a new entry for the data structure
4. END WHILE


For Query 2

Query 2:
The data structure used for this code is efficient to store the data and has a linear time complexity i.e. O(n) and space complexity of c. Using arrays will increase the time and space complexity to O(n2) . Using hash maps will keep the time complexity to O(n), but will increase the space complexity to O(n).
Important steps in the code:
1. Retrieving a tuple at a time from the database
2. Initializing the data structure with the first tuple using the init function
3. In the while loop, creating a new entry in the data structure if an entry with the same product and customer doesn’t exist
4. If such an entry exists, updating that entry using the min_max function
Pseudocode:
1. Define a data structure data2 for storing the data
2. Initialize the data structure using init function
3. SET an integer variable c as 1 //counter for the data structure
4. WHILE there is a tuple in the database
a. Set flag as 1
b. FOR loop, set counter = 0 to (size of the data structure i.e. c – 1)
a. Search the data structure if there is an entry for the corresponding product and customer from the retrieved tuple and if the state != PA
b. If the product and customer pair is found, then
 Set flag as 0
call min_max function
c. If the product and customer pair is not found and flag is 1 , then call init function
5. END WHILE
INIT FUNCTION 1) IF state is NJ
a) IF year < 2008
i) Set NJ_MAX as quant
ii) Set date
b) ELSE
i) Set NJ_MAX as -1
c) Set NY_MIN as -1 d) Set NJ_MAX as -1
2) IF state is NY
IF year < 2008
a) Set NY_MIN as quant b) Set NJ_MAX as -1
c) Set CT_MIN as -1
3) IF state is CT
a) Set CT_MIN as quant b) Set NJ_MAX as -1
c) Set NY_MIN as -1
MIN_MAX FUNCTION i) IF state is NJ
(1) IF year < 2008 AND NJ_MAX is -1
1. Set NJ_MAX as quant
2. Set date
(2) ELSE IF year < 2008 AND NJ_MAX < quant
1. Set NJ_MAX as quant
2. Set date
ii) IF state is NY
IF year < 2008 and
(1) IF NY_MIN is-1
(a) Set NY_MIN asquant (b) Set date
(2) IF NY_MIN is>quant
(a) Set NY_MIN asquant (b) Set date
iii) IF state is CT
(1) IF CT_MINis-1
(a) Set CT_MIN as quant
(b) Set date
(2) IF CT_MIN is>quant
(a) Set CT_MIN as quant (b) Set date



My observations from this assignment have been that evaluation is much simpler than expression.This is because our programming languages are much more flexible than SQL.
However, evaluation requires more instructions as we have to explain each and every step in the process to obtain the output.

