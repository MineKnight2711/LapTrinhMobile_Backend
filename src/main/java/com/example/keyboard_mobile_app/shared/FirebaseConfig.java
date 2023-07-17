package com.example.keyboard_mobile_app.shared;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Configuration
public class FirebaseConfig {
    String firekey="src/main/java/com/example/keyboard_mobile_app/shared/firebase/firebase_key.json";
    @Bean
    public FirebaseApp firebaseApp() throws IOException {
        FileInputStream serviceAccount = new FileInputStream(firekey);
        FirebaseApp firebaseApp = null;
        List<FirebaseApp> firebaseApps = FirebaseApp.getApps();
        for(FirebaseApp app : firebaseApps){
            if(app.getName().equals(FirebaseApp.DEFAULT_APP_NAME)){
                firebaseApp = app;
            }
        }
        if (firebaseApp == null) {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .setProjectId("keyboard-mobile-app")
                    .build();
            firebaseApp = FirebaseApp.initializeApp(options, FirebaseApp.DEFAULT_APP_NAME);
            serviceAccount.close();
        }
        return firebaseApp;
    }

    @Bean
    public Firestore firestore() throws IOException {
        FileInputStream serviceAccount = new FileInputStream(firekey);

        Firestore firestore = FirestoreOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setProjectId("keyboard-mobile-app")
                .build()
                .getService();
        // Close the FileInputStream after creating the Firestore object
        serviceAccount.close();
        return firestore;
    }
}
