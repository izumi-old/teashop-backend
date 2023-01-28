package org.izumi.jstore.components;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.jmix.core.DataManager;
import io.jmix.core.LoadContext;
import io.jmix.core.Metadata;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author Aiden Izumi (aka Flamesson).
 */
@RequiredArgsConstructor
@Component
public class CustomDataManager {
    private final DataManager dataManager;
    private final Metadata metadata;

    public FluentCounter getCount(Class<?> clazz) {
        return new FluentCounter(dataManager, metadata, clazz);
    }

    @RequiredArgsConstructor(access = AccessLevel.PROTECTED)
    public static final class FluentCounter {
        private final DataManager dataManager;
        private final Metadata metadata;

        private final Map<String, Object> parameters = new HashMap<>();
        private final Class<?> entityClass;
        private String queryString;
        private Integer firstResult;
        private Integer maxResults;

        public long count() {
            final LoadContext.Query query = new LoadContext.Query(queryString);
            if (!parameters.isEmpty()) {
                query.setParameters(parameters);
            }
            if (Objects.nonNull(firstResult)) {
                query.setFirstResult(firstResult);
            }
            if (Objects.nonNull(maxResults)) {
                query.setMaxResults(maxResults);
            }

            return dataManager.getCount(new LoadContext<>(metadata.getClass(entityClass)).setQuery(query));
        }

        public FluentCounter parameter(String name, Object value) {
            parameters.put(name, value);
            return this;
        }

        public FluentCounter query(String queryString) {
            this.queryString = queryString;
            return this;
        }

        public FluentCounter firstResult(int firstResult) {
            this.firstResult = firstResult;
            return this;
        }

        public FluentCounter maxResults(int maxResults) {
            this.maxResults = maxResults;
            return this;
        }
    }
}
