package com.example.demo.util.doc;


import com.example.demo.util.UUIDGenerator;
import org.icepdf.core.pobjects.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class IcepdfUtil {
    private static Logger logger = LoggerFactory.getLogger(IcepdfUtil.class);

    public static int PdfToImg(String source,String destpath, String uuid){
        int total = 0;
        Document document = null;
        try{
            document=new Document();
            document.setFile(source);
            int numberOfPages = document.getNumberOfPages();
            BufferedImage image = null;
            for (int i=0;i<numberOfPages;i++){
                image = (BufferedImage)document.getPageImage(i, 1, 4, 0.0F, 1);
                File file = new File(destpath + uuid + "_" + i + ".jpg");
                if (file.exists()) {
                    file.delete();
                }
                file.createNewFile();
                ImageIO.write(image, "jpg", file);
                image.flush();
            }
        }catch (Exception e){
            logger.error("icepdf error:" + e);
            total = -1;

        }finally {
            if (null != document) {
                document.dispose();
            }
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
