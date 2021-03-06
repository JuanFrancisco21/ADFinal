package com.ajaguilar.apiRestful.services;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import com.ajaguilar.apiRestful.AppMain;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.batch.BatchRequest;
import com.google.api.client.googleapis.batch.json.JsonBatchCallback;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.Permission;

public class DriveService {
	/** Application name. */
	private static final String APPLICATION_NAME = "Google Drive API Java Quickstart";
	/** Global instance of the JSON factory. */
	private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	/** Directory to store authorization tokens for this application. */
	private static final String TOKENS_DIRECTORY_PATH = "src/main/resources/com/ajaguilar/apiRestful/tokens";

	/**
	 * Global instance of the scopes required by this quickstart. If modifying these
	 * scopes, delete your previously saved tokens/ folder.
	 */
	private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);
	private static final String CREDENTIALS_FILE_PATH = "credentials.json";

	private static Drive service;
	
	public static Drive getService() {
		// Build a new authorized API client service.
		if (service == null) {
			try {
				final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
				service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
						.setApplicationName(APPLICATION_NAME).build();
				return service;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GeneralSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return service;
	}
	
	   /**
     * Sube un archivo al directorio raiz del usuario de Google Drive.
     * 
     * @param file: El archivo a subir
     * @throws IOException: Interrupcion de la comunicacion durante la operacion
     * @throws GeneralSecurityException: Errores relacionados con los permisos del usuario
     */
    public static String uploadFile(java.io.File file){
    	String result = "";
    	File fileMetadata = new File();
    	fileMetadata.setName(file.toPath().getFileName().toString());
    	
    	String mimeType;
		try {
			mimeType = Files.probeContentType(file.toPath());
			FileContent mediaContent = new FileContent(mimeType, file);
			File gfile = service.files().create(fileMetadata, mediaContent)
					.setFields("id, name, webViewLink")
					.execute();
			result = "http://drive.google.com/uc?export=view&id="+ gfile.getId();
			setPermission(gfile.getId().toString());//gfile.getId();
		} catch (IOException | GeneralSecurityException e) {
			result = "http://drive.google.com/uc?export=view&id=1iNbUIohjg-KoZPD-g6Z0fZo9Q7k8GTUF";
			e.printStackTrace();
		}
		
    	return result;
    }
	
	public static String uploadfile() {
		String result = "";
		File fileMetadata = new File();
		fileMetadata.setName("perfil.jpg");
		java.io.File filePath = new java.io.File("perfil.jpg");
		FileContent mediaContent = new FileContent("image/jpg", filePath);
		File file;
		try {
			file = getService().files().create(fileMetadata, mediaContent).setFields("id").execute();
			//System.out.println("File ID: " + file.getId());
			result = file.getId();
			setPermission(file.getId().toString());
		} catch (IOException | GeneralSecurityException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	/**
	 * Creates an authorized Credential object.
	 * 
	 * @param HTTP_TRANSPORT The network HTTP Transport.
	 * @return An authorized Credential object.
	 * @throws IOException If the credentials.json file cannot be found.
	 */
	private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
		// Load client secrets.
		InputStream in = AppMain.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		if (in == null) {
			throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
		}
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES)
						.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
						.setAccessType("offline").build();
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
		// returns an authorized Credential object.
		return credential;
	}
	
	/**
     * Comparte y se le da permisos de lectura a un archivo asociado a un usuario distinto al propietario de la cuenta
     * de Google Drive.
     *
     * @param fileId: El archivo que se va a compartir y al que se le va a dar permisos de lectura
     * @param email:  El correo del usuario al que se le van a conceder los permisos
     * @throws GeneralSecurityException: Errores relacionados con los permisos del usuario
     * @throws IOException:              Interrupcion de la comunicacion durante la operacion
     */
    public static void setPermission(String fileId) throws GeneralSecurityException, IOException {
        JsonBatchCallback<Permission> callback = new JsonBatchCallback<Permission>() {
            @Override
            public void onFailure(GoogleJsonError e, HttpHeaders responseHeaders) throws IOException {
                throw new IOException(e.getMessage());
            }

            @Override
            public void onSuccess(Permission permission, HttpHeaders responseHeaders) {
            }
        };
        BatchRequest batch = service.batch();
        Permission userPermission = new Permission()
                .setType("anyone")
                .setRole("reader");
        try {
            service.permissions().create(fileId, userPermission)
                    .setFields("id")
                    .queue(batch, callback);

            batch.execute();
        } catch (IOException e1) {
            throw new IOException(e1.getMessage());
        }
    }

	
}
