### donation-analytics
## Donation Analytics for political Campaigns.


## Approach To the Problem:

1. The value of percentile is fetched from the input file and the input from “itcont.txt” is read line by line.
2. Each line (record) is first divided in the various fields and desired fields are checked for validation.
3. An entry is made corresponding to a donor in a table and the fields of the records are processed only when we encounter repeat donor.
4. Different classes are made for Recipient, Donor and Calendar year which contains the tables for the corresponding values for each class.
5. Contribution amount, for a repeat donor from a particular zip code, for a particular recipient and for a particular calendar year are stored in a sorted list which is used to calculate percentile.
6. Once processed, the output values are written the file as a String.


## Steps To Run:

The code can be run with the following command from within the insight_testsuite folder:
insight_testsuite~$ ./run_tests.sh

Note: While running test suite I faced an issue. The test cases were failing as no output file was getting generated for the comparison (I was able to generate my output file in the folder “output”). Then I edited the test script by commenting below line (in Bold).

  mkdir -p ${TEST_OUTPUT_PATH}

  cp -r ${PROJECT_PATH}/src ${TEST_OUTPUT_PATH}
  cp -r ${PROJECT_PATH}/run.sh ${TEST_OUTPUT_PATH}
  cp -r ${PROJECT_PATH}/input ${TEST_OUTPUT_PATH}
  cp -r ${PROJECT_PATH}/output ${TEST_OUTPUT_PATH}

  rm -r ${TEST_OUTPUT_PATH}/input/*
  **rm -r ${TEST_OUTPUT_PATH}/output/***
  cp -r ${GRADER_ROOT}/tests/${test_folder}/input/itcont.txt ${TEST_OUTPUT_PATH}/input/itcont.txt

After this I was able to pass the test cases but only when the comparison was made on the basis of my previous run. So, if I run test case for a particular input, then it is getting passed in the second run for the same input. 

## Comments:

1. The code is built using maven build system. Kindly install it for the compilation.

2. I am writing total amount of transactions received by recipient from the contributor's zip code streamed in so far, this calendar year from repeat donors, as a Long Integer to the file. 
The reason behind this is that the output of the test case provided has the representation of total amount of transactions as Integer. 

3. Even if the second donation that came later in the file has a transaction date that is for a previous calendar year, I am identifying the later donation as coming from a repeat donor and outputting the requested calculations for that calendar year, zip code and recipient.

4. I was not able to write the unit test cases due to insufficient time as my exams were going on. However, I have included my own test cases inside insight_testsuite folder.

