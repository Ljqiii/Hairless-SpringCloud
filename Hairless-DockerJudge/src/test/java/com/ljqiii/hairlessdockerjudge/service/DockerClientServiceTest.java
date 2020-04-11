package com.ljqiii.hairlessdockerjudge.service;

import com.ljqiii.hairlesscommon.domain.ProblemCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


@SpringBootTest
public class DockerClientServiceTest {

    @Autowired
    DockerClientService dockerClientService;

    ProblemCode problemCode;

    @BeforeEach
    public void setUp() throws Exception {


        String pomContent = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n" +
                "<project xmlns=\"http://maven.apache.org/POM/4.0.0\"\n" +
                "         xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"\n" +
                "         xsi:schemaLocation=\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\">\n" +
                "    <modelVersion>4.0.0</modelVersion>\n" +
                "\n" +
                "    <groupId>org.example</groupId>\n" +
                "    <artifactId>basicMavenProject</artifactId>\n" +
                "    <version>1.0-SNAPSHOT</version>\n" +
                "\n" +
                "    <dependencies>\n" +
                "        <dependency>\n" +
                "            <groupId>junit</groupId>\n" +
                "            <artifactId>junit</artifactId>\n" +
                "            <version>4.13</version>\n" +
                "            <scope>test</scope>\n" +
                "        </dependency>\n" +
                "\n" +
                "    </dependencies>\n" +
                "\n" +
                "    <build>\n" +
                "        <plugins>\n" +
                "            <plugin>\n" +
                "                <groupId>org.apache.maven.plugins</groupId>\n" +
                "                <artifactId>maven-compiler-plugin</artifactId>\n" +
                "                <configuration>\n" +
                "                    <source>1.8</source>\n" +
                "                    <target>1.8</target>\n" +
                "                </configuration>\n" +
                "            </plugin>\n" +
                "        </plugins>\n" +
                "    </build>\n" +
                "\n" +
                "</project>\n";

        String MainContent = "public class Main {\n" +
                "    public static void main(String[] args) {\n" +
                "\n" +
                "    }\n" +
                "}\n";

        String MainTestsContent = "import org.junit.Assert;\n" +
                "import org.junit.Test;\n" +
                "\n" +
                "public class MainTests {\n" +
                "\n" +
                "    @Test\n" +
                "    public void example() {\n" +
                "        Assert.assertTrue(true);\n" +
                "    }\n" +
                "}\n";

        problemCode = new ProblemCode();

        ProblemCode.ProblemCodeFileItem src = ProblemCode.ProblemCodeFileItem.builder()
                .filename("src")
                .path("/")
                .type("folder")
                .build();
        ProblemCode.ProblemCodeFileItem src_main = ProblemCode.ProblemCodeFileItem.builder()
                .filename("main")
                .path("/src")
                .type("folder")
                .build();
        src.addChildren(src_main);

        ProblemCode.ProblemCodeFileItem src_main_java = ProblemCode.ProblemCodeFileItem.builder()
                .filename("java")
                .path("/src/main")
                .type("folder")
                .build();
        src_main.addChildren(src_main_java);

        ProblemCode.ProblemCodeFileItem src_main_resources = ProblemCode.ProblemCodeFileItem.builder()
                .filename("resources")
                .path("/src/main")
                .type("folder")
                .build();
        src_main.addChildren(src_main_resources);

        ProblemCode.ProblemCodeFileItem src_main_java_Main = ProblemCode.ProblemCodeFileItem.builder()
                .filename("Main.java")
                .path("/src/main/java/")
                .content(MainContent)
                .type("file")
                .build();
        src_main_java.addChildren(src_main_java_Main);

        ProblemCode.ProblemCodeFileItem src_test = ProblemCode.ProblemCodeFileItem.builder()
                .filename("test")
                .path("/src/")
                .type("folder")
                .build();
        src.addChildren(src_test);

        ProblemCode.ProblemCodeFileItem src_test_java = ProblemCode.ProblemCodeFileItem.builder()
                .filename("java")
                .path("/src/test")
                .type("folder")
                .build();
        src_test.addChildren(src_test_java);

        ProblemCode.ProblemCodeFileItem src_test_java_Main = ProblemCode.ProblemCodeFileItem.builder()
                .filename("MainTests.java")
                .path("/src/test/java/")
                .type("file")
                .content(MainTestsContent)
                .build();
        src_test_java.addChildren(src_test_java_Main);

        ProblemCode.ProblemCodeFileItem pomxml = ProblemCode.ProblemCodeFileItem.builder()
                .filename("pom.xml")
                .path("/")
                .type("file")
                .content(pomContent)
                .build();

        ProblemCode.ProblemCodeFileItem readme = ProblemCode.ProblemCodeFileItem.builder()
                .filename("README.md")
                .path("/")
                .type("file")
                .content("change me please")
                .build();

        problemCode.addChildren(pomxml);
        problemCode.addChildren(src);
        problemCode.addChildren(readme);


    }

    @Test
    public void execProblemWithContainer() {

        dockerClientService.execProblemWithContainer("maven:3-jdk-8", "test", problemCode, "mvn clean test".split(" "),
                "/test", "/test", null, true);
        int a1 = 12;

    }
}
