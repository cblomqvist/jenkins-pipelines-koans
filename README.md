## Jenkins pipelines koans

A number of exercises you can train yourself testing given pipelines and methods.
In this project all tests are using [jenkins-stargate](https//github.com/swedbank/jenkins-stargate) framework.

### How to use

* You should have installed gradle 5.
* You can choice whatever IDE you used to work.
* Clone the repo. Make sure you on the **unfixed** branch!
* Note! To make the project compiled you need to have an internet access.

### Where to start

There are four groovy scripts into vars/ repo so far. You can ignore demoPipeline.groovy
It is only for demo purpose. You also can ignore bPipeline.groovy for now. It will be updated.

It would be better to start with aPipeline.groovy and go to the test class for this pipeline 
in src/test directory. If you are using IntelliJ you may just click on test execution green arrow
on the side bar. Alternatively, you may configure 'gradle test' with '--tests "APipelineTest"'
argument. You can run tests in cmd line terminal by using 'gradle test --tests "APipelineTest"'
After you run the tests you will get errors need to be fixed. Before each test case you will find some context 
information which might give you a hint what to do. Good luck!       
