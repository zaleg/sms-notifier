import com.codeborne.selenide.Configuration;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class checker {
    private List<String> receivers = new ArrayList<String>();
    private DateFormat dateFormat = new SimpleDateFormat("dd/MM");
    private String todayDate = dateFormat.format(new Date());
    private String ACCOUNT_SID = System.getenv("ACCOUNT_SID");
    private String AUTH_TOKEN = System.getenv("AUTH_TOKEN");
    private String RECEIVER_01 = System.getenv("RECEIVER_01");
    private String RECEIVER_02 = System.getenv("RECEIVER_02");
    private String TWL_SERVICE = System.getenv("TWL_SERVICE");

    @Test
    public void checkCurrencyTest() {
        Configuration.browser = "chrome";
        Configuration.timeout = 10000;
        open("https://finance.i.ua/");
        String strUSDcurrency = $(By.xpath("(//*[@class='widget-currency_bank']//span)[5]")).shouldBe(visible).text();
        receivers.add(RECEIVER_01);
        receivers.add(RECEIVER_02);
        sendSMS(strUSDcurrency);
    }

    private void sendSMS(String msg) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        for (String receiver : receivers) {
            Message message = Message.creator(
                    new com.twilio.type.PhoneNumber(receiver),
                    new com.twilio.type.PhoneNumber(TWL_SERVICE),
                    "КУРС $ В БАНКАХ НА '" + todayDate + "': " + msg)
                    .create();
        }
    }
}

