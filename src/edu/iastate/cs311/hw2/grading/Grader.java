/**
 * 
 */
package edu.iastate.cs311.hw2.grading;

import java.io.FileNotFoundException;

import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;


/**
 * 
 * 
 * @author Michael Seibert
 * @author Justin Derby
 */
public class Grader extends RunListener
{
   private int pointsPossible = 0;
   private int pointsLost = 0;
   private final Class<?> testClass;
   private final String testName;
   private static double setTotal = 0;
   private static double studentTotal = 0;

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
   
   public static double getTotal() {
	   return setTotal;
   }
   
   public static void resetGrader() {
	   studentTotal = 0;
	   setTotal = 0;
   }
   
   public static double getStudentTotal() {
	   return studentTotal;
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
      String name = failure.getTestHeader();
      name = name.substring(0, name.indexOf('('));
      System.out.printf("[%s] %s (-%d points)%n", name, message, getPoints(penalty));
   }
   
   /**
    * @see org.junit.runner.notification.RunListener#testRunFinished(org.junit.runner.Result)
    */
   @Override
   public void testRunFinished(Result result) throws Exception
   {
      super.testRunFinished(result);
      int totalResult = getClassTotal();
      System.out.println("***** " + (result.getRunCount()-result.getFailureCount()) +
            " tests passed and " + result.getFailureCount() + " failed out of " + result.getRunCount() + " total on " +
            testName );
      double  normalized = ((pointsPossible-pointsLost)*1.0/pointsPossible)*totalResult;
      
      System.out.println(String.format("Raw Score: %d/%d", (pointsPossible-pointsLost), pointsPossible));
      System.out.println(String.format("Normalized Score: %.2f/%d", normalized, totalResult));
      studentTotal += normalized;
      setTotal += totalResult;
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
      if (annotation == null)
		  System.err.println(description.getMethodName() + " does not have a Points annotation!");
      int value = annotation.value();
      return value;
   }
   
   /**
    * @return How many points that whole test class was worth
    */
   private int getClassTotal()
   {
	  Total annotation = testClass.getAnnotation(Total.class);
	  if (annotation == null)
		  System.err.println(testClass.getName() + " does not have a Total annotation!");
      int value = annotation.value();
      return value;
   }
}