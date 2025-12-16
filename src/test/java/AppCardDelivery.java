import com.codeborne.selenide.*;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.*;

public class AppCardDelivery {


    @Test
    void shouldSuccessRegistration() {
        String date = LocalDate.now().plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        open("http://localhost:9999/");

        ElementsCollection inputText = $$(".input__control[type='text']");
        ElementsCollection inputTel = $$(".input__control[type='tel']");

        inputText.first().setValue("Екатеринбург");
        inputTel.first().sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.DELETE));
        inputTel.first().setValue(date);
        inputText.last().setValue("Иванов Иван");
        inputTel.last().setValue("+79999999999");

        $(".checkbox__box").click();
        $(".button_view_extra").click();
        $("[data-test-id = 'notification']").should(Condition.visible, Duration.ofSeconds(15));
        $("[data-test-id = 'notification'] .notification__content").shouldHave(text("Встреча успешно забронирована на " + date));
    }
}
