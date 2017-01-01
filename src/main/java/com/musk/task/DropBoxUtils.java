package com.musk.task;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import com.dropbox.core.DbxException;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.FolderMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.dropbox.core.v2.users.FullAccount;

public class DropBoxUtils {

	public static String DROPBOX_ACCESS_TOKEN;

	private static Logger logger = Logger.getGlobal();

	public FolderMetadata createFolder(String folderName) throws DbxException {
		DbxRequestConfig config = new DbxRequestConfig("musk-assignment/1.0.0");
		DbxClientV2 client = new DbxClientV2(config, DROPBOX_ACCESS_TOKEN);

		FolderMetadata folderMetadata = client.files().createFolder("/" + folderName);
		logger.info("=== Folder Created ===");
		logger.info(folderMetadata.toStringMultiline());
		return folderMetadata;
	}

	public Metadata deletePath(String path) throws DbxException {
		DbxRequestConfig config = new DbxRequestConfig("musk-assignment/1.0.0");
		DbxClientV2 client = new DbxClientV2(config, DROPBOX_ACCESS_TOKEN);

		Metadata metadata = client.files().delete(path);
		logger.info("=== Resource Deleted ===");
		logger.info(metadata.toStringMultiline());
		return metadata;
	}

	public void upload(String details) throws IOException, DbxException {

		DbxRequestConfig config = new DbxRequestConfig("musk-assignment");
		DbxClientV2 client = new DbxClientV2(config, DROPBOX_ACCESS_TOKEN);

		FolderMetadata metadata = createFolder(String.valueOf(System.currentTimeMillis()));

		try (InputStream in = new ByteArrayInputStream(details.getBytes(StandardCharsets.UTF_8))) {
			FileMetadata fileMetadata = client.files().uploadBuilder(metadata.getPathLower() + "/details.txt")
					.uploadAndFinish(in);
			logger.info("=== File Uploaded Successfully ===");
			logger.info(fileMetadata.toStringMultiline());
		}
	}

	public ListFolderResult listFiles() throws DbxException {

		DbxRequestConfig config = new DbxRequestConfig("musk-assignment");
		DbxClientV2 client = new DbxClientV2(config, DROPBOX_ACCESS_TOKEN);

		ListFolderResult result = client.files().listFolder("");
//		while (true) {
//			for (Metadata metadata : result.getEntries()) {
//				// System.out.println(metadata.getPathLower());
//			}
//
//			if (!result.getHasMore()) {
//				break;
//			}
//			result = client.files().listFolderContinue(result.getCursor());
//		}

		return result;
	}

}
