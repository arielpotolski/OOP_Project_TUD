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
	 * @param source the source from where taken
	 */
	public Activity(String id, String title, int consumptionInWh, String source) {
		this.id = id;
		this.title = title;
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
	 * Setter for the source from where the activity has been taken
	 * @param source the new source
	 */
	public void setSource(String source) {
		this.source = source;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Activity activity = (Activity) o;
		return consumptionInWh == activity.consumptionInWh && id.equals(activity.id)
				&& title.equals(activity.title) && source.equals(activity.source);

	}

	@Override
	public int hashCode() {
		return Objects.hash(id, title, consumptionInWh, source);
	}
}
