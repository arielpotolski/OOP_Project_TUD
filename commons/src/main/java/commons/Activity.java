package commons;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static commons.Utility.nullOrEmpty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import javax.imageio.ImageIO;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
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
	@JsonProperty("array-image") @Lob
	private byte[] imageInArray;

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
	public Activity(String id, String title, long consumptionInWh,
					String imagePath, String source) throws IOException {
		this.id = id;
		this.title = title;
		this.imagePath = imagePath;
		this.consumptionInWh = consumptionInWh;
		this.source = source;
		this.imageInArray = castImageToByteArray();
	}

	/**
	 * Makes a deep copy of this instance
	 * @return a new instance of this class with the same parameters
	 */
	public Activity deepCopy() {
		Activity result = new Activity();
		result.setConsumptionInWh(this.consumptionInWh);
		result.setId(this.id);
		result.setTitle(this.title);
		result.setSource(this.source);
		result.setImagePath(this.imagePath);
		result.setImageInArray(this.imageInArray);
		return result;
	}

	/**
	 * Getter for the image in a byte array
	 * @return the image in a byte array
	 */
	public byte[] getImageInArray() {
		return imageInArray;
	}

	/**
	 * Setter for the image's byte array
	 * @param imageInArray the new byte array for the image
	 */
	public void setImageInArray(byte[] imageInArray) {
		this.imageInArray = imageInArray;
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
	public void setConsumptionInWh(long consumptionInWh) {
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
		Random random = new Random(prev);
		do {
			this.consumptionInWh = Math.round(random.nextDouble() * 2 * this.consumptionInWh);
		} while (this.consumptionInWh == prev || forbiddenValues.contains(this.consumptionInWh));
	}

	/**
	 * Parses an image to byte array so that it could be more easily sent to the user
	 * If the picture is not found automatically it is set to ImageNotFound
	 *
	 * !! If someone wants to import pictures they need to put the folder with Activities under the
	 * name 'activities' in commons/src/main/resources/
	 * This folder is added to the gitignore file and therefore will not be pushed in gitlab
	 *
	 * @return byte array containing information about the image
	 * @throws IOException The exception if there is something wrong with the file
	 */
	public byte[] castImageToByteArray() throws IOException {
		try {
			String extension = "";

			int i = imagePath.lastIndexOf('.');
			if (i > 0) {
				extension = imagePath.substring(i+1);
			}
			BufferedImage bufferedImage =ImageIO
					.read(Objects
							.requireNonNull(Activity
									.class
									.getClassLoader()
									.getResourceAsStream("activities/" + imagePath)));
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, extension, bos);
			return bos.toByteArray();
		} catch (Exception err) {
			BufferedImage bufferedImage =ImageIO
					.read(Objects
							.requireNonNull(Activity
									.class
									.getClassLoader()
									.getResourceAsStream("IMGNotFound.jpg")));
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			ImageIO.write(bufferedImage, "jpg", bos);
			return bos.toByteArray();
		}
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
		return !nullOrEmpty(this.id)
			&& !nullOrEmpty(this.source)
			&& !nullOrEmpty(this.title)
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
		return this.consumptionInWh == activity.consumptionInWh
				&& this.id.equals(activity.id)
				&& this.title.equals(activity.title)
				&& imagePath.equals(activity.imagePath)
				&& this.source.equals(activity.source);
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