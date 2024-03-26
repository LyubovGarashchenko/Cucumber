package ru.netology.steps;
import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Когда;
import io.cucumber.java.ru.Пусть;
import io.cucumber.java.ru.Тогда;
import ru.netology.data.DataHelper;
import ru.netology.page.DashboardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.MoneyTransferPage;
import ru.netology.page.VerificationPage;

public class TemplateSteps {
        private static LoginPage loginPage;
        private static DashboardPage dashboardPage;
        private static VerificationPage verificationPage;
        private static MoneyTransferPage moneyTransferPage;

        @Пусть("пользователь залогинен с именем «vasya» и паролем «qwerty123»")
        public void loginWithNameAndPassword(DataHelper.AuthInfo info) {
            verificationPage = loginPage.validLogin(new DataHelper.AuthInfo(info.getLogin(), info.getPassword()));
        }

        @И("пользователь вводит проверочный код 'из смс' {string}")
        public void enterValidCode(DataHelper.VerificationCode code) {
            dashboardPage = verificationPage.validVerify(new DataHelper.VerificationCode("12345"));
        }

        @Когда("пользователь переводит 5 000 рублей с карты с номером 5559 0000 0000 0002 на свою 1 карту с главной страницы")
        public void setMoneyTransferPage(String amountToTransfer, DataHelper.CardInfo cardInfo) {
            dashboardPage = moneyTransferPage.makeValidTransfer(amountToTransfer, cardInfo);
        }

        @Тогда("тогда баланс его 1 карты из списка на главной странице должен стать 15 000 рублей.")
        public void getCardBalance (int index) {
        dashboardPage.getCardBalance(15);
        }

        @Когда("пользователь переводит 50 000 рублей с карты с номером 5559 0000 0000 0002 на свою 1 карту с главной страницы")
        public void invalidMoneyTransfer(String amountToTransfer, DataHelper.CardInfo cardInfo) {
            dashboardPage = moneyTransferPage.makeValidTransfer(amountToTransfer, cardInfo);
        }

        @Тогда("Появится ошибка о выполнении попытки перевода суммы, превышающей остаток на карте списания")
        public void findErrorMessage(String expectedText) {
                moneyTransferPage.findErrorMessage("Появится ошибка о выполнении попытки " +
                        "перевода суммы, превышающей остаток на карте списания");

        }
}
