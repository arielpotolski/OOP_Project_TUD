package commons;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class EstimateQuestionTest {
	private Activity activity;
	private EstimateQuestion question;

	@BeforeEach
	void setup() throws IOException {
		this.activity = new Activity("123", "act1", 1000, "pathpng1", "first site");
		this.question = new EstimateQuestion(this.activity);
	}

	@Test
	public void constructor(){
		assertNotNull(this.question);
		assertNotNull(new EstimateQuestion());
	}

	@Test
	public void getActivityTitleTest(){
		assertEquals(this.question.getActivityTitle(), "act1");
	}

	@Test
	public void getActivityImagePathTest() {
		assertEquals(this.question.getActivityImagePath(), "pathpng1");
	}

	@Test
	public void pointsEarnedTest() {
		assertEquals(250, this.question.pointsEarned(1000, 100, 0.5, false));
	}

	@Test
	public void equalsTestTrue() {
		EstimateQuestion o = new EstimateQuestion(this.activity);
		assertEquals(this.question, o);
	}

	@Test
	public void hashCodeTest() {
		EstimateQuestion o = this.question;
		assertEquals(o.hashCode(), this.question.hashCode());
	}

	@Test
	public void equalsTestFalse() throws IOException {
		Activity activity2 = new Activity("456", "act2", 1000, "pathpng2", "second site");
		EstimateQuestion o = new EstimateQuestion(activity2);
		assertNotEquals(this.question, o);
		assertEquals(o , o);
		assertNotEquals(null, o);
	}

	@Test
	public void imageInByteArrayQuestion() throws IOException {
		BufferedImage bufferedImage = ImageIO.read(
			Objects.requireNonNull(
				Activity
					.class
					.getClassLoader()
					.getResourceAsStream("IMGNotFound.jpg")
			)
		);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "jpg", bos);

		assertArrayEquals(bos.toByteArray(), this.question.imageInByteArrayQuestion());
	}

	@Test
	public void getActivity() {
		assertEquals(this.activity, this.question.getActivity());
	}
}