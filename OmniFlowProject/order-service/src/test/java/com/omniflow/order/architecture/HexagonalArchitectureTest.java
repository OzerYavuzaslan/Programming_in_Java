package com.omniflow.order.architecture;

import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

@AnalyzeClasses(packages = "com.omniflow.order", importOptions = ImportOption.DoNotIncludeTests.class)
class HexagonalArchitectureTest {

    @ArchTest
    static final ArchRule domainShouldNotDependOnSpringOrInfrastructure = noClasses()
            .that().resideInAPackage("..domain..")
            .should().dependOnClassesThat().resideInAnyPackage(
                    "org.springframework..",
                    "jakarta.persistence..",
                    "org.hibernate..",
                    "..order.infra..",
                    "..order.application.."
            );

    @ArchTest
    static final ArchRule applicationShouldNotDependOnInfrastructure = noClasses()
            .that().resideInAPackage("..application..")
            .should().dependOnClassesThat().resideInAPackage("..order.infra..")
            .allowEmptyShould(true);

    @ArchTest
    static final ArchRule entityClassesShouldOnlyResideInEntityPackage = classes()
            .that().haveSimpleNameEndingWith("JpaEntity")
            .should().resideInAPackage("..infra.persistence.entity..");
}