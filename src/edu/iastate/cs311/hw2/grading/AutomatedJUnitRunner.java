package edu.iastate.cs311.hw2.grading;

import java.io.FileNotFoundException;

public class AutomatedJUnitRunner {
	public static void main(String[] args) throws FileNotFoundException {
		System.out.println("-----------------------------\r\n"
				+ "HW 2 Grade\r\n"
				+ "Graded by: []\r\n"
				+ "Email: []\r\n"
				+ "Office Hours: \r\n"
				+ "-----------------------------\r\n"
				+ "Automated Test Output:");
		Grader g=new Grader(HeapTest.class, "Heap");
		g.run();
		System.out.println("-----------------------------");
//2 points for the author tag, 
//3 points for the .zip file name format,
//and 5 points for the directory structure (edu/iastate/...),
//20 points for the code that does not work. This would add up to 100 points.

		double total = Grader.getTotal(); // 100 for this project. This value might be changed if the file paths are allocated points. (eg. changed to 95 if file paths is worth 5 points)
		double studentTotal = Grader.getStudentTotal();
		int zipFileFormatPoints = 5;
		int filePathPoints = 5;
		System.out.println(String.format( "(  %d/ 5).zip file name format  \r\n"
				+ "(  %d/ 5)file path \r\n"
				+ "(%d/90)code execution  \r\n"
				, zipFileFormatPoints, filePathPoints, (int)studentTotal));
		System.out.println("-----------------------------");

		studentTotal+=zipFileFormatPoints + filePathPoints;
		System.out.println(String.format("total grade: %.2f/%.2f", studentTotal, total));
	}
}
