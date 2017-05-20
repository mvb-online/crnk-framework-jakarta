package io.crnk.meta;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import io.crnk.client.CrnkClient;
import io.crnk.client.http.okhttp.OkHttpAdapter;
import io.crnk.client.http.okhttp.OkHttpAdapterListenerBase;
import io.crnk.core.boot.CrnkProperties;
import io.crnk.meta.mock.model.Schedule;
import io.crnk.meta.provider.resource.ResourceMetaProvider;
import io.crnk.rs.CrnkFeature;
import okhttp3.OkHttpClient.Builder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Before;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.concurrent.TimeUnit;

public abstract class AbstractMetaJerseyTest extends JerseyTest {

	protected CrnkClient client;

	public static void setNetworkTimeout(CrnkClient client, final int timeout, final TimeUnit timeUnit) {
		OkHttpAdapter httpAdapter = (OkHttpAdapter) client.getHttpAdapter();
		httpAdapter.addListener(new OkHttpAdapterListenerBase() {

			@Override
			public void onBuild(Builder builder) {
				builder.readTimeout(timeout, timeUnit);
			}
		});
	}

	@Before
	public void setup() {
		client = new CrnkClient(getBaseUri().toString());
		client.setPushAlways(false);
		client.addModule(createModule());
		setNetworkTimeout(client, 10000, TimeUnit.SECONDS);
	}

	@Override
	protected Application configure() {
		return new TestApplication();
	}

	public MetaModule createModule() {
		MetaModule module = MetaModule.create();
		module.addMetaProvider(new ResourceMetaProvider());
		module.putIdMapping(Schedule.class.getPackage().getName(), "app.resources");
		return module;
	}

	@ApplicationPath("/")
	private class TestApplication extends ResourceConfig {

		public TestApplication() {
			property(CrnkProperties.RESOURCE_SEARCH_PACKAGE, "io.crnk.meta.mock.model");
			CrnkFeature feature = new CrnkFeature();
			ObjectMapper objectMapper = feature.getObjectMapper();
			objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
			feature.addModule(createModule());
			register(feature);
		}
	}

}
