package io.crnk.operations;

import io.crnk.core.engine.http.HttpMethod;
import io.crnk.core.queryspec.QuerySpec;
import io.crnk.core.repository.ResourceRepositoryV2;
import io.crnk.core.resource.list.ResourceList;
import io.crnk.operations.client.OperationsCall;
import io.crnk.operations.client.OperationsClient;
import io.crnk.operations.model.MovieEntity;
import io.crnk.operations.model.PersonEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

public class OperationsPostTest extends AbstractOperationsTest {

	protected ResourceRepositoryV2<MovieEntity, UUID> movieRepo;

	private OperationsClient operationsClient;

	@Before
	@Override
	public void setUp() throws Exception {
		super.setUp();

		movieRepo = client.getRepositoryForType(MovieEntity.class);
		operationsClient = new OperationsClient(client);
	}

	@Test
	public void testMultiplePost() {
		ResourceRepositoryV2<PersonEntity, UUID> personRepo = client.getRepositoryForType(PersonEntity.class);

		PersonEntity person1 = newPerson("1");
		PersonEntity person2 = newPerson("2");
		MovieEntity movie = newMovie("test");
		movie.setDirectors(new HashSet<>(Arrays.asList(person1, person2)));

		OperationsCall call = operationsClient.createCall();
		call.add(HttpMethod.POST, movie);
		call.add(HttpMethod.POST, person1);
		call.add(HttpMethod.POST, person2);
		call.execute();


		QuerySpec querySpec = new QuerySpec(PersonEntity.class);
		ResourceList<PersonEntity> persons = personRepo.findAll(querySpec);
		Assert.assertEquals(2, persons.size());

		querySpec = new QuerySpec(MovieEntity.class);
		querySpec.includeRelation(Arrays.asList("directors"));
		ResourceList<MovieEntity> movies = movieRepo.findAll(querySpec);
		Assert.assertEquals(1, movies.size());
		movie = movies.get(0);
		Assert.assertEquals(2, movie.getDirectors().size());
	}
}
