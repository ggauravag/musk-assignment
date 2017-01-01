package com.musk.task;

import java.io.IOException;

import com.dropbox.core.DbxException;
import com.dropbox.core.v2.files.FolderMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest extends TestCase {
	/**
	 * Create the test case
	 *
	 * @param testName
	 *            name of the test case
	 * @throws IOException
	 * @throws JsonMappingException
	 * @throws JsonParseException
	 */
	public AppTest(String testName) throws JsonParseException, JsonMappingException, IOException {
		super(testName);
		AppConfig config = new ObjectMapper(new YAMLFactory()).readValue(getClass().getClassLoader().getResourceAsStream("config.yaml"),
				AppConfig.class);
		DropBoxUtils.DROPBOX_ACCESS_TOKEN = config.getDropbox().get("accessToken");
	}

	/**
	 * @return the suite of tests being tested
	 */
	public static Test suite() {
		return new TestSuite(AppTest.class);
	}

	/**
	 * Rigourous Test :-)
	 * 
	 * @throws DbxException
	 */
	public void testDropBoxUtils() throws DbxException {
		DropBoxUtils dropBoxUtils = new DropBoxUtils();
		String folderName = "test";

		// To test the name of folder created is same
		FolderMetadata folderMetadata = dropBoxUtils.createFolder(folderName);
		assertTrue(folderMetadata.getName().equals(folderName));

		// To test the newly created folder exists
		ListFolderResult folderList = dropBoxUtils.listFiles();
		assertTrue(folderList.getEntries().contains(folderMetadata));

		// To test the name of deleted folder is same
		Metadata metadata = dropBoxUtils.deletePath(folderMetadata.getPathLower());
		assertEquals(metadata.getPathLower(), folderMetadata.getPathLower());
		
		// To test the deleted folder no more exists
		folderList = dropBoxUtils.listFiles();
		assertFalse(folderList.getEntries().contains(folderMetadata));
	}
}
