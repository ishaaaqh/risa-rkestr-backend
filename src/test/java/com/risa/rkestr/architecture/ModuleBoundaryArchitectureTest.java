package com.risa.rkestr.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

@AnalyzeClasses(packages = "com.risa.rkestr")
class ModuleBoundaryArchitectureTest {

    @ArchTest
    static final ArchRule modules_should_not_have_cycles =
            slices()
                    .matching("com.risa.rkestr.(*)..")
                    .should()
                    .beFreeOfCycles();

    @ArchTest
    static final ArchRule core_modules_should_not_depend_on_derived_modules =
            noClasses()
                    .that()
                    .resideInAnyPackage(
                            RkestrModules.IDENTITY,
                            RkestrModules.WORKSPACE,
                            RkestrModules.WORKITEM,
                            RkestrModules.CLASSIFICATION,
                            RkestrModules.DEADLINE
                    )
                    .should()
                    .dependOnClassesThat()
                    .resideInAnyPackage(
                            RkestrModules.REMINDER,
                            RkestrModules.NOTIFICATION,
                            RkestrModules.SEARCH
                    )
                    .allowEmptyShould(true);
}