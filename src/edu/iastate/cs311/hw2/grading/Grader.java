/**
 * 
 */
package edu.iastate.cs311.hw2.grading;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;

// import edu.iastate.cs228.hw2.grading.hw2.InsertionSorter;
// import edu.iastate.cs228.hw2.InsertionSorter;

/**
 * 
 * 
 * @author Michael Seibert
 */
public class Grader extends RunListener
{
   private int pointsPossible = 0;
   private int pointsLost = 0;
   private final Class<?> testClass;
   private final String testName;
   private static PrintStream out;
   private static double total = -1;

   /**
 * @throws FileNotFoundException 
    * 
    */
   public Grader(Class<?> testClass, String testName) throws FileNotFoundException
   {
      this.testClass = testClass;
      this.testName = testName;
//      File f = new File("out.txt");
//      if(out == null)
//    	  out = new PrintStream(f);
//      System.setOut(out);
   }
   
   public void run()
   {
      JUnitCore run = new JUnitCore();
      run.addListener(this);
      run.run(testClass);
   }
   
   /**
    * @see org.junit.runner.notification.RunListener#testFailure(org.junit.runner.notification.Failure)
    */
   @Override
   public void testFailure(Failure failure) throws Exception
   {
      super.testFailure(failure);
      Description penalty = failure.getDescription();
      pointsLost += getPoints(penalty);
      String message = failure.getMessage();
      if (!(failure.getException() instanceof AssertionError))
      {
         String exceptionMessage;
         if (message == null)
            exceptionMessage = "";
         else
            exceptionMessage = ": \"" + message + "\"";
         String name = failure.getException().getClass().getSimpleName();
         StackTraceElement locThrown = failure.getException().getStackTrace()[0];
         String fullLocation = locThrown.getFileName() + ":" + locThrown.getLineNumber();
         message = "Threw " + name + exceptionMessage + " at (" + fullLocation + ")";
      }
      System.out.printf("%s (-%d points)%n", message, getPoints(penalty));
   }
   
   /**
    * @see org.junit.runner.notification.RunListener#testRunFinished(org.junit.runner.Result)
    */
   @Override
   public void testRunFinished(Result result) throws Exception
   {
      super.testRunFinished(result);
      System.out.println("*****" + (result.getRunCount()-result.getFailureCount()) +
            " tests passed and " + result.getFailureCount() + " failed out of " + result.getRunCount() + " total on " +
            testName );
      double normalized;
 //     if(this.testClass==HeapTest.class){
    	  normalized = ((pointsPossible-pointsLost)*1.0/pointsPossible)*70; // TODO
    	  pointsPossible = 70;  // TODO
 //     }
 //     else{
 //   	  normalized = ((pointsPossible-pointsLost)*1.0/pointsPossible)*30;
   // 	  pointsPossible = 30;
 //     }
      boolean firstTest = total <0;
      total = total<0?normalized:total+normalized;
      System.out.printf("Score: %.2f/%d\n", normalized, pointsPossible);
      if(!firstTest){
    	  System.out.printf("\nAutomated tests: %.2f/40\n", total);
      }
   }
   
   /**
    * @see org.junit.runner.notification.RunListener#testFinished(org.junit.runner.Description)
    */
   @Override
   public void testFinished(Description description) throws Exception
   {
      super.testFinished(description);
      pointsPossible += getPoints(description);
   }
   
   /**
    * @param description A description of a test
    * @return How many points that test was worth
    */
   private static int getPoints(Description description)
   {
      Points annotation = description.getAnnotation(Points.class);
      int value = annotation.value();
      return value;
   }
   public double getPoints() {
	   return this.total;
   }
}
