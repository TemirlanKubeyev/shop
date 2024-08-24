package kz.runtime.shop.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {
    public boolean correctFormatFile(MultipartFile photo) {

        if (photo.getSize()>2000000) {
            return false;
        }

        if (photo.getContentType().equals("image/jpeg") || photo.getContentType().equals("image/png")) {
            return true;
        }else {
            return false;
        }
    }
}
