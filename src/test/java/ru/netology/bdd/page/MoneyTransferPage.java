package ru.netology.bdd.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.bdd.data.DataHelper;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.$;

public class MoneyTransferPage {
    private SelenideElement amount = $("[data-test-id=amount] input");
    private SelenideElement fromCard = $("[data-test-id=from] input");
    private SelenideElement transfer = $("[data-test-id=action-transfer]");
    private SelenideElement cancel = $("[data-test-id=action-cancel]");
    private SelenideElement errorMessage = $("[data-test-id=error-notification] div");

    public DashboardPage validTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo) {
        makeTransfer(amountToTransfer,cardInfo);
        return new DashboardPage();
    }
    public void makeTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo){
        amount.setValue(amountToTransfer);
        fromCard.setValue(cardInfo.getCardNumber());
        transfer.click();
    }
    public void getErrorMessage(String expectedText){
        errorMessage.shouldBe(Condition.visible,Duration.ofSeconds(15)).
                shouldHave(Condition.exactText(expectedText));
    }
}
