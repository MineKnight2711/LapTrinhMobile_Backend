package com.example.keyboard_mobile_app.shared;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UploadImageService {

    private Storage storage;
    private String firekey = "src/main/java/com/example/keyboard_mobile_app/shared/firebase/firebase_key.json";

    public UploadImageService() throws IOException {
            String firebasekeyjson = firekey;
            FileInputStream serviceAccount = new FileInputStream(firebasekeyjson);
            if (FirebaseApp.getApps().isEmpty()) {
                GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
                FirebaseOptions options = new FirebaseOptions.Builder()
                        .setCredentials(credentials)
                        .setStorageBucket("keyboard_mobile_app.appspot.com")
                        .build();
                FirebaseApp.initializeApp(options);
            }
            FileInputStream storageAccount = new FileInputStream(firebasekeyjson);
            this.storage = StorageOptions.newBuilder()
                    .setCredentials(GoogleCredentials.fromStream(storageAccount))
                    .build()
                    .getService();
    }
    public String uploadImage(MultipartFile file,String savePath,String objectName) throws IOException {
        if (!file.isEmpty()) {
            String storagePath = savePath+objectName+".jpg";
            BlobId blobId = BlobId.of("keyboard_mobile_app.appspot.com", storagePath);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType("image/jpeg")
                    .setAcl(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.OWNER)))
                    .build();
            // Upload tệp lên firebase
            Blob blob = storage.create(blobInfo, file.getBytes());
            // Lấy đường dẫn của file v trả về
            String downloadUrl = "https://firebasestorage.googleapis.com/v0/b/" +
                    blob.getBucket() + "/o/" + URLEncoder.encode(blob.getName(), StandardCharsets.UTF_8) +
                    "?alt=media";
            return downloadUrl;
        }
        return null;
    }

    public List<String> uploadFiles(MultipartFile[] files) throws IOException {
        Storage storage = StorageOptions.getDefaultInstance().getService();
        String bucketName = "keyboard-mobile-app.appspot.com";
        List<String> urls = new ArrayList<>();
        for (MultipartFile file : files) {
            String filename = String.format("%d-%s", System.currentTimeMillis(), file.getOriginalFilename()).replaceAll(" ", "+");
            BlobInfo blobInfo = BlobInfo.newBuilder(bucketName, filename)
                    .setContentType(file.getContentType())
                    .setAcl(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.OWNER)))
                    .build();
            storage.create(blobInfo, file.getBytes());
            String downloadUrl = "https://firebasestorage.googleapis.com/v0/b/" +
                    blobInfo.getBucket() + "/o/" + URLEncoder.encode(blobInfo.getName(), StandardCharsets.UTF_8) +
                    "?alt=media";
            urls.add(downloadUrl);
        }
        return urls;
    }
    public void deleteExistImage(String path,String fileName) {
        String storagePath = path+fileName+".jpg";
        System.out.println(storagePath);
        BlobId blobId = BlobId.of("keyboard_mobile_app.appspot.com", storagePath);
        boolean deleted = storage.delete(blobId);
        if (deleted) {
            System.out.println("File " + fileName + " deleted successfully from Firebase Storage.");
        } else {
            System.out.println("Failed to delete file " + fileName + " from Firebase Storage.");
        }
    }
}

