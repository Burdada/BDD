package ru.netology.bdd.test;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.bdd.data.DataHelper;
import ru.netology.bdd.page.DashboardPage;
import ru.netology.bdd.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MoneyTransferTest {

    DashboardPage dashboardPage;
    @BeforeEach
    void openAndLogin() {
        open("http://localhost:9999");
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);

    }


    @Test
    void shouldTransferMoneyBetweenOwnCards() {
        var firstCardInfo = DataHelper.getFirstCardInfo();
        var secondCardInfo = DataHelper.getSecondCardInfo();
        var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        var amount = DataHelper.generateValidAmount(firstCardBalance);
        var expectedFirstCardBalance = firstCardBalance - amount;
        var expectedSecondCardBalance = secondCardBalance + amount;
        var moneyTransferPage = dashboardPage.chooseCardForTransfer(secondCardInfo);
        dashboardPage = moneyTransferPage.validTransfer(String.valueOf(amount),firstCardInfo);
        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        assertEquals(expectedFirstCardBalance,actualFirstCardBalance);
        assertEquals(expectedSecondCardBalance, actualSecondCardBalance);

    }
    @Test
    void shouldHaveErrorIfAmountMoreBalance() {
        var firstCardInfo = DataHelper.getFirstCardInfo();
        var secondCardInfo = DataHelper.getSecondCardInfo();
        var firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        var amount = DataHelper.generateInValidAmount(firstCardBalance);
        var moneyTransferPage = dashboardPage.chooseCardForTransfer(secondCardInfo);
        moneyTransferPage.makeTransfer(String.valueOf(amount),secondCardInfo);
        moneyTransferPage.getErrorMessage("Ошибка! ");
        var actualFirstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        var actualSecondCardBalance = dashboardPage.getCardBalance(secondCardInfo);
        assertEquals(firstCardBalance,actualFirstCardBalance);
        assertEquals(secondCardBalance, actualSecondCardBalance);

    }
}
