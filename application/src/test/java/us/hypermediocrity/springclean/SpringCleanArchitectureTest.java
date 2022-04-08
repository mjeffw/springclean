package us.hypermediocrity.springclean;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

@AnalyzeClasses(packages = "us.hypermediocrity.springclean")
class SpringCleanArchitectureTest {

  private static final String JAVA = "java..";
  private static final String COMMON = "..springclean.common..";
  private static final String DOMAIN = "..springclean.domain..";
  private static final String DOMAIN_ENTITY = "..springclean.domain.entity..";
  private static final String DOMAIN_PORT = "..springclean.domain.port..";

  /**
   * No cycles between packages are allowed.
   */
  @ArchTest static final ArchRule noCycles = slices().matching("us.hypermediocrity.springclean.(*)..").should()
      .beFreeOfCycles();

  /**
   * Package springclean.common contains common data types or utilities that other
   * classes can use, but depend on nothing.
   */
  @ArchTest static final ArchRule commonDependsOnNothing = noClasses().that().resideInAPackage(COMMON).should()
      .dependOnClassesThat().resideOutsideOfPackages(JAVA, COMMON);

  /**
   * No class in the springclean.domain packages should depend on anything outside
   * of those packages.
   */
  @ArchTest static final ArchRule domainOnlyDependsOnCommon = noClasses().that().resideInAPackage(DOMAIN).should()
      .dependOnClassesThat().resideOutsideOfPackages(JAVA, COMMON, DOMAIN);

  /**
   * Adapters should not depend on each other, just domain.
   */
  @ArchTest static final ArchRule adaptersCannotDependOnEachOther = slices().matching("..springclean.adapter.(*)..")
      .should().notDependOnEachOther();

  /**
   * Package springclean.domain.entity depends only on springclean.common.
   */
  @ArchTest static final ArchRule domainEntityOnlyDependsOnDomainCommon = noClasses().that()
      .resideInAPackage(DOMAIN_ENTITY).should().dependOnClassesThat()
      .resideOutsideOfPackages(JAVA, COMMON, DOMAIN_ENTITY);

  /**
   * Package springclean.domain.port depends only on springclean.domain.entity.
   */
  @ArchTest static final ArchRule domainPortOnlyDependsOnEntity = noClasses().that().resideInAPackage(DOMAIN_PORT)
      .should().dependOnClassesThat().resideOutsideOfPackages(JAVA, COMMON, DOMAIN_ENTITY, DOMAIN_PORT);
}
