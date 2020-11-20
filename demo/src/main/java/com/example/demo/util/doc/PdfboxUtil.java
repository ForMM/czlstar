package com.example.demo.util.doc;

import com.example.demo.util.UUIDGenerator;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class PdfboxUtil {
    private static Logger logger = LoggerFactory.getLogger(IcepdfUtil.class);

    public static int PdfToImg(String source,String destpath, String uuid){
        int total = 0;
        PDDocument document = null;
        try{
            File sFile = new File(source);
            document = document.load(sFile);
            PDFRenderer renderer = new PDFRenderer(document);
            int numberOfPages = document.getNumberOfPages();
            BufferedImage image = null;
            for (int i=0;i<numberOfPages;i++){
//                float ff = 1.5F * (float)width / 893.0F;
                image = renderer.renderImage(i,1f);
                File file = new File(destpath + uuid + "_" + i + ".jpg");
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();
                ImageIO.write(image, "jpg", file);
                image.flush();
            }
            total = numberOfPages;
            if (document != null) {
                document.close();
            }
        }catch (Exception e){
            logger.error("pdfbox error:" + e);
            total = -1;

        }
        return  total;
    }


    public static void main(String[] args) {
        String source = "/Users/hayashika/program-kk/upload-file/aa.pdf";
        String uuidc = UUIDGenerator.getUUID();
        String destpath="/Users/hayashika/program-kk/upload-file/";

        File file = new File(source);
        logger.info("name:{},size:{}",file.getName(),file.length());

        int i = PdfToImg(source, destpath,uuidc);
        logger.info("total:{}",i);


    }



}
