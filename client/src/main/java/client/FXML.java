package client;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import com.google.inject.Injector;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.Builder;
import javafx.util.BuilderFactory;
import javafx.util.Callback;
import javafx.util.Pair;

public record FXML(Injector injector) {
	public <T> Pair<T, Parent> load(Class<T> c, String part) {
		try {
			var loader = new FXMLLoader(
				this.getLocation(part),
				null,
				null,
				new Factory(),
				StandardCharsets.UTF_8
			);
			Parent parent = loader.load();
			T ctrl = loader.getController();
			return new Pair<>(ctrl, parent);
		} catch (IOException err) {
			throw new RuntimeException(err);
		}
	}

	private URL getLocation(String part) {
		var path = Path.of("", "client", "scenes", part).toString();
		return FXML.class.getClassLoader().getResource(path);
	}

	private class Factory implements BuilderFactory, Callback<Class<?>, Object> {
		@Override
		@SuppressWarnings("rawtypes")
		public Builder<?> getBuilder(Class<?> type) {
			return (Builder) () -> FXML.this.injector.getInstance(type);
		}

		@Override
		public Object call(Class<?> type) {
			return FXML.this.injector.getInstance(type);
		}
	}
}