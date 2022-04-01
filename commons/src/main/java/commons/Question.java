package commons;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(
		use = JsonTypeInfo.Id.NAME,
		include = JsonTypeInfo.As.PROPERTY,
		property = "type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = MCQuestion.class, name = "MCQuestion"),
		@JsonSubTypes.Type(value = EstimateQuestion.class, name = "EstimateQuestion"),
		@JsonSubTypes.Type(value = HighestConsumptionQuestion.class,
				name = "HighestConsumptionQuestion"),
		@JsonSubTypes.Type(value = InsteadOfQuestion.class, name = "InsteadOfQuestion")
})

public abstract class Question {
	public Question() {}

	public abstract int pointsEarned(int maxPoints, long answerGiven, double progress,
									boolean doublePoints);

	//necessary for testing
	public abstract boolean equals(Object other);
}