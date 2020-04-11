package com.ljqiii.hairlessdockerjudge;

import com.alibaba.fastjson.JSONObject;
import com.ljqiii.hairlesscommon.domain.ProblemCode;
import com.ljqiii.hairlessdockerjudge.utils.ProblemCodeUtil;
import com.ljqiii.hairlessdockerjudge.utils.TarUtil;
import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.io.FileUtils;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;

public class TarTest {

    @Test
    public void createTar() throws IOException {
        ByteArrayInputStream inputStream = null;
        try {
            inputStream = TarUtil.TarOutStreamBuilder
                    .builder()
                    .addFileWithStringContent("/aaa", "aaaa")
                    .addFileWithStringContent("/b/aaaa", "aaaa")
                    .addFileWithStringContent("/c/", "")

                    .buildInputStream();
        } catch (ArchiveException | IOException e) {
            e.printStackTrace();
        }
        FileUtils.copyInputStreamToFile(inputStream, new File("/tmp/aa.tar"));
    }

    @Test
    public void createProblemCodeTarFile() throws IOException {
        ProblemCode problemCode = JSONObject.parseObject("{\"problemCodeFileItems\":[{\"children\":[{\"children\":[{\"children\":[{\"content\":\"public class Main {\\n    public static void main(String[] args) {\\n\\n    }\\n}\\n\",\"filename\":\"Main.java\",\"path\":\"/src/main/java/\",\"readOnly\":false,\"type\":\"file\"}],\"filename\":\"java\",\"path\":\"/src/main\",\"readOnly\":false,\"type\":\"folder\"},{\"filename\":\"resources\",\"path\":\"/src/main\",\"readOnly\":false,\"type\":\"folder\"}],\"filename\":\"main\",\"path\":\"/src\",\"readOnly\":false,\"type\":\"folder\"},{\"children\":[{\"children\":[{\"content\":\"import org.junit.Assert;\\nimport org.junit.Test;\\n\\npublic class MainTests {\\n\\n    @Test\\n    public void example() {\\n        Assert.assertTrue(true);\\n    }\\n}\\n\",\"filename\":\"Main.java\",\"path\":\"/src/test/java/\",\"readOnly\":false,\"type\":\"file\"}],\"filename\":\"java\",\"path\":\"/src/test\",\"readOnly\":false,\"type\":\"folder\"}],\"filename\":\"test\",\"path\":\"/src/\",\"readOnly\":false,\"type\":\"folder\"}],\"filename\":\"src\",\"path\":\"/\",\"readOnly\":false,\"type\":\"folder\"},{\"content\":\"change me please\",\"filename\":\"README.md\",\"path\":\"/\",\"readOnly\":false,\"type\":\"file\"},{\"content\":\"<?xml version=\\\"1.0\\\" encoding=\\\"UTF-8\\\"?>\\n<project xmlns=\\\"http://maven.apache.org/POM/4.0.0\\\"\\n         xmlns:xsi=\\\"http://www.w3.org/2001/XMLSchema-instance\\\"\\n         xsi:schemaLocation=\\\"http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd\\\">\\n    <modelVersion>4.0.0</modelVersion>\\n\\n    <groupId>org.example</groupId>\\n    <artifactId>basicMavenProject</artifactId>\\n    <version>1.0-SNAPSHOT</version>\\n\\n    <dependencies>\\n        <dependency>\\n            <groupId>junit</groupId>\\n            <artifactId>junit</artifactId>\\n            <version>4.13</version>\\n            <scope>test</scope>\\n        </dependency>\\n\\n    </dependencies>\\n\\n    <build>\\n        <plugins>\\n            <plugin>\\n                <groupId>org.apache.maven.plugins</groupId>\\n                <artifactId>maven-compiler-plugin</artifactId>\\n                <configuration>\\n                    <source>1.8</source>\\n                    <target>1.8</target>\\n                </configuration>\\n            </plugin>\\n        </plugins>\\n    </build>\\n\\n</project>\\n\",\"filename\":\"pom.xml\",\"path\":\"/\",\"readOnly\":false,\"type\":\"file\"}]}", ProblemCode.class);
        ByteArrayInputStream inputStream = ProblemCodeUtil.convertProblemCodeToInputStream(problemCode,"/");
        FileUtils.copyInputStreamToFile(inputStream, new File("/tmp/baseMavenProject.tar"));
    }

    @Test
    public void createTarDir(){
        try {
            ByteArrayInputStream inputStream = TarUtil.buildEmptyDir("asadfdswfdsa/adsfgdsf/");
            FileUtils.copyInputStreamToFile(inputStream, new File("/tmp/testdir.tar"));

        } catch (ArchiveException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
