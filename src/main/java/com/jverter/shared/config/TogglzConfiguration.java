package com.jverter.shared.config;

import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.togglz.core.Feature;
import org.togglz.core.context.FeatureContext;
import org.togglz.core.manager.FeatureManager;
import org.togglz.core.manager.FeatureManagerBuilder;
import org.togglz.core.manager.TogglzConfig;
import org.togglz.core.metadata.FeatureMetaData;
import org.togglz.core.metadata.enums.EnumFeatureMetaData;
import org.togglz.core.repository.StateRepository;
import org.togglz.core.repository.cache.CachingStateRepository;
import org.togglz.core.repository.jdbc.JDBCStateRepository;
import org.togglz.core.spi.FeatureProvider;
import org.togglz.core.user.UserProvider;

import com.jverter.shared.model.AppFeatures;

@Configuration
public class TogglzConfiguration implements TogglzConfig {

	private static final String TOGGLZ_TABLE_NAME = "TOGGLZ";
	private static final AppFeatures[] FEATURES = AppFeatures.values();
	private static final Class<? extends Feature> FEATURES_CLASS = AppFeatures.class;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Bean
	public FeatureManager featureManager() {

		// Configure the JDBCStateRepository to use your data source and table name
		StateRepository stateRepository = getStateRepository();

		return new FeatureManagerBuilder().featureEnum(getFeatureClass()).featureProvider(new FeatureProvider() {
			
			@Override
			public Set<Feature> getFeatures() {
				// Implement logic to return the set of all available features
				Set<Feature> features = new HashSet<>();
				for (Feature feature : FEATURES) {
					features.add(feature);
				}
				return features;
			}

			@Override
			public FeatureMetaData getMetaData(Feature feature) {
				return new EnumFeatureMetaData(feature);
			}
		}).stateRepository(stateRepository).build();
	}

	@Override
	public StateRepository getStateRepository() {
		return new CachingStateRepository(new JDBCStateRepository(dataSource, TOGGLZ_TABLE_NAME));
	}

	@Override
	public Class<? extends Feature> getFeatureClass() {
		return FEATURES_CLASS;
	}

	@Override
	public UserProvider getUserProvider() {
		return null;
	}

	@Bean
	public ApplicationRunner insertEnumValues() {
		return new ApplicationRunner() {
			@Override
			public void run(ApplicationArguments args) throws Exception {
				// Ensure enum values are stored in the database
				ensureEnumValuesInserted();
			}
		};
	}

	private void ensureEnumValuesInserted() {
		for (Feature feature : FEATURES) {
			String featureName = feature.name();
			int count = jdbcTemplate.queryForObject(
					"SELECT COUNT(*) FROM " + TOGGLZ_TABLE_NAME + " where feature_name = ?", Integer.class,
					featureName);
			if (count == 0) {
				// Feature is missing, insert it
				jdbcTemplate.update("INSERT INTO " + TOGGLZ_TABLE_NAME + " VALUES (?, ?, ?, ?)", featureName,
						FeatureContext.getFeatureManager().isActive(feature), null, null);
			}
		}
	}

}
