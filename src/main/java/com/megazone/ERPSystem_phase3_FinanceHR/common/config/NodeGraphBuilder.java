package com.megazone.ERPSystem_phase3_FinanceHR.common.config;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.Attribute;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.Metamodel;
import jakarta.persistence.metamodel.PluralAttribute;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class NodeGraphBuilder {

    private final EntityManager entityManager;
    private final Map<String, Set<String>> dependencyGraph = new HashMap<>();

    public NodeGraphBuilder(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostConstruct
    public void buildGraph() {
        Metamodel metamodel = entityManager.getMetamodel();

        // 모든 JPA 엔터티 탐색
        for (EntityType<?> entity : metamodel.getEntities()) {
            String entityName = entity.getName();
            dependencyGraph.putIfAbsent(entityName, new HashSet<>());

            // 엔터티의 모든 속성 탐색
            entity.getAttributes().forEach(attribute -> {
                if (attribute.isAssociation()) {
                    if (attribute.getPersistentAttributeType() == Attribute.PersistentAttributeType.ONE_TO_MANY) {
                        // 컬렉션의 제네릭 타입 추출
                        Class<?> targetEntity = ((PluralAttribute<?, ?, ?>) attribute).getElementType().getJavaType();
                        dependencyGraph.get(entityName).add(targetEntity.getSimpleName());
                    } else {
                        // 일반 연관관계 처리
                        String targetEntityName = attribute.getJavaType().getSimpleName();
                        dependencyGraph.get(entityName).add(targetEntityName);
                    }
                }
            });
        }

        // 디버깅: 그래프 출력
        dependencyGraph.forEach((key, value) ->
                System.out.println(key + " -> " + value)
        );
    }

    public Map<String, Set<String>> getGraph() {
        return dependencyGraph;
    }
}