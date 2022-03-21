package commons;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Activity {
	private @Id @JsonProperty("id") String id;
	@JsonProperty("title")
	private String title;
	@JsonProperty("consumption_in_wh")
	private long consumptionInWh;
	@JsonProperty("image_path")
	private String imagePath;
	@JsonProperty("source")
	private String source;

	/**
	 * An empty constructor
	 */
	public Activity() {}

	/**
	 * A constructor for an activity
	 * @param id the unique identifier
	 * @param title the title
	 * @param consumptionInWh the consumption
	 * @param imagePath the path to the image
	 * @param source the source from where taken
	 */
	public Activity(String id, String title, long consumptionInWh, String imagePath, String source){
		this.id = id;
		this.title = title;
		this.imagePath = imagePath;
		this.consumptionInWh = consumptionInWh;
		this.source = source;
	}

	/**
	 * Getter for the identifier
	 * @return the identifier of the activity
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * Getter for the title
	 * @return the title of the activity
	 */
	public String getTitle() {
		return this.title;
	}

	/**
	 * Getter for the consumption of the activity
	 * @return the consumption of the activity in Wh
	 */
	public long getConsumptionInWh() {
		return this.consumptionInWh;
	}

	/**
	 * Getter for the image path
	 * @return the path to the image for the activity
	 */
	public String getImagePath() {
		return this.imagePath;
	}

	/**
	 * Getter for the source from which the activity has been taken
	 * @return the source in the form of a URL
	 */
	public String getSource() {
		return this.source;
	}

	/**
	 * Setter for the identifier
	 * @param id the new identifier
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * Setter for the title of the activity
	 * @param title the new title of the activity
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * Setter for the consumption of the activity
	 * @param consumptionInWh the new consumption in wH
	 */
	public void setConsumptionInWh(int consumptionInWh) {
		this.consumptionInWh = consumptionInWh;
	}

	/**
	 * Setter for the image path of the activity
	 * @param imagePath the new image path for the activity
	 */
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	/**
	 * Setter for the source from where the activity has been taken
	 * @param source the new source
	 */
	public void setSource(String source) {
		this.source = source;
	}


	/**
	 * Makes an Activity with wrong consumption for the purpose of InsteadOfQuestions
	 * @param forbiddenValues The values that cannot be a fake value
	 */
	public void makeFake(List<Long> forbiddenValues) {
		long prev = this.consumptionInWh;
		do {
			this.consumptionInWh = Math.round(Math.random() * 2 * this.consumptionInWh);
		} while (this.consumptionInWh == prev || forbiddenValues.contains(this.consumptionInWh));
	}

	/**
	 * ToString method for an Activity
	 * @return the object in the form of human-readable string
	 */
	@Override
	public String toString() {
		return "Activity{" +
				"id='" + this.id + '\'' +
				", title='" + this.title + '\'' +
				", consumptionInWh=" + this.consumptionInWh +
				", imagePath='" + this.imagePath + '\'' +
				", source='" + this.source + '\'' +
				'}';
	}

	/**
	 * Check if `this' is a valid activity.  An activity is considered valid if none of the
	 * fields are null and the energy consumption is a non-negative integer.
	 * @return True if the activity is valid and false otherwise.
	 */
	public boolean isValid() {
		Predicate<String> isNotNullOrEmpty = s -> s != null && !s.isEmpty();

		return isNotNullOrEmpty.test(this.id)
			&& isNotNullOrEmpty.test(this.source)
			&& isNotNullOrEmpty.test(this.title)
			&& this.getConsumptionInWh() >= 0;
	}

	/**
	 * Equals method for the activity class
	 * @param o the object for comparison
	 * @return true if the objects are equal and false otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Activity activity = (Activity) o;
		return this.consumptionInWh == activity.consumptionInWh && this.id.equals(activity.id) &&
				this.title.equals(activity.title) && imagePath.equals(activity.imagePath) &&
				this.source.equals(activity.source);
	}

	/**
	 * Hashing function for the activity class
	 * @return the hash code of the activity
	 */
	@Override
	public int hashCode() {
		return Objects.hash(this.id, this.title, this.consumptionInWh, this.imagePath, this.source);
	}
}

