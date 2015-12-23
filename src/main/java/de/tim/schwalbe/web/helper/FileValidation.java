package de.tim.schwalbe.web.helper;

import com.j256.simplemagic.ContentInfo;
import com.j256.simplemagic.ContentInfoUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author tim.schwalbe
 */
@Component
public class FileValidation {

        /**
         * Checks if input file is a jpeg or a png.
         *
         * @param image MultipartFile as an image
         * @return true if input file is a jpeg or a png, otherwise false
         * @throws IOException
         */
        public boolean isFileAnImageJpegOrPng(MultipartFile image) throws IOException {
                if (image != null) {
                        String contentType;
                        ContentInfoUtil util = new ContentInfoUtil();

                        ContentInfo info = util.findMatch(image.getInputStream());
                        contentType = info.getMimeType();

                        if ("image/jpeg".equalsIgnoreCase(contentType) || "image/png".equalsIgnoreCase(contentType)) {
                                return true;
                        } else {
                                return false;
                        }
                } else {
                        return false;
                }
        }
}
