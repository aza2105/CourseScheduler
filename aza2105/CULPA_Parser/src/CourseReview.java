/**
 * @author aalsyed
 *
 */
public class CourseReview
{
	//instance variables
	private int numWhoThinkReviewIsFunny;
	private String workload;
	private String reviewText;
	private int numWhoAgreeWithReview;
	private int numWhoDisagreeWithReview;
	private int instructorID;
	
	//constructor
	public CourseReview(int numWhoThinkReviewIsFunny, String workload,
			String reviewText, int numWhoAgreeWithReview,
			int numWhoDisagreeWithReview, int instructorID)
	{
		this.numWhoThinkReviewIsFunny = numWhoThinkReviewIsFunny;
		this.workload = workload;
		this.reviewText = reviewText;
		this.numWhoAgreeWithReview = numWhoAgreeWithReview;
		this.numWhoDisagreeWithReview = numWhoDisagreeWithReview;
		this.instructorID = instructorID;
	}

	//instance methods
	public int getNumWhoThinkReviewIsFunny()
	{
		return numWhoThinkReviewIsFunny;
	}

	public void setNumWhoThinkReviewIsFunny(int numWhoThinkReviewIsFunny)
	{
		this.numWhoThinkReviewIsFunny = numWhoThinkReviewIsFunny;
	}

	public String getWorkload()
	{
		return workload;
	}

	public void setWorkload(String workload)
	{
		this.workload = workload;
	}

	public String getReviewText()
	{
		return reviewText;
	}

	public void setReviewText(String reviewText)
	{
		this.reviewText = reviewText;
	}

	public int getNumWhoAgreeWithReview()
	{
		return numWhoAgreeWithReview;
	}

	public void setNumWhoAgreeWithReview(int numWhoAgreeWithReview)
	{
		this.numWhoAgreeWithReview = numWhoAgreeWithReview;
	}

	public int getNumWhoDisagreeWithReview()
	{
		return numWhoDisagreeWithReview;
	}

	public void setNumWhoDisagreeWithReview(int numWhoDisagreeWithReview)
	{
		this.numWhoDisagreeWithReview = numWhoDisagreeWithReview;
	}

	public int getInstructorID()
	{
		return instructorID;
	}

	public void setInstructorID(int instructorID)
	{
		this.instructorID = instructorID;
	}
	
}
