package server.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.StreamSupport;

import commons.Activity;
import server.database.ActivityRepository;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;

class DummyActivityRepository implements ActivityRepository {
	public final List<Activity> activities = new ArrayList<>();

	@Override
	public List<Activity> findAll() {
		return this.activities;
	}

	@Override
	public List<Activity> findAll(Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Activity> findAllById(Iterable<String> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Activity> List<S> saveAll(Iterable<S> entities) {
		return StreamSupport
			.stream(entities.spliterator(), false)
			.map(this::save)
			.toList();
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

	@Override
	public <S extends Activity> S saveAndFlush(S entity) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Activity> List<S> saveAllAndFlush(Iterable<S> entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteAllInBatch(Iterable<Activity> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllByIdInBatch(Iterable<String> ids) {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public void deleteAllInBatch() {
		// TODO Auto-generated method stub

	}

	@Override
	public Activity getOne(String id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Activity getById(String id) {
		return this.find(id).get();
	}

	private Optional<Activity> find(String id) {
		return this.activities
			.stream()
			.filter(q -> q.getId().equals(id))
			.findFirst();
	}

	@Override
	public <S extends Activity> List<S> findAll(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Activity> List<S> findAll(Example<S> example, Sort sort) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Activity> findAll(Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Activity> S save(S entity) {
		this.activities.add(entity);
		return entity;
	}

	@Override
	public Optional<Activity> findById(String id) {
		return this.activities
			.stream()
			.filter(a -> a.getId().equals(id))
			.findFirst();
	}

	@Override
	public boolean existsById(String id) {
		return this.find(id).isPresent();
	}

	@Override
	public long count() {
		return this.activities.size();
	}

	@Override
	public void deleteById(String id) {
		this.activities.remove(this.findById(id).get());
	}

	@Override
	public void delete(Activity entity) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAllById(Iterable<? extends String> ids) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll(Iterable<? extends Activity> entities) {
		// TODO Auto-generated method stub

	}

	@Override
	public void deleteAll() {
		// TODO Auto-generated method stub

	}

	@Override
	public <S extends Activity> Optional<S> findOne(Example<S> example) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Activity> Page<S> findAll(Example<S> example, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <S extends Activity> long count(Example<S> example) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public <S extends Activity> boolean exists(Example<S> example) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public <S extends Activity, R> R findBy(
		Example<S> example,
		Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction
	) {
		// TODO Auto-generated method stub
		return null;
	}
}