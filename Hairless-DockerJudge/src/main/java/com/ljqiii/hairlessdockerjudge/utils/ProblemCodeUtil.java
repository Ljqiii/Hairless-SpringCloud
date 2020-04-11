package com.ljqiii.hairlessdockerjudge.utils;

import com.ljqiii.hairlesscommon.domain.ProblemCode;
import org.apache.commons.compress.archivers.ArchiveException;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.TreeSet;

public class ProblemCodeUtil {


    /**
     * 根据ProblemCode生成Tar的InputStream
     *
     * @param problemCode
     * @return
     */
    public static ByteArrayInputStream convertProblemCodeToInputStream(ProblemCode problemCode, String codePath) {

        //必须以根目录开始
        if (!codePath.startsWith("/")) {
            throw new IllegalArgumentException("code Path must start with unix root dir '/' ");
        }
        //去除路径结尾的/
        if (codePath.endsWith("/")) {
            codePath = codePath.substring(0, codePath.length() - 1);
        }

        ByteArrayInputStream inputStream = null;
        try {
            TarUtil.TarOutStreamBuilder builder = TarUtil.TarOutStreamBuilder.builder();

            TreeSet<ProblemCode.ProblemCodeFileItem> problemCodeFileItems = problemCode.getProblemCodeFileItems();
            addStringFileRecursive(problemCodeFileItems, builder, codePath);
            inputStream = builder.buildInputStream();
        } catch (ArchiveException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return inputStream;
    }

    /**
     * 递归添加文件
     *
     * @param problemCodeFileItems
     * @param builder
     * @param codePath
     * @throws IOException
     */
    private static void addStringFileRecursive(TreeSet<ProblemCode.ProblemCodeFileItem> problemCodeFileItems,
                                               TarUtil.TarOutStreamBuilder builder,
                                               String codePath) throws IOException {


        for (ProblemCode.ProblemCodeFileItem problemCodeFileItem : problemCodeFileItems) {
            String filename = problemCodeFileItem.getPath() + "/" + problemCodeFileItem.getFilename();
            if (problemCodeFileItem.getType().equals("folder")) {
                filename += "/";
            }
            String content = problemCodeFileItem.getContent();
            builder.addFileWithStringContent(filename, content == null ? "" : content);

            if (problemCodeFileItem.getChildren() != null && problemCodeFileItem.getChildren().size() > 0) {
                addStringFileRecursive(problemCodeFileItem.getChildren(), builder, codePath);
            }
        }
    }


}
