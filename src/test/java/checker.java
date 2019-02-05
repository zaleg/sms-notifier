import com.codeborne.selenide.Configuration;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import org.openqa.selenium.By;
import org.testng.annotations.Test;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class checker {
    @Test
    public void checkCurrencyTest() {
        Configuration.browser = "chrome";
        Configuration.timeout = 10000;
        open("https://finance.i.ua/");
        String strUSDcurrrency = $(By.xpath("(//*[@class='widget-currency_cash']//span)[5]")).shouldBe(visible).text();
        sendSMS(strUSDcurrrency);
    }

    private void sendSMS(String msg) {
        String ACCOUNT_SID = System.getenv("ACCOUNT_SID");
        String AUTH_TOKEN = System.getenv("AUTH_TOKEN");
        String RECEIVER = System.getenv("RECEIVER");
        String TWL_SERVICE = System.getenv("TWL_SERVICE");
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                new com.twilio.type.PhoneNumber(RECEIVER),
                new com.twilio.type.PhoneNumber(TWL_SERVICE),
                "КУРС ДОЛАРА: " + msg)
                .create();
    }
}

