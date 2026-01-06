import java.util.ArrayList;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class RatingCollection {
	File ratingFile;
	File reviewFile;
	ArrayList<Integer> allRatings;
	
	public RatingCollection() {
		allRatings = new ArrayList<>();
		ratingFile = new File("Ratings.txt");
		reviewFile = new File("Reviews.txt");
		
		System.out.println("To...:");
	 //   System.out.println(ratingFile.getAbsolutePath());
	}
	
	public void addReviews(String review) {
		try {
	//		Scanner inFile = new Scanner(ratingFile);
			FileWriter out = new FileWriter(reviewFile, true);
			out.write(review + "\n");
			out.flush();
			out.close();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void addRatings(int rating) {
		try {
			FileWriter out = new FileWriter(ratingFile, true);
			out.write(rating + "\n");
			out.flush();
			out.close();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public double getRatings() {
		double average = 0;
		int num = 0;
		
		try {
			Scanner inFile = new Scanner(ratingFile);
			
			while (inFile.hasNextLine()) {
				average += Integer.parseInt(inFile.nextLine());
				num++;
			}
			average /= num;
			
			inFile.close();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		average = Math.round(average*100.0) / 100.0;
//		average *= 100;
//		average /= (int)100;
		
		return average;
	}
	
	public String getLatestReviews() {
		String latestReviews = "";
		int count = 0;
		int secondCount = 0;
		System.out.println("In get latest reveiw");
		
		try {
			Scanner inFile = new Scanner(reviewFile);
			
			while (inFile.hasNextLine()) {
				count++;
				inFile.nextLine();
			}
			
			inFile.close();
			
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		try {
			Scanner inFile = new Scanner(reviewFile);
			
			while (inFile.hasNextLine()) {
				secondCount++;
				if (secondCount == count - 3 || secondCount == count - 2 || secondCount == count - 1 || secondCount == count) {
					latestReviews += "•  " + inFile.nextLine() + "\n";
				}
				else {
					inFile.nextLine();
				}
			}
			
			if (latestReviews.length() == 0) {
				latestReviews = "No reviews";
			}
			
			inFile.close();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return latestReviews;
	}
}
