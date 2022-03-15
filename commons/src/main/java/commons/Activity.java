package commons;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.persistence.Entity;
import javax.persistence.Id;



@Entity
public class Activity {


	@JsonProperty ("id")
	private @Id String id;
	@JsonProperty("title")
	private String title;
	@JsonProperty("consumption_in_wh")
	private int consumptionInWh;
	@JsonProperty("image_path")
	private String imagePath;
	@JsonProperty("source")
	private String source;

	/**
	 * An empty constructor
	 */
	public Activity() {
	}

	/**
	 * A constructor for an activity
	 * @param id the unique identifier
	 * @param title the title
	 * @param consumptionInWh the consumption
	 * @param imagePath the path to the image
	 * @param source the source from where taken
	 */
	public Activity(String id, String title, int consumptionInWh, String imagePath, String source) {
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
		return id;
	}

	/**
	 * Getter for the title
	 * @return the title of the activity
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * Getter for the consumption of the activity
	 * @return the consumption of the activity in Wh
	 */
	public int getConsumptionInWh() {
		return consumptionInWh;
	}

	/**
	 * Getter for the image path
	 * @return the path to the image for the activity
	 */
	public String getImagePath() {
		return imagePath;
	}

	/**
	 * Getter for the source from which the activity has been taken
	 * @return the source in the form of a URL
	 */
	public String getSource() {
		return source;
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
	 */
	public void makeFake() {
		int prev = this.consumptionInWh;
		do {
			this.consumptionInWh = Math.round(
					((Double) (Math.random() * 2 * this.consumptionInWh)).floatValue());
		}
		while (this.consumptionInWh == prev);
	}

	/**
	 * ToString method for an Activity
	 * @return the object in the form of human-readable string
	 */
	@Override
	public String toString() {
		return "Activity{" +
				"id='" + id + '\'' +
				", title='" + title + '\'' +
				", consumptionInWh=" + consumptionInWh +
				", imagePath='" + imagePath + '\'' +
				", source='" + source + '\'' +
				'}';
	}

	/**
	 * Equals method for the activity class
	 * @param o the object for comparison
	 * @return true if the objects are equal and false otherwise
	 */
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Activity activity = (Activity) o;
		return consumptionInWh == activity.consumptionInWh && id.equals(activity.id) &&
				title.equals(activity.title) && imagePath.equals(activity.imagePath) &&
				source.equals(activity.source);
	}

	/**
	 * Hashing function for the activity class
	 * @return the hash code of the activity
	 */
	@Override
	public int hashCode() {
		return Objects.hash(id, title, consumptionInWh, imagePath, source);
	}
}
