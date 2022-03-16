package commons;

public abstract class Question {
	public Question() {

	}
	public abstract int pointsEarned(int maxPoints, int answerGiven, double progress);
}