package server;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class CustomAssertions {
	/**
	 * Assert that the response code returned from `re' is equal to the HTTP status code
	 * specified by `s'.
	 * @param re The ResponseEntity whose status code we want to assert.
	 * @param s The status code we want to compare to.
	 */
	public static void assertResponseEquals(HttpStatus s, ResponseEntity re) {
		assertEquals(re.getStatusCode(), s);
	}
}