import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.Objects;

import client.utils.ServerUtils;
import commons.Activity;
import commons.HighestConsumptionQuestion;
import commons.Player;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import javax.imageio.ImageIO;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.put;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


public class ServerUtilsTest {

	private WireMockServer mockServer;
	private ServerUtils serverUtils;
	ResponseDefinitionBuilder response;


	@BeforeEach
	void setUp() {
		this.response = new ResponseDefinitionBuilder();
		this.response.withHeader("Content-type", "application/json");
		this.response.withStatus(200);
		this.mockServer = new WireMockServer();
		this.mockServer.start();
		WireMock.configureFor("localhost", 8080);
		this.serverUtils = new ServerUtils("localhost");
	}

	@AfterEach
	void tearDown() {
		this.mockServer.stop();
	}

	@Test
	void getConnection() {
	}

	@Test
	void getServer() {

	}

	@Test
	void addPlayer() {

		Player dimitar = new Player("Dimitar", 986);
		Player mitnika = new Player("Mitnika", 199);
		this.response.withBody("{\n" +
				"        \"id\": 0,\n" +
				"        \"nickname\": \"Dimitar\",\n" +
				"        \"points\": 986\n" +
				"    }");
		this.mockServer.stubFor(
				put("/api/players/addPlayer")
						.withRequestBody(
								equalToJson("{\n" +
										"\"id\": 0,\n" +
										"\"nickName\": \"Dimitar\",\n" +
										"\"point\": 986,\n" +
										"\"nickname\": \"Dimitar\",\n" +
										"\"points\": 986\n" +
										"}")
						)
						.willReturn(this.response)
		);
		Player result = this.serverUtils.addPlayer(dimitar);
		assertNotEquals(mitnika, result);
		assertEquals(dimitar, result);
	}

	@Test
	void getQuestions() {
		this.response.withBody("[{" +
				"        \"type\": \"HighestConsumptionQuestion\",\n" +
				"        \"activity1\": {\n" +
				"            \"valid\": true,\n" +
				"            \"id\": \"66-router\",\n" +
				"            \"title\": \"A WIFI router for one day\",\n" +
				"            \"consumption_in_wh\": 144,\n" +
				"            \"image_path\": \"66/router.jpg\",\n" +
				"            \"source\": \"https://www.makeitcheaper.com.au/\",\n" +
				"            \"array-image\": \"\"\n" +
				"        },\n" +
				"        \"activity2\": {\n" +
				"            \"valid\": true,\n" +
				"            \"id\": \"34-netflix\",\n" +
				"            \"title\": \"Watching Netflix for 1 hour\",\n" +
				"            \"consumption_in_wh\": 6100,\n" +
				"            \"image_path\": \"34/netflix.png\",\n" +
				"            \"source\": \"https://www.iea.org/\",\n" +
				"            \"array-image\": \"\"\n" +
				"        },\n" +
				"        \"activity3\": {\n" +
				"            \"valid\": true,\n" +
				"            \"id\": \"78-airconditioner\",\n" +
				"            \"title\": \"Using an air conditioner for 30 minutes\",\n" +
				"            \"consumption_in_wh\": 1500,\n" +
				"            \"image_path\": \"78/airconditioner.jpg\",\n" +
				"            \"source\": \"https://americanhomewater.com/\",\n" +
				"            \"array-image\": \"\"\n" +
				"        },\n" +
				"        \"choice1\": {\n" +
				"            \"valid\": true,\n" +
				"            \"id\": \"66-router\",\n" +
				"            \"title\": \"A WIFI router for one day\",\n" +
				"            \"consumption_in_wh\": 144,\n" +
				"            \"image_path\": \"66/router.jpg\",\n" +
				"            \"source\": \"https://www.makeitcheaper.com.au/\",\n" +
				"            \"array-image\": \"\"\n" +
				"        },\n" +
				"        \"choice2\": {\n" +
				"            \"valid\": true,\n" +
				"            \"id\": \"34-netflix\",\n" +
				"            \"title\": \"Watching Netflix for 1 hour\",\n" +
				"            \"consumption_in_wh\": 6100,\n" +
				"            \"image_path\": \"34/netflix.png\",\n" +
				"            \"source\": \"https://www.iea.org/\",\n" +
				"            \"array-image\": \"\"\n" +
				"        },\n" +
				"        \"choice3\": {\n" +
				"            \"valid\": true,\n" +
				"            \"id\": \"78-airconditioner\",\n" +
				"            \"title\": \"Using an air conditioner for 30 minutes\",\n" +
				"            \"consumption_in_wh\": 1500,\n" +
				"            \"image_path\": \"78/airconditioner.jpg\",\n" +
				"            \"source\": \"https://americanhomewater.com/\",\n" +
				"            \"array-image\": \"\"\n" +
				"        },\n" +
				"        \"title_3\": \"Using an air conditioner for 30 minutes\",\n" +
				"        \"correctAnswer\": {\n" +
				"            \"valid\": true,\n" +
				"            \"id\": \"34-netflix\",\n" +
				"            \"title\": \"Watching Netflix for 1 hour\",\n" +
				"            \"consumption_in_wh\": 6100,\n" +
				"            \"image_path\": \"34/netflix.png\",\n" +
				"            \"source\": \"https://www.iea.org/\",\n" +
				"            \"array-image\": \"\"\n" +
				"        },\n" +
				"        \"title_1\": \"A WIFI router for one day\",\n" +
				"        \"title_2\": \"Watching Netflix for 1 hour\",\n" +
				"        \"image_path_3\": \"78/airconditioner.jpg\",\n" +
				"        \"image_path_1\": \"66/router.jpg\",\n" +
				"        \"image_path_2\": \"34/netflix.png\"\n" +
				"    }\n" +
				"]");
		this.mockServer.stubFor(get("/api/questions/?seed=1").willReturn(this.response));
		HighestConsumptionQuestion result = (HighestConsumptionQuestion)
				(this.serverUtils.getQuestions(1).get(0));
		assertEquals(144, result.getActivity1().getConsumptionInWh());
		assertEquals(6100, result.getActivity2().getConsumptionInWh());
	}

	@Test
	void getActivities() throws IOException {
		Activity activity1 = new Activity("123", "title", 230, "pathpng", "some site");
		this.response.withBody("[{\"valid\": true,\n" +
				"        \"id\": \"123\",\n" +
				"        \"title\": \"title\",\n" +
				"        \"consumption_in_wh\": 230,\n" +
				"        \"image_path\": \"pathpng\",\n" +
				"        \"source\": \"some site\",\n" +
				"        \"array-image\": \"/9j/4AAQSkZJRgABAgAAAQABAAD/\"}]");
		this.mockServer.stubFor(get("/api/questions/getActivities").willReturn(this.response));
		assertEquals(activity1.getTitle(), this.serverUtils.getActivities().get(0).getTitle());
		assertEquals(activity1.getConsumptionInWh(),
				this.serverUtils.getActivities().get(0).getConsumptionInWh());
	}

	@Test
	void getPlayers() {
		this.response.withBody("[{\n" +
				"        \"id\": 1,\n" +
				"        \"nickname\": \"Dimitar\",\n" +
				"        \"points\": 1484\n" +
				"    }]");
		this.mockServer.stubFor(get("/api/players/").willReturn(this.response));
		assertEquals("Dimitar",
				this.serverUtils.getPlayers().get(0).getNickName());
	}

	@Test
	void connectToLobby() {

	}

	@Test
	void refreshLobby() {

	}

	@Test
	void startMultiplayerGame() {

	}

	@Test
	void makeConnection() throws IOException {

	}

	@Test
	void addActivity() throws IOException {
		BufferedImage bufferedImage = ImageIO.read(
				Objects.requireNonNull(
						ServerUtilsTest
								.class
								.getClassLoader()
								.getResourceAsStream("IMGNotFound.jpg")
				)
		);
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ImageIO.write(bufferedImage, "jpg", bos);
		String picture = Base64.getEncoder().encodeToString(bos.toByteArray());
		Activity activity1 = new Activity("123", "title", 230, "pathpng", "some site");
		Activity activity2 = new Activity("124", "titleFake", 212, "", "some site");

		this.response.withBody("{\n" +
				"\"valid\": true,\n" +
				"\"id\": \"123\",\n" +
				"\"title\": \"title\",\n" +
				"\"consumption_in_wh\": 230,\n" +
				"\"image_path\": \"pathpng\",\n" +
				"\"source\": \"some site\",\n" +
				"\"array-image\": \"\"\n" +
				"}");
		this.mockServer.stubFor(
				put("/api/questions/addActivity")
						.withRequestBody(
								equalToJson("{\n" +
										"\"valid\" : true,\n" +
										"\"id\" : \"123\",\n" +
										"\"title\" : \"title\",\n" +
										"\"consumption_in_wh\" : 230,\n" +
										"\"image_path\" : \"pathpng\",\n" +
										"\"source\" : \"some site\",\n" +
										"\"array-image\" :\"" + picture + "\"\n" +
										"}")
						)
						.willReturn(this.response)
		);
		Activity result = this.serverUtils.addActivity(activity1);
		assertNotEquals(activity2, result);
		assertEquals(activity1, result);
	}

	@Test
	void removeActivity() {

	}

	@Test
	void connect() {

	}

	@Test
	void registerForMessages() {

	}

	@Test
	void send() {

	}

	@Test
	void setSession() {

	}
}
