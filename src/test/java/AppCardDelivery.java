import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import org.junit.jupiter.api.*;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


public class AppCardDelivery {


    @Test
    void shouldSuccessRegistration() {
        String dateForm = LocalDate.now().plusDays(30).format(DateTimeFormatter.ofPattern("d"));
        String date = LocalDate.now().plusDays(30).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        open("http://localhost:9999/");

        $("[data-test-id = 'city'] input").setValue("ек");

        ElementsCollection cities = $$(".input__menu .menu-item__control");

        cities.find(text("Екатеринбург")).click();
        $("[data-test-id = 'city'] input").shouldHave(value("Екатеринбург"));
//        $("[data-test-id = 'date'] input").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME, Keys.DELETE));
//        $("[data-test-id = 'date'] input").setValue(date);
        $("[data-test-id = 'date'] input").click();

        try {
            if ($$(".calendar__day").find(exactText(dateForm)).getCssValue("color").equals("rgba(11, 31, 53, 0.3)")) {
                $("[data-step='1']").click();
                $$(".calendar__day").find(exactText(dateForm)).click();
            } else {
                $$(".calendar__day").find(exactText(dateForm)).click();
            }
        } catch (Error e) {
            $("[data-step='1']").click();
            $$(".calendar__day").find(exactText(dateForm)).click();
        }

        $("[data-test-id = 'name'] input").setValue("Иванов Иван");
        $("[data-test-id = 'phone'] input").setValue("+79999999999");
        $("[data-test-id = 'agreement'] .checkbox__box").click();
        $(".button_view_extra").click();
        $("[data-test-id = 'notification'] .notification__content").shouldHave(text("Встреча успешно забронирована на " + date),
                Duration.ofSeconds(15)).shouldBe(visible);
    }
}
