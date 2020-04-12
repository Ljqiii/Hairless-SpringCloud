package com.ljqiii.hairlessdockerjudge.utils;

import org.apache.commons.compress.archivers.ArchiveException;
import org.apache.commons.compress.archivers.ArchiveOutputStream;
import org.apache.commons.compress.archivers.ArchiveStreamFactory;
import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.io.IOUtils;

import java.io.*;

public class TarUtil {

    public static class TarOutStreamBuilder {
        ArchiveOutputStream archive;
        ByteArrayOutputStream outputStream;

        public static TarOutStreamBuilder builder() throws ArchiveException {
            TarOutStreamBuilder tarOutStreamBuilder = new TarOutStreamBuilder();
            tarOutStreamBuilder.outputStream = new ByteArrayOutputStream();
            tarOutStreamBuilder.archive = new ArchiveStreamFactory().createArchiveOutputStream(ArchiveStreamFactory.TAR, tarOutStreamBuilder.outputStream);
            return tarOutStreamBuilder;
        }

        public TarOutStreamBuilder addFileWithStringContent(String entryName, String content) throws IOException {
            TarArchiveEntry entry = new TarArchiveEntry(entryName);
            byte[] bytes = content.getBytes("utf-8");
            entry.setSize(bytes.length);
            archive.putArchiveEntry(entry);
            IOUtils.copy(new ByteArrayInputStream(bytes), archive);
            archive.closeArchiveEntry();
            return this;
        }

        private void finshibuild() throws IOException {
            archive.finish();
            archive.close();
        }

        public ByteArrayOutputStream build() throws IOException {
            finshibuild();
            return this.outputStream;
        }


        public ByteArrayInputStream buildInputStream() throws IOException {
            finshibuild();
            return new ByteArrayInputStream(this.outputStream.toByteArray());
        }
    }


    //创建tar格式的空文件夹
    public static ByteArrayInputStream buildEmptyDir(String path) {
        //文件夹以/结尾
        if (!path.endsWith("/")) {
            path += "/";
        }

        ByteArrayInputStream byteArrayInputStream = null;
        try {
            byteArrayInputStream = TarOutStreamBuilder
                    .builder()
                    .addFileWithStringContent(path, "")
                    .buildInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ArchiveException e) {
            e.printStackTrace();
        }
        return byteArrayInputStream;
    }


    public static void main(String[] args) throws IOException, ArchiveException {
        ByteArrayOutputStream build = TarOutStreamBuilder
                .builder()
                .addFileWithStringContent("a", "aaaaaaaaaaaaaaaaaaaaa")
                .addFileWithStringContent("a/aa", "sa")
                .build();

    }


}
