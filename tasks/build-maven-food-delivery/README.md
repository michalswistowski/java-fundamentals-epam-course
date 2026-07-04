# Food Delivery - Build Maven Exercise

In this exercise your job is to transform OOP Food Delivery exercise to be a multi-module maven project.

The project should have to following modules:

| Module      | java packages to add                                                      |
|-------------|---------------------------------------------------------------------------|
| persistence | com.epam.training.food.data <br /> com.epam.training.food.domain          | 
| service     | com.epam.training.food.service <br /> Include also the test source files. |
| application | all other packages                                                        |

Each module should contain source code of java packages it is defined in the table.

# Solution hints

Parent module
- Do not forget to specify pom **packaging type** for the parent pom.xml
- Parent pom should contain only **dependencyManagement**, not direct dependencies.
- Do not forget to add **maven-compiler-plugin** plugin to the parent pom, otherwise Autocode will not execute the tests.

Modules
- Modules should refer the parent pom (see parent tag).
- Modules should not define dependency versions. Those values are inherited from the parent.
  Dependency version of other modules can be given by ${project.version}.
- The `service` module must contain junit dependencies (otherwise Autocode tests will not be executed).
