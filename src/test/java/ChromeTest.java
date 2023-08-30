import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class ChromeTest {
    public String generateDate(long addDays, String pattern) {
        return LocalDate.now().plusDays(addDays).format(DateTimeFormatter.ofPattern(pattern));
    }
    String planningDate = generateDate(4, "dd.MM.yyyy");

    @Test
    void deliveryCardPositiveTest() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input").setValue("Молодцова Дарья");
        $("[data-test-id=phone] input").setValue("+79160000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=notification]").shouldHave(text("Встреча успешно забронирована на " + planningDate),
                Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    void deliveryCardNegativeCityTest() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Светлогорск");
        $("[data-test-id=date] input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input").setValue("Молодцова Дарья");
        $("[data-test-id=phone] input").setValue("+79160000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(text("Доставка в выбранный город недоступна"),
                Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    void deliveryCardEmptyCityTest() {
        open("http://localhost:9999");
        $("[data-test-id=date] input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input").setValue("Молодцова Дарья");
        $("[data-test-id=phone] input").setValue("+79160000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(text("Поле обязательно для заполнения"),
                Duration.ofSeconds(15)).shouldBe(visible);
    }

    @Test
    void deliveryCardEmptyDateTest() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id=name] input").setValue("Молодцова Дарья");
        $("[data-test-id=phone] input").setValue("+79160000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=date] span.input__sub").shouldBe(visible).shouldHave(text("Неверно введена дата"),
                Duration.ofSeconds(15));
    }

    @Test
    void deliveryCardNegativeDateTest() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(generateDate(0, "dd.MM.yyyy"));
        $("[data-test-id=name] input").setValue("Молодцова Дарья");
        $("[data-test-id=phone] input").setValue("+79160000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=date] span.input__sub").shouldBe(visible).shouldHave(text("Заказ на выбранную дату невозможен"),
                Duration.ofSeconds(15));
    }

    @Test
    void deliveryCardNegativeNameTest() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input").setValue("Molodtsova Daria");
        $("[data-test-id=phone] input").setValue("+79160000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldBe(visible).shouldHave(text("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."),
                Duration.ofSeconds(15));
    }

    @Test
    void deliveryCardEmptyNameTest() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=phone] input").setValue("+79160000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"),
                Duration.ofSeconds(15));
    }

    @Test
    void deliveryCardNegativePhoneTest() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input").setValue("Молодцова Дарья");
        $("[data-test-id=phone] input").setValue("89160000000");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldBe(visible).shouldHave(text("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."),
                Duration.ofSeconds(15));
    }

    @Test
    void deliveryCardEmptyPhoneTest() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input").setValue("Молодцова Дарья");
        $("[data-test-id=agreement]").click();
        $(byText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"),
                Duration.ofSeconds(15));
    }

    @Test
    void bankCardNoClickTest() {
        open("http://localhost:9999");
        $("[data-test-id=city] input").setValue("Москва");
        $("[data-test-id=date] input").doubleClick().sendKeys(planningDate);
        $("[data-test-id=name] input").setValue("Молодцова Дарья");
        $("[data-test-id=phone] input").setValue("+79160000000");
        $(byText("Забронировать")).click();
        $("[data-test-id=agreement].input_invalid .checkbox__text").shouldBe(visible).shouldHave(text("Я соглашаюсь с условиями обработки и использования моих персональных данных"),
                Duration.ofSeconds(15));
    }
}
