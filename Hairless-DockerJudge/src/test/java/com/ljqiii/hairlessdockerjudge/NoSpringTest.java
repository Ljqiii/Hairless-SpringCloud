package com.ljqiii.hairlessdockerjudge;

import com.google.common.collect.ImmutableList;
import com.google.common.jimfs.Configuration;
import com.google.common.jimfs.Jimfs;
import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;
import sun.nio.ch.IOUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

public class NoSpringTest {


    private static final int buffersize = 8048;


    @Test
    public void test() throws IOException {
        InputStream dsaf = IOUtils.toInputStream("dsaf", "uth-8");
    }

    @Test
    public void virtualFile() throws IOException {
        File f = File.createTempFile("asa", "aa");
        FileOutputStream fileOutputStream = new FileOutputStream(f);
        InputStream o = IOUtils.toInputStream("aaa", "utf-8");

        IOUtils.copy(o, fileOutputStream);

        System.out.println(1);
    }

    @Test
    public void jimfsTest() throws IOException {
        FileSystem fs = Jimfs.newFileSystem(Configuration.unix());
        Path foo = fs.getPath("/foo");
        Files.createDirectory(foo);
        Path bar = fs.getPath("/foo/a");
        Files.createDirectory(bar);

        Path hello = bar.resolve("hello.txt"); // /foo/hello.txt
        Files.write(hello, ImmutableList.of("hello world"), StandardCharsets.UTF_8);
        Path path = fs.getPath("/foo/a/");
        Collection<File> files = FileUtils.listFiles(path.toFile(), null, null);

        InputStream inputStream = Files.newInputStream(path);

        byte[] aaa = new byte[1];
        IOUtils.read(inputStream, aaa);

        Assert.assertEquals(new String(aaa), "h");
    }
}
