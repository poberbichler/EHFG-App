package org.ehfg.app.mvc.export;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.ehfg.app.twitter.TweetDTO;
import org.ehfg.app.twitter.TwitterFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.view.document.AbstractXlsView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

/**
 * @author patrick
 * @since 10.2015
 */
@Controller
class TweetExport extends AbstractXlsView {
    private static final int TEXT_COLUMN_INDEX = 0;
    private static final int FULL_NAME_COLUMN_INDEX = 1;
    private static final int NICKNAME_COLUMN_INDEX = 2;
    private static final int CREATED_COLUMN_INDEX = 3;

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    private final TwitterFacade twitterFacade;

    @Autowired
    public TweetExport(TwitterFacade twitterFacade) {
        this.twitterFacade = twitterFacade;
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> model, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        final Sheet tweetSheet = workbook.createSheet("Tweets");
        Row headerRow = tweetSheet.createRow(0);

        headerRow.createCell(TEXT_COLUMN_INDEX).setCellValue("Text");
        headerRow.createCell(FULL_NAME_COLUMN_INDEX).setCellValue("User");
        headerRow.createCell(NICKNAME_COLUMN_INDEX).setCellValue("Nickname");
        headerRow.createCell(CREATED_COLUMN_INDEX).setCellValue("Created");

        List<TweetDTO> tweets = twitterFacade.findTweetsForExport((String) model.getOrDefault("hashtag", "EHFG2015"));
        for (int rowCounter = 0; rowCounter < tweets.size(); rowCounter++) {
            TweetDTO tweet = tweets.get(rowCounter);

            Row tweetRow = tweetSheet.createRow(rowCounter+1);
            tweetRow.createCell(TEXT_COLUMN_INDEX).setCellValue(tweet.getMessage());
            tweetRow.createCell(FULL_NAME_COLUMN_INDEX).setCellValue(tweet.getFullName());
            tweetRow.createCell(NICKNAME_COLUMN_INDEX).setCellValue(tweet.getNickName());
            tweetRow.createCell(CREATED_COLUMN_INDEX).setCellValue(tweet.getTimestamp().format(DATE_TIME_FORMATTER));
        }

    }
}
