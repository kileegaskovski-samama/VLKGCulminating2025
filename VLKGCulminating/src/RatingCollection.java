import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Collection of ratings and reviews for the connect 4 game
 */
public class RatingCollection {
	private File ratingFile;
	private File reviewFile;
	
	/**
	 * Creates a ratingCollection containing written reviews and game ratings
	 */
	public RatingCollection() {
		ratingFile = new File("Ratings.txt");
		reviewFile = new File("Reviews.txt");
		
	}
	
	/**
	 * Adds a review to the file "Reviews.txt"
	 * @param review - a written review from 1 to 45 characters, inclusive
	 */
	public void addReviews(String review) {
		try {
			FileWriter out = new FileWriter(reviewFile, true);
			out.write(review + "\n");
			out.flush();
			out.close();
		}
		catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	/**
	 * Adds a rating to the file "Ratings.txt"
	 * @param rating - a number rating from 1 to 5, inclusive
	 * Precondition: rating must be an integer
	 */
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
	
	/**
	 * Returns the average rating for Connect 4
	 * @return average game rating
	 */
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
		
		return average;
	}
	
	/**
	 * Returns the last four reviews for Connect 4
	 * @return returns the last four game reviews
	 */
	public String getLatestReviews() {
		String latestReviews = "";
		int count = 0;
		int secondCount = 0;
		
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
