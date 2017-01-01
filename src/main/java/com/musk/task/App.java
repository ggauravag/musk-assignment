package com.musk.task;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.dropbox.core.DbxException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;

public class App {

	private static Logger logger = Logger.getGlobal();

	public static void main(String[] args) throws DbxException, FileNotFoundException, IOException {

		ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
		try {
			AppConfig config = mapper.readValue(App.class.getClassLoader().getResourceAsStream("config.yaml"),
					AppConfig.class);
			logger.info("=== Configuration Loaded ===");
			logger.info(ReflectionToStringBuilder.toString(config, ToStringStyle.MULTI_LINE_STYLE));

			DropBoxUtils.DROPBOX_ACCESS_TOKEN = config.getDropbox().get("accessToken");

			App app = new App();
			Dealer dealer = app.getDealer("07490304055");

			if (dealer != null) {
				logger.info(dealer.toString());
				app.uploadDetails(dealer);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void uploadDetails(Dealer dealer) throws DbxException, FileNotFoundException, IOException {
		DropBoxUtils utils = new DropBoxUtils();
		utils.upload(dealer.toString());
	}

	public Dealer getDealer(String tinNumber) {
		Dealer dealer = null;

		Map<String, String> dataParams = new HashMap<String, String>();
		dataParams.put("tinNumber", tinNumber);
		dataParams.put("searchBy", "TIN");
		dataParams.put("backPage", "searchByTin_Inter.jsp");

		try {
			Response response = Jsoup.connect("http://www.tinxsys.com/TinxsysInternetWeb/dealerControllerServlet")
					.data(dataParams).method(Method.POST).execute();

			logger.log(Level.FINEST, response.body());
			logger.info("=========================");

			if (response.statusCode() == 200) {
				Document document = response.parse();

				Elements notFoundElements = document
						.getElementsContainingOwnText("Dealer Not Found for the entered TIN");

				if (notFoundElements.size() == 0) {
					Elements tables = document.getElementsByTag("table");
					boolean hasData = false;
					for (int i = 0; i < tables.size(); i++) {
						Element table = tables.get(i);
						Elements header = table.getElementsByAttributeValue("class", "headerBlue");

						if (header.size() != 0) {
							hasData = true;

							Elements columns = table.getElementsByAttributeValue("class", "tdGrey");
							Elements values = table.getElementsByAttributeValue("class", "tdWhite");
							DealerUtils dealerUtils = new DealerUtils();
							for (int j = 0; j < columns.size(); j++) {
								Element column = columns.get(j);
								Element value = values.get(j);

								String columnText = column.text().replaceAll("\u00A0", "").trim();
								String columnValue = value.text().replaceAll("\u00A0", "").trim();

								logger.log(Level.FINER, String.format("%s => %s", columnText, columnValue,
										columnText.length(), columnValue.length()));
								dealerUtils.setProperty(columnText, columnValue);
							}

							dealer = dealerUtils.getDealer();
						}
					}

					if (!hasData) {
						logger.severe("Invalid TIN : " + tinNumber);
					}
				} else {
					logger.severe("Invalid TIN : " + tinNumber);
				}

			} else {
				logger.severe("Error in Get Tin call !");
			}
		} catch (IOException e) {
			logger.severe(e.getMessage());
			logger.severe("Unable to connect to TINXSYS");
		}
		return dealer;
	}
}
